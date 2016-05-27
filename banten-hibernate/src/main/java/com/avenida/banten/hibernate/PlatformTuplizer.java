package com.avenida.banten.hibernate;

import java.util.*;

import org.hibernate.bytecode.spi.ReflectionOptimizer;
import org.hibernate.mapping.PersistentClass;

import org.hibernate.tuple.*;
import org.hibernate.tuple.entity.*;

import org.springframework.beans.DirectFieldAccessor;

import com.avenida.banten.core.*;

/** Platform Tuplizer that use the Factories declared within
 * the PersistenceUnit.
 *
 * @author waabox (waabox[at]gmail[dot]com)
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

  /** {@inheritDoc}.*/
  @Override
  protected Instantiator buildInstantiator(
      final EntityMetamodel entityMetamodel,
      final PersistentClass persistentClass) {
    Instantiator instantiator;
    instantiator = super.buildInstantiator(entityMetamodel, persistentClass);
    try {
      return new BantenInstantiator(persistentClass, instantiator);
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  /** Instantiator that serch within the PersistenceUnits the ones that
   * has been declared a Factory and use it as Instantiator.
   *
   * @author waabox (waabox[at]gmail[dot]com)
   */
  public static class BantenInstantiator extends PojoInstantiator {

    /** The serial version. */
    private static final long serialVersionUID = 1L;

    /** The Hibernate's persistent class, it's never null. */
    private final PersistentClass persistentClass;

    /** Creates a new instance of the instantiator.
     * @param aPersistentClass the {@link PersistentClass}, cannot be null.
     * @param instantiator the {@link Instantiator}, cannot be null.
     * @throws ClassNotFoundException
     */
    public BantenInstantiator(final PersistentClass aPersistentClass,
        final Instantiator instantiator) throws ClassNotFoundException {
      super(
          Class.forName(aPersistentClass.getClassName()),
          optimizer(instantiator),
          aPersistentClass.hasEmbeddedIdentifier()
      );
      persistentClass = aPersistentClass;
    }

    /** Extract the InstantiationOptimizer from the given {@link Instantiator}.
     * @param instantiator the {@link Instantiator}, cannot be null.
     * @return the {@link Instantiator} or null.
     */
    private static ReflectionOptimizer.InstantiationOptimizer optimizer(
        final Instantiator instantiator) {
      return (ReflectionOptimizer.InstantiationOptimizer)
          new DirectFieldAccessor(instantiator).getPropertyValue("optimizer");
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
          persistenceUnits = HibernateConfigurationApi.getPersistenceUnits();
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
