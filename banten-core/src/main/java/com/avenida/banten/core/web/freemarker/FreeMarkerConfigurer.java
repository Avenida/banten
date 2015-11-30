package com.avenida.banten.core.web.freemarker;

import java.util.HashMap;

import com.avenida.banten.core.web.WebletDirective;

/** FreeMarker configurer that forces utf-8 encoding.
 */
public class FreeMarkerConfigurer extends
    org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer {

  /** FreeMarkerViewResolver constructor.
   */
  public FreeMarkerConfigurer(final String ... templatePaths) {
    setTemplateLoaderPaths(templatePaths);
    setDefaultEncoding("UTF-8");
    setPreferFileSystemAccess(false);
    HashMap<String, Object> variables = new HashMap<>();
    variables.put("weblet", new WebletDirective());
    setFreemarkerVariables(variables);
  }
}
