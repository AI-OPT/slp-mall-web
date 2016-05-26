package com.ai.slp.mall.web.controller.shopcart;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.web.model.ResponseData;
import com.ai.paas.ipaas.util.JSonUtil;
import com.ai.slp.mall.web.model.product.ProductImageVO;
import com.ai.slp.order.api.shopcart.interfaces.IShopCartSV;
import com.ai.slp.order.api.shopcart.param.CartProd;
import com.ai.slp.product.api.webfront.param.ProductData;
import com.ai.slp.product.api.webfront.param.ProductSKURequest;
import com.ai.slp.product.api.webfront.param.ProductSKUResponse;

/**
 * 购物车操作
 * Created by jackieliu on 16/5/20.
 */
@Controller
@RequestMapping("/shopcart")
public class ShopCartController {

    //添加商品
	@RequestMapping("/addProd")
	public ResponseData<ProductData> index(@RequestParam Integer buyNum, @RequestParam String skuId) {
		System.out.println("buyNum:"+buyNum+",skuId:"+skuId);
//		IShopCartSV iShopCartSV = DubboConsumerFactory.getService("IShopCartSV");
//		CartProd cartProd = new CartProd();
//		cartProd.setBuyNum(request.getParameter("buyNum"));
		
		
//		
//		ProductSKURequest productskurequest = new ProductSKURequest();
//		productskurequest.setSkuId("0001");
//		ProductSKUResponse producSKU = iProductDetailSV.queryProducSKUById(productskurequest);
//
//		// 设置商品属性中的图片
//		changeAttrIamge(attrImageSize, producSKU);
//		// 获得商品图片
//		List<ProductImageVO> productImageVOList = getProductImages(producSKU);
//		Map<String, String> model = new HashMap<String, String>();
//		String producSKUJson = JSonUtil.toJSon(producSKU);
//		model.put("productSKU", producSKUJson);
//		String productImageJson = JSonUtil.toJSon(productImageVOList);
//		model.put("imageArrayList", productImageJson);
//		ModelAndView view = new ModelAndView("jsp/product/product_detail");
		
		return null;
	}
}
