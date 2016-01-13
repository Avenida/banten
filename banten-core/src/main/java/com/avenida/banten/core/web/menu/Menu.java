package com.avenida.banten.core.web.menu;

import java.util.*;
import org.apache.commons.lang3.*;

/** Represents a Menu.
 *
 * The structure represents a simple tree, the construction is private and its
 * factory is {@link MenuConfigurationApi}.
 *
 * TODO [waabox] add an attribute that allow us to order the menu.
 * TODO [waabox] please doc.me
 *
 * @author waabox (emi[at]avenida[dot]com)
 */
public class Menu {

  /** The menu node name, it's never null. */
  private final String displayName;

  /** The menu node link, can be null.*/
  private final String link;

  /** The menu path, can be null.*/
  private final String path;

  /** The list of child nodes, it's never null.*/
  private final List<Menu> childNodes = new LinkedList<>();

  /** Whether or not this Menu is leaf or not. */
  private final boolean leaf;

  /** Creates a new instance of the menu node for 'root' menus.
   * @param aName the name, cannot be null.
   */
  Menu(final String aPath, final String aName) {

    Validate.notNull(aName, "The name cannot be null.");
    Validate.notNull(aPath, "The path cannot be null.");
    Validate.isTrue(aPath.startsWith("/"), "The path must start with '/'");

    displayName = aName;
    path = aPath;
    link = null;
    leaf = false;

  }

  /** Creates a new instance of the menu node for a leaf.
   * @param aName the name, cannot be null.
   * @param aLink the link, cannot be null.
   * @param aPath the path, cannot be null & must start with: '/';
   */
  Menu(final String aName, final String aLink, final String aPath) {

    Validate.notNull(aName, "The name cannot be null.");
    Validate.notNull(aLink, "The link cannot be null.");
    Validate.notNull(aPath, "The path cannot be null.");
    Validate.isTrue(aPath.startsWith("/"), "The path must start with '/'");

    displayName = aName;
    link = aLink;
    path = aPath;
    leaf = true;

  }

  /** Retrieves the name.
   * @return the name
   */
  public String getName() {
    return displayName;
  }

  /** Retrieves the link.
   * @return the link
   */
  public String getLink() {
    return link;
  }

  /** Retrieves the childNodes.
   * @return the childNodes
   */
  public List<Menu> getChildNodes() {
    return childNodes;
  }

  /** Adds a new child node.
   * @param node the menu node to add.
   */
  Menu add(final Menu node) {
    childNodes.add(node);
    return this;
  }

  /** Retrieves the path for this menu.
   * @return the path, never null.
   */
  public String getPath() {
    return path;
  }

  /** Search within this menu the Menu that matches the given path.
   * @param path the path, cannot be null.
   * @return the menu or null.
   */
  Menu searchFor(final String path) {
    Validate.notNull(path, "The path cannot be null");
    for (Menu menu : childNodes) {
      if (menu.getPath().equals(path)) {
        return menu;
      } else {
        Menu candidate = menu.searchFor(path);
        if (candidate != null) {
          return candidate;
        }
      }
    }
    return null;
  }

  /** Retrieves the leaf.
   * @return the leaf
   */
  public boolean isLeaf() {
    return leaf;
  }

  /** {@inheritDoc}.*/
  @Override
  public String toString() {

    StringBuffer sb = new StringBuffer();
    sb.append("{");
    sb.append(displayName).append(":").append(link);

    if (!childNodes.isEmpty()) {
      sb.append("[");
    }

    Iterator<Menu> it = childNodes.iterator();
    while (it.hasNext()) {
      Menu menu = (Menu) it.next();
      sb.append(menu.toString());
      if (it.hasNext()) {
        sb.append(",");
      }
    }

    if (!childNodes.isEmpty()) {
      sb.append("]");
    }

    sb.append("}\n");

    return sb.toString();
  }

}
