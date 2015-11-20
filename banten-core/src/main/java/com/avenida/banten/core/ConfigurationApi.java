package com.avenida.banten.core;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.support.GenericBeanDefinition;

/**
 * @author waabox (emi[at]avenida[dot]com)
 */
public abstract class ConfigurationApi {

  protected void appendToList(final String resourceName,
      final List<?> values) {
    String name = "listFactoryAppender[" + UUID.randomUUID().toString() + "]";

    ConstructorArgumentValues args;
    args = new ConstructorArgumentValues();
    args.addIndexedArgumentValue(0, resourceName);
    args.addIndexedArgumentValue(1, values);

    GenericBeanDefinition bean = new GenericBeanDefinition();
    bean.setBeanClass(ListFactoryAppender.class);
    bean.setConstructorArgumentValues(args);

    InitContext.beanDefinitionRegistry().registerBeanDefinition(name, bean);
  }

}
