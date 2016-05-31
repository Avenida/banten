package com.avenida.banten.login.application;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/** The Login controller.
 *
 * @author waabox (waabox[at]gmail[dot]com)
 */
@RestController
@RequestMapping("/web")
public class LoginController {

  @RequestMapping(value = "/form.html",
      method = { RequestMethod.GET, RequestMethod.POST })
  public ModelAndView form() {
    return new ModelAndView("form");
  }

  @RequestMapping(value = "/unauthorized.html",
      method = { RequestMethod.GET, RequestMethod.POST })
  public ModelAndView unauthorized() {
    return new ModelAndView("unauthorized");
  }

}
