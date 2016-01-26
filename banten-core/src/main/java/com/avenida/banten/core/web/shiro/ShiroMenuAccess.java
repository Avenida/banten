package com.avenida.banten.core.web.shiro;

import java.util.List;

/**
 * ShiroMenuAccess contains access url, roles associated and Menu features
 * in order to easily create the App Menu.
 *
 * Created by lucas on 22/01/16.
 */
public class ShiroMenuAccess {

    /** The url path, cannot be null.*/
    private String url;

    /** The roles list, cannot be null.*/
    private List<String> roles;

    /** Menu Module Name*/
    private String name;

    /** Is root : true, is not root : false*/
    private boolean isRoot = false;

    /** If it is root this will be the module name*/
    private String moduleName;

    /**
     * Default Constructor.
     */
    public ShiroMenuAccess() {}

    /**
     * ShiroMenuAccess instantiation.
     * @return ShiroMenuAccess
     */
    public static ShiroMenuAccess getAclInstance() {
        return new ShiroMenuAccess();
    }

    /**
     * Gets all Roles for this Menu Access.
     * @return
     */
    public List<String> getRoles() {
        return roles;
    }

    /**
     * Gets Url for this Access.
     * @return Url String
     */
    public String getUrl() {
        return url;
    }

    /**
     * Sets Roles listing for this Access.
     * @param theRoles
     */
    public void setRoles(List<String> theRoles) {
        roles = theRoles;
    }

    /**
     * Sets Url for this Access.
     * @param theUrl Url to be set.
     */
    public void setUrl(String theUrl) {
        url = theUrl;
    }

    /**
     * Gets Access Menu Name
     * @return String
     */
    public String getName() {
        return name;
    }

    /**
     * Sets Access Menu Name
     * @param name {String}
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets if Access Menu is Root.
     * @param root
     */
    public void setRoot(boolean root) {
        isRoot = root;
    }

    /**
     * Is Menu Access root : True, is Menu Access not root : false
     * @return {boolean}
     */
    public boolean isRoot() {
        return isRoot;
    }

    /**
     * If Root gets Menu Access Module Name
     * @return Menu Acceess Module Name
     */
    public String getModuleName() {
        return moduleName;
    }

    /**
     * If Root sets Menu Access Module Name
     * @param moduleName Menu Access Module Name
     */
    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }
}
