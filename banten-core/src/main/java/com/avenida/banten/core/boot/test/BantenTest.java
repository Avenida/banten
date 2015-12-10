package com.avenida.banten.core.boot.test;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;

import org.springframework.test.context.ContextConfiguration;

import com.avenida.banten.core.BantenApplication;
import com.avenida.banten.core.BantenApplicationContextLoader;

/** Annotation that initializes a banten application in a test case.
 *
 * You usually add :
 *
 * @RunWith(SpringJUnit4ClassRunner.class)
 *
 * @BantenTest(applicationClass = Application.class)
 *
 * to your junit 4 test class.
 *
 * @author waabox (emi[at]avenida[dot]com)
 */
@ContextConfiguration(loader = BantenApplicationContextLoader.class)
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface BantenTest {

  /** The Factory class that bootstrap the application, cannot be null.
   * @return the Application Factory, never null.
   */
  Class<? extends BantenApplication> applicationClass();
}
