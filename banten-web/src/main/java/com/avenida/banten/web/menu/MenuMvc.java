package com.avenida.banten.web.menu;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.avenida.banten.web.freemarker.FreeMarkerViewResolver;
import com.avenida.banten.web.menu.application.MenuController;

import freemarker.template.TemplateException;

/** Menu MVC configuration.
 * @author waabox (waabox[at]gmail[dot]com)
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
    return new com.avenida.banten.web.freemarker.FreeMarkerConfigurer(
        debugMode, "../banten-web",
        "classpath:com/avenida/banten/web/menu/templates");
  }

}
