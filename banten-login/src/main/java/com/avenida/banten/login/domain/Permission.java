package com.avenida.banten.login.domain;

import javax.persistence.*;

import org.apache.commons.lang3.builder.*;

/** Represents a Permission that a user has.
 *
 * @author waabox (waabox[at]gmail[dot]com)
 */
@Entity
@Table(name = "banten_permissions")
public class Permission {

  /** The id. */
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  /** The permission name. */
  @Column(name = "name")
  private String name;

  /** The description for this permission, it's never null.*/
  @Column(name = "description")
  private String description;

  /** Creates an empty instance for Hibernate.*/
  Permission() {
  }

  public Permission(final String theName, final String theDescription) {
    name = theName;
    description = theDescription;
  }

  /** Retrieves the id.
   * @return the id
   */
  public Integer getId() {
    return id;
  }

  /** Retrieves the name.
   * @return the name
   */
  public String getName() {
    return name;
  }

  /** {@inheritDoc}.*/
  @Override
  public int hashCode() {
    return new HashCodeBuilder().append(id).append(name).hashCode();
  }

  /** {@inheritDoc}.*/
  @Override
  public boolean equals(final Object obj) {
    if (obj == null) {
      return false;
    }
    if (obj instanceof Permission) {
      Permission anotherP = (Permission) obj;
      return new EqualsBuilder()
          .append(name, anotherP.getName())
          .append(id, anotherP.getId()).isEquals();
    } else {
      return false;
    }
  }

}
