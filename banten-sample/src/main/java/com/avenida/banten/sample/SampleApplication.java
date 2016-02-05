package com.avenida.banten.sample;

import org.springframework.boot.context.embedded.jetty
  .JettyEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;

import com.avenida.banten.core.BantenApplication;
import com.avenida.banten.core.web.menu.MenuModule;
import com.avenida.banten.core.web.sitemesh.SitemeshConfiguration;
import com.avenida.banten.core.web.sitemesh.SitemeshModule;
import com.avenida.banten.hibernate.HibernateModule;
import com.avenida.banten.sample.time.TimeModule;
import com.avenida.banten.sample.user.UserModule;

/** The Sample application Factory.
 *
 * @author waabox (emi[at]avenida[dot]com)
 */
public class SampleApplication extends BantenApplication {

  /** Creates a new instance of the Sample Application.*/
  public SampleApplication() {
    super(
        HibernateModule.class,
        SitemeshModule.class,
        MenuModule.class,
        TimeModule.class,
        UserModule.class
    );
  }

  /** Retrieves the Jetty Factory. This factory can be used to configure the
   * Jetty Server.
   * @return the factory, never null.
   */
  @Bean public JettyEmbeddedServletContainerFactory jetty() {
    return new JettyEmbeddedServletContainerFactory("", 8080);
  }

  /** The Sitemesh's decorator configuration.
   * @return the Sitemesh's decorator configuration.
   */
  @Bean public SitemeshConfiguration sitemeshConfig() {
    return new SitemeshConfiguration("xx", "classpath:decorators/");
  }

}

