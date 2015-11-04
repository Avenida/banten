package com.avenida.hazelcast.controllers;

import java.io.*;
import java.util.*;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.Validate;
import org.hibernate.SessionFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.ModelAndView;

import com.avenida.hazelcast.domain.CacheDescription;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.hazelcast.core.*;

/** Hazelcast web console main controller.
 * @author waabox (emi[at]avenida[dot]com)
 */
@RequestMapping("/webconsole")
public class HazelcastWebConsoleController {

  /** The configuration file. */
  private static final String FILE = "cacheDescriptions.json";

  /** The hazelcast instance, it's never null.*/
  private final HazelcastInstance hazelcast;

  /** The list of cache descriptions */
  private final List<CacheDescription> descriptors;

  /** The object mapper. */
  private final ObjectMapper mapper = new ObjectMapper();

  /** Creates a new instance of the controller.
   * @param hzInstance the Hazelcast instance, cannot be null.
   */
  @Autowired
  public HazelcastWebConsoleController(final HazelcastInstance hzInstance,
      final SessionFactory sf) {
    Validate.notNull(hzInstance, "The client instance cannot be null");
    hazelcast = hzInstance;
    InputStream json;
    json = getClass().getClassLoader().getResourceAsStream(FILE);
    try {
      String daJson = IOUtils.toString(json);
      descriptors = mapper.readValue(
          daJson, mapper.getTypeFactory().constructCollectionType(
                  List.class, CacheDescription.class));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /** List the active caches.
   * @return the spring model and view.
   */
  @RequestMapping(value = "/")
  public ModelAndView home() {
    ModelAndView mav = new ModelAndView("home");
    mav.addObject("members", hazelcast.getCluster().getMembers());
    mav.addObject("caches", descriptors);
    return mav;
  }

  /** List the active caches.
   * @return the spring model and view.
   */
  @RequestMapping(value = "/clean/{cacheName}")
  public ModelAndView clean(
      @PathVariable(value = "cacheName") final String cacheName) {
    for (CacheDescription cacheDescription : descriptors) {
      if (cacheDescription.getName().equals(cacheName)) {
        if (cacheDescription.isReadOnly()) {
          throw new IllegalStateException("Cache is readonly!");
        }
      }
    }
    hazelcast.getMap(cacheName).evictAll();
    return new ModelAndView("redirect:/");
  }

}
