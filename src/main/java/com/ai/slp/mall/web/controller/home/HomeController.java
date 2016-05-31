package com.ai.slp.mall.web.controller.home;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.web.model.ResponseData;
import com.ai.slp.mall.web.model.product.ProductHomeVO;
import com.ai.slp.mall.web.util.ImageUtil;
import com.ai.slp.product.api.webfront.interfaces.IProductHomeSV;
import com.ai.slp.product.api.webfront.param.ProductHomeRequest;
import com.ai.slp.product.api.webfront.param.ProductHomeResponse;

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
    public ResponseData<List<ProductHomeVO>> getFlow(HttpServletRequest request,ProductHomeRequest proRequest){
        IProductHomeSV iHomeProductSV = DubboConsumerFactory.getService("iProductHomeSV");
        ResponseData<List<ProductHomeVO>> responseData = null;
        List<ProductHomeVO> resultList = new ArrayList<ProductHomeVO>();
        try {
            List<ProductHomeResponse> list  = iHomeProductSV.queryHomeDataProduct(proRequest);
            for(ProductHomeResponse data:list){
                ProductHomeVO vo = new ProductHomeVO();
                vo.setPicUrl(ImageUtil.getHotImage());
                vo.setProdId(data.getProdId());
                vo.setProdName(data.getProdName());
                vo.setSalePrice(data.getSalePrice());
                resultList.add(vo);
            }
            responseData = new ResponseData<List<ProductHomeVO>>(ResponseData.AJAX_STATUS_SUCCESS, "查询成功", resultList);
        } catch (Exception e) {
            responseData = new ResponseData<List<ProductHomeVO>>(ResponseData.AJAX_STATUS_FAILURE, "查询失败");
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
    public ResponseData<List<ProductHomeVO>> getPhoneBill(HttpServletRequest request,ProductHomeRequest proRequest){
        IProductHomeSV iHomeProductSV = DubboConsumerFactory.getService("iProductHomeSV");
        ResponseData<List<ProductHomeVO>> responseData = null;
        List<ProductHomeVO> resultList = new ArrayList<ProductHomeVO>();
        try {
            List<ProductHomeResponse> list  = iHomeProductSV.queryHomeDataProduct(proRequest);
            for(ProductHomeResponse data:list){
                ProductHomeVO vo = new ProductHomeVO();
                vo.setPicUrl(ImageUtil.getHotImage());
                vo.setProdId(data.getProdId());
                vo.setProdName(data.getProdName());
                vo.setSalePrice(data.getSalePrice());
                resultList.add(vo);
            }
            responseData = new ResponseData<List<ProductHomeVO>>(ResponseData.AJAX_STATUS_SUCCESS, "查询成功", resultList);
        } catch (Exception e) {
            responseData = new ResponseData<List<ProductHomeVO>>(ResponseData.AJAX_STATUS_FAILURE, "查询失败");
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
    public ResponseData<List<ProductHomeVO>> getHotProduct(HttpServletRequest request,ProductHomeRequest proRequest){
        IProductHomeSV iHomeProductSV = DubboConsumerFactory.getService("iProductHomeSV");
        ResponseData<List<ProductHomeVO>> responseData = null;
        List<ProductHomeVO> resultList = new ArrayList<ProductHomeVO>();
        try {
            List<ProductHomeResponse> list  = iHomeProductSV.queryHomeDataProduct(proRequest);
            for(ProductHomeResponse data:list){
                ProductHomeVO vo = new ProductHomeVO();
                vo.setPicUrl(ImageUtil.getHotImage());
                vo.setProdId(data.getProdId());
                vo.setProdName(data.getProdName());
                vo.setSalePrice(data.getSalePrice());
                resultList.add(vo);
            }
        responseData = new ResponseData<List<ProductHomeVO>>(ResponseData.AJAX_STATUS_SUCCESS, "查询成功", resultList);
    } catch (Exception e) {
        responseData = new ResponseData<List<ProductHomeVO>>(ResponseData.AJAX_STATUS_FAILURE, "查询失败");
    }
    return responseData;
    }
}
