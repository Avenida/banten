package com.avenida.banten.emailsender;

import com.avenida.banten.core.*;

/** Hibernate's Module.
 */
public class EmailSenderModule implements Module {

  /** {@inheritDoc}.*/
  @Override
  public String getName() {
    return "Email-Sender-Module";
  }

  /** {@inheritDoc}.*/
  @Override
  public Class<?> getPublicConfiguration() {
    return EmailSenderConfiguration.class;
  }

  @Override
  public void init(final ConfigurationApiRegistry registry) {
  }

  /** {@inheritDoc}.*/
  @Override
  public ConfigurationApi getConfigurationApi() {
    return null;
  }
}

