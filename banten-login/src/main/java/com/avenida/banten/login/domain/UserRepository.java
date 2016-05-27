package com.avenida.banten.login.domain;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import com.avenida.banten.hibernate.Repository;

/** Root of the aggregate for the entity user.
 *
 * @author waabox (waabox[at]gmail[dot]com)
 */
public class UserRepository extends Repository {

  /** Creates a new instance of the repository.
   * @param theSessionFactory the session factory, cannot be null.
   */
  public UserRepository(final SessionFactory theSessionFactory) {
    super(theSessionFactory);
  }

  public User byEmail(final String email) {
    Criteria criteria = createCriteria(User.class);
    criteria.add(Restrictions.eq("email", email));
    return (User) criteria.uniqueResult();
  }

}
