package com.ai.slp.mall.web.controller.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.util.CollectionUtil;
import com.ai.opt.sso.client.filter.SLPClientUser;
import com.ai.opt.sso.client.filter.SSOClientConstants;
import com.ai.slp.balance.api.accountquery.interfaces.IAccountQuerySV;
import com.ai.slp.balance.api.accountquery.param.AccountInfoVo;
import com.ai.slp.balance.api.accountquery.param.CustIdParam;
import com.ai.slp.mall.web.constants.SLPMallConstants;
import com.ai.slp.user.api.ucuser.intefaces.IUcUserSV;
import com.ai.slp.user.api.ucuser.param.SearchUserRequest;
import com.ai.slp.user.api.ucuser.param.SearchUserResponse;

@RequestMapping("/user/security")
@Controller
public class SecuritySettingController {
   
    @RequestMapping("/securitySettings")
    public ModelAndView securitySettings(HttpServletRequest request) {
       SLPClientUser userClient = (SLPClientUser) request.getSession().getAttribute(SSOClientConstants.USER_SESSION_KEY);
       IUcUserSV ucUserSV = DubboConsumerFactory.getService("iUcUserSV");
       SearchUserRequest reachUserRequest = new SearchUserRequest();
       reachUserRequest.setUserId(userClient.getUserId());
       SearchUserResponse response = ucUserSV.queryBaseInfo(reachUserRequest);
       
       /**
        * 通过userId获取acctId
        */
       CustIdParam accountId  = new CustIdParam();
       accountId.setTenantId(SLPMallConstants.COM_TENANT_ID);
       accountId.setCustId(userClient.getUserId());
       IAccountQuerySV accountQuerySV = DubboConsumerFactory.getService(IAccountQuerySV.class);
       List<AccountInfoVo> accountList = accountQuerySV.queryAccontByCustId(accountId);
       String payCheckFlag = "0";
       if(!CollectionUtil.isEmpty(accountList)){
           AccountInfoVo accountInfoVo = accountList.get(0);
           payCheckFlag = accountInfoVo.getPayCheck();
       }
       
       Map<String, Object> model = new HashMap<String, Object>();
       model.put("userInfo", response);
       model.put("payCheckFlag", payCheckFlag);
       return new ModelAndView("jsp/user/security_settings", model);
    }
}
