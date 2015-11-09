package com.avenida.banten.core.boot;

import org.springframework.boot.SpringApplication;

import com.avenida.banten.core.Module;

/** Factory for Banten Applications.
 *
 * @author waabox (emi[at]avenida[dot]com)
 */
public abstract class BantenApplicationFactory {

  /** Retrieves the list of modules.
   * @return the list of modules, never null.
   */
  public abstract Class<? extends Module>[] modules();

  /** Creates the Spring application.
   * @param mainClass the main class for lookup beans.
   * @return the Spring Application, never null.
   */
  @SuppressWarnings("rawtypes")
  public SpringApplication create(final Class mainClass) {
    BantenApplication application = new BantenApplication(modules());
    return application.createApplication(mainClass);
  }

}
