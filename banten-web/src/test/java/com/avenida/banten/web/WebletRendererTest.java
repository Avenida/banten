package com.avenida.banten.web;

import static org.easymock.EasyMock.*;

import java.util.Arrays;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import com.avenida.banten.core.ConfigurationApi;
import com.avenida.banten.core.ModuleApiRegistry;
import com.avenida.banten.core.WebModule;
import com.avenida.banten.web.Weblet;
import com.avenida.banten.web.WebletContainer;
import com.avenida.banten.web.WebletRenderer;
import com.avenida.banten.web.WebletResponseWrapper;

public class WebletRendererTest {

  private WebletRenderer renderer;
  private HttpServletRequest request;
  private HttpServletResponse response;


  @Before public void before() {
    WebletContainer.instance().register(
        new SampleModule(),
        Arrays.asList(
            new Weblet("test", "test.html"),
            new Weblet("test2", "test2.html")
        )
    );
    renderer = new WebletRenderer(WebletContainer.instance());

    request = createMock(HttpServletRequest.class);
    response = createNiceMock(HttpServletResponse.class);
  }

  @Test public void render() throws Exception {
    RequestDispatcher rd = createMock(RequestDispatcher.class);
    rd.include(EasyMock.eq(request), isA(WebletResponseWrapper.class));

    expect(request.getRequestDispatcher("/sample/test.html")).andReturn(rd);

    replay(request, response);

    renderer.render("Sample-Module", "test", request, response);

    verify(request, response);
  }

  @Test(expected = RuntimeException.class)
  public void render_unknownweblet() throws Exception {
    replay(request, response);
    renderer.render("test", "__Sample-Module___", request, response);
    verify(request, response);
  }

  private static class SampleModule implements WebModule {

    @Override
    public String getName() {
      return "Sample-Module";
    }

    @Override
    public String getNamespace() {
      return "sample";
    }

    /** {@inheritDoc}.*/
    @Override
    public String getRelativePath() {
      return "../banten-core";
    }

    @Override
    public Class<?> getPrivateConfiguration() {
      return null;
    }

    @Override
    public Class<?> getPublicConfiguration() {
      return null;
    }

    @Override
    public void init(final ModuleApiRegistry registry) {
    }

    @Override
    public ConfigurationApi getConfigurationApi() {
      return null;
    }
  }
}

