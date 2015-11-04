package com.avenida.banten.core.database;

import com.avenida.banten.core.Factory;

public class MockEntityWithTuplizerFactory implements Factory {

  @Override
  public Object create() {
    return new MockEntityWithTuplizer("foo");
  }

}
