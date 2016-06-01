package com.avenida.banten.web.menu;

import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.avenida.banten.web.freemarker.FreeMarkerViewResolver;
import com.avenida.banten.web.menu.application.MenuController;

/** Menu MVC configuration.
 * @author waabox (waabox[at]gmail[dot]com)
 */
@Configuration
public class MenuMvc {

  /** Retrieves the {@link MenuController}.
   * @return the {@link MenuController}, never null.
   */
  @Bean
  public MenuController menuController(
      final Menu menu,
      final MenuSecurityFilter filter) {
    return new MenuController(menu, filter);
  }

  @Bean public SecuredUrlService urlService() {
    return new SecuredUrlService();
  }

  @Bean public RoleVoter roleVoter() {
    return new RoleVoter();
  }

  @Bean public Menu menu() {
    return MenuConfigurationApi.get();
  }

  @Bean public MenuSecurityFilter filter(
      final SecuredUrlService urlService,
      final RoleVoter voter,
      @Value("${menu.filterBySecurity}") final boolean isSecured) {
    return new MenuSecurityFilter(voter, urlService, isSecured);
  }

  @Bean public ViewResolver viewResolver() {
    return new FreeMarkerViewResolver();
  }

  @Bean public FreeMarkerConfigurer freemarkerConfig(
      @Value("${debugMode:false}") final boolean debugMode) {
    return new com.avenida.banten.web.freemarker.FreeMarkerConfigurer(
        debugMode, "../banten-web",
        "classpath:com/avenida/banten/web/menu/templates");
  }

}
