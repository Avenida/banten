package com.avenida.hazelcast.config;

import static org.slf4j.LoggerFactory.getLogger;

import org.slf4j.Logger;

import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

import com.avenida.hazelcast.domain.HazelcastClientFactory;
import com.hazelcast.core.HazelcastInstance;

/** The configuration.
 * @author waabox (emi[at]avenida[dot]com)
 */
public class HazelcastWebConsoleConfiguration implements EnvironmentAware {

  /** The log. */
  private static Logger log = getLogger(HazelcastWebConsoleConfiguration.class);

  /** The spring environment. */
  private Environment environment;

  /** Retrieves the hazelcast instance.
   * @return the hazelcast instance.
   */
  @Bean public HazelcastInstance hazelcastInstance() {
    if(!environment.getProperty("cloud", Boolean.class)) {
      log.error("Starting hazelcast as local instance");
      return new HazelcastClientFactory().get();
    } else {
      log.debug("Starting hazelcast with cloud configuration");
      return new HazelcastClientFactory(
          environment.getProperty("accessKey"),
          environment.getProperty("secretKey"),
          environment.getProperty("region"),
          environment.getProperty("tag")).get();
    }
  }

  /** {@inheritDoc}.*/
  @Override
  public void setEnvironment(final Environment env) {
    environment = env;
  }

}
