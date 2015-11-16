package com.avenida.banten.core.web;

import org.junit.Test;
import static org.junit.Assert.*;

public class MenuBarTest {

  @Test public void testConstructor() {
    MenuBar menuBar = new MenuBar("root", "root");
    assertNull(menuBar.getParent());
    assertNull(menuBar.getToolTip());
  }
}