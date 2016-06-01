package com.avenida.banten.login;

import com.avenida.banten.core.ConfigurationApi;
import com.avenida.banten.core.ConfigurationApiRegistry;
import com.avenida.banten.core.PersistenceUnit;
import com.avenida.banten.core.WebModule;

import com.avenida.banten.hibernate.HibernateConfigurationApi;

import com.avenida.banten.login.domain.Role;
import com.avenida.banten.login.domain.User;
import com.avenida.banten.login.shiro.BantenLoginRealm;

import com.avenida.banten.shiro.ShiroConfigurationApi;

/** Login Module description.
 *
 * @author waabox (waabox[at]gmail[dot]com)
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
    return new LoginConfigurationApi();
  }

  /** {@inheritDoc}.*/
  @Override
  public void init(final ConfigurationApiRegistry registry) {

    registry.get(ShiroConfigurationApi.class)

      .configureViews(
          "/login/web/form.html",
          LoginConfigurationApi.getSuccessUrl(),
          "/login/web/unauthorized.html")

      .registerRealm(BantenLoginRealm.class);

    registry.get(HibernateConfigurationApi.class)
      .persistenceUnits(
          new PersistenceUnit(User.class),
          new PersistenceUnit(Role.class)
       );
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
  public Class<?> getMvcConfiguration() {
    return LoginMvc.class;
  }

}
