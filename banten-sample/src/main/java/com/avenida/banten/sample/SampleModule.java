package com.avenida.banten.sample;

import com.avenida.banten.core.ConfigurationApi;
import com.avenida.banten.core.Module;
import com.avenida.banten.core.ModuleApiRegistry;
import com.avenida.banten.login.LoginConfigurationApi;

/** Configuration Module.
 *
 * @author waabox (waabox[at]gmail[dot]com)
 */
public class SampleModule implements Module {

  /** {@inheritDoc}.*/
  @Override
  public String getName() {
    return "Sample-Module-Confiration";
  }

  /** {@inheritDoc}.*/
  @Override
  public Class<?> getPublicConfiguration() {
    return null;
  }

  /** {@inheritDoc}.*/
  @Override
  public ConfigurationApi getConfigurationApi() {
    return null;
  }

  /** {@inheritDoc}.*/
  @Override
  public void init(final ModuleApiRegistry registry) {
    registry.get(LoginConfigurationApi.class)
      .successUrl("/users/users/list.html");
  }

}
