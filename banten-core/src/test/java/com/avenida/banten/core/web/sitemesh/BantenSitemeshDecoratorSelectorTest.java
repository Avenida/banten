package com.avenida.banten.core.web.sitemesh;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.easymock.EasyMock.*;

import org.junit.Before;
import org.junit.Test;
import org.sitemesh.DecoratorSelector;
import org.sitemesh.content.Content;
import org.sitemesh.webapp.WebAppContext;
import org.springframework.http.MediaType;

public class BantenSitemeshDecoratorSelectorTest {

  DecoratorSelector<WebAppContext> parent;
  Content content;
  WebAppContext context;

  @Before public void before() {
    parent = createMock(DecoratorSelector.class);
    content = createMock(Content.class);
    context = createMock(WebAppContext.class);
  }

  @Test public void selectDecoratorPaths_isAjax() throws Exception {

    expect(context.getContentType()).andReturn(
        MediaType.APPLICATION_JSON_VALUE);

    replay(parent, content, context);

    BantenSitemeshDecoratorSelector selector;
    selector = new BantenSitemeshDecoratorSelector(parent);
    String[] out = selector.selectDecoratorPaths(content, context);

    assertThat(out.length, is(0));

    verify(parent, content, context);
  }

  @Test public void selectDecoratorPaths_isModal() throws Exception {

    expect(context.getContentType()).andReturn(
        MediaType.TEXT_HTML_VALUE);

    expect(context.getPath()).andReturn("/modal/hours.html").anyTimes();

    replay(parent, content, context);

    BantenSitemeshDecoratorSelector selector;
    selector = new BantenSitemeshDecoratorSelector(parent);
    String[] out = selector.selectDecoratorPaths(content, context);

    assertThat(out.length, is(0));

    verify(parent, content, context);
  }

  @Test public void selectDecoratorPaths_isApi() throws Exception {

    expect(context.getContentType()).andReturn(
        MediaType.TEXT_HTML_VALUE);

    expect(context.getPath()).andReturn("/api/hours.json").anyTimes();

    replay(parent, content, context);

    BantenSitemeshDecoratorSelector selector;
    selector = new BantenSitemeshDecoratorSelector(parent);
    String[] out = selector.selectDecoratorPaths(content, context);

    assertThat(out.length, is(0));

    verify(parent, content, context);
  }

  @Test public void selectDecoratorPaths_decorate() throws Exception {

    expect(context.getContentType()).andReturn(
        MediaType.TEXT_HTML_VALUE);
    expect(context.getPath()).andReturn("/hours.html").anyTimes();

    expect(parent.selectDecoratorPaths(content, context))
      .andReturn(new String[] {"foo"});

    replay(parent, content, context);

    BantenSitemeshDecoratorSelector selector;
    selector = new BantenSitemeshDecoratorSelector(parent);
    String[] out = selector.selectDecoratorPaths(content, context);

    assertThat(out.length, is(1));

    verify(parent, content, context);
  }

}
