package com.avenida.banten.sample;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

import com.avenida.banten.hibernate.Transaction;
import com.avenida.banten.login.domain.Role;
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

    if (event.getClass() != ApplicationReadyEvent.class) {
      return;
    }

    try {
      transaction.start();

      Role roleAdmin = userRepository.getRoleByName("admin");
      if (roleAdmin == null) {
        roleAdmin = new Role("admin");
        userRepository.save(roleAdmin);
      }

      Set<Role> rolesForRoot = new HashSet<>();
      rolesForRoot.add(roleAdmin);

      userRepository.save(new User("root@banten.org", "root", rolesForRoot));

      userRepository.save(new User("root2@banten.org", "root",
          new HashSet<Role>()));

      userRepository.save(new User("root3@banten.org", "root",
          new HashSet<Role>()));

      transaction.commit();
    } finally {
      transaction.cleanup();
    }

  }

}
