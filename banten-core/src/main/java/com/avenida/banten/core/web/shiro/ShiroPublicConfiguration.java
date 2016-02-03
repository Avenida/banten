package com.avenida.banten.core.web.shiro;

import org.apache.commons.lang3.Validate;
import org.apache.shiro.config.Ini;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.config.WebIniSecurityManagerFactory;
import org.apache.shiro.web.filter.authc.AnonymousFilter;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.apache.shiro.web.filter.authz.RolesAuthorizationFilter;
import org.slf4j.Logger;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.env.Environment;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.servlet.Filter;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.slf4j.LoggerFactory.getLogger;


/**
 * Shiro Public Configuration class.
 *
 * Created by (lucas.luduena) on 13/01/16.
 */
@Configuration
public class ShiroPublicConfiguration {


    /** The log. */
    private static Logger log = getLogger(ShiroPublicConfiguration.class);

    /** Shiro development configuration. */
    private static final String SHIRO_DEV_CFG = "shiro-dev.ini";

    /** Shiro external configuration. */
    private static final String SHIRO_EXTERNAL_CFG = "shiro.ini.path";

    /** Shiro filter order. */
    private static final int SHIRO_FILTER_ORDER = 1;

    /** Shiro filter.
     *
     * @return The FilterRegistrationBean containing the Shiro filter
     * configuration, never null.
     */
    @Bean
    public FilterRegistrationBean shiroFilter() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        DelegatingFilterProxy shiroFilter;
        shiroFilter = new DelegatingFilterProxy("shiroFilterFactory");
        shiroFilter.setTargetFilterLifecycle(true);
        registrationBean.setFilter(shiroFilter);
        registrationBean.setName("shiroFilter");
        registrationBean.setOrder(SHIRO_FILTER_ORDER);
        return registrationBean;
    }

    /**Shiro filter factory for urls.
     * @param environment the Environment.
     * @return a shiro factory bean, never null.
     */
    @Bean(name = "shiroFilterFactory")
    public ShiroFilterFactoryBean shiroFilterFactory(
            final Environment environment, ShiroConfiguration shiroConfig) {
        Validate.notNull(shiroConfig, "The ShiroConfiguration cannot be null");

        log.info("Creating Shiro Filter Factory");

        ShiroFilterFactoryBean shiro = new ShiroFilterFactoryBean();
        shiro.setSecurityManager(securityManager(environment));
        shiro.setLoginUrl(shiroConfig.getLoginUrl());
        shiro.setUnauthorizedUrl(shiroConfig.getUnauthorizeUrl());
        shiro.setSuccessUrl(shiroConfig.getSuccessUrl());
        shiro.setFilterChainDefinitionMap(
                createFilterChainDefinitionMap(
                        shiroConfig.getUrlRoleMappings()));

        return shiro;
    }

    /** Creates the apache shiro Filter Chain Definition Map.
     * @param mappings the ShiroUrlRoleMapping.
     * @return the FilterChainDefinitionMap.
     */
    private Map<String, String> createFilterChainDefinitionMap(
            List<ShiroUrlRoleMapping> mappings) {
      Map<String, String> filterChainDefinition = new LinkedHashMap<>();

      log.info("Creating Shiro FilterChainDefinitionMap, size: " +
              mappings.size());

      for (ShiroUrlRoleMapping mapping : mappings) {
        filterChainDefinition.put(mapping.getUrl(),
                  String.format("authc, roles[%s]", mapping.getRolesString()));
        }


        return filterChainDefinition;
    }

    /** Gets the security manager.
     * Search in the external config property if it's present a shiro.ini path
     * if it's not, fallbacks to "shiro-dev.ini".
     * @param environment the Environment.
     * @return the security manager
     */
    @Bean(name = "securityManager")
    public SecurityManager securityManager(final Environment environment) {
        log.debug("Initializing the security manager");
        Ini ini = new Ini();

        FileInputStream fileInputStream = null;
        String externalShiroConfigPath = environment.getProperty(
                SHIRO_EXTERNAL_CFG);
        if (externalShiroConfigPath != null) {
            try {
                fileInputStream = new FileInputStream(externalShiroConfigPath);
            } catch (Exception e) {
                log.info(String.format("Cant load shiro config from %s",
                        externalShiroConfigPath));
            }
        }

        if(fileInputStream != null) {
            log.info(String.format("Running shiro config from %s",
                    externalShiroConfigPath));
            ini.load(fileInputStream);
        }else {
            log.info("Running shiro in dev environment");
            ini.load(getClass().getClassLoader()
                    .getResourceAsStream(SHIRO_DEV_CFG));
        }

        return new WebIniSecurityManagerFactory(ini).createInstance();
    }

    /** Shiro post processor.
     * @return the siro LifecycleBeanPostProcessor, never null.
     */
    @Bean(name = "lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /** Spring Advisor Auto Proxy.
     * @return the Spring Advisor Auto Proxy, never null.
     */
    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator(){
        return new DefaultAdvisorAutoProxyCreator();
    }

}
