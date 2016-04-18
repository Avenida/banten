package com.avenida.banten.core.web.menu;

import java.util.Arrays;

import com.avenida.banten.core.*;
import com.avenida.banten.core.web.WebConfigurationApi;

/** The Menu module.
 *
 * @author waabox (emi[at]avenida[dot]com)
 */
public class MenuModule implements Module {

  /** {@inheritDoc}.*/
  @Override
  public String getName() {
    return "Menu-Module";
  }

  /** {@inheritDoc}.*/
  @Override
  public String getNamespace() {
    return "banten-menu";
  }

  /** {@inheritDoc}.*/
  @Override
  public String getRelativePath() {
    return "../banten-core";
  }

  /** {@inheritDoc}.*/
  @Override
  public Class<?> getPrivateConfiguration() {
    return MenuMvc.class;
  }

  /** {@inheritDoc}.*/
  @Override
  public Class<?> getPublicConfiguration() {
    return null;
  }

  /** {@inheritDoc}.*/
  @Override
  public void init(final ModuleApiRegistry registry) {
    registry.get(WebConfigurationApi.class)
      .addWeblets(
        Arrays.asList(
            new Weblet("menu", "menu/index.html")
        ), this);
  }

  /** {@inheritDoc}.*/
  @Override
  public ConfigurationApi getConfigurationApi() {
    return new MenuConfigurationApi();
  }

}
