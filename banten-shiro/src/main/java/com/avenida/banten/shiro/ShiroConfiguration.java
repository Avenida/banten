package com.avenida.banten.shiro;

import org.apache.shiro.authc.credential.DefaultPasswordService;
import org.apache.shiro.authc.credential.PasswordMatcher;

import org.apache.shiro.realm.AuthorizingRealm;

import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;

import org.apache.shiro.web.mgt.DefaultWebSecurityManager;

import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

/** Shiro Spring configuration.
 *
 * @author waabox (waabox[at]gmail[dot]com)
 */
@Configuration
public class ShiroConfiguration {

  @Bean
  public ShiroFilterFactoryBean shiroFilter(
      final DefaultWebSecurityManager securityManager) {
    ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
    factoryBean.setSecurityManager(securityManager);

    for (UrlToRoleMapping mapping : ShiroConfigurationApi.getMappings()) {
      factoryBean.getFilterChainDefinitionMap().put(mapping.getUrl(),
          String.format("authc, roles[%s]", mapping.rolesAsString()));
    }

    ShiroViews shiroViews = ShiroConfigurationApi.getShiroViews();
    factoryBean.setLoginUrl(shiroViews.getLoginUrl());
    factoryBean.setUnauthorizedUrl(shiroViews.getUnauthorizedUrl());
    factoryBean.setSuccessUrl(shiroViews.getSuccessUrl());

    return factoryBean;
  }

  @Bean(name = "securityManager")
  public DefaultWebSecurityManager securityManager(
       final AuthorizingRealm realm,
       final DefaultWebSessionManager sessionManager) {
    DefaultWebSecurityManager securityManager;
    securityManager = new DefaultWebSecurityManager();
    securityManager.setRealm(realm);
    securityManager.setSessionManager(sessionManager);
    return securityManager;
  }

  @Bean
  @Lazy
  public DefaultWebSessionManager sessionManager() {
     DefaultWebSessionManager sessionManager;
    sessionManager = new DefaultWebSessionManager();
    //sessionManager.setSessionDAO(sessionDao());
    //sessionManager.setGlobalSessionTimeout(43200000); // 12 hours
    return sessionManager;
  }

  @Bean(name = "credentialsMatcher")
  public PasswordMatcher credentialsMatcher() {
    PasswordMatcher credentialsMatcher = new PasswordMatcher();
    credentialsMatcher.setPasswordService(passwordService());
    return credentialsMatcher;
  }

  @Bean(name = "passwordService")
  public DefaultPasswordService passwordService() {
    return new DefaultPasswordService();
  }

  @Bean
  public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
    return new LifecycleBeanPostProcessor();
  }

}
