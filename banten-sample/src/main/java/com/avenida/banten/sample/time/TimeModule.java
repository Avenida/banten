package com.avenida.banten.sample.time;

import java.util.Arrays;
import java.util.List;

import com.avenida.banten.core.*;
import com.avenida.banten.hibernate.HibernateConfigurationApi;
import com.avenida.banten.sample.time.domain.Time;

/** A simple time module.
 * @author waabox (emi[at]avenida[dot]com)
 */
public class TimeModule implements Module {

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
  public Class<?> getPrivateConfiguration() {
    return TimeMVC.class;
  }

  /** {@inheritDoc}.*/
  @Override
  public Class<?> getPublicConfiguration() {
    return TimeConfiguration.class;
  }

  /** {@inheritDoc}.*/
  @Override
  public List<Weblet> getWeblets() {
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
    registry.get(HibernateConfigurationApi.class)
      .persistenceUnits(
          Arrays.asList(
              new PersistenceUnit(Time.class)
          )
      );
  }

}
