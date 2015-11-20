package com.avenida.banten.core.database;

import java.util.List;

import com.avenida.banten.core.ConfigurationApi;
import com.avenida.banten.core.Module;
import com.avenida.banten.core.PersistenceUnit;

/** Hibernate configuration.
 *
 * @author waabox (emi[at]avenida[dot]com)
 */
public class HibernateConfigurationApi extends ConfigurationApi {

  /** Register the persistence units for a {@link Module}.
   * @param units the persistence units to register.
   */
  public void persistenceUnits(final List<PersistenceUnit> units) {
    appendToList("persistenceUnitList", units);
  }

}
