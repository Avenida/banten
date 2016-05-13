package com.avenida.banten.core.beans;

import java.io.IOException;
import java.util.*;

import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.Validate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.embedded.ServletRegistrationBean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import org.springframework.web.servlet.view.RedirectView;

import com.avenida.banten.core.*;
import com.avenida.banten.core.web.*;

/** Core bean configuration.
 * @author waabox (emi[at]avenida[dot]com)
 */
public class CoreBeansConfiguration {

  /** Bean factory post processor to support @Value in spring beans.
   *
   * This lets modules use @Value in their global bean configuration.
   *
   * @return a post processor that can interpret @Value annotations,
   * never null.
   */
  @Bean public PropertySourcesPlaceholderConfigurer
      propertySourcesPlaceholderConfigurer() {
    return new PropertySourcesPlaceholderConfigurer();
  }

  /** Creates the Module Service locator.
   * @return the module service locator.
   */
  @Bean public ModuleServiceLocator createModuleServiceLocator() {
    return new ModuleServiceLocator();
  }

  /** Creates the list that holds the persistence units.
   * @return the list of persistence units.
   */
  @Bean(name = "persistenceUnitList")
  public List<PersistenceUnit> persistenceUnitList() {
    return new LinkedList<>();
  }

  /** Creates the weblet dispatcher.
   *
   * @return the weblet dispatcher.
   */
  @Bean(name = "banten.webletDispatcher")
  public WebletRenderer webletDispatcher() {
    return new WebletRenderer(WebletContainer.instance());
  }

  @Bean(name = "banten.contextFilter")
  public FilterRegistrationBean bantenContextFilter() {
    FilterRegistrationBean filterBean = new FilterRegistrationBean();
    filterBean.setFilter(new ContextFilter());
    filterBean.addUrlPatterns("/*");
    return filterBean;
  }

  /** Retrieves the {@link WebletRendererRegistrationFilter}.
   * @param renderer the {@link WebletRenderer}, cannot be null.
   * @return a {@link FilterRegistrationBean} instance, never null.
   */
  @Bean(name = "banten.webletRenderer")
  @Autowired
  public FilterRegistrationBean webletRendererFilter(
      final WebletRenderer renderer) {
    FilterRegistrationBean filterBean = new FilterRegistrationBean();
    filterBean.setFilter(new WebletRendererRegistrationFilter(renderer));
    filterBean.addUrlPatterns("/*");
    return filterBean;
  }

  /** Servlet mapped to the root web application context to redirect to a
   * configurable url.
   *
   * Banten maps this servlet to the '/' path. This servlet just redirects to
   * the configured url.
   *
   * Banten creates and configures this servlet obtaining the home url from
   * the value passed to BantenApplication.setLandingUrl() operation.
   *
   * Note: this servlet is very naive. We should allow the application to
   * configure a more complex strategy to select the landing url.
   */
  private static class HomeServlet extends HttpServlet {

    /** The serial version id. */
    private static final long serialVersionUID = 1L;

    /** The configured landing url, never null.
     */
    private final String landingUrl;

    /** Creates the home servlet.
     *
     * @param theLandingUrl the landing url. It cannot be null.
     */
    public HomeServlet(final String theLandingUrl) {
      Validate.notNull(theLandingUrl, "The landing page cannot be null.");
      landingUrl = theLandingUrl;
    }

    /** {@inheritDoc}
     */
    @Override
    public void service(final HttpServletRequest request,
        final HttpServletResponse response)
            throws ServletException, IOException {
      RedirectView redirectView = new RedirectView(landingUrl);
      try {
        redirectView.render(null, request, response);
      } catch (IOException | ServletException e) {
        throw e;
      } catch (Exception e) {
        throw new ServletException("Error redirecting to home page", e);
      }
    }
  }

  /** Creates the home servlet if the application-wise landing url is defined.
   *
   * @param landingUrl the application-wise landing url. Null if not defined.
   * See BantenApplication.setLandingUrl() for more information.
   *
   * @return the home servlet (an instance of HomeServlet) mapped to the root
   * path of the web application context if the landingUrl is not null. If
   * landingUrl is null, this operation returns null and the servlet container
   * will handle all requests to the root path.
   */
  @Bean(name = "banten.homeServlet")
  @ConditionalOnBean(name = "banten.landingUrl")
  public ServletRegistrationBean homeServlet(
      @Qualifier("banten.landingUrl") final String landingUrl) {
    ServletRegistrationBean servletBean = null;
    if (landingUrl != null) {
      HomeServlet servlet = new HomeServlet(landingUrl);
      servletBean = new ServletRegistrationBean(servlet, false, "");
      servletBean.setOrder(Integer.MAX_VALUE);
    }
    return servletBean;
  }
}

