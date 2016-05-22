package com.avenida.banten.core;

import static org.easymock.EasyMock.*;

import org.junit.Test;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;

public class ObjectFactoryBeanTest {

  @Test
  public void testRegister() {

    BeanDefinitionRegistry registry = createMock(BeanDefinitionRegistry.class);

    registry.registerBeanDefinition(eq("test"),
        isA(GenericBeanDefinition.class));

    replay(registry);

    ObjectFactoryBean.register(registry, Object.class, new Object(), "test");

    verify(registry);
  }

}
