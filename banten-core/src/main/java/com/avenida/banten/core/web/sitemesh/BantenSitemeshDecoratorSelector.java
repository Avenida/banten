package com.avenida.banten.core.web.sitemesh;

import java.io.IOException;

import org.apache.commons.lang3.Validate;

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
    if (isAjax(context) || isModal(context) || isApiCall(context)) {
      return new String[0];
    }
    return selector.selectDecoratorPaths(content, context);
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
