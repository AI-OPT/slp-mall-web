package com.ai.slp.mall.web.controller.product;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.slp.product.api.webfront.interfaces.IProductDetailSV;
import com.ai.slp.product.api.webfront.param.ProductSKURequest;
import com.ai.slp.product.api.webfront.param.ProductSKUResponse;

@RestController
@RequestMapping("/product")
public class ProductController {

	@RequestMapping("/detail")
	public ModelAndView index(HttpServletRequest request) {
		IProductDetailSV iProductDetailSV = DubboConsumerFactory.getService("iProductDetailSV");
		ProductSKURequest productskurequest=new ProductSKURequest();
		productskurequest.setSkuId("0001");
		ProductSKUResponse producSKU= iProductDetailSV.queryProducSKUById(productskurequest);
        Map<String,ProductSKUResponse> model = new HashMap<String,ProductSKUResponse>();
        model.put("producSKU", producSKU);
		ModelAndView view = new ModelAndView("jsp/product/product_detail",model);
        return view;
    }
}
