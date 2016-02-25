package com.avenida.banten.emailsender;

import java.util.List;

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
  public String getNamespace() {
    return null;
  }

  /** {@inheritDoc}.*/
  @Override
  public String getRelativePath() {
    return "../banten-email-sender";
  }

  /** {@inheritDoc}.*/
  @Override
  public Class<?> getPrivateConfiguration() {
    return null;
  }

  /** {@inheritDoc}.*/
  @Override
  public Class<?> getPublicConfiguration() {
    return EmailSenderConfiguration.class;
  }

  /** {@inheritDoc}.*/
  @Override
  public List<Weblet> getWeblets() {
    return null;
  }

  @Override
  public void init(final ModuleApiRegistry registry) {
  }

  /** {@inheritDoc}.*/
  @Override
  public ConfigurationApi getConfigurationApi() {
    return null;
  }
}

