package com.avenida.banten.core.hazelcast;

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
  public Class<?> getMvcConfiguration() {
    return null;
  }

  /** {@inheritDoc}.*/
  @Override
  public Class<?> getModuleConfiguration() {
    return HazelcastConfiguration.class;
  }

  /** {@inheritDoc}.*/
  @Override
  public List<PersistenceUnit> getPersistenceUnits() {
    return null;
  }

  /** {@inheritDoc}.*/
  @Override
  public List<Weblet> getWeblets() {
    return null;
  }

}
