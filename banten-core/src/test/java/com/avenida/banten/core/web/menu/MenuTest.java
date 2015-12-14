package com.avenida.banten.core.web.menu;

import org.junit.Test;

public class MenuTest {

  @Test public void test() {

    new MenuConfigurationApi()

      .root("Admin", "/Admin")
      .root("Time", "/Time")

      .node("Users-Admin", "/users/admin.html", "/Admin/users")
      .node("Time-Admin", "/Time/admin.html", "/Admin/time")
      .node("Time View", "/Time/view.html", "/Time")
      .node("Time View 2", "/Time/view.html", "/Time/view")

    .init();

    Menu m = MenuConfigurationApi.get();
    System.out.println(m.toString());

  }

}
