package com.avenida.hazelcast.domain;

import org.apache.commons.lang3.Validate;

/** Describes a Cache.
 * @author waabox (emi[at]avenida[dot]com)
 */
public class CacheDescription {

  /** The cache's name, it's never null. */
  private String name;

  /** Whether or not the cache is read-only.*/
  private boolean readOnly;

  /** Creates an empty instance for Jackson.*/
  CacheDescription() {}

  /** Creates a new instance of the
   * @param cacheName the cache's name, cannot be null.
   * @param isReadOnly whether or not this cache should be read-only or not.
   */
  public CacheDescription(final String cacheName, final boolean isReadOnly) {
    Validate.notNull(cacheName, "The cache name cannot be null");
    name = cacheName;
    readOnly = isReadOnly;
  }

  /** Retrieves the name.
   * @return the name
   */
  public String getName() {
    return name;
  }

  /** Retrieves if this cache description is read only or not.
   * @return the true if is read only.
   */
  public boolean isReadOnly() {
    return readOnly;
  }

}
