package com.avenida.banten.shiro;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.Validate;
import org.apache.shiro.realm.Realm;
import org.springframework.beans.factory.support.GenericBeanDefinition;

import com.avenida.banten.core.ConfigurationApi;
import com.avenida.banten.core.InitContext;

/** Configuration API for ShiroConfiguration.
 *
 * @author waabox (waabox[at]gmail[dot]com)
 */
public class ShiroConfigurationApi extends ConfigurationApi {

  /** The realm bean. */
  private GenericBeanDefinition realmBean;

  /** The Shiro views bean. */
  private static ShiroViews shiroViews;

  /** The list of URL to Role Mappings. */
  private static List<UrlToRoleMapping> mappings = new LinkedList<>();

  /** Registers the given Realm into the Spring context.
   * @param realm the realm to register, cannot be null.
   */
  public ShiroConfigurationApi registerRealm(
      final Class<? extends Realm> realm) {
    Validate.notNull(realm, "The realm cannot be null");
    realmBean = new GenericBeanDefinition();
    realmBean.setBeanClass(realm);
    realmBean.setDependsOn("lifecycleBeanPostProcessor");
    realmBean.setLazyInit(true);

    InitContext.beanDefinitionRegistry().registerBeanDefinition(
        "banten.realm", realmBean);

    return this;
  }

  /** Register the {@link UrlToRoleMapping} belongs to a module.
   * @param mapping the list of {@link UrlToRoleMapping}, cannot be null.
   */
  public ShiroConfigurationApi register(final UrlToRoleMapping...mapping) {
    Validate.notNull(mapping, "The mappings cannot be null");
    mappings.addAll(Arrays.asList(mapping));
    return this;
  }

  /** Configures the views for each authentication & authorization.
   * @param loginUrl the login URL, cannot be null.
   * @param successUrl the success URL once the login process has succeed,
   *  cannot be null.
   * @param unauthorizedUrl the unauthorized URL, cannot be null.
   */
  public ShiroConfigurationApi configureViews(
      final String loginUrl,
      final String successUrl,
      final String unauthorizedUrl) {
    shiroViews = new ShiroViews(loginUrl, successUrl, unauthorizedUrl);
    return this;
  }


  /** {@inheritDoc}.*/
  @Override
  protected void init() {
    Validate.notNull(realmBean, "ShiroConfiguration Realm _Must_ be defined");
    Validate.notNull(shiroViews,
        "Shiro views not defined, see ShiroConfigurationApi#configureViews");
  }

  /** Retrieves the mappings.
   * @return the mappings
   */
  public static List<UrlToRoleMapping> getMappings() {
    return mappings;
  }

  /** Retrieves the shiroViews.
   * @return the shiroViews
   */
  public static ShiroViews getShiroViews() {
    return shiroViews;
  }

}
