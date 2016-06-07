package com.ai.slp.mall.web.controller.shopcart;

import com.ai.opt.base.exception.BusinessException;
import com.ai.opt.base.exception.SystemException;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.web.model.ResponseData;
import com.ai.paas.ipaas.util.JSonUtil;
import com.ai.slp.mall.web.constants.ComParamsConstants;
import com.ai.slp.order.api.shopcart.interfaces.IShopCartSV;
import com.ai.slp.order.api.shopcart.param.*;
import com.alibaba.fastjson.JSON;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liutong5 on 16/5/30.
 */
@Controller
@RequestMapping("/shopcart")
public class ShopCartController {
    private static final Logger LOG = Logger.getLogger(ShopCartController.class);
    /**
     * 购物车中添加商品
     */
    @RequestMapping("/addProd")
    @ResponseBody
    public ResponseData<CartProdOptRes> addProd(HttpSession session, @RequestParam Long buyNum, @RequestParam String skuId) {
        //获取service
        IShopCartSV iShopCartSV = DubboConsumerFactory.getService("IShopCartSV");
        ResponseData<CartProdOptRes> responseData = null;
        try{
            //设置参数
            CartProd cartProd = new CartProd();
            cartProd.setBuyNum(buyNum);
            cartProd.setSkuId(skuId);
            cartProd.setTenantId(ComParamsConstants.COM_TENANT_ID);
            cartProd.setUserId(getUserId(session));
            CartProdOptRes cartProdOptRes = iShopCartSV.addProd(cartProd);
            LOG.debug("添加购物车商品出参:"+ JSonUtil.toJSon(cartProdOptRes));
            responseData = new ResponseData<CartProdOptRes>(ResponseData.AJAX_STATUS_SUCCESS, "添加成功", cartProdOptRes);
        }catch(BusinessException|SystemException e){
            responseData = new ResponseData<CartProdOptRes>(ResponseData.AJAX_STATUS_FAILURE, "添加失败:"+e.getMessage());
            LOG.error("添加购物车商品出错",e);
        }catch (Exception e){
            responseData = new ResponseData<CartProdOptRes>(ResponseData.AJAX_STATUS_FAILURE, "添加失败,出现未知异常");
            LOG.error("添加购物车商品出错",e);
        }
        return responseData;
    }
    /**
     * 查询用户的购物车详细信息
     */
    @RequestMapping("/cartDetails")
    @ResponseBody
    public ModelAndView queryCartDetails(HttpServletRequest request){
    	Map<String, Object> model = new HashMap<String, Object>();
    	try{
//    		String tenantId = request.getParameter("tenantId");
//    		String userId = request.getParameter("userId");
//    		IShopCartSV iShopCartSV = DubboConsumerFactory.getService("IShopCartSV");
    		UserInfo userInfo = new UserInfo();
    		userInfo.setTenantId("SLP");
//            userInfo.setUserId(userId);
//            List<CartProdInfo> cartProdInfoList = iShopCartSV.queryCartOfUser(userInfo);
    		//统计商品数量
    		int prodTotal = 0;
//    		for(CartProdInfo cartProdInfo : cartProdInfoList){
//    			prodTotal+=cartProdInfo.getBuyNum();
//    		}
    		
    		List<CartProdInfo> cartProdInfoList = new ArrayList<>();
            CartProdInfo cartProdInfo = new CartProdInfo();
            cartProdInfo.setSkuId("111");
            cartProdInfo.setState("5");
            cartProdInfo.setBuyNum(3);
            cartProdInfo.setProductName("小黄鸭");
            cartProdInfo.setSalePrice(300l);
            cartProdInfoList.add(cartProdInfo);
            
            CartProdInfo cartProdInfo2 = new CartProdInfo();
            cartProdInfo2.setSkuId("222");
            
            cartProdInfo2.setBuyNum(2);
            cartProdInfo2.setProductName("小黄鸭2");
            cartProdInfo2.setSalePrice(3002l);
            cartProdInfoList.add(cartProdInfo2);
            
            CartProdInfo cartProdInfo3 = new CartProdInfo();
            cartProdInfo3.setSkuId("333");
            cartProdInfo3.setState("5");
            cartProdInfo3.setBuyNum(6);
            cartProdInfo3.setProductName("小黄鸭3");
            cartProdInfo3.setSalePrice(30020l);
            cartProdInfoList.add(cartProdInfo3);
            String cartProdInfoJSON = JSonUtil.toJSon(cartProdInfoList);
            model.put("cartProdList", cartProdInfoJSON);
            model.put("prodTotal", prodTotal);
    	}catch(Exception e){
    		e.printStackTrace();
    		LOG.error("查询购物车商品详情出错",e);
    	}
        return new ModelAndView("jsp/shopcart/shopping_cart",model);
    }
    /**
     * 修改购物车数量
     */
    @RequestMapping("/updateProdNum")
    @ResponseBody
    public ResponseData<CartProdOptRes> updateProdNum(HttpSession session, @RequestParam Long buyNum, @RequestParam String skuId) {
        IShopCartSV iShopCartSV = DubboConsumerFactory.getService("IShopCartSV");
        ResponseData<CartProdOptRes> responseData = null;
        try {
            //设置参数
            CartProd cartProd = new CartProd();
            cartProd.setBuyNum(buyNum);
            cartProd.setSkuId(skuId);
            cartProd.setTenantId(ComParamsConstants.COM_TENANT_ID);
            cartProd.setUserId(getUserId(session));
            CartProdOptRes cartProdOptRes = iShopCartSV.updateProdNum(cartProd);
            LOG.debug("修改购物车数量出参:" + JSonUtil.toJSon(cartProdOptRes));
            responseData = new ResponseData<CartProdOptRes>(ResponseData.AJAX_STATUS_SUCCESS, "修改成功", cartProdOptRes);
        } catch (BusinessException | SystemException e) {
            responseData = new ResponseData<CartProdOptRes>(ResponseData.AJAX_STATUS_FAILURE, "修改失败:" + e.getMessage());
            LOG.error("修改购物车数量出错", e);
        } catch (Exception e) {
            responseData = new ResponseData<CartProdOptRes>(ResponseData.AJAX_STATUS_FAILURE, "修改失败,出现未知异常");
            LOG.error("修改购物车数量出错", e);
        }
        return responseData;
    }
    
    /**
     * 删除购物车商品
     */
    @RequestMapping("/deleteProd")
    @ResponseBody
    public ResponseData<CartProdOptRes> deleteMultiProd(HttpSession session, String skuList) {
        IShopCartSV iShopCartSV = DubboConsumerFactory.getService("IShopCartSV");
        ResponseData<CartProdOptRes> responseData = null;
        try {
            List<String> skuIds = JSON.parseArray(skuList, String.class);
            //设置参数
            MultiCartProd multiCartProd = new MultiCartProd();
            multiCartProd.setTenantId(ComParamsConstants.COM_TENANT_ID);
            multiCartProd.setUserId(getUserId(session));
            multiCartProd.setSkuIdList(skuIds);
            CartProdOptRes cartProdOptRes = iShopCartSV.deleteMultiProd(multiCartProd);
            LOG.debug("删除购物车商品出参:" + JSonUtil.toJSon(cartProdOptRes));
            responseData = new ResponseData<CartProdOptRes>(ResponseData.AJAX_STATUS_SUCCESS, "删除成功", cartProdOptRes);
        } catch (BusinessException | SystemException e) {
            responseData = new ResponseData<CartProdOptRes>(ResponseData.AJAX_STATUS_FAILURE, "删除失败:" + e.getMessage());
            LOG.error("删除购物车商品出错", e);
        } catch (Exception e) {
            responseData = new ResponseData<CartProdOptRes>(ResponseData.AJAX_STATUS_FAILURE, "删除失败,出现未知异常");
            LOG.error("删除购物车商品出错", e);
        }
        return responseData;
    }

    private String getUserId(HttpSession session){
//        SLPClientUser user = (SLPClientUser) session.getAttribute(SSOClientConstants.USER_SESSION_KEY);
        return "234";
    }
    
}
