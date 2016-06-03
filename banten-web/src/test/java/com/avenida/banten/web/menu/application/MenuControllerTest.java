package com.avenida.banten.web.menu.application;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.easymock.EasyMock.*;

import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

import com.avenida.banten.web.menu.Menu;
import com.avenida.banten.web.menu.MenuObjectMother;
import com.avenida.banten.web.menu.MenuSecurityFilter;

public class MenuControllerTest {

  @Test public void index() {

    Menu menu = MenuObjectMother.createFooMenu();
    MenuSecurityFilter filter = createMock(MenuSecurityFilter.class);

    expect(filter.filter(menu)).andReturn(menu);

    replay(filter);

    MenuController controller = new MenuController(menu, filter);

    ModelAndView mav = controller.index();

    assertThat((Menu) mav.getModelMap().get("menu"), is(menu));

    verify(filter);
  }

}
