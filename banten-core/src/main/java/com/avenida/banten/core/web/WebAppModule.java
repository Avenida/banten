package com.avenida.banten.core.web;

import com.avenida.banten.core.ConfigurationApi;
import com.avenida.banten.core.Module;
import com.avenida.banten.core.ModuleApiRegistry;

/** Web module configuration.
 *
 * @author waabox (emi[at]avenida[dot]com)
 */
public class WebAppModule implements Module {

  /** {@inheritDoc}.*/
  @Override
  public String getName() {
    return "banten-web-module";
  }

  /** {@inheritDoc}.*/
  @Override
  public Class<?> getPublicConfiguration() {
    return null;
  }

  /** {@inheritDoc}.*/
  @Override
  public ConfigurationApi getConfigurationApi() {
    return new WebConfigurationApi();
  }

  /** {@inheritDoc}.*/
  @Override
  public void init(final ModuleApiRegistry registry) {
  }

}
