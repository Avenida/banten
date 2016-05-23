package com.avenida.banten.elasticsearch;

import com.avenida.banten.core.BantenApplication;

/** Just a Sample Application for testing purposes.
 *
 * @author waabox (waabox[at]gmail[dot]com)
 */
public class ElasticsearchSampleApplication extends BantenApplication {

  public ElasticsearchSampleApplication() {
    super(
        ElasticsearchModule.class,
        ElasticsearchSampleModule.class
    );
  }

}
