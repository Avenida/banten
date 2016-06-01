package com.avenida.banten.web.menu;

import java.util.LinkedList;
import java.util.List;

import org.apache.shiro.SecurityUtils;

import com.avenida.banten.shiro.ShiroConfigurationApi;

/** Filters the menu based on the active logged in user and its roles.
 *
 * @author waabox (waabox[at]gmail[dot]com)
 */
public class MenuSecurityFilter {

  /** The menu to be filtered, it's never null.*/
  private final Menu menu;

  /** Creates a new instance of the {@link MenuSecurityFilter}.
   *
   * @param theMenu the {@link Menu} to filter, cannot be null.
   */
  public MenuSecurityFilter(final Menu theMenu) {
    menu = theMenu;
  }

  /** Retrieves a filtered {@link Menu}.
   * @return the {@link Menu}, never null.
   */
  public Menu filter() {
    Menu filteredMenu = new Menu(menu.getPath(), menu.getDisplayName());
    filteredMenu.add(doFilter(menu));
    return filteredMenu;
  }

  /** Performs a recursive action in order to detect nodes that the current
   * logged in user can access.
   * @param theMenu the menu.
   * @return the list of {@link Menu}s that the user is allowed to access.
   */
  private List<Menu> doFilter(final Menu theMenu) {
    List<Menu> toAdd = new LinkedList<>();
    for(Menu current : theMenu.getChildNodes()) {
      if (current.isLeaf()) {
        List<String> roles = ShiroConfigurationApi.rolesFor(current.getLink());
        boolean hasRole = false;
        for (String role : roles) {
          if(SecurityUtils.getSubject().hasRole(role)) {
            hasRole = true;
          }
        }
        if (hasRole) {
          toAdd.add(current);
        }
      }
      if (!current.getChildNodes().isEmpty()) {
        List<Menu> fromChild = doFilter(current);
        if (!fromChild.isEmpty()) {
          Menu container = current.emptyCopy();
          container.add(fromChild);
          toAdd.add(container);
        }
      }
    }
    return toAdd;
  }

}
