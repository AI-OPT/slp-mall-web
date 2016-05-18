package com.ai.slp.mall.web.controller.home;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class HomeController {

	@RequestMapping("/home")
	public ModelAndView index(HttpServletRequest request) {

        ModelAndView view = new ModelAndView("jsp/home/index");
        return view;
    }
}
