package com.ai.slp.mall.web.controller.user;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.opt.base.vo.ResponseHeader;
import com.ai.opt.sdk.components.ccs.CCSClientFactory;
import com.ai.opt.sdk.components.mcs.MCSClientFactory;
import com.ai.opt.sdk.util.RandomUtil;
import com.ai.opt.sdk.util.StringUtil;
import com.ai.opt.sdk.util.UUIDUtil;
import com.ai.opt.sdk.web.model.ResponseData;
import com.ai.opt.sso.client.filter.SLPClientUser;
import com.ai.opt.sso.client.filter.SSOClientConstants;
import com.ai.paas.ipaas.ccs.IConfigClient;
import com.ai.paas.ipaas.mcs.interfaces.ICacheClient;
import com.ai.runner.center.mmp.api.manager.param.SMData;
import com.ai.runner.center.mmp.api.manager.param.SMDataInfoNotify;
import com.ai.slp.mall.web.constants.SLPMallConstants;
import com.ai.slp.mall.web.constants.SLPMallConstants.BandEmail;
import com.ai.slp.mall.web.constants.VerifyConstants;
import com.ai.slp.mall.web.constants.VerifyConstants.PhoneVerifyConstants;
import com.ai.slp.mall.web.constants.VerifyConstants.ResultCodeConstants;
import com.ai.slp.mall.web.model.user.SafetyConfirmData;
import com.ai.slp.mall.web.util.CacheUtil;
import com.ai.slp.mall.web.util.IPUtil;
import com.ai.slp.mall.web.util.VerifyUtil;
import com.ai.slp.user.api.keyinfo.param.SearchGroupKeyInfoRequest;
import com.ai.slp.user.api.ucuser.param.SearchUserRequest;

@RequestMapping("/user/verify")
@Controller
public class VerifyController {
    private static final Logger LOGGER = LoggerFactory.getLogger(VerifyController.class);

    /**
     * 得到图片验证码
     * @param request
     * @param response
     * @author zhangyh7
     * @ApiDocMethod
     */
    @RequestMapping("/getImageVerifyCode")
    @ResponseBody
    public void getImageVerifyCode(HttpServletRequest request, HttpServletResponse response) {
        String cacheKey = BandEmail.CACHE_KEY_VERIFY_PICTURE + request.getSession().getId();
        BufferedImage image = VerifyUtil.getImageVerifyCode(BandEmail.CACHE_NAMESPACE, cacheKey, 100, 35);
        try {
            ImageIO.write(image, "PNG", response.getOutputStream());
        } catch (IOException e) {
            LOGGER.error("生成图片验证码错误：" + e);
            e.printStackTrace();
        }
    }

    /**
     * 发送验证码
     * 
     * @return
     */
    @RequestMapping("/sendPhoneVerify")
    @ResponseBody
    public ResponseData<String> sendVerify(HttpServletRequest request, String confirmType) {
        SLPClientUser userClient = (SLPClientUser) request.getSession().getAttribute(SSOClientConstants.USER_SESSION_KEY);
        ResponseData<String> responseData = null;
        String sessionId = request.getSession().getId();
        IConfigClient defaultConfigClient = CCSClientFactory.getDefaultConfigClient();
        try {
            if (userClient != null) {
                    // 检查ip发送验证码次数
                    ResponseData<String> checkIpSendPhone = VerifyUtil.checkIPSendPhoneCount(BandEmail.CACHE_NAMESPACE, IPUtil.getIp(request)
                            + BandEmail.CACHE_KEY_IP_SEND_PHONE_NUM);
                    if (!checkIpSendPhone.getResponseHeader().isSuccess()) {
                        return checkIpSendPhone;
                    }
                    // 发送手机验证码
                    userClient.setUserMp(request.getParameter("userMp"));
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

            } else {
                responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS, "认证信息失效", "/center/bandEmail/confirminfo");
                ResponseHeader responseHeader = new ResponseHeader(false, VerifyConstants.ResultCodeConstants.USER_INFO_NULL, "认证信息失效");
                responseData.setResponseHeader(responseHeader);
                return responseData;
            }
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
        String phoneVerifyCode = userClient.getUserMp()+";"+RandomUtil.randomNum(PhoneVerifyConstants.VERIFY_SIZE);
        // 查询是否发送过短信
        String smstimes = "1";
        String smskey = BandEmail.CACHE_KEY_CONFIRM_SEND_PHONE_NUM + userClient.getUserMp();
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
                smData.setPhone(userClient.getUserMp());
                smData.setTemplateId(PhoneVerifyConstants.TEMPLATE_RETAKE_PASSWORD_ID);
                smData.setServiceType(PhoneVerifyConstants.SERVICE_TYPE);
                dataList.add(smData);
                smDataInfoNotify.setDataList(dataList);
                smDataInfoNotify.setMsgSeq(VerifyUtil.createPhoneMsgSeq());
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
            ResponseData<String> phoneCheck = VerifyUtil.checkPhoneVerifyCode(sessionId, cacheClient, safetyConfirmData);
            String resultCode = phoneCheck.getResponseHeader().getResultCode();
            if (!VerifyConstants.ResultCodeConstants.SUCCESS_CODE.equals(resultCode)) {
                return phoneCheck;
            }
        }
        
        responseData = new ResponseData<String>(VerifyConstants.ResultCodeConstants.SUCCESS_CODE, "正确", null);
        ResponseHeader responseHeader = new ResponseHeader(true, VerifyConstants.ResultCodeConstants.SUCCESS_CODE, "正确");
        responseData.setResponseHeader(responseHeader);
        return responseData;
    }
    
    
    
    
    @RequestMapping("/checkPhone")
    @ResponseBody
    public ResponseData<String> checkPhone(HttpServletRequest request,  String userMp){
        SLPClientUser userClient = (SLPClientUser) request.getSession().getAttribute(SSOClientConstants.USER_SESSION_KEY);
        SearchUserRequest searchUserRequest = new SearchUserRequest();
        searchUserRequest.setUserMp(userMp);
        searchUserRequest.setUserType(userClient.getUserType());
        return VerifyUtil.checkPhoneOnly(searchUserRequest);
    }
    
    @RequestMapping("/checkCustName")
    @ResponseBody
    public ResponseData<String> checkCustName(HttpServletRequest request,  String custName){
        SearchGroupKeyInfoRequest searchGroupKeyInfoRequest = new SearchGroupKeyInfoRequest();
        searchGroupKeyInfoRequest.setTenantId(SLPMallConstants.COM_TENANT_ID);
        searchGroupKeyInfoRequest.setCustName(custName);
        return VerifyUtil.checkCustNameOnly(searchGroupKeyInfoRequest);
    }
}
