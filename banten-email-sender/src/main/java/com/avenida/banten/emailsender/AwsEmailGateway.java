package com.avenida.banten.emailsender;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClient;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;

/** Email sender gateway implementation that uses AWS client.
 */
public class AwsEmailGateway implements EmailGateway {

  /** The class logger. */
  private static Logger log = LoggerFactory.getLogger(AwsEmailGateway.class);

  /** The AWS credentials. */
  private final BasicAWSCredentials credentials;

  /** Creates a new instance of the email gateway.
   * @param awsAccessKey the access key.
   * @param awsSecretKey the secret.
   */
  public AwsEmailGateway(final String awsAccessKey,
      final String awsSecretKey) {
    Validate.notNull(awsAccessKey, "The aws access key cannot be null");
    Validate.notNull(awsSecretKey, "The aws secret key cannot be null");
    credentials = new BasicAWSCredentials(awsAccessKey, awsSecretKey);
  }

  /** {@inheritDoc}.*/
  @Override
  public boolean send(final Email email) {

    log.trace("Sending email: {} to: {}", email.subject(), email.to());

    Destination dest = new Destination().withToAddresses(email.to());
    Content subject = new Content().withData(email.subject());
    Body body = new Body().withHtml(new Content().withData(email.content()));
    Message message = new Message().withSubject(subject).withBody(body);

    SendEmailRequest request = new SendEmailRequest();
    request.withSource(email.from());
    request.withDestination(dest);
    request.withMessage(message);

    try {
      AmazonSimpleEmailServiceClient client;
      client = new AmazonSimpleEmailServiceClient(credentials);

      // TODO: the region should be externalized.
      Region region = Region.getRegion(Regions.US_EAST_1);
      client.setRegion(region);
      client.sendEmail(request);
      return true;
    } catch (Exception e) {
      log.error("Error sending message with subject " + email.subject(), e);
      return false;
    }
  }
}

