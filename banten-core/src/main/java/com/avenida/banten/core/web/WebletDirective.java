package com.avenida.banten.core.web;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.*;

import com.avenida.banten.core.Context;

import freemarker.core.Environment;
import freemarker.template.*;

import static com.avenida.banten.core.web.WebletRendererRegistrationFilter.*;

/** Weblet renderer freemarker template directive.
 *
 * @author waabox (emi[at]avenida[dot]com)
 */
public class WebletDirective implements TemplateDirectiveModel {

  /** {@inheritDoc}.*/
  @Override @SuppressWarnings("rawtypes")
  public void execute(final Environment env, final Map parameters,
      final TemplateModel[] loopVars, final TemplateDirectiveBody body)
      throws TemplateException, IOException {

    HttpServletRequest request = Context.request();
    HttpServletResponse response = Context.response();

    String moduleName = ((SimpleScalar) parameters.get("module")).getAsString();
    String webletName = ((SimpleScalar) parameters.get("name")).getAsString();

    WebletRenderer renderer;
    renderer = (WebletRenderer) request.getAttribute(WEBLET_RENDERER_PARAMETER);

    try {
      String html = renderer.render(webletName, moduleName, request, response);
      env.getOut().append(html);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

}
