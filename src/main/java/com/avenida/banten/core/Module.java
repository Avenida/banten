package com.avenida.banten.core;

import java.util.List;

/** Describes a Module.
 *
 * @author waabox (emi[at]avenida[dot]com)
 */
public interface Module {

  /** Retrieves the name.
   * @return the name
   */
  String getName();

  /** Retrieves the mapping URL for this module.
   * @return the URL mapping, can be null.
   */
  String getUrlMapping();

  /** Retrieves the MVC configuration.
   * @return the configuration, can be null.
   */
  Class<?> getMvcConfiguration();

  /** Retrieves the moduleConfiguration.
   * @return the moduleConfiguration
   */
  Class<?> getModuleConfiguration();

  /** Retrieves the list of persistence units.
   * @return the list of persistence units, can be null or empty-
   */
  List<PersistenceUnit> getPersistenceUnits();

}
