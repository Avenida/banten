package com.avenida.banten.hibernate;

import java.util.List;

import com.avenida.banten.core.ConfigurationApi;
import com.avenida.banten.core.PersistenceUnit;

/** Hibernate configuration.
 *
 * @author waabox (waabox[at]gmail[dot]com)
 */
public class HibernateConfigurationApi extends ConfigurationApi {

  /** Register the persistence units for a {@link Module}.
   * @param units the persistence units to register.
   */
  public void persistenceUnits(final List<PersistenceUnit> units) {
    appendToList("persistenceUnitList", units);
  }

}
