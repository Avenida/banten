package com.avenida.banten.core;

import org.slf4j.*;

import java.util.*;

import org.apache.commons.lang3.Validate;

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

  /** Holds a key value with the registered web modules.*/
  private final Map<Class<?>, BantenWebApplicationContext> webContexts;

  /** Creates a new instance of the {@link BantenContext}.
   * @param bootstrap the {@link Bootstrap} that holds the modules.
   */
  public BantenContext(final Bootstrap bootstrap) {

    Validate.notNull(bootstrap, "The list of modules cannot be null");
    Validate.notEmpty(bootstrap, "The list of modules cannot be empty");

    moduleRegistry = new ConfigurationApiRegistry();
    modules = new LinkedList<>();
    webContexts = new HashMap<>();

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

  /** Registers the {@link BantenWebApplicationContext} into this context.
   * @param context the {@link BantenWebApplicationContext}, cannot be null.
   */
  void register(final BantenWebApplicationContext context) {
    webContexts.put(context.getModule().getClass(), context);
  }

  /** Registers a new {@link ConfigurationApi} into the {@link BantenContext}.
   * @param api the {@link ConfigurationApi}, cannot be null.
   */
  void register(final ConfigurationApi api) {
    moduleRegistry.register(api);
  }

  /** Retrieves the {@link BantenWebApplicationContext} for the given
   * {@link Module} class.
   * @param module the {@link Module} class, cannot be null.
   * @return the {@link BantenWebApplicationContext} or null.
   */
  public BantenWebApplicationContext getWebApplicationContext(
      final Class<? extends Module> module) {
    return webContexts.get(module);
  }

}
