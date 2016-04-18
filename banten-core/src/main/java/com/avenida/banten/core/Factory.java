package com.avenida.banten.core;

/** Base factory interface.
 *
 * @author waabox (emi[at]avenida[dot]com)
 */
public interface Factory<T> {

  /** Creates an object.
   * @return the new object.
   */
  T create();

}

