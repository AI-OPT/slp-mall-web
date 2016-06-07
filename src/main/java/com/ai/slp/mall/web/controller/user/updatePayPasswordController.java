package com.ai.slp.mall.web.controller.user;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
import com.ai.opt.sdk.util.RandomUtil;
import com.ai.opt.sdk.util.StringUtil;
import com.ai.opt.sdk.util.UUIDUtil;
import com.ai.opt.sdk.web.model.ResponseData;
import com.ai.opt.sso.client.filter.SLPClientUser;
import com.ai.opt.sso.client.filter.SSOClientConstants;
import com.ai.opt.sso.client.filter.SSOClientUser;
import com.ai.paas.ipaas.ccs.IConfigClient;
import com.ai.paas.ipaas.mcs.interfaces.ICacheClient;
import com.ai.runner.center.mmp.api.manager.param.SMData;
import com.ai.runner.center.mmp.api.manager.param.SMDataInfoNotify;
import com.ai.slp.mall.web.constants.SLPMallConstants;
import com.ai.slp.mall.web.constants.SLPMallConstants.BandEmail;
import com.ai.slp.mall.web.constants.SLPMallConstants.ExceptionCode;
import com.ai.slp.mall.web.constants.VerifyConstants;
import com.ai.slp.mall.web.constants.VerifyConstants.PhoneVerifyConstants;
import com.ai.slp.mall.web.constants.VerifyConstants.ResultCodeConstants;
import com.ai.slp.mall.web.model.user.SafetyConfirmData;
import com.ai.slp.mall.web.util.CacheUtil;
import com.ai.slp.mall.web.util.IPUtil;
import com.ai.slp.mall.web.util.VerifyUtil;
import com.ai.slp.user.api.register.interfaces.IRegisterSV;
import com.ai.slp.user.api.register.param.UcUserParams;
import com.ai.slp.user.api.ucuser.intefaces.IUcUserSV;
import com.ai.slp.user.api.ucuser.param.SearchUserRequest;
import com.ai.slp.user.api.ucuser.param.SearchUserResponse;

@RequestMapping("/user/payPassword")
@Controller
public class updatePayPasswordController {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(updatePayPasswordController.class);
   
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
    /**
     * 发送验证码
     * 
     * @return
     */
    @RequestMapping("/sendVerify")
    @ResponseBody
    public ResponseData<String> sendVerify(HttpServletRequest request, String confirmType) {
        SLPClientUser userClient = (SLPClientUser) request.getSession().getAttribute(SSOClientConstants.USER_SESSION_KEY);
        ResponseData<String> responseData = null;
        String sessionId = request.getSession().getId();
        IConfigClient defaultConfigClient = CCSClientFactory.getDefaultConfigClient();
        try {
           // if (userClient != null) {
                if (BandEmail.CHECK_TYPE_PHONE.equals(confirmType)) {
                    // 检查ip发送验证码次数
                    ResponseData<String> checkIpSendPhone = VerifyUtil.checkIPSendPhoneCount(BandEmail.CACHE_NAMESPACE, IPUtil.getIp(request)
                            + BandEmail.CACHE_KEY_IP_SEND_PHONE_NUM);
                    if (!checkIpSendPhone.getResponseHeader().isSuccess()) {
                        return checkIpSendPhone;
                    }
                    // 发送手机验证码
                    String isSuccess = sendPhoneVerifyCode(sessionId, userClient);
                    if ("0000".equals(isSuccess)) {
                        responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS, "短信验证码发送成功", null);
                        ResponseHeader header = new ResponseHeader();
                        header.setIsSuccess(true);
                        header.setResultCode(ResultCodeConstants.SUCCESS_CODE);
                        responseData.setResponseHeader(header);
                        return responseData;
                    } else if ("0002".equals(isSuccess)) {
                        String maxTimeStr = defaultConfigClient.get(PhoneVerifyConstants.SEND_VERIFY_MAX_TIME_KEY);
                        int maxTime = Integer.valueOf(maxTimeStr) / 60;
                        String errorMsg = maxTime + "分钟内不可重复发送";
                        responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS, errorMsg, null);
                        ResponseHeader header = new ResponseHeader();
                        header.setIsSuccess(false);
                        header.setResultCode(ResultCodeConstants.REGISTER_VERIFY_ERROR);
                        header.setResultMessage(errorMsg);
                        responseData.setResponseHeader(header);
                        return responseData;
                    } else {
                        responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_FAILURE, "短信验证码发送失败", null);
                        ResponseHeader header = new ResponseHeader();
                        header.setIsSuccess(false);
                        header.setResultCode(ResultCodeConstants.ERROR_CODE);
                        responseData.setResponseHeader(header);
                        return responseData;
                    }
                } 
           /* } else {
                responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS, "认证信息失效", "/center/bandEmail/confirminfo");
                ResponseHeader responseHeader = new ResponseHeader(false, VerifyConstants.ResultCodeConstants.USER_INFO_NULL, "认证信息失效");
                responseData.setResponseHeader(responseHeader);
                return responseData;
            }*/
        } catch (Exception e) {
            LOGGER.error("发送验证码错误：" + e);
        }
        return responseData;
    }

    /**
     * 发送手机验证码
     * 
     * @param userClient
     */
    private String sendPhoneVerifyCode(String sessionId, SLPClientUser userClient) {
        SMDataInfoNotify smDataInfoNotify = new SMDataInfoNotify();
        String phoneVerifyCode = RandomUtil.randomNum(PhoneVerifyConstants.VERIFY_SIZE);
        // 查询是否发送过短信
        String smstimes = "1";
        String smskey = BandEmail.CACHE_KEY_CONFIRM_SEND_PHONE_NUM + "13718206604";
        ICacheClient cacheClient = MCSClientFactory.getCacheClient(BandEmail.CACHE_NAMESPACE);
        IConfigClient configClient = CCSClientFactory.getDefaultConfigClient();
        String times = cacheClient.get(smskey);
        try {
            if (StringUtil.isBlank(times)) {
                // 将验证码放入缓存
                String cacheKey = BandEmail.CACHE_KEY_VERIFY_PHONE + sessionId;
                String overTimeStr = configClient.get(PhoneVerifyConstants.VERIFY_OVERTIME_KEY);
                cacheClient.setex(cacheKey, Integer.valueOf(overTimeStr), phoneVerifyCode);
                // 将发送次数放入缓存
                String maxTimeStr = configClient.get(PhoneVerifyConstants.SEND_VERIFY_MAX_TIME_KEY);
                cacheClient.setex(smskey, Integer.valueOf(maxTimeStr), smstimes);
                // 设置短息信息
                List<SMData> dataList = new LinkedList<SMData>();
                SMData smData = new SMData();
                smData.setGsmContent("${VERIFY}:" + phoneVerifyCode + "^${VALIDMINS}:" + Integer.valueOf(overTimeStr) / 60);
                //smData.setPhone(userClient.getUserMp());
                smData.setPhone("13718206604");
                smData.setTemplateId(PhoneVerifyConstants.TEMPLATE_RETAKE_PASSWORD_ID);
                smData.setServiceType(PhoneVerifyConstants.SERVICE_TYPE);
                dataList.add(smData);
                smDataInfoNotify.setDataList(dataList);
                smDataInfoNotify.setMsgSeq(VerifyUtil.createPhoneMsgSeq());
                //smDataInfoNotify.setTenantId(userClient.getTenantId());
                smDataInfoNotify.setTenantId("0");
                smDataInfoNotify.setSystemId(SLPMallConstants.SYSTEM_ID);
                boolean flag = VerifyUtil.sendPhoneInfo(smDataInfoNotify);
                if (flag) {
                    // 成功
                    return "0000";
                } else {
                    // 失败
                    return "0001";
                }
            } else {
                // 已发送
                return "0002";
            }
        } catch (Exception e) {
            LOGGER.error("发送验证码错误：" + e);
        }
        return null;
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
     * 身份认证
     * 
     * @param request
     * @return
     */
    @RequestMapping("/confirmInfo")
    @ResponseBody
    public ResponseData<String> confirmInfo(HttpServletRequest request, SafetyConfirmData safetyConfirmData) {
        ResponseData<String> responseData = null;
        ICacheClient cacheClient = MCSClientFactory.getCacheClient(BandEmail.CACHE_NAMESPACE);
        String sessionId = request.getSession().getId();
        
        // 检查短信验证码
        String verifyCodeCache = cacheClient.get(BandEmail.CACHE_KEY_VERIFY_PHONE + sessionId);
        String verifyCode = safetyConfirmData.getVerifyCode();
        ResponseData<String> phoneCheck = VerifyUtil.checkPhoneVerifyCode(verifyCode, verifyCodeCache);
        String phoneResultCode = phoneCheck.getResponseHeader().getResultCode();
        if (!VerifyConstants.ResultCodeConstants.SUCCESS_CODE.equals(phoneResultCode)) {
            return phoneCheck;
        }
        
        // 用户信息放入缓存
        String uuid = UUIDUtil.genId32();
        SLPClientUser userClient = (SLPClientUser) request.getSession().getAttribute(SSOClientConstants.USER_SESSION_KEY);
        CacheUtil.setValue(uuid, SLPMallConstants.UUID.OVERTIME, userClient, BandEmail.CACHE_NAMESPACE);
        responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS, "正确", "/center/bandEmail/setEmail?" + SLPMallConstants.UUID.KEY_NAME + "=" + uuid);
        ResponseHeader responseHeader = new ResponseHeader(true, VerifyConstants.ResultCodeConstants.SUCCESS_CODE, "正确");
        responseData.setResponseHeader(responseHeader);
        return responseData;
    }

  
   
}
