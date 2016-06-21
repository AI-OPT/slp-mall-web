package com.ai.slp.mall.web.controller.order;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ai.net.xss.util.StringUtil;
import com.ai.opt.base.vo.PageInfo;
import com.ai.opt.sdk.components.idps.IDPSClientFactory;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.util.BeanUtils;
import com.ai.opt.sdk.util.DateUtil;
import com.ai.opt.sdk.web.model.ResponseData;
import com.ai.opt.sso.client.filter.SLPClientUser;
import com.ai.opt.sso.client.filter.SSOClientConstants;
import com.ai.paas.ipaas.image.IImageClient;
import com.ai.paas.ipaas.util.JSonUtil;
import com.ai.slp.common.api.cache.interfaces.ICacheSV;
import com.ai.slp.common.api.cache.param.SysParam;
import com.ai.slp.common.api.cache.param.SysParamMultiCond;
import com.ai.slp.mall.web.constants.SLPMallConstants;
import com.ai.slp.mall.web.constants.SLPMallConstants.ExceptionCode;
import com.ai.slp.mall.web.constants.SLPMallConstants.ProductImageConstant;
import com.ai.slp.mall.web.model.order.OrderListQueryParams;
import com.ai.slp.order.api.orderlist.interfaces.IOrderListSV;
import com.ai.slp.order.api.orderlist.param.OrdOrderVo;
import com.ai.slp.order.api.orderlist.param.OrdProductVo;
import com.ai.slp.order.api.orderlist.param.ProductImage;
import com.ai.slp.order.api.orderlist.param.QueryOrderListRequest;
import com.ai.slp.order.api.orderlist.param.QueryOrderListResponse;
import com.ai.slp.order.api.orderlist.param.QueryOrderRequest;
import com.ai.slp.order.api.orderlist.param.QueryOrderResponse;
import com.ai.slp.product.api.webfront.interfaces.IProductDetailSV;
import com.ai.slp.product.api.webfront.param.ProductSKURequest;
import com.ai.slp.product.api.webfront.param.ProductSKUResponse;
import com.alibaba.dubbo.common.utils.StringUtils;

@Controller
@RequestMapping("/myorder")
public class MyOrderController {

	private static final Logger LOG = Logger.getLogger(MyOrderController.class);

	@RequestMapping("/list")
	public ModelAndView orderList(HttpServletRequest request) {
		ICacheSV iCacheSV = DubboConsumerFactory.getService(ICacheSV.class);
		SysParamMultiCond payQParams = new SysParamMultiCond("SLP", "ORD_OD_FEE_TOTAL", "PAY_STYLE");
		List<SysParam> payStyleParamList = iCacheSV.getSysParamList(payQParams);
		String payStyleParams = JSonUtil.toJSon(payStyleParamList);
		SysParamMultiCond orderQParams = new SysParamMultiCond("SLP", "ORD_ORDER", "ORDER_TYPE");
		List<SysParam> orderStyleParamList = iCacheSV.getSysParamList(orderQParams);
		String orderStyleParams = JSonUtil.toJSon(orderStyleParamList);
		Map<String, String> model = new HashMap<String, String>();
		model.put("payStyleParams", payStyleParams);
		model.put("orderStyleParams", orderStyleParams);
		return new ModelAndView("jsp/order/order_list", model);
	}

	@RequestMapping("/getOrderListData")
	@ResponseBody
	public ResponseData<PageInfo<OrdOrderVo>> getOrderListData(HttpServletRequest request, OrderListQueryParams queryParams) {
		ResponseData<PageInfo<OrdOrderVo>> responseData = null;
		try {
			QueryOrderListRequest queryRequest = getQueryOrderListParams(request, queryParams);
			IOrderListSV iOrderListSV = DubboConsumerFactory.getService(IOrderListSV.class);
			QueryOrderListResponse orderListResponse = iOrderListSV.queryOrderList(queryRequest);
			if (orderListResponse != null && orderListResponse.getResponseHeader().isSuccess()) {
				PageInfo<OrdOrderVo> pageInfo = orderListResponse.getPageInfo();
				setOrderListImageUrl(pageInfo);
				responseData = new ResponseData<PageInfo<OrdOrderVo>>(ExceptionCode.SUCCESS, "查询成功", pageInfo);
			} else {
				responseData = new ResponseData<PageInfo<OrdOrderVo>>(ExceptionCode.SYSTEM_ERROR, "查询失败", null);
			}
		} catch (Exception e) {
			LOG.error("查询订单列表失败：", e);
			e.printStackTrace();
			responseData = new ResponseData<PageInfo<OrdOrderVo>>(ExceptionCode.SYSTEM_ERROR, "查询失败", null);
		}
		return responseData;
	}

	/**
	 * 获取查询订单列表参数
	 * 
	 * @param queryParams
	 * @return
	 */
	private QueryOrderListRequest getQueryOrderListParams(HttpServletRequest request, OrderListQueryParams queryParams) {
		QueryOrderListRequest queryRequest = new QueryOrderListRequest();
		BeanUtils.copyProperties(queryRequest, queryParams);
		String states = queryParams.getStates();
		if(!StringUtil.isBlank(states)){
			String[] stateArray = states.split(",");
			List<String> stateList = new LinkedList<String>();
			for(String state : stateArray){
				stateList.add(state);
			}
			queryRequest.setStateList(stateList);
		}
		String searchType = queryParams.getSearchType();
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
			}else{
				queryRequest.setOrderTimeBegin(null);
			}
			String orderTimeEnd = queryRequest.getOrderTimeEnd();
			if (!StringUtil.isBlank(orderTimeEnd)) {
				queryRequest.setOrderTimeEnd(orderTimeEnd + " 23:59:59");
			}else{
				queryRequest.setOrderTimeEnd(null);
			}
		}
		queryRequest.setTenantId(SLPMallConstants.COM_TENANT_ID);
		HttpSession session = request.getSession();
		SLPClientUser user = (SLPClientUser) session.getAttribute(SSOClientConstants.USER_SESSION_KEY);
		queryRequest.setUserId(user.getUserId());
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
				if(picType!=null && vfsId!=null){
					String imageUrl = imageClient.getImageUrl(vfsId, picType, "60x60");
					productVo.setImageUrl(imageUrl);
				}
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
		OrdOrderVo orderDetail = getOrderDetail(orderRequest);
		Map<String, String> model = new HashMap<String, String>();
		if (orderDetail != null) {
			// 获取imageClient
			IImageClient imageClient = IDPSClientFactory.getImageClient(ProductImageConstant.IDPSNS);
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
	private OrdOrderVo getOrderDetail(QueryOrderRequest orderRequest) {
		OrdOrderVo responseData = null;
		try {
			orderRequest.setTenantId("SLP");
			IOrderListSV iOrderListSV = DubboConsumerFactory.getService(IOrderListSV.class);
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
	 * 检查商品是否有效（未下架 有货）
	 * 多个商品的时候 全部无效视为无效 否则视为有效，
	 * @param request
	 * @param orderRequest
	 * @return 
	 */
	@RequestMapping("/checkOrderProduct")
	@ResponseBody
	public boolean checkOrderProduct(HttpServletRequest request, QueryOrderRequest orderRequest){
		OrdOrderVo orderDetail = getOrderDetail(orderRequest);
		List<OrdProductVo> productList = orderDetail.getProductList();
		if(productList!=null && productList.size()>0){
			for(OrdProductVo ordProduct:productList){
				ProductSKUResponse skuProduct = getSKUProduct(ordProduct.getSkuId());
				String state = skuProduct.getState();
				Long usableNum = skuProduct.getUsableNum();
				if("5".equals(state) && usableNum > 0){
					//存在有效商品
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * 获得sku商品信息
	 * @param request
	 * @param skuId
	 * @return
	 */
	private ProductSKUResponse getSKUProduct(String skuId) {
		ProductSKUResponse producSKU = null;
		try {
			ProductSKURequest productskurequest = new ProductSKURequest();
			productskurequest.setSkuId(skuId);
			IProductDetailSV iProductDetailSV = DubboConsumerFactory.getService("iProductDetailSV");
			productskurequest.setTenantId("SLP");
			producSKU = iProductDetailSV.queryProducSKUById(productskurequest);
		}catch(Exception e){
			LOG.error("商品详情查询报错：", e);
		}
		return producSKU;
	}
}
