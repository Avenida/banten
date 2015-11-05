package com.avenida.banten.core.database;

import java.util.*;

import org.hibernate.bytecode.spi.ReflectionOptimizer;

import org.hibernate.mapping.PersistentClass;

import org.hibernate.metamodel.binding.EntityBinding;

import org.hibernate.tuple.*;
import org.hibernate.tuple.entity.*;

import org.springframework.beans.DirectFieldAccessor;

import com.avenida.banten.core.*;

/** Platform Tuplizer that use the Factories declared within
 * the PersistenceUnit.
 *
 * @author waabox (emi[at]avenida[dot]com)
 */
public class PlatformTuplizer extends PojoEntityTuplizer {

  /** The reflection optimizer. */
  private ReflectionOptimizer optimizer;

  /** Creates a new instance of the Tuplizer.
   * @param entityMetamodel the entity model.
   * @param mappedEntity the mapped entity.
   */
  public PlatformTuplizer(final EntityMetamodel entityMetamodel,
      final PersistentClass mappedEntity) {
    super(entityMetamodel, mappedEntity);
    postConstruct();
  }

  /** Creates a new instance of the Tuplizer.
   * @param entityMetamodel the entity model.
   * @param mappedEntity the mapped entity.
   */
  public PlatformTuplizer(final EntityMetamodel entityMetamodel,
      final EntityBinding mappedEntity) {
    super(entityMetamodel, mappedEntity);
    postConstruct();
  }

  /** Hack, check if there are a reflection optimizer within the parent...
   * using reflection.
   */
  private void postConstruct() {
    DirectFieldAccessor dfa = new DirectFieldAccessor(this);
    optimizer = (ReflectionOptimizer) dfa.getPropertyValue("optimizer");
  }

  /** {@inheritDoc}.*/
  @Override
  protected Instantiator buildInstantiator(
      final PersistentClass persistentClass) {
    if (optimizer == null) {
      return new BantenInstantiator(persistentClass);
    }
    return new BantenInstantiator(persistentClass, optimizer);
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
     * @param optimizer the reflection optimizer.
     */
    public BantenInstantiator(final PersistentClass aPersistentClass,
        final ReflectionOptimizer optimizer) {
      super(aPersistentClass, optimizer.getInstantiationOptimizer());
      persistentClass = aPersistentClass;
    }

    /** Creates a new instance of the Instantiator.
     * @param aPersistentClass the Hibernate's persistent class.
     */
    public BantenInstantiator(final PersistentClass aPersistentClass) {
      super(aPersistentClass, null);
      persistentClass = aPersistentClass;
    }

    /** {@inheritDoc}.*/
    @SuppressWarnings("rawtypes")
    @Override
    public Object instantiate() {
      Class<? extends Factory> factoryClass = FactoryCache.get(persistentClass);
      if(factoryClass != null) {
        return ModuleServiceLocator.getBean(factoryClass).create();
      }
      return super.instantiate();
    }
  }

  /** Cache for the Factory classes.*/
  @SuppressWarnings("rawtypes")
  private static class FactoryCache {

    /** The instance. */
    private static FactoryCache instance = new FactoryCache();

    /** Whether or not has been initialized. */
    private static Boolean initialized = false;

    /** The cache. */
    private Map<String, Class<? extends Factory>> factories = new HashMap<>();

    /** Retrieves the factory class by its class name.
     * @param name the class name.
     * @return the factory if exists.
     */
    public static Class<? extends Factory> get(final PersistentClass pc) {
      if (!initialized) {
        synchronized (initialized) {
          List<PersistenceUnit> persistenceUnits;
          persistenceUnits = ModuleServiceLocator.getBean(
              "persistenceUnitList");
          for(PersistenceUnit pu : persistenceUnits) {
            instance.factories.put(
                pu.getPersistenceClass().getName(), pu.getFactory());
          }
          initialized = true;
        }
      }
      return instance.factories.get(pc.getClassName());
    }
  }
}
