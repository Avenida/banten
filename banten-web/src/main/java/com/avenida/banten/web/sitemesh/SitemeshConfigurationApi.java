package com.avenida.banten.web.sitemesh;

import org.apache.commons.lang3.Validate;

import com.avenida.banten.core.ConfigurationApi;
import com.avenida.banten.core.InitContext;
import com.avenida.banten.core.ObjectFactoryBean;

/** Sitemesh configuration Api.
 * @author waabox (waabox[at]gmail[dot]com)
 */
public class SitemeshConfigurationApi extends ConfigurationApi {

  /** Configures Sitemesh for the application.
   * @param configuration the {@link SitemeshConfiguration}, cannot be null.
   */
  public void configure(final SitemeshConfiguration configuration) {
    Validate.notNull(configuration, "The configuration cannot be null");
    ObjectFactoryBean.register(
        InitContext.beanDefinitionRegistry(),
        SitemeshConfiguration.class, configuration, "banten.sitemeshCfg");
  }

}
