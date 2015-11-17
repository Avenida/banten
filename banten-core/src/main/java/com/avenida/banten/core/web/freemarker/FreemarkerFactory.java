package com.avenida.banten.core.web.freemarker;

import java.io.IOException;

import org.springframework.ui.freemarker.FreeMarkerConfigurationFactory;

import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.avenida.banten.core.web.WebletDirective;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;

/** Creates {@link FreeMarkerViewResolver} & {@link FreeMarkerConfigurer}.
 *
 * @author waabox (emi[at]avenida[dot]com)
 */
public class FreemarkerFactory {

  /** Creates a new instance of the {@link ViewResolver}.
   * @return the {@link ViewResolver}, never null.
   */
  public static ViewResolver viewResolver() {
    FreeMarkerViewResolver resolver = new FreeMarkerViewResolver();
    resolver.setCache(true);
    resolver.setPrefix("");
    resolver.setSuffix(".ftl");
    resolver.setContentType("text/html; charset=UTF-8");
    resolver.setExposeRequestAttributes(true);
    resolver.setExposeSpringMacroHelpers(true);
    return resolver;
  }

  /** Creates a new instance of the {@link FreeMarkerConfigurer}.
   * @param templatePaths the list of template paths, cannot be null.
   * @return the {@link FreeMarkerConfigurer}, never null.
   *
   * @throws IOException
   * @throws TemplateException
   */
  public static FreeMarkerConfigurer freemarkerConfigurer(
      final String ... templatePaths) throws IOException,
      TemplateException {
    FreeMarkerConfigurationFactory factory;
    factory = new FreeMarkerConfigurationFactory();
    factory.setTemplateLoaderPaths(templatePaths);
    factory.setDefaultEncoding("UTF-8");

    Configuration configuration = factory.createConfiguration();
    configuration.setSharedVariable("weblet", new WebletDirective());

    FreeMarkerConfigurer cfg = new FreeMarkerConfigurer();
    cfg.setConfiguration(configuration);
    return cfg;
  }

}
