package com.avenida.banten.hibernate;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

public class DatabaseTestModuleConfiguration {

  @Autowired
  @Bean public Repository repository(final SessionFactory sf) {
    return new Repository(sf);
  }

  @Bean public MockEntityWithTuplizerFactory mockEntityWithTuplizerFactory() {
    return new MockEntityWithTuplizerFactory();
  }

}
