package com.avenida.banten.sample.calendar;

import java.util.List;

import com.avenida.banten.core.Module;
import com.avenida.banten.core.PersistenceUnit;

/** A simple calendar module.
 * @author waabox (emi[at]avenida[dot]com)
 */
public class CalendarModule implements Module {

  /** {@inheritDoc}.*/
  @Override
  public String getName() {
    return "Calendar-Module";
  }

  /** {@inheritDoc}.*/
  @Override
  public String getUrlMapping() {
    return "/calendar/*";
  }

  /** {@inheritDoc}.*/
  @Override
  public Class<?> getMvcConfiguration() {
    return CalendarMVC.class;
  }

  /** {@inheritDoc}.*/
  @Override
  public Class<?> getModuleConfiguration() {
    return CalendarModule.class;
  }

  /** {@inheritDoc}.*/
  @Override
  public List<PersistenceUnit> getPersistenceUnits() {
    return null;
  }

}
