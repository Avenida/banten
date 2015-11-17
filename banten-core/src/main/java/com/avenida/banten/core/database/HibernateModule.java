package com.avenida.banten.core.database;

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
  public List<PersistenceUnit> getPersistenceUnits() {
    return null;
  }

  /** {@inheritDoc}.*/
  @Override
  public List<Weblet> getWeblets() {
    return null;
  }

}
