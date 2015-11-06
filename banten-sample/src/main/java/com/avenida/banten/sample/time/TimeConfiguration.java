package com.avenida.banten.sample.time;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.avenida.banten.sample.time.domain.TimeRepository;

/** The configuration.
 *
 * @author waabox (emi[at]avenida[dot]com)
 */
@Configuration
public class TimeConfiguration {

  @Bean @Autowired
  public TimeRepository timeRepository(final SessionFactory sessionFactory) {
    return new TimeRepository(sessionFactory);
  }

}
