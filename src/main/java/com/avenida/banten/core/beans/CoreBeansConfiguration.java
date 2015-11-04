package com.avenida.banten.core.beans;

import java.util.LinkedList;
import java.util.List;

import org.springframework.context.annotation.Bean;

import com.avenida.banten.core.ModuleServiceLocator;
import com.avenida.banten.core.PersistenceUnit;

/** Core bean configuration.
 * @author waabox (emi[at]avenida[dot]com)
 */
public class CoreBeansConfiguration {

  /** Creates the Module Service locator.
   * @return the module service locator.
   */
  @Bean public ModuleServiceLocator createModuleServiceLocator() {
    return new ModuleServiceLocator();
  }

  /** Creates the list that holds the persistence units.
   * @return the list of persistence units.
   */
  @Bean(name = "persistenceUnitList")
  public List<PersistenceUnit> persistenceUnitList() {
    return new LinkedList<>();
  }

}
