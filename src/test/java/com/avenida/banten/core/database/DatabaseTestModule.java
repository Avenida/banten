package com.avenida.banten.core.database;

import java.util.LinkedList;
import java.util.List;

import com.avenida.banten.core.Module;
import com.avenida.banten.core.PersistenceUnit;

public class DatabaseTestModule implements Module {

  @Override
  public String getName() {
    return "database-test-module";
  }

  @Override
  public String getUrlMapping() {
    return null;
  }

  @Override
  public Class<?> getMvcConfiguration() {
    return null;
  }

  @Override
  public Class<?> getModuleConfiguration() {
    return DatabaseTestModuleConfiguration.class;
  }

  @Override
  public List<PersistenceUnit> getPersistenceUnits() {
    List<PersistenceUnit> list = new LinkedList<>();
    list.add(new PersistenceUnit(MockEntity.class));
    list.add(new PersistenceUnit(MockEntityWithTuplizer.class,
        MockEntityWithTuplizerFactory.class));
    list.add(new PersistenceUnit(MockEntityWithCollectionWithTuplizers.class));
    return list;
  }

}
