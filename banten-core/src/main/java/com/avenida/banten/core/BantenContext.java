package com.avenida.banten.core;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** BantenContext holds the initialized {@link Module}s and also its
 * {@link ConfigurationApi}.
 *
 * @author waabox (waabox[at]gmail[dot]com)
 */
public class BantenContext {

  /** The log. */
  private final Logger log = LoggerFactory.getLogger(BantenContext.class);

  /** The list of initialized modules, it's never null.*/
  private final List<Module> modules;

  /** The module registry, it's never null. */
  private final ConfigurationApiRegistry moduleRegistry;

  /** Creates a new instance of the {@link BantenContext}.
   * @param bootstrap the {@link Bootstrap} that holds the modules.
   */
  public BantenContext(final Bootstrap bootstrap) {

    Validate.notNull(bootstrap, "The list of modules cannot be null");
    Validate.notEmpty(bootstrap, "The list of modules cannot be empty");

    moduleRegistry = ConfigurationApiRegistry.instance();
    modules = new LinkedList<>();

    for(Class<? extends Module> moduleClass : bootstrap) {
      try {
        modules.add(moduleClass.newInstance());
      } catch (Exception e) {
        log.error("Cannot instantiate the module: {}", moduleClass);
        log.error("{} should have an empty constructor", moduleClass);
        throw new Error(e);
      }
    }

  }

  /** Retrieves the list of {@link Module}s.
   * @return an unmodifiable list of {@link Module}s.
   */
  public List<Module> getModules() {
    return Collections.unmodifiableList(modules);
  }

  /** Retrieves the moduleRegistry.
   * @return the moduleRegistry
   */
  public ConfigurationApiRegistry getModuleRegistry() {
    return moduleRegistry;
  }

  /** Initializes the {@link ConfigurationApiRegistry}.*/
  void initRegistry() {
    moduleRegistry.init();
  }

}
