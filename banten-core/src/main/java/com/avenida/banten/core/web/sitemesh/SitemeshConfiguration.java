package com.avenida.banten.core.web.sitemesh;

import org.sitemesh.config.PathBasedDecoratorSelector;
import org.sitemesh.webapp.WebAppContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.*;

import org.springframework.context.annotation.*;

/** Sitemesh's configuration.
 *
 * @author waabox (emi[at]avenida[dot]com)
 */
@Configuration
public class SitemeshConfiguration {

  @Bean(name = "banten.sitemeshfilter")
  @Autowired
  public FilterRegistrationBean sitemeshFilter(
      final SitemeshDecoratorConfiguration configuration) {

    PathBasedDecoratorSelector<WebAppContext> decorator;
    decorator = new PathBasedDecoratorSelector<>();
    configuration.configure(decorator);

    BantenSitemeshDecoratorSelector selector;
    selector = new BantenSitemeshDecoratorSelector(decorator);

    FilterRegistrationBean filterBean = new FilterRegistrationBean();
    filterBean.setFilter(new BantenSiteMeshFilter(selector, configuration));
    filterBean.addUrlPatterns("/*");

    return filterBean;
  }
}
