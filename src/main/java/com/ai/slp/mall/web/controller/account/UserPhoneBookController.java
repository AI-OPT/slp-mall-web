package com.ai.slp.mall.web.controller.account;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class UserPhoneBookController {

	@RequestMapping("/account/phonebook/phonebookmgr")
	public ModelAndView phonebookmgr(HttpServletRequest request) {
		ModelAndView view = new ModelAndView("jsp/account/phonebook/phonebookmgr");
		return view;
	}

}
