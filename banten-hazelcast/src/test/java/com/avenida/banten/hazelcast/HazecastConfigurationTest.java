package com.avenida.banten.hazelcast;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.avenida.banten.testsupport.BantenTest;

@RunWith(SpringJUnit4ClassRunner.class)
@BantenTest(applicationClass = HazelcastSampleApplication.class)
public class HazecastConfigurationTest {

  @Autowired SampleCacheableBean sampleCacheableBean;

  /** Test that spring has been correctly configured the cache.*/
  @Test
  public void test() {
    sampleCacheableBean.call("hey");
    sampleCacheableBean.call("hey");
    sampleCacheableBean.call("hey");
    sampleCacheableBean.call("hey");
    sampleCacheableBean.call("hey");
    sampleCacheableBean.call("hey");

    assertThat(sampleCacheableBean.getCalled(), is(1));


    sampleCacheableBean.call("ho");
    sampleCacheableBean.call("let's go!");

    assertThat(sampleCacheableBean.getCalled(), is(3));

    sampleCacheableBean.call("hey ho, let's go!");

    assertThat(sampleCacheableBean.getCalled(), is(4));

  }

}
