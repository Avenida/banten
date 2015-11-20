package com.avenida.banten.sample;

import com.avenida.banten.core.*;
import com.avenida.banten.core.database.HibernateModule;
import com.avenida.banten.core.web.WebModule;
import com.avenida.banten.core.web.sitemesh.SitemeshModule;

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
        SitemeshModule.class,
        WebModule.class,
        TimeModule.class,
        UserModule.class
    };
  }

}
