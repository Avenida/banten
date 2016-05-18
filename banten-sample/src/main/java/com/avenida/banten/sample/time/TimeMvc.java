package com.avenida.banten.sample.time;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.transaction.annotation.*;

import org.springframework.web.servlet.*;

import com.avenida.banten.core.web.freemarker.FreeMarkerConfigurer;
import com.avenida.banten.core.web.freemarker.FreeMarkerViewResolver;

import com.avenida.banten.sample.time.controllers.TimeController;
import com.avenida.banten.sample.time.domain.TimeRepository;

/** The MVC configuration.
 *
 * @author waabox (emi[at]avenida[dot]com)
 */
@Configuration
@EnableTransactionManagement
public class TimeMvc {

  @Bean public TimeController timeController(final TimeRepository repository) {
    return new TimeController(repository);
  }

  @Bean public ViewResolver viewResolver() {
    return new FreeMarkerViewResolver();
  }

  @Bean public FreeMarkerConfigurer freemarkerConfig(
    @Value("${debugMode:false}") final boolean debugMode) {
    return new FreeMarkerConfigurer(debugMode, "../banten-sample",
        "classpath:com/avenida/banten/sample/time/templates");
  }
}
