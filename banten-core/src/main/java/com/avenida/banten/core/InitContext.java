package com.avenida.banten.core;

import org.jsoup.helper.Validate;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;

/** Initialization context that holds within a {@link ThreadLocal} values
 * that will be used in the Spring's initialization.
 *
 * @author waabox (emi[at]avenida[dot]com)
 */
public class InitContext {

  /** Thread local storage that holds the {@link BeanDefinitionRegistry}. */
  private static ThreadLocal<BeanDefinitionRegistry> registry;

  static {
    registry = new ThreadLocal<>();
  }

  /** Register the {@link BeanDefinitionRegistry} within the context.
   * @param aRegistry the {@link BeanDefinitionRegistry}, cannot be null.
   */
  public static void registerBeandefinitionRegistry(
      final BeanDefinitionRegistry aRegistry) {
    Validate.notNull(aRegistry, "the registry cannot be null");
    registry.set(aRegistry);
  }

  /** Retrieves the {@link BeanDefinitionRegistry}.
   * @return the {@link BeanDefinitionRegistry}, never null.
   */
  public static BeanDefinitionRegistry beanDefinitionRegistry() {
    return registry.get();
  }

  /** Cleans up the context.*/
  public static void cleanup() {
    registry.remove();
  }

}
