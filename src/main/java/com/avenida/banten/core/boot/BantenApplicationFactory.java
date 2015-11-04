package com.avenida.banten.core.boot;

import org.springframework.boot.SpringApplication;

/** TODO doc.me
 * @author waabox (emi[at]avenida[dot]com)
 */
public abstract class BantenApplicationFactory {

  public abstract Class[] modules();

  public abstract Class[] with();

  public SpringApplication create(final Class mainClass) {
    BantenApplication application = new BantenApplication(modules());
    application.with(with());
    return application.createApplication(mainClass);
  }

}
