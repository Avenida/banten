package com.avenida.banten.hazelcast;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import com.avenida.banten.core.BantenApplication;
import com.hazelcast.core.HazelcastInstance;

/** Just a sample hazelcast application.
 *
 * @author waabox (emi[at]avenida[dot]com)
 */
public class HazelcastSampleApplication extends BantenApplication {

  public HazelcastSampleApplication() {
    super(
        HazelcastModule.class
    );
  }

  @Autowired
  @Bean public SampleCacheableBean sampleCacheableBean(
      final HazelcastInstance hz) {
    return new SampleCacheableBean(hz);
  }

}
