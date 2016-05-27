package com.avenida.banten.login;

import com.avenida.banten.core.ConfigurationApi;
import com.avenida.banten.core.ModuleApiRegistry;
import com.avenida.banten.core.PersistenceUnit;
import com.avenida.banten.core.WebModule;

import com.avenida.banten.hibernate.HibernateConfigurationApi;
import com.avenida.banten.login.domain.Permission;
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
    return null;
  }

  /** {@inheritDoc}.*/
  @Override
  public void init(final ModuleApiRegistry registry) {

    registry.get(ShiroConfigurationApi.class)
      .configureViews("/login/form", "/", "/login/unauthorized")
      .registerRealm(BantenLoginRealm.class);

    registry.get(HibernateConfigurationApi.class)
      .persistenceUnits(
          new PersistenceUnit(User.class),
          new PersistenceUnit(Permission.class)
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
