package com.avenida.banten.core;

import java.util.LinkedList;

import static org.easymock.EasyMock.*;

import org.junit.Test;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;

public class ConfigurationApiTest {

  @Test public void testAppendToList() {
    BeanDefinitionRegistry registry = createMock(BeanDefinitionRegistry.class);

    registry.registerBeanDefinition(isA(String.class),
        isA(GenericBeanDefinition.class));

    replay(registry);

    InitContext.init(registry);

    ConfigurationApi api = new ConfigurationApi() {};

    api.appendToList("waabox", new LinkedList<>());

    verify(registry);

    InitContext.destroy();
  }

}
