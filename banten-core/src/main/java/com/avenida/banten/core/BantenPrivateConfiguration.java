/* vim: set et sw=2 cindent fo=qroca: */

package com.avenida.banten.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import org.springframework.core.env.Environment;

import org.springframework.web.servlet.config.annotation
    .ResourceHandlerRegistration;
import org.springframework.web.servlet.config.annotation
    .ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation
    .WebMvcConfigurationSupport;

/** The banten base configuration for the module private spring application
 * context.
 *
 * This configuration defines beans that are available in the dispatcher
 * servlet application context. See BantenApplication for more information.
 */
@Configuration
public class BantenPrivateConfiguration extends WebMvcConfigurationSupport {

  /** The spring environment, never null.
   *
   * WARNING: do not generally autowire properties, IT IS A BAD PRACTICE!!!!
   * Done here because I did not find another way.
   */
  @Autowired Environment environment;

  /** The module description bean that 'owns' the current application
   * context, never null.
   *
   * WARNING: do not generally autowire properties, IT IS A BAD PRACTICE!!!!
   * Done here because I did not find another way.
   */
  @Autowired ModuleDescription moduleDescription;

  /** Bean that supports ${property} in @Value annotations.
   *
   * @return the configurer that replaces ${property} with the corresponding
   * environment values. Never returns null.
   */
  @Bean
  public static PropertySourcesPlaceholderConfigurer
      propertySourcesPlaceholderConfigurer() {
    return new PropertySourcesPlaceholderConfigurer();
  }

  /** {@inheritDoc}
   *
   * This implementation makes the module serve static content from the
   * module classpath.
   */
  @Override
  protected void addResourceHandlers(final ResourceHandlerRegistry registry) {
    boolean debugMode;
    debugMode = environment.getProperty("debugMode", Boolean.class, false);

    ResourceHandlerRegistration registration;
    registration = registry.addResourceHandler("/**");
    if (debugMode) {
      registration.addResourceLocations(
          "file:" + moduleDescription.getRelativePath()
          + "/src/main/resources/" + moduleDescription.getClasspath()
          + "/static/");
    }
    registration.addResourceLocations("classpath:/"
        + moduleDescription.getClasspath() + "/static/");
    //registration.setCachePeriod(cachePeriod);
  }
}


