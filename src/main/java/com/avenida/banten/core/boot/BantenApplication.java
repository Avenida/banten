package com.avenida.banten.core.boot;

import java.util.*;

import org.apache.commons.lang3.*;

import org.slf4j.*;

import org.springframework.beans.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.beans.factory.config.*;
import org.springframework.beans.factory.support.*;

import org.springframework.boot.*;
import org.springframework.boot.context.embedded.*;

import org.springframework.context.*;

import org.springframework.web.context.support.*;
import org.springframework.web.servlet.DispatcherServlet;

import com.avenida.banten.core.*;
import com.avenida.banten.core.beans.CoreBeansConfiguration;

/** Bootstrap the Banten's Application.
 *
 * @author waabox (emi[at]avenida[dot]com)
 */
public class BantenApplication {

  /** The log. */
  private final Logger log = LoggerFactory.getLogger(BantenApplication.class);

  /** The list of modules to bootstrap the application, it's never null.*/
  private final List<Class<? extends Module>> moduleClasses;

  /** The list of extra configuration classes, it's never null.*/
  private final List<Class<?>> configurationClasses = new LinkedList<>();

  /** Creates a new Application with the given modules.
   * @param modules the list of modules to bootstrap, cannot be null.
   */
  @SafeVarargs
  public BantenApplication(final Class<? extends Module> ... modules) {
    Validate.notNull(modules, "The modules cannot be null");
    moduleClasses = Arrays.asList(modules);
  }

  /** Add extra Spring configuration classes.
   * @param configurations the configuration classes.
   */
  public void with(final Class<?>... configurations) {
    configurationClasses.addAll(Arrays.asList(configurations));
  }

  /** Run the Spring application.
   * This action creates and refresh the new application context.
   *
   * @param mainClass the root class for the component scanning.
   * @param args the command line arguments.
   *
   * @return the new Spring's ApplicationContext.
   */
  public ConfigurableApplicationContext run(
      final Class<?> mainClass, final String[] args) {
    SpringApplication app = new SpringApplication(mainClass);

    app.addInitializers(
        new ApplicationContextInitializer<ConfigurableApplicationContext>() {

      /** Initializes the application context.
       * @param parentContext the parent application context.
       */
      @Override
      public void initialize(
          final ConfigurableApplicationContext parentContext) {

        parentContext.addBeanFactoryPostProcessor(
            new BeanDefinitionRegistryPostProcessor() {

          /** {@inheritDoc}.*/
          @Override
          public void postProcessBeanFactory(
              final ConfigurableListableBeanFactory beanFactory)
              throws BeansException {
          }

          /** {@inheritDoc}.*/
          @Override
          public void postProcessBeanDefinitionRegistry(
              final BeanDefinitionRegistry registry)
              throws BeansException {

            registerCoreModule(registry);
            registerConfigurationClasses(registry);
            registerModules(registry);

          }
        });
      }
    });

    log.info("Finish application configuration, starting Spring's context");

    return app.run(args);

  }

  /** Register the modules within the Spring's root application context.
   * @param registry the registry.
   * @throws Error
   */
  private void registerModules(final BeanDefinitionRegistry registry)
      throws Error {

    log.info("Registering Avenida Modules");

    for(Class<? extends Module> moduleClass : moduleClasses) {

      Module module;
      try {
        module = moduleClass.newInstance();
      } catch (Exception e) {
        log.error("Cannot instantiate the module: {}", moduleClass);
        log.error("{} should have an empty constructor", moduleClass);
        throw new Error(e);
      }

      log.info("Registering: {}" , module.getName());

      // Register the Module.
      BeanDefinition moduleDef = new AnnotatedGenericBeanDefinition(
          module.getModuleConfiguration());
      registry.registerBeanDefinition(module.getName(), moduleDef);

      // Register the MVC if any.
      if (module.getMvcConfiguration() != null) {

        String name = "mvc-" + module.getName();

        log.info("Registering mvc for: {}" , module.getName());

        DispatcherServlet dispatcherServlet = new DispatcherServlet();

        AnnotationConfigWebApplicationContext ctx;
        ctx = new AnnotationConfigWebApplicationContext();
        ctx.register(module.getMvcConfiguration());
        //ctx.setParent(parentContext); ??

        dispatcherServlet.setApplicationContext(ctx);

        ConstructorArgumentValues args;
        args = new ConstructorArgumentValues();
        args.addIndexedArgumentValue(0, dispatcherServlet);
        args.addIndexedArgumentValue(1,  module.getUrlMapping());

        MutablePropertyValues mpv = new MutablePropertyValues();
        mpv.add("name", name);

        GenericBeanDefinition mvcDef = new GenericBeanDefinition();
        mvcDef.setBeanClass(ServletRegistrationBean.class);
        mvcDef.setConstructorArgumentValues(args);
        mvcDef.setPropertyValues(mpv);

        registry.registerBeanDefinition(name , mvcDef);
      }
    }
  }

  /** Register the core module.
   *
   * @param registry the bean definition registry.
   */
  private void registerCoreModule(final BeanDefinitionRegistry registry) {
    log.info("Registering core beans");
    BeanDefinition coreBean = new GenericBeanDefinition();
    coreBean.setBeanClassName(CoreBeansConfiguration.class.getName());
    registry.registerBeanDefinition("coreBeansConfiguration", coreBean);
  }

  /** Register the configuration classes.
   * @param registry the bean definition registry.
   */
  private void registerConfigurationClasses(
      final BeanDefinitionRegistry registry) {
    for (Class<?> klass : configurationClasses) {
      log.info("Registering: {}" , klass.getName());
      BeanDefinition moduleDef = new AnnotatedGenericBeanDefinition(klass);
      registry.registerBeanDefinition(klass.getName(), moduleDef);
    }
  }

}
