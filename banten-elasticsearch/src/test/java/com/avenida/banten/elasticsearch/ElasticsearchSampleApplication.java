package com.avenida.banten.elasticsearch;

import com.avenida.banten.core.BantenApplication;

/**
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
