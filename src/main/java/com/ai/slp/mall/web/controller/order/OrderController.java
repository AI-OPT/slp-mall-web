package com.ai.slp.mall.web.controller.order;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ai.opt.sdk.components.idps.IDPSClientFactory;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.util.UUIDUtil;
import com.ai.opt.sdk.web.model.ResponseData;
import com.ai.opt.sso.client.filter.SLPClientUser;
import com.ai.opt.sso.client.filter.SSOClientConstants;
import com.ai.paas.ipaas.image.IImageClient;
import com.ai.paas.ipaas.util.JSonUtil;
import com.ai.slp.mall.web.constants.SLPMallConstants;
import com.ai.slp.mall.web.constants.SLPMallConstants.ExceptionCode;
import com.ai.slp.mall.web.constants.SLPMallConstants.ProductImageConstant;
import com.ai.slp.mall.web.model.order.InfoJsonVo;
import com.ai.slp.mall.web.model.order.OrderSubmit;
import com.ai.slp.mall.web.model.order.PayOrderRequest;
import com.ai.slp.mall.web.util.CacheUtil;
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
                orderReq.setUserId("900000000000000000");
            }
            String orderKey = UUIDUtil.genId32();

            CacheUtil.setValue(orderKey, 300, orderReq, SLPMallConstants.Order.CACHE_NAMESPACE);
            resData = new ResponseData<String>(ExceptionCode.SUCCESS, "查询成功", orderKey);

        } catch (Exception e) {
            resData = new ResponseData<String>(ExceptionCode.SYSTEM_ERROR, "查询失败", null);
        }

        return resData;

    }

    @RequestMapping("/toOrderPay")
    public String toOrderPay(HttpServletRequest request, Model model) {
        String orderKey = request.getParameter("orderKey");
        PayOrderRequest res = (PayOrderRequest) CacheUtil.getValue(orderKey,
                SLPMallConstants.Order.CACHE_NAMESPACE, PayOrderRequest.class);
        OrderTradeCenterRequest orderrequest = new OrderTradeCenterRequest();
        HttpSession session = request.getSession();
        SLPClientUser user = (SLPClientUser) session
                .getAttribute(SSOClientConstants.USER_SESSION_KEY);
        if (null == user) {
            orderrequest.setTenantId("SLP");
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

        String orderId = String.valueOf(response.getOrderId());

        return "redirect:/order/pay?orderId=" + orderId;
    }

    @RequestMapping("/pay")
    public String toPay(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        SLPClientUser user = (SLPClientUser) session
                .getAttribute(SSOClientConstants.USER_SESSION_KEY);
        // QueryOrderResponse queryOrder
        Long orderId = Long.valueOf(request.getParameter("orderId"));
        IOrderListSV orderList = DubboConsumerFactory.getService(IOrderListSV.class);

        QueryOrderRequest orderRequest = new QueryOrderRequest();
        if (null == user) {
            orderRequest.setTenantId("SLP");
        } else {
            orderRequest.setTenantId(user.getTenantId());
        }

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
        orderSubmit.setBalance(0);
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

}
