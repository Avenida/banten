package com.avenida.banten.core.web;

import junit.framework.TestCase;

public class MenuBarTest extends TestCase {
  public void testConstructor() {
    MenuBar menuBar = new MenuBar("root", "root");

    assertNull(menuBar.getParent());
    assertNull(menuBar.getToolTip());
  }
}