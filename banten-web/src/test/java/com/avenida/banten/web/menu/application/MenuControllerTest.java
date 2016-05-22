package com.avenida.banten.web.menu.application;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.avenida.banten.web.menu.Menu;
import com.avenida.banten.web.menu.application.MenuController;

public class MenuControllerTest {

  @Test
  public void testIndex() {
    MockHttpServletRequest request = new MockHttpServletRequest();
    MenuController controller = new MenuController();
    ModelAndView mav = controller.index(request);
    Menu menu = (Menu) mav.getModelMap().get("menu");
    assertThat(menu, notNullValue());
  }

}
