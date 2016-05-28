package com.avenida.banten.sample;

import com.avenida.banten.core.ConfigurationApi;
import com.avenida.banten.core.Module;
import com.avenida.banten.core.ModuleApiRegistry;
import com.avenida.banten.login.LoginConfigurationApi;
import com.avenida.banten.web.WebAppConfigurationApi;

/** Configuration Module.
 *
 * This is the one who configures the "application" as "application level".
 * We have, modules that interacts, however this one configures the
 * application itself, for example: landing URl, and in the future should
 * condifure: jetty, decorators, etc.
 *
 * TODO [waabox]
 * Also, we should use something called "ApplicationModule" instead of
 * Module.
 *
 * @author waabox (waabox[at]gmail[dot]com)
 */
public class ApplicationConfigurationModule implements Module {

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

    registry.get(WebAppConfigurationApi.class)
      .setLandingUrl("/users/users/list.html");
  }

}
