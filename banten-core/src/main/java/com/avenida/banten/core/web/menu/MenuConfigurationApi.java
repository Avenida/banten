package com.avenida.banten.core.web.menu;

import java.util.*;

import org.slf4j.Logger;
import static org.slf4j.LoggerFactory.getLogger;

import com.avenida.banten.core.ConfigurationApi;

/** Menu configuration Api.
 *
 *
 *
 * @author waabox (emi[at]avenida[dot]com)
 */
public class MenuConfigurationApi extends ConfigurationApi {

  /** The log. */
  private static Logger log = getLogger(MenuConfigurationApi.class);

  /** The list of nodes. */
  private static List<Menu> nodes = new LinkedList<>();

  /** The root menu. */
  private static Menu menu = new Menu("/", "root");

  /** Register a menu node.
   * @param node the menu node.
   */
  private void register(final Menu node) {
    nodes.add(node);
  }

  /** Retrieves the menu.
   *
   * @return the menu, never never null.
   */
  public static Menu get() {
    return menu;
  }

  /** {@inheritDoc}.*/
  @Override
  protected void init() {

    // Sort the node list by path.
    Collections.sort(nodes, new Comparator<Menu>() {
      @Override
      public int compare(final Menu o1, final Menu o2) {
        return o1.getPath().compareTo(o2.getPath());
      }
    });

    for (Menu aMenu : nodes) {

      String path = aMenu.getPath();

      log.debug("Adding to the menu: " + path);

      Menu parentMenu = menu.searchFor(path);
      if (parentMenu == null) {
        String parentPath = path.substring(0, path.lastIndexOf('/'));
        if (parentPath.equals("")) {
          menu.add(aMenu);
        } else {
          Menu newParent = menu.searchFor(parentPath);
          if (newParent == null) {
            throw new IllegalStateException(
                "Please declare the root menu for the path:" + parentPath);
          }
          newParent.add(aMenu);
        }
      } else {
        parentMenu.add(aMenu);
      }
    }
  }

  /** Registers a new menu entry as a leaf.
   *
   * @param aName the name, cannot be null.
   * @param aLink the link, cannot be null.
   * @param aPath the path, cannot be null & must start with: '/';
   * @return this.
   */
  public MenuConfigurationApi node(final String aName, final String aLink,
      final String aPath) {
    register(new Menu(aName, aLink, aPath));
    return this;
  }

  /** Registers a root node.
   *
   * @param aName the name for the root menu, cannot be null.
   * @param path the path for this root menu,
   *  cannot be null & must start with: '/';
   * @return this.
   */
  public MenuConfigurationApi root(final String aName, final String path) {
    register(new Menu(path, aName));
    return this;
  }

}
