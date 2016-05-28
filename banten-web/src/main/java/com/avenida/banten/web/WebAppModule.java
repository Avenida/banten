package com.avenida.banten.web;

import com.avenida.banten.core.ConfigurationApi;
import com.avenida.banten.core.Module;
import com.avenida.banten.core.ModuleApiRegistry;

/** Web module configuration.
 *
 * @author waabox (waabox[at]gmail[dot]com)
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
    return WebAppConfiguration.class;
  }

  /** {@inheritDoc}.*/
  @Override
  public ConfigurationApi getConfigurationApi() {
    return new WebAppConfigurationApi();
  }

  /** {@inheritDoc}.*/
  @Override
  public void init(final ModuleApiRegistry registry) {
  }

}
