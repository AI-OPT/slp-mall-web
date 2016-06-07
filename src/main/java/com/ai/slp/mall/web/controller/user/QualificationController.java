package com.ai.slp.mall.web.controller.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping("/user/qualification")
@Controller
public class QualificationController {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(BandEmailController.class);

    
    @RequestMapping("/user/qualification")
    public ModelAndView toApplyPage(){
        
        return new ModelAndView("jsp/user/qualification/qualification-select");
    }
}
