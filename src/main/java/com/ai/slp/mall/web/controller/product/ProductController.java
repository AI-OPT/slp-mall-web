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
import com.ai.slp.mall.web.model.product.ProductImagesVO;
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

@RestController
@RequestMapping("/product")
public class ProductController {

	private static final Logger LOG = Logger.getLogger(ProductController.class);

	@RequestMapping("/detail")
	public ModelAndView detail(HttpServletRequest request) {
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
				ProductImagesVO productImages = getProductImages(producSKU);
				String productImageJson = JSonUtil.toJSon(productImages);
				model.put("productImages", productImageJson);
				// 设置skuID
				model.put("skuId", producSKU.getSkuId());
				// 设置skuAttrs
				model.put("skuAttrs", skuAttrs);
				// 设置商品有效期
				String activeType = producSKU.getActiveType();
				String activeValue = getActiveDateValue(producSKU, activeType);
				model.put("activeDateValue", activeValue);
				// 设置商品详情展示信息
				model.put("productInfo", productInfoHtml);
				//设置商品类目
				model.put("productCatId", producSKU.getProductCatId());
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("商品详情查询报错：", e);
		}
		return new ModelAndView("jsp/product/product_detail", model);
	}
	
	/**
	 * 获得商品类目集
	 * @param request
	 * @return
	 */
	@RequestMapping("/getProductCatList")
	public  ResponseData<List<ProductCatInfo>> getProductCatList(HttpServletRequest request,ProductCatUniqueReq queryParams) {
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
	 * 示例返回值
	 *
	 * @return
     */
	private ProductSKUResponse demoResponse(){
		ProductSKUResponse productSKUResponse = new ProductSKUResponse();

		productSKUResponse.setCommentNum(1000L);
		productSKUResponse.setProdId("0001");
		productSKUResponse.setProdName("小米5 全网通 标准版 ");
		productSKUResponse.setProductSellPoint("套餐更实惠，千元畅机！高通晓龙650处理器，轻薄4000mAH电池，5.5英寸1080p高清大屏！5.5英寸1080p高清大屏！");
		productSKUResponse.setSaleNum(2000L);
		productSKUResponse.setSalePrice(1999l);
		productSKUResponse.setSkuId("0001");
		productSKUResponse.setSkuName("小米5 全网通 标准版");
		productSKUResponse.setUsableNum(5000L);
		productSKUResponse.setState("1");
		// 设置属性
		List<ProductSKUAttr> productAttrList = new LinkedList<ProductSKUAttr>();
		ProductSKUAttr skuAttr1 = new ProductSKUAttr();
		skuAttr1.setAttrId(1l);
		skuAttr1.setAttrName("选择颜色");
		List<ProductSKUAttrValue> attrValueList = new LinkedList<ProductSKUAttrValue>();
		ProductSKUAttrValue skuAttrValue1 = new ProductSKUAttrValue();
		skuAttrValue1.setAttrvalueDefId("1001");
		skuAttrValue1.setAttrValueName("白色");
		skuAttrValue1.setIsOwn(true);
		ProductImage image1 = new ProductImage();
		image1.setVfsId("57454f50d601800009c0b0cf");
		image1.setPicType(".jpg");
		skuAttrValue1.setImage(image1);
		attrValueList.add(skuAttrValue1);
		skuAttr1.setAttrValueList(attrValueList);
		ProductSKUAttrValue skuAttrValue2 = new ProductSKUAttrValue();
		skuAttrValue2.setAttrvalueDefId("1002");
		skuAttrValue2.setAttrValueName("黑色");
		skuAttrValue2.setIsOwn(false);
		ProductImage image2 = new ProductImage();
		image2.setVfsId("574551b4d601800009c0b0d9");
		image2.setPicType(".jpg");
		skuAttrValue2.setImage(image2);
		attrValueList.add(skuAttrValue2);
		skuAttr1.setAttrValueList(attrValueList);
		ProductSKUAttrValue skuAttrValue3 = new ProductSKUAttrValue();
		skuAttrValue3.setAttrvalueDefId("1002");
		skuAttrValue3.setAttrValueName("紫色");
		skuAttrValue3.setIsOwn(false);
		ProductImage image3 = new ProductImage();
		image3.setVfsId("57455205d601800009c0b0df");
		image3.setPicType(".jpg");
		skuAttrValue3.setImage(image3);
		attrValueList.add(skuAttrValue3);
		skuAttr1.setAttrValueList(attrValueList);
		productAttrList.add(skuAttr1);

		ProductSKUAttr skuAttr2 = new ProductSKUAttr();
		skuAttr2.setAttrId(2l);
		skuAttr2.setAttrName("选择版本");
		List<ProductSKUAttrValue> attrValueList2 = new LinkedList<ProductSKUAttrValue>();
		ProductSKUAttrValue skuAttrValue4 = new ProductSKUAttrValue();
		skuAttrValue4.setAttrvalueDefId("1004");
		skuAttrValue4.setAttrValueName("标准版");
		skuAttrValue4.setIsOwn(false);
		attrValueList2.add(skuAttrValue4);
		skuAttr2.setAttrValueList(attrValueList);
		ProductSKUAttrValue skuAttrValue5 = new ProductSKUAttrValue();
		skuAttrValue5.setAttrvalueDefId("1005");
		skuAttrValue5.setAttrValueName("高配版");
		skuAttrValue5.setIsOwn(true);
		attrValueList2.add(skuAttrValue5);
		skuAttr2.setAttrValueList(attrValueList);
		ProductSKUAttrValue skuAttrValue6 = new ProductSKUAttrValue();
		skuAttrValue6.setAttrvalueDefId("1006");
		skuAttrValue6.setAttrValueName("尊享版");
		skuAttrValue6.setIsOwn(false);
		attrValueList2.add(skuAttrValue6);
		skuAttr2.setAttrValueList(attrValueList2);
		productAttrList.add(skuAttr2);
		productSKUResponse.setProductAttrList(productAttrList);

		// 设置图片
		List<ProductImage> productImageList = new LinkedList<ProductImage>();
		ProductImage productImage1 = new ProductImage();
		productImage1.setPicType(".jpg");
		productImage1.setVfsId("57454f50d601800009c0b0cf");
		productImageList.add(productImage1);
		ProductImage productImage2 = new ProductImage();
		productImage2.setPicType(".jpg");
		productImage2.setVfsId("5745516fd601800009c0b0d5");
		productImageList.add(productImage2);
		ProductImage productImage3 = new ProductImage();
		productImage3.setPicType(".jpg");
		productImage3.setVfsId("57455191d601800009c0b0d7");
		productImageList.add(productImage3);
		productSKUResponse.setProductImageList(productImageList);
		String productInfoHtml = "<p><A><img src=\"/slp-mall/resources/slpmall/images/parameter-a.png\"></A></p>" + "<p><img src=\"/slp-mall/resources/slpmall/images/parameter-b.png\"></p>"
				+ "<p><img src=\"/slp-mall/resources/slpmall/images/parameter-c.png\"></p>" + "<p><img src=\"${_slpbase }/images/parameter-d.jpg\"></p>"
				+ "<p><img src=\"/slp-mall/resources/slpmall/images/parameter-e.png\"></p>";
		productSKUResponse.setProDetailContent(productInfoHtml);
		ResponseHeader responseHeader = new ResponseHeader(true, "000000", "查询成功");
		productSKUResponse.setResponseHeader(responseHeader);
		return productSKUResponse;
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
	private ProductImagesVO getProductImages(ProductSKUResponse productSKUVO) {
		String productImageBigSize = "360x457";
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

			//TODO 测试数据 
			//productSKUConfig = demoConfigResponse();

			if (productSKUConfig != null && productSKUConfig.getResponseHeader().isSuccess()) {
				List<ProductSKUAttr> configParamterList = productSKUConfig.getProductAttrList();
				responseData = new ResponseData<List<ProductSKUAttr>>(ResponseData.AJAX_STATUS_SUCCESS, "查询成功", configParamterList);
			}else{
				responseData = new ResponseData<List<ProductSKUAttr>>(ResponseData.AJAX_STATUS_SUCCESS, "无数据", null);
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
