package com.avenida.banten.shiro;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.realm.AuthorizingRealm;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;

import org.apache.shiro.web.mgt.DefaultWebSecurityManager;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.FilterRegistrationBean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.core.Ordered;

/** Shiro Spring configuration.
 *
 * @author waabox (waabox[at]gmail[dot]com)
 */
@Configuration
public class ShiroConfiguration {

  @Bean public FilterRegistrationBean shiroFilter(
      final DefaultWebSecurityManager securityManager) {

    ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
    bean.setSecurityManager(securityManager);

    bean.getFilters().put("saveSession", new BantenSessionStorerFilter());

    Map<String, String> filterChainMap = bean.getFilterChainDefinitionMap();

    Map<String, String> chainDefinitions = new LinkedHashMap<>();

    chainDefinitions.put("/logout", "saveSession, noSessionCreation, logout");
    chainDefinitions.put("/**/static/**", "anon");

    for (UrlToRoleMapping mapping : ShiroConfigurationApi.getMappings()) {
      filterChainMap.put(mapping.getUrl(),
          String.format("authc, roles[%s]", mapping.rolesAsString()));
    }

    chainDefinitions.put("/**", "saveSession, noSessionCreation, authc");

    bean.setFilterChainDefinitionMap(chainDefinitions);

    ShiroViews shiroViews = ShiroConfigurationApi.getShiroViews();
    bean.setLoginUrl(shiroViews.getLoginUrl());
    bean.setUnauthorizedUrl(shiroViews.getUnauthorizedUrl());
    bean.setSuccessUrl(shiroViews.getSuccessUrl());

    try {
      FilterRegistrationBean registration;
      registration = new FilterRegistrationBean((Filter) bean.getObject());
      registration.setName("shiroFilter");
      registration.setOrder(Ordered.HIGHEST_PRECEDENCE + 1);

      return registration;

    } catch (Exception e) {
      throw new RuntimeException("Error creating shiro filter.", e);
    }
  }

  @Bean public DefaultWebSecurityManager securityManager(
      @Value("${jwt.clientSecret}") final String clientSecret,
      final AuthorizingRealm realm) {

    BantenSession.validateKey(clientSecret);

    DefaultWebSecurityManager sm = new DefaultWebSecurityManager();
    sm.setRealm(realm);
    sm.setSessionManager(new BantenWebSessionManager(clientSecret));
    sm.setSubjectFactory(new BantenSubjectFactory());
    return sm;
  }

}
