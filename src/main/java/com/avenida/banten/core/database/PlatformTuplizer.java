package com.avenida.banten.core.database;

import java.util.List;

import org.hibernate.mapping.PersistentClass;
import org.hibernate.metamodel.binding.EntityBinding;

import org.hibernate.tuple.*;
import org.hibernate.tuple.entity.*;

import com.avenida.banten.core.*;

/** Platform Tuplizer that use the Factories declared within
 * the PersistenceUnit.
 *
 * @author waabox (emi[at]avenida[dot]com)
 */
public class PlatformTuplizer extends PojoEntityTuplizer {

  /** Creates a new instance of the Tuplizer.
   * @param entityMetamodel the entity model.
   * @param mappedEntity the mapped entity.
   */
  public PlatformTuplizer(final EntityMetamodel entityMetamodel,
      final PersistentClass mappedEntity) {
    super(entityMetamodel, mappedEntity);
  }

  /** Creates a new instance of the Tuplizer.
   * @param entityMetamodel the entity model.
   * @param mappedEntity the mapped entity.
   */
  public PlatformTuplizer(final EntityMetamodel entityMetamodel,
      final EntityBinding mappedEntity) {
    super(entityMetamodel, mappedEntity);
  }

  /** {@inheritDoc}.*/
  @Override
  protected Instantiator buildInstantiator(
      final PersistentClass persistentClass) {
    return new BantenInstantiator(persistentClass);
  }

  /** Instantiator that serch within the PersistenceUnits the ones that
   * has been declared a Factory and use it as Instantiator.
   *
   * @author waabox (emi[at]avenida[dot]com)
   */
  public static class BantenInstantiator extends PojoInstantiator {

    /** The serial version. */
    private static final long serialVersionUID = 1L;

    /** The Hibernate's persistent class, it's never null. */
    private final PersistentClass persistentClass;

    /** Creates a new instance of the Instantiator.
     * @param aPersistentClass the Hibernate's persistent class.
     */
    public BantenInstantiator(final PersistentClass aPersistentClass) {
      super(aPersistentClass, null); // check the optimizer!
      persistentClass = aPersistentClass;
    }

    /** {@inheritDoc}.*/
    @Override
    public Object instantiate() {
      List<PersistenceUnit> persistenceUnits;
      persistenceUnits = ModuleServiceLocator.getBean("persistenceUnitList");
      // TODO [waabox] I do really need to cache this.
      for(PersistenceUnit pu : persistenceUnits) {
        if (pu.getPersistenceClass().getName().equals(
            persistentClass.getClassName())) {
          return ModuleServiceLocator.getBean(pu.getFactory()).create();
        }
      }
      return super.instantiate();
    }
  }

}
