package com.avenida.banten.login;

import org.hibernate.SessionFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import com.avenida.banten.login.domain.UserRepository;

/** Login public configuration.
 *
 * @author waabox (waabox[at]gmail[dot]com)
 */
@Configuration
public class LoginConfiguration {

  @Bean
  @Lazy
  public UserRepository userRepository(final SessionFactory sf) {
    return new UserRepository(sf);
  }

}
