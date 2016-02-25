package com.avenida.banten.emailsender;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** The email sender configuration.
 */
@Configuration
public class EmailSenderConfiguration {

  @Bean(name = "banten.emailsender.gateway")
  public EmailGateway emailGateway(
      @Value("${awsAccessKey}") final String awsAccessKey,
      @Value("${awsSecretKey}") final String awsSecretKey
      ) {
    return new AwsEmailGateway(awsAccessKey, awsSecretKey);
  }

  @Bean(name = "banten.emailsender.sender")
  public EmailSender emailSender(final EmailGateway gateway) {
    return new EmailSender(gateway);
  }
}

