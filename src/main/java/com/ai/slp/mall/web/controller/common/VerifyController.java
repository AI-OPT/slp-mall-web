package com.ai.slp.mall.web.controller.common;

import com.ai.net.xss.util.StringUtil;
import com.ai.opt.base.vo.ResponseHeader;
import com.ai.opt.sdk.components.ccs.CCSClientFactory;
import com.ai.opt.sdk.components.mcs.MCSClientFactory;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.util.RandomUtil;
import com.ai.opt.sdk.web.model.ResponseData;
import com.ai.paas.ipaas.mcs.interfaces.ICacheClient;
import com.ai.runner.base.exception.CallerException;
import com.ai.runner.center.mmp.api.manager.interfaces.SMSServices;
import com.ai.runner.center.mmp.api.manager.param.SMData;
import com.ai.runner.center.mmp.api.manager.param.SMDataInfoNotify;
import com.ai.slp.mall.web.constants.Constants;
import com.ai.slp.mall.web.constants.Constants.Register;
import com.ai.slp.mall.web.constants.Constants.SMSUtil;
import com.ai.slp.mall.web.constants.Constants.VerifyPhoneCode;
import com.ai.slp.mall.web.constants.VerifyConstants.PhoneVerifyConstants;
import com.ai.slp.mall.web.model.verify.PhoneVerifyCodeReq;
import com.ai.slp.mall.web.util.VerifyUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 发送短信验证码、验证短信验证码是否正确
 * Date: Apr 11, 2016 <br>
 * Copyright (c) 2016 asiainfo.com <br>
 * 
 * @author liangbs
 */
@RequestMapping("/verify")
@Controller
public class VerifyController {
    private static final Logger LOG = LoggerFactory.getLogger(VerifyController.class);

    /**
     * 发送短信验证码
     * 
     * @param request
     * @return
     * @throws Exception
     * @throws CallerException
     */
    @ResponseBody
    @RequestMapping("/toSendPhone")
    public ResponseData<String> sendPhone(PhoneVerifyCodeReq reqParam, HttpServletRequest request) throws CallerException, Exception {
        ResponseData<String> responseData = null;
        
        //短信模板ID
        String templateId = PhoneVerifyConstants.TEMPLATE_UPDATE_BDP_ID;
        if("12".equals(reqParam.getCodeTemplateId())){
            templateId = PhoneVerifyConstants.TEMPLATE_UPDATE_SFP_ID;
        } else if("13".equals(reqParam.getCodeTemplateId())){
            templateId = PhoneVerifyConstants.TEMPLATE_UPDATE_SP_ID;
        } 
        
        try {
            //获取短信发送次数
            String smstimes = "1";
            String smskey = SMSUtil.CACHE_KEY_SMS_REGISTER + reqParam.getPhone()+request.getSession().getId();
            ICacheClient cacheClient = MCSClientFactory.getCacheClient(Register.CACHE_NAMESPACE);
            String times = cacheClient.get(smskey);
            if(StringUtil.isBlank(times)){
                SMDataInfoNotify smData = new SMDataInfoNotify();
                smData.setTenantId(request.getSession().getId());
                smData.setSystemId(Constants.SYSTEM_ID);
                smData.setMsgSeq(VerifyUtil.createPhoneMsgSeq());
                List<SMData> dataList = new ArrayList<SMData>();
                SMData data = new SMData();
                data.setPhone(reqParam.getPhone());
                data.setServiceType(PhoneVerifyConstants.SERVICE_TYPE);
                data.setTemplateId(templateId);
                String identifyCode = RandomUtil.randomNum(PhoneVerifyConstants.VERIFY_SIZE);
                String codeContent = "${VERIFY}:" + identifyCode;
                String overTimeStr = CCSClientFactory.getDefaultConfigClient().get(PhoneVerifyConstants.VERIFY_OVERTIME_KEY);
                String timeContent = "^${VALIDMINS}:" + Integer.valueOf(overTimeStr)/60;
                data.setGsmContent(codeContent + timeContent);
                dataList.add(data);
                smData.setDataList(dataList);
                SMSServices sMSServices = DubboConsumerFactory.getService(SMSServices.class);
                sMSServices.dataInput(smData);
                // 存手机和验证码到缓存
                String phoneAddIdentufy =reqParam.getPhone()+";"+identifyCode;
                String key = Register.REGISTER_PHONE_KEY + request.getSession().getId();
                ICacheClient iCacheClient = MCSClientFactory.getCacheClient(Register.CACHE_NAMESPACE);
                iCacheClient.setex(key, Integer.valueOf(overTimeStr), phoneAddIdentufy);
                //存发送次数到缓存
                String maxTimeStr = CCSClientFactory.getDefaultConfigClient().get(PhoneVerifyConstants.SEND_VERIFY_MAX_TIME_KEY);
                iCacheClient.setex(smskey, Integer.valueOf(maxTimeStr), smstimes);
                ResponseHeader header = new ResponseHeader();
                header.setIsSuccess(true);
                header.setResultCode(SMSUtil.CACHE_SMS_SUCCESS_CODE);
                responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS, "验证码获取成功", null);
                responseData.setResponseHeader(header);
            }else{
                ResponseHeader header = new ResponseHeader();
                header.setIsSuccess(false);
                header.setResultCode(SMSUtil.CACHE_SMS_ERROR_CODE);
                header.setResultMessage("超过1分钟后，可重复发送");
                responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS, "超过1分钟后，可重复发送", null);
                responseData.setResponseHeader(header);
                return responseData;
            }
            
        } catch (Exception e) {
            LOG.error("验证码获取失败！", e);
            responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_FAILURE, "验证码获取失败", null);
        }
        return responseData;
    }

    /**
     * 验证短信码是否正确
     * @param request
     * @param reqParam
     */
    @ResponseBody
    @RequestMapping("/verifyPhoneCode")
    public ResponseData<String> verifyPhoneCode(PhoneVerifyCodeReq reqParam, HttpServletRequest request) {
        ResponseData<String> responseData = null;
        try {
         
            ICacheClient iCacheClient = MCSClientFactory.getCacheClient(Register.CACHE_NAMESPACE);
            ResponseHeader header = new ResponseHeader();
            header.setIsSuccess(true);
            
            // 校验短信验证码是否失效
            String phoneAddIdenti = iCacheClient.get(Register.REGISTER_PHONE_KEY + request.getSession().getId());
            String s[] = phoneAddIdenti.split(";");
            String phone=s[0];
            String vitify = s[1];
            if(!reqParam.getPhone().equals(phone)){
                header.setResultCode(VerifyPhoneCode.VERIFY_SSM_DUMPHONE_ERROR);
                header.setResultMessage("手机与发送短信手机不一致");
                 responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS, "手机与发送短信手机不一致", null);
                 responseData.setResponseHeader(header);
                 return responseData;
            }
            if(StringUtil.isBlank(vitify)){
                header.setResultCode(VerifyPhoneCode.VERIFY_SSM_OVERTIME_ERROR);
                header.setResultMessage("验证码已失效");
                 responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS, "验证码已失效", null);
                 responseData.setResponseHeader(header);
                 return responseData;
            }
            // 校验短信验证码
            if (!reqParam.getPhoneVerifyCode().equals(vitify)) {
                header.setResultCode(VerifyPhoneCode.VERIFY_SSM_ERROR);
                header.setResultMessage("短信验证码错误");
                responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS, "短信验证码错误", null);
                responseData.setResponseHeader(header);
                return responseData;
            }
            
            header.setResultCode(VerifyPhoneCode.VERIFY_SUCCESS_ID);
            header.setResultMessage("验证成功");
            responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_SUCCESS, "验证成功！", null);
            responseData.setResponseHeader(header);
            
        } catch (Exception e) {
            LOG.error("验证失败！", e);
            responseData = new ResponseData<String>(ResponseData.AJAX_STATUS_FAILURE, "验证失败！", null);
        }

        return responseData;
    }
   
}
