package com.avenida.banten.sample.user;

import java.util.Arrays;

import com.avenida.banten.core.*;
import com.avenida.banten.core.web.WebConfigurationApi;
import com.avenida.banten.core.web.Weblet;
import com.avenida.banten.core.web.menu.MenuConfigurationApi;
import com.avenida.banten.hibernate.HibernateConfigurationApi;
import com.avenida.banten.sample.user.domain.User;
import com.avenida.banten.sample.user.domain.UserFactory;

/** The user module.
 * @author waabox (emi[at]avenida[dot]com)
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
    return UserMVC.class;
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
        Arrays.asList(
            new PersistenceUnit(User.class, UserFactory.class)
        )
     );

    registry.get(WebConfigurationApi.class)
      .addWeblets(
          Arrays.asList(
              new Weblet("samplepicture", "users/samplePicture.html")
          ), this);

    registry.get(MenuConfigurationApi.class)
      .root("Users", "/users")
      .node("List", "/users/users/list.html", "/users");

  }

}
