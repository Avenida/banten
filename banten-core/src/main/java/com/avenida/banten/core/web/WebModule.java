package com.avenida.banten.core.web;

import java.util.List;

import com.avenida.banten.core.Module;
import com.avenida.banten.core.PersistenceUnit;
import com.avenida.banten.core.Weblet;

/** Web Module.
 *
 * @author waabox (emi[at]avenida[dot]com)
 */
public class WebModule implements Module {

  @Override
  public String getName() {
    return "Web-Module";
  }

  @Override
  public String getNamespace() {
    return null;
  }

  @Override
  public Class<?> getMvcConfiguration() {
    return null;
  }

  @Override
  public Class<?> getModuleConfiguration() {
    return WebModuleConfiguration.class;
  }

  @Override
  public List<PersistenceUnit> getPersistenceUnits() {
    return null;
  }

  @Override
  public List<Weblet> getWeblets() {
    return null;
  }
}
