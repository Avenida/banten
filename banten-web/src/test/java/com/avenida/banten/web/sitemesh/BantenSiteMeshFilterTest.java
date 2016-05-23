package com.avenida.banten.web.sitemesh;

import static org.junit.Assert.*;

import org.junit.Test;

import com.avenida.banten.web.freemarker.FreeMarkerConfigurer;

public class BantenSiteMeshFilterTest {

  @Test
  public void test() throws Exception {
    BantenSitemeshDecoratorSelector selector =
        new BantenSitemeshDecoratorSelector();
    FreeMarkerConfigurer cfg = new FreeMarkerConfigurer(
        true, "../banten-web", "BantenSiteMeshFilter");
    BantenSiteMeshFilter filter =
        new BantenSiteMeshFilter(false, cfg, selector);

    assertNotNull(filter.setup());
  }

}
