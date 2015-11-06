package com.avenida.banten.sample;

import com.avenida.banten.core.boot.BantenApplicationFactory;
import com.avenida.banten.core.database.HibernateConfiguration;
import com.avenida.banten.sample.time.TimeModule;

/** The Sample application Factory.
 *
 * @author waabox (emi[at]avenida[dot]com)
 */
public class SampleApplicationFactory extends BantenApplicationFactory {

  /** {@inheritDoc}.*/
  @Override
  public Class[] modules() {
    return new Class[] {
        TimeModule.class
    };
  }

  /** {@inheritDoc}.*/
  @Override
  public Class[] with() {
    return new Class[] {
        HibernateConfiguration.class
    };
  }

}
