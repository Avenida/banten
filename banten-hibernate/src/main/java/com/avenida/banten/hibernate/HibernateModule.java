package com.avenida.banten.hibernate;

import com.avenida.banten.core.*;

/** Hibernate's Module.
 * @author waabox (waabox[at]gmail[dot]com)
 */
public class HibernateModule implements Module {

  /** {@inheritDoc}.*/
  @Override
  public String getName() {
    return "Hibernate-Module";
  }

  /** {@inheritDoc}.*/
  @Override
  public Class<?> getPublicConfiguration() {
    return HibernateConfiguration.class;
  }

  @Override
  public void init(final ModuleApiRegistry registry) {
  }

  /** {@inheritDoc}.*/
  @Override
  public ConfigurationApi getConfigurationApi() {
    return new HibernateConfigurationApi();
  }

}
