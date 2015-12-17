package com.avenida.banten.sample;

import org.springframework.boot.context.embedded.jetty.JettyEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;

import com.avenida.banten.core.BantenApplication;
import com.avenida.banten.hibernate.HibernateModule;
import com.avenida.banten.core.web.WebModule;
import com.avenida.banten.core.web.sitemesh.SitemeshDecoratorConfiguration;
import com.avenida.banten.core.web.sitemesh.SitemeshModule;
import com.avenida.banten.core.web.sitemesh.SitemeshDecoratorConfiguration.Builder;

import com.avenida.banten.sample.time.TimeModule;
import com.avenida.banten.sample.user.UserModule;

/** The Sample application Factory.
 *
 * @author waabox (emi[at]avenida[dot]com)
 */
public class SampleApplication extends BantenApplication {

  public SampleApplication() {
    super(
        HibernateModule.class,
        SitemeshModule.class,
        WebModule.class,
        TimeModule.class,
        UserModule.class);
  }

  /** Retrieves the Jetty Factory. This factory can be used to configure the
   * Jetty Server.
   * @return the factory, never null.
   */
  @Bean public JettyEmbeddedServletContainerFactory jetty() {
    return new JettyEmbeddedServletContainerFactory("", 8888);
  }

  /** The Sitemesh's decorator configuration.
   * @return the Sitemesh's decorator configuration.
   */
  @Bean public SitemeshDecoratorConfiguration sitemeshConfig() {
    return new Builder().build();
  }
}
