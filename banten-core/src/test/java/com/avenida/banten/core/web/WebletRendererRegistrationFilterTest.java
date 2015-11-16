package com.avenida.banten.core.web;

import static org.easymock.EasyMock.*;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;

public class WebletRendererRegistrationFilterTest {

  @Test public void doFilter() throws Exception {
    HttpServletRequest request = createMock(HttpServletRequest.class);
    HttpServletResponse response = createMock(HttpServletResponse.class);
    FilterChain chain = createMock(FilterChain.class);

    WebletRenderer renderer = createMock(WebletRenderer.class);

    request.setAttribute(
        WebletRendererRegistrationFilter.WEBLET_RENDERER_PARAMETER, renderer);

    chain.doFilter(request, response);

    replay(request,response, chain, renderer);

    WebletRendererRegistrationFilter filter;
    filter = new WebletRendererRegistrationFilter(renderer);

    filter.doFilter(request, response, chain);

    verify(request,response, chain, renderer);

  }

}
