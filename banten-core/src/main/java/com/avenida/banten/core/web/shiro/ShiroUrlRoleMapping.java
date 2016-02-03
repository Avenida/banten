package com.avenida.banten.core.web.shiro;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * ShiroUrlRoleMapping contains access url and roles associated.
 *
 * Created by jordan.cabral on 02/02/16.
 */
public class ShiroUrlRoleMapping {

    /** The url path, cannot be null.*/
    private String url;

    /** The roles list, cannot be null.*/
    private List<String> roles;

    /**
     * ShiroUrlRoleMapping Constructor.
     *
     * @param url
     * @param roles
     */
    public ShiroUrlRoleMapping(String url, List<String> roles) {
        this.url = url;
        this.roles = roles;
    }

    /**
     * Gets all Roles for this URL.
     * @return List of roles
     */
    public List<String> getRoles() {
        return roles;
    }

    /**
     * Gets all Roles for this URL in a comma separated string.
     * @return String with all roles
     */
    public String getRolesString() {
        return StringUtils.join(roles, ",");
    }

    /**
     * Gets Url for this Access.
     * @return Url String
     */
    public String getUrl() {
        return url;
    }

}
