/* vim: set et sw=2 cindent fo=qroca: */

package com.avenida.banten.core;

import java.util.*;
import org.apache.commons.lang3.Validate;

import org.slf4j.*;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.annotation.*;
import org.springframework.beans.factory.config.*;
import org.springframework.beans.factory.support.*;

import org.springframework.boot.*;
import org.springframework.boot.context.embedded.ServletRegistrationBean;

import org.springframework.context.*;

import org.springframework.core.env.*;

import org.springframework.web.context.support.*;
import org.springframework.web.servlet.DispatcherServlet;

import com.avenida.banten.core.beans.CoreBeansConfiguration;

/** Bootstrap the Banten's Application.
 *
 * The application creates a {@link SpringApplication} using the {@link Module}
 * as {@link Configuration} as entry points.
 *
 * Each {@link Module} defines public beans within the
 * {@link Module#getModuleConfiguration}, those one will be merge into the
 * main application context. However, configuration declared within the
 * {@link Module#getPrivateConfiguration()} will be isolated; The main or
 * parent {@link ApplicationContext} is the same where {@link Module}s has
 * been declared.
 *
 * The {@link ApplicationContext} looks like:
 *
 *<code>
 *                /[>>>Parent Context<<<]\
 *               |                        |
 *               |                        |
 *       [Module A(public)]       [Module B (public)] ...
 *           |                                  |
 *  [MVC Context (private)]            [MVC Context (private)]
 *
 *</code>
 *
 * @author waabox (emi[at]avenida[dot]com)
 */
public abstract class BantenApplication {

  /** The log. */
  private final Logger log = LoggerFactory.getLogger(BantenApplication.class);

  /** The list of modules to bootstrap the application, it's never null.*/
  private final List<Class<? extends Module>> moduleClasses;

  /** The module registry, it's never null. */
  private final ModuleApiRegistry moduleRegistry = ModuleApiRegistry.instance();

  /** The spring boot application, null if not yet created.
   *
   * This is initialized with getApplication().
   */
  private SpringApplication application = null;

  /** The application-wise landing URL, null if not configured.
   *
   * See HomeServlet for more information.
   */
  private String landingUrl = null;

  /** Creates a new Application with the given modules.
   * @param modules the list of modules to bootstrap, cannot be null.
   */
  @SafeVarargs
  protected BantenApplication(final Class<? extends Module> ... modules) {
    Validate.notNull(modules, "The modules cannot be null");
    moduleClasses = Arrays.asList(modules);
  }

  /** Sets the landing URL.
   *
   * If called, you must pass a non-null value. If not called, the web
   * application container will define how to handle requests to the root of
   * the web context.
   *
   * @param theLandingUrl the landing URL. It cannot be null.
   */
  protected void setLandingUrl(final String theLandingUrl) {
    landingUrl = theLandingUrl;
  }

  /** Gets the currently wrapped spring boot application, creating one if not
   * yet created.
   *
   * This is not serialized, so don't attempt to call this from a
   * multi-threaded context. This is package access so that it can be used from
   * BantenApplicationContextLoader, a test utility.
   *
   * @return the Spring Application, never null.
   */
  SpringApplication getApplication() {

    if (application != null) {
      return application;
    }

    application = new SpringApplication(getClass());

    application.addInitializers(
        new ApplicationContextInitializer<ConfigurableApplicationContext>() {

      /** Initializes the application context.
       * @param parentContext the parent application context.
       */
      @Override
      public void initialize(
          final ConfigurableApplicationContext parentContext) {
        registerCoreBeans((BeanDefinitionRegistry) parentContext);
        registerModules((BeanDefinitionRegistry) parentContext);
      }

    });

    log.info("Finish application configuration, starting Spring's context");

    return application;
  }

  /** Run the Spring application.
   * This action creates and refresh the new application context.
   *
   * @param args the command line arguments.
   *
   * @return the new Spring's ApplicationContext.
   */
  public final ConfigurableApplicationContext run(final String[] args) {
    return getApplication().run(args);
  }

  /** Register the modules within the Spring's root application context.
   * @param registry the registry.
   * @throws Error
   */
  private void registerModules(final BeanDefinitionRegistry registry) {
    log.info("Registering Banten Modules");

    InitContext.init(registry);

    List<Module> modulesInitialized = new LinkedList<>();

    for(Class<? extends Module> moduleClass : moduleClasses) {
      try {
        modulesInitialized.add(moduleClass.newInstance());
      } catch (Exception e) {
        log.error("Cannot instantiate the module: {}", moduleClass);
        log.error("{} should have an empty constructor", moduleClass);
        throw new Error(e);
      }
    }

    // Register the beans into the Application Context.
    for (Module aModule : modulesInitialized) {
      log.info("Registering: {}" , aModule.getName());
      registerPublicConfiguration(registry, aModule);
      registerPrivateConfiguration(registry, aModule);
      registerApi(registry, aModule);
    }

    // Initializes the modules
    for (Module aModule : modulesInitialized) {
      log.info("Initializing the module: {}" , aModule.getName());
      aModule.init(moduleRegistry);
    }

    moduleRegistry.initApi();

    InitContext.destroy();

    log.info("Finish the registration of the Banten modules");
  }

  /** Register the Module.
   *
   * @param registry the bean definition registry. It cannot be null.
   *
   * @param module the module to register. It cannot be null.
   */
  private void registerPublicConfiguration(
      final BeanDefinitionRegistry registry, final Module module) {
    if (module.getPublicConfiguration() != null) {
      BeanDefinition moduleDef = new AnnotatedGenericBeanDefinition(
          module.getPublicConfiguration());
      moduleDef.setLazyInit(true);
      registry.registerBeanDefinition(module.getName(), moduleDef);
    }
  }

  /** Register the Module's configuration API if any.
   * @param registry the bean definition registry.
   * @param module the module.
   */
  private void registerApi(final BeanDefinitionRegistry registry,
      final Module module) {
    ConfigurationApi api = module.getConfigurationApi();
    if (api != null) {
      ModuleApiRegistry.register(api);
    }
  }

  /** Register the module private configuration.
   *
   * This creates a dispatcher Servlet with a spring context configured with
   * the module private configuration if the module defines such private
   * configuration.
   *
   * @param registry the bean definition registry. It cannot be null.
   *
   * @param aModule the module to register. It cannot be null.
   */
  private void registerPrivateConfiguration(
      final BeanDefinitionRegistry registry, final Module aModule) {

    if (!(aModule instanceof WebModule)) {
      return;
    }

    final WebModule module = (WebModule) aModule;

    Validate.notNull(module.getPrivateConfiguration(),
        "WebModules must declare a private configuration.");
    Validate.notNull(module.getNamespace(),
        "WebModules must declare a namespace.");

    /*
     * Banten will prefix the classpaths of the module with this value.
     * This creates a sort of 'namespace' for the module.
     */
    final String mClasspath;
    mClasspath = module.getClass().getPackage().getName().replace(".", "/");

    log.info("Registering Private configuration for: {}", module.getName());

    AnnotationConfigWebApplicationContext privateContext;
    privateContext = new AnnotationConfigWebApplicationContext() {

      /**  Hack to create the Banten's module description.
       *
       * The AnnotationConfigWebApplicationContext is not a
       * BeanDefinitionRegistry, so we override the loadDefinitions to create
       * a singleton for the module description.
       */
      @Override protected void loadBeanDefinitions(
          final DefaultListableBeanFactory beanFactory) {
        super.loadBeanDefinitions(beanFactory);
        ModuleDescription description = new ModuleDescription(
            module.getName(),
            mClasspath,
            module.getNamespace(),
            module.getRelativePath()
        );
        beanFactory.registerSingleton("banten.moduleDescription", description);
      }
    };

    privateContext.register(BantenPrivateConfiguration.class,
        module.getPrivateConfiguration());

    Map<String, Object> properties = new HashMap<>();
    properties.put("banten.moduleClasspath", mClasspath);

    MapPropertySource source;
    source = new MapPropertySource("module.properties", properties);
    privateContext.getEnvironment().getPropertySources().addFirst(source);

    /*
     * Let the dispatcher Servlet initialize the context. The dispatcher
     * Servlet will set the parent, do its magic on the context and call
     * refresh.
     */
    DispatcherServlet dispatcherServlet;
    dispatcherServlet = new DispatcherServlet(privateContext);

    /*
     * Create a bean definition for a bean of type ServletRegistrationBean
     * that registers the dispatcherServlet in the web context. We cannot
     * add this to the BantenPrivateConfiguration because we manually create
     * the dispatcher Servlet.
     */
    ConstructorArgumentValues constructor;
    constructor = new ConstructorArgumentValues();
    constructor.addIndexedArgumentValue(0, dispatcherServlet);
    constructor.addIndexedArgumentValue(1, "/" + module.getNamespace() + "/*");

    String name = "private-" + module.getName();

    MutablePropertyValues beanNamePropertyValue = new MutablePropertyValues();
    beanNamePropertyValue.add("name", name);

    GenericBeanDefinition servletRegistrationBean;
    servletRegistrationBean = new GenericBeanDefinition();
    servletRegistrationBean.setBeanClass(ServletRegistrationBean.class);
    servletRegistrationBean.setConstructorArgumentValues(constructor);
    servletRegistrationBean.setPropertyValues(beanNamePropertyValue);
    servletRegistrationBean.setLazyInit(true);

    registry.registerBeanDefinition(name, servletRegistrationBean);
  }

  /** Register the Banten's core beans in the provided bean definition registry.
   *
   * The provided registry should correspond to the public application context,
   * so this operation adds the Banten's core beans to the public application
   * context.
   *
   * @param registry the bean definition registry. It cannot be null.
   */
  private void registerCoreBeans(final BeanDefinitionRegistry registry) {
    log.info("Registering core beans");
    if (landingUrl != null) {
      ObjectFactoryBean.register(registry, String.class, landingUrl,
          "banten.landingUrl");
    }
    BeanDefinition coreBean = new GenericBeanDefinition();
    coreBean.setBeanClassName(CoreBeansConfiguration.class.getName());
    registry.registerBeanDefinition("coreBeansConfiguration", coreBean);
  }
}
