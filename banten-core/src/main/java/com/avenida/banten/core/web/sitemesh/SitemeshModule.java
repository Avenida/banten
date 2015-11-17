package com.avenida.banten.core.web.sitemesh;

import java.util.List;

import com.avenida.banten.core.*;

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
  public String getNamespace() {
    return null;
  }

  /** {@inheritDoc}.*/
  @Override
  public Class<?> getPrivateConfiguration() {
    return null;
  }

  /** {@inheritDoc}.*/
  @Override
  public Class<?> getPublicConfiguration() {
    return SitemeshConfiguration.class;
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
