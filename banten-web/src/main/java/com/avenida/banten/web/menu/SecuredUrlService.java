package com.avenida.banten.web.menu;

import java.util.List;

import com.avenida.banten.shiro.ShiroConfigurationApi;

/** Service that provides {@link Role}s information based on URLs.
 *
 * @author waabox (waabox[at]gmail[dot]com)
 */
public class SecuredUrlService {

  /** Retrieves the roles for the requested URL.
   * @param url the URL, cannot be null.
   * @return the list of roles, or an empty list if does not have a definition
   * for the given URL.
   */
  public List<String> rolesFor(final String url) {
    return ShiroConfigurationApi.rolesFor(url);
  }

}
