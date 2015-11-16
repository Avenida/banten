package com.avenida.banten.core.database;

import java.util.*;

import javax.annotation.Resource;

import org.apache.commons.lang3.Validate;

import org.apache.tomcat.jdbc.pool.*;

import org.hibernate.SessionFactory;

import org.springframework.beans.factory.annotation.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.core.env.Environment;

import org.springframework.orm.hibernate4.*;

import com.avenida.banten.core.*;

/** Hibernate configuration.
 *
 * The property files expect the Hibernate's documentation
 * with a prefix: dbHibernate + <Hibernate's property>
 *
 * [Required]
 * dbHibernate.hibernate.dialect
 *
 * Also, this configuration configures the data-source.
 *
 * [Required]
 * db.url
 * db.password
 * db.user
 * db.driver
 * db.pool.active
 *
 * if the property db.pool.active is true, the configuration muse provide:
 *
 * db.pool.maxActive
 * db.pool.initialSize
 * db.pool.maxAge
 * db.pool.maxWait
 * db.pool.logAbandoned
 * db.pool.minIdle
 * db.pool.maxIdle
 * db.pool.testWhileIdle
 * db.pool.validationQuery
 * db.pool.minEvictableTimeMilis
 *
 * @author waabox (emi[at]avenida[dot]com)
 */
@Configuration
public class HibernateConfiguration {

  /** The list of persistence units, it's never null. */
  @Autowired @Resource(name = "persistenceUnitList")
  private List<PersistenceUnit> persistenceUnitList;

  /** The Spring's environment. */
  @Autowired
  private Environment environment;

  /** The Hibernate's configurator, null until the method getConfigurator()
   * is called. Do not access this field directly,
   * use {@link #getConfigurator()} instead.
   */
  private HibernateConfigurator hibernateConfigurator;

  /** DataSource bean definition.
   * @return the DataSource to be used by the application.
   * */
  @Bean(name = "banten.dataSource")
  public DataSource dataSource() {
    DataSource datasource = new DataSource();
    datasource.setPoolProperties(getConfigurator().buildPoolProperties());
    return datasource;
  }

  /** Defines the transaction manager to use.
   * @param sessionFactory the session factory.
   * @return the Hibernate Transaction manager.
   * */
  @Bean(name = "banten.transactionManager") @Autowired
  public HibernateTransactionManager transactionManager(
      final SessionFactory sessionFactory) {
    HibernateTransactionManager manager = new HibernateTransactionManager();
    manager.setSessionFactory(sessionFactory);
    return manager;
  }

  /** Hibernate's LocalSession Factory.
   * @param dataSource the data source.
   * @return the Hibernate's SessionFactory.
   * */
  @Bean(name = "banten.sessionFactory")
  public SessionFactory bantenSessionFactory() {
    Validate.notNull(persistenceUnitList,  "The list cannot be null");
    LocalSessionFactoryBuilder builder;
    builder = new LocalSessionFactoryBuilder(dataSource());
    builder = getConfigurator().configure(builder, persistenceUnitList);
    return builder.buildSessionFactory();
  }

  /** Retrieves the transaction handler for manually TXs handling.
   * @param tm the transaction manager.
   * @return the transaction handler, it's never null.
   */
  @Bean(name = "banten.transaction")
  public Transaction transaction(final HibernateTransactionManager tm) {
    return new Transaction(tm);
  }

  /** Retrieves the Hibernate configurator.
   * @return the Hibernate configurator.
   */
  private synchronized HibernateConfigurator getConfigurator() {
    if (hibernateConfigurator == null) {
      hibernateConfigurator = new HibernateConfigurator(environment);
    }
    return hibernateConfigurator;
  }

}
