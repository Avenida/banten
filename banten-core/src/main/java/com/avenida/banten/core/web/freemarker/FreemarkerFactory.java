package com.avenida.banten.core.web.freemarker;

import java.io.IOException;
import java.util.HashMap;

import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.avenida.banten.core.web.WebletDirective;

import freemarker.template.TemplateException;

/** Creates {@link FreeMarkerViewResolver} & {@link FreeMarkerConfigurer}.
 *
 * @author waabox (emi[at]avenida[dot]com)
 */
public class FreemarkerFactory {

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
    FreeMarkerConfigurer cfg = new FreeMarkerConfigurer();
    cfg.setTemplateLoaderPaths(templatePaths);
    cfg.setDefaultEncoding("UTF-8");
    cfg.setPreferFileSystemAccess(false);
    HashMap<String, Object> variables = new HashMap<>();
    variables.put("weblet", new WebletDirective());
    cfg.setFreemarkerVariables(variables);
    return cfg;
  }
}
