package com.avenida.hazelcast.domain;

import com.hazelcast.client.*;
import com.hazelcast.client.config.*;
import com.hazelcast.config.*;
import com.hazelcast.core.*;

/** Wrapper that configures Hazelcast.
 *
 * @author waabox (emi[at]avenida[dot]com)
 */
public class HazelcastClientFactory {

  /** The AWS access key. */
  private final String awsAccessKey;

  /** The AWS secret key. */
  private final String awsSecretKey;

  /** The AWS region. */
  private final String awsRegion;

  /** The AWS tag. */
  private final String awsTag;

  /** Whether or not is running with a local instance.*/
  private final boolean local;

  /** Creates a local Hazelcast client.*/
  public HazelcastClientFactory() {
    awsAccessKey = null;
    awsSecretKey = null;
    awsRegion = null;
    awsTag = null;
    local = false;
  }

  /** Creates a new instance of the Hazelcast client for AWS.
   * @param accessKey the AWS access key.
   * @param secretKey the AWS secret key.
   * @param region the AWS region.
   * @param tag the AWS tag.
   */
  public HazelcastClientFactory(final String accessKey, final String secretKey,
      final String region, final String tag) {
    awsAccessKey = accessKey;
    awsSecretKey = secretKey;
    awsRegion = region;
    awsTag = tag;
    local = false;
  }

  /** Creates a new instance of the Hazelcast client.
   * @return the hazelcast client, never null.
   */
  public HazelcastInstance get() {
    return HazelcastClient.newHazelcastClient(cfg());
  }

  /** Configuration for hazelcast client.
   * @return the hazelcast client config.
   */
  public ClientConfig cfg() {
    ClientConfig config = new ClientConfig();

    if (local) {
      ClientAwsConfig awsConfig = new ClientAwsConfig();
      awsConfig.setAccessKey(awsAccessKey);
      awsConfig.setSecretKey(awsSecretKey);
      awsConfig.setRegion(awsRegion);
      awsConfig.setTagKey("Cluster");
      awsConfig.setTagValue(awsTag);
      awsConfig.setEnabled(true);

      awsConfig.setHostHeader("ec2.amazonaws.com");
      awsConfig.setInsideAws(true);
      config.getNetworkConfig().setAwsConfig(awsConfig);

    } else {
      config.getNetworkConfig().addAddress("127.0.0.1:5701");
    }

    GroupConfig gc = new GroupConfig("default", "password");
    config.setGroupConfig(gc);

    return config;
  }

}
