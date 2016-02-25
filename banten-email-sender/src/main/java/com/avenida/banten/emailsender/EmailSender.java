package com.avenida.banten.emailsender;

import java.io.IOException;
import java.util.Map;
import java.util.TimeZone;

import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.*;
import org.apache.commons.lang3.Validate;

/** The email sender.
 */
public class EmailSender {

  /** The Freemarker's configuration, it's never null. */
  private final Configuration freemarkerConfiguration;

  /** The email gateway. */
  private EmailGateway gateway;

  /** Creates a new instance of the email sender.
   *
   * @param theGateway the mail gateway, that abstracts how a mail is sent
   * through a specific provider. It cannot be null.
   *
   * @param templateLoader the template loader, cannot be null.
   */
  public EmailSender(final EmailGateway theGateway) {

    Validate.notNull(theGateway, "The email gateway cannot be null");
    gateway = theGateway;

    // Configures freemarker to load all mail templates from the root of the
    // current class classloader.
    TemplateLoader templateLoader = new ClassTemplateLoader(getClass(), "/");

    Validate.notNull(theGateway, "The mail gateway cannot be null");
    freemarkerConfiguration = new Configuration(Configuration.VERSION_2_3_21);
    freemarkerConfiguration.setTemplateLoader(templateLoader);
    freemarkerConfiguration.setDefaultEncoding("UTF-8");
    freemarkerConfiguration.setTemplateExceptionHandler(
        TemplateExceptionHandler.RETHROW_HANDLER);
    freemarkerConfiguration.setTimeZone(
        TimeZone.getTimeZone("America/Argentina/Buenos_Aires"));
  }

  /** Creates a new email.
   *
   * @param template the freemarker template full path in the class loader. It
   * cannot be null.
   *
   * @param model the model used to expand the template. It cannot be null.
   *
   * @return the new email. Never returns null.
   */
  public Email newMail(final String template, final Map<String, Object> model) {
    return new Email(this, template, model);
  }

  /** Retrieves the template given by its name.
   * @param name the template name.
   * @return the template.
   */
  Template getTemplate(final String name) {
    try {
      return freemarkerConfiguration.getTemplate(name);
    } catch (IOException e1) {
      throw new RuntimeException(e1);
    }
  }

  /** Sends the given email.
   * @param email the email to send, cannot be null.
   * @return true if the given email was delivered.
   */
  boolean send(final Email email) {
    return gateway.send(email);
  }
}

