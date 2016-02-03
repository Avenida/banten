package com.avenida.banten.core.web.shiro;

import java.util.ArrayList;
import java.util.List;

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

  /** List containing all valid endpoints with the appropriate roles. */
  private List<ShiroUrlRoleMapping> urlRoleMappings;

  /**
   * ShiroConfiguration constructor.
   *
   * @param theLoginUrl
   * @param theSuccessUrl
   * @param theUnauthorizeUrl
   * @param theUrlRoleMappings
   */
  public ShiroConfiguration(String theLoginUrl, String theSuccessUrl,
                            String theUnauthorizeUrl,
                            List<ShiroUrlRoleMapping> theUrlRoleMappings) {
    this.loginUrl = theLoginUrl;
    this.successUrl = theSuccessUrl;
    this.unauthorizeUrl = theUnauthorizeUrl;
    this.urlRoleMappings = theUrlRoleMappings;
  }

  /**
   * ShiroConfiguration constructor.
   *
   * @param theLoginUrl
   * @param theSuccessUrl
   * @param theUnauthorizeUrl
   */
  public ShiroConfiguration(String theLoginUrl, String theSuccessUrl,
                            String theUnauthorizeUrl) {
      this.loginUrl = theLoginUrl;
      this.successUrl = theSuccessUrl;
      this.unauthorizeUrl = theUnauthorizeUrl;
      this.urlRoleMappings = new ArrayList<>();
  }


  /**
   * @return loginUrl.
   */
  public String getLoginUrl() {
    return loginUrl;
  }

  /**
   * @return successUrl.
   */
  public String getSuccessUrl() {
    return successUrl;
  }

  /**
   * @return unauthorizeUrl.
   */
  public String getUnauthorizeUrl() {
    return unauthorizeUrl;
  }

  /**
   * @return urlRoleMappings.
   */
  public List<ShiroUrlRoleMapping> getUrlRoleMappings() {
      return urlRoleMappings;
  }

  /**
   * Adds urlRoleMappings to the existing list.
   * @param urlRoleMappings
   */
  public void addUrlRoleMappings(List<ShiroUrlRoleMapping> urlRoleMappings) {
  this.urlRoleMappings.addAll(urlRoleMappings);
  }

}
