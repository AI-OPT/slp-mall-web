package com.ai.slp.mall.web.controller.order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ai.net.xss.util.StringUtil;
import com.ai.opt.base.vo.BaseResponse;
import com.ai.opt.base.vo.ResponseHeader;
import com.ai.opt.sdk.components.idps.IDPSClientFactory;
import com.ai.opt.sdk.constants.ExceptCodeConstants;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.util.UUIDUtil;
import com.ai.opt.sdk.web.model.ResponseData;
import com.ai.opt.sso.client.filter.SLPClientUser;
import com.ai.opt.sso.client.filter.SSOClientConstants;
import com.ai.paas.ipaas.image.IImageClient;
import com.ai.paas.ipaas.util.JSonUtil;
import com.ai.slp.balance.api.deduct.interfaces.IDeductSV;
import com.ai.slp.balance.api.deduct.param.DeductParam;
import com.ai.slp.balance.api.deduct.param.DeductResponse;
import com.ai.slp.balance.api.deduct.param.TransSummary;
import com.ai.slp.balance.api.fundquery.interfaces.IFundQuerySV;
import com.ai.slp.balance.api.fundquery.param.AccountIdParam;
import com.ai.slp.balance.api.fundquery.param.FundInfo;
import com.ai.slp.mall.web.constants.SLPMallConstants;
import com.ai.slp.mall.web.constants.SLPMallConstants.ExceptionCode;
import com.ai.slp.mall.web.constants.SLPMallConstants.ProductImageConstant;
import com.ai.slp.mall.web.model.order.InfoJsonVo;
import com.ai.slp.mall.web.model.order.OrderBalance;
import com.ai.slp.mall.web.model.order.OrderSubmit;
import com.ai.slp.mall.web.model.order.PayOrderRequest;
import com.ai.slp.mall.web.util.CacheUtil;
import com.ai.slp.mall.web.util.PaymentUtil;
import com.ai.slp.order.api.orderlist.interfaces.IOrderListSV;
import com.ai.slp.order.api.orderlist.param.OrdOrderVo;
import com.ai.slp.order.api.orderlist.param.OrdProductVo;
import com.ai.slp.order.api.orderlist.param.ProdExtendInfoVo;
import com.ai.slp.order.api.orderlist.param.ProductImage;
import com.ai.slp.order.api.orderlist.param.QueryOrderRequest;
import com.ai.slp.order.api.orderlist.param.QueryOrderResponse;
import com.ai.slp.order.api.orderpay.interfaces.IOrderPaySV;
import com.ai.slp.order.api.orderpay.param.OrderPayRequest;
import com.ai.slp.order.api.ordertradecenter.interfaces.IOrderTradeCenterSV;
import com.ai.slp.order.api.ordertradecenter.param.OrdBaseInfo;
import com.ai.slp.order.api.ordertradecenter.param.OrdExtendInfo;
import com.ai.slp.order.api.ordertradecenter.param.OrdFeeInfo;
import com.ai.slp.order.api.ordertradecenter.param.OrdProductInfo;
import com.ai.slp.order.api.ordertradecenter.param.OrdProductResInfo;
import com.ai.slp.order.api.ordertradecenter.param.OrderTradeCenterRequest;
import com.ai.slp.order.api.ordertradecenter.param.OrderTradeCenterResponse;
import com.alibaba.fastjson.JSON;

@Controller
@RequestMapping("/order")
public class OrderController {

    private static final Logger LOG = Logger.getLogger(OrderController.class);

    /**
     * 下单并且跳转到支付页面
     */
    @RequestMapping("/orderCommit")
    @ResponseBody
    public ResponseData<String> toPayOrder(HttpServletRequest request, PayOrderRequest orderReq) {
        // 接口入参
        // OrderTradeCenterRequest
        // OrdBaseInfo
        // List<OrdProductInfo>
        // InfoJsonVo 扩展信息
        ResponseData<String> resData = null;

        try {
            HttpSession session = request.getSession();
            SLPClientUser user = (SLPClientUser) session
                    .getAttribute(SSOClientConstants.USER_SESSION_KEY);
            if (null != user) {
                orderReq.setUserId(user.getUserId());
                orderReq.setUserType(user.getUserType());
            } else {
                orderReq.setUserId(SLPMallConstants.Order.VISITUSERID);
                orderReq.setUserType(SLPMallConstants.Order.USERTYPE_VISITOR);
            }
            String orderKey = UUIDUtil.genId32();

            CacheUtil.setValue(orderKey, 300, orderReq, SLPMallConstants.Order.CACHE_NAMESPACE);
            resData = new ResponseData<String>(ExceptionCode.SUCCESS, "查询成功", orderKey);

        } catch (Exception e) {
            LOG.error(e.getMessage());
            resData = new ResponseData<String>(ExceptionCode.SYSTEM_ERROR, "查询失败", null);
        }

        return resData;

    }

    @RequestMapping("/toOrderPay")
    public String toOrderPay(HttpServletRequest request, Model model) {
        String orderId = null;
        try {
            String orderKey = request.getParameter("orderKey");
            PayOrderRequest res = (PayOrderRequest) CacheUtil.getValue(orderKey,
                    SLPMallConstants.Order.CACHE_NAMESPACE, PayOrderRequest.class);
            OrderTradeCenterRequest orderrequest = new OrderTradeCenterRequest();
            HttpSession session = request.getSession();
            SLPClientUser user = (SLPClientUser) session
                    .getAttribute(SSOClientConstants.USER_SESSION_KEY);
            if (null == user) {
                orderrequest.setTenantId(SLPMallConstants.COM_TENANT_ID);
            } else {
                orderrequest.setTenantId(user.getTenantId());
            }

            OrdBaseInfo baseInfo = new OrdBaseInfo();
            baseInfo.setUserId(res.getUserId());
            baseInfo.setOrderType(res.getOrderType());
            baseInfo.setUserType(res.getUserType());
            orderrequest.setOrdBaseInfo(baseInfo);

            List<OrdProductInfo> list = new ArrayList<OrdProductInfo>();
            OrdProductInfo opInfo = new OrdProductInfo();
            opInfo.setBasicOrgId(res.getBasicOrgId());
            opInfo.setBuySum(Integer.valueOf(res.getBuySum()));
            opInfo.setProvinceCode(res.getProvinceCode());
            opInfo.setSkuId(res.getSkuId());
            opInfo.setChargeFee(res.getChargeFee());
            list.add(opInfo);
            orderrequest.setOrdProductInfoList(list);
            OrdExtendInfo exInfo = new OrdExtendInfo();
            List<ProdExtendInfoVo> listVo = new ArrayList<ProdExtendInfoVo>();
            InfoJsonVo vo = new InfoJsonVo();
            ProdExtendInfoVo pvo = new ProdExtendInfoVo();
            pvo.setProdExtendInfoValue(res.getPhoneNum());
            listVo.add(pvo);
            vo.setProdExtendInfoVoList(listVo);
            exInfo.setInfoJson(JSON.toJSONString(vo));
            exInfo.setBatchFlag(SLPMallConstants.Order.BATCH_FLAG);
            orderrequest.setOrdExtendInfo(exInfo);
            IOrderTradeCenterSV iOrderTradeCenterSV = DubboConsumerFactory
                    .getService(com.ai.slp.order.api.ordertradecenter.interfaces.IOrderTradeCenterSV.class);
            OrderTradeCenterResponse response = iOrderTradeCenterSV.apply(orderrequest);
            if (!(response.getResponseHeader().getResultCode())
                    .equals(ExceptCodeConstants.Special.SUCCESS)) {
                LOG.info(response.getResponseHeader().getResultMessage());
                // 可能会出现创建订单失败的情况，如果这样就调到订单失败页面
                return "redirect:/order/fail";
            }
            orderId = String.valueOf(response.getOrderId());
        } catch (Exception e) {
            LOG.error(e.getMessage());
            // 遇到异常也同上处理
            // return "redirect:/home";
            return "redirect:/order/fail";
        }

        return "redirect:/order/pay?orderId=" + orderId;
    }

    @RequestMapping("/pay")
    public String toPay(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        String tenantId = "";
        double balance = 0;
        SLPClientUser user = (SLPClientUser) session
                .getAttribute(SSOClientConstants.USER_SESSION_KEY);
        if (null == user) {
            tenantId = SLPMallConstants.COM_TENANT_ID;
        } else {
            tenantId = user.getTenantId();
            AccountIdParam accountIdParam = new AccountIdParam();
            accountIdParam.setAccountId(user.getAcctId());// (new Long(ACCOUNT_ID));
            accountIdParam.setTenantId(user.getTenantId());// (TENANT_ID);
            FundInfo fundInfo = DubboConsumerFactory.getService(IFundQuerySV.class)
                    .queryUsableFund(accountIdParam);
            if (null != fundInfo) {
                balance = ((double) fundInfo.getBalance());
            }
        }
        // QueryOrderResponse queryOrder
        Long orderId = Long.valueOf(request.getParameter("orderId"));
        IOrderListSV orderList = DubboConsumerFactory.getService(IOrderListSV.class);

        QueryOrderRequest orderRequest = new QueryOrderRequest();
        orderRequest.setTenantId(tenantId);
        orderRequest.setOrderId(orderId);
        QueryOrderResponse response = orderList.queryOrder(orderRequest);
        OrdOrderVo vo = response.getOrdOrderVo();

        // 返回的list
        List<OrdProductVo> ordProdList = vo.getProductList();

        // 需要返回的List
        List<OrdProductResInfo> ordProductResList = new ArrayList<OrdProductResInfo>();

        // 循环
        for (OrdProductVo ord : ordProdList) {
            OrdProductResInfo resInfo = new OrdProductResInfo();
            resInfo.setBuySum(ord.getBuySum());
            resInfo.setSalePrice(ord.getSalePrice());
            resInfo.setSkuId(ord.getSkuId());
            resInfo.setSkuName(ord.getProdName());
            resInfo.setSkuTotalFee(ord.getTotalFee());
            IImageClient imageClient = IDPSClientFactory
                    .getImageClient(ProductImageConstant.IDPSNS);
            ProductImage productImage = ord.getProductImage();
            String vfsId = productImage.getVfsId();
            String picType = productImage.getPicType();
            String imageUrl = this.getImageUrl(imageClient, vfsId, picType);
            resInfo.setImageUrl(imageUrl);
            ordProductResList.add(resInfo);
        }
        // 设置列表

        // ordFeeInfo 属性设置
        OrdFeeInfo ordFeeInfo = new OrdFeeInfo();
        ordFeeInfo.setTotalFee(vo.getTotalFee());
        ordFeeInfo.setOperDiscountFee(vo.getDiscountFee());
        ordFeeInfo.setDiscountFee(vo.getDiscountFee());

        OrderSubmit orderSubmit = new OrderSubmit();
        orderSubmit.setBalance(balance);
        orderSubmit.setBalanceFee(0);
        orderSubmit.setExpFee(0);
        orderSubmit.setOrderId(orderId);
        orderSubmit.setOrdFeeInfo(ordFeeInfo);
        orderSubmit.setOrdProductResList(ordProductResList);
        String orderSubmitJson = JSonUtil.toJSon(orderSubmit);
        model.addAttribute("orderSubmitJson", orderSubmitJson);
        model.addAttribute("user", user);

        return "jsp/order/order_submit";
    }

    private String getImageUrl(IImageClient imageClient, String vfsId, String picType) {
        return imageClient.getImageUrl(vfsId, picType, "60x60");
    }

    @RequestMapping("/fail")
    public String toFailPage(HttpServletRequest request, Model model) {
        return "jsp/order/orderfail";
    }

    @RequestMapping("/usebalance")
    @ResponseBody
    public ResponseData<OrderBalance> usebalance(HttpServletRequest request, Model model) {
        ResponseData<OrderBalance> responseData = null;
        HttpSession session = request.getSession();
        String tenantId = "";
        SLPClientUser user = (SLPClientUser) session
                .getAttribute(SSOClientConstants.USER_SESSION_KEY);
        if (null == user) {
            tenantId = SLPMallConstants.COM_TENANT_ID;
        } else {
            tenantId = user.getTenantId();
        }
        String balance = request.getParameter("balance");
        String orderId = request.getParameter("orderId");
        String tempPassword = request.getParameter("userPassword");
        String password;
        try {
            password = StringUtil.toString(DigestUtils.md5DigestAsHex(tempPassword
                    .getBytes("UTF-8")));
            Long amount = parseLong(Double.valueOf(balance) * 1000);
            IOrderListSV orderList = DubboConsumerFactory.getService(IOrderListSV.class);
            QueryOrderRequest orderRequest = new QueryOrderRequest();
            orderRequest.setTenantId(tenantId);
            orderRequest.setOrderId(Long.valueOf(orderId));
            QueryOrderResponse queryOrderResponse = orderList.queryOrder(orderRequest);
            ResponseHeader responseHeader = queryOrderResponse.getResponseHeader();
            if (!responseHeader.isSuccess()) {
                responseData = new ResponseData<OrderBalance>(ResponseData.AJAX_STATUS_SUCCESS,
                        "订单详情查询异常", null);
                return responseData;
            }
            DeductParam deductParam = new DeductParam();
            deductParam.setTenantId(tenantId);
            deductParam.setSystemId("slp-order");
            deductParam.setExternalId(PaymentUtil.getExternalId());
            deductParam.setBusinessCode(queryOrderResponse.getOrdOrderVo().getBusiCode());
            deductParam.setAccountId(user.getAcctId());
            deductParam.setSubsId(0);
            deductParam.setCheckPwd(0);
            deductParam.setPassword(password);
            List<TransSummary> transSummaryList = new ArrayList<TransSummary>();
            TransSummary transSummary = new TransSummary();
            transSummary.setAmount(amount);
            transSummary.setSubjectId(100000);
            transSummaryList.add(transSummary);
            deductParam.setTransSummary(transSummaryList);
            deductParam.setTotalAmount(amount);
            LOG.info("订单支付：请求参数:" + JSON.toJSONString(deductParam));
            IDeductSV iDeductSV = DubboConsumerFactory.getService(IDeductSV.class);
            DeductResponse response = iDeductSV.deductFund(deductParam);
            responseHeader = response.getResponseHeader();
            LOG.info("订单支付：扣款流水:" + response.getSerialNo());
            OrderBalance orderBalance = new OrderBalance();
            orderBalance.setOrderId(orderId);
            orderBalance.setSerialNo(response.getSerialNo());
            responseData = new ResponseData<OrderBalance>(ResponseData.AJAX_STATUS_SUCCESS, "扣款成功",
                    orderBalance);
            responseData.setResponseHeader(responseHeader);
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("扣款发生错误");
            responseData = new ResponseData<OrderBalance>(ResponseData.AJAX_STATUS_SUCCESS, "扣款失败",
                    null);
        }
        return responseData;

    }

    @RequestMapping("/balancePay")
    public ModelAndView balancePay(HttpServletRequest request, Model model) {
        ModelAndView view = null;
        HttpSession session = request.getSession();
        String tenantId = "";
        SLPClientUser user = (SLPClientUser) session
                .getAttribute(SSOClientConstants.USER_SESSION_KEY);
        if (null == user) {
            tenantId = SLPMallConstants.COM_TENANT_ID;
        } else {
            tenantId = user.getTenantId();
        }
        String serialNo = request.getParameter("serialNo");
        String orderId = request.getParameter("orderId");
        try {
            IOrderListSV orderList = DubboConsumerFactory.getService(IOrderListSV.class);
            QueryOrderRequest orderRequest = new QueryOrderRequest();
            orderRequest.setTenantId(tenantId);
            orderRequest.setOrderId(Long.valueOf(orderId));
            QueryOrderResponse queryOrderResponse = orderList.queryOrder(orderRequest);
            String orderType = queryOrderResponse.getOrdOrderVo().getOrderType();
            Long payFee = queryOrderResponse.getOrdOrderVo().getPayFee();
            String orderAmount = String
                    .valueOf(new BigDecimal(payFee).divide(new BigDecimal(1000)));

            request.setAttribute("orderId", orderId);
            request.setAttribute("orderType", orderType);
            request.setAttribute("orderAmount", orderAmount);

            // 组装参数调用订单支付服务
            OrderPayRequest payRequest = new OrderPayRequest();
            List<Long> orderIds = new ArrayList<Long>();
            orderIds.add(Long.parseLong(orderId));
            payRequest.setPayFee(parseLong(Double.valueOf(payFee)));
            payRequest.setOrderIds(orderIds);
            payRequest.setExternalId(serialNo);
            payRequest.setPayType(SLPMallConstants.OrderPayType.COUNTER_PAY);
            payRequest.setTenantId(tenantId);
            IOrderPaySV iOrderPaySV = DubboConsumerFactory.getService(IOrderPaySV.class);
            BaseResponse payResponse = iOrderPaySV.pay(payRequest);
            String resultCode = payResponse.getResponseHeader().getResultCode().toString();
            if (!StringUtil.isBlank(resultCode)
                    && resultCode.equalsIgnoreCase(SLPMallConstants.DUBBO.SUCCESS)) {// 支付成功
                LOG.info("订单支付成功：orderId=" + orderId);
            } else {
                LOG.info("调用订单支付服务失败：orderId=" + orderId + ",resultCode=" + resultCode);
            }
            view = new ModelAndView("jsp/pay/paySuccess");

        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("余额支付发生错误");
        }
        return view;

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
