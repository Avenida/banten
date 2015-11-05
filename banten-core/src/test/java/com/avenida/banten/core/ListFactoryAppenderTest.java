package com.avenida.banten.core;
/* vim: set ts=2 et sw=2 cindent fo=qroca: */

import java.util.*;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import org.springframework.context.annotation.Bean;
import org.springframework.web.context.support.*;

public class ListFactoryAppenderTest {

  AnnotationConfigWebApplicationContext ctx;

  @Before public void before() {
    ctx = new AnnotationConfigWebApplicationContext();
    ctx.register(ListFactoryAppenderContext.class);
    ctx.refresh();
  }

  @SuppressWarnings("unchecked")
  @Test public void testFactoryAppender() {
    List<String> lst = (List<String>) ctx.getBean("listAppenderExample");
    String[] letters = new String[] {"a", "b", "c", "d", "e"};
    for(String letter : letters) {
      assertTrue("Must contain: " + letter, lst.contains(letter));
    }
  }

  public static final class ListFactoryAppenderContext {

    @Bean(name = "listAppenderExample")
    public List<String> listAppenderExample() {
      return new LinkedList<>();
    }

    @Bean
    public ListFactoryAppender appenderToExample() {
      return new ListFactoryAppender("listAppenderExample",
          Arrays.asList("a", "b", "c"));
    }

    @Bean
    public ListFactoryAppender appenderToExample_b() {
      return new ListFactoryAppender("listAppenderExample",
          Arrays.asList("d", "e", "f"));
    }
  }

}

