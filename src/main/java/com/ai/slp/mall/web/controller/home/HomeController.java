package com.ai.slp.mall.web.controller.home;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.util.CollectionUtil;
import com.ai.opt.sdk.web.model.ResponseData;
import com.ai.slp.common.api.servicenum.interfaces.IServiceNumSV;
import com.ai.slp.common.api.servicenum.param.ServiceNum;
import com.ai.slp.mall.web.model.compare.MapKeyComparator;
import com.ai.slp.mall.web.model.product.FastProduceRequest;
import com.ai.slp.mall.web.model.product.FastProductInfo;
import com.ai.slp.mall.web.model.product.FastProductResponse;
import com.ai.slp.mall.web.model.product.PhoneFee;
import com.ai.slp.mall.web.model.product.ProductHomeVO;
import com.ai.slp.mall.web.util.ImageUtil;
import com.ai.slp.product.api.webfront.interfaces.IProductHomeSV;
import com.ai.slp.product.api.webfront.param.FastProductInfoRes;
import com.ai.slp.product.api.webfront.param.FastProductReq;
import com.ai.slp.product.api.webfront.param.FastSkuProdInfo;
import com.ai.slp.product.api.webfront.param.ProductHomeRequest;
import com.ai.slp.product.api.webfront.param.ProductHomeResponse;
import com.alibaba.fastjson.JSON;

@RestController
public class HomeController {

	private static final Logger LOGGER = Logger.getLogger(HomeController.class);
	FastProductResponse localCache = new FastProductResponse();
	FastProductResponse nationCache = new FastProductResponse();

	@RequestMapping("/home")
	public ModelAndView index(HttpServletRequest request) {
		ModelAndView view = new ModelAndView("jsp/home/index");
		return view;
	}

	/**
	 * 流量查询
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/getFlow")
	@ResponseBody
	public ResponseData<List<ProductHomeVO>> getFlow(HttpServletRequest request, ProductHomeRequest proRequest) {
		IProductHomeSV iHomeProductSV = DubboConsumerFactory.getService("iProductHomeSV");
		proRequest.setTenantId("SLP");
		ResponseData<List<ProductHomeVO>> responseData = null;
		List<ProductHomeVO> resultList = new ArrayList<ProductHomeVO>();
		try {
			List<ProductHomeResponse> list = iHomeProductSV.queryHomeDataProduct(proRequest);
			if (!CollectionUtil.isEmpty(list)) {
				for (ProductHomeResponse data : list) {
					ProductHomeVO vo = new ProductHomeVO();
					vo.setPicUrl(
							ImageUtil.getImage(data.getProductImage().getVfsId(), data.getProductImage().getPicType()));
					vo.setProdId(data.getProdId());
					vo.setProdName(data.getProdName());
					vo.setSalePrice(data.getSalePrice());
					resultList.add(vo);
				}
			}

			responseData = new ResponseData<List<ProductHomeVO>>(ResponseData.AJAX_STATUS_SUCCESS, "查询成功", resultList);
		} catch (Exception e) {
			responseData = new ResponseData<List<ProductHomeVO>>(ResponseData.AJAX_STATUS_FAILURE, "查询失败");
		}
		return responseData;
	}

	/**
	 * 话费查询
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/getPhoneBill")
	@ResponseBody
	public ResponseData<List<ProductHomeVO>> getPhoneBill(HttpServletRequest request, ProductHomeRequest proRequest) {
		IProductHomeSV iHomeProductSV = DubboConsumerFactory.getService("iProductHomeSV");
		proRequest.setTenantId("SLP");
		ResponseData<List<ProductHomeVO>> responseData = null;
		List<ProductHomeVO> resultList = new ArrayList<ProductHomeVO>();
		try {
			List<ProductHomeResponse> list = iHomeProductSV.queryHomeDataProduct(proRequest);
			if (!CollectionUtil.isEmpty(list)) {
				for (ProductHomeResponse data : list) {
					ProductHomeVO vo = new ProductHomeVO();
					vo.setPicUrl(
							ImageUtil.getImage(data.getProductImage().getVfsId(), data.getProductImage().getPicType()));
					vo.setProdId(data.getProdId());
					vo.setProdName(data.getProdName());
					vo.setSalePrice(data.getSalePrice());
					resultList.add(vo);
				}
			}

			responseData = new ResponseData<List<ProductHomeVO>>(ResponseData.AJAX_STATUS_SUCCESS, "查询成功", resultList);
		} catch (Exception e) {
			responseData = new ResponseData<List<ProductHomeVO>>(ResponseData.AJAX_STATUS_FAILURE, "查询失败");
		}
		return responseData;
	}

	/**
	 * 热品查询
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/getHotProduct")
	@ResponseBody
	public ResponseData<List<ProductHomeVO>> getHotProduct(HttpServletRequest request, ProductHomeRequest proRequest) {
		IProductHomeSV iHomeProductSV = DubboConsumerFactory.getService("iProductHomeSV");
		proRequest.setTenantId("SLP");
		ResponseData<List<ProductHomeVO>> responseData = null;
		List<ProductHomeVO> resultList = new ArrayList<ProductHomeVO>();
		try {
			List<ProductHomeResponse> list = iHomeProductSV.queryHotProduct(proRequest);
			if (!CollectionUtil.isEmpty(list)) {
				for (ProductHomeResponse data : list) {
					ProductHomeVO vo = new ProductHomeVO();
					vo.setPicUrl(
							ImageUtil.getImage(data.getProductImage().getVfsId(), data.getProductImage().getPicType()));
					vo.setProdId(data.getProdId());
					vo.setProdName(data.getProdName());
					vo.setSalePrice(data.getSalePrice());
					vo.setProductSellPoint(data.getProductSellPoint());
					resultList.add(vo);
				}
			}

			responseData = new ResponseData<List<ProductHomeVO>>(ResponseData.AJAX_STATUS_SUCCESS, "查询成功", resultList);
		} catch (Exception e) {
			responseData = new ResponseData<List<ProductHomeVO>>(ResponseData.AJAX_STATUS_FAILURE, "查询失败");
		}
		return responseData;
	}

	/**
	 * 根据手机号获取手机的相关信息
	 */
	@RequestMapping("/getPhoneInfo")
	@ResponseBody
	public ResponseData<ServiceNum> getPhoneInfo(HttpServletRequest request) {

		IServiceNumSV iServiceNumSV = DubboConsumerFactory.getService(IServiceNumSV.class);
		String phoneNumber = request.getParameter("phoneNum");
		ResponseData<ServiceNum> responseData = null;
		try {
			ServiceNum serviceNum = iServiceNumSV.getServiceNumByPhone(phoneNumber);
			responseData = new ResponseData<ServiceNum>(ResponseData.AJAX_STATUS_SUCCESS, "获取信息成功", serviceNum);

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			responseData = new ResponseData<ServiceNum>(ResponseData.AJAX_STATUS_FAILURE, "获取信息失败", null);

		}

		return responseData;

	}

	@RequestMapping("/getFastInfo")
	@ResponseBody
	public ResponseData<FastProductResponse> getFaseChareInfo(HttpServletRequest request,
			FastProduceRequest fastProduct) {

		IProductHomeSV iProductHomeSV = DubboConsumerFactory.getService(IProductHomeSV.class);
		ResponseData<FastProductResponse> responseData = null;
		try {
			FastProductReq req = new FastProductReq();
			req.setTenantId("SLP");
			req.setBasicOrgId(fastProduct.getBasicOrgId());
			req.setProductCatId("10000010010000");
			req.setUserType("10");
			req.setProvCode(Integer.valueOf(fastProduct.getProvCode()));
			FastProductInfoRes res = iProductHomeSV.queryFastProduct(req);

			FastProductInfo info = new FastProductInfo();

			Map<String, FastSkuProdInfo> PhoneInfoMap = res.getNationMap();

			for (Entry<String, FastSkuProdInfo> map : res.getLocalMap().entrySet()) { // 去重
				PhoneInfoMap.put(map.getKey(), map.getValue());
			}
			info.setPhoneInfoMap(PhoneInfoMap);
			FastProductResponse feeRes = new FastProductResponse();
			List<PhoneFee> phoneFee = new ArrayList<PhoneFee>();
			for (Entry<String, FastSkuProdInfo> map : sortMapByKey(PhoneInfoMap).entrySet()) {
				PhoneFee fee = new PhoneFee();

				fee.setContent(map.getKey());

				fee.setSkuInfo(map.getValue());
				phoneFee.add(fee);
			}
			feeRes.setPhoneFee(phoneFee);
			responseData = new ResponseData<FastProductResponse>(ResponseData.AJAX_STATUS_SUCCESS, "获取信息成功", feeRes);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			responseData = new ResponseData<FastProductResponse>(ResponseData.AJAX_STATUS_FAILURE, "获取信息失败", null);
		}

		return responseData;
	}

	/**
	 * 首次请求流量
	 * 
	 * @param request
	 * @param fastProduct
	 * @return
	 */
	@RequestMapping("/getFastGprs")
	@ResponseBody
	public ResponseData<FastProductResponse> getGprs(HttpServletRequest request, FastProduceRequest fastProduct) {

		IProductHomeSV iProductHomeSV = DubboConsumerFactory.getService(IProductHomeSV.class);
		ResponseData<FastProductResponse> responseData = null;
		try {
			FastProductReq req = new FastProductReq();
			req.setTenantId("SLP");
			req.setBasicOrgId(fastProduct.getBasicOrgId());
			req.setProductCatId("10000010020000");
			req.setUserType("10");
			req.setProvCode(Integer.valueOf(fastProduct.getProvCode()));
			FastProductInfoRes res = iProductHomeSV.queryFastProduct(req);
			FastProductResponse feeRes = new FastProductResponse();
			List<PhoneFee> phoneFee = new ArrayList<PhoneFee>();
			if ("local".equals(fastProduct.getLocation())) {// 本地
				
				for (Entry<String, FastSkuProdInfo> map : sortMapByKey(res.getLocalMap()).entrySet()) {
					PhoneFee fee = new PhoneFee();

					fee.setContent(map.getKey());

					fee.setSkuInfo(map.getValue());
					phoneFee.add(fee);
				}
				feeRes.setPhoneFee(phoneFee);
				// 缓存本地的数据
				localCache = feeRes;
			} else {// 全国
				for (Entry<String, FastSkuProdInfo> map : sortMapByKey(res.getNationMap()).entrySet()) {
					PhoneFee fee = new PhoneFee();

					fee.setContent(map.getKey());

					fee.setSkuInfo(map.getValue());
					phoneFee.add(fee);
				}
				feeRes.setPhoneFee(phoneFee);

				// 缓存全国的数据
				nationCache = feeRes;
			}

			System.out.println(JSON.toJSONString(res));

			responseData = new ResponseData<FastProductResponse>(ResponseData.AJAX_STATUS_SUCCESS, "获取信息成功", feeRes);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			responseData = new ResponseData<FastProductResponse>(ResponseData.AJAX_STATUS_FAILURE, "获取信息失败", null);
		}

		return responseData;
	}

	// 单次请求流量
	@RequestMapping("/getOneGprs")
	@ResponseBody
	public ResponseData<FastProductResponse> getSpecialGprs(HttpServletRequest request,
			FastProduceRequest fastProduct) {
		FastProductResponse feeRes = new FastProductResponse();
		ResponseData<FastProductResponse> responseData = null;
		try {

			if ("local".equals(fastProduct.getLocation())) {// 本地

				
				feeRes = localCache;
			} else {// 全国

				
				feeRes = nationCache;
			}

			responseData = new ResponseData<FastProductResponse>(ResponseData.AJAX_STATUS_SUCCESS, "获取信息成功", feeRes);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			responseData = new ResponseData<FastProductResponse>(ResponseData.AJAX_STATUS_FAILURE, "获取信息失败", null);
		}

		return responseData;
	}
	/**
	 * 使用 Map按key进行排序
	 * @param map
	 * @return
	 */
	
	public static Map<String, FastSkuProdInfo> sortMapByKey(Map<String, FastSkuProdInfo> map) {
		if (map == null || map.isEmpty()) {
			return null;
		}

		Map<String, FastSkuProdInfo> sortMap = new TreeMap<String, FastSkuProdInfo>(new MapKeyComparator());

		sortMap.putAll(map);

		return sortMap;
	}
	//比较器类  
	

}
