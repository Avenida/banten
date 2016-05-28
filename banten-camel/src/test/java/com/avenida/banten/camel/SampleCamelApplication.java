package com.avenida.banten.camel;

import org.apache.camel.RoutesBuilder;
import org.apache.camel.builder.RouteBuilder;

import org.springframework.context.annotation.Bean;

import com.avenida.banten.core.BantenApplication;
import com.avenida.banten.core.ModuleApiRegistry;

/** Just a sample application for camel.
 * @author waabox (waabox[at]gmail[dot]com)
 */
public class SampleCamelApplication extends BantenApplication {

  public SampleCamelApplication() {
    super(
        CamelModule.class
    );
  }

  @Bean public RoutesBuilder builder() {
    return new RouteBuilder() {
      @Override
      public void configure() throws Exception {
        from("direct:hello").bean("helloEndpoint", "hello(${body})");
      }
    };
  }

  @Bean(name = "helloEndpoint") public HelloBean hello() {
    return new HelloBean();
  }

  public class HelloBean {

    public String hello(final String tellMe) {
      return tellMe;
    }

  }

  @Override
  public void init(final ModuleApiRegistry registry) {
  }

}
