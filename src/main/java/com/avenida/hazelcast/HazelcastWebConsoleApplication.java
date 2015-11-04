package com.avenida.hazelcast;

import org.springframework.boot.context.embedded.jetty.*;
import org.springframework.context.annotation.Bean;

import com.avenida.banten.core.boot.BantenApplication;
import com.avenida.banten.core.database.HibernateConfiguration;
import com.avenida.hazelcast.config.HazelcastWebConsoleModule;

/** The application runner.
 * @author waabox (emi[at]avenida[dot]com)
 */
public class HazelcastWebConsoleApplication {

  /** The application entry-point.
   * @param args the command line arguments.
   */
  public static void main(final String[] args) {
    BantenApplication app;
    app = new BantenApplication(HazelcastWebConsoleModule.class);
    app.with(HibernateConfiguration.class);
    app.run(HazelcastWebConsoleApplication.class, args);
  }

  /** Retrieves the Jetty Factory. This factory can be used to configure the
   * Jetty Server.
   * @return the factory, never null.
   */
  @Bean public JettyEmbeddedServletContainerFactory jetty() {
    return new JettyEmbeddedServletContainerFactory("", 8080);
  }

}
