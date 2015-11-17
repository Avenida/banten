package com.avenida.banten.core;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Holds the current {@link HttpServletRequest} & {@link HttpServletResponse}
 * for the current active request.
 *
 * @author waabox (emi[at]avenida[dot]com)
 */
public class Context {

  /** The thread local storage, it's never null. */
  private static final ThreadLocal<ContextData> context = new ThreadLocal<>();

  /** Initializes the context.
   * @param request the servlet request, cannot be null.
   * @param response the servlet response, cannot be null.
   */
  public static void init(final HttpServletRequest request,
      final HttpServletResponse response) {
    context.set(new ContextData(request, response));
  }

  /** Destroys the current context.*/
  public static void destroy() {
    context.remove();
  }

  /** Retrieves the current {@link HttpServletRequest}
   * @return the request, never null.
   */
  public static HttpServletRequest request() {
    return context.get().getRequest();
  }

  /** Retrieves the current {@link HttpServletResponse}
   * @return the response, never null.
   */
  public static HttpServletResponse response() {
    return context.get().getResponse();
  }


  /** Holds the {@link HttpServletRequest} & {@link HttpServletResponse}.
   *
   * @author waabox (emi[at]avenida[dot]com)
   */
  private static class ContextData {

    /** The request. */
    private final HttpServletRequest request;

    /** The response. */
    private final HttpServletResponse response;

    /** Creates a new instance.
     * @param theRequest the request.
     * @param theResponse the response.
     */
    private ContextData(final HttpServletRequest theRequest,
        final HttpServletResponse theResponse) {
      request = theRequest;
      response = theResponse;
    }

    /** Retrieves the request.
     * @return the request
     */
    public HttpServletRequest getRequest() {
      return request;
    }

    /** Retrieves the response.
     * @return the response
     */
    public HttpServletResponse getResponse() {
      return response;
    }
  }

}
