package com.avenida.banten.sample.user.domain;

import com.avenida.banten.core.Factory;
import com.avenida.banten.sample.time.domain.TimeRepository;

/** Factory class for Users.
 * @author waabox (emi[at]avenida[dot]com)
 */
public class UserFactory implements Factory<User> {

  /** The time repository. */
  private final TimeRepository timeRepository;

  public UserFactory(final TimeRepository theTimeRepository) {
    timeRepository = theTimeRepository;
  }

  /** {@inheritDoc}.*/
  @Override
  public User create() {
    return new User(timeRepository);
  }

  /** Creates a user with a name.
   * @param name the user name.
   * @param gmt the gmt.
   * @return the new user.
   */
  public User create(final String name, final String gmt) {
    User user = new User(timeRepository, name, gmt);
    return user;
  }

}
