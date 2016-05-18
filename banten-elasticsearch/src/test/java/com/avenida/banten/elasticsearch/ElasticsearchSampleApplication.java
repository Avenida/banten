package com.avenida.banten.elasticsearch;

import com.avenida.banten.core.BantenApplication;

/** Just a Sample Application for testing purposes.
 *
 * @author waabox (emi[at]avenida[dot]com)
 */
public class ElasticsearchSampleApplication extends BantenApplication {

  public ElasticsearchSampleApplication() {
    super(
        ElasticsearchModule.class,
        ElasticsearchSampleModule.class
    );
  }

}
