package com.avenida.banten.elasticsearch;

import com.avenida.banten.core.BantenApplication;
import com.avenida.banten.core.ConfigurationApiRegistry;
import com.avenida.banten.core.Bootstrap;

/** Just a Sample Application for testing purposes.
 *
 * @author waabox (waabox[at]gmail[dot]com)
 */
public class ElasticsearchSampleApplication extends BantenApplication {

  @Override
  protected Bootstrap bootstrap() {
    return new Bootstrap(
        ElasticsearchModule.class,
        ElasticsearchSampleModule.class
    );
  }

  @Override
  public void init(final ConfigurationApiRegistry registry) {
  }

}
