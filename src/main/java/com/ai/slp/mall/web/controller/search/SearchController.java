package com.ai.slp.mall.web.controller.search;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/search")
public class SearchController {

	@RequestMapping("/list")
	public ModelAndView index(HttpServletRequest request) {

        ModelAndView view = new ModelAndView("jsp/search/search_list");
        return view;
    }
}
