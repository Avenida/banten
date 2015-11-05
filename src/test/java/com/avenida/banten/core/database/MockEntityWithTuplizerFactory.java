package com.avenida.banten.core.database;

import com.avenida.banten.core.Factory;

public class MockEntityWithTuplizerFactory
  implements Factory<MockEntityWithTuplizer> {

  @Override
  public MockEntityWithTuplizer create() {
    return new MockEntityWithTuplizer("foo");
  }

}
