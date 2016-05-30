package com.avenida.banten.sample;

import org.springframework.boot.context.embedded.jetty
  .JettyEmbeddedServletContainerFactory;

import org.springframework.context.annotation.Bean;

import com.avenida.banten.core.BantenApplication;
import com.avenida.banten.core.ConfigurationApiRegistry;
import com.avenida.banten.core.Bootstrap;

import com.avenida.banten.hibernate.HibernateModule;

import com.avenida.banten.login.LoginConfigurationApi;
import com.avenida.banten.login.LoginModule;

import com.avenida.banten.sample.time.TimeModule;
import com.avenida.banten.sample.user.UserModule;

import com.avenida.banten.shiro.ShiroModule;

import com.avenida.banten.web.WebAppConfigurationApi;
import com.avenida.banten.web.WebAppModule;
import com.avenida.banten.web.menu.MenuModule;

import com.avenida.banten.web.sitemesh.SitemeshConfigurationApi;
import com.avenida.banten.web.sitemesh.SitemeshModule;

/** The Sample application Factory.
 *
 * @author waabox (waabox[at]gmail[dot]com)
 */
public class SampleApplication extends BantenApplication {

  /** The application's port.*/
  private static final int APPLICATION_PORT = 8080;

  @Override
  protected Bootstrap bootstrap() {
    return new Bootstrap(
        HibernateModule.class,
        WebAppModule.class,
        SitemeshModule.class,
        MenuModule.class,
        LoginModule.class,
        ShiroModule.class,
        // Domain Bootstrap.
        TimeModule.class,
        UserModule.class
    );
  }

  /** Retrieves the Jetty Factory. This factory can be used to configure the
   * Jetty Server.
   * @return the factory, never null.
   */
  @Bean public JettyEmbeddedServletContainerFactory jetty() {
    return new JettyEmbeddedServletContainerFactory("", APPLICATION_PORT);
  }

  /** Creates a test account for development.
   * @return a {@link SampleLoginAccount}
   */
  @Bean public SampleLoginAccount sampleLoginAccount() {
    return new SampleLoginAccount();
  }

  /** {@inheritDoc}.*/
  @Override
  public void init(final ConfigurationApiRegistry registry) {
    registry.get(LoginConfigurationApi.class)
      .successUrl("/users/users/list.html");

    registry.get(WebAppConfigurationApi.class)
      .setLandingUrl("/users/users/list.html");

    registry.get(SitemeshConfigurationApi.class)
      .configure("../banten-sample", "classpath:decorators/");
  }

}
