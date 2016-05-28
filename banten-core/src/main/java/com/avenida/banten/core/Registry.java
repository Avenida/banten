package com.avenida.banten.core;

/** A module registry is the ability to interacts with the
 * {@link ModuleApiRegistry} in order to configure a {@link Module}.
 *
 * @author waabox (waabox[at]gmail[dot]com)
 */
public interface Registry {

  /** Register within the context this module.
   * @param registry the {@link ModuleApiRegistry}, never null.
   */
  void init(ModuleApiRegistry registry);

}