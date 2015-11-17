package com.avenida.banten.core;

import java.util.List;

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

  /** Retrieves the private Module configuration.
   * @return the configuration, can be null.
   */
  Class<?> getPrivateConfiguration();

  /** Retrieves the public Module configuration.
   * @return the public configuration, never null.
   */
  Class<?> getPublicConfiguration();

  /** Retrieves the list of persistence units.
   * @return the list of persistence units, can be null or empty-
   */
  List<PersistenceUnit> getPersistenceUnits();

  /** Retrieves the list of weblets for this module.
   *
   * @return the list of weblets.
   */
  List<Weblet> getWeblets();

}
