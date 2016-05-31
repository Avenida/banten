package com.avenida.banten.login;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.ViewResolver;

import com.avenida.banten.web.freemarker.FreeMarkerConfigurer;
import com.avenida.banten.web.freemarker.FreeMarkerViewResolver;

import com.avenida.banten.login.application.LoginController;

import com.avenida.banten.login.domain.CreateRolesFromContextTask;
import com.avenida.banten.login.domain.UserRepository;

/** Login Module MVC configuration.
 *
 * @author waabox (waabox[at]gmail[dot]com)
 */
@Configuration
@EnableTransactionManagement
public class LoginMvc {

  /** The login form controller.
   * @return the {@link LoginController} never null.
   */
  @Bean public LoginController loginController() {
    return new LoginController();
  }

  @Bean public CreateRolesFromContextTask uploadRolesTask(
      final UserRepository repository) {
    return new CreateRolesFromContextTask(repository);
  }

  @Bean public ViewResolver viewResolver() {
    return new FreeMarkerViewResolver();
  }

  @Bean public FreeMarkerConfigurer freemarkerConfig(
    @Value("${debugMode:false}") final boolean debugMode) {
    return new FreeMarkerConfigurer(debugMode, "../banten-login",
        "classpath:com/avenida/banten/login/templates");
  }

}
