package com.avenida.banten.hibernate;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import com.avenida.banten.core.ConfigurationApi;

/** Hibernate configuration.
 *
 * @author waabox (waabox[at]gmail[dot]com)
 */
public class HibernateConfigurationApi extends ConfigurationApi {

  /** The list of persistence units. */
  private static List<PersistenceUnit> persistenceUnits = new LinkedList<>();

  /** Register the persistence units for a {@link Module}.
   * @param units the persistence units to register.
   */
  public void persistenceUnits(final PersistenceUnit ... units) {
    persistenceUnits.addAll(Arrays.asList(units));
  }

  /** Retrieves the list of persistence units.
   * @return the list of persistence units.
   */
  public static List<PersistenceUnit> getPersistenceUnits() {
    return persistenceUnits;
  }

}
