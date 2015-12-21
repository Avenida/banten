package com.avenida.banten.hazelcast;

import java.util.List;

import com.avenida.banten.core.*;

/** Hazelcast Module.
 *
 * @author waabox (emi[at]avenida[dot]com)
 */
public class HazelcastModule implements Module {

  /** {@inheritDoc}.*/
  @Override
  public String getName() {
    return "Hazelcast-Module";
  }

  /** {@inheritDoc}.*/
  @Override
  public String getNamespace() {
    return null;
  }

  /** {@inheritDoc}.*/
  @Override
  public String getRelativePath() {
    return "../banten-hazelcast";
  }

  /** {@inheritDoc}.*/
  @Override
  public Class<?> getPrivateConfiguration() {
    return null;
  }

  /** {@inheritDoc}.*/
  @Override
  public Class<?> getPublicConfiguration() {
    return HazelcastConfiguration.class;
  }

  /** {@inheritDoc}.*/
  @Override
  public List<Weblet> getWeblets() {
    return null;
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
