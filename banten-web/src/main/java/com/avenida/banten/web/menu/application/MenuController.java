package com.avenida.banten.web.menu.application;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.servlet.ModelAndView;

import com.avenida.banten.web.menu.MenuConfigurationApi;
import com.avenida.banten.web.menu.MenuSecurityFilter;

/** The {@link MenuBar} controller.
 *
 * @author waabox (waabox[at]gmail[dot]com)
 */
@RestController
@RequestMapping("/menu/")
public class MenuController {

  @RequestMapping(value = "/index.html", method = RequestMethod.GET)
  public ModelAndView index(final HttpServletRequest request) {
    ModelAndView mav = new ModelAndView("menu");
    mav.addObject("menu",
        new MenuSecurityFilter(MenuConfigurationApi.get()).filter());
    return mav;
  }

}
