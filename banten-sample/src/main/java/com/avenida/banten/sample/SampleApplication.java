package com.avenida.banten.sample;

import org.springframework.boot.context.embedded.jetty
  .JettyEmbeddedServletContainerFactory;

import org.springframework.context.annotation.Bean;

import com.avenida.banten.core.BantenApplication;

import com.avenida.banten.hibernate.HibernateModule;

import com.avenida.banten.login.LoginModule;

import com.avenida.banten.sample.time.TimeModule;
import com.avenida.banten.sample.user.UserModule;

import com.avenida.banten.shiro.ShiroModule;

import com.avenida.banten.web.WebAppModule;
import com.avenida.banten.web.menu.MenuModule;

import com.avenida.banten.web.sitemesh.SitemeshConfiguration;
import com.avenida.banten.web.sitemesh.SitemeshModule;

/** The Sample application Factory.
 *
 * @author waabox (waabox[at]gmail[dot]com)
 */
public class SampleApplication extends BantenApplication {

  /** The application's port.*/
  private static final int APPLICATION_PORT = 8080;

  /** Creates a new instance of the Sample Application.*/
  public SampleApplication() {
    super(
        ApplicationConfigurationModule.class,
        HibernateModule.class,
        WebAppModule.class,
        SitemeshModule.class,
        MenuModule.class,
        TimeModule.class,
        UserModule.class,
        LoginModule.class,
        ShiroModule.class
    );
  }

  /** Retrieves the Jetty Factory. This factory can be used to configure the
   * Jetty Server.
   * @return the factory, never null.
   */
  @Bean public JettyEmbeddedServletContainerFactory jetty() {
    return new JettyEmbeddedServletContainerFactory("", APPLICATION_PORT);
  }

  /** The Sitemesh's decorator configuration.
   * @return the Sitemesh's decorator configuration.
   */
  @Bean public SitemeshConfiguration sitemeshConfig() {
    return new SitemeshConfiguration("../banten-sample",
        "classpath:decorators/");
  }

  /** Creates a test account for development.
   * @return a {@link SampleLoginAccount}
   */
  @Bean public SampleLoginAccount sampleLoginAccount() {
    return new SampleLoginAccount();
  }

}
