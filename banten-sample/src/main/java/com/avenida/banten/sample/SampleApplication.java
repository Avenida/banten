package com.avenida.banten.sample;

import org.springframework.boot.context.embedded.jetty.*;
import org.springframework.context.annotation.Bean;

/** The Sample Application entry point.
 * @author waabox (emi[at]avenida[dot]com)
 */
public class SampleApplication {

  /** The executor.
   * @param args the command line arguments.
   */
  public static void main(final String[] args) {
    SampleApplicationFactory factory = new SampleApplicationFactory();
    factory.create(SampleApplication.class).run(args);
  }

  /** Retrieves the Jetty Factory. This factory can be used to configure the
   * Jetty Server.
   * @return the factory, never null.
   */
  @Bean public JettyEmbeddedServletContainerFactory jetty() {
    return new JettyEmbeddedServletContainerFactory("", 8080);
  }

}
