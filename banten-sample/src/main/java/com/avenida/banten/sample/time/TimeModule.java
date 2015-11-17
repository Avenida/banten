package com.avenida.banten.sample.time;

import java.util.LinkedList;
import java.util.List;

import com.avenida.banten.core.*;
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
  public String getNamespace() {
    return "time";
  }

  /** {@inheritDoc}.*/
  @Override
  public Class<?> getPrivateConfiguration() {
    return TimeMVC.class;
  }

  /** {@inheritDoc}.*/
  @Override
  public Class<?> getPublicConfiguration() {
    return TimeConfiguration.class;
  }

  /** {@inheritDoc}.*/
  @Override
  public List<PersistenceUnit> getPersistenceUnits() {
    List<PersistenceUnit> units = new LinkedList<>();
    units.add(new PersistenceUnit(Time.class));
    return units;
  }

  /** {@inheritDoc}.*/
  @Override
  public List<Weblet> getWeblets() {
    return null;
  }

}
