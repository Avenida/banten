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
import org.springframework.context.annotation.Configuration;

import org.springframework.web.context.support.*;
import org.springframework.web.servlet.DispatcherServlet;

import com.avenida.banten.core.*;
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
 * {@link Module#getMvcConfiguration()} will be isolated; The main or
 * parent {@link ApplicationContext} is the same where {@link Module}s has
 * been declared.
 *
 * The {@link ApplicationContext} looks like:
 *
 *               <Parent Context>
 *               |              |
 *          [Module A]     [Module B] ...
 *              |              |
 *         [MVC Context] [MVC Context]
 *
 *
 * @author waabox (emi[at]avenida[dot]com)
 */
public class BantenApplication {

  /** The log. */
  private final Logger log = LoggerFactory.getLogger(BantenApplication.class);

  /** The list of modules to bootstrap the application, it's never null.*/
  private final List<Class<? extends Module>> moduleClasses;

  /** Creates a new Application with the given modules.
   * @param modules the list of modules to bootstrap, cannot be null.
   */
  @SafeVarargs
  BantenApplication(final Class<? extends Module> ... modules) {
    Validate.notNull(modules, "The modules cannot be null");
    moduleClasses = Arrays.asList(modules);
  }

  /** Creates the Spring's Application.
   * @param mainClass the main class.
   * @return the Spring Application, never null.
   */
  public SpringApplication createApplication(final Class<?> mainClass) {
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
            registerModules(registry);

          }
        });
      }
    });

    log.info("Finish application configuration, starting Spring's context");

    return app;
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
    return createApplication(mainClass).run(args);
  }

  /** Register the modules within the Spring's root application context.
   * @param registry the registry.
   * @throws Error
   */
  private void registerModules(final BeanDefinitionRegistry registry)
      throws Error {
    log.info("Registering Banten Modules");

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

      registerModule(registry, module);
      registerMVC(registry, module);
      registerPersistenceUnits(registry, module);
      registerWeblets(module);

    }

    log.info("Finish the registration of the Banten modules");
  }

  /** Register the Module.
   * @param registry the bean definition registry.
   * @param module the module.
   */
  private void registerModule(final BeanDefinitionRegistry registry,
      final Module module) {
    BeanDefinition moduleDef = new AnnotatedGenericBeanDefinition(
        module.getModuleConfiguration());
    moduleDef.setLazyInit(true);
    registry.registerBeanDefinition(module.getName(), moduleDef);
  }

  /** Register the MVC.
   * @param registry the bean definition registry.
   * @param module the module.
   */
  private void registerMVC(final BeanDefinitionRegistry registry,
      final Module module) {
    if (module.getMvcConfiguration() != null) {
      String name = "mvc-" + module.getName();

      log.info("Registering MVC for: {}", module.getName());

      DispatcherServlet dispatcherServlet = new DispatcherServlet();

      AnnotationConfigWebApplicationContext ctx;
      ctx = new AnnotationConfigWebApplicationContext();
      ctx.register(module.getMvcConfiguration());
      // ctx.setParent(parentContext); ??

      dispatcherServlet.setApplicationContext(ctx);

      ConstructorArgumentValues args;
      args = new ConstructorArgumentValues();
      args.addIndexedArgumentValue(0, dispatcherServlet);
      args.addIndexedArgumentValue(1, "/" + module.getNamespace() + "/*");

      MutablePropertyValues mpv = new MutablePropertyValues();
      mpv.add("name", name);

      GenericBeanDefinition mvcDef = new GenericBeanDefinition();
      mvcDef.setBeanClass(ServletRegistrationBean.class);
      mvcDef.setConstructorArgumentValues(args);
      mvcDef.setPropertyValues(mpv);
      mvcDef.setLazyInit(true);

      registry.registerBeanDefinition(name, mvcDef);
    }
  }

  /** Register the persistence units.
   * @param registry the bean definition registry.
   * @param module the module.
   */
  private void registerPersistenceUnits(final BeanDefinitionRegistry registry,
      final Module module) {

    if(module.getPersistenceUnits() != null) {
      String name = "persistence-units-" + module.getName();

      ConstructorArgumentValues args;
      args = new ConstructorArgumentValues();
      args.addIndexedArgumentValue(0, "persistenceUnitList");
      args.addIndexedArgumentValue(1, module.getPersistenceUnits());

      GenericBeanDefinition bean = new GenericBeanDefinition();
      bean.setBeanClass(ListFactoryAppender.class);
      bean.setConstructorArgumentValues(args);

      registry.registerBeanDefinition(name, bean);
    }
  }

  /** Register the weblets into the WebletContainer.
   *
   * @param module the module.
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
    BeanDefinition coreBean = new GenericBeanDefinition();
    coreBean.setBeanClassName(CoreBeansConfiguration.class.getName());
    registry.registerBeanDefinition("coreBeansConfiguration", coreBean);
  }

}
