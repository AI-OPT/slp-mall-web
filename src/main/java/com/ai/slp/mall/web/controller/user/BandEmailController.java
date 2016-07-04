package com.ai.slp.mall.web.controller.user;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ai.opt.base.vo.BaseResponse;
import com.ai.opt.base.vo.ResponseHeader;
import com.ai.opt.sdk.components.ccs.CCSClientFactory;
import com.ai.opt.sdk.components.mcs.MCSClientFactory;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.util.StringUtil;
import com.ai.opt.sdk.util.UUIDUtil;
import com.ai.opt.sdk.web.model.ResponseData;
import com.ai.opt.sso.client.filter.SLPClientUser;
import com.ai.opt.sso.client.filter.SSOClientConstants;
import com.ai.opt.sso.client.filter.SSOClientUser;
import com.ai.paas.ipaas.ccs.IConfigClient;
import com.ai.paas.ipaas.mcs.interfaces.ICacheClient;
import com.ai.slp.mall.web.constants.SLPMallConstants;
import com.ai.slp.mall.web.constants.SLPMallConstants.BandEmail;
import com.ai.slp.mall.web.constants.SLPMallConstants.ExceptionCode;
import com.ai.slp.mall.web.constants.VerifyConstants;
import com.ai.slp.mall.web.constants.VerifyConstants.EmailVerifyConstants;
import com.ai.slp.mall.web.constants.VerifyConstants.ResultCodeConstants;
import com.ai.slp.mall.web.model.user.SafetyConfirmData;
import com.ai.slp.mall.web.model.user.SendEmailRequest;
import com.ai.slp.mall.web.util.CacheUtil;
import com.ai.slp.mall.web.util.IPUtil;
import com.ai.slp.mall.web.util.VerifyUtil;
import com.ai.slp.user.api.ucUserSecurity.interfaces.IUcUserSecurityManageSV;
import com.ai.slp.user.api.ucUserSecurity.param.UcUserEmailRequest;
import com.ai.slp.user.api.ucuser.intefaces.IUcUserSV;
import com.ai.slp.user.api.ucuser.param.SearchUserRequest;
import com.ai.slp.user.api.ucuser.param.SearchUserResponse;
import com.ai.slp.user.api.ucuser.param.UpdateUserInfoRequest;

@RequestMapping("/user/bandEmail")
@Controller
public class BandEmailController {
    private static final Logger LOGGER = LoggerFactory.getLogger(BandEmailController.class);
    
    @RequestMapping("/bandEmailStart")
    public ModelAndView bandEmailStart(HttpServletRequest request) {
        String uuid = UUIDUtil.genId32();
        SLPClientUser userClient = (SLPClientUser) request.getSession().getAttribute(SSOClientConstants.USER_SESSION_KEY);
        CacheUtil.setValue(uuid, SLPMallConstants.UUID.OVERTIME, userClient, BandEmail.CACHE_NAMESPACE);
        Map<String,String> model = new HashMap<String,String>();
        IUcUserSV ucUserSV = DubboConsumerFactory.getService("iUcUserSV");
        SearchUserRequest reachUserRequest = new SearchUserRequest();
        reachUserRequest.setUserId(userClient.getUserId());
        SearchUserResponse response = ucUserSV.queryBaseInfo(reachUserRequest);
        model.put("email", response.getUserEmail());
        model.put("uuid", uuid);
        model.put("confirminfo", "");
        return new ModelAndView("jsp/user/email/band-email-start",model);
    }


    /**
     * 身份认证
     * 
     * @param request
     * @return
     */
    @RequestMapping("/confirmInfo")
    @ResponseBody
    public ResponseData<String> confirmInfo(HttpServletRequest request, SafetyConfirmData safetyConfirmData) {
        ResponseData<String> responseData = null;
        String confirmType = safetyConfirmData.getConfirmType();
        ICacheClient cacheClient = MCSClientFactory.getCacheClient(BandEmail.CACHE_NAMESPACE);
        String sessionId = request.getSession().getId();
        // 检查图片验证码
        if(BandEmail.CHECK_TYPE_EMAIL.equals(confirmType)){
            String pictureVerifyCodeCache = cacheClient.get(BandEmail.CACHE_KEY_VERIFY_PICTURE + sessionId);
            String pictureVerifyCode = safetyConfirmData.getPictureVerifyCode();
            ResponseData<String> pictureCheck = VerifyUtil.checkPictureVerifyCode(pictureVerifyCode, pictureVerifyCodeCache);
            String resultCode = pictureCheck.getResponseHeader().getResultCode();
            if (!VerifyConstants.ResultCodeConstants.SUCCESS_CODE.equals(resultCode)) {
                return pictureCheck;
            }
        }
        // 检查短信
        if (BandEmail.CHECK_TYPE_PHONE.equals(confirmType)) {
            // 检查短信验证码
            ResponseData<String> phoneCheck = VerifyUtil.checkPhoneVerifyCode(sessionId, cacheClient, safetyConfirmData);
            String phoneResultCode = phoneCheck.getResponseHeader().getResultCode();
            if (!VerifyConstants.ResultCodeConstants.SUCCESS_CODE.equals(phoneResultCode)) {
                return phoneCheck;
            }
        }
        // 用户信息放入缓存
        String uuid = UUIDUtil.genId32();
        SLPClientUser userClient = (SLPClientUser) request.getSession().getAttribute(SSOClientConstants.USER_SESSION_KEY);
        CacheUtil.setValue(uuid, SLPMallConstants.UUID.OVERTIME, userClient, BandEmail.CACHE_NAMESPACE);
        responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS, "正确", "/user/bandEmail/updateEmailAuthenticate?" + SLPMallConstants.UUID.KEY_NAME + "=" + uuid);
        ResponseHeader responseHeader = new ResponseHeader(true, VerifyConstants.ResultCodeConstants.SUCCESS_CODE, "正确");
        responseData.setResponseHeader(responseHeader);
        return responseData;
    }

    
    /**
     * 修改邮箱页跳转
     * 
     * @param request
     * @return
     */
    @RequestMapping("/setEmail")
    public ModelAndView BandEmailPage(HttpServletRequest request) {
        SLPClientUser userClient = (SLPClientUser) request.getSession().getAttribute(SSOClientConstants.USER_SESSION_KEY);
        if (userClient == null) {
            return new ModelAndView("redirect:/user/bandEmail/confirminfo");
        }
        IUcUserSV ucUserSV = DubboConsumerFactory.getService("iUcUserSV");
        SearchUserRequest reachUserRequest = new SearchUserRequest();
        reachUserRequest.setUserId(userClient.getUserId());
        SearchUserResponse response = ucUserSV.queryBaseInfo(reachUserRequest);
        Map<String, Object> model = new HashMap<String, Object>();
        String uuid = UUIDUtil.genId32();
        model.put("userInfo", response);
        model.put("uuid", uuid);
        model.put("confirminfo", "");
        return new ModelAndView("jsp/user/email/update-email-start", model);
    }

    /**
     * 检查修改邮箱是否唯一
     * 
     * @param request
     * @param email
     * @return
     */
    @RequestMapping("/checkEmailValue")
    @ResponseBody
    public ResponseData<String> checkEmailValue(HttpServletRequest request, String email) {
        SLPClientUser userClient = (SLPClientUser) request.getSession().getAttribute(SSOClientConstants.USER_SESSION_KEY);
        // 检查是否重复
        return VerifyUtil.checkEmailOnly(userClient.getUserId(),email);
    }
    
    
    /**
     * 绑定邮件，发送url地址
     */
    @RequestMapping("/sendEmail")
    @ResponseBody
    public ResponseData<String> sendEmail(HttpServletRequest request, String email,String emailType) {
        ResponseData<String> responseData = null;
        SLPClientUser userClient = (SLPClientUser) request.getSession().getAttribute(SSOClientConstants.USER_SESSION_KEY);
        IConfigClient configClient = CCSClientFactory.getDefaultConfigClient();
        try { 
                // 检查ip发送验证码次数
                ResponseData<String> checkIpSendEmail = VerifyUtil.checkIPSendEmailCount(BandEmail.CACHE_NAMESPACE, IPUtil.getIp(request) + BandEmail.CACHE_KEY_IP_SEND_EMAIL_NUM);
                if (!checkIpSendEmail.getResponseHeader().isSuccess()) {
                    return checkIpSendEmail;
                }
                String templateUrl = "";
                if("setEmail".equals(emailType)){
                    templateUrl = BandEmail.TEMPLATE_SETEMAIL_URL;
                }else if("updateEmail".equals(emailType)){
                    templateUrl = BandEmail.TEMPLATE_UPDATE_EMAIL_URL;
                }else if("bandEmail".equals(emailType)){
                    templateUrl = BandEmail.TEMPLATE_BAND_EMAIL_URL;
                }
                String rasultCode = sendBandEmailVerifyCode(request, email, userClient,templateUrl);
                if ("0000".equals(rasultCode)) {
                    responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS, "短信验证码发送成功", "短信验证码发送成功");
                    ResponseHeader header = new ResponseHeader();
                    header.setIsSuccess(true);
                    header.setResultCode(ResultCodeConstants.SUCCESS_CODE);
                    responseData.setResponseHeader(header);
                     
                    
                    IUcUserSV iAccountManageSV = DubboConsumerFactory.getService("iUcUserSV");
                    SearchUserRequest accountReq = new SearchUserRequest();
                    accountReq.setUserEmail(email);
                    SearchUserResponse accountQueryResponse = iAccountManageSV.queryByEmail(accountReq);
                    String emailValidateFlag = BandEmail.EMAIL_NOT_CERTIFIED;
                    
                    if(BandEmail.EMAIL_CERTIFIED.equals(accountQueryResponse.getEmailValidateFlag())){
                         emailValidateFlag = BandEmail.EMAIL_CERTIFIED;
                     }
                    UpdateUserInfoRequest updateUserInfoRequest = new UpdateUserInfoRequest();
                    updateUserInfoRequest.setUserId(userClient.getUserId());
                    updateUserInfoRequest.setUserEmail(email);
                    updateUserInfoRequest.setEmailValidateFlag(emailValidateFlag);
                    updateUserInfoRequest.setTenantId(userClient.getTenantId());
                    IUcUserSV ucUser = DubboConsumerFactory.getService("iUcUserSV");
                    ucUser.updateBaseInfo(updateUserInfoRequest);
                    
                    return responseData;
                } else if ("0002".equals(rasultCode)) {
                    String maxTimeStr = configClient.get(EmailVerifyConstants.SEND_VERIFY_MAX_TIME_KEY);
                    String errorMsg = Integer.valueOf(maxTimeStr) / 60 + "分钟内不可重复发送";
                    responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS, errorMsg, errorMsg);
                    ResponseHeader header = new ResponseHeader();
                    header.setIsSuccess(false);
                    header.setResultCode(ResultCodeConstants.REGISTER_VERIFY_ERROR);
                    header.setResultMessage(errorMsg);
                    responseData.setResponseHeader(header);
                    return responseData;
                } else {
                    responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_FAILURE, "短信验证码发送失败", "服务器连接超时");
                    ResponseHeader header = new ResponseHeader();
                    header.setIsSuccess(false);
                    header.setResultCode(ResultCodeConstants.ERROR_CODE);
                    responseData.setResponseHeader(header);
                    return responseData;
                }
              
        } catch (Exception e) {
            LOGGER.error("发送邮件验证码错误：" + e);
        }
        return null;
    }
    
    
    private String sendBandEmailVerifyCode(HttpServletRequest request, String email, SLPClientUser userClient,String templateUrl) {
        // 查询是否发送过邮件
        String smstimes = "1";
        String smskey = BandEmail.CACHE_KEY_UPDATE_SEND_EMAIL_NUM + email + request.getSession().getId();
        ICacheClient cacheClient = MCSClientFactory.getCacheClient(BandEmail.CACHE_NAMESPACE);
        IConfigClient configClient = CCSClientFactory.getDefaultConfigClient();
        String times = cacheClient.get(smskey);
        String uuid = request.getParameter(SLPMallConstants.UUID.KEY_NAME);
        try {
            if (StringUtil.isBlank(times)) {
                String loginName = userClient.getUsername();
                SendEmailRequest emailRequest = new SendEmailRequest();
                emailRequest.setTomails(new String[] { email });
                emailRequest.setTemplateURL(templateUrl);
                emailRequest.setSubject(BandEmail.EMAIL_SUBJECT);
                
                String overTimeStr = configClient.get(EmailVerifyConstants.VERIFY_OVERTIME_KEY);
                // 将发送次数放入缓存
                String maxTimeStr = configClient.get(EmailVerifyConstants.SEND_VERIFY_MAX_TIME_KEY);
                cacheClient.setex(smskey, Integer.valueOf(maxTimeStr), smstimes);
                // 超时时间
                String overTime = ObjectUtils.toString(Integer.valueOf(overTimeStr) / 60);
                String service_url = CCSClientFactory.getDefaultConfigClient().get(SLPMallConstants.URLConstant.INDEX_URL_KEY);
                //String service_url="http://localhost:8090/slp-mall";
                if(BandEmail.TEMPLATE_SETEMAIL_URL.equals(templateUrl)){
                    service_url=service_url+"/user/bandEmail/bandEmailAuthenticate";
                }else if(BandEmail.TEMPLATE_BAND_EMAIL_URL.equals(templateUrl)){
                    service_url=service_url+"/user/bandEmail/updateBandEmailAuthenticate";
                }else if(BandEmail.TEMPLATE_UPDATE_EMAIL_URL.equals(templateUrl)){
                    service_url=service_url+"/user/bandEmail/updateEmailAuthenticate";
                }
                emailRequest.setData(new String[] { loginName,overTime,service_url, uuid});
                boolean isSuccess = VerifyUtil.sendEmail(emailRequest);
                if (isSuccess) {
                    userClient.setUserEmail(email);
                    CacheUtil.setValue(uuid, SLPMallConstants.UUID.OVERTIME, userClient, BandEmail.CACHE_NAMESPACE); 
                    // 成功
                    return "0000";
                } else {
                    // 失败
                    return "0001";
                }
            } else {
                // 重复发送
                return "0002";
            }
        } catch (Exception e) {
            LOGGER.error("是否发送过邮件" + e);
        }
        return null;
    }

    /**
     * 设置新邮箱
     * 
     * @param request
     * @param newPassword
     * @return
     */
    @RequestMapping("/setNewEmail")
    @ResponseBody
    public ResponseData<String> setNewEmail(HttpServletRequest request, String email, String verifyCode) {
        ResponseData<String> responseData = null;
        ResponseHeader responseHeader = null;
        String uuid = request.getParameter(SLPMallConstants.UUID.KEY_NAME);
        SSOClientUser userClient = (SSOClientUser) CacheUtil.getValue(uuid, BandEmail.CACHE_NAMESPACE, SSOClientUser.class);
        if (userClient == null) {
            responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS, "身份认证失效", "/center/bandEmail/confirminfo");
            responseHeader = new ResponseHeader(false, VerifyConstants.ResultCodeConstants.USER_INFO_NULL, "认证身份失效");
            responseData.setResponseHeader(responseHeader);
        } else {
            // 检查验证码
            ICacheClient cacheClient = MCSClientFactory.getCacheClient(BandEmail.CACHE_NAMESPACE);
            String cacheKey = BandEmail.CACHE_KEY_VERIFY_SETEMAIL + email + request.getSession().getId();
            String verifyCodeCache = cacheClient.get(cacheKey);
            ResponseData<String> checkVerifyCode = VerifyUtil.checkEmailVerifyCode(verifyCode, verifyCodeCache);
            String emailResultCode = checkVerifyCode.getResponseHeader().getResultCode();
            if (!VerifyConstants.ResultCodeConstants.SUCCESS_CODE.equals(emailResultCode)) {
                responseData = checkVerifyCode;
            } else {
                // 更新邮箱
                IUcUserSecurityManageSV accountSecurityManageSV = DubboConsumerFactory.getService("iUcUserSecurityManageSV");
                UcUserEmailRequest accountEmailRequest = new UcUserEmailRequest();
                accountEmailRequest.setAccountId(userClient.getAccountId());
                accountEmailRequest.setEmail(email);
                accountEmailRequest.setUpdateAccountId(userClient.getAccountId());
                BaseResponse resultData = accountSecurityManageSV.setEmailData(accountEmailRequest);
                if (ExceptionCode.SUCCESS.equals(resultData.getResponseHeader().getResultCode())) {
                    String newuuid = UUIDUtil.genId32();
                    userClient.setEmail(email);// 更改为新邮箱
                    CacheUtil.setValue(newuuid, SLPMallConstants.UUID.OVERTIME, userClient, BandEmail.CACHE_NAMESPACE);
                    responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS, "修改邮箱成功", "/center/bandEmail/success?" + SLPMallConstants.UUID.KEY_NAME + "=" + newuuid);
                    responseHeader = new ResponseHeader(true, VerifyConstants.ResultCodeConstants.SUCCESS_CODE, "修改邮箱成功");
                    responseData.setResponseHeader(responseHeader);
                    CacheUtil.deletCache(uuid, BandEmail.CACHE_NAMESPACE);
                } else if (BandEmail.EMAIL_NOTONE_ERROR.equals(resultData.getResponseHeader().getResultCode())) {
                    responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS, "该邮箱已经被注册，请使用其它邮箱", null);
                    responseHeader = new ResponseHeader(true, VerifyConstants.ResultCodeConstants.EMAIL_ERROR, "该邮箱已经被注册，请使用其它邮箱");
                    responseData.setResponseHeader(responseHeader);
                } else {
                    String resultMessage = resultData.getResponseHeader().getResultMessage();
                    responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_FAILURE, resultMessage, null);
                    responseHeader = new ResponseHeader(true, VerifyConstants.ResultCodeConstants.SUCCESS_CODE, "修改邮箱失败");
                    responseData.setResponseHeader(responseHeader);
                }
            }
        }
        return responseData;
    }

    

    @RequestMapping("/sendBandEmailSuccess")
    @ResponseBody
    public ModelAndView sendBandEmailSuccess(HttpServletRequest request, String email) {
        Map<String,String> model = new HashMap<String,String>();
        model.put("email", email);
        return new ModelAndView("/jsp/user/email/band_email_verification",model);
    }
    
    @RequestMapping("/sendUpdateEmailSuccess")
    @ResponseBody
    public ModelAndView sendUpdateEmailSuccess(HttpServletRequest request, String email) {
        String uuid =UUIDUtil.genId32();
        Map<String,String> model = new HashMap<String,String>();
        model.put("uuid", uuid);
        model.put("email", email);
        return new ModelAndView("/jsp/user/email/update_email_verification",model);
    }
    
    @RequestMapping("/bandEmailAuthenticate")
    public ModelAndView bandEmailAuthenticate(HttpServletRequest request) {
        String uuid = request.getParameter(SLPMallConstants.UUID.KEY_NAME);
        if("".equals(uuid)){
            uuid = UUIDUtil.genId32();
        }
        //验证时使用
        SLPClientUser userAuthenticateClient = (SLPClientUser) CacheUtil.getValue(uuid, BandEmail.CACHE_NAMESPACE, SLPClientUser.class);
        //单点登录时的client
        SLPClientUser userClient = (SLPClientUser) CacheUtil.getValue(uuid, BandEmail.CACHE_NAMESPACE, SLPClientUser.class);
        if (userAuthenticateClient == null) {
            Map<String,String> model = new HashMap<String,String>();
            model.put("confirminfo", "fail");
            model.put("uuid", uuid);
            return new ModelAndView("jsp/user/email/band-email-start",model);
        }
        request.getSession().setAttribute(SSOClientConstants.USER_SESSION_KEY, userClient);
        CacheUtil.deletCache(uuid, BandEmail.CACHE_NAMESPACE);
        UpdateUserInfoRequest updateUserInfoRequest = new UpdateUserInfoRequest();
        updateUserInfoRequest.setUserId(userClient.getUserId());
        updateUserInfoRequest.setEmailValidateFlag(BandEmail.EMAIL_CERTIFIED);
        updateUserInfoRequest.setTenantId(userClient.getTenantId());
        updateUserInfoRequest.setUserEmail(userClient.getUserEmail());
        updateUserInfoRequest.setEmailValidateFlag(BandEmail.EMAIL_CERTIFIED);
        IUcUserSV ucUser = DubboConsumerFactory.getService("iUcUserSV");
        ucUser.updateBaseInfo(updateUserInfoRequest);
        return new ModelAndView("redirect:/user/bandEmail/bandEmailAuthenticateSuccess?email="+userClient.getUserEmail());
    }
    
    @RequestMapping("/bandEmailAuthenticateSuccess")
    public ModelAndView bandEmailAuthenticateSuccess(HttpServletRequest request,String email){
        Map<String,String> model = new HashMap<String,String>();
        model.put("email", email);
        return new ModelAndView("jsp/user/email/band-email-finish",model);
    }
    
    
    @RequestMapping("/updateEmailAuthenticate")
    public ModelAndView updateSuccess(HttpServletRequest request) {
        String uuid = request.getParameter(SLPMallConstants.UUID.KEY_NAME);
        if("".equals(uuid)){
            uuid= UUIDUtil.genId32();
        }
        SLPClientUser userClient = (SLPClientUser) CacheUtil.getValue(uuid, BandEmail.CACHE_NAMESPACE, SLPClientUser.class);
        if (userClient == null) {
            SLPClientUser uClient = (SLPClientUser) request.getSession().getAttribute(SSOClientConstants.USER_SESSION_KEY);
            IUcUserSV ucUserSV = DubboConsumerFactory.getService("iUcUserSV");
            SearchUserRequest reachUserRequest = new SearchUserRequest();
            reachUserRequest.setUserId(uClient.getUserId());
            SearchUserResponse response = ucUserSV.queryBaseInfo(reachUserRequest);
            Map<String, Object> model = new HashMap<String, Object>();
            model.put("userInfo", response);
            model.put("uuid", uuid);
            model.put("confirminfo", "fail");
            return new ModelAndView("jsp/user/email/update-email-start",model);
        }
        request.getSession().setAttribute(SSOClientConstants.USER_SESSION_KEY, userClient);
        CacheUtil.deletCache(uuid, BandEmail.CACHE_NAMESPACE);
        Map<String,String> model = new HashMap<String,String>();
        model.put("uuid", UUIDUtil.genId32());
        return new ModelAndView("redirect:/user/bandEmail/updateEmailAuthenticateSuccess");
    }
    
    @RequestMapping("/updateEmailAuthenticateSuccess")
    public ModelAndView updateEmailAuthenticateSuccess(HttpServletRequest request){
        Map<String,String> model = new HashMap<String,String>();
        model.put("uuid", UUIDUtil.genId32());
        return new ModelAndView("jsp/user/email/band-email-new",model);
    }
    
    @RequestMapping("/updateBandEmailAuthenticate")
    public ModelAndView updateBandEmailAuthenticate(HttpServletRequest request){
        String uuid = request.getParameter(SLPMallConstants.UUID.KEY_NAME);
        if("".equals(uuid)){
            uuid = UUIDUtil.genId32();
        }
        SLPClientUser userClient = (SLPClientUser) CacheUtil.getValue(uuid, BandEmail.CACHE_NAMESPACE, SLPClientUser.class);
        if(userClient==null){
            SLPClientUser uClient = (SLPClientUser) request.getSession().getAttribute(SSOClientConstants.USER_SESSION_KEY);
            IUcUserSV ucUserSV = DubboConsumerFactory.getService("iUcUserSV");
            SearchUserRequest reachUserRequest = new SearchUserRequest();
            reachUserRequest.setUserId(uClient.getUserId());
            SearchUserResponse response = ucUserSV.queryBaseInfo(reachUserRequest);
            Map<String, Object> model = new HashMap<String, Object>();
            model.put("userInfo", response);
            model.put("uuid", uuid);
            model.put("confirminfo", "fail");
            return new ModelAndView("jsp/user/email/update-email-start",model);
        }
        request.getSession().setAttribute(SSOClientConstants.USER_SESSION_KEY, userClient);
        CacheUtil.deletCache(uuid, BandEmail.CACHE_NAMESPACE);
        return new ModelAndView("redirect:/user/bandEmail/updateFinishSuccess");
    }
    
    
    @RequestMapping("/updateFinishSuccess")
    public ModelAndView updateFinishSuccess(HttpServletRequest request) {
        SLPClientUser userClient = (SLPClientUser) request.getSession().getAttribute(SSOClientConstants.USER_SESSION_KEY);
        request.getSession().setAttribute(SSOClientConstants.USER_SESSION_KEY, userClient);
        UpdateUserInfoRequest updateUserInfoRequest = new UpdateUserInfoRequest();
        updateUserInfoRequest.setUserId(userClient.getUserId());
        updateUserInfoRequest.setEmailValidateFlag(BandEmail.EMAIL_CERTIFIED);
        updateUserInfoRequest.setTenantId(userClient.getTenantId());
        updateUserInfoRequest.setUserEmail(userClient.getUserEmail());
        IUcUserSV ucUser = DubboConsumerFactory.getService("iUcUserSV");
        ucUser.updateBaseInfo(updateUserInfoRequest);
        Map<String,String> model = new HashMap<String,String>();
        model.put("email", userClient.getUserEmail());
        return new ModelAndView("jsp/user/email/update-email-finish",model);
    }
   
    @RequestMapping("/getBandEmailView")
    public ModelAndView getBandEmailView(HttpServletRequest request) {
       String uuid = UUIDUtil.genId32();
       SLPClientUser userClient = (SLPClientUser) request.getSession().getAttribute(SSOClientConstants.USER_SESSION_KEY);
       IUcUserSV ucUserSV = DubboConsumerFactory.getService("iUcUserSV");
       SearchUserRequest reachUserRequest = new SearchUserRequest();
       reachUserRequest.setUserId(userClient.getUserId());
       SearchUserResponse response = ucUserSV.queryBaseInfo(reachUserRequest);
       Map<String, Object> model = new HashMap<String, Object>();
       if(response.getUserEmail()==null||"".equals(response.getUserEmail())){
           model.put("email", response.getUserEmail());
           model.put("uuid", uuid);
           model.put("confirminfo", "");
           return new ModelAndView("jsp/user/email/band-email-start",model);
       }else{
           return new ModelAndView("redirect:/user/bandEmail/setEmail");
       }
    }
}
