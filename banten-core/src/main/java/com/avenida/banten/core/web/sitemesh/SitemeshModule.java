package com.avenida.banten.core.web.sitemesh;

import java.util.List;

import com.avenida.banten.core.Module;
import com.avenida.banten.core.PersistenceUnit;

/** Sitemesh's configuration Module.
 *
 * @author waabox (emi[at]avenida[dot]com)
 */
public class SitemeshModule implements Module {

  /** {@inheritDoc}.*/
  @Override
  public String getName() {
    return "Sitemesh-Module";
  }

  /** {@inheritDoc}.*/
  @Override
  public String getUrlMapping() {
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
    return SitemeshConfiguration.class;
  }

  /** {@inheritDoc}.*/
  @Override
  public List<PersistenceUnit> getPersistenceUnits() {
    return null;
  }

}
