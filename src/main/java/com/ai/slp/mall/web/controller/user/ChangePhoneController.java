package com.ai.slp.mall.web.controller.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.mortbay.log.Log;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.ai.opt.base.vo.ResponseHeader;
import com.ai.opt.sdk.components.mcs.MCSClientFactory;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.web.model.ResponseData;
import com.ai.opt.sso.client.filter.SLPClientUser;
import com.ai.opt.sso.client.filter.SSOClientConstants;
import com.ai.paas.ipaas.mcs.interfaces.ICacheClient;
import com.ai.slp.mall.web.constants.SLPMallConstants;
import com.ai.slp.mall.web.constants.SLPMallConstants.BandEmail;
import com.ai.slp.mall.web.constants.VerifyConstants;
import com.ai.slp.mall.web.util.VerifyUtil;
import com.ai.slp.user.api.ucUserSecurity.interfaces.IUcUserSecurityManageSV;
import com.ai.slp.user.api.ucUserSecurity.param.UcUserPhoneRequest;
import com.ai.slp.user.api.ucuser.intefaces.IUcUserSV;
import com.ai.slp.user.api.ucuser.param.QueryBaseInfoRequest;
import com.ai.slp.user.api.ucuser.param.SearchUserRequest;

@RestController
@RequestMapping("/user/phone")
public class ChangePhoneController {

    // 跳转页面
    @RequestMapping("/toChangePhone")
    public ModelAndView toChangePhone(HttpServletRequest request) {
        HttpSession session = request.getSession();
        SLPClientUser user = (SLPClientUser) session
                .getAttribute(SSOClientConstants.USER_SESSION_KEY);
        ModelAndView view = new ModelAndView("jsp/user/changephone/change-phone");
        // 根据userId查询密码
        SearchUserRequest searchUserRequest = new SearchUserRequest();
        searchUserRequest.setTenantId("0");
        searchUserRequest.setUserId(user.getUserId());
        // 获取dubbo服务
        IUcUserSV ucUserSV = DubboConsumerFactory.getService(IUcUserSV.class);
        String userMp = "";
        try {
            userMp = ucUserSV.queryBaseInfo(searchUserRequest).getUserMp();
        } catch (Exception e) {
            Log.info("查询失败");
        }
        view.addObject("phone", userMp==null?"未绑定手机号":userMp);
        return view;
    }

    // 校验支付密码
    @RequestMapping("/validatePassword")
    @ResponseBody
    public ResponseData<String> validatePassword(HttpServletRequest request) {
        ResponseData<String> responseData = null;
        ResponseHeader responseHeader = null;
        HttpSession session = request.getSession();
        SLPClientUser user = (SLPClientUser) session
                .getAttribute(SSOClientConstants.USER_SESSION_KEY);
        // 根据userId查询密码
        SearchUserRequest searchUserRequest = new SearchUserRequest();
        searchUserRequest.setUserId(user.getUserId());
        // 获取dubbo服务
        IUcUserSV ucUserSV = DubboConsumerFactory.getService(IUcUserSV.class);
        String password = "";
        try {
            password = ucUserSV.queryBaseInfo(searchUserRequest).getUserLoginPwd();
            responseData = new ResponseData<String>("1111", "success", null);
            responseHeader = new ResponseHeader(true, "1111", "校验密码成功");
        } catch (Exception e) {
            Log.info("查询失败");
            responseData = new ResponseData<String>("1112", "fail", null);
            responseHeader = new ResponseHeader(false, "1112", "校验密码失败");
        }
        responseData.setResponseHeader(responseHeader);
        responseData.setData(password);
        return responseData;
    }

    @RequestMapping("/checkPhoneVerifyCode")
    @ResponseBody
    public ResponseData<String> checkPhoneVerifyCode(HttpServletRequest request) {
        ResponseHeader responseHeader = null;
        ICacheClient cacheClient = MCSClientFactory.getCacheClient(BandEmail.CACHE_NAMESPACE);
        String sessionId = request.getSession().getId();

        // 检查短信验证码
        String verifyCodeCache = cacheClient.get(BandEmail.CACHE_KEY_VERIFY_PHONE + sessionId);
        String verifyCode = request.getParameter("verifyCode");
        ResponseData<String> phoneCheck = VerifyUtil.checkPhoneVerifyCode(verifyCode,
                verifyCodeCache);
        String phoneResultCode = phoneCheck.getResponseHeader().getResultCode();
        if (VerifyConstants.ResultCodeConstants.SUCCESS_CODE.equals(phoneResultCode)) {
            responseHeader = new ResponseHeader(true, phoneResultCode, "校验成功");
        } else
            responseHeader = new ResponseHeader(false, phoneResultCode, "校验失败");
        phoneCheck.setResponseHeader(responseHeader);
        return phoneCheck;
    }

    // 修改手机
    @RequestMapping("/updatePhone")
    @ResponseBody
    public ResponseData<String> updatePassword(HttpServletRequest request) {

        ResponseData<String> responseData = null;
        ResponseHeader responseHeader = null;

        HttpSession session = request.getSession();
        SLPClientUser user = (SLPClientUser) session.getAttribute(SSOClientConstants.USER_SESSION_KEY);
        
        if(!"success".equals(validatePhone(request).getResponseHeader().getResultCode())){
            return validatePhone(request);
        }else{
        UcUserPhoneRequest ucUserPhoneRequest = new UcUserPhoneRequest();
        ucUserPhoneRequest.setTenantId(user.getTenantId());
        ucUserPhoneRequest.setAccountId(user.getUserId());
        ucUserPhoneRequest.setPhone(request.getParameter("phone"));
        // 获取dubbo服务
        IUcUserSecurityManageSV ucUserSecurityManageSV = DubboConsumerFactory
                .getService(IUcUserSecurityManageSV.class);
        try {
            ucUserSecurityManageSV.setPhoneData(ucUserPhoneRequest);
            responseData = new ResponseData<String>("11112", "更新成功", null);
            responseHeader = new ResponseHeader(true, "11112", "更新成功");
        } catch (Exception e) {
            responseData = new ResponseData<String>("11113", "更新失败", null);
            responseHeader = new ResponseHeader(false, "11113", "更新失败");
        }
        }
        responseData.setResponseHeader(responseHeader);
        return responseData;
    }
    
    private ResponseData<String> validatePhone(HttpServletRequest request){
        ResponseData<String> responseData= new ResponseData<String>("success", "手机未注册", null);
        ResponseHeader responseHeader=new ResponseHeader(true,"success","手机未注册");
        SLPClientUser user = (SLPClientUser) request.getSession().getAttribute(SSOClientConstants.USER_SESSION_KEY);
        String userType=user.getUserType();
        String userMp =request.getParameter("userMp");
        QueryBaseInfoRequest queryBaseInfoRequest = new QueryBaseInfoRequest();
        queryBaseInfoRequest.setTenantId("0");
        queryBaseInfoRequest.setUserMp(userMp);
        queryBaseInfoRequest.setUserType(userType);
        IUcUserSV ucUserSV = DubboConsumerFactory.getService(IUcUserSV.class);
        if(ucUserSV.queryByBaseInfo(queryBaseInfoRequest).getUserId()!=null){
            responseData = new ResponseData<String>(SLPMallConstants.BandEmail.PHONE_NOTONE_ERROR, "手机已注册", null);
            responseHeader = new ResponseHeader(false,SLPMallConstants.BandEmail.PHONE_NOTONE_ERROR,"手机已注册");
        }
        responseData.setResponseHeader(responseHeader);
        return responseData;
        }
}
