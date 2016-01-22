package com.avenida.banten.core.web.shiro;

import org.apache.commons.lang3.Validate;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lucas on 13/01/16.
 */
public class ShiroConfiguration {

  /** The login url, never null. */
  private String loginUrl;

  /** The login url, never null. */
  private String successUrl;

  /** The unauthorize url, never null. */
  private String unauthorizeUrl;

  /** Map containing all valid endpoints with the appropriate permission,
   *  never null. */
  private Map<String, String> endpointPermissions
          = new HashMap<String, String>();

  /**
   * @return loginUrl
   */
  public String getLoginUrl() {
      return loginUrl;
  }

  /**
   * @return successUrl
   */
  public String getSuccessUrl() {
      return successUrl;
  }

  /**
   * @return unauthorizeUrl
   */
  public String getUnauthorizeUrl() {
      return unauthorizeUrl;
  }

  /**
   *
   * @return endpointPermissions
   */
  public Map<String, String> getEndpointPermissions() {
    return endpointPermissions;
  }

  /**
   *
   * @param theLoginUrl
   * @param theSuccessUrl
   * @param theUnauthorizeUrl
   * @param theEndpointPermissions
   */
  public ShiroConfiguration(String theLoginUrl, String theSuccessUrl,
                            String theUnauthorizeUrl,
                            Map<String, String> theEndpointPermissions) {
    this.loginUrl = theLoginUrl;
    this.successUrl = theSuccessUrl;
    this.unauthorizeUrl = theUnauthorizeUrl;
    this.endpointPermissions = theEndpointPermissions;
  }
}
