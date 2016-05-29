package com.avenida.banten.core;

/** A module registry is the ability to interacts with the
 * {@link ConfigurationApiRegistry} in order to work with
 * another {@link ConfigurationApi}.
 *
 * @author waabox (waabox[at]gmail[dot]com)
 */
public interface Registry {

  /** Register within the context this module.
   * @param registry the {@link ConfigurationApiRegistry}, never null.
   */
  void init(ConfigurationApiRegistry registry);

}