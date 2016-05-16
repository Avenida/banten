package com.avenida.banten.hibernate;

import org.slf4j.Logger;
import static org.slf4j.LoggerFactory.getLogger;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.Validate;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataBuilder;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import org.springframework.orm.hibernate5.HibernateTransactionManager;

import com.avenida.banten.core.Configurator;
import com.avenida.banten.core.PersistenceUnit;

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
 *
 * @author waabox (emi[at]avenida[dot]com)
 */
@Configuration
@EnableConfigurationProperties
public class HibernateConfiguration {

  /** The log. */
  private static Logger log = getLogger(HibernateConfiguration.class);

  /** The list of persistence units, it's never null. */
  @Autowired @Resource(name = "persistenceUnitList")
  private List<PersistenceUnit> persistenceUnitList;

  /** The Spring's environment. */
  @Autowired
  private Environment environment;

  /** DataSource bean definition.
   * @return the DataSource to be used by the application.
   * */
  @Bean(name = "banten.dataSource")
  @ConfigurationProperties(prefix = "db")
  public DataSource dataSource() {
    DataSource datasource = new DataSource();
    return datasource;
  }

  /** Defines the transaction manager to use.
   * @param sessionFactory the session factory.
   * @return the Hibernate Transaction manager.
   * */
  @Bean(name = "banten.transactionManager")
  @Autowired
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

    StandardServiceRegistryBuilder builder;
    builder = new StandardServiceRegistryBuilder();
    builder.applySetting("hibernate.connection.datasource", dataSource());
    builder.applySetting("hibernate.current_session_context_class",
            "org.springframework.orm.hibernate5.SpringSessionContext");
    builder.applySettings(new Configurator("dbHibernate", environment).get());

    StandardServiceRegistry registry = builder.build();
    MetadataSources sources = new MetadataSources(registry);

    for(PersistenceUnit pu : persistenceUnitList) {
      log.info("Adding persistence class:[{}]", pu.getPersistenceClass());
      sources.addAnnotatedClass(pu.getPersistenceClass());
    }

    MetadataBuilder metadataBuilder = sources.getMetadataBuilder();
    return metadataBuilder.build().getSessionFactoryBuilder().build();
  }

  /** Retrieves the transaction handler for manually TXs handling.
   * @param tm the transaction manager.
   * @return the transaction handler, it's never null.
   */
  @Bean(name = "banten.transaction")
  public Transaction transaction(final HibernateTransactionManager tm) {
    return new Transaction(tm);
  }

}
