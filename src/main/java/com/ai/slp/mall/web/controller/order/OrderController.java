package com.ai.slp.mall.web.controller.order;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/order")
public class OrderController {

	private static final Logger LOG = Logger.getLogger(OrderController.class);

	@RequestMapping("/list")
	public ModelAndView index(HttpServletRequest request) {
		return new ModelAndView("jsp/order/order_list");
	}
}
