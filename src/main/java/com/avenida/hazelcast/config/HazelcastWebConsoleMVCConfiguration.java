package com.avenida.hazelcast.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactory;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import com.avenida.hazelcast.controllers.HazelcastWebConsoleController;
import com.hazelcast.core.HazelcastInstance;

import freemarker.template.TemplateException;

/** The MVC configuation for Hazelcast.
 *
 * @author waabox (emi[at]avenida[dot]com)
 */
@Configuration
public class HazelcastWebConsoleMVCConfiguration
  extends WebMvcConfigurerAdapter {

  @Autowired
  @Bean public HazelcastWebConsoleController hzWebConsoleController(
      final HazelcastInstance hz) {
    return new HazelcastWebConsoleController(hz);
  }

  @Bean
  public ViewResolver viewResolver() {
    FreeMarkerViewResolver resolver = new FreeMarkerViewResolver();
    resolver.setCache(true);
    resolver.setPrefix("");
    resolver.setSuffix(".ftl");
    resolver.setContentType("text/html; charset=UTF-8");
    return resolver;
  }

  @Bean
  public FreeMarkerConfigurer freemarkerConfig() throws IOException,
      TemplateException {
    FreeMarkerConfigurationFactory factory;
    factory = new FreeMarkerConfigurationFactory();
    factory.setTemplateLoaderPaths("classpath:templates",
        "src/main/resource/templates");
    factory.setDefaultEncoding("UTF-8");
    FreeMarkerConfigurer result = new FreeMarkerConfigurer();
    result.setConfiguration(factory.createConfiguration());
    return result;
  }
}
