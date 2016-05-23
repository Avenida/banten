package com.avenida.banten.hazelcast;

import com.avenida.banten.core.*;

/** Hazelcast Module.
 *
 * @author waabox (waabox[at]gmail[dot]com)
 */
public class HazelcastModule implements Module {

  /** {@inheritDoc}.*/
  @Override
  public String getName() {
    return "Hazelcast-Module";
  }

  /** {@inheritDoc}.*/
  @Override
  public Class<?> getPublicConfiguration() {
    return HazelcastConfiguration.class;
  }

  /** {@inheritDoc}.*/
  @Override
  public void init(final ModuleApiRegistry registry) {
  }

  /** {@inheritDoc}.*/
  @Override
  public ConfigurationApi getConfigurationApi() {
    return null;
  }

}
