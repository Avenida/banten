package com.avenida.banten.login.domain;

import org.apache.commons.lang3.Validate;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import com.avenida.banten.hibernate.Repository;

/** Root of the aggregate for the {@link User} entity.
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

  /** Retrieves a {@link User} by its email.
   * @param email the email, cannot be null.
   * @return the {@link User} or null.
   */
  public User getByEmail(final String email) {
    Validate.notNull(email, "The email cannot be null");
    Criteria criteria = createCriteria(User.class);
    criteria.add(Restrictions.eq("email", email));
    return (User) criteria.uniqueResult();
  }

  /** Stores a {@link User}.
   * @param user the {@link User} to store, cannot be null.
   */
  public void save(final User user) {
    Validate.notNull(user, "The user cannot be null");
    getCurrentSession().saveOrUpdate(user);
  }

  /** Stores a {@link Permission}.
   * @param permission the {@link Permission} to store, cannot be null.
   */
  public void savePermission(final Permission permission) {
    Validate.notNull(permission, "The permission cannot be null");
    getCurrentSession().saveOrUpdate(permission);
  }

  /** Retrieves a {@link Permission} by its name.
   * @param name the {@link Permission} name.
   * @return the {@link Permission} or null.
   */
  public Permission getPermissionByName(final String name) {
    Validate.notNull(name, "The permission name cannot be null");
    Criteria criteria = createCriteria(Permission.class);
    criteria.add(Restrictions.eq("name", name));
    return (Permission) criteria.uniqueResult();
  }

}
