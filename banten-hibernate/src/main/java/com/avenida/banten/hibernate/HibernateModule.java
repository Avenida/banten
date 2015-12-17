package com.avenida.banten.hibernate;

import java.util.List;

import com.avenida.banten.core.*;

/** Hibernate's Module.
 * @author waabox (emi[at]avenida[dot]com)
 */
public class HibernateModule implements Module {

  /** {@inheritDoc}.*/
  @Override
  public String getName() {
    return "Hibernate-Module";
  }

  /** {@inheritDoc}.*/
  @Override
  public String getNamespace() {
    return null;
  }

  /** {@inheritDoc}.*/
  @Override
  public Class<?> getPrivateConfiguration() {
    return null;
  }

  /** {@inheritDoc}.*/
  @Override
  public Class<?> getPublicConfiguration() {
    return HibernateConfiguration.class;
  }

  /** {@inheritDoc}.*/
  @Override
  public List<Weblet> getWeblets() {
    return null;
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
