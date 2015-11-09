package com.avenida.banten.sample.user;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.ui.freemarker.*;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.freemarker.*;

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
    FreeMarkerViewResolver resolver = new FreeMarkerViewResolver();
    resolver.setCache(true);
    resolver.setPrefix("");
    resolver.setSuffix(".ftl");
    resolver.setContentType("text/html; charset=UTF-8");
    return resolver;
  }

  @Bean public FreeMarkerConfigurer freemarkerConfig() throws IOException,
      TemplateException {
    FreeMarkerConfigurationFactory factory;
    factory = new FreeMarkerConfigurationFactory();

    factory.setTemplateLoaderPaths(
        "classpath:com/avenida/banten/sample/user/templates");

    factory.setDefaultEncoding("UTF-8");
    FreeMarkerConfigurer result = new FreeMarkerConfigurer();
    result.setConfiguration(factory.createConfiguration());
    return result;
  }

}
