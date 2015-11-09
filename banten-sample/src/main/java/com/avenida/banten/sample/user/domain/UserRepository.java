package com.avenida.banten.sample.user.domain;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

/** The user repository.
 * @author waabox (emi[at]avenida[dot]com)
 */
public class UserRepository {

  /** the session factory. */
  private final SessionFactory session;

  /** Creates a new instance of the repository.
   * @param sessionFactory the session factory.
   */
  @Autowired
  public UserRepository(final SessionFactory sessionFactory) {
    session = sessionFactory;
  }

  /** Saves the given user.
   * @param user the user to save.
   */
  public void save(final User user) {
    session.getCurrentSession().saveOrUpdate(user);
  }

  /** List the users.
   * @return the list of users.
   */
  @SuppressWarnings("unchecked")
  public List<User> list() {
    Criteria criteria = session.getCurrentSession().createCriteria(User.class);
    return criteria.list();
  }

}
