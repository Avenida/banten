package com.avenida.banten.sample.time;

import com.avenida.banten.core.*;
import com.avenida.banten.hibernate.HibernateConfigurationApi;
import com.avenida.banten.sample.time.domain.Time;
import com.avenida.banten.shiro.ShiroConfigurationApi;
import com.avenida.banten.shiro.UrlToRoleMapping;
import com.avenida.banten.web.menu.MenuConfigurationApi;

/** A simple time module.
 * @author waabox (waabox[at]gmail[dot]com)
 */
public class TimeModule implements WebModule {

  /** {@inheritDoc}.*/
  @Override
  public String getName() {
    return "Time-Module";
  }

  /** {@inheritDoc}.*/
  @Override
  public String getNamespace() {
    return "time";
  }

  /** {@inheritDoc}.*/
  @Override
  public String getRelativePath() {
    return "../banten-sample";
  }

  /** {@inheritDoc}.*/
  @Override
  public Class<?> getMvcConfiguration() {
    return TimeMvc.class;
  }

  /** {@inheritDoc}.*/
  @Override
  public Class<?> getPublicConfiguration() {
    return TimeConfiguration.class;
  }

  /** {@inheritDoc}.*/
  @Override
  public ConfigurationApi getConfigurationApi() {
    return null;
  }

  /** {@inheritDoc}.*/
  @Override
  public void init(final ConfigurationApiRegistry registry) {

    registry.get(HibernateConfigurationApi.class)
      .persistenceUnits(
          new PersistenceUnit(Time.class)
      );

    registry.get(MenuConfigurationApi.class)
      .root("Time", "/time")
      .node("List", "/time/time/view.html", "/time");

    registry.get(ShiroConfigurationApi.class)
      .register(new UrlToRoleMapping("/time/time/view.html", "time"));

  }

}
