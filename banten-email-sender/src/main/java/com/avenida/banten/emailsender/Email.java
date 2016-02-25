package com.avenida.banten.emailsender;

import java.io.StringWriter;
import java.io.Writer;
import java.io.IOException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.StringUtils;

import freemarker.template.Template;
import freemarker.template.TemplateException;

/** Represents an HTML email.
 */
public class Email {

  /** The email sender. */
  private EmailSender sender;

  /** The name of the template, it's never null. */
  private String template;

  /** The sender email, it's never null. */
  private String from;

  /** The list of recipients, it's never null. */
  private List<String> to = new LinkedList<String>();

  /** The email subject. */
  private String subject;

  /** The template model, it's never null. */
  private Map<String, Object> model = new HashMap<String, Object>();

  /** The compiled html code. */
  private String content;

  /** Private constructor.*/
  Email(final EmailSender emailSender, final String theTemplate,
      final Map<String, Object> theModel) {
    sender = emailSender;
    template = theTemplate;
    model = theModel;
  }

  /** Retrieves the template name.
   * @return the template name, never null.
   */
  private String template() {
    return template;
  }

  /** Retrieves the model.
   * @return the model, never null.
   */
  private Map<String, Object> model() {
    return model;
  }

  /** Retrieves the list of recipients.
   * @return the list of recipients.
   */
  public List<String> to() {
    return to;
  }

  /** Adds the recipient email.
   * @param to the recipient email.
   */
  public void to(final String address) {
    Validate.isTrue(to.isEmpty(), "Already called to");
    to = Arrays.asList(address);
  }

  /** Adds the recipients emails.
   * @param to the recipients emails.
   * @return this.
   */
  public void to(final List<String> addresses) {
    Validate.isTrue(to.isEmpty(), "Already called to");
    to.addAll(addresses);
  }

  /** Returns the sender email.
   * @return the sender email.
   */
  public String from() {
    return from;
  }

  /** Adds the email sender.
   * @param address the email sender.
   */
  public void from(final String address) {
    from = address;
  }
  /** Retrieves the email subject.
   * @return the email subject, never null.
   */
  public String subject() {
    return subject;
  }

  /** Adds the subject.
   * @param subject the subject.
   * @return this.
   */
  public void subject(final String theSubject) {
    subject = theSubject;
  }

  /** Retrieves the content.
   * @return the content.
   */
  public String content() {
    return content;
  }

  /** Sends this email.
   *
   * @return true if the email was sent, false in case of error.
   */
  public synchronized boolean send() {
    validate();
    if (content == null) {
      Template ftlTemplate = sender.getTemplate(template());
      Writer out = new StringWriter();
      try {
        ftlTemplate.process(model(), out);
        content = out.toString();
      } catch (TemplateException | IOException e) {
        throw new RuntimeException(e);
      }
    }
    return sender.send(this);
  }

  /** Validates the email.*/
  private void validate() {
    Validate.notNull(subject, "The subject cannot be null");
    Validate.notNull(from, "The sender cannot be null");
    Validate.isTrue(!to.isEmpty(), "The recipients cannot be empty");
    Validate.notNull(template, "The template cannot be null");
  }

  /** {@inheritDoc}.*/
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("to: ").append(StringUtils.join(to, ',')).append("; ");
    sb.append("from: ").append(from).append("; ");
    sb.append("subject: ").append(subject).append("; ");
    sb.append("content: ").append(content);
    return sb.toString();
  }
}
