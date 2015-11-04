package com.avenida.hazelcast;

import org.springframework.boot.context.embedded.jetty.*;
import org.springframework.context.annotation.Bean;

import com.avenida.banten.core.boot.BantenApplication;
import com.avenida.banten.core.boot.BantenApplicationFactory;
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
    BantenApplicationFactory f = new BantenApplicationFactory() {
      @Override
      public Class[] with() {
        return new Class[] {HibernateConfiguration.class};
      }
      @Override
      public Class[] modules() {
        return new Class[] {HazelcastWebConsoleModule.class};
      }
    };
    f.create(HazelcastWebConsoleApplication.class).run(args);
  }

  /** Retrieves the Jetty Factory. This factory can be used to configure the
   * Jetty Server.
   * @return the factory, never null.
   */
  @Bean public JettyEmbeddedServletContainerFactory jetty() {
    return new JettyEmbeddedServletContainerFactory("", 8080);
  }

}
