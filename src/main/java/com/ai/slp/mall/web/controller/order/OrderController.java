package com.ai.slp.mall.web.controller.order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ai.net.xss.util.StringUtil;
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
import com.ai.slp.balance.api.fundquery.interfaces.IFundQuerySV;
import com.ai.slp.balance.api.fundquery.param.AccountIdParam;
import com.ai.slp.balance.api.fundquery.param.FundInfo;
import com.ai.slp.mall.web.constants.SLPMallConstants;
import com.ai.slp.mall.web.constants.SLPMallConstants.ExceptionCode;
import com.ai.slp.mall.web.constants.SLPMallConstants.ProductImageConstant;
import com.ai.slp.mall.web.model.order.InfoJsonVo;
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
            } else {
                orderReq.setUserId(SLPMallConstants.Order.VISITUSERID);
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
        SLPClientUser user = (SLPClientUser) session
                .getAttribute(SSOClientConstants.USER_SESSION_KEY);
        if (null == user) {
            tenantId = SLPMallConstants.COM_TENANT_ID;
        } else {
            tenantId = user.getTenantId();
        }
        AccountIdParam accountIdParam = new AccountIdParam();
        accountIdParam.setAccountId(user.getAcctId());// (new Long(ACCOUNT_ID));
        accountIdParam.setTenantId(user.getTenantId());// (TENANT_ID);
        FundInfo fundInfo = DubboConsumerFactory.getService(IFundQuerySV.class).queryUsableFund(
                accountIdParam);
        double balance = 0;
        if (null != fundInfo) {
            balance = ((double) fundInfo.getBalance()) / 1000;
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
    public ModelAndView usebalance(HttpServletRequest request, Model model) {
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
         String balance = request.getParameter("balance");
        // String userPassword = request.getParameter("userPassword");
        DeductParam deductParam = new DeductParam();
        deductParam.setTenantId(tenantId);
        deductParam.setSystemId("slp");
        deductParam.setExternalId(PaymentUtil.getExternalId());
        deductParam.setBusinessCode("100010");
        deductParam.setAccountId(user.getAcctId());
        deductParam.setSubsId(0);
        LOG.error("订单支付：请求参数:" + JSON.toJSONString(deductParam));
        deductParam.setTotalAmount(parseLong(Double.valueOf(balance) * 1000));
        LOG.error("订单支付：请求参数:" + JSON.toJSONString(deductParam));
        IDeductSV iDeductSV = DubboConsumerFactory.getService(IDeductSV.class);
        String deductFund = iDeductSV.deductFund(deductParam);
        LOG.error("订单支付：扣款流水:" + deductFund);
        if (!StringUtil.isBlank(deductFund)) {
            view = new ModelAndView("jsp/pay/paySuccess");
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
