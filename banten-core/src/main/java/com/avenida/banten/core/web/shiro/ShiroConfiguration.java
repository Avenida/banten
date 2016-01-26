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

  /** List containing all valid endpoints with the appropriate permission,
   *  never null. */
  private List<ShiroMenuAccess> aclPermissions
    = new ArrayList<ShiroMenuAccess>();

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
   * @param theLoginUrl
   * @param theSuccessUrl
   * @param theUnauthorizeUrl
   * @param theEndpointPermissions
   */
  public ShiroConfiguration(String theLoginUrl, String theSuccessUrl,
                            String theUnauthorizeUrl,
                            List<ShiroMenuAccess> theEndpointPermissions) {
    this.loginUrl = theLoginUrl;
    this.successUrl = theSuccessUrl;
    this.unauthorizeUrl = theUnauthorizeUrl;
    this.aclPermissions = theEndpointPermissions;
  }

    /**
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
    }

    /**
     *
     * @return
     */
    public List<ShiroMenuAccess> getAclPermissions() {
        return aclPermissions;
    }

    /**
     *
     * @param aclPermissions
     */
    public void setAclPermissions(List<ShiroMenuAccess> aclPermissions) {
        this.aclPermissions = aclPermissions;
    }

    /**
     *
     * @param aclPermissions
     */
    public void addAclPermissions(List<ShiroMenuAccess> aclPermissions) {
    this.aclPermissions.addAll(aclPermissions);
  }

}
