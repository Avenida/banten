package com.avenida.banten.core.web.sitemesh;

import java.util.*;
import java.util.Map.*;

import org.sitemesh.config.PathBasedDecoratorSelector;
import org.sitemesh.webapp.WebAppContext;

/** Sitemesh's decorator configuration.
 *
 * @author waabox (emi[at]avenida[dot]com)
 */
public class SitemeshDecoratorConfiguration {

  /** Map that holds a context path as a key and a decorator as value. */
  private final Map<String, String> contextPathDecorator = new HashMap<>();

  /** Whether or not should use freemarker, default true.*/
  private boolean useFreemarker = true;

  /** The freemarker template loader path, it's never null. */
  private String templateLoaderPath = "classpath:decorators/";

  /** Creates an empty instance.*/
  private SitemeshDecoratorConfiguration() {
  }

  /** Configure the Path Based decorator selector.
   * @param d the decorator selector, cannot be null.
   */
  public void configure(final PathBasedDecoratorSelector<WebAppContext> d) {
    for(Entry<String, String> entry : contextPathDecorator.entrySet()) {
      d.put(entry.getKey(), entry.getValue());
    }
  }

  /** Checks whether or not should use Freemarker to discovery the decorators.
   * @return true if should use Freemarker.
   */
  public boolean useFreemarker() {
    return useFreemarker;
  }

  /** Retrieves the template loader path.
   * @return the template loader path, never null.
   */
  public String templateLoaderPath() {
    return templateLoaderPath;
  }

  /** The Sitemesh decorator configuration builder.
   *
   * @author waabox (emi[at]avenida[dot]com)
   */
  public static class Builder {

    /** The configuration. */
    private SitemeshDecoratorConfiguration configuration;

    /** Creates a new instance of the Builder.
     * @param addDefaultDecorator if should add into "/*" a default decorator.
     */
    public Builder() {
      configuration = new SitemeshDecoratorConfiguration();
    }

    /** Disable the Freemarker decorator discovery.
     * @return this.
     */
    public Builder disableFreemarker() {
      configuration.useFreemarker = false;
      return this;
    }

    /** The Freemarker's template loador path.
     * @param classpathLocation the classpath location.
     * @return this.
     */
    public Builder templateLoaderPath(final String classpathLocation) {
      configuration.templateLoaderPath = classpathLocation;
      return this;
    }

    /** Adds a new decorator for the given URL.
     * @param url the URL.
     * @param decorator the decorator.
     * @return this.
     */
    public Builder addDecorator(final String url, final String decorator) {
      configuration.contextPathDecorator.put(url, decorator);
      return this;
    }

    /** Builds the configurator.
     * @return the configurator, never null.
     */
    public SitemeshDecoratorConfiguration build() {
      return configuration;
    }
  }
}

