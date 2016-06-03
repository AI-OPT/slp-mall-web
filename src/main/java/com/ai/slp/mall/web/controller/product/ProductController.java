package com.ai.slp.mall.web.controller.product;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.util.DateUtil;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.ai.net.xss.util.StringUtil;
import com.ai.opt.base.vo.ResponseHeader;
import com.ai.opt.sdk.components.idps.IDPSClientFactory;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.web.model.ResponseData;
import com.ai.paas.ipaas.image.IImageClient;
import com.ai.paas.ipaas.util.JSonUtil;
import com.ai.slp.mall.web.constants.SLPMallConstants.ProductImageConstant;
import com.ai.slp.mall.web.model.product.ProductImageVO;
import com.ai.slp.product.api.webfront.interfaces.IProductDetailSV;
import com.ai.slp.product.api.webfront.param.ProductImage;
import com.ai.slp.product.api.webfront.param.ProductSKUAttr;
import com.ai.slp.product.api.webfront.param.ProductSKUAttrValue;
import com.ai.slp.product.api.webfront.param.ProductSKUConfigResponse;
import com.ai.slp.product.api.webfront.param.ProductSKURequest;
import com.ai.slp.product.api.webfront.param.ProductSKUResponse;

@RestController
@RequestMapping("/product")
public class ProductController {

	private static final Logger LOG = Logger.getLogger(ProductController.class);

	@RequestMapping("/detail")
	public ModelAndView index(HttpServletRequest request) {
		Map<String, String> model = new HashMap<String, String>();
		try {
			// 商品属性图片大小
			String attrImageSize = "30x30";

			String skuId = StringUtil.toString(request.getParameter("skuId"));
			String skuAttrs = StringUtil.toString(request.getParameter("skuAttrs"));
			IProductDetailSV iProductDetailSV = DubboConsumerFactory.getService("iProductDetailSV");
			ProductSKURequest productskurequest = new ProductSKURequest();
			productskurequest.setSkuId(skuId);
			productskurequest.setSkuAttrs(skuAttrs);

			productskurequest.setTenantId("SLP");
			ProductSKUResponse producSKU = iProductDetailSV.queryProducSKUById(productskurequest);
			ResponseHeader responseHeader = producSKU.getResponseHeader();
			if (responseHeader.isSuccess()) {
				String productInfoHtml = producSKU.getProDetailContent();
				producSKU.setProDetailContent("");
				// 设置商品属性中的图片
				changeAttrImage(attrImageSize, producSKU);
				String producSKUJson = JSonUtil.toJSon(producSKU);
				model.put("productSKU", producSKUJson);
				// 获得商品图片
				List<ProductImageVO> productImageVOList = getProductImages(producSKU);
				String productImageJson = JSonUtil.toJSon(productImageVOList);
				model.put("imageArrayList", productImageJson);
				// 设置skuID
				model.put("skuId", skuId);
				// 设置skuAttrs
				model.put("skuAttrs", skuAttrs);
				// 设置商品有效期
				String activeType = producSKU.getActiveType();
				String activeValue = getActiveDateValue(producSKU, activeType);
				model.put("activeDateValue", activeValue);
				// 设置商品详情展示信息
				model.put("productInfo", productInfoHtml);
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("商品详情查询报错：", e);
		}
		return new ModelAndView("jsp/product/product_detail", model);
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
			Integer activeCycle = producSKU.getActiveCycle();
			String unit = producSKU.getUnit();
			activeValue = "支付后" + activeCycle + unit + "内充值使用";
		}
		return activeValue;
	}

	/**
	 * 获得商品图片
	 * 
	 * @param productSKUVO
	 * @return
	 */
	private List<ProductImageVO> getProductImages(ProductSKUResponse productSKUVO) {
		String productImageBigSize = "360x457";
		String productImageSmailSize = "60x60";
		List<ProductImage> productImageList = productSKUVO.getProductImageList();
		List<ProductImageVO> productImageArrayList = new LinkedList<ProductImageVO>();
		if (productImageList != null && productImageList.size() > 0) {
			IImageClient imageClient = IDPSClientFactory.getImageClient(ProductImageConstant.IDPSNS);
			for (ProductImage productImage : productImageList) {
				String vfsId = productImage.getVfsId();
				String picType = productImage.getPicType();
				String bigImageUrl = imageClient.getImageUrl(vfsId, picType, productImageBigSize);
				String smallImageUrl = imageClient.getImageUrl(vfsId, picType, productImageSmailSize);
				ProductImageVO productImageVO = new ProductImageVO();
				productImageVO.setBigImageUrl(bigImageUrl);
				productImageVO.setSmallImageUrl(smallImageUrl);
				productImageArrayList.add(productImageVO);
			}
		}
		return productImageArrayList;
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

	// /**
	// * 热销商品查询
	// *
	// * @param request
	// * @return
	// */
	// @RequestMapping("/getHotProduct")
	// @ResponseBody
	// public ResponseData<List<ProductData>> getHotProduct(HttpServletRequest
	// request) {
	// ISearchProductSV iPaymentQuerySV =
	// DubboConsumerFactory.getService("iSearchProductSV");
	// ResponseData<List<ProductData>> responseData = null;
	// try {
	// List<ProductData> resultInfo = iPaymentQuerySV.queryHotSellProduct();
	// for (ProductData data : resultInfo) {
	// data.setPictureUrl(ImageUtil.getHotImage());
	// }
	// resultInfo.get(0).setPictureUrl(ImageUtil.getHotImage());
	// responseData = new
	// ResponseData<List<ProductData>>(ResponseData.AJAX_STATUS_SUCCESS, "查询成功",
	// resultInfo);
	// } catch (Exception e) {
	// responseData = new
	// ResponseData<List<ProductData>>(ResponseData.AJAX_STATUS_FAILURE,
	// "查询失败");
	// LOG.error("获取信息出错：", e);
	// }
	// return responseData;
	// }

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
			// ProductSKURequest productSKURequest = new ProductSKURequest();
			// String skuId =
			// StringUtil.toString(request.getParameter("skuId"));
			// String skuAttrs =
			// StringUtil.toString(request.getParameter("skuAttrs"));
			// productSKURequest.setSkuId(skuId);
			// productSKURequest.setSkuAttrs(skuAttrs);

			// productSKURequest.setSkuId("0001");
			productSKURequest.setTenantId("SLP");
			ProductSKUConfigResponse productSKUConfig = iProductDetailSV.queryProductSKUConfig(productSKURequest);

			productSKUConfig = demoConfigResponse();

			ResponseHeader responseHeader = productSKUConfig.getResponseHeader();
			if (responseHeader.isSuccess()) {
				List<ProductSKUAttr> configParamterList = productSKUConfig.getProductAttrList();
				responseData = new ResponseData<List<ProductSKUAttr>>(ResponseData.AJAX_STATUS_SUCCESS, "查询成功", configParamterList);
			}
		} catch (Exception e) {
			responseData = new ResponseData<List<ProductSKUAttr>>(ResponseData.AJAX_STATUS_FAILURE, "查询失败", null);
			LOG.error("商品详情查询报错：", e);
			e.printStackTrace();
		}
		return responseData;
	}

	private ProductSKUConfigResponse demoConfigResponse() {
		ProductSKUConfigResponse ProductSKUConfigResponse = new ProductSKUConfigResponse();
		ResponseHeader responseHeader = new ResponseHeader(true, "000000", "查询成功");
		ProductSKUConfigResponse.setResponseHeader(responseHeader);
		List<ProductSKUAttr> configParamterList = new LinkedList<ProductSKUAttr>();
		String[] keyArray = new String[] { "品牌", "型号", "颜色", "上市年份", "输入方式", "智能机", "操作系统版本", "CPU品牌", "CPU型号", "CPU频率" };
		String[] valueArray = new String[] { "小米（MI）", "小米手机 5", "白色", "2016年", "触控", "是", "MIUI", "Qualcomm 骁龙", "骁龙820", "最高主频 1.8GHz" };
		for (int i = 0; i < keyArray.length; i++) {
			ProductSKUAttr configParamter = new ProductSKUAttr();
			configParamter.setAttrId(new Long(i));
			configParamter.setAttrName(keyArray[i]);
			configParamter.setAttrValueList(new ArrayList<ProductSKUAttrValue>());
			ProductSKUAttrValue attrValue = new ProductSKUAttrValue();
			attrValue.setAttrvalueDefId(Integer.toString(i + 5));
			attrValue.setAttrValueName(valueArray[i]);
			configParamter.getAttrValueList().add(attrValue);
			configParamterList.add(configParamter);
		}
		ProductSKUConfigResponse.setProductAttrList(configParamterList);
		return ProductSKUConfigResponse;
	}
}
