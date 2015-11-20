package com.avenida.banten.core.database;

import java.util.Arrays;
import java.util.List;

import com.avenida.banten.core.*;

public class DatabaseTestModule implements Module {

  @Override
  public String getName() {
    return "database-test-module";
  }

  @Override
  public String getNamespace() {
    return null;
  }

  @Override
  public Class<?> getPrivateConfiguration() {
    return null;
  }

  @Override
  public Class<?> getPublicConfiguration() {
    return DatabaseTestModuleConfiguration.class;
  }

  /** {@inheritDoc}.*/
  @Override
  public List<Weblet> getWeblets() {
    return null;
  }

  /** {@inheritDoc}.*/
  @Override
  public void init(final ModuleApiRegistry registry) {
    HibernateConfigurationApi api;
    api = registry.get(HibernateConfigurationApi.class);
    api.persistenceUnits(
        Arrays.asList(
            new PersistenceUnit(MockEntity.class),
            new PersistenceUnit(MockEntityWithCollectionWithTuplizers.class),
            new PersistenceUnit(MockEntityWithTuplizer.class,
                MockEntityWithTuplizerFactory.class)
    ));
  }

  @Override
  public ConfigurationApi getConfigurationApi() {
    return null;
  }

}
