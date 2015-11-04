package com.avenida.banten.core.database;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.avenida.banten.core.boot.test.BantenTest;

/** Test the Hibernate integration.
 * @author waabox (emi[at]avenida[dot]com)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@BantenTest(factoryClass = DatabaseTestModuleApplicationFactory.class)
public class HibernateTest {

  @Autowired Repository repository;

  @Autowired Transaction tx;

  @Test
  @Transactional
  public void test() {
    repository.save(new MockEntity("foo"));
    MockEntity entity = repository.byName("foo");
    assertThat(entity.getName(), is("foo"));
  }

  @Test
  public void testTuplizer() {
    tx.start();
    repository.save(new MockEntityWithTuplizer("pepe", true));
    tx.commit();
    tx.start();
    List<MockEntityWithTuplizer> mocks = repository.all();
    mocks.get(0).getDependency();
    tx.commit();
  }

}
