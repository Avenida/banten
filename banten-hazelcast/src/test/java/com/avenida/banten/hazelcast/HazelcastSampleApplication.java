package com.avenida.banten.hazelcast;

import org.springframework.context.annotation.Bean;

import com.avenida.banten.core.BantenApplication;
import com.avenida.banten.core.ConfigurationApiRegistry;
import com.avenida.banten.core.Bootstrap;
import com.hazelcast.core.HazelcastInstance;

/** Just a sample hazelcast application.
 *
 * @author waabox (waabox[at]gmail[dot]com)
 */
public class HazelcastSampleApplication extends BantenApplication {

  /** {@inheritDoc}.*/
  protected Bootstrap bootstrap() {
    return new Bootstrap(HazelcastModule.class);
  }

  @Bean public SampleCacheableBean sampleCacheableBean(
      final HazelcastInstance hz) {
    return new SampleCacheableBean(hz);
  }

  @Override
  public void init(final ConfigurationApiRegistry registry) {
  }

}
