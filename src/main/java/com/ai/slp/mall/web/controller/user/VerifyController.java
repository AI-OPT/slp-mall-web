package com.ai.slp.mall.web.controller.user;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
import com.ai.slp.mall.web.constants.VerifyConstants.EmailVerifyConstants;
import com.ai.slp.mall.web.constants.VerifyConstants.PhoneVerifyConstants;
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
        BufferedImage image = VerifyUtil.getImageVerifyCode(BandEmail.CACHE_NAMESPACE, cacheKey, 100, 38);
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

                } else if (BandEmail.CHECK_TYPE_EMAIL.equals(confirmType)) {
                    // 检查ip发送验证码次数
                    ResponseData<String> checkIpSendEmail = VerifyUtil.checkIPSendEmailCount(BandEmail.CACHE_NAMESPACE, IPUtil.getIp(request)
                            + BandEmail.CACHE_KEY_IP_SEND_EMAIL_NUM);
                    if (!checkIpSendEmail.getResponseHeader().isSuccess()) {
                        return checkIpSendEmail;
                    }
                    // 发送邮件验证码
                    String isSuccess = sendEmailVerifyCode(sessionId, userClient);
                    if ("0000".equals(isSuccess)) {
                        responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS, "验证码发送成功", null);
                        ResponseHeader header = new ResponseHeader(true, ResultCodeConstants.SUCCESS_CODE, "验证码发送成功");
                        responseData.setResponseHeader(header);
                        return responseData;
                    } else if ("0002".equals(isSuccess)) {
                        String maxTimeStr = defaultConfigClient.get(EmailVerifyConstants.SEND_VERIFY_MAX_TIME_KEY);
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
                    responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_FAILURE, "验证码发送失败", null);
                    ResponseHeader responseHeader = new ResponseHeader(false, VerifyConstants.ResultCodeConstants.ERROR_CODE, "验证码发送失败");
                    responseData.setResponseHeader(responseHeader);
                    return responseData;
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
     * 发送邮件验证码
     * 
     * @param accountInfo
     */
    private String sendEmailVerifyCode(String sessionId, SLPClientUser userClient) {
        // 查询是否发送过短信
        String smstimes = "1";
        String smskey = BandEmail.CACHE_KEY_CONFIRM_SEND_EMAIL_NUM + userClient.getUserEmail();
        ICacheClient cacheClient = MCSClientFactory.getCacheClient(BandEmail.CACHE_NAMESPACE);
        IConfigClient configClient = CCSClientFactory.getDefaultConfigClient();
        String times = cacheClient.get(smskey);
        try {
            if (StringUtil.isBlank(times)) {
                // 邮箱验证
                String email = userClient.getUserEmail();
                String nickName = userClient.getUserNickname();
                SendEmailRequest emailRequest = new SendEmailRequest();
                emailRequest.setTomails(new String[] { email });
                emailRequest.setTemplateURL(BandEmail.TEMPLATE_SETEMAIL_URL);
                emailRequest.setSubject(BandEmail.EMAIL_SUBJECT);
                // 验证码
                String verifyCode = RandomUtil.randomNum(EmailVerifyConstants.VERIFY_SIZE);
                // 将验证码放入缓存
                String cacheKey = BandEmail.CACHE_KEY_VERIFY_EMAIL + sessionId;
                String overTimeStr = configClient.get(EmailVerifyConstants.VERIFY_OVERTIME_KEY);
                cacheClient.setex(cacheKey, Integer.valueOf(overTimeStr), verifyCode);
                // 将发送次数放入缓存
                String maxTimeStr = configClient.get(EmailVerifyConstants.SEND_VERIFY_MAX_TIME_KEY);
                cacheClient.setex(smskey, Integer.valueOf(maxTimeStr), smstimes);
                // 超时时间
                String overTime = ObjectUtils.toString(Integer.valueOf(overTimeStr) / 60);
                emailRequest.setData(new String[] { nickName, verifyCode, overTime });
                boolean flag = VerifyUtil.sendEmail(emailRequest);
                if (flag) {
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
            // 检查短信验证码
            String verifyCodeCache = cacheClient.get(BandEmail.CACHE_KEY_VERIFY_PHONE + sessionId);
            String verifyCode = safetyConfirmData.getVerifyCode();
            ResponseData<String> phoneCheck = VerifyUtil.checkPhoneVerifyCode(verifyCode, verifyCodeCache);
            String phoneResultCode = phoneCheck.getResponseHeader().getResultCode();
            if (!VerifyConstants.ResultCodeConstants.SUCCESS_CODE.equals(phoneResultCode)) {
                return phoneCheck;
            }

        }
        // 用户信息放入缓存
        String uuid = UUIDUtil.genId32();
        SSOClientUser userClient = (SSOClientUser) request.getSession().getAttribute(SSOClientConstants.USER_SESSION_KEY);
        CacheUtil.setValue(uuid, SLPMallConstants.UUID.OVERTIME, userClient, BandEmail.CACHE_NAMESPACE);
        responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS, "正确", "/center/bandEmail/setEmail?" + SLPMallConstants.UUID.KEY_NAME + "=" + uuid);
        ResponseHeader responseHeader = new ResponseHeader(true, VerifyConstants.ResultCodeConstants.SUCCESS_CODE, "正确");
        responseData.setResponseHeader(responseHeader);
        return responseData;
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
        // 检查是否重复
        return VerifyUtil.checkEmailOnly(email);
    }
    
    /**
     * 绑定邮件，发送url地址
     */
    @RequestMapping("/sendEmail")
    @ResponseBody
    public ResponseData<String> sendEmail(HttpServletRequest request, String email,String emailType) {
        ResponseData<String> responseData = null;
        String uuid = request.getParameter(SLPMallConstants.UUID.KEY_NAME);
        SLPClientUser userClient = (SLPClientUser) CacheUtil.getValue(uuid, BandEmail.CACHE_NAMESPACE, SLPClientUser.class);
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
                    SearchUserRequest searchUserReqeust = new SearchUserRequest();
                    searchUserReqeust.setUserId(userClient.getUserId());
                    searchUserReqeust.setUserEmail(email);
                    searchUserReqeust.setEmailValidateFlag(BandEmail.EMAIL_NOT_CERTIFIED);
                    searchUserReqeust.setTenantId("0");
                    IUcUserSV ucUser = DubboConsumerFactory.getService("iUcUserSV");
                    ucUser.updateBaseInfo(searchUserReqeust);
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
    
    /**
     * 发送邮件验证码(修改新邮箱时验证)
     * 
     * @param request
     * @param sessionId
     * @param email
     * @return
     */
    @RequestMapping("/sendEmailVerify")
    @ResponseBody
    public ResponseData<String> sendEmailVerifyCode(HttpServletRequest request, String email,String emailType) {
        ResponseData<String> responseData = null;
        ResponseHeader responseHeader = null;
        String uuid = request.getParameter(SLPMallConstants.UUID.KEY_NAME);
        SLPClientUser userClient = (SLPClientUser) CacheUtil.getValue(uuid, SLPMallConstants.BandEmail.CACHE_NAMESPACE, SLPClientUser.class);
        IConfigClient configClient = CCSClientFactory.getDefaultConfigClient();
        try {
            if (userClient == null) {
                responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS, "身份认证失效", "/center/bandEmail/confirminfo");
                responseHeader = new ResponseHeader(false, VerifyConstants.ResultCodeConstants.USER_INFO_NULL, "认证身份失效");
                responseData.setResponseHeader(responseHeader);
                return responseData;
            } else {
                // 检查ip发送验证码次数
                ResponseData<String> checkIpSendEmail = VerifyUtil.checkIPSendEmailCount(BandEmail.CACHE_NAMESPACE, IPUtil.getIp(request) + BandEmail.CACHE_KEY_IP_SEND_EMAIL_NUM);
                if (!checkIpSendEmail.getResponseHeader().isSuccess()) {
                    return checkIpSendEmail;
                }
                String rasultCode = sendBandEmailVerifyCode(request, email, userClient,emailType);
                if ("0000".equals(rasultCode)) {
                    responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS, "短信验证码发送成功", "短信验证码发送成功");
                    ResponseHeader header = new ResponseHeader();
                    header.setIsSuccess(true);
                    header.setResultCode(ResultCodeConstants.SUCCESS_CODE);
                    responseData.setResponseHeader(header);
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
        ;
        ICacheClient cacheClient = MCSClientFactory.getCacheClient(BandEmail.CACHE_NAMESPACE);
        IConfigClient configClient = CCSClientFactory.getDefaultConfigClient();
        String times = cacheClient.get(smskey);
        try {
            if (StringUtil.isBlank(times)) {
                //String nickName = userClient.getNickName();
                String nickName = "zhangyuehong";
                SendEmailRequest emailRequest = new SendEmailRequest();
                emailRequest.setTomails(new String[] { email });
                emailRequest.setTemplateURL(templateUrl);
                emailRequest.setSubject(BandEmail.EMAIL_SUBJECT);
                // 验证码
                String verifyCode = RandomUtil.randomNum(EmailVerifyConstants.VERIFY_SIZE);
                // 将验证码放入缓存
                String cacheKey = BandEmail.CACHE_KEY_VERIFY_SETEMAIL + email + request.getSession().getId();
                String overTimeStr = configClient.get(EmailVerifyConstants.VERIFY_OVERTIME_KEY);
                cacheClient.setex(cacheKey, Integer.valueOf(overTimeStr), verifyCode);
                // 将发送次数放入缓存
                String maxTimeStr = configClient.get(EmailVerifyConstants.SEND_VERIFY_MAX_TIME_KEY);
                cacheClient.setex(smskey, Integer.valueOf(maxTimeStr), smstimes);
                // 超时时间
                String overTime = ObjectUtils.toString(Integer.valueOf(overTimeStr) / 60);
                emailRequest.setData(new String[] { nickName, verifyCode, overTime });
                boolean isSuccess = VerifyUtil.sendEmail(emailRequest);
                if (isSuccess) {
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

   
}
