package com.avenida.banten.core;

import java.util.*;

import org.apache.commons.lang3.*;

import org.slf4j.*;

import org.springframework.beans.*;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.*;
import org.springframework.beans.factory.config.*;
import org.springframework.beans.factory.support.*;

import org.springframework.boot.*;
import org.springframework.boot.context.embedded.*;

import org.springframework.context.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;

import org.springframework.web.context.support.*;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.avenida.banten.core.beans.CoreBeansConfiguration;
import com.avenida.banten.core.web.WebletContainer;

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

  /** The application-wise landing url, null if not configured.
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


  /** Sets the landing url.
   *
   * If called, you must pass a non-null value. If not called, the web
   * application container will define how to handle requests to the root of
   * the web context.
   *
   * @param theLandingUrl the landing url. It cannot be null.
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

        registerCoreModule((BeanDefinitionRegistry) parentContext);
        registerModules((BeanDefinitionRegistry)parentContext);
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
  private void registerModules(final BeanDefinitionRegistry registry)
      throws Error {
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
      registerWeblets(aModule);
    }

    // Initializes the modules
    for (Module aModule : modulesInitialized) {
      log.info("Initializing the module: {}" , aModule.getName());
      aModule.init(moduleRegistry);
    }

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
  private void registerApi(
      final BeanDefinitionRegistry registry, final Module module) {
    ConfigurationApi api = module.getConfigurationApi();
    if (api != null) {
      ModuleApiRegistry.register(api);
    }
  }

  /** The banten base configuration for the module private spring
   * application context.
   *
   * This configuration defines beans that are available in the dispatcher
   * servlet application context.
   */
  @Configuration
  static class BantenPrivateConfiguration extends WebMvcConfigurationSupport {

    @Autowired ModuleDescription moduleDescription;

    /** Bean that supports ${property} in @Value annotations.
     *
     * @return the configurer that replaces ${property} with the corresponding
     * environment values. Never returns null.
     */
    @Bean
    public static PropertySourcesPlaceholderConfigurer
        propertySourcesPlaceholderConfigurer() {
      return new PropertySourcesPlaceholderConfigurer();
    }

    /** {@inheritDoc}
     *
     * This implementation makes the module serve static content from the
     * module classpath.
     */
    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
      registry.addResourceHandler("/**")
        .addResourceLocations(
            "classpath:/" + moduleDescription.getClasspath() + "/static/");
        //.setCachePeriod(cachePeriod);
    }
  }

  /** Utility class to add a concrete instance of an object as a bean in a
   * bean factory.
   *
   * This is used, for example, to make module information available to the
   * private spring application context (see ModuleDescription) and to create
   * a bean with the web application root context.
   */
  public static class ObjectFactoryBean implements FactoryBean<Object> {

    /** The instance of the module description to expose as a spring bean.
     */
    private Object instance;

    private Class<?> type;

    private static void register(final BeanDefinitionRegistry registry,
        final Class<?> type, final Object instance, final String beanName) {

      MutablePropertyValues descriptionValues = new MutablePropertyValues();
      descriptionValues.add("instance", instance);
      descriptionValues.add("type", type);

      GenericBeanDefinition descriptionDefinition;
      descriptionDefinition = new GenericBeanDefinition();
      descriptionDefinition.setBeanClass(ObjectFactoryBean.class);
      descriptionDefinition.setPropertyValues(descriptionValues);
      descriptionDefinition.setLazyInit(true);

      registry.registerBeanDefinition(beanName,
          descriptionDefinition);
    }

    public void setInstance(final Object theInstance) {
      instance = theInstance;
    }

    public void setType(final Class<?> theType) {
      type = theType;
    }

    @Override
    public Object getObject() throws Exception {
      return instance;
    }

    @Override
    public Class<?> getObjectType() {
      return type;
    }

    @Override
    public boolean isSingleton() {
      return true;
    }
  }

  /** The module description.
   *
   * Banten adds this to the module private spring application context. This
   * description includes the module name, the module classpath and the module
   * namespace.
   */
  public static class ModuleDescription {

    /** The module name, never null. */
    private String name;

    /** The module classpath, the package of the module class, never null. */
    private String classpath;

    /** The module namespace, never null. */
    private String namespace;

    /** Creates a module description.
     *
     * @param theName the module name. It cannot be null.
     *
     * @param theClasspath the module classpath, ie: the package of the module
     * class. It cannot be null.
     *
     * @param theNamespace the module namespace. It cannot be null.
     */
    ModuleDescription(final String theName, final String theClasspath,
        final String theNamespace) {
      name = theName;
      classpath = theClasspath;
      namespace = theNamespace;
    }

    /** Obtains the module name.
     *
     * @return the module name, never null.
     */
    public String getName() {
      return name;
    }

    /** Obtains the module classpath, the package of the module class.
     *
     * @return the module classpath, never null.
     */
    public String getClasspath() {
      return classpath;
    }

    /** Obtains the module namespace.
     *
     * @return the module namespace, never null.
     */
    public String getNamespace() {
      return namespace;
    }
  }

  /** Register the module private configuration.
   *
   * This creates a dispatcher servlet a spring context configured with the
   * module private configuration, if any.
   *
   * @param registry the bean definition registry. It cannot be null.
   *
   * @param module the module to register. It cannot be null.
   */
  private void registerPrivateConfiguration(
      final BeanDefinitionRegistry registry, final Module module) {

    // Banten will prefix the classpaths of the module with this value. This
    // creates a sort of 'namespace' for the module.
    String moduleClasspath = module.getClass().getPackage().getName()
        .replace(".", "/");

    if (module.getPrivateConfiguration() != null) {
      String name = "private-" + module.getName();

      log.info("Registering Private configuration for: {}", module.getName());

      AnnotationConfigWebApplicationContext privateContext;
      privateContext = new AnnotationConfigWebApplicationContext();
      privateContext.register(BantenPrivateConfiguration.class,
          module.getPrivateConfiguration());

      // Adds some module information as environment properties of the current
      // environment.
      ConfigurableEnvironment environment = privateContext.getEnvironment();
      MutablePropertySources propertySources = environment.getPropertySources();
      Map<String, Object> moduleProperties = new HashMap<String, Object>();
      moduleProperties.put("banten.moduleClasspath", moduleClasspath);
      propertySources.addFirst(new MapPropertySource(
          "MODULE_PRIVATE_PROPERTIES", moduleProperties));

      // Let the dispatcher servlet initialize the context. The dispatcher
      // servlet will set the parent, do its magic on the context and call
      // refresh.
      DispatcherServlet dispatcherServlet;
      dispatcherServlet = new DispatcherServlet(privateContext);

      // Create a bean definition for a bean of type ServletRegistrationBean
      // that registers the dispatcherServlet in the web context. We cannot
      // add this to the BantenPrivateConfiguration because we manually create
      // the dispatcher servlet.
      ConstructorArgumentValues dispatcherMapping;
      dispatcherMapping = new ConstructorArgumentValues();
      dispatcherMapping.addIndexedArgumentValue(0, dispatcherServlet);
      dispatcherMapping.addIndexedArgumentValue(1,
          "/" + module.getNamespace() + "/*");

      MutablePropertyValues beanNamePropertyValue = new MutablePropertyValues();
      beanNamePropertyValue.add("name", name);

      GenericBeanDefinition servletRegistrationBean;
      servletRegistrationBean = new GenericBeanDefinition();
      servletRegistrationBean.setBeanClass(ServletRegistrationBean.class);
      servletRegistrationBean.setConstructorArgumentValues(dispatcherMapping);
      servletRegistrationBean.setPropertyValues(beanNamePropertyValue);
      servletRegistrationBean.setLazyInit(true);

      registry.registerBeanDefinition(name, servletRegistrationBean);

      // Creates the bean definition for a factory bean that exposes the module
      // description.
      ModuleDescription description = new ModuleDescription(module.getName(),
          moduleClasspath, module.getNamespace());
      ObjectFactoryBean.register(registry, description.getClass(), description,
          "banten.moduleDescription");
    }
  }

  /** Register the weblets into the WebletContainer.
   *
   * @param module the module that has the weblets to register. It cannot be
   * null.
   */
  private void registerWeblets(final Module module) {
    if (module.getWeblets() != null) {
      WebletContainer.instance().register(module, module.getWeblets());
    }
  }

  /** Register the core module.
   *
   * @param registry the bean definition registry.
   */
  private void registerCoreModule(final BeanDefinitionRegistry registry) {
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
