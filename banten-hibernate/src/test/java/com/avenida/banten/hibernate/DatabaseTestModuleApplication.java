package com.avenida.banten.hibernate;

import com.avenida.banten.core.BantenApplication;

/** Database test module application.
 * @author waabox (waabox[at]gmail[dot]com)
 */
public class DatabaseTestModuleApplication extends BantenApplication {

  public DatabaseTestModuleApplication() {
    super(HibernateModule.class,
        DatabaseTestModule.class);
  }
}

