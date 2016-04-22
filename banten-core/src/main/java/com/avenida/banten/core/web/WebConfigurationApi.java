package com.avenida.banten.core.web;

import java.util.List;

import org.apache.commons.lang3.Validate;

import com.avenida.banten.core.ConfigurationApi;
import com.avenida.banten.core.Module;

/** The web configuration API.
 *
 * @author waabox (emi[at]avenida[dot]com)
 */
public class WebConfigurationApi extends ConfigurationApi {

  /** Register a weblet instance for a Module.
   * @param weblets the list of weblets to register, cannot be null.
   * @param module the module that this weblet belogns to, cannot be null.
   * @return this.
   */
  public WebConfigurationApi addWeblets(final List<Weblet> weblets,
      final Module module) {
    Validate.notNull(weblets, "The list of weblets cannot be null");
    Validate.notNull(module, "The module cannot be null");
    WebletContainer.instance().register(module, weblets);
    return this;
  }

}
