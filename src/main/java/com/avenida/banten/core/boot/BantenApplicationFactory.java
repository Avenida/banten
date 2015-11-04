package com.avenida.banten.core.boot;

import org.springframework.boot.SpringApplication;

/** Factory for Banten Applications.
 *
 * @author waabox (emi[at]avenida[dot]com)
 */
public abstract class BantenApplicationFactory {

  /** Retrieves the list of modules.
   * @return the list of modules, never null.
   */
  public abstract Class[] modules();

  /** Retrieves the infrastructure modules.
   * @return the list of modules, never null.
   */
  public abstract Class[] with();

  /** Creates the Spring application.
   * @param mainClass the main class for lookup beans.
   * @return the Spring Application, never null.
   */
  public SpringApplication create(final Class mainClass) {
    BantenApplication application = new BantenApplication(modules());
    application.with(with());
    return application.createApplication(mainClass);
  }

}
