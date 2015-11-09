package com.avenida.banten.sample;

import com.avenida.banten.core.Module;
import com.avenida.banten.core.boot.BantenApplicationFactory;
import com.avenida.banten.core.database.HibernateModule;

import com.avenida.banten.sample.time.TimeModule;
import com.avenida.banten.sample.user.UserModule;

/** The Sample application Factory.
 *
 * @author waabox (emi[at]avenida[dot]com)
 */
public class SampleApplicationFactory extends BantenApplicationFactory {

  /** {@inheritDoc}.*/
  @SuppressWarnings("unchecked")
  @Override
  public Class<? extends Module>[] modules() {
    return new Class[] {
        HibernateModule.class,
        TimeModule.class,
        UserModule.class
    };
  }

}
