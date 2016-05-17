package com.avenida.banten.core.beans;

import java.util.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.support
  .PropertySourcesPlaceholderConfigurer;

import com.avenida.banten.core.*;

/** Core bean configuration.
 * @author waabox (emi[at]avenida[dot]com)
 */
public class CoreBeansConfiguration {

  /** Bean factory post processor to support @Value in spring beans.
   *
   * This lets modules use @Value in their global bean configuration.
   *
   * @return a post processor that can interpret @Value annotations,
   * never null.
   */
  @Bean public PropertySourcesPlaceholderConfigurer
      propertySourcesPlaceholderConfigurer() {
    return new PropertySourcesPlaceholderConfigurer();
  }

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

