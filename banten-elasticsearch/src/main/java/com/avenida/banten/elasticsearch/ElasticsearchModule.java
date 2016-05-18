package com.avenida.banten.elasticsearch;

import com.avenida.banten.core.ConfigurationApi;
import com.avenida.banten.core.Module;
import com.avenida.banten.core.ModuleApiRegistry;

/** Elasticsearch Module.
 *
 * @author waabox (emi[at]avenida[dot]com)
 */
public class ElasticsearchModule implements Module {

  /** {@inheritDoc}.*/
  @Override
  public String getName() {
    return "Elasticsearch-Module";
  }

  /** {@inheritDoc}.*/
  @Override
  public Class<?> getPublicConfiguration() {
    return ElasticsearchConfiguration.class;
  }

  /** {@inheritDoc}.*/
  @Override
  public ConfigurationApi getConfigurationApi() {
    return new ElasticsearchConfigurationApi();
  }

  /** {@inheritDoc}.*/
  @Override
  public void init(final ModuleApiRegistry registry) {
  }

}
