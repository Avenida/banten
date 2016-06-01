package com.avenida.banten.core;

import java.util.*;

import org.apache.commons.lang3.Validate;

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
  private final Map<Class<? extends ConfigurationApi>, ConfigurationApi> apis;

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
  void init() {
    Collection<ConfigurationApi> cfgApis = instance.apis.values();
    for(ConfigurationApi cfg : cfgApis) {
      cfg.init();
    }
  }

  /** Retrieves the configuration API instance.
   *
   * @param configurationApiClass the configuration API to get.
   * @return the configuration API instance, never null.
   */
  @SuppressWarnings("unchecked")
  public <T> T get(final Class<T> configurationApiClass) {
    T api = (T) instance.apis.get(configurationApiClass);
    Validate.notNull(api, "Api not found: "
        + configurationApiClass.getName() + " "
        + "check you application Bootstrap configuration");
    return api;
  }

}
