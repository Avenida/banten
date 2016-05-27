package com.avenida.banten.sample.user;

import java.util.Arrays;

import com.avenida.banten.core.*;

import com.avenida.banten.hibernate.HibernateConfigurationApi;

import com.avenida.banten.sample.user.domain.User;
import com.avenida.banten.sample.user.domain.UserFactory;

import com.avenida.banten.shiro.ShiroConfigurationApi;
import com.avenida.banten.shiro.UrlToRoleMapping;

import com.avenida.banten.web.WebConfigurationApi;
import com.avenida.banten.web.Weblet;
import com.avenida.banten.web.menu.MenuConfigurationApi;

/** The user module.
 * @author waabox (waabox[at]gmail[dot]com)
 */
public class UserModule implements WebModule {

  /** {@inheritDoc}.*/
  @Override
  public String getName() {
    return "User-Module";
  }

  /** {@inheritDoc}.*/
  @Override
  public String getNamespace() {
    return "users";
  }

  /** {@inheritDoc}.*/
  @Override
  public String getRelativePath() {
    return "../banten-sample";
  }

  /** {@inheritDoc}.*/
  @Override
  public Class<?> getPrivateConfiguration() {
    return UserMvc.class;
  }

  /** {@inheritDoc}.*/
  @Override
  public Class<?> getPublicConfiguration() {
    return UserConfiguration.class;
  }

  /** {@inheritDoc}.*/
  @Override
  public ConfigurationApi getConfigurationApi() {
    return null;
  }

  /** {@inheritDoc}.*/
  @Override
  public void init(final ModuleApiRegistry registry) {

    registry.get(HibernateConfigurationApi.class)
      .persistenceUnits(
          new PersistenceUnit(User.class, UserFactory.class)
     );

    registry.get(WebConfigurationApi.class)
      .addWeblets(
          Arrays.asList(
              new Weblet("samplepicture", "users/samplePicture.html")
          ), this);

    registry.get(MenuConfigurationApi.class)
      .root("Users", "/users")
      .node("List", "/users/users/list.html", "/users");

    registry.get(ShiroConfigurationApi.class)
      .register(new UrlToRoleMapping("/users/users/list.html", "admin"));

  }

}
