package com.avenida.banten.core.database;

import java.util.List;

import org.hibernate.mapping.PersistentClass;
import org.hibernate.metamodel.binding.EntityBinding;
import org.hibernate.tuple.Instantiator;
import org.hibernate.tuple.PojoInstantiator;
import org.hibernate.tuple.entity.EntityMetamodel;
import org.hibernate.tuple.entity.PojoEntityTuplizer;

import com.avenida.banten.core.ModuleServiceLocator;
import com.avenida.banten.core.PersistenceUnit;

/**
 * @author waabox (emi[at]avenida[dot]com)
 */
public class PlatformTuplizer extends PojoEntityTuplizer {

  public PlatformTuplizer(final EntityMetamodel entityMetamodel,
      final PersistentClass mappedEntity) {
    super(entityMetamodel, mappedEntity);
  }

  public PlatformTuplizer(final EntityMetamodel entityMetamodel,
      final EntityBinding mappedEntity) {
    super(entityMetamodel, mappedEntity);
  }

  @Override
  protected Instantiator buildInstantiator(
      final PersistentClass persistentClass) {
    return new BantenInstantiator(persistentClass);
  }

  public static class BantenInstantiator extends PojoInstantiator {

    private static final long serialVersionUID = 1L;

    private PersistentClass klass;

    public BantenInstantiator(final PersistentClass persistentClass) {
      super(persistentClass, null); // check the optimizer!
      klass = persistentClass;
    }

    @Override
    public Object instantiate() {
      List<PersistenceUnit> lt = ModuleServiceLocator.getBean(
          "persistenceUnitList");
      for(PersistenceUnit pu : lt) {
        if (pu.getPersistenceClass().getName().equals(klass.getClassName())) {
          return ModuleServiceLocator.getBean(pu.getFactory()).create();
        }
      }
      return super.instantiate();
    }

  }

}
