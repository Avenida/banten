package com.avenida.banten.core.web;

import static org.easymock.EasyMock.*;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import com.avenida.banten.core.ConfigurationApi;
import com.avenida.banten.core.Module;
import com.avenida.banten.core.ModuleApiRegistry;
import com.avenida.banten.core.Weblet;

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

    renderer.render("test", "Sample-Module", request, response);

    verify(request, response);
  }

  @Test(expected = RuntimeException.class)
  public void render_unknownweblet() throws Exception {
    replay(request, response);
    renderer.render("test", "__Sample-Module___", request, response);
    verify(request, response);
  }

  private static class SampleModule implements Module {

    @Override
    public String getName() {
      return "Sample-Module";
    }

    @Override
    public String getNamespace() {
      return "sample";
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
    public List<Weblet> getWeblets() {
      return new LinkedList<>();
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
