package com.avenida.banten.web.sitemesh;

import com.avenida.banten.core.*;

/** Banten module to integrate sitemesh in your application.
 *
 * For information on sitemesh, see http://www.sitemesh.org.
 *
 * This module defines two decorators, one for anonymous users and another one
 * for logged in users. It does not decorate modals and api calls (see
 * BantenSitemeshDecoratorSelector for more information on the decorator
 * selection). You must implement two decorators, as freemarker files named
 * decorator.ftl and decoratorAnonymous.ftl.
 *
 * To configure this module, create a bean of type SitemeshConfiguration. You
 * can create that bean in your application or in the public configuration of a
 * module. The SitemeshConfiguration specifies the location of the decorators
 * and the file system relative location of that location.
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
  public Class<?> getPublicConfiguration() {
    return SitemeshPublicConfiguration.class;
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
