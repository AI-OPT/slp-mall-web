package com.ai.slp.mall.web.controller.product;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.httpclient.util.DateUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ai.opt.base.vo.ResponseHeader;
import com.ai.opt.sdk.components.dss.DSSClientFactory;
import com.ai.opt.sdk.components.idps.IDPSClientFactory;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.util.UUIDUtil;
import com.ai.opt.sdk.web.model.ResponseData;
import com.ai.opt.sso.client.filter.SLPClientUser;
import com.ai.opt.sso.client.filter.SSOClientConstants;
import com.ai.paas.ipaas.dss.base.interfaces.IDSSClient;
import com.ai.paas.ipaas.image.IImageClient;
import com.ai.paas.ipaas.util.JSonUtil;
import com.ai.slp.common.api.cache.interfaces.ICacheSV;
import com.ai.slp.common.api.cache.param.SysParam;
import com.ai.slp.common.api.cache.param.SysParamSingleCond;
import com.ai.slp.mall.web.constants.IPaasConstants;
import com.ai.slp.mall.web.constants.SLPMallConstants;
import com.ai.slp.mall.web.constants.SLPMallConstants.ExceptionCode;
import com.ai.slp.mall.web.constants.SLPMallConstants.ProductImageConstant;
import com.ai.slp.mall.web.model.order.InfoJsonVo;
import com.ai.slp.mall.web.model.order.PayOrderRequest;
import com.ai.slp.mall.web.model.product.ProductImagesVO;
import com.ai.slp.mall.web.util.CacheUtil;
import com.ai.slp.order.api.orderlist.param.ProdExtendInfoVo;
import com.ai.slp.order.api.ordertradecenter.interfaces.IOrderTradeCenterSV;
import com.ai.slp.order.api.ordertradecenter.param.OrdBaseInfo;
import com.ai.slp.order.api.ordertradecenter.param.OrdExtendInfo;
import com.ai.slp.order.api.ordertradecenter.param.OrdProductInfo;
import com.ai.slp.order.api.ordertradecenter.param.OrderTradeCenterRequest;
import com.ai.slp.order.api.ordertradecenter.param.OrderTradeCenterResponse;
import com.ai.slp.product.api.productcat.interfaces.IProductCatSV;
import com.ai.slp.product.api.productcat.param.ProductCatInfo;
import com.ai.slp.product.api.productcat.param.ProductCatUniqueReq;
import com.ai.slp.product.api.webfront.interfaces.IProductDetailSV;
import com.ai.slp.product.api.webfront.param.ProductImage;
import com.ai.slp.product.api.webfront.param.ProductSKUAttr;
import com.ai.slp.product.api.webfront.param.ProductSKUAttrValue;
import com.ai.slp.product.api.webfront.param.ProductSKUConfigResponse;
import com.ai.slp.product.api.webfront.param.ProductSKURequest;
import com.ai.slp.product.api.webfront.param.ProductSKUResponse;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

@Controller
@RequestMapping("/product")
public class ProductController {

	private static final Logger LOG = Logger.getLogger(ProductController.class);

	@RequestMapping("/detail")
	public ModelAndView detail(HttpServletRequest request, ProductSKURequest productskurequest) {
		Map<String, String> model = new HashMap<String, String>();
		try {
			// 商品属性图片大小
			String attrImageSize = "30x30";
			ProductSKUResponse producSKU = getSKUProduct(productskurequest);
			ResponseHeader responseHeader = producSKU.getResponseHeader();
			if (responseHeader.isSuccess()) {
				String productInfoHtml = producSKU.getProDetailContent();
				producSKU.setProDetailContent("");
				// 设置商品属性中的图片
				changeAttrImage(attrImageSize, producSKU);
				String producSKUJson = JSonUtil.toJSon(producSKU);
				model.put("productSKU", producSKUJson);
				// 获得商品图片
				ProductImagesVO productImages = getProductImages(producSKU);
				String productImageJson = JSonUtil.toJSon(productImages);
				model.put("productImages", productImageJson);
				// 设置skuID
				model.put("skuId", producSKU.getSkuId());
				// 设置skuAttrs
				model.put("skuAttrs", producSKU.getSaleAttrs());
				// 设置商品有效期
				String activeType = producSKU.getActiveType();
				String activeValue = getActiveDateValue(producSKU, activeType);
				model.put("activeDateValue", activeValue);
				// 设置商品详情展示信息
				model.put("productInfo", productInfoHtml);
				// 设置商品类目
				model.put("productCatId", producSKU.getProductCatId());
				setProdDetail(productInfoHtml, model);
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("商品详情查询报错：", e);
		}
		return new ModelAndView("jsp/product/product_detail", model);
	}

	private ProductSKUResponse getSKUProduct(ProductSKURequest productskurequest) {
		ProductSKUResponse producSKU = null;
		try {
			IProductDetailSV iProductDetailSV = DubboConsumerFactory.getService("iProductDetailSV");
			productskurequest.setTenantId("SLP");
			producSKU = iProductDetailSV.queryProducSKUById(productskurequest);
		} catch (Exception e) {
			LOG.error("商品详情查询报错：", e);
		}
		return producSKU;
	}

	/**
	 * 获得商品类目集
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/getProductCatList")
	@ResponseBody
	public ResponseData<List<ProductCatInfo>> getProductCatList(HttpServletRequest request, ProductCatUniqueReq queryParams) {
		ResponseData<List<ProductCatInfo>> responseData = null;
		try {
			IProductCatSV iProductCatSV = DubboConsumerFactory.getService("iProductCatSV");
			queryParams.setTenantId("SLP");
			List<ProductCatInfo> queryLinkOfCatById = iProductCatSV.queryLinkOfCatById(queryParams);
			responseData = new ResponseData<List<ProductCatInfo>>(ResponseData.AJAX_STATUS_SUCCESS, "查询成功", queryLinkOfCatById);
		} catch (Exception e) {
			responseData = new ResponseData<List<ProductCatInfo>>(ResponseData.AJAX_STATUS_FAILURE, "查询失败", null);
		}
		return responseData;
	}

	/**
	 * 获得商品有效期
	 * 
	 * @param producSKU
	 * @param activeType
	 * @return
	 */
	private String getActiveDateValue(ProductSKUResponse producSKU, String activeType) {
		String activeValue = null;
		if ("1".equals(activeType)) {
			Date activeTime = producSKU.getActiveTime();
			Date inactiveTime = producSKU.getInactiveTime();
			String activeStr = DateUtil.formatDate(activeTime, "yyyy-MM-dd");
			String inactiveStr = DateUtil.formatDate(inactiveTime, "yyyy-MM-dd");
			activeValue = activeStr + " ~ " + inactiveStr;
		} else if ("2".equals(activeType)) {
			Short activeCycle = producSKU.getActiveCycle();
			String unit = producSKU.getUnit();
			ICacheSV iCacheSV = DubboConsumerFactory.getService(ICacheSV.class);
			SysParamSingleCond params = new SysParamSingleCond("SLP", "PRODUCT", "UNIT", unit);
			SysParam sysParamSingle = iCacheSV.getSysParamSingle(params);
			if (sysParamSingle != null) {
				activeValue = "支付后" + activeCycle + sysParamSingle.getColumnDesc() + "内充值使用";
			}
		}
		return activeValue;
	}

	/**
	 * 获得商品图片
	 * 
	 * @param productSKUVO
	 * @return
	 */
	private ProductImagesVO getProductImages(ProductSKUResponse productSKUVO) {
		String productImageBigSize = "450x450";
		String productImageSmailSize = "60x60";
		List<ProductImage> productImageList = productSKUVO.getProductImageList();
		List<String> bigImagetList = new LinkedList<String>();
		List<String> smallImagetList = new LinkedList<String>();
		if (productImageList != null && productImageList.size() > 0) {
			IImageClient imageClient = IDPSClientFactory.getImageClient(ProductImageConstant.IDPSNS);
			for (ProductImage productImage : productImageList) {
				String vfsId = productImage.getVfsId();
				String picType = productImage.getPicType();
				String bigImageUrl = imageClient.getImageUrl(vfsId, picType, productImageBigSize);
				String smallImageUrl = imageClient.getImageUrl(vfsId, picType, productImageSmailSize);
				bigImagetList.add(bigImageUrl);
				smallImagetList.add(smallImageUrl);
			}
		}
		ProductImagesVO productImages = new ProductImagesVO();
		productImages.setBigImagesUrl(bigImagetList);
		productImages.setSmallImagesUrl(smallImagetList);
		return productImages;
	}

	/**
	 * 设置商品属性中的图片 返回
	 * 
	 * @param attrImageSize
	 * @param productSKUVO
	 */
	private void changeAttrImage(String attrImageSize, ProductSKUResponse productSKUVO) {
		List<ProductSKUAttr> productAttrList = productSKUVO.getProductAttrList();
		// 获取imageClient
		IImageClient imageClient = IDPSClientFactory.getImageClient(ProductImageConstant.IDPSNS);
		for (ProductSKUAttr productSKUAttr : productAttrList) {
			List<ProductSKUAttrValue> attrValueList = productSKUAttr.getAttrValueList();
			for (ProductSKUAttrValue attrValue : attrValueList) {
				ProductImage image = attrValue.getImage();
				if (image != null) {
					String vfsId = image.getVfsId();
					String picType = image.getPicType();
					String imageUrl = imageClient.getImageUrl(vfsId, picType, attrImageSize);
					attrValue.setImageUrl(imageUrl);
				}
			}
		}
	}

	/**
	 * 查询商品配置参数
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/getProductConfigParameter")
	@ResponseBody
	public ResponseData<List<ProductSKUAttr>> getProductConfigParamter(HttpServletRequest request, ProductSKURequest productSKURequest) {
		ResponseData<List<ProductSKUAttr>> responseData = null;
		try {
			IProductDetailSV iProductDetailSV = DubboConsumerFactory.getService("iProductDetailSV");
			productSKURequest.setTenantId("SLP");
			ProductSKUConfigResponse productSKUConfig = iProductDetailSV.queryProductSKUConfig(productSKURequest);

			if (productSKUConfig != null && productSKUConfig.getResponseHeader().isSuccess()) {
				List<ProductSKUAttr> configParamterList = productSKUConfig.getProductAttrList();
				responseData = new ResponseData<List<ProductSKUAttr>>(ResponseData.AJAX_STATUS_SUCCESS, "查询成功", configParamterList);
			} else {
				responseData = new ResponseData<List<ProductSKUAttr>>(ResponseData.AJAX_STATUS_SUCCESS, "无数据", null);
			}
		} catch (Exception e) {
			responseData = new ResponseData<List<ProductSKUAttr>>(ResponseData.AJAX_STATUS_FAILURE, "查询失败", null);
			LOG.error("商品详情查询报错：", e);
			e.printStackTrace();
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
			SLPClientUser user = (SLPClientUser) session.getAttribute(SSOClientConstants.USER_SESSION_KEY);
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
			PayOrderRequest res = (PayOrderRequest) CacheUtil.getValue(orderKey, SLPMallConstants.Order.CACHE_NAMESPACE, PayOrderRequest.class);
			OrderTradeCenterRequest orderrequest = new OrderTradeCenterRequest();
			HttpSession session = request.getSession();
			SLPClientUser user = (SLPClientUser) session.getAttribute(SSOClientConstants.USER_SESSION_KEY);
			OrdBaseInfo baseInfo = new OrdBaseInfo();
			if (null == user) {
				orderrequest.setTenantId(SLPMallConstants.COM_TENANT_ID);
				baseInfo.setUserType("10");
			} else {
				orderrequest.setTenantId(user.getTenantId());
				baseInfo.setUserType(user.getUserType());
			}
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
			IOrderTradeCenterSV iOrderTradeCenterSV = DubboConsumerFactory.getService(com.ai.slp.order.api.ordertradecenter.interfaces.IOrderTradeCenterSV.class);
			OrderTradeCenterResponse response = iOrderTradeCenterSV.apply(orderrequest);
			orderId = String.valueOf(response.getOrderId());
		} catch (Exception e) {
			LOG.error(e.getMessage());
			return "redirect:/home";
		}

		return "redirect:/order/pay?orderId=" + orderId;
	}

	public void setProdDetail(String fileId, Map<String, String> uiMap) {
		LOG.info("The product detail id is === " + fileId);
		if (StringUtils.isBlank(fileId)) {
			return;
		}
		IDSSClient client = DSSClientFactory.getDSSClient(IPaasConstants.DssParams.PROD_DETAIL_DSS);
		String context = client.findById(fileId);
		if (StringUtils.isNotBlank(context)) {
			JSONObject object = JSON.parseObject(context);
			uiMap.put("prodDetail", object.getString("content"));
		}
	}
}
