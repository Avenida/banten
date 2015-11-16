package com.avenida.banten.core.web.menu;

import java.util.List;

import com.avenida.banten.core.*;

/** The Menu module.
 *
 * @author waabox (emi[at]avenida[dot]com)
 */
public class MenuModule implements Module {

  /** {@inheritDoc}.*/
  @Override
  public String getName() {
    return "Menu-Module";
  }

  /** {@inheritDoc}.*/
  @Override
  public String getNamespace() {
    return "banten-menu";
  }

  /** {@inheritDoc}.*/
  @Override
  public Class<?> getMvcConfiguration() {
    return MenuMVC.class;
  }

  /** {@inheritDoc}.*/
  @Override
  public Class<?> getModuleConfiguration() {
    return MenuConfiguration.class;
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
