package com.avenida.banten.sample.user;

import java.util.LinkedList;
import java.util.List;

import com.avenida.banten.core.Module;
import com.avenida.banten.core.PersistenceUnit;
import com.avenida.banten.sample.user.domain.User;
import com.avenida.banten.sample.user.domain.UserFactory;

/** The user module.
 * @author waabox (emi[at]avenida[dot]com)
 */
public class UserModule implements Module {

  /** {@inheritDoc}.*/
  @Override
  public String getName() {
    return "User-Module";
  }

  /** {@inheritDoc}.*/
  @Override
  public String getUrlMapping() {
    return "/users/*";
  }

  /** {@inheritDoc}.*/
  @Override
  public Class<?> getMvcConfiguration() {
    return UserMVC.class;
  }

  /** {@inheritDoc}.*/
  @Override
  public Class<?> getModuleConfiguration() {
    return UserConfiguration.class;
  }

  /** {@inheritDoc}.*/
  @Override
  public List<PersistenceUnit> getPersistenceUnits() {
    List<PersistenceUnit> pu = new LinkedList<>();
    pu.add(new PersistenceUnit(User.class, UserFactory.class));
    return pu;
  }

}
