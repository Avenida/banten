package com.avenida.banten.core.beans;

import java.util.*;

import org.springframework.context.annotation.Bean;

import com.avenida.banten.core.*;
import com.avenida.banten.core.web.WebletContainer;
import com.avenida.banten.core.web.WebletRenderer;

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

  /** Creates the weblet dispatcher.
   *
   * @return the weblet dispatcher.
   */
  @Bean(name = "bantenWebletDispatcher")
  public WebletRenderer webletDispatcher() {
    return new WebletRenderer(WebletContainer.instance());
  }

}
