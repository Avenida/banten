package com.avenida.banten.hibernate;

import org.apache.commons.lang3.Validate;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/** Base repository.
 *
 * @author waabox (waabox[at]gmail[dot]com)
 */
public class Repository {

  /** The session factory, it's never null.*/
  private final SessionFactory factory;

  /** Creates a new instance of the repository.
   * @param theSessionFactory the session factory, cannot be null.
   */
  public Repository(final SessionFactory theSessionFactory) {
    Validate.notNull(theSessionFactory, "The session factory cannot be null");
    factory = theSessionFactory;
  }

  /** Creates a new criteria based on the given class.
   * @param klass the class for the criteria.
   * @return the {@link Criteria}, never null.
   */
  protected Criteria createCriteria(final Class<?> klass) {
    return factory.getCurrentSession().createCriteria(klass);
  }

  /** Retrieves the session factory.
   * @return the {@link SessionFactory}, never null.
   */
  protected SessionFactory getSessionFactory() {
    return factory;
  }

  /** Retrieves the current session.
   * @return the current {@link Session}, never null.
   */
  protected Session getCurrentSession() {
    return factory.getCurrentSession();
  }

}
