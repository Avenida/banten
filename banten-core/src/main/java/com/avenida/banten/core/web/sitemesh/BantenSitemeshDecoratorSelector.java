package com.avenida.banten.core.web.sitemesh;

import java.io.IOException;

import org.apache.commons.lang3.Validate;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;

import org.sitemesh.*;
import org.sitemesh.content.*;
import org.sitemesh.webapp.*;

import org.springframework.http.MediaType;

/** Sitemesh decorator that ignores any request that:
 *
 * a) content type is {@link MediaType.APPLICATION_JSON_VALUE}.
 * b) matches the request path to "/modal/".
 * c) matches the request path to "/api/".
 *
 * @author waabox (emi[at]avenida[dot]com)
 */
public class BantenSitemeshDecoratorSelector
  implements DecoratorSelector<WebAppContext> {

  /** The decorator selector, it's never null. */
  private final DecoratorSelector<WebAppContext> selector;

  /** Creates a new instance of the decorator selector.
   * @param parent the parent decorator, cannot be null.
   */
  public BantenSitemeshDecoratorSelector(
      final DecoratorSelector<WebAppContext> parent) {
    Validate.notNull(parent, "The selector cannot be null");
    selector = parent;
  }

  /** {@inheritDoc}.*/
  @Override
  public String[] selectDecoratorPaths(
      final Content content, final WebAppContext context) throws IOException {
    if (isAjax(context) || isModal(context) || isApiCall(context)
        || explicitNoDecorate(context)) {
      return new String[0];
    }

    boolean isSecurityConfigured = false;

    try {
      SecurityUtils.getSecurityManager();
      isSecurityConfigured = true;
    } catch (UnavailableSecurityManagerException e) {
      // The UnavailableSecurityManagerException when shiro is not
      // configured.
      isSecurityConfigured = false;
    }

    if (!isSecurityConfigured) {
      // No security configured, use the default decorator always.
      return new String[] {"decorator.ftl"};
    }

    if (SecurityUtils.getSubject().isAuthenticated()) {
      return new String[] {"decorator.ftl"};
    } else {
      return new String[] {"decoratorAnonymous.ftl"};
    }
    //return selector.selectDecoratorPaths(content, context);
  }

  /** Use a request attribute called: "::decoratePage" and checks that its
   * value is false in the scenario that the requested page does not need
   * decoration. If not present, will return false.
   *
   * @param context the Sitemesh's context.
   * @return true if should not decorate the page.
   */
  private boolean explicitNoDecorate(final WebAppContext context) {
    Object value = context.getRequest().getAttribute("::decoratePage");
    if (value != null) {
      return ((Boolean) value == false);
    }
    return false;
  }

  /** Checks whether or not the given context's path matches with "api".
   * @param context the Sitemesh's context.
   * @return true if matches.
   */
  private boolean isApiCall(final WebAppContext context) {
    return context.getPath().contains("/api/");
  }

  /** Checks whether or not the given context's path matches with "modal".
   * @param context the Sitemesh's context.
   * @return true if matches.
   */
  private boolean isModal(final WebAppContext context) {
    return context.getPath().contains("/modal/");
  }

  /** Checks whether or not the current request's media type is json.
   * @param context the Sitemesh's context.
   * @return true if matches.
   */
  private boolean isAjax(final WebAppContext context) {
    return context.getContentType().equals(MediaType.APPLICATION_JSON_VALUE);
  }
}
