package com.ai.slp.mall.web.controller.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ai.opt.base.vo.ResponseHeader;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.web.model.ResponseData;
import com.ai.opt.sso.client.filter.SLPClientUser;
import com.ai.opt.sso.client.filter.SSOClientConstants;
import com.ai.slp.mall.web.constants.SLPMallConstants;
import com.ai.slp.user.api.keyinfo.interfaces.IUcKeyInfoSV;
import com.ai.slp.user.api.keyinfo.param.InsertGroupKeyInfoRequest;

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
    public ResponseData<String> saveEnterprise(HttpServletRequest request,InsertGroupKeyInfoRequest insertGroupKeyInfoRequest){
        ResponseData<String> responseData=null;
        ResponseHeader responseHeader=null;
        
        HttpSession session = request.getSession();
        SLPClientUser user = (SLPClientUser) session.getAttribute(SSOClientConstants.USER_SESSION_KEY);

        insertGroupKeyInfoRequest.setTenantId(user.getTenantId());
        insertGroupKeyInfoRequest.setUserType(user.getUserType());
        insertGroupKeyInfoRequest.setUserId(user.getUserId());
        
        IUcKeyInfoSV ucKeyInfoSV = DubboConsumerFactory.getService(IUcKeyInfoSV.class);
        try{
        ucKeyInfoSV.insertGroupKeyInfo(insertGroupKeyInfoRequest);
        responseData = new ResponseData<String>(SLPMallConstants.Qualification.QUALIFICATION_SUCCESS, "操作成功", null);
        responseHeader = new ResponseHeader(true,SLPMallConstants.Qualification.QUALIFICATION_SUCCESS,"操作成功");
        }catch(Exception e){
            LOGGER.error("更新失败");
            responseData = new ResponseData<String>(SLPMallConstants.Qualification.QUALIFICATION_ERROR, "操作失败", null);
            responseHeader = new ResponseHeader(false,SLPMallConstants.Qualification.QUALIFICATION_ERROR,"操作失败");
        }
        responseData.setResponseHeader(responseHeader);
        return responseData;
    }
    
    
    @RequestMapping("/saveAgentEnterprise")
    @ResponseBody
    public ResponseData<String> saveAgentEnterprise(HttpServletRequest request ,InsertGroupKeyInfoRequest insertGroupKeyInfoRequest){
        ResponseData<String> responseData=null;
        ResponseHeader responseHeader=null;
        
        HttpSession session = request.getSession();
        SLPClientUser user = (SLPClientUser) session.getAttribute(SSOClientConstants.USER_SESSION_KEY);

        insertGroupKeyInfoRequest.setTenantId(user.getTenantId());
        insertGroupKeyInfoRequest.setUserType(user.getUserType());
        insertGroupKeyInfoRequest.setUserId(user.getUserId());
        
        IUcKeyInfoSV ucKeyInfoSV = DubboConsumerFactory.getService(IUcKeyInfoSV.class);
        try{
        ucKeyInfoSV.insertGroupKeyInfo(insertGroupKeyInfoRequest);
        responseData = new ResponseData<String>(SLPMallConstants.Qualification.QUALIFICATION_SUCCESS, "操作成功", null);
        responseHeader = new ResponseHeader(true,SLPMallConstants.Qualification.QUALIFICATION_SUCCESS,"操作成功");
        }catch(Exception e){
            LOGGER.error("更新失败");
            responseData = new ResponseData<String>(SLPMallConstants.Qualification.QUALIFICATION_ERROR, "操作失败", null);
            responseHeader = new ResponseHeader(false,SLPMallConstants.Qualification.QUALIFICATION_ERROR,"操作失败");
        }
        responseData.setResponseHeader(responseHeader);
        return responseData;
    }
    
    
    @RequestMapping("/savePersonalEnterprise")
    @ResponseBody
    public ResponseData<String> savePersonalEnterprise(HttpServletRequest request ,InsertGroupKeyInfoRequest insertGroupKeyInfoRequest){
        ResponseData<String> responseData=null;
        ResponseHeader responseHeader=null;
        
        HttpSession session = request.getSession();
        SLPClientUser user = (SLPClientUser) session.getAttribute(SSOClientConstants.USER_SESSION_KEY);

        insertGroupKeyInfoRequest.setTenantId(user.getTenantId());
        insertGroupKeyInfoRequest.setUserType(user.getUserType());
        insertGroupKeyInfoRequest.setUserId(user.getUserId());
        
        IUcKeyInfoSV ucKeyInfoSV = DubboConsumerFactory.getService(IUcKeyInfoSV.class);
        try{
        ucKeyInfoSV.insertGroupKeyInfo(insertGroupKeyInfoRequest);
        responseData = new ResponseData<String>(SLPMallConstants.Qualification.QUALIFICATION_SUCCESS, "操作成功", null);
        responseHeader = new ResponseHeader(true,SLPMallConstants.Qualification.QUALIFICATION_SUCCESS,"操作成功");
        }catch(Exception e){
            LOGGER.error("更新失败");
            responseData = new ResponseData<String>(SLPMallConstants.Qualification.QUALIFICATION_ERROR, "操作失败", null);
            responseHeader = new ResponseHeader(false,SLPMallConstants.Qualification.QUALIFICATION_ERROR,"操作失败");
        }
        responseData.setResponseHeader(responseHeader);
        return responseData;
    }
    
    
}