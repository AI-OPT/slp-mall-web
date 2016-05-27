package com.ai.slp.mall.web.controller.shopcart;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.opt.base.vo.PageInfo;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.web.model.ResponseData;
import com.ai.slp.mall.web.controller.search.SearchController;
import com.ai.slp.order.api.shopcart.interfaces.IShopCartSV;
import com.ai.slp.order.api.shopcart.param.CartProd;
import com.ai.slp.order.api.shopcart.param.CartProdOptRes;
import com.ai.slp.product.api.webfront.param.ProductData;

import net.sf.json.JSONArray;

/**
 * 购物车操作
 * Created by jackieliu on 16/5/20.
 */
@Controller
@RequestMapping("/shopcart")
public class ShopCartController {
	private static final Logger LOG = Logger.getLogger(SearchController.class);
    //添加商品
	@RequestMapping("/addProd")
	public ResponseData<CartProdOptRes> addProd(HttpSession session, @RequestParam Long buyNum, @RequestParam String skuId) {
		//获取service
		IShopCartSV iShopCartSV = DubboConsumerFactory.getService("IShopCartSV");
		//设置参数
		CartProd cartProd = new CartProd();
		cartProd.setBuyNum(buyNum);
		cartProd.setSkuId(skuId);
//		cartProd.setTenantId((String)session.getAttribute("tenantId"));
//		cartProd.setUserId((String)session.getAttribute("userId"));
		ResponseData<CartProdOptRes> responseData = null;
		CartProdOptRes cartProdOptRes = null;
		try{
			cartProdOptRes = iShopCartSV.addProd(cartProd);
			LOG.debug("添加购物车商品出参:"+JSONArray.fromObject(cartProdOptRes).toString());
            responseData = new ResponseData<CartProdOptRes>(ResponseData.AJAX_STATUS_SUCCESS, "添加成功", cartProdOptRes);
		}catch(Exception e){
			responseData = new ResponseData<CartProdOptRes>(ResponseData.AJAX_STATUS_SUCCESS, "添加失败");
			LOG.error("添加购物车商品出错",e);
        }
		return responseData;
	}
}
