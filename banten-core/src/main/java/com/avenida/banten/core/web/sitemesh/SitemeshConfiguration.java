package com.avenida.banten.core.web.sitemesh;

import org.sitemesh.config.PathBasedDecoratorSelector;
import org.sitemesh.webapp.WebAppContext;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.avenida.banten.core.web.freemarker.FreeMarkerConfigurer;

/** Sitemesh's configuration.
 *
 * @author waabox (emi[at]avenida[dot]com)
 */
@Configuration
public class SitemeshConfiguration {

  @Bean(name = "banten.sitemesh.freemarkerConfigurer")
  public FreeMarkerConfigurer freemarkerConfigurer(
      @Value("${debugMode}") final boolean debugMode) {

    return new FreeMarkerConfigurer("classpath:decorators/");
  }

  @Bean(name = "banten.sitemesh.mainFilter")
  public FilterRegistrationBean sitemeshFilter(
      @Qualifier("banten.sitemesh.freemarkerConfigurer")
      final FreeMarkerConfigurer freemarkerConfigurer,
      final SitemeshDecoratorConfiguration configuration) {

    PathBasedDecoratorSelector<WebAppContext> decoratorSelector;
    decoratorSelector = new PathBasedDecoratorSelector<>();
    configuration.configure(decoratorSelector);

    BantenSitemeshDecoratorSelector selector;
    selector = new BantenSitemeshDecoratorSelector(decoratorSelector);

    FilterRegistrationBean filterBean = new FilterRegistrationBean();
    filterBean.setFilter(new BantenSiteMeshFilter(selector, configuration));
    filterBean.addUrlPatterns("/*");

    return filterBean;
  }
}
