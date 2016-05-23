package com.avenida.banten.core;

/** Describes a Module.
 *
 * @author waabox (waabox[at]gmail[dot]com)
 */
public interface Module {

  /** Retrieves the Module's name.
   * @return the name, never null.
   */
  String getName();

  /** Retrieves the public Module configuration.
   * @return the public configuration, can be null.
   */
  Class<?> getPublicConfiguration();

  /** Retrieves the configuration API.
   * @return the configuration API or null.
   */
  ConfigurationApi getConfigurationApi();

  /** Register within the context this module.
   * @param registry the {@link ModuleApiRegistry}, never null.
   */
  void init(final ModuleApiRegistry registry);
}
