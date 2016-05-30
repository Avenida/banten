package com.avenida.banten.shiro;

import com.avenida.banten.core.ConfigurationApi;
import com.avenida.banten.core.Module;
import com.avenida.banten.core.ConfigurationApiRegistry;

/** Banten module to integrate shiro in your application.
 *
 * For information on shiro, see http://shiro.apache.org/.
 *
 * This module defines two decorators, one for anonymous users and another one
 * for logged in users. It does not decorate modals and api calls (see
 * BantenShiroDecoratorSelector for more information on the decorator
 * selection). You must implement two decorators, as freemarker files named
 * decorator.ftl and decoratorAnonymous.ftl.
 *
 * To configure this module, create a bean of type ShiroConfigurationOld. You
 * can create that bean in your application or in the public configuration of a
 * module. The ShiroConfigurationOld specifies the location of the decorators
 * and the file system relative location of that location.
 *
 * @author waabox (waabox[at]gmail[dot]com)
 */
public class ShiroModule implements Module {

  /** {@inheritDoc}. */
  @Override
  public String getName() {
    return "Shiro-Module";
  }

  /** {@inheritDoc}. */
  @Override
  public Class<?> getPublicConfiguration() {
    return ShiroConfiguration.class;
  }

  /** {@inheritDoc}. */
  @Override
  public void init(final ConfigurationApiRegistry registry) {
  }

  /** {@inheritDoc}. */
  @Override
  public ConfigurationApi getConfigurationApi() {
    return new ShiroConfigurationApi();
  }
}
