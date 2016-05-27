package com.ai.slp.mall.web.controller.home;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.web.model.ResponseData;
import com.ai.slp.mall.web.util.ImageUtil;
import com.ai.slp.product.api.webfront.interfaces.IProductHomeSV;
import com.ai.slp.product.api.webfront.param.ProductHome;

@RestController
public class HomeController {

	@RequestMapping("/home")
	public ModelAndView index(HttpServletRequest request) {
        ModelAndView view = new ModelAndView("jsp/home/index");
        return view;
    }
	
	/**
     * 流量查询
     * @param request
     * @return
     */
    @RequestMapping("/getFlow")
    @ResponseBody
    public ResponseData<List<ProductHome>> getFlow(HttpServletRequest request){
        IProductHomeSV iHomeProductSV = DubboConsumerFactory.getService("iProductHomeSV");
        ResponseData<List<ProductHome>> responseData = null;
        try {
            List<ProductHome> list  = iHomeProductSV.queryFlowDataProduct();
            for(ProductHome data:list){
                data.setPicUrl(ImageUtil.getImage());
            }
            responseData = new ResponseData<List<ProductHome>>(ResponseData.AJAX_STATUS_SUCCESS, "查询成功", list);
        } catch (Exception e) {
            responseData = new ResponseData<List<ProductHome>>(ResponseData.AJAX_STATUS_FAILURE, "查询失败");
        }
        return responseData;
    }
    /**
     * 话费查询
     * @param request
     * @return
     */
    @RequestMapping("/getPhoneBill")
    @ResponseBody
    public ResponseData<List<ProductHome>> getPhoneBill(HttpServletRequest request){
        IProductHomeSV iHomeProductSV = DubboConsumerFactory.getService("iProductHomeSV");
        ResponseData<List<ProductHome>> responseData = null;
        try {
            List<ProductHome> list  = iHomeProductSV.queryPhoneBillProduct();
            for(ProductHome data:list){
                data.setPicUrl(ImageUtil.getImage());
            }
            responseData = new ResponseData<List<ProductHome>>(ResponseData.AJAX_STATUS_SUCCESS, "查询成功", list);
        } catch (Exception e) {
            responseData = new ResponseData<List<ProductHome>>(ResponseData.AJAX_STATUS_FAILURE, "查询失败");
        }
        return responseData;
    }
    /**
     * 热品查询
     * @param request
     * @return
     */
    @RequestMapping("/getHotProduct")
    @ResponseBody
    public ResponseData<List<ProductHome>> getHotProduct(HttpServletRequest request){
        IProductHomeSV iHomeProductSV = DubboConsumerFactory.getService("iProductHomeSV");
        ResponseData<List<ProductHome>> responseData = null;
        try {
            List<ProductHome> list  = iHomeProductSV.queryHotProduct();
            for(ProductHome data:list){
                data.setPicUrl(ImageUtil.getHotImage());
            }
            responseData = new ResponseData<List<ProductHome>>(ResponseData.AJAX_STATUS_SUCCESS, "查询成功", list);
        } catch (Exception e) {
            responseData = new ResponseData<List<ProductHome>>(ResponseData.AJAX_STATUS_FAILURE, "查询失败");
        }
        return responseData;
    }
}
