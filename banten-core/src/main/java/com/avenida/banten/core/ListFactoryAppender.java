package com.avenida.banten.core;

import java.util.*;

import org.apache.commons.lang3.*;

import org.springframework.beans.*;
import org.springframework.beans.factory.config.*;

/** Bean post processor that adds additional elements to a target list.
 *
 * It is used to compose a list (for example, the list of persistent classes)
 * with fragments provided by different modules.
 * It is implemented as a bean factory post processor to make sure that the
 * list is fully created before any other bean.
 *
 * Multiple ListFactoryAppender instances can target the same list. There is no
 * guarantee on the order in which fragments are added to the target list.
 */
public class ListFactoryAppender implements BeanPostProcessor {

  /** The id of the target list to add the elements, it is never null.*/
  private final String targetList;

  /** The list of elements to add to the target list, it is never null. */
  private final List<?> elements;

  /** ListFactoryAppender constructor.
   *
   * @param theTargetList the bean name of the target list to add the elements,
   * it cannot be null.
   *
   * @param theElements the list of elements to add to the target list, it
   * cannot be null.
   */
  public ListFactoryAppender(final String theTargetList,
      final List<?> theElements) {
    Validate.notNull(theElements, "the elements cannnot be null.");
    Validate.notNull(theTargetList, "the target list cannnot be null.");
    targetList = theTargetList;
    elements = theElements;
  }

  /** {@inheritDoc}.*/
  @Override
  @SuppressWarnings("unchecked")
  public Object postProcessBeforeInitialization(
      final Object bean, final String beanName)
      throws BeansException {
    if (beanName.equals(targetList)) {
      ((List<Object>) bean).addAll(elements);
    }
    return bean;
  }

  /** {@inheritDoc}.*/
  @Override
  public Object postProcessAfterInitialization(
      final Object bean, final String beanName)
      throws BeansException {
    return bean;
  }

}
