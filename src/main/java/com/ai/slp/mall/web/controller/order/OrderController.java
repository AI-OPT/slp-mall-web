package com.ai.slp.mall.web.controller.order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ai.net.xss.util.StringUtil;
import com.ai.opt.base.vo.PageInfo;
import com.ai.opt.sdk.components.idps.IDPSClientFactory;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.util.BeanUtils;
import com.ai.opt.sdk.util.DateUtil;
import com.ai.opt.sdk.util.UUIDUtil;
import com.ai.opt.sdk.web.model.ResponseData;
import com.ai.opt.sso.client.filter.SLPClientUser;
import com.ai.opt.sso.client.filter.SSOClientConstants;
import com.ai.paas.ipaas.image.IImageClient;
import com.ai.paas.ipaas.util.JSonUtil;
import com.ai.slp.common.api.cache.interfaces.ICacheSV;
import com.ai.slp.common.api.cache.param.SysParam;
import com.ai.slp.mall.web.constants.SLPMallConstants;
import com.ai.slp.mall.web.constants.SLPMallConstants.ExceptionCode;
import com.ai.slp.mall.web.constants.SLPMallConstants.ProductImageConstant;
import com.ai.slp.mall.web.model.order.InfoJsonVo;
import com.ai.slp.mall.web.model.order.OrderListQueryParams;
import com.ai.slp.mall.web.model.order.OrderSubmit;
import com.ai.slp.mall.web.model.order.PayOrderRequest;
import com.ai.slp.mall.web.util.CacheUtil;
import com.ai.slp.order.api.orderlist.interfaces.IOrderListSV;
import com.ai.slp.order.api.orderlist.param.OrdOrderVo;
import com.ai.slp.order.api.orderlist.param.OrdProductVo;
import com.ai.slp.order.api.orderlist.param.ProdExtendInfoVo;
import com.ai.slp.order.api.orderlist.param.ProductImage;
import com.ai.slp.order.api.orderlist.param.QueryOrderListRequest;
import com.ai.slp.order.api.orderlist.param.QueryOrderListResponse;
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
import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSON;

@Controller
@RequestMapping("/order")
public class OrderController {

    private static final Logger LOG = Logger.getLogger(OrderController.class);

    @RequestMapping("/list")
    public ModelAndView orderList(HttpServletRequest request) {
        ICacheSV iCacheSV = DubboConsumerFactory.getService("iCacheSV");
        List<SysParam> payStyleParamList = iCacheSV.getSysParams("SLP", "ORD_OD_FEE_TOTAL",
                "PAY_STYLE");
        String payStyleParams = JSonUtil.toJSon(payStyleParamList);
        List<SysParam> orderStyleParamList = iCacheSV
                .getSysParams("SLP", "ORD_ORDER", "ORDER_TYPE");
        String orderStyleParams = JSonUtil.toJSon(orderStyleParamList);
        Map<String, String> model = new HashMap<String, String>();
        model.put("payStyleParams", payStyleParams);
        model.put("orderStyleParams", orderStyleParams);
        return new ModelAndView("jsp/order/order_list", model);
    }

    @RequestMapping("/getOrderListData")
    @ResponseBody
    public ResponseData<PageInfo<OrdOrderVo>> getOrderListData(HttpServletRequest request,
            OrderListQueryParams queryParams) {
        ResponseData<PageInfo<OrdOrderVo>> responseData = null;
        try {
            String searchType = queryParams.getSearchType();
            QueryOrderListRequest queryRequest = getQueryOrderListParams(request, queryParams,
                    searchType);
            IOrderListSV iOrderListSV = DubboConsumerFactory.getService("iOrderListSV");
            QueryOrderListResponse orderListResponse = iOrderListSV.queryOrderList(queryRequest);
            if (orderListResponse != null && orderListResponse.getResponseHeader().isSuccess()) {
                PageInfo<OrdOrderVo> pageInfo = orderListResponse.getPageInfo();
                setOrderListImageUrl(pageInfo);
                responseData = new ResponseData<PageInfo<OrdOrderVo>>(ExceptionCode.SUCCESS,
                        "查询成功", pageInfo);
            } else {
                responseData = new ResponseData<PageInfo<OrdOrderVo>>(ExceptionCode.SYSTEM_ERROR,
                        "查询失败", null);
            }
        } catch (Exception e) {
            LOG.error("查询订单列表失败：", e);
            e.printStackTrace();
            responseData = new ResponseData<PageInfo<OrdOrderVo>>(ExceptionCode.SYSTEM_ERROR,
                    "查询失败", null);
        }
        return responseData;
    }

    /**
     * 获取查询订单列表参数
     * 
     * @param queryParams
     * @param searchType
     * @return
     */
    private QueryOrderListRequest getQueryOrderListParams(HttpServletRequest request,
            OrderListQueryParams queryParams, String searchType) {
        QueryOrderListRequest queryRequest = new QueryOrderListRequest();
        BeanUtils.copyProperties(queryRequest, queryParams);
        if ("1".equals(searchType)) {
            String selectTime = queryParams.getSelectTime();
            queryRequest.setOrderTimeBegin(null);
            queryRequest.setOrderTimeEnd(null);
            if ("1".equals(selectTime)) {// 3月内
                String startDateStr = getBeforeMonthDate(3);
                queryRequest.setOrderTimeBegin(startDateStr);
                String endDateStr = DateUtil.getDateString("yyyy-MM-dd HH:mm:ss");
                queryRequest.setOrderTimeEnd(endDateStr);
            } else if ("2".equals(selectTime)) {// 当年
                String startDateStr = getYearBeginDate();
                queryRequest.setOrderTimeBegin(startDateStr);
                String endDateStr = DateUtil.getDateString("yyyy-MM-dd HH:mm:ss");
                queryRequest.setOrderTimeEnd(endDateStr);
            }
        } else {
            String orderTimeBegin = queryRequest.getOrderTimeBegin();
            if (!StringUtil.isBlank(orderTimeBegin)) {
                queryRequest.setOrderTimeBegin(orderTimeBegin + " 00:00:00");
            }
            String orderTimeEnd = queryRequest.getOrderTimeEnd();
            if (!StringUtil.isBlank(orderTimeEnd)) {
                queryRequest.setOrderTimeEnd(orderTimeEnd + " 23:59:59");
            }
        }
        queryRequest.setTenantId("SLP");
        HttpSession session = request.getSession();
        SLPClientUser user = (SLPClientUser) session
                .getAttribute(SSOClientConstants.USER_SESSION_KEY);
        // queryRequest.setUserId(user.getUserId());
        queryRequest.setUserId("234");
        return queryRequest;
    }

    /**
     * 设置商品图片
     * 
     * @param pageInfo
     */
    private void setOrderListImageUrl(PageInfo<OrdOrderVo> pageInfo) {
        // 获取imageClient
        IImageClient imageClient = IDPSClientFactory.getImageClient(ProductImageConstant.IDPSNS);
        List<OrdOrderVo> orderList = pageInfo.getResult();
        if (orderList != null && orderList.size() > 0) {
            for (OrdOrderVo orderVo : orderList) {
                List<OrdProductVo> productList = orderVo.getProductList();
                setProductImageUrl(imageClient, productList);
            }
        }
    }

    /**
     * 设置商品图片url
     * 
     * @param imageClient
     * @param productList
     */
    private void setProductImageUrl(IImageClient imageClient, List<OrdProductVo> productList) {
        if (productList != null && productList.size() > 0) {
            for (OrdProductVo productVo : productList) {
                ProductImage productImage = productVo.getProductImage();
                String picType = productImage.getPicType();
                String vfsId = productImage.getVfsId();
                String imageUrl = imageClient.getImageUrl(vfsId, picType, "60x60");
                productVo.setImageUrl(imageUrl);
            }
        }
    }

    /**
     * 获得本年开始日期
     * 
     * @param beforeMonth
     * @return
     */
    private String getYearBeginDate() {
        String dateString = DateUtil.getDateString("yyyy-MM-dd");
        String[] dataArray = dateString.split("-");
        int year = Integer.parseInt(dataArray[0]);
        return year + "-01-01 00:00:00";
    }

    /**
     * 获得提前几月日期（1号）
     * 
     * @param beforeMonth
     * @return
     */
    private String getBeforeMonthDate(int beforeMonth) {
        String dateString = DateUtil.getDateString("yyyy-MM-dd");
        String[] dataArray = dateString.split("-");
        int year = Integer.parseInt(dataArray[0]);
        int month = Integer.parseInt(dataArray[1]);
        String startDateStr = null;
        int startMonth = month;
        if (month > 2) {
            startMonth = month - (beforeMonth - 1);
        } else {
            startMonth = 12 + (month - (beforeMonth - 1));
        }
        if (startMonth < 10) {
            startDateStr = year + "-0" + startMonth + "-01 00:00:00";
        } else {
            startDateStr = year + "-" + startMonth + "-01 00:00:00";
        }
        return startDateStr;
    }

    @RequestMapping("/detail")
    public ModelAndView orderDetail(HttpServletRequest request, QueryOrderRequest orderRequest) {
        OrdOrderVo orderDetail = getOrderDetail(request, orderRequest);
        Map<String, String> model = new HashMap<String, String>();
        if (orderDetail != null) {
            // 获取imageClient
            IImageClient imageClient = IDPSClientFactory
                    .getImageClient(ProductImageConstant.IDPSNS);
            List<OrdProductVo> productList = orderDetail.getProductList();
            setProductImageUrl(imageClient, productList);
            String orderJSon = JSonUtil.toJSon(orderDetail);
            model.put("orderDetail", orderJSon);
        }
        String orderType = request.getParameter("orderType");
        if (StringUtils.isContains("100010", orderType)) {
            return new ModelAndView("jsp/order/order_info_detail", model);
        } else {
            return new ModelAndView("jsp/order/order_product_detail", model);
        }
    }

    /**
     * 订单详情查询
     * 
     * @param request
     * @param orderRequest
     * @return
     */
    private OrdOrderVo getOrderDetail(HttpServletRequest request, QueryOrderRequest orderRequest) {
        OrdOrderVo responseData = null;
        try {
            orderRequest.setTenantId("SLP");
            IOrderListSV iOrderListSV = DubboConsumerFactory.getService("iOrderListSV");
            QueryOrderResponse orderInfo = iOrderListSV.queryOrder(orderRequest);
            if (orderInfo != null && orderInfo.getResponseHeader().isSuccess()) {
                responseData = orderInfo.getOrdOrderVo();
            }
        } catch (Exception e) {
            LOG.error("查询订单失败：", e);
        }
        return responseData;
    }

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
