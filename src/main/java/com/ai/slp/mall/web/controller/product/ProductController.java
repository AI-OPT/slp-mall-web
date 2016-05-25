package com.ai.slp.mall.web.controller.product;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.ai.opt.sdk.components.idps.IDPSClientFactory;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.paas.ipaas.image.IImageClient;
import com.ai.paas.ipaas.util.JSonUtil;
import com.ai.slp.mall.web.constants.SLPMallConstants.ProductImageConstant;
import com.ai.slp.product.api.webfront.interfaces.IProductDetailSV;
import com.ai.slp.product.api.webfront.param.ProductImage;
import com.ai.slp.product.api.webfront.param.ProductSKUAttr;
import com.ai.slp.product.api.webfront.param.ProductSKUAttrValue;
import com.ai.slp.product.api.webfront.param.ProductSKURequest;
import com.ai.slp.product.api.webfront.param.ProductSKUResponse;

@RestController
@RequestMapping("/product")
public class ProductController {

	@RequestMapping("/detail")
	public ModelAndView index(HttpServletRequest request) {
		// 商品属性图片大小
		String attrImageSize = "30x30";

		IProductDetailSV iProductDetailSV = DubboConsumerFactory.getService("iProductDetailSV");
		ProductSKURequest productskurequest = new ProductSKURequest();
		productskurequest.setSkuId("0001");
		ProductSKUResponse producSKU = iProductDetailSV.queryProducSKUById(productskurequest);

		// 设置商品属性中的图片
		changeAttrIamge(attrImageSize, producSKU);
		// 获得商品图片
		List<String[]> productImageVOList = getProductImages(producSKU);
		Map<String, String> model = new HashMap<String, String>();
		String producSKUJson = JSonUtil.toJSon(producSKU);
		model.put("productSKU", producSKUJson);
		String productImageJson = JSonUtil.toJSon(productImageVOList);
		model.put("imageArrayList", productImageJson);
		ModelAndView view = new ModelAndView("jsp/product/product_detail", model);
		return view;
	}

	/**
	 * 获得商品图片
	 * 
	 * @param productSKUVO
	 * @return
	 */
	private List<String[]> getProductImages(ProductSKUResponse productSKUVO) {
		String productImageBigSize = "360x457";
		String productImageSmailSize = "60x60";
		List<ProductImage> productImageList = productSKUVO.getProductImageList();
		List<String[]> productImageArrayList = new LinkedList<String[]>();
		if (productImageList != null && productImageList.size() > 0) {
			IImageClient imageClient = IDPSClientFactory.getImageClient(ProductImageConstant.IDPSNS);
			for (ProductImage productImage : productImageList) {
				String idpsId = productImage.getIdpsId();
				String extension = productImage.getExtension();
				String bigImageUrl = imageClient.getImageUrl(idpsId, extension, productImageBigSize);
				String smailImageUrl = imageClient.getImageUrl(idpsId, extension, productImageSmailSize);
				String[] imageUrlArray = new String[] { bigImageUrl, smailImageUrl };
				productImageArrayList.add(imageUrlArray);
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
	private Long changeAttrIamge(String attrImageSize, ProductSKUResponse productSKUVO) {
		List<ProductSKUAttr> productAttrList = productSKUVO.getProductAttrList();
		// 获取imageClient
		IImageClient imageClient = IDPSClientFactory.getImageClient(ProductImageConstant.IDPSNS);
		Long defAttrValueId = null;
		for (ProductSKUAttr productSKUAttr : productAttrList) {
			List<ProductSKUAttrValue> attrValueList = productSKUAttr.getAttrValueList();
			for (ProductSKUAttrValue attrValue : attrValueList) {
				if (defAttrValueId == null) {
					defAttrValueId = attrValue.getAttrvalueDefId();
				}
				ProductImage image = attrValue.getImage();
				if (image != null) {
					String idpsId = image.getIdpsId();
					String extension = image.getExtension();
					String imageUrl = imageClient.getImageUrl(idpsId, extension, attrImageSize);
					attrValue.setImageUrl(imageUrl);
				}
			}
		}
		return defAttrValueId;
	}
}
