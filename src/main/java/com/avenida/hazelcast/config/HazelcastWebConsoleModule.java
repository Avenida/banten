package com.avenida.hazelcast.config;

import java.util.List;

import com.avenida.banten.core.Module;
import com.avenida.banten.core.PersistenceUnit;

/** Hazelcast web console Module.
 *
 * @author waabox (emi[at]avenida[dot]com)
 */
public class HazelcastWebConsoleModule implements Module {

  /** {@inheritDoc}.*/
  @Override
  public String getName() {
    return "HazelcastConsole";
  }

  /** {@inheritDoc}.*/
  @Override
  public String getUrlMapping() {
    return "/webconsole/*";
  }

  /** {@inheritDoc}.*/
  @Override
  public Class<?> getMvcConfiguration() {
    return HazelcastWebConsoleMVCConfiguration.class;
  }

  /** {@inheritDoc}.*/
  @Override
  public Class<?> getModuleConfiguration() {
    return HazelcastWebConsoleConfiguration.class;
  }

  /** {@inheritDoc}.*/
  @Override
  public List<PersistenceUnit> getPersistenceUnits() {
    return null;
  }

}
