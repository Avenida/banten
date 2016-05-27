package com.avenida.banten.login.shiro;

import javax.transaction.Transactional;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.*;

import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.avenida.banten.login.domain.User;
import com.avenida.banten.login.domain.UserRepository;
import com.avenida.banten.login.domain.Permission;

/** Hibernate's Real that use the {@link User} as principal.
 *
 * @author waabox (waabox[at]gmail[dot]com)
 */
public class BantenLoginRealm extends AuthorizingRealm {

  /** The user repository, it's never null. */
  @Autowired private UserRepository repository;

  /** Creates a new instance of the Realm.*/
  public BantenLoginRealm() {
    setName("banten.realm");
  }

  /** {@inheritDoc}.*/
  @Override
  @Transactional
  protected AuthorizationInfo doGetAuthorizationInfo(
      final PrincipalCollection pc) {
    String email = (String) pc.getPrimaryPrincipal();

    User user = repository.byEmail(email);

    if (user != null) {
      SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
      for (Permission role : user.getPermissions()) {
        info.addRole(role.getName());
      }
      return info;
    }

    return null;
  }

  /** {@inheritDoc}.*/
  @Override
  @Transactional
  protected AuthenticationInfo doGetAuthenticationInfo(
      final AuthenticationToken authcToken) {
    UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
    User user = repository.byEmail(token.getUsername());
    if (user != null) {
      return new SimpleAuthenticationInfo(user.getId(), user.getPassword(),
          getName());
    } else {
      return null;
    }
  }

}
