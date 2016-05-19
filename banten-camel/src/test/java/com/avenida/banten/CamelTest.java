package com.avenida.banten;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.avenida.banten.camel.MessageRequest;
import com.avenida.banten.camel.MessageResponse;
import com.avenida.banten.camel.SampleCamelApplication;
import com.avenida.banten.camel.ServiceBus;
import com.avenida.banten.testsupport.BantenTest;

/**
 * @author waabox (emi[at]avenida[dot]com)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@BantenTest(applicationClass = SampleCamelApplication.class)
public class CamelTest {

  @Autowired private ServiceBus serviceBus;

  @Test public void testEventBus() {
    MessageResponse response;
    response = serviceBus.request(
        new MessageRequest("direct:hello", "just a word"));

    String theResponse = (String) response.response();

    assertThat(response.isFailed(), is(false));
    assertThat(theResponse, is("just a word"));
  }

}
