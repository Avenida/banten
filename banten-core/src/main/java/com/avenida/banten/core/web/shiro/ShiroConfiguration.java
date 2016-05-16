package com.avenida.banten.core.web.shiro;

import java.util.*;

import org.apache.commons.lang3.Validate;

/** Holds the Shiro configuration.
 *
 * Created by lucas on 13/01/16.
 */
public class ShiroConfiguration {

  /** The login URL, it's never null. */
  private String loginUrl;

  /** The login URL, it's never null. */
  private String successUrl;

  /** The unauthorized URL, it's never null. */
  private String unauthorizedUrl;

  /** List containing all valid end-points with the appropriate roles. */
  private List<UrlToRoleMapping> urlToRoleMappings;

  /** Creates a new instance of the configuration.
   *
   * @param theLoginUrl the login URL, cannot be null.
   * @param theSuccessUrl the success URl, cannot be null.
   * @param theUnauthorizedUrl the unauthorized URL, cannot be null.
   * @param mappings the mappings, cannot be null.
   */
  public ShiroConfiguration(final String theLoginUrl,
      final String theSuccessUrl,
      final String theUnauthorizedUrl,
      final List<UrlToRoleMapping> mappings) {

    Validate.notNull(theLoginUrl, "The login URL cannot be null");
    Validate.notNull(theSuccessUrl, "The success URL cannot be null");
    Validate.notNull(theUnauthorizedUrl, "The unauthorized URL cannot be null");
    Validate.notNull(mappings, "The mappings cannot be null");

    loginUrl = theLoginUrl;
    successUrl = theSuccessUrl;
    unauthorizedUrl = theUnauthorizedUrl;
    urlToRoleMappings = mappings;
  }

  /** Creates a new instance of the configuration without mappings.
  *
  * @param theLoginUrl the login URL, cannot be null.
  * @param theSuccessUrl the success URl, cannot be null.
  * @param theUnauthorizedUrl the unauthorized URL, cannot be null.
  */
  public ShiroConfiguration(
      final String theLoginUrl,
      final String theSuccessUrl,
      final String theUnauthorizedUrl) {
    this(theLoginUrl, theSuccessUrl, theUnauthorizedUrl,
        new ArrayList<UrlToRoleMapping>());
  }

  /** Retrieves the login URL.
   * @return the URL never null.
   */
  public String getLoginUrl() {
    return loginUrl;
  }

  /** The success URL.
   * @return the URL never null.
   */
  public String getSuccessUrl() {
    return successUrl;
  }

  /** The Unauthorized URL.
   * @return the URL never null.
   */
  public String getUnauthorizedUrl() {
    return unauthorizedUrl;
  }

  /** The list of mappings.
   * @return the mappings never null.
   */
  public List<UrlToRoleMapping> getUrlRoleMappings() {
    return Collections.unmodifiableList(urlToRoleMappings);
  }

  /** Adds a new mapping into the existing list.
   *
   * @param urlRoleMappings the new ACLs.
   */
  public void addUrlRoleMappings(final List<UrlToRoleMapping> urlRoleMappings) {
    urlRoleMappings.addAll(urlRoleMappings);
  }

}
