package com.avenida.banten.core;

import java.util.*;

/** Holds the {@link ConfigurationApi} implementations.
 *
 * @author waabox (waabox[at]gmail[dot]com)
 */
public final class ConfigurationApiRegistry {

  /** The static inistance. */
  private static ConfigurationApiRegistry instance;

  static {
    instance = new ConfigurationApiRegistry();
  }

  /** Map that holds the class and implementation of the
   * {@link ConfigurationApi}s.
   */
  private Map<Class<? extends ConfigurationApi>, ConfigurationApi> apis;

  /** Creates a new instance of the Registry.*/
  private ConfigurationApiRegistry() {
    apis = new HashMap<>();
  }

  /** Retrieves the {@link ConfigurationApiRegistry} instance.
   * @return the {@link ConfigurationApiRegistry}, never null.
   */
  static ConfigurationApiRegistry instance() {
    return instance;
  }

  /** Registers the configuration API.
   *
   * @param api the {@link ConfigurationApi}'s implementation.
   */
  static void register(final ConfigurationApi api) {
    instance.apis.put(api.getClass(), api);
  }

  /** Initializes the APIs.*/
  void initApi() {
    Collection<ConfigurationApi> cfgApis = instance.apis.values();
    for(ConfigurationApi cfg : cfgApis) {
      cfg.init();
    }
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
