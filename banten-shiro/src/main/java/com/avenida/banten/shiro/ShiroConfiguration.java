package com.avenida.banten.shiro;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.commons.lang3.Validate;
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

  /** The min key size for the encryption key, in bytes.*/
  private static final int MIN_KEY_SIZE = 40;

  /** The max key size for the encryption key, in bytes.*/
  private static final int MAX_KEY_SIZE = 1024;

  /** The shiro filter chain definition, a map of url patters to filters to
   * apply to that url.
   *
   * This is written by the ShiroRegistry when a module calls registerEndpoint.
   */
  private final Map<String, String> chainDefinitions = new LinkedHashMap<>();

  /** Adds a new chain definition to the list of shiro chain definitions.
  *
  * See ShiroRegistry.registerEndpoint for more information.
  *
  * @param pattern the url pattern. It cannot be null.
  *
  * @param chain the shiro filter chain. It cannot be null.
  */
 void addChainDefinition(final String pattern, final String chain) {
   if (chainDefinitions.isEmpty()) {
     chainDefinitions.put("/logout", "saveSession, noSessionCreation, logout");
   }
   chainDefinitions.put(pattern, "saveSession, noSessionCreation, " + chain);
 }

  @Bean
  public FilterRegistrationBean shiroFilter(
      final DefaultWebSecurityManager securityManager) {

    ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
    bean.setSecurityManager(securityManager);

    //filters.putAll(DefaultFilter.createInstanceMap(null));
    bean.getFilters().put("saveSession", new BantenSessionStorerFilter());

    Map<String, String> filterChainMap = bean.getFilterChainDefinitionMap();
//    filterChainMap.put("*.js*", "anon");
//    filterChainMap.put("*.css*", "anon");
//    filterChainMap.put("*.jpg*", "anon");
//    filterChainMap.put("*.gif*", "anon");
//    filterChainMap.put("*.jpeg*", "anon");

    addChainDefinition("/**", "authc");

    for (UrlToRoleMapping mapping : ShiroConfigurationApi.getMappings()) {
      filterChainMap.put(mapping.getUrl(),
          String.format("authc, roles[%s]", mapping.rolesAsString()));
    }

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

  @Bean(name = "securityManager")
  public DefaultWebSecurityManager securityManager(
       final AuthorizingRealm realm,
       @Value("${jwt.clientSecret}") final String clientSecret) {

    int keySize = clientSecret.getBytes().length;
    Validate.isTrue((keySize >= MIN_KEY_SIZE) && (keySize <= MAX_KEY_SIZE));

    DefaultWebSecurityManager sm = new DefaultWebSecurityManager();
    sm.setRealm(realm);
    sm.setSessionManager(new BantenWebSessionManager(clientSecret));
    sm.setSubjectFactory(new BantenSubjectFactory());
    return sm;
  }

}
