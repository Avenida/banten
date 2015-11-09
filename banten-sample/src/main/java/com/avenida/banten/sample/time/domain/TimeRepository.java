package com.avenida.banten.sample.time.domain;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

/** The time repository.
 * @author waabox (emi[at]avenida[dot]com)
 */
public class TimeRepository {

  /** the session factory. */
  private final SessionFactory session;

  /** Creates a new instance of the repository.
   * @param sessionFactory the session factory.
   */
  @Autowired
  public TimeRepository(final SessionFactory sessionFactory) {
    session = sessionFactory;
  }

  /** Retrieves the stored times.
   * @return the stored times.
   */
  @SuppressWarnings("unchecked")
  public List<Time> getTimes() {
    Criteria criteria = session.getCurrentSession().createCriteria(Time.class);
    return criteria.list();
  }

  /** Finds a time given by its GMT.
   * @param gmt the gmt.
   * @return the list of times for that GMT.
   */
  @SuppressWarnings("unchecked")
  public List<Time> find(final String gmt) {
    Criteria criteria = session.getCurrentSession().createCriteria(Time.class);
    criteria.add(Restrictions.eq("gmt", gmt));
    return criteria.list();
  }

  /** Stores the given time.
   * @param time the time.
   */
  public void save(final Time time) {
    session.getCurrentSession().saveOrUpdate(time);
  }

  /** Deletes the given time.
   * @param time the time to delete.
   */
  public void delete(final Time time) {
    session.getCurrentSession().delete(time);
  }

}
