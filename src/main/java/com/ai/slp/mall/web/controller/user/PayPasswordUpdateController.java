package com.ai.slp.mall.web.controller.user;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ai.opt.base.vo.BaseResponse;
import com.ai.opt.base.vo.ResponseHeader;
import com.ai.opt.sdk.components.mcs.MCSClientFactory;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.util.CollectionUtil;
import com.ai.opt.sdk.util.Md5Encoder;
import com.ai.opt.sdk.util.StringUtil;
import com.ai.opt.sdk.util.UUIDUtil;
import com.ai.opt.sdk.web.model.ResponseData;
import com.ai.opt.sso.client.filter.SLPClientUser;
import com.ai.opt.sso.client.filter.SSOClientConstants;
import com.ai.paas.ipaas.mcs.interfaces.ICacheClient;
import com.ai.slp.balance.api.accountmaintain.interfaces.IAccountMaintainSV;
import com.ai.slp.balance.api.accountmaintain.param.AccountUpdateParam;
import com.ai.slp.balance.api.accountquery.interfaces.IAccountQuerySV;
import com.ai.slp.balance.api.accountquery.param.AccountInfoVo;
import com.ai.slp.balance.api.accountquery.param.CustIdParam;
import com.ai.slp.mall.web.constants.SLPMallConstants;
import com.ai.slp.mall.web.constants.SLPMallConstants.BandEmail;
import com.ai.slp.mall.web.constants.SLPMallConstants.ExceptionCode;
import com.ai.slp.mall.web.constants.SLPMallConstants.PayPassword;
import com.ai.slp.mall.web.constants.VerifyConstants;
import com.ai.slp.mall.web.model.user.SafetyConfirmData;
import com.ai.slp.mall.web.util.CacheUtil;
import com.ai.slp.mall.web.util.VerifyUtil;
import com.ai.slp.user.api.login.interfaces.ILoginSV;
import com.ai.slp.user.api.login.param.LoginRequest;
import com.ai.slp.user.api.login.param.LoginResponse;
import com.ai.slp.user.api.register.interfaces.IRegisterSV;
import com.ai.slp.user.api.register.param.UcUserParams;
import com.ai.slp.user.api.ucuser.intefaces.IUcUserSV;
import com.ai.slp.user.api.ucuser.param.SearchUserRequest;
import com.ai.slp.user.api.ucuser.param.SearchUserResponse;

@RequestMapping("/user/payPassword")
@Controller
public class PayPasswordUpdateController {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(PayPasswordUpdateController.class);
   
    @RequestMapping("/updatePayPassword")
    public ModelAndView updatePayPassword(HttpServletRequest request) {

        SLPClientUser userClient = (SLPClientUser) request.getSession().getAttribute(SSOClientConstants.USER_SESSION_KEY);
        Map<String, SearchUserResponse> model = new HashMap<String, SearchUserResponse>(); 
        if (userClient != null) {
             IUcUserSV ucUserSV = DubboConsumerFactory.getService("iUcUserSV");
             SearchUserRequest reachUserRequest = new SearchUserRequest();
             reachUserRequest.setUserId(userClient.getUserId());
             SearchUserResponse response = ucUserSV.queryBaseInfo(reachUserRequest);
             model.put("userInfo", response);
         } 
         return new ModelAndView("jsp/user/paymentpassword/update_paymentpassword_start", model);
     
    }
    
    @RequestMapping("/setPayPassword")
    public ModelAndView setPayPassword(HttpServletRequest request) {

         return new ModelAndView("jsp/user/paymentpassword/set_paymentpassword_new");
     
    }

    @RequestMapping("/checkPhone")
    @ResponseBody
    public ResponseData<String> checkPhone(UcUserParams userParams, HttpSession session, HttpServletRequest req) {
        ResponseData<String> responseData = null;
        ResponseHeader header = new ResponseHeader();
        header.setIsSuccess(true);
        try {
            IRegisterSV iRegisterSV = DubboConsumerFactory.getService("iRegisterSV");
            userParams.setUserType("10");
            BaseResponse searchResponse = iRegisterSV.searchUserInfo(userParams);
            if (searchResponse != null) {
                if (searchResponse.getResponseHeader()!=null&&searchResponse.getResponseHeader().getResultCode().equals(BandEmail.PHONE_NOTONE_ERROR)) {
                    header.setResultCode(BandEmail.PHONE_NOTONE_ERROR);
                    header.setResultMessage("手机已经注册");
                    responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS, "手机已经注册", null);
                    responseData.setResponseHeader(header);
                } else {
                    header.setResultCode(ExceptionCode.SUCCESS);
                    header.setResultMessage("成功");
                    String accountIdKey = UUIDUtil.genId32();
                    responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS, "手机校验成功", accountIdKey);
                    responseData.setResponseHeader(header);
                }
            }
        } catch (Exception e) {
            LOGGER.error("手机校验失败！", e);
            responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_FAILURE, "手机校验失败", null);
        }
        return responseData;
    }


    /**
     * 检查支付密码是否和登录密码相同
     */
    @RequestMapping("/checkPasswordValue")
    @ResponseBody
    public ResponseData<String> checkPasswordValue(HttpServletRequest request, String password) {
        SLPClientUser userClient = (SLPClientUser) request.getSession().getAttribute(SSOClientConstants.USER_SESSION_KEY);
        ResponseData<String> responseData = null;
        ResponseHeader header = new ResponseHeader();
        header.setIsSuccess(true);
        ILoginSV login = DubboConsumerFactory.getService(ILoginSV.class);
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUserLoginName(userClient.getUsername());
        loginRequest.setUserType(userClient.getUserType());
        LoginResponse loginResponse = login.login(loginRequest);
        String loginPassword = loginResponse.getUserLoginPwd();
        password = Md5Encoder.encodePassword(password);
        if(loginPassword.equals(password)){
            header.setResultCode(PayPassword.PASSWORD_EQUALS);
            header.setResultMessage("支付密码与登录密码相同");
            responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS, "支付密码与登录密码相同", null);
            responseData.setResponseHeader(header);
        }else{
            header.setResultCode(ExceptionCode.SUCCESS);
            header.setResultMessage("成功");
            String accountIdKey = UUIDUtil.genId32();
            responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS, "支付密码校验成功", accountIdKey);
            responseData.setResponseHeader(header);
        }
        return responseData;
    }
    
    /**
     * 修改密码
     */
    @RequestMapping("/updatePayPasswordNew")
    @ResponseBody
    public ResponseData<String> updatePayPasswordNew(HttpServletRequest request, String password) {
        SLPClientUser userClient = (SLPClientUser) request.getSession().getAttribute(SSOClientConstants.USER_SESSION_KEY);
        ResponseData<String> responseData = null;
        ResponseHeader header = new ResponseHeader();
        header.setIsSuccess(true);
        /**
         * 通过userId获取acctId
         */
        CustIdParam accountId  = new CustIdParam();
        accountId.setTenantId(SLPMallConstants.COM_TENANT_ID);
        accountId.setCustId(userClient.getUserId());
        IAccountQuerySV accountQuerySV = DubboConsumerFactory.getService(IAccountQuerySV.class);
        List<AccountInfoVo> accountList = accountQuerySV.queryAccontByCustId(accountId);
        long acctId = 0L;
        if(!CollectionUtil.isEmpty(accountList)){
            AccountInfoVo accountInfoVo = accountList.get(0);
            acctId = accountInfoVo.getAcctId();
        }
        
        /**
         * 修改支付密码
         */
        try {
            password = StringUtil.toString(DigestUtils.md5DigestAsHex(password
                    .getBytes("UTF-8")));
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        IAccountMaintainSV accountMaintainSV = DubboConsumerFactory.getService(IAccountMaintainSV.class);
        AccountUpdateParam updateParam = new AccountUpdateParam();
        updateParam.setTenantId(SLPMallConstants.COM_TENANT_ID);
        updateParam.setAcctId(acctId);
        updateParam.setAcctMailType(0);
        updateParam.setAcctName(userClient.getUsername());
        updateParam.setPayCheck(1);
        updateParam.setPayPassword(password);
        try{
            accountMaintainSV.updateAccount(updateParam);
            header.setResultCode(PayPassword.UPDATE_PASSWORD_SUCCESS);
            header.setResultMessage("成功");
            responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS, "修改支付密码校验成功", "/user/payPassword/updatePayPasswordSuccess");
            responseData.setResponseHeader(header);
        }catch(Exception e){
            header.setResultCode(PayPassword.UPDATE_PASSWORD_ERROR);
            header.setResultMessage("修改支付密码失败");
            responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS, "修改支付密码失败", null);
            responseData.setResponseHeader(header);
        }
        return responseData;
    }
    
    @RequestMapping("/updatePayPasswordSuccess")
    @ResponseBody
    public ModelAndView updatePayPasswordSuccess(HttpServletRequest request, String password) {
        return new ModelAndView("jsp/user/paymentpassword/update_paymentpassword_success");
    }
}
