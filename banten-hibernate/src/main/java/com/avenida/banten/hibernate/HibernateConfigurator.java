package com.avenida.banten.hibernate;

import org.slf4j.Logger;
import static org.slf4j.LoggerFactory.*;

import java.util.*;
import java.util.Map.*;

import org.apache.tomcat.jdbc.pool.PoolConfiguration;
import org.apache.tomcat.jdbc.pool.PoolProperties;

import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;

import com.avenida.banten.core.Configurator;
import com.avenida.banten.core.PersistenceUnit;

/** Hibernate configurator.
 * @author waabox (emi[at]avenida[dot]com)
 */
class HibernateConfigurator extends Configurator {

  /** The log. */
  private static Logger log = getLogger(HibernateConfigurator.class);

  /** Creates a new instance of the configurator.
   * @param environment the spring environment.
   */
  HibernateConfigurator(final Environment environment) {
    super("dbHibernate", environment);
  }

  /** Configure the Hibernate's properties.
   * @param builder the session factory builder.
   * @param persistenceUnitList the list of persistence units.
   * @return the local session factory builder, never null.
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  LocalSessionFactoryBuilder configure(
      final LocalSessionFactoryBuilder builder,
      final List<PersistenceUnit> persistenceUnitList) {
    log.info("Configuring Hibernate");
    Properties properties = new Properties();
    properties.putAll(get());
    if(log.isDebugEnabled()) {
      log.debug("Hibernate properties");
      Set<Entry<Object, Object>> entries = properties.entrySet();
      for (Iterator iterator = entries.iterator(); iterator.hasNext();) {
        Entry<Object, Object> entry = (Entry<Object, Object>) iterator.next();
        log.debug("{}, {}", entry.getKey(), entry.getValue());
      }
    }
    builder.addProperties(properties);
    for(PersistenceUnit pu : persistenceUnitList) {
      log.info("Adding persistence class:[{}]", pu.getPersistenceClass());
      builder.addAnnotatedClass(pu.getPersistenceClass());
    }
    return builder;
  }

  /** Creates the pool configuration.
   * @return the pool configuration.
   */
  PoolConfiguration buildPoolProperties() {
    PoolProperties props = new PoolProperties();

    props.setUrl(get("db.url"));
    props.setPassword(get("db.password"));
    props.setUsername(get("db.user"));
    props.setDriverClassName(get("db.driver"));

    boolean poolActive = getBoolean("db.pool.active");

    if (poolActive) {
      props.setMaxActive(getInt("db.pool.maxActive"));
      props.setInitialSize(getInt("db.pool.initialSize"));
      props.setMaxAge(getInt("db.pool.maxAge"));
      props.setMaxWait(getInt("db.pool.maxWait"));
      props.setLogAbandoned(getBoolean("db.pool.logAbandoned"));
      props.setMinIdle(getInt("db.pool.minIdle"));
      props.setMaxIdle(getInt("db.pool.maxIdle"));
      props.setTestWhileIdle(getBoolean("db.pool.testWhileIdle"));
      props.setValidationQuery(get("db.pool.validationQuery"));
      props.setMinEvictableIdleTimeMillis(
          getInt("db.pool.minEvictableTimeMilis"));
    }

    return props;
  }
}
