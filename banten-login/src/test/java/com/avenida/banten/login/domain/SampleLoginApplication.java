package com.avenida.banten.login.domain;

import com.avenida.banten.core.BantenApplication;
import com.avenida.banten.core.ModuleApiRegistry;
import com.avenida.banten.hibernate.HibernateModule;
import com.avenida.banten.login.LoginModule;
import com.avenida.banten.shiro.ShiroModule;

public class SampleLoginApplication extends BantenApplication {

  public SampleLoginApplication() {
    super(
        HibernateModule.class,
        ShiroModule.class,
        LoginModule.class
    );
  }

  @Override
  public void init(final ModuleApiRegistry registry) {
  }

}
