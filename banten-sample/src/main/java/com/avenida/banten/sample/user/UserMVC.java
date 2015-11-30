package com.avenida.banten.sample.user;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.avenida.banten.core.web.freemarker.FreeMarkerViewResolver;
import com.avenida.banten.core.web.freemarker.FreemarkerFactory;
import com.avenida.banten.sample.user.controllers.UserController;
import com.avenida.banten.sample.user.domain.UserFactory;
import com.avenida.banten.sample.user.domain.UserRepository;

import freemarker.template.TemplateException;

/** The user MVC.
 * @author waabox (emi[at]avenida[dot]com)
 */
@Configuration
@EnableTransactionManagement
public class UserMVC {

  @Autowired
  @Bean
  public UserController userController(final UserRepository repository,
      final UserFactory userFactory) {
    return new UserController(repository, userFactory);
  }

  @Bean public ViewResolver viewResolver() {
    return new FreeMarkerViewResolver();
  }

  @Bean public FreeMarkerConfigurer freemarkerConfig() throws IOException,
      TemplateException {
    return FreemarkerFactory.freemarkerConfigurer(
        "classpath:com/avenida/banten/sample/user/templates");
  }

}
