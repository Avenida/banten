package com.avenida.banten.core.web.shiro;

import org.apache.commons.lang3.Validate;

/**
 * Created by lucas on 13/01/16.
 */
public class ShiroConfiguration {


    /** The login url, never null.
     */
    private String loginUrl;

    /** The unauthorize url, never null.
     */
    private String unauthorizeUrl;

    /** The module name, never null.
     */
    private String moduleName;

    /**
     *
     * @return loginUrl
     */
    public String getLoginUrl() {
        return loginUrl;
    }

    /**
     *
     * @return unauthorizeUrl
     */
    public String getUnauthorizeUrl() {
        return unauthorizeUrl;
    }

    /**
     *
     * @return moduleName
     */
    public String getModuleName() {
        return moduleName;
    }

    /** Creates a configuration.
     *
     * @param theLoginUrl
     * @param theUnauthorizeUrl
     */

    public ShiroConfiguration(final String theLoginUrl,
                                 final String theUnauthorizeUrl) {
        Validate.notNull(theLoginUrl, "The loginUrl path cannot be null");
        Validate.notNull(theUnauthorizeUrl, "The unauthorizeUrl cannot be null");
        loginUrl = theLoginUrl;
        unauthorizeUrl = theUnauthorizeUrl;
    }



}
