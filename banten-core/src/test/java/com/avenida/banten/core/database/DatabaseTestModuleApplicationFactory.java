package com.avenida.banten.core.database;

import com.avenida.banten.core.Module;
import com.avenida.banten.core.boot.BantenApplicationFactory;

/** Database test module application factory.
 * @author waabox (emi[at]avenida[dot]com)
 */
@SuppressWarnings("unchecked")
public class DatabaseTestModuleApplicationFactory extends
    BantenApplicationFactory {

  /** {@inheritDoc}.*/
  @Override
  public Class<? extends Module>[] modules() {
    return new Class[] {
        HibernateModule.class,
        DatabaseTestModule.class
    };
  }

}

