package com.ai.slp.mall.web.controller.head;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/head")
public class HeadController {
    @RequestMapping("/fastCharge")
    public ModelAndView fastChrge(HttpServletRequest request,String flowFastFlag) {
        request.setAttribute("flowFastFlag", flowFastFlag);
        ModelAndView view = new ModelAndView("jsp/fastcharge/fastCharge");
        return view;
    }
}
