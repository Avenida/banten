package com.avenida.banten.hibernate;

import java.util.*;

import com.avenida.banten.core.ConfigurationApi;

/** Hibernate's configuration.
 *
 * I need to use static references for the persistenceUnits because I need
 * it within the {@link PlatformTuplizer.FactoryCache#get()}.
 *
 * TODO [waabox] use the Hibernate Services in order to provide those
 * classes, for now, it's ok, however it's a little bit dark this.
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
