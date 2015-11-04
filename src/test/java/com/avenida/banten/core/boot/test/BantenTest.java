package com.avenida.banten.core.boot.test;

import java.lang.annotation.*;

import org.springframework.test.context.ContextConfiguration;

import com.avenida.banten.core.boot.BantenApplicationFactory;

/** Annotation that handles the Banten Application.
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
  Class<? extends BantenApplicationFactory> factoryClass();

}
