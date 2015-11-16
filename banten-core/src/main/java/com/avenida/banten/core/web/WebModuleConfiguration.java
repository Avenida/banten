package com.avenida.banten.core.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.*;


/** Web Module configuration.
 *
 * @author waabox (emi[at]avenida[dot]com)
 */
@Configuration
public class WebModuleConfiguration {

  @Bean(name = "banten.webletRenderer")
  @Autowired
  public FilterRegistrationBean webletRendererFilter(
      final WebletRenderer renderer) {
    FilterRegistrationBean filterBean = new FilterRegistrationBean();
    filterBean.setFilter(new WebletRendererRegistrationFilter(renderer));
    filterBean.addUrlPatterns("/*");
    return filterBean;
  }
}
