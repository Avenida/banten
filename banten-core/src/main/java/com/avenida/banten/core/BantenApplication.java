/* vim: set et sw=2 cindent fo=qroca: */

package com.avenida.banten.core;

import java.util.*;
import org.apache.commons.lang3.Validate;

import org.slf4j.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.beans.factory.config.*;
import org.springframework.beans.factory.support.*;

import org.springframework.boot.*;

import org.springframework.context.*;

/** Bootstrap the Banten's Application.
 *
 * The application creates a {@link SpringApplication} using the {@link Module}
 * as {@link Configuration} as entry points.
 *
 * Each {@link Module} defines public beans within the
 * {@link Module#getModuleConfiguration}, those one will be merge into the
 * main application context. However, configuration declared within the
 * {@link Module#getMvcConfiguration()} will be isolated; The main or
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
 * @author waabox (waabox[at]gmail[dot]com)
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

  /** Creates a new Application with the given modules.
   * @param modules the list of modules to bootstrap, cannot be null.
   */
  @SafeVarargs
  protected BantenApplication(final Class<? extends Module> ... modules) {
    Validate.notNull(modules, "The modules cannot be null");
    moduleClasses = Arrays.asList(modules);
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
      registerPublicConfiguration(aModule, registry);
      registerPrivateConfiguration(aModule, registry);
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
  private void registerPublicConfiguration(final Module module,
      final BeanDefinitionRegistry registry) {
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
   * @param module the module to register. It cannot be null.
   */
  @SuppressWarnings("resource")
  private void registerPrivateConfiguration(final Module module,
      final BeanDefinitionRegistry registry) {

    if (!(module instanceof WebModule)) {
      return;
    }

    WebModuleWebApplicationContext context;
    context = new WebModuleWebApplicationContext((WebModule) module);
    context.register(registry);
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
    BeanDefinition coreBean = new GenericBeanDefinition();
    coreBean.setBeanClassName(CoreBeansConfiguration.class.getName());
    registry.registerBeanDefinition("coreBeansConfiguration", coreBean);
  }
}
