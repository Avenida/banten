package com.avenida.banten.core;

/** Describes a Module.
 *
 * @author waabox (emi[at]avenida[dot]com)
 */
public interface Module {

  /** Retrieves the Module's name.
   * @return the name, never null.
   */
  String getName();

  /** Retrieves the module namespace, typically this will be the Servlet's
   * context path.
   * @return the name-space, can be null.
   */
  String getNamespace();

  /** Retrieves the file system relative path of this module.
   *
   * Modules should return a value of the form "../banten-sample". The banten
   * application searches for resources exposed as static resources from the
   * file system location: <relativePath>/src/main/resources. This makes
   * it possible, for example, to refresh static content sent to the browser.
   *
   * @return the file systemn relative path, never null.
   */
  String getRelativePath();

  /** Retrieves the private Module configuration.
   * @return the configuration, can be null.
   */
  Class<?> getPrivateConfiguration();

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
