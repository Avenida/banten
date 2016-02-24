package com.avenida.banten.core;

import java.util.*;

import org.apache.commons.lang3.Validate;

/** Groups modules within the same configuration, modules that coexist within
 * the same group share the same database connection, loggin configuration, etc.
 *
 * @author waabox (emi[at]avenida[dot]com)
 */
public class ModuleGroup {

  /** The list of modules, it's never null. */
  private final List<Class<? extends Module>> modules = new LinkedList<>();

  /** The name of the property file.*/
  private String propertyFile;

  /** The group name, by default it's a concatenated module names classes.*/
  private String groupName;

  /** Creates a new instance of the module group.
   *
   * @param theModules the list of modules, cannot be null.
   */
  private ModuleGroup(final Class<? extends Module> ...theModules) {
    Validate.notNull(theModules, "The modules cannot be null");
    modules.addAll(Arrays.asList(theModules));

    StringBuffer sb = new StringBuffer();
    for(Class<? extends Module> m : modules) {
      sb.append(m.getName()).append("-");
    }

    groupName = sb.toString().substring(0, sb.lastIndexOf("-"));

  }

  /** Creates a new group.
   * @param module the list of affected modules, cannot be null.
   * @return this.
   */
  public static ModuleGroup group(final Class<? extends Module> ...module) {
    return new ModuleGroup(module);
  }

  /** Sets the config file for this group.
   * @param configFileName the config or property file name, cannot be null.
   * @return this.
   */
  public ModuleGroup config(final String configFileName) {
    Validate.notNull(configFileName, "The config file cannot be null");
    propertyFile = configFileName;
    return this;
  }

  /** Sets the name for this group.
   * @param name the name for this group.
   * @return this.
   */
  public ModuleGroup name(final String name) {
    groupName = name;
    return this;
  }

  /** Retrieves the modules that belongs to this group.
   * @return the modules, never null.
   */
  public List<Class<? extends Module>> getModules() {
    return modules;
  }

  /** Retrieves the property file.
   * @return the property file, never null.
   */
  public String getPropertyFile() {
    return propertyFile;
  }

  /** Retrieves the groupName.
   * @return the groupName
   */
  public String getGroupName() {
    return groupName;
  }

}
