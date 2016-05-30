package com.avenida.banten.web.menu;

import java.util.Arrays;

import com.avenida.banten.core.*;
import com.avenida.banten.web.WebAppConfigurationApi;
import com.avenida.banten.web.Weblet;

/** The Menu module.
 *
 * @author waabox (waabox[at]gmail[dot]com)
 */
public class MenuModule implements WebModule {

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
    return "../banten-web";
  }

  /** {@inheritDoc}.*/
  @Override
  public Class<?> getMvcConfiguration() {
    return MenuMvc.class;
  }

  /** {@inheritDoc}.*/
  @Override
  public Class<?> getPublicConfiguration() {
    return null;
  }

  /** {@inheritDoc}.*/
  @Override
  public void init(final ConfigurationApiRegistry registry) {
    registry.get(WebAppConfigurationApi.class)
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
