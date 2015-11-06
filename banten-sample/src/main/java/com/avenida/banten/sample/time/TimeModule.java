package com.avenida.banten.sample.time;

import java.util.LinkedList;
import java.util.List;

import com.avenida.banten.core.Module;
import com.avenida.banten.core.PersistenceUnit;
import com.avenida.banten.sample.time.domain.Time;

/** A simple time module.
 * @author waabox (emi[at]avenida[dot]com)
 */
public class TimeModule implements Module {

  /** {@inheritDoc}.*/
  @Override
  public String getName() {
    return "Time-Module";
  }

  /** {@inheritDoc}.*/
  @Override
  public String getUrlMapping() {
    return "/time/*";
  }

  /** {@inheritDoc}.*/
  @Override
  public Class<?> getMvcConfiguration() {
    return TimeMVC.class;
  }

  /** {@inheritDoc}.*/
  @Override
  public Class<?> getModuleConfiguration() {
    return TimeConfiguration.class;
  }

  /** {@inheritDoc}.*/
  @Override
  public List<PersistenceUnit> getPersistenceUnits() {
    List<PersistenceUnit> units = new LinkedList<>();
    units.add(new PersistenceUnit(Time.class));
    return units;
  }

}
