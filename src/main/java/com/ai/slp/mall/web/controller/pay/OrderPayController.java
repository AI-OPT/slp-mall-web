package com.ai.slp.mall.web.controller.pay;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.ai.opt.base.vo.BaseResponse;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.util.StringUtil;
import com.ai.opt.sso.client.filter.SLPClientUser;
import com.ai.opt.sso.client.filter.SSOClientConstants;
import com.ai.slp.mall.web.constants.SLPMallConstants;
import com.ai.slp.mall.web.util.ConfigUtil;
import com.ai.slp.mall.web.util.PaymentUtil;
import com.ai.slp.mall.web.util.VerifyUtil;
import com.ai.slp.order.api.orderlist.interfaces.IOrderListSV;
import com.ai.slp.order.api.orderlist.param.QueryOrderRequest;
import com.ai.slp.order.api.orderlist.param.QueryOrderResponse;
import com.ai.slp.order.api.orderpay.interfaces.IOrderPaySV;
import com.ai.slp.order.api.orderpay.param.OrderPayRequest;
import com.alibaba.fastjson.JSON;

/**
 * 订单支付 Date: 2016年5月31日 <br>
 * Copyright (c) 2016 asiainfo.com <br>
 * 
 * @author zhangxw
 */
@RestController
@RequestMapping("/pay")
public class OrderPayController {
    private static final Logger logger = Logger.getLogger(OrderPayController.class);

    /**
     * 首页
     * 
     * @param request
     * @param response
     * @return
     * @throws Exception
     * @author LiangMeng
     * @ApiDocMethod
     */
    @RequestMapping("/index")
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        /* 1.组织参数 */
        String basePath = request.getScheme() + "://" + request.getServerName() + ":"
                + request.getServerPort() + request.getContextPath();
        System.out.println("测试支付:" + basePath);
        String tenantId = ConfigUtil.getProperty("TENANT_ID");
        String returnUrl = basePath + "/demo/returnUrl";
        String notifyUrl = basePath + "/demo/notifyUrl";
        String orderAmount = "1";
        String subject = "苹果 6 plus plus";
        request.setAttribute("tenantId", tenantId);
        request.setAttribute("orderId", "123456789");
        request.setAttribute("returnUrl", returnUrl);
        request.setAttribute("notifyUrl", notifyUrl);
        request.setAttribute("orderAmount", orderAmount);
        request.setAttribute("subject", subject);
        ModelAndView view = new ModelAndView("jsp/pay/index");
        return view;
    }

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
        HttpSession session = request.getSession();
        SLPClientUser user = (SLPClientUser) session.getAttribute(SSOClientConstants.USER_SESSION_KEY);
        String basePath = request.getScheme() + "://" + request.getServerName() + ":"
                + request.getServerPort() + request.getContextPath();
        String tenantId = ConfigUtil.getProperty("TENANT_ID");
        String returnUrl = basePath + "/pay/returnUrl";
        String notifyUrl = basePath + "/pay/notifyUrl";
        String orderId = request.getParameter("orderId");
        IOrderListSV orderList = DubboConsumerFactory.getService(IOrderListSV.class);
        QueryOrderRequest orderRequest = new QueryOrderRequest();
        if (null == user) {
            orderRequest.setTenantId(SLPMallConstants.COM_TENANT_ID);
        } else {
            orderRequest.setTenantId(user.getTenantId());
        }
        orderRequest.setOrderId(Long.valueOf(orderId));
        QueryOrderResponse queryOrderResponse = orderList.queryOrder(orderRequest);
        Long payFee = queryOrderResponse.getOrdOrderVo().getPayFee();
        String orderAmount = String.valueOf(new BigDecimal(payFee).divide(new BigDecimal(1000)));
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

    /**
     * 前台返回页面
     * 
     * @param request
     * @param response
     * @return
     * @throws Exception
     * @author zhangxw
     * @ApiDocMethod
     */
    @RequestMapping("/returnUrl")
    public ModelAndView returnUrl(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        logger.info("前台回调...");
        HttpSession session = request.getSession();
        SLPClientUser user = (SLPClientUser) session.getAttribute(SSOClientConstants.USER_SESSION_KEY);
        IOrderListSV orderList = DubboConsumerFactory.getService(IOrderListSV.class);
        QueryOrderRequest orderRequest = new QueryOrderRequest();
        if (null == user) {
            orderRequest.setTenantId(SLPMallConstants.COM_TENANT_ID);
        } else {
            orderRequest.setTenantId(user.getTenantId());
        }
        ModelAndView view = null;
        String orderId = request.getParameter("orderId"); // 订单号
        String payStates = request.getParameter("payStates"); // 交易状态
        orderRequest.setOrderId(Long.valueOf(orderId));
        QueryOrderResponse queryOrderResponse = orderList.queryOrder(orderRequest);
        String orderType = queryOrderResponse.getOrdOrderVo().getOrderType();
        String orderAmount = request.getParameter("orderAmount"); // 订单金额
        request.setAttribute("orderId", orderId);
        request.setAttribute("orderType", orderType);
        request.setAttribute("orderAmount", orderAmount);
        if (SLPMallConstants.PayState.PAY_SUCCESS.equals(payStates)) {
            view=new ModelAndView("jsp/pay/paySuccess");
        }
        return view;
    }

    /**
     * 后台通知
     * 
     * @param request
     * @param response
     * @throws Exception
     * @author zhangxw
     * @ApiDocMethod
     */
    @RequestMapping("/notifyUrl")
    public void notifyUrl(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        System.out.println("开始调用后台通知");
        String tenantId = ConfigUtil.getProperty("TENANT_ID");
        logger.info("==================开始调用后台通知=======================================");
        /************************************** 从返回报文中获取数据 ****************************************/
        String outOrderId_ = request.getParameter("outOrderId"); // 第三方支付平台交易流水号
        String orderId_ = request.getParameter("orderId"); // 订单号
        String subject_ = request.getParameter("subject"); // 订单名称
        String orderAmount_ = request.getParameter("orderAmount"); // 订单金额
        String payStates_ = request.getParameter("payStates"); // 交易状态
        String notifyTime_ = request.getParameter("notifyTime"); // 交易时间
        String payType_ = request.getParameter("payOrgCode"); // 交易类型
        String infoMd5_ = request.getParameter("infoMd5"); // 加密信息

        logger.info("第三方支付平台交易流水号：" + outOrderId_);
        logger.info("订单号：" + orderId_);
        logger.info("订单名称：" + subject_);
        logger.info("订单金额：" + orderAmount_);
        logger.info("交易状态：" + payStates_);
        logger.info("交易时间：" + notifyTime_);
        logger.info("交易类型：" + payType_);
        logger.info("加密信息：" + infoMd5_);

        String md5Str = outOrderId_ + ";" + orderId_ + ";" + orderAmount_ + ";" + payStates_ + ";"+tenantId; 
        String infoMd5 = VerifyUtil.encodeParam(md5Str, ConfigUtil.getProperty("REQUEST_KEY"));
        logger.info("我的============加密信息：" + infoMd5);
        if (infoMd5_.equals(infoMd5)) {// 加密信息校验
            // 支付平台返回支付成功，调用订单支付接口
            logger.info("进来了吗检查一下：" + infoMd5);
            if (SLPMallConstants.PayState.PAY_SUCCESS.equalsIgnoreCase(payStates_)) {
                logger.info("说明状态一致：" + infoMd5);
                // 组装参数调用订单支付服务
                OrderPayRequest payRequest = new OrderPayRequest();
                BaseResponse payResponse = null;
                List<Long> orderIds = new ArrayList<Long>();
                if (!StringUtil.isBlank(orderId_)) {
                    orderIds.add(Long.parseLong(orderId_));
                }
                payRequest.setOrderIds(orderIds);
                if (!StringUtil.isBlank(orderAmount_)) {
                    payRequest.setPayFee(parseLong(Double.valueOf(orderAmount_) * 1000));// 转换成分
                }
                logger.info("金额对吗：" + infoMd5);
                payRequest.setExternalId(outOrderId_);
                payRequest.setPayType(payType_);
                payRequest.setTenantId(tenantId);
                logger.info("123123123对吗：" + JSON.toJSONString(payRequest));
                IOrderPaySV iOrderPaySV = DubboConsumerFactory.getService(IOrderPaySV.class);
                logger.info("实例化支付接口：====================" + iOrderPaySV);
                payResponse = iOrderPaySV.pay(payRequest);
                logger.info("打印返回参数：====================" + payResponse);
                logger.info("什么情况=======对吗：" + infoMd5);
                String resultCode = payResponse.getResponseHeader().getResultCode().toString();
                if (!StringUtil.isBlank(resultCode)
                        && resultCode.equalsIgnoreCase(SLPMallConstants.DUBBO.SUCCESS)) {// 支付成功
                    logger.info("订单支付成功：orderId=" + orderId_);
                    response.getWriter().print("success");
                    return;
                } else {
                    logger.info("调用订单支付服务失败：orderId=" + orderId_ + ",resultCode=" + resultCode);
                    response.getWriter().print("error");
                    return;
                }
            }
        } else {
            System.out.println("开始调用后台通知:========================");
            logger.error("加密信息不匹配：orderId=" + orderId_);
        }

    }

    /**
     * 转化订单金额为long型
     * 
     * @Description
     * @author Administrator
     * @param num
     * @return
     */
    private Long parseLong(Double num) {
        if (null == num) {
            return null;
        }
        try {
            BigDecimal bnum = new BigDecimal(num);
            return bnum.longValue();
        } catch (NumberFormatException e) {
            return null;
        }
    }

}
