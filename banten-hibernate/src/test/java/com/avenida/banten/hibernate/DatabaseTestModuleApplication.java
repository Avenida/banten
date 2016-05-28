package com.avenida.banten.hibernate;

import com.avenida.banten.core.BantenApplication;
import com.avenida.banten.core.ModuleApiRegistry;
import com.avenida.banten.core.Bootstrap;

/** Database test module application.
 * @author waabox (waabox[at]gmail[dot]com)
 */
public class DatabaseTestModuleApplication extends BantenApplication {

  @Override
  protected Bootstrap bootstrap() {
    return new Bootstrap(
        HibernateModule.class,
        DatabaseTestModule.class
     );
  }

  @Override
  public void init(final ModuleApiRegistry registry) {
  }

}

