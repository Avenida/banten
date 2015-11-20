package com.avenida.banten.core;

import java.util.HashMap;
import java.util.Map;

/** Holds the {@link ConfigurationApi} implementations for each {@link Module}.
 *
 *
 * @author waabox (emi[at]avenida[dot]com)
 */
public class ModuleApiRegistry {

  /** The static instance. */
  private static ModuleApiRegistry instance = new ModuleApiRegistry();

  /** Map that holds the class and implementation of the
   * {@link ConfigurationApi}s.
   */
  private Map<Class<? extends ConfigurationApi>, ConfigurationApi> apis;

  /** Creates a new instance of the Registry.*/
  private ModuleApiRegistry() {
    apis = new HashMap<>();
  }

  /** Retrieves the {@link ModuleApiRegistry} instance.
   * @return the {@link ModuleApiRegistry}, never null.
   */
  static ModuleApiRegistry instance() {
    return instance;
  }

  /** Registers the configuration API.
   *
   * @param api the {@link ConfigurationApi}'s implementation.
   */
  static void register(final ConfigurationApi api) {
    instance.apis.put(api.getClass(), api);
  }

  /** Retrieves the configuration API instance.
   * @param configurationApiClass the configuration API to get.
   * @return the configuration API instance, can be null.
   */
  @SuppressWarnings("unchecked")
  public <T> T get(final Class<T> configurationApiClass) {
    return (T) instance.apis.get(configurationApiClass);
  }

}
