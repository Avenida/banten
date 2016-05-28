package com.avenida.banten.shiro;

import javax.servlet.Filter;

import org.apache.shiro.authc.credential.DefaultPasswordService;
import org.apache.shiro.authc.credential.PasswordMatcher;

import org.apache.shiro.realm.AuthorizingRealm;

import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.mgt.DefaultFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;

import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.boot.context.embedded.FilterRegistrationBean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.Ordered;

/** Shiro Spring configuration.
 *
 * @author waabox (waabox[at]gmail[dot]com)
 */
@Configuration
public class ShiroConfiguration {

  @Bean
  public FilterRegistrationBean shiroFilter(
      final DefaultWebSecurityManager securityManager) {

    ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
    factoryBean.setSecurityManager(securityManager);

    factoryBean.getFilters().putAll(DefaultFilter.createInstanceMap(null));

    factoryBean.getFilterChainDefinitionMap().put("/**", "authc");
    factoryBean.getFilterChainDefinitionMap().put("*.js*", "anon");
    factoryBean.getFilterChainDefinitionMap().put("*.css*", "anon");
    factoryBean.getFilterChainDefinitionMap().put("*.jpg*", "anon");
    factoryBean.getFilterChainDefinitionMap().put("*.gif*", "anon");
    factoryBean.getFilterChainDefinitionMap().put("*.jpeg*", "anon");

    for (UrlToRoleMapping mapping : ShiroConfigurationApi.getMappings()) {
      factoryBean.getFilterChainDefinitionMap().put(mapping.getUrl(),
          String.format("authc, roles[%s]", mapping.rolesAsString()));
    }

    ShiroViews shiroViews = ShiroConfigurationApi.getShiroViews();
    factoryBean.setLoginUrl(shiroViews.getLoginUrl());
    factoryBean.setUnauthorizedUrl(shiroViews.getUnauthorizedUrl());
    factoryBean.setSuccessUrl(shiroViews.getSuccessUrl());

    Filter filter;
    try {
      filter = ((Filter) factoryBean.getObject());
    } catch (Exception e) {
      throw new RuntimeException("Error creating shiro filter.", e);
    }

    FilterRegistrationBean registration = new FilterRegistrationBean(filter);
    registration.setName("shiroFilter");
    registration.setOrder(Ordered.HIGHEST_PRECEDENCE + 1);

    return registration;
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
