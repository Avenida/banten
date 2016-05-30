package com.avenida.banten.sample;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

import com.avenida.banten.hibernate.Transaction;
import com.avenida.banten.login.domain.Permission;
import com.avenida.banten.login.domain.User;
import com.avenida.banten.login.domain.UserRepository;

/** Creates a sample login account for development.
 *
 * @author waabox (waabox[at]gmail[dot]com)
 */
public class SampleLoginAccount
  implements ApplicationListener<ApplicationEvent> {

  /** The user repository. */
  @Autowired private UserRepository userRepository;

  /** The platform transaction. */
  @Autowired private Transaction transaction;

  @Override
  public void onApplicationEvent(final ApplicationEvent event) {

    if (!(event instanceof ApplicationReadyEvent)) {
      return;
    }

    try {
      transaction.start();
      userRepository.save(new User("root@banten.org", "root",
          new HashSet<Permission>()));
      userRepository.save(new User("root2@banten.org", "root",
          new HashSet<Permission>()));
      userRepository.save(new User("root3@banten.org", "root",
          new HashSet<Permission>()));
      transaction.commit();
    } finally {
      transaction.cleanup();
    }

  }

}
