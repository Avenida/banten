package com.avenida.banten.emailsender;

/** Email gateway that abstracts different sender implementations, like
 * AWS or standard java smtp.
 */
public interface EmailGateway {

  /** Sends the given email.
   *
   * WARNING: this interface does not provide any information on the cause
   * of the failure if sending the mail failed.
   *
   * @param email the email to send, cannot be null.
   *
   * @return true if the given email was delivered.
   */
  boolean send(final Email email);
}

