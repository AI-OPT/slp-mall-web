package com.ai.slp.mall.web.controller.account;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.ai.opt.sdk.components.sequence.util.SeqUtil;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.slp.balance.api.deposit.interfaces.IDepositSV;
import com.ai.slp.balance.api.deposit.param.DepositParam;
import com.ai.slp.balance.api.deposit.param.TransSummary;
import com.ai.slp.charge.api.payment.interfaces.IPayOrderSV;
import com.ai.slp.charge.api.payment.param.ChargeDetail;
import com.ai.slp.charge.api.payment.param.PayOrderParam;
import com.ai.slp.charge.api.payment.param.PayTypeDetail;
import com.ai.slp.charge.api.payment.param.PaymentParam;
import com.ai.slp.mall.web.constants.SLPMallConstants;
import com.ai.slp.mall.web.util.ConfigUtil;
import com.ai.slp.mall.web.util.PaymentUtil;
import com.ai.slp.mall.web.util.VerifyUtil;

@RestController
@RequestMapping("/payment")
public class PayController {
	//

    private static final Logger LOG = Logger.getLogger(PayController.class);
	private static final String ACCOUNT_ID = "1111";
	private static final String TENANT_ID = "1111";

    private static final int STATUS_GOTO_PAY = 1;
    private static final int PAY_CHANNEL = 4;
	//
	
	@RequestMapping("/recharge/one")
	public ModelAndView one(HttpServletRequest request) {
		Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("testMassage", "test");
        ModelAndView view = new ModelAndView("jsp/account/recharge/one",paramMap);
        return view;
    }
	/**
	 * 提交
	 * @param request
	 * @author LiangMeng
	 * @ApiDocMethod
	 */
	@RequestMapping("/recharge/two")
	public ModelAndView two(HttpServletRequest request) {
	    String payAmount = request.getParameter("payAmount");
	    Map<String,Object> paramMap = new HashMap<String,Object>();
	    paramMap.put("payAmount", payAmount);
	    request.setAttribute("payAmount",payAmount);
	    ModelAndView view = new ModelAndView("jsp/account/recharge/two",paramMap);
	    return view;
    }
	@RequestMapping("/recharge/gotoPay")
    public void gotoPay(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String payAmount = request.getParameter("payAmount");
                
        IPayOrderSV iPayOrderSV = DubboConsumerFactory.getService(IPayOrderSV.class);
        PayOrderParam payOrderParam = new PayOrderParam();
        payOrderParam.setTenantId(TENANT_ID);
        payOrderParam.setAcctId(ACCOUNT_ID);
        payOrderParam.setPayAmount(Long.parseLong(payAmount));
        payOrderParam.setStatus(STATUS_GOTO_PAY);
        payOrderParam.setPayChannel(PAY_CHANNEL);
        String orderId = iPayOrderSV.createPayOrder(payOrderParam);
        
        
        String basePath = request.getScheme() + "://" + request.getServerName() + ":"
                + request.getServerPort() + request.getContextPath();
        String tenantId = ConfigUtil.getProperty("TENANT_ID");
        String returnUrl = basePath + "/payment/recharge/returnUrl";
        String notifyUrl = basePath + "/payment/recharge/notifyUrl";
        //String tempAmount = request.getParameter("payAmount");
        String orderAmount = "0.01";request.getParameter("payAmount");//String.valueOf(new BigDecimal(tempAmount).divide(new BigDecimal(100)));
        String requestSource = SLPMallConstants.RequestSource.WEB;
        String payChannel = SLPMallConstants.PayChannel.BSS_JF;
        String subject = "";

        Map<String, String> map = new HashMap<String, String>();
        map.put("tenantId", tenantId);
        map.put("orderId", orderId);
        map.put("returnUrl", returnUrl);
        map.put("notifyUrl", notifyUrl);
        map.put("requestSource", requestSource);
        map.put("payChannel", payChannel);
        map.put("orderAmount",orderAmount);//String.valueOf(orderAmount));
        map.put("subject", subject);
        // 加密
        String infoStr = orderId + VerifyUtil.SEPARATOR + orderAmount + VerifyUtil.SEPARATOR
                + notifyUrl + VerifyUtil.SEPARATOR + tenantId;
        String infoMd5 = VerifyUtil.encodeParam(infoStr, ConfigUtil.getProperty("REQUEST_KEY"));
        map.put("infoMd5", infoMd5);
        LOG.info("开始前台通知:" + map);
        String htmlStr = PaymentUtil.generateAutoSubmitForm(ConfigUtil.getProperty("ACTION_URL"),
                map);
        LOG.info("发起支付申请:" + htmlStr);

        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(htmlStr);

    }
	@RequestMapping("/recharge/returnUrl")
	public ModelAndView returnUrl(HttpServletRequest request) {
	    String orderId = request.getParameter("orderId"); // 订单号
        String payStates = request.getParameter("payStates"); // 交易状态
        System.out.println("支付状态为:"+payStates);
        String orderAmount = request.getParameter("orderAmount"); // 订单金额
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("orderId", orderId);
		paramMap.put("orderAmount", orderAmount);
        paramMap.put("payStates", payStates);
        ModelAndView view = new ModelAndView("jsp/account/recharge/four",paramMap);
        return view;
    }
	
	@RequestMapping("/recharge/notify")
	public void notifyUrl(HttpServletRequest request) {

        IPayOrderSV iPayOrderSV = DubboConsumerFactory.getService(IPayOrderSV.class);
	    String orderId = request.getParameter("orderId"); // 订单号
	    String payAmount = request.getParameter("orderAmount");
	    String subject = request.getParameter("subject");
	    String outOrderId = request.getParameter("outOrderId");
	    String payType = request.getParameter("payType");
	    IDepositSV depositSV = DubboConsumerFactory.getService(IDepositSV.class);
	    DepositParam param = new DepositParam();
        TransSummary summary = new TransSummary();
        summary.setAmount(Long.parseLong(payAmount));
        summary.setSubjectId(100000);
        List<TransSummary> transSummaryList = new ArrayList<TransSummary>();
        transSummaryList.add(summary);
        param.setTransSummary(transSummaryList);
        param.setAccountId(Long.parseLong(ACCOUNT_ID));
        param.setBusiDesc(subject);
        param.setBusiSerialNo(orderId);
        param.setTenantId(TENANT_ID);
        depositSV.depositFund(param);
        
        PayOrderParam payParam = new PayOrderParam();
        payParam.setOrderId(orderId);
        payParam.setPayOrgId(payType);
        payParam.setPayOrgSerial(outOrderId);
        payParam.setStatus(2);        
        PaymentParam paymentParam = new PaymentParam();
        paymentParam.setTenantId(TENANT_ID);
        paymentParam.setAcctId(Long.parseLong(ACCOUNT_ID));
        paymentParam.setOrderId(orderId);
        paymentParam.setBusiType("1");
        paymentParam.setBusiOperCode("300000");
        paymentParam.setStatus(1);
        paymentParam.setTotalFee(Long.parseLong(payAmount));
        paymentParam.setDiscountFee(0);
        paymentParam.setOperDiscountFee(0);
        paymentParam.setChargeFee(0l);
        paymentParam.setPaidFee(0l);
        paymentParam.setProvinceCode("11");
        paymentParam.setCityCode("110");
        paymentParam.setApplyChlId("slp");
        paymentParam.setOperId("slp");
        ChargeDetail chargeDetail = new ChargeDetail();
        chargeDetail.setFeeItemId("100000");
        chargeDetail.setTotalFee(Long.parseLong(payAmount)); 
        chargeDetail.setDiscountFee(0);
        chargeDetail.setOperDiscountFee(0);
        chargeDetail.setChargeFee(0l);
        chargeDetail.setFeeType("2");
        List<ChargeDetail> chargeDetails = new ArrayList<ChargeDetail>();
        chargeDetails.add(chargeDetail);
        PayTypeDetail payTypeDetail = new PayTypeDetail();
        payTypeDetail.setPayStyle(1);
        payTypeDetail.setPaidFee(Long.parseLong(payAmount));
        List<PayTypeDetail> payTypeDetails = new ArrayList<PayTypeDetail>();
        payTypeDetails.add(payTypeDetail);
        paymentParam.setChargeDetail(chargeDetails);
        paymentParam.setPayTypeDetail(payTypeDetails);
        
        iPayOrderSV.callPayOrder(payParam, paymentParam);
    }
}
