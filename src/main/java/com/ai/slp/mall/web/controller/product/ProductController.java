package com.ai.slp.mall.web.controller.product;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.apache.commons.httpclient.util.DateUtil;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.ai.opt.sdk.components.idps.IDPSClientFactory;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.web.model.ResponseData;
import com.ai.paas.ipaas.image.IImageClient;
import com.ai.paas.ipaas.util.JSonUtil;
import com.ai.slp.mall.web.constants.SLPMallConstants.ProductImageConstant;
import com.ai.slp.mall.web.model.product.ProductImageVO;
import com.ai.slp.mall.web.util.ImageUtil;
import com.ai.slp.product.api.webfront.interfaces.IProductDetailSV;
import com.ai.slp.product.api.webfront.interfaces.ISearchProductSV;
import com.ai.slp.product.api.webfront.param.ProductData;
import com.ai.slp.product.api.webfront.param.ProductImage;
import com.ai.slp.product.api.webfront.param.ProductSKUAttr;
import com.ai.slp.product.api.webfront.param.ProductSKUAttrValue;
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

			IProductDetailSV iProductDetailSV = DubboConsumerFactory.getService("iProductDetailSV");
			ProductSKURequest productskurequest = new ProductSKURequest();
			productskurequest.setSkuId("0001");
			ProductSKUResponse producSKU = iProductDetailSV.queryProducSKUById(productskurequest);

			// producSKU.setActiveType("2");
			// producSKU.setActiveTime(DateUtils.parseDate("2016-01-01",
			// "yyyy-MM-dd"));
			// producSKU.setInactiveTime(DateUtils.parseDate("2018-01-01",
			// "yyyy-MM-dd"));
			// producSKU.setActiveCycle(15);
			// producSKU.setUnit("天");

			// 设置商品属性中的图片
			changeAttrIamge(attrImageSize, producSKU);
			String producSKUJson = JSonUtil.toJSon(producSKU);
			model.put("productSKU", producSKUJson);
			// 获得商品图片
			List<ProductImageVO> productImageVOList = getProductImages(producSKU);
			String productImageJson = JSonUtil.toJSon(productImageVOList);
			model.put("imageArrayList", productImageJson);
			// 设置skuID
			String skuId = producSKU.getSkuId();
			model.put("skuId", skuId);
			// 设置商品有效期
			String activeType = producSKU.getActiveType();
			String activeValue = getActiveDateValue(producSKU, activeType);
			model.put("activeDateValue", activeValue);
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
				String idpsId = productImage.getIdpsId();
				String extension = productImage.getExtension();
				String bigImageUrl = imageClient.getImageUrl(idpsId, extension, productImageBigSize);
				String smallImageUrl = imageClient.getImageUrl(idpsId, extension, productImageSmailSize);
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
	
	/**
     * 热销商品查询
     * @param request
     * @return
     */
    @RequestMapping("/getHotProduct")
    @ResponseBody
    public ResponseData<List<ProductData>> getHotProduct(HttpServletRequest request){
        ISearchProductSV iPaymentQuerySV = DubboConsumerFactory.getService("iSearchProductSV");
        ResponseData<List<ProductData>> responseData = null;
        try {
            List<ProductData> resultInfo = iPaymentQuerySV.queryHotSellProduct();
            for(ProductData data:resultInfo){
                data.setPictureUrl(ImageUtil.getHotImage());
            }
            resultInfo.get(0).setPictureUrl(ImageUtil.getImage());
            LOG.debug("商品查询出参:"+JSONArray.fromObject(resultInfo).toString());
            responseData = new ResponseData<List<ProductData>>(ResponseData.AJAX_STATUS_SUCCESS, "查询成功", resultInfo);
        } catch (Exception e) {
            responseData = new ResponseData<List<ProductData>>(ResponseData.AJAX_STATUS_FAILURE, "查询失败");
            LOG.error("获取信息出错：", e);
        }
        return responseData;
    }
}
