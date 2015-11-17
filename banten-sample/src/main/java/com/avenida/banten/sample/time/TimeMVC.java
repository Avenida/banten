package com.avenida.banten.sample.time;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.transaction.annotation.*;

import org.springframework.web.servlet.*;

import org.springframework.web.servlet.view.freemarker.*;

import com.avenida.banten.core.web.freemarker.FreemarkerFactory;

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
    return FreemarkerFactory.viewResolver();
  }

  @Bean public FreeMarkerConfigurer freemarkerConfig() throws IOException,
      TemplateException {
    return FreemarkerFactory.freemarkerConfigurer(
        "classpath:com/avenida/banten/sample/time/templates");
  }

}
