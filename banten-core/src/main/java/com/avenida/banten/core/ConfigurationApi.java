package com.avenida.banten.core;

/** Provides hooks in order to allow {@link Module}s to extends
 * its configuration to another modules.
 *
 * @see Module#getConfigurationApi()
 *
 * @author waabox (waabox[at]gmail[dot]com)
 */
public abstract class ConfigurationApi {

  /** Initialize the configuration API.*/
  protected void init() {
  }

}

