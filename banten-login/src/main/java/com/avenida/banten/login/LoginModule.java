package com.avenida.banten.login;

import com.avenida.banten.core.ConfigurationApi;
import com.avenida.banten.core.ModuleApiRegistry;
import com.avenida.banten.core.WebModule;

/** Login Module description.
 *
 * @author waabox (emi[at]avenida[dot]com)
 */
public class LoginModule implements WebModule {

  /** {@inheritDoc}.*/
  @Override
  public String getName() {
    return "Login-Module";
  }

  /** {@inheritDoc}.*/
  @Override
  public Class<?> getPublicConfiguration() {
    return LoginConfiguration.class;
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

  /** {@inheritDoc}.*/
  @Override
  public String getNamespace() {
    return "login";
  }

  /** {@inheritDoc}.*/
  @Override
  public String getRelativePath() {
    return "../banten-login";
  }

  /** {@inheritDoc}.*/
  @Override
  public Class<?> getPrivateConfiguration() {
    return LoginMvc.class;
  }

}
