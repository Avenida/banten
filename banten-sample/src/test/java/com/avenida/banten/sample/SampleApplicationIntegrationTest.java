package com.avenida.banten.sample;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.avenida.banten.testsupport.BantenTest;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

@RunWith(SpringJUnit4ClassRunner.class)
@BantenTest(applicationClass = SampleApplication.class)
@WebIntegrationTest
public class SampleApplicationIntegrationTest {

  @Test public void testTime() throws Exception {
    // view the page.
    try (WebClient webClient = new WebClient()) {
      HtmlPage page = webClient.getPage(
        "http://localhost:8080/time/time/view.html");
      String xmlPage = page.asXml();

      assertTrue(xmlPage.contains("Chuck Norris CAN get bacon from a cow"));
      assertTrue(xmlPage.contains("Timezones"));
    }

    // post to the page a new GTM value.
    try (WebClient webClient = new WebClient()) {
      HtmlPage page = webClient.getPage(
        "http://localhost:8080/time/time/view.html");
      HtmlForm form = page.getForms().get(0);
      form.getInputByName("gmt").setValueAttribute("GMT+3");

      HtmlPage result = form.getButtonByName("submit-btn").click();
      String xmlPage = result.asXml();

      // verify that within the new page contains the posted value.
      assertTrue(xmlPage.contains("GMT+3"));
    }

  }

}
