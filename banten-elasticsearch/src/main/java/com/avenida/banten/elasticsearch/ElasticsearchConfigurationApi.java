package com.avenida.banten.elasticsearch;

import java.util.Arrays;

import org.apache.commons.lang3.Validate;

import com.avenida.banten.core.ConfigurationApi;

/** Elasticsearch's configuration API.
 *
 * @author waabox (emi[at]avenida[dot]com)
 */
public class ElasticsearchConfigurationApi extends ConfigurationApi {

  /** Register a list of {@link MappingDefinition}s.
   * @param definitions the {@link MappingDefinition}s, cannot be null.
   */
  public void mappings(final MappingDefinition...definitions) {
    Validate.notNull(definitions,
        "The list of mapping definitions cannot be null");
    appendToList("elasticsearch.mappings", Arrays.asList(definitions));
  }

}
