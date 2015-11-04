package com.avenida.banten.core.database;

import com.avenida.banten.core.boot.BantenApplicationFactory;

/**
 * @author waabox (emi[at]avenida[dot]com)
 */
public class DatabaseTestModuleApplicationFactory extends
    BantenApplicationFactory {

  /** {@inheritDoc}.*/
  @Override
  public Class[] modules() {
    return new Class[] { DatabaseTestModule.class };
  }

  /** {@inheritDoc}.*/
  @Override
  public Class[] with() {
    return new Class[] { HibernateConfiguration.class };
  }

}
