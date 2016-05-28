package com.avenida.banten.web.sitemesh;

import com.avenida.banten.core.ConfigurationApi;
import com.avenida.banten.core.InitContext;
import com.avenida.banten.core.ObjectFactoryBean;

/** Sitemesh configuration Api.
 * @author waabox (waabox[at]gmail[dot]com)
 */
public class SitemeshConfigurationApi extends ConfigurationApi {

  /** Configures Sitemesh for the application.
   *
   * @param theRelativePath the relative path of the module that contains the
   * decorators in the file system. This assumes a standard maven file system
   * layout, ie: if a decorator is in classpath:com/avenida/d/x.ftl,
   * sitemesh will look for it in
   * relativePath/src/main/resources/com/avenida/d/x.ftl. This cannot be null.
   *
   * @param paths the paths to look for decorators for. It cannot be null.
   */
  public void configure(final String theRelativePath,
      final String ... paths) {
    ObjectFactoryBean.register(
        InitContext.beanDefinitionRegistry(),
        SitemeshConfiguration.class,
        new SitemeshConfiguration(theRelativePath, paths),
        "banten.sitemeshCfg"
    );
  }

}
