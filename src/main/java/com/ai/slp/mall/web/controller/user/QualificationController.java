package com.ai.slp.mall.web.controller.user;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ai.opt.sdk.web.model.ResponseData;

@RequestMapping("/user/qualification")
@Controller
public class QualificationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BandEmailController.class);

    //代理商选择页面
    @RequestMapping("/toAgentSelectPage")
    public ModelAndView toAgentSelectPage() {
        return new ModelAndView("jsp/user/qualification/agent-select");
    }
    //代理商个人页面
    @RequestMapping("/toAgentPersonalPage")
    public ModelAndView toAgentPersonalPage() {
        return new ModelAndView("jsp/user/qualification/agent-personal");
    }
    //代理商企业页面
    @RequestMapping("/toAgentEnterprisePage")
    public ModelAndView toAgentEnterprisePage() {
        return new ModelAndView("jsp/user/qualification/agent-enterprise");
    }
    //企业页面
    @RequestMapping("/toEnterprisePage")
    public ModelAndView toEnterprisePage() {
        
        return new ModelAndView("jsp/user/qualification/enterprise");
    }
    
    
    @RequestMapping("/saveEnterprise")
    @ResponseBody
    public ResponseData<String> saveEnterprise(HttpServletRequest request){
        
        return null;
    }
    
    
    @RequestMapping("/saveAgentEnterprise")
    @ResponseBody
    public ResponseData<String> saveAgentEnterprise(HttpServletRequest request){
        
        return null;
    }
    
    
    @RequestMapping("/savePersonalEnterprise")
    @ResponseBody
    public ResponseData<String> savePersonalEnterprise(HttpServletRequest request){
        
        return null;
    }
    
    
}