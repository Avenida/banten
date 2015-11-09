package com.avenida.banten.sample.user;

import com.avenida.banten.sample.time.domain.TimeRepository;
import com.avenida.banten.sample.user.domain.UserFactory;
import com.avenida.banten.sample.user.domain.UserRepository;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** The user configuration.
 * @author waabox (efinal mi[at]avenida[dot]com)
 */
@Configuration
public class UserConfiguration {

  @Bean(name = "user.userFactory")
  @Autowired
  public UserFactory userFactory(final TimeRepository timeRepository) {
    return new UserFactory(timeRepository);
  }

  @Bean(name = "user.userRepository")
  @Autowired
  public UserRepository userRepository(final SessionFactory sessionFactory) {
    return new UserRepository(sessionFactory);
  }

}
