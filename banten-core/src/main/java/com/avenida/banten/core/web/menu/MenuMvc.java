package com.avenida.banten.core.web.menu;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.avenida.banten.core.web.freemarker.FreeMarkerViewResolver;
import com.avenida.banten.core.web.menu.application.MenuController;

import freemarker.template.TemplateException;

/** Menu MVC configuration.
 * @author waabox (emi[at]avenida[dot]com)
 */
@Configuration
public class MenuMvc {

  /** Retrieves the {@link MenuController}.
   * @return the {@link MenuController}, never null.
   */
  @Bean
  public MenuController menuController() {
    return new MenuController();
  }

  @Bean public ViewResolver viewResolver() {
    return new FreeMarkerViewResolver();
  }

  @Bean public FreeMarkerConfigurer freemarkerConfig(
      @Value("${debugMode:false}") final boolean debugMode) throws IOException,
      TemplateException {
    return new com.avenida.banten.core.web.freemarker.FreeMarkerConfigurer(
        debugMode, "../banten-core",
        "classpath:com/avenida/banten/core/web/menu/templates");
  }

}
