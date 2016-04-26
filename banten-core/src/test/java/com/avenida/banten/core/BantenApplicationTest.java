package com.avenida.banten.core;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Test;
import org.springframework.boot.context.embedded.jetty
  .JettyEmbeddedServletContainerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class BantenApplicationTest {

  public static String configuredString = null;

  @Test public void application() {

    ApplicationContext ctx;
    ctx = new SampleApplication().run(new String[0]);

    ctx.containsBeanDefinition("modulePublicStringBean");

    assertThat(configuredString, is("from module B"));

    // Checks public beans.
    String beanFromA = (String) ctx.getBean("modulePublicStringBean");
    String beanFromB = (String) ctx.getBean("moduleBPublicStringBean");

    assertThat(beanFromA, is("a value"));
    assertThat(beanFromB, is("a value from B"));

    // Checks private beans.
    assertThat(ctx.containsBean("modulePrivateStringBean"), is(false));
  }

  public static class SampleApplication extends BantenApplication {

    public SampleApplication() {
      super(SampleModule.class, SampleModuleB.class);
    }

    @Bean public JettyEmbeddedServletContainerFactory jetty() {
      return new JettyEmbeddedServletContainerFactory("", 8080);
    }

  }

  public static class SampleModule implements Module {

    @Override
    public String getName() {
      return "sample-module";
    }

    @Override
    public String getNamespace() {
      return "sample-module";
    }

    @Override
    public String getRelativePath() {
      return "../banten-core";
    }

    @Override
    public Class<?> getPrivateConfiguration() {
      return ModulePrivateConfiguration.class;
    }

    @Override
    public Class<?> getPublicConfiguration() {
      return ModulePublicConfiguration.class;
    }

    @Override
    public ConfigurationApi getConfigurationApi() {
      return new SampleModuleConfigurationApi();
    }

    @Override
    public void init(final ModuleApiRegistry registry) {
    }
  }

  public static class SampleModuleConfigurationApi extends ConfigurationApi {
    public void configureGlobalString(final String value) {
      BantenApplicationTest.configuredString = value;
    }
  }

  @Configuration
  public static class ModulePublicConfiguration {
    @Bean(name = "modulePublicStringBean")
    public String modulePublicStringBean() {
      return "a value";
    }
  }

  @Configuration public static class ModulePrivateConfiguration {

    @Bean(name = "modulePrivateStringBean")
    public String modulePublicStringBean() {
      return "a private value";
    }

  }

  public static class SampleModuleB implements Module {

    @Override
    public String getName() {
      return "sample-module-b";
    }

    @Override
    public String getNamespace() {
      return "sample-module-b";
    }

    @Override
    public String getRelativePath() {
      return "../banten-core";
    }

    @Override
    public Class<?> getPrivateConfiguration() {
      return null;
    }

    @Override
    public Class<?> getPublicConfiguration() {
      return ModuleBPublicConfiguration.class;
    }

    @Override
    public ConfigurationApi getConfigurationApi() {
      return null;
    }

    @Override
    public void init(final ModuleApiRegistry registry) {
      registry.get(SampleModuleConfigurationApi.class)
        .configureGlobalString("from module B");
    }
  }

  @Configuration
  public static class ModuleBPublicConfiguration {
    @Bean(name = "moduleBPublicStringBean")
    public String moduleBPublicStringBean() {
      return "a value from B";
    }
  }

}
