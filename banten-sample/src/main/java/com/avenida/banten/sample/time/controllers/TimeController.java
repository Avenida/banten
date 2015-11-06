package com.avenida.banten.sample.time.controllers;

import javax.transaction.Transactional;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.servlet.ModelAndView;

import com.avenida.banten.sample.time.domain.Time;
import com.avenida.banten.sample.time.domain.TimeRepository;

/**
 * @author waabox (emi[at]avenida[dot]com)
 */
@RestController
@RequestMapping("/time")
public class TimeController {

  /** The time repository. */
  private final TimeRepository repository;

  /** Creates a new instance of the controller.
   * @param theTimeRepository the time repository.
   */
  public TimeController(final TimeRepository theTimeRepository) {
    repository = theTimeRepository;
  }

  @Transactional
  @RequestMapping(value = "/view.html", method = RequestMethod.GET)
  public ModelAndView view() {
    ModelAndView mav = new ModelAndView("view");
    mav.addObject("times", repository.getTimes());
    return mav;
  }

  @Transactional
  @RequestMapping(value = "/save", method = RequestMethod.POST)
  public ModelAndView save(@RequestParam("gmt") final String gmt) {
    Time time = new Time(gmt);
    repository.save(time);
    return new ModelAndView("redirect:view.html");
  }

}
