package com.avenida.banten.core.web.sitemesh;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.commons.lang3.Validate;

import org.sitemesh.builder.SiteMeshFilterBuilder;
import org.sitemesh.config.ConfigurableSiteMeshFilter;
import org.sitemesh.content.ContentProcessor;

import org.sitemesh.webapp.*;
import org.sitemesh.webapp.contentfilter.*;

import org.springframework.ui.freemarker.FreeMarkerConfigurationFactory;

import freemarker.template.*;

/** Sitemesh filter configuration.
 *
 * @author waabox (emi[at]avenida[dot]com)
 */
public class BantenSiteMeshFilter extends ConfigurableSiteMeshFilter {

  /** The sitemesh's configuration, cannot be null.*/
  private final BantenSitemeshDecoratorSelector bantenSelector;

  /** Whether or not should use freemarker. */
  private final boolean useFreemarker;

  /** The freemarker configuration, can be null.*/
  private final Configuration freemarkerConfiguration;

  /** Creates a new instance of the Filter.
   * @param selector the decorator selector.
   * @param configuration the decorator configuration.
   */
  public BantenSiteMeshFilter(final BantenSitemeshDecoratorSelector selector,
      final SitemeshDecoratorConfiguration configuration) {

    Validate.notNull(selector, "The bantenSelector cannot be null");
    bantenSelector = selector;
    useFreemarker = configuration.useFreemarker();

    if (useFreemarker) {
      FreeMarkerConfigurationFactory factory;
      factory = new FreeMarkerConfigurationFactory();
      factory.setTemplateLoaderPaths(configuration.templateLoaderPath());
      factory.setDefaultEncoding("UTF-8");

      try {
        freemarkerConfiguration = factory.createConfiguration();
      } catch (IOException e) {
        throw new RuntimeException(e);
      } catch (TemplateException e) {
        throw new RuntimeException(e);
      }
    } else {
      freemarkerConfiguration = null;
    }
  }

  /** {@inheritDoc}.*/
  @Override
  protected void applyCustomConfiguration(
      final SiteMeshFilterBuilder builder) {
    builder.setCustomDecoratorSelector(bantenSelector);
  }

  /** {@inheritDoc}.*/
  @Override
  protected boolean reloadRequired() {
    if (!useFreemarker) {
      return super.reloadRequired();
    }
    return false;
  }

  /** {@inheritDoc}.*/
  protected Filter setup() throws ServletException {

    if (!useFreemarker) {
      return super.setup();
    }

    SiteMeshFilterBuilder builder;
    builder = new SiteMeshFilterBuilder() {

      // Begin inner class of SiteMeshFilterBuilder.
      /** {@inheritDoc}.*/
      public Filter create() {

        final ContentProcessor processor = getContentProcessor();
        final boolean includeErrorPages = isIncludeErrorPages();

        SiteMeshFilter filter = new SiteMeshFilter(
            getSelector(),
            getContentProcessor(),
            getDecoratorSelector(),
            isIncludeErrorPages())
        // begin inner class of SiteMeshFilter
        {
          /** {@inheritDoc}.*/
          protected WebAppContext createContext(
              final String contentType,
              final HttpServletRequest request,
              final HttpServletResponse response,
              final ResponseMetaData metaData) {

            return new FreemarkerWebAppContext(
                contentType,
                request,
                response,
                processor,
                metaData,
                includeErrorPages,
                freemarkerConfiguration
             );

          }
        };
        // end inner class of SiteMeshFilter
        return filter;
      }
    };
    // end inner class of SiteMeshFilterBuilder

    applyCustomConfiguration(builder);

    return builder.create();
  }

}
