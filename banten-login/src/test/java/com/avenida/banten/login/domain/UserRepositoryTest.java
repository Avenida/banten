package com.avenida.banten.login.domain;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.HashSet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.avenida.banten.hibernate.Transaction;
import com.avenida.banten.testsupport.BantenTest;

@RunWith(SpringJUnit4ClassRunner.class)
@BantenTest(applicationClass = SampleLoginApplication.class)
public class UserRepositoryTest {

  @Autowired private UserRepository repository;

  @Autowired private Transaction transaction;

  @Test
  public void testSave() {
    transaction.start();

    repository.save(new User("me@waabox.org", "waabox",
        new HashSet<Role>()));

    repository.save(new Role("test", "a simple role"));

    transaction.commit();

    transaction.start();
    User user = repository.getByEmail("me@waabox.org");
    assertThat(user, notNullValue());

    user.assignRole(repository.getRoleByName("test"));

    transaction.commit();

    transaction.start();
    user = repository.getByEmail("me@waabox.org");
    assertThat(user, notNullValue());
    assertThat(user.getRoles().iterator().next().getName(), is("test"));

    user.unassignRole((user.getRoles().iterator().next()));
    assertThat(user.getRoles().size(), is(0));
    transaction.commit();

  }

}
