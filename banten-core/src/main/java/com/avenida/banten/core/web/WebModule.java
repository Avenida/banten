package com.avenida.banten.core.web;

import java.util.List;

import com.avenida.banten.core.ConfigurationApi;
import com.avenida.banten.core.Module;
import com.avenida.banten.core.ModuleApiRegistry;
import com.avenida.banten.core.Weblet;

/** Web Module.
 *
 * @author waabox (emi[at]avenida[dot]com)
 */
public class WebModule implements Module {

  /** {@inheritDoc}.*/
  @Override
  public String getName() {
    return "Web-Module";
  }

  /** {@inheritDoc}.*/
  @Override
  public String getNamespace() {
    return null;
  }

  /** {@inheritDoc}.*/
  @Override
  public String getRelativePath() {
    return "../banten-core";
  }

  /** {@inheritDoc}.*/
  @Override
  public Class<?> getPrivateConfiguration() {
    return null;
  }

  /** {@inheritDoc}.*/
  @Override
  public Class<?> getPublicConfiguration() {
    return WebModuleConfiguration.class;
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
