package com.avenida.banten.core.web.freemarker;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.Validate;

import com.avenida.banten.core.web.WebletDirective;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;

/** FreeMarker configurer that forces utf-8 encoding.
 */
public class FreeMarkerConfigurer extends
    org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer {

  private boolean debugMode;

  private String relativePath;

  private String[] templatePaths;

  public FreeMarkerConfigurer(final boolean theDebugMode,
      final String relativePath, final String ... theTemplatePaths) {
    Validate.notNull(theTemplatePaths, "The template paths cannot be null.");
    Validate.notEmpty(theTemplatePaths, "You must specify at least one path");
    templatePaths = theTemplatePaths;
  }

  @Override
  public Configuration createConfiguration()
      throws IOException, TemplateException {

    List<String> allPaths = new LinkedList<String>();
    for (String path : templatePaths) {
      if (path.startsWith("classpath:")) {
        String suffix = path.substring("classpath:".length());
        // The suffix cannot start with "/".
        Validate.isTrue(!suffix.startsWith("/"),
            "Classpath resource cannot start with /");

        if (debugMode) {
          // Only add the file system relative path in debug mode.
          String fsPath =  "file:" + relativePath;
          if (!relativePath.endsWith("/")) {
            fsPath += "/";
          }
          fsPath += "src/main/resources/" + suffix;
          allPaths.add(fsPath);
        }
      }
    }
    allPaths.addAll(Arrays.asList(templatePaths));
    setTemplateLoaderPaths(allPaths.toArray(new String[0]));
    setDefaultEncoding("UTF-8");
    setPreferFileSystemAccess(false);
    HashMap<String, Object> variables = new HashMap<>();
    variables.put("weblet", new WebletDirective());
    setFreemarkerVariables(variables);

    return super.createConfiguration();
  }
}

