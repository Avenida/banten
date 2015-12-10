package com.avenida.banten.core.database;

import com.avenida.banten.core.BantenApplication;

/** Database test module application.
 * @author waabox (emi[at]avenida[dot]com)
 */
public class DatabaseTestModuleApplication extends BantenApplication {

  public DatabaseTestModuleApplication() {
    super(HibernateModule.class,
        DatabaseTestModule.class);
  }
}

