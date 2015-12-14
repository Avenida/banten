package com.avenida.banten.core;

import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.jsoup.helper.Validate;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.support.GenericBeanDefinition;

/** Provides hooks in order to allow {@link Module}s to extends
 * its configuration to another modules.
 *
 * @see Module#getConfigurationApi()
 *
 * @author waabox (emi[at]avenida[dot]com)
 */
public abstract class ConfigurationApi {

  /** Appends to the given {@link Resource} the given list.
   * @param resourceName the resource name, cannot be null.
   * @param list the list to append to the given resource, cannot be null.
   */
  protected void appendToList(final String resourceName, final List<?> list) {

    Validate.notNull(resourceName, "The resource name cannot be null");
    Validate.notNull(list, "The list cannot be null");

    String name = "listFactoryAppender[" + UUID.randomUUID().toString() + "]";

    ConstructorArgumentValues args;
    args = new ConstructorArgumentValues();
    args.addIndexedArgumentValue(0, resourceName);
    args.addIndexedArgumentValue(1, list);

    GenericBeanDefinition bean = new GenericBeanDefinition();
    bean.setBeanClass(ListFactoryAppender.class);
    bean.setConstructorArgumentValues(args);

    InitContext.beanDefinitionRegistry().registerBeanDefinition(name, bean);
  }
}

