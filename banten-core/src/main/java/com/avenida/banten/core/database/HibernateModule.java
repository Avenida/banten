package com.avenida.banten.core.database;

import java.util.List;

import com.avenida.banten.core.Module;
import com.avenida.banten.core.PersistenceUnit;

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
  public String getUrlMapping() {
    return null;
  }

  /** {@inheritDoc}.*/
  @Override
  public Class<?> getMvcConfiguration() {
    return null;
  }

  /** {@inheritDoc}.*/
  @Override
  public Class<?> getModuleConfiguration() {
    return HibernateConfiguration.class;
  }

  /** {@inheritDoc}.*/
  @Override
  public List<PersistenceUnit> getPersistenceUnits() {
    return null;
  }

}
