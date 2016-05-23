package com.avenida.banten.web.sitemesh;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.avenida.banten.web.freemarker.FreeMarkerConfigurer;

import freemarker.template.TemplateException;

/** Banten sitemesh module public configuration.
 *
 * @author waabox (waabox[at]gmail[dot]com)
 */
@Configuration
public class SitemeshPublicConfiguration {

  @Bean(name = "banten.sitemesh.mainFilter")
  public FilterRegistrationBean sitemeshFilter(
      @Value("${debugMode:false}") final boolean debugMode,
      final SitemeshConfiguration configuration)
          throws IOException, TemplateException {

    BantenSitemeshDecoratorSelector selector;
    selector = new BantenSitemeshDecoratorSelector();

    FreeMarkerConfigurer freemarkerConfigurer;
    freemarkerConfigurer = configuration.createFreeMarkerConfigurer(debugMode);

    FilterRegistrationBean filterBean = new FilterRegistrationBean();
    filterBean.setFilter(new BantenSiteMeshFilter(debugMode,
        freemarkerConfigurer, selector));
    filterBean.addUrlPatterns("/*");

    return filterBean;
  }
}
