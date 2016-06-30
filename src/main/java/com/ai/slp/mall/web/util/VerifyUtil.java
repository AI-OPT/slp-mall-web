package com.ai.slp.mall.web.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Random;

import com.ai.net.xss.util.StringUtil;
import com.ai.opt.base.vo.ResponseHeader;
import com.ai.opt.sdk.components.ccs.CCSClientFactory;
import com.ai.opt.sdk.components.mail.EmailFactory;
import com.ai.opt.sdk.components.mail.EmailTemplateUtil;
import com.ai.opt.sdk.components.mcs.MCSClientFactory;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.util.RandomUtil;
import com.ai.opt.sdk.web.model.ResponseData;
import com.ai.paas.ipaas.ccs.IConfigClient;
import com.ai.paas.ipaas.mcs.interfaces.ICacheClient;
import com.ai.runner.center.mmp.api.manager.interfaces.SMSServices;
import com.ai.runner.center.mmp.api.manager.param.SMDataInfoNotify;
import com.ai.slp.mall.web.constants.SLPMallConstants.BandEmail;
import com.ai.slp.mall.web.constants.SLPMallConstants.ExceptionCode;
import com.ai.slp.mall.web.constants.SLPMallConstants;
import com.ai.slp.mall.web.constants.VerifyConstants;
import com.ai.slp.mall.web.constants.VerifyConstants.PictureVerifyConstants;
import com.ai.slp.mall.web.constants.VerifyConstants.ResultCodeConstants;
import com.ai.slp.mall.web.model.user.SafetyConfirmData;
import com.ai.slp.mall.web.model.user.SendEmailRequest;
import com.ai.slp.user.api.keyinfo.interfaces.IUcKeyInfoSV;
import com.ai.slp.user.api.keyinfo.param.SearchGroupKeyInfoRequest;
import com.ai.slp.user.api.keyinfo.param.SearchGroupKeyInfoResponse;
import com.ai.slp.user.api.register.param.UcUserParams;
import com.ai.slp.user.api.seq.interfaces.ICreateSeqSV;
import com.ai.slp.user.api.seq.param.PhoneMsgSeqResponse;
import com.ai.slp.user.api.ucuser.intefaces.IUcUserSV;
import com.ai.slp.user.api.ucuser.param.SearchUserRequest;
import com.ai.slp.user.api.ucuser.param.SearchUserResponse;


/**
 * 验签工具类
 *
 * Date: 2015年11月2日 <br>
 * Copyright (c) 2015 asiainfo.com <br>
 * @author fanpw
 */
public final class VerifyUtil {
    
    private VerifyUtil() {
        
    }

    /**
     * 默认编码
     */
    private static final String DEFAULT_CHARSET = "utf-8";
    
    /**
     * 加密组装分隔符
     */
    public static final String SEPARATOR = ";";
    
    /**
     * 检查参数
     * 
     * @param param
     * @param paramMd5
     * @param key
     * @return
     * @author LiangMeng
     */
    public static boolean checkParam(String param, String paramMd5, String key) {
        String paramMd5New = MD5.sign(param, key, DEFAULT_CHARSET);
        return paramMd5New.equals(paramMd5);
    }

    /**
     * 参数加密
     * 
     * @param param
     * @param key
     * @return
     * @author LiangMeng
     */
    public static String encodeParam(String param, String key) {
        return MD5.sign(param, key, DEFAULT_CHARSET);
    }
    
    /**
     * 检查ip发送邮箱验证码次数是否超限
     * 
     * @param namespace
     * @param key
     * @return
     */
    public static ResponseData<String> checkIPSendEmailCount(String namespace, String key) {
        ResponseData<String> responseData = null;
        ResponseHeader header = null;
        ICacheClient cacheClient = MCSClientFactory.getCacheClient(namespace);
        String countStr = cacheClient.get(key);
        //IConfigCenterClient configCenterClient = ConfigCenterFactory.getConfigCenterClient();
        IConfigClient configCenterClient = CCSClientFactory.getDefaultConfigClient();
        //限制时间
        try{
            String overTime = configCenterClient.get(VerifyConstants.EmailVerifyConstants.IP_SEND_OVERTIME_KEY);
            if (!StringUtil.isBlank(countStr)) {
                String maxNoStr = configCenterClient.get(VerifyConstants.EmailVerifyConstants.SEND_VERIFY_IP_MAX_NO_KEY);
                int maxNo = Integer.valueOf(maxNoStr);
                int count = Integer.valueOf(countStr);
                count++;
                if (count > maxNo) {
                    String message = "频繁发送邮箱，已被禁止" + Integer.valueOf(overTime) / 60 + "分钟";
                    responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS, message);
                    header = new ResponseHeader(false, VerifyConstants.ResultCodeConstants.REGISTER_VERIFY_ERROR, message);
                    responseData.setResponseHeader(header);
                    return responseData;
                }else{
                    cacheClient.setex(key, Integer.valueOf(overTime), Integer.toString(count));
                }
            }else{
                cacheClient.setex(key, Integer.valueOf(overTime), "1");
            }
            responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS, null);
            header = new ResponseHeader(true, VerifyConstants.ResultCodeConstants.REGISTER_VERIFY_ERROR, null);
            responseData.setResponseHeader(header);
            return responseData;
        }catch(Exception e){
            e.printStackTrace();
        }
         return responseData;
    }
    /**
     * 创建短信信息seq
     * 
     * @return
     */
    public static String createPhoneMsgSeq() {
        ICreateSeqSV service = DubboConsumerFactory.getService("iCreateSeqSV");
        PhoneMsgSeqResponse msgSeqResponse = service.createPhoneMsgSeq();
        if (msgSeqResponse != null) {
            ResponseHeader responseHeader = msgSeqResponse.getResponseHeader();
            String resultCode = responseHeader.getResultCode();
            if (ExceptionCode.SUCCESS.equals(resultCode)) {
                return msgSeqResponse.getMsgSeqId();
            }
        }
        return null;
    }
    
    /**
     * 发送手机信息
     * 
     * @param smDataInfoNotify
     * @return
     */
    public static boolean sendPhoneInfo(SMDataInfoNotify smDataInfoNotify) {
        SMSServices smsService = DubboConsumerFactory.getService("sMSServices");
        boolean isSuccess = true;
        try {
            smsService.dataInput(smDataInfoNotify);
        } catch (Exception e) {
            isSuccess = false;
            e.printStackTrace();
        }
        return isSuccess;
    }
    
    public static BufferedImage getImageVerifyCode(String namespace, String cacheKey, int width, int height) {
        // int width = 100, height = 38;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        // 获取图形上下文
        Graphics g = image.getGraphics();

        // 设定背景色
        g.setColor(new Color(0xDCDCDC));
        g.fillRect(0, 0, width, height);

        // 画边框
        g.setColor(Color.lightGray);
        g.drawRect(0, 0, width - 1, height - 1);

        // 取随机产生的认证码
        String verifyCode = RandomUtil.randomString(PictureVerifyConstants.VERIFY_SIZE);
        // 将认证码存入缓存
        try{
            ICacheClient cacheClient = MCSClientFactory.getCacheClient(namespace);
            IConfigClient defaultConfigClient = CCSClientFactory.getDefaultConfigClient();
            String overTimeStr = defaultConfigClient.get(PictureVerifyConstants.VERIFY_OVERTIME_KEY);
            cacheClient.setex(cacheKey, Integer.valueOf(overTimeStr), verifyCode);
            // 将认证码显示到图象中
            g.setColor(new Color(0x10a2fb));

            g.setFont(new Font("Atlantic Inline", Font.PLAIN, 30));
            String Str = verifyCode.substring(0, 1);
            g.drawString(Str, 8, 25);

            Str = verifyCode.substring(1, 2);
            g.drawString(Str, 28, 30);
            Str = verifyCode.substring(2, 3);
            g.drawString(Str, 48, 27);

            Str = verifyCode.substring(3, 4);
            g.drawString(Str, 68, 32);
            // 随机产生88个干扰点，使图象中的认证码不易被其它程序探测到
            Random random = new Random();
            for (int i = 0; i < 88; i++) {
                int x = random.nextInt(width);
                int y = random.nextInt(height);
                g.drawOval(x, y, 0, 0);
            }

            // 图象生效
            g.dispose();

        }catch(Exception e){
            e.printStackTrace();
        }
         return image;
    }
    
    /**
     * 检查ip发送手机验证码次数是否超限
     * 
     * @param namespace
     * @param key
     * @return
     */
    public static ResponseData<String> checkIPSendPhoneCount(String namespace, String key) {
        ResponseData<String> responseData = null;
        ResponseHeader header = null;
        ICacheClient cacheClient = MCSClientFactory.getCacheClient(namespace);
        String countStr = cacheClient.get(key);
        IConfigClient configCenterClient = CCSClientFactory.getDefaultConfigClient();
        //IConfigCenterClient configCenterClient = ConfigCenterFactory.getConfigCenterClient();
        try{
            String overTime = configCenterClient.get(VerifyConstants.PhoneVerifyConstants.IP_SEND_OVERTIME_KEY);
            if (!StringUtil.isBlank(countStr)) {
                String maxNoStr = configCenterClient.get(VerifyConstants.PhoneVerifyConstants.SEND_VERIFY_IP_MAX_NO_KEY);
                int maxNo = Integer.valueOf(maxNoStr);
                int count = Integer.valueOf(countStr);
                count++;
                if (count > maxNo) {
                    String message = "频繁发送手机验证码，已被禁止" + Integer.valueOf(overTime) / 60 + "分钟";
                    responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS, message);
                    header = new ResponseHeader(false, VerifyConstants.ResultCodeConstants.REGISTER_VERIFY_ERROR, message);
                    responseData.setResponseHeader(header);
                    return responseData;
                }else{
                    cacheClient.setex(key, Integer.valueOf(overTime), Integer.toString(count));
                }
            }else{
                cacheClient.setex(key, Integer.valueOf(overTime), "1");
            }
            responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS, null);
            header = new ResponseHeader(true, VerifyConstants.ResultCodeConstants.REGISTER_VERIFY_ERROR, null);
            responseData.setResponseHeader(header);
            return responseData;  
        }catch(Exception e){
           e.printStackTrace(); 
        }
         return responseData;  
    }
    /**
     * 发送邮件
     * 
     * @param emailRequest
     * @return
     */
    public static boolean sendEmail(SendEmailRequest emailRequest) {
        boolean success = true;
        String htmlcontext = EmailTemplateUtil.buildHtmlTextFromTemplate(emailRequest.getTemplateURL(), emailRequest.getData());
        try {
            EmailFactory.SendEmail(emailRequest.getTomails(), emailRequest.getCcmails(), emailRequest.getSubject(), htmlcontext);
        } catch (Exception e) {
            success = false;
            e.printStackTrace();
        }
        return success;
    }
    /**
     * 检测邮箱唯一性
     * 
     * @param email
     * @return
     */
    public static ResponseData<String> checkEmailOnly(String userId,String email) {
        ResponseData<String> responseData = null;
        ResponseHeader header = null;
        try {
            IUcUserSV iAccountManageSV = DubboConsumerFactory.getService("iUcUserSV");
            SearchUserRequest accountReq = new SearchUserRequest();
            accountReq.setUserEmail(email);
            SearchUserResponse accountQueryResponse = iAccountManageSV.queryByEmail(accountReq);
            boolean isSuccess = accountQueryResponse.getResponseHeader().isSuccess();
            if (accountQueryResponse != null) {
                if(isSuccess&&userId!=accountQueryResponse.getUserId()){
                    header = new ResponseHeader(false, VerifyConstants.ResultCodeConstants.EMAIL_ERROR, "该邮箱已经注册");
                    responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS, "该邮箱已经注册", null);
                    responseData.setResponseHeader(header);
                }else if(isSuccess&&userId==accountQueryResponse.getUserId()){
                    header = new ResponseHeader(false, VerifyConstants.ResultCodeConstants.SUCCESS_CODE, "成功");
                    responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS, "成功", null);
                    responseData.setResponseHeader(header);
                }else{ 
                    header = new ResponseHeader(false, VerifyConstants.ResultCodeConstants.SUCCESS_CODE, "成功");
                    responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS, "成功", null);
                    responseData.setResponseHeader(header);
                }
            }
        } catch (Exception e) { 
            responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_FAILURE, "邮箱校验失败", null);
        }
        return responseData;
    }
    
    /**
     * 检查图片验证码
     * 
     * @param verifyCode
     * @param cacheVerifyCode
     * @return
     */
    public static ResponseData<String> checkPictureVerifyCode(String verifyCode, String cacheVerifyCode) {
        ResponseData<String> responseData = null;
        ResponseHeader responseHeader = null;
        if (cacheVerifyCode == null) {
            responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS, "图形验证码已失效", null);
            responseHeader = new ResponseHeader(false, VerifyConstants.ResultCodeConstants.REGISTER_PICTURE_ERROR, "图形验证码已失效");
        } else if (cacheVerifyCode.compareToIgnoreCase(verifyCode) != 0) {
            responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS, "图形验证码错误", null);
            responseHeader = new ResponseHeader(false, VerifyConstants.ResultCodeConstants.REGISTER_PICTURE_ERROR, "图形验证码错误");
        } else {
            responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS, "图形验证码正确", null);
            responseHeader = new ResponseHeader(true, VerifyConstants.ResultCodeConstants.SUCCESS_CODE, "图形验证码正确");
        }
        responseData.setResponseHeader(responseHeader);
        return responseData;
    }
    
    /**
     * 检查邮箱验证码
     * 
     * @param verifyCode
     * @param cacheVerifyCode
     * @return
     */
    public static ResponseData<String> checkPhoneVerifyCode(String sessionId,ICacheClient cacheClient,SafetyConfirmData safetyConfirmData) {
        ResponseHeader header = new ResponseHeader();
        ResponseData<String> responseData = null;
        // 校验短信验证码是否失效
        String phoneAddIdenti = cacheClient.get( BandEmail.CACHE_KEY_VERIFY_PHONE + sessionId);
        if (StringUtil.isBlank(phoneAddIdenti)) {
            header.setResultCode(SLPMallConstants.SSM_OVERTIME_ERROR);
            header.setResultMessage("验证码已失效");
            responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS, "验证码已失效", null);
            responseData.setResponseHeader(header);
            return responseData;
        }
        String s[] = phoneAddIdenti.split(";");
        String phone = s[0];
        String vitify = s[1];
        
        if (!safetyConfirmData.getUserMp().equals(phone)) {
            header.setResultCode(SLPMallConstants.SSM_DUMPHONE_ERROR);
            header.setResultMessage("手机与发送短信手机不一致");
            responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS, "手机与发送短信手机不一致", null);
            responseData.setResponseHeader(header);
            return responseData;
        }
        if (StringUtil.isBlank(vitify)) {
            header.setResultCode(SLPMallConstants.SSM_OVERTIME_ERROR);
            header.setResultMessage("验证码已失效");
            responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS, "验证码已失效", null);
            responseData.setResponseHeader(header);
            return responseData;
        }
        // 校验短信验证码
        if (!safetyConfirmData.getVerifyCode().equals(vitify)) {
            header.setResultCode(SLPMallConstants.SSM_ERROR);
            header.setResultMessage("短信验证码错误");
            responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS, "短信验证码错误", null);
            responseData.setResponseHeader(header);
            return responseData;
        }
        header.setResultCode(ExceptionCode.SUCCESS);
        responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS, "正确", null);
        ResponseHeader responseHeader = new ResponseHeader(true, VerifyConstants.ResultCodeConstants.SUCCESS_CODE, "正确");
        responseData.setResponseHeader(responseHeader);
        return responseData;
    }
    /**
     * 检查邮箱验证码
     * 
     * @param safetyConfirmData
     * @param cacheClient
     * @param sessionId
     * @return
     */
    public static ResponseData<String> checkEmailVerifyCode(String verifyCode, String cacheVerifyCode) {
        ResponseData<String> responseData = null;
        ResponseHeader responseHeader = null;
        if (cacheVerifyCode == null) {
            responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS, "邮箱校验码已失效", null);
            responseHeader = new ResponseHeader(false, VerifyConstants.ResultCodeConstants.REGISTER_VERIFY_ERROR, "邮箱校验码已失效");
        } else if (!cacheVerifyCode.equals(verifyCode)) {
            responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS, "邮箱校验码已错误", null);
            responseHeader = new ResponseHeader(false, VerifyConstants.ResultCodeConstants.REGISTER_VERIFY_ERROR, "邮箱校验码错误");
        } else {
            responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS, "邮箱校验码正确", null);
            responseHeader = new ResponseHeader(true, VerifyConstants.ResultCodeConstants.SUCCESS_CODE, "邮箱校验码正确");
        }
        responseData.setResponseHeader(responseHeader);
        return responseData;
    }
    
    /**
     * 检测手机号码唯一性
     * 
     * @param phone
     * @return
     */
    public static ResponseData<String> checkPhoneOnly(SearchUserRequest request) {
        ResponseData<String> responseData = null;
        ResponseHeader header = null;
        try {
            IUcUserSV iAccountManageSV = DubboConsumerFactory.getService("iUcUserSV");
            SearchUserResponse accountQueryResponse = iAccountManageSV.queryByPhone(request);
            if (accountQueryResponse != null) {
                String resultCode = accountQueryResponse.getResponseHeader().getResultCode();
                if (resultCode.equals(ResultCodeConstants.SUCCESS_CODE)) {
                    header = new ResponseHeader(false, VerifyConstants.ResultCodeConstants.PHONE_ERROR, "该手机号码已经注册");
                    responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS, "该手机号码已经注册", null);
                    responseData.setResponseHeader(header);
                } else {
                    header = new ResponseHeader(false, VerifyConstants.ResultCodeConstants.SUCCESS_CODE, "成功");
                    responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS, "成功", null);
                    responseData.setResponseHeader(header);
                }
            }
        } catch (Exception e) {
            responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_FAILURE, "手机校验失败", null);
        }
        return responseData;
    }

    
    /**
     * 检测手机号码唯一性
     * 
     * @param phone
     * @return
     */
    public static ResponseData<String> checkCustNameOnly(SearchGroupKeyInfoRequest request) {
        ResponseData<String> responseData = null;
        ResponseHeader header = null;
        try {
            IUcKeyInfoSV ucKeyInfoSV = DubboConsumerFactory.getService("iUcKeyInfoSV");
            SearchGroupKeyInfoResponse accountQueryResponse = ucKeyInfoSV.searchGroupKeyInfo(request);
            if (accountQueryResponse != null) {
                String resultCode = accountQueryResponse.getResponseHeader().getResultCode();
                if (resultCode.equals(ResultCodeConstants.SUCCESS_CODE)) {
                    header = new ResponseHeader(false, VerifyConstants.ResultCodeConstants.CUST_NAME_NOONE_ERROR, "该企业名称已注册");
                    responseData = new ResponseData<String>(VerifyConstants.ResultCodeConstants.CUST_NAME_NOONE_ERROR, "该企业名称已注册", null);
                    responseData.setResponseHeader(header);
                } else {
                    header = new ResponseHeader(false, VerifyConstants.ResultCodeConstants.SUCCESS_CODE, "成功");
                    responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS, "成功", null);
                    responseData.setResponseHeader(header);
                }
            }
        } catch (Exception e) {
            responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_FAILURE, "企业名称注册失败", null);
        }
        return responseData;
    }
    
}
