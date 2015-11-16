package com.avenida.banten.sample.time;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.transaction.annotation.*;
import org.springframework.ui.freemarker.*;

import org.springframework.web.servlet.*;

import org.springframework.web.servlet.view.freemarker.*;

import com.avenida.banten.core.web.freemarker.FreeMarkerViewResolver;

import com.avenida.banten.sample.time.controllers.TimeController;
import com.avenida.banten.sample.time.domain.TimeRepository;

import freemarker.template.TemplateException;

/** The MVC configuration.
 *
 * @author waabox (emi[at]avenida[dot]com)
 */
@Configuration
@EnableTransactionManagement
public class TimeMVC {

  @Autowired
  @Bean public TimeController timeController(final TimeRepository repository) {
    return new TimeController(repository);
  }

  @Bean public ViewResolver viewResolver() {
    FreeMarkerViewResolver resolver = new FreeMarkerViewResolver();
    resolver.setCache(true);
    resolver.setPrefix("");
    resolver.setSuffix(".ftl");
    resolver.setContentType("text/html; charset=UTF-8");
    resolver.setExposeRequestAttributes(true);
    resolver.setExposeSpringMacroHelpers(true);
    return resolver;
  }

  @Bean public FreeMarkerConfigurer freemarkerConfig() throws IOException,
      TemplateException {
    FreeMarkerConfigurationFactory factory;
    factory = new FreeMarkerConfigurationFactory();

    factory.setTemplateLoaderPaths(
        "classpath:com/avenida/banten/sample/time/templates");

    factory.setDefaultEncoding("UTF-8");
    FreeMarkerConfigurer result = new FreeMarkerConfigurer();
    result.setConfiguration(factory.createConfiguration());
    return result;
  }

}
