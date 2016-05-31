package com.ai.slp.mall.web.controller.pay;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ai.slp.mall.web.constants.SLPMallConstants;
import com.ai.slp.mall.web.util.ConfigUtil;
import com.ai.slp.mall.web.util.PaymentUtil;
import com.ai.slp.mall.web.util.VerifyUtil;
import com.ai.slp.order.api.orderpay.interfaces.IOrderPaySV;

/**
 * 订单支付 Date: 2016年5月31日 <br>
 * Copyright (c) 2016 asiainfo.com <br>
 * 
 * @author zhangxw
 */
@Controller
@RequestMapping(value = "/pay")
public class OrderPayController {
    private static final Logger logger = Logger.getLogger(OrderPayController.class);

    // 订单支付服务
    @Autowired
    private IOrderPaySV iOrderPaySV;

    /***
     * 订单支付
     * 
     * @Description
     * @author Administrator
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/orderPay")
    private void pay(HttpServletRequest request, HttpServletResponse response) throws Exception {
        /* 1.组织参数 */
        String basePath = request.getScheme() + "://" + request.getServerName() + ":"
                + request.getServerPort() + request.getContextPath();
        String tenantId = ConfigUtil.getProperty("TENANT_ID");
        String returnUrl = basePath + "/orderPay/returnUrl";
        String notifyUrl = basePath + "/orderPay/notifyUrl";
        String orderId = request.getParameter("orderId");
        String tempAmount = request.getParameter("orderAmount");
        String orderAmount = String.valueOf(new BigDecimal(tempAmount).divide(new BigDecimal(100)));
        String requestSource = SLPMallConstants.RequestSource.WEB;
        String payChannel = SLPMallConstants.PayChannel.BSS_SK;
        String subject = "";

        Map<String, String> map = new HashMap<String, String>();
        map.put("tenantId", tenantId);
        map.put("orderId", orderId);
        map.put("returnUrl", returnUrl);
        map.put("notifyUrl", notifyUrl);
        map.put("requestSource", requestSource);
        map.put("payChannel", payChannel);
        map.put("orderAmount", String.valueOf(orderAmount));
        map.put("subject", subject);
        // 加密
        String infoStr = orderId + VerifyUtil.SEPARATOR + orderAmount + VerifyUtil.SEPARATOR
                + notifyUrl + VerifyUtil.SEPARATOR + tenantId;
        String infoMd5 = VerifyUtil.encodeParam(infoStr, ConfigUtil.getProperty("REQUEST_KEY"));
        map.put("infoMd5", infoMd5);
        logger.info("开始前台通知:" + map);
        String htmlStr = PaymentUtil.generateAutoSubmitForm(ConfigUtil.getProperty("ACTION_URL"),
                map);
        logger.info("发起支付申请:" + htmlStr);
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(htmlStr);

    }

}
