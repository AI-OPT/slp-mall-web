package com.ai.slp.mall.web.controller.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.mortbay.log.Log;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.ai.opt.base.vo.ResponseHeader;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.web.model.ResponseData;
import com.ai.opt.sso.client.filter.SLPClientUser;
import com.ai.opt.sso.client.filter.SSOClientConstants;
import com.ai.slp.user.api.ucuser.intefaces.IUcUserSV;
import com.ai.slp.user.api.ucuser.param.SearchUserRequest;

@RestController
@RequestMapping("/user")
public class ChangePasswordController {

    @RequestMapping("/toChangePassword")
    public ModelAndView toChangePassword(HttpServletRequest request) {
        ModelAndView view = new ModelAndView("jsp/user/change-password");
        return view;
    }
    
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
        searchUserRequest.setUserId(user.getUserId());
        user.getUserId();
        //获取dubbo服务
        IUcUserSV ucUserSV = DubboConsumerFactory.getService("iUcUser");
        String password="";
        try{
        password = ucUserSV.queryBaseInfo(searchUserRequest).getUserLoginPwd();
        }catch(Exception e){
            Log.info("查询失败");
        }
        //判断密码
        if(pagePassword.equals(password)){
            responseData = new ResponseData<String>("11110", "密码错误", null);
            responseHeader = new ResponseHeader(false, "11110", "密码错误");
        }else{
            responseData = new ResponseData<String>("11111", "成功", null);
            responseHeader = new ResponseHeader(true, "11111", "成功");
        }
          responseData.setResponseHeader(responseHeader);
        return responseData;
    } 
    
}
