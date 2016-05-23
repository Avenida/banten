package com.avenida.banten.camel;

import com.avenida.banten.core.*;

/** Camel Module.
 *
 * @author waabox (waabox[at]gmail[dot]com)
 */
public class CamelModule implements Module {

  /** {@inheritDoc}.*/
  @Override
  public String getName() {
    return "Camel-Module";
  }

  /** {@inheritDoc}.*/
  @Override
  public Class<?> getPublicConfiguration() {
    return CamelConfiguration.class;
  }

  /** {@inheritDoc}.*/
  @Override
  public ConfigurationApi getConfigurationApi() {
    return null;
  }

  /** {@inheritDoc}.*/
  @Override
  public void init(final ModuleApiRegistry registry) {
  }

}
