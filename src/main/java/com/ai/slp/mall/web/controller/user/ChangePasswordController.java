package com.ai.slp.mall.web.controller.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.ai.opt.base.vo.ResponseHeader;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.web.model.ResponseData;
import com.ai.opt.sso.client.filter.SLPClientUser;
import com.ai.opt.sso.client.filter.SSOClientConstants;
import com.ai.slp.user.api.ucUserSecurity.interfaces.IUcUserSecurityManageSV;
import com.ai.slp.user.api.ucUserSecurity.param.UcUserPasswordRequest;
import com.ai.slp.user.api.ucuser.intefaces.IUcUserSV;
import com.ai.slp.user.api.ucuser.param.SearchUserRequest;

@RestController
@RequestMapping("/user/password")
public class ChangePasswordController {
	private static Logger Log=LoggerFactory.getLogger(ChangePasswordController.class);

    //跳转修改密码页面
    @RequestMapping("/toChangePassword")
    public ModelAndView toChangePassword(HttpServletRequest request) {
        ModelAndView view = new ModelAndView("jsp/user/password/change-password");
        return view;
    }
    
    //验证原来密码
    @RequestMapping("/validatePassword")
    @ResponseBody
    public ResponseData<String> validatePassword(HttpServletRequest request){
        ResponseData<String> responseData= null;
        ResponseHeader responseHeader=null;
        HttpSession session = request.getSession();
        SLPClientUser user = (SLPClientUser) session.getAttribute(SSOClientConstants.USER_SESSION_KEY);
        //根据userId查询密码
        String pagePassword = request.getParameter("password");
        SearchUserRequest searchUserRequest = new SearchUserRequest();
        searchUserRequest.setTenantId(user.getTenantId());
        searchUserRequest.setUserId(user.getUserId());
        //获取dubbo服务
        IUcUserSV ucUserSV = DubboConsumerFactory.getService(IUcUserSV.class);
        String password="";
        try{
        password = ucUserSV.queryBaseInfo(searchUserRequest).getUserLoginPwd();
        }catch(Exception e){
            Log.info("查询失败");
        }
        //判断密码
        if(pagePassword.equals(password)){
            responseData = new ResponseData<String>("11111", "成功", null);
            responseHeader = new ResponseHeader(true, "11111", "成功");
        }else{
            responseData = new ResponseData<String>("11110", "密码错误", null);
            responseHeader = new ResponseHeader(false, "11110", "密码错误");
            
        }
          responseData.setResponseHeader(responseHeader);
        return responseData;
    } 
    
    //修改密码
    @RequestMapping("/updatePassword")
    @ResponseBody
    public ResponseData<String> updatePassword(HttpServletRequest request){
        
        ResponseData<String> responseData=null;
        ResponseHeader responseHeader=null;
        
        HttpSession session = request.getSession();
        SLPClientUser user = (SLPClientUser) session.getAttribute(SSOClientConstants.USER_SESSION_KEY);
        UcUserPasswordRequest  ucUserPasswordRequest = new UcUserPasswordRequest();
        ucUserPasswordRequest.setTenantId(user.getTenantId());
        ucUserPasswordRequest.setAccountId(user.getUserId());
        ucUserPasswordRequest.setAccountPassword(request.getParameter("password"));
        //获取dubbo服务
        IUcUserSecurityManageSV ucUserSecurityManageSV = DubboConsumerFactory.getService(IUcUserSecurityManageSV.class);
        try{
            ucUserSecurityManageSV.setPasswordData(ucUserPasswordRequest);
            responseData = new ResponseData<String>("11112", "更新成功", null);
            responseHeader = new ResponseHeader(true, "11112", "更新成功");
        }catch(Exception e){
            Log.info("更新失败");
            responseData = new ResponseData<String>("11113", "更新失败", null);
            responseHeader = new ResponseHeader(false, "11113", "更新失败");
        }
        responseData.setResponseHeader(responseHeader);
        return responseData;
    } 
    
}
