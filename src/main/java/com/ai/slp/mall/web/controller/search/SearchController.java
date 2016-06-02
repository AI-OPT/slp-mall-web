package com.ai.slp.mall.web.controller.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.ai.opt.base.vo.PageInfo;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.util.CollectionUtil;
import com.ai.opt.sdk.util.StringUtil;
import com.ai.opt.sdk.web.model.ResponseData;
import com.ai.slp.mall.web.model.product.ProductDataVO;
import com.ai.slp.mall.web.util.ImageUtil;
import com.ai.slp.product.api.webfront.interfaces.ISearchProductSV;
import com.ai.slp.product.api.webfront.param.ProductData;
import com.ai.slp.product.api.webfront.param.ProductImage;
import com.ai.slp.product.api.webfront.param.ProductQueryRequest;
import com.ai.slp.product.api.webfront.param.ProductQueryResponse;

import net.sf.json.JSONArray;

@RestController
@RequestMapping("/search")
public class SearchController {
    private static final Logger LOG = Logger.getLogger(SearchController.class);
	@RequestMapping("/list")
	public ModelAndView index(HttpServletRequest request,String priceId,String billType,String orgired,String skuName,String sourceFlag) {
	    try{
	        if(!StringUtil.isBlank(skuName)){
	            skuName= java.net.URLDecoder.decode(skuName, "UTF-8"); 
	        }
	        request.setAttribute("priceId", priceId);
	        request.setAttribute("billType", billType);
	        request.setAttribute("orgired", orgired);
	        request.setAttribute("skuName", skuName);
	        request.setAttribute("sourceFlag", sourceFlag);
	    }catch(Exception e){
	        LOG.error("中文解码错误", e); 
	    }
        ModelAndView view = new ModelAndView("jsp/search/search_list");
        return view;
    }
	
	/**
     * 商品查询
     * @param request
     * @return
     */
    @RequestMapping("/getProduct")
    @ResponseBody
    public ResponseData<PageInfo<ProductDataVO>> getList(HttpServletRequest request,ProductQueryRequest req){
        ISearchProductSV iPaymentQuerySV = DubboConsumerFactory.getService("iSearchProductSV");
        ResponseData<PageInfo<ProductDataVO>> responseData = null;
        PageInfo<ProductData> pageInfo = new PageInfo<ProductData> ();
        String strPageNo=(null==request.getParameter("pageNo"))?"1":request.getParameter("pageNo");
        String strPageSize=(null==request.getParameter("pageSize"))?"10":request.getParameter("pageSize");
        pageInfo.setPageNo(Integer.parseInt(strPageNo));
        pageInfo.setPageSize(Integer.parseInt(strPageSize));
        try {
            req.setPageInfo(pageInfo);
            ProductQueryResponse resultInfo = iPaymentQuerySV.queryProductPage(req);
            PageInfo<ProductData> result= resultInfo.getPageInfo();
            List<ProductData> proList = result.getResult();
            List<ProductDataVO> results = new ArrayList<ProductDataVO>();
            PageInfo<ProductDataVO> pageVo = new PageInfo<ProductDataVO>();
            pageVo.setPageCount(result.getPageCount());
            pageVo.setPageNo(result.getPageNo());
            pageVo.setPageSize(result.getPageSize());
            pageVo.setCount(result.getCount());
            for(ProductData data:proList){
                ProductDataVO vo = new ProductDataVO();
                vo.setProdId(data.getProdId());
                vo.setProdName(data.getProdName());
                vo.setSalePrice(data.getSalePrice());
                vo.setPicUrl(ImageUtil.getImage(data.getImageinfo().getVfsId(),data.getImageinfo().getPicType()));
                //获取缩略图id
               List<ProductImage> iamgeList = data.getThumbnail();
               Map<String,String> map = new HashMap<String,String>();
               
               //List<String> vsidList = new ArrayList<String>();
               if(!CollectionUtil.isEmpty(iamgeList)){
                   for(ProductImage img:iamgeList){
                      // map.set(img.getVfsId());
                       map.put(img.getVfsId(), img.getPicType());
                   }  
               }
                vo.setThumnailUrl(ImageUtil.getImages(map));
                results.add(vo);
            }
            pageVo.setResult(results);
            LOG.debug("商品查询出参:"+JSONArray.fromObject(resultInfo).toString());
            responseData = new ResponseData<PageInfo<ProductDataVO>>(ResponseData.AJAX_STATUS_SUCCESS, "查询成功", pageVo);
        } catch (Exception e) {
            responseData = new ResponseData<PageInfo<ProductDataVO>>(ResponseData.AJAX_STATUS_FAILURE, "查询失败");
            LOG.error("获取信息出错：", e);
        }
        return responseData;
    }
    
    
    /**
     * 热销商品查询
     * @param request
     * @return
     */
    @RequestMapping("/getHotProduct")
    @ResponseBody
    public ResponseData<List<ProductDataVO>> getHotProduct(HttpServletRequest request,ProductQueryRequest req){
        ISearchProductSV iPaymentQuerySV = DubboConsumerFactory.getService("iSearchProductSV");
        ResponseData<List<ProductDataVO>> responseData = null;
        try {
            List<ProductData> resultInfo = iPaymentQuerySV.queryHotSellProduct(req);
            List<ProductDataVO> voList = new ArrayList<ProductDataVO>();
            for(ProductData data:resultInfo){
                ProductDataVO vo  =new ProductDataVO();
                vo.setSalePrice(data.getSalePrice());
                vo.setProdName(data.getProdName());
                vo.setPicUrl(ImageUtil.getHotImage());
                voList.add(vo);
            }
            LOG.debug("商品查询出参:"+JSONArray.fromObject(resultInfo).toString());
            responseData = new ResponseData<List<ProductDataVO>>(ResponseData.AJAX_STATUS_SUCCESS, "查询成功", voList);
        } catch (Exception e) {
            responseData = new ResponseData<List<ProductDataVO>>(ResponseData.AJAX_STATUS_FAILURE, "查询失败");
            LOG.error("获取信息出错：", e);
        }
        return responseData;
    }
    /**
     * 搜索
     * @param request
     * @return
     */
    @RequestMapping("/commonSearch")
    @ResponseBody
    public ResponseData<PageInfo<ProductDataVO>> search(HttpServletRequest request,ProductQueryRequest req){
        ISearchProductSV iPaymentQuerySV = DubboConsumerFactory.getService("iSearchProductSV");
        ResponseData<PageInfo<ProductDataVO>> responseData = null;
        PageInfo<ProductData> pageInfo = new PageInfo<ProductData> ();
        String strPageNo=(null==request.getParameter("pageNo"))?"1":request.getParameter("pageNo");
        String strPageSize=(null==request.getParameter("pageSize"))?"10":request.getParameter("pageSize");
        pageInfo.setPageNo(Integer.parseInt(strPageNo));
        pageInfo.setPageSize(Integer.parseInt(strPageSize));
        try {
            req.setPageInfo(pageInfo);
            ProductQueryResponse resultInfo = iPaymentQuerySV.searchProduct(req);
            PageInfo<ProductData> result= resultInfo.getPageInfo();
            List<ProductData> proList = result.getResult();
            List<ProductDataVO> results = new ArrayList<ProductDataVO>();
            PageInfo<ProductDataVO> pageVo = new PageInfo<ProductDataVO>();
            pageVo.setPageCount(result.getPageCount());
            pageVo.setPageNo(result.getPageNo());
            pageVo.setPageSize(result.getPageSize());
            pageVo.setCount(result.getCount());
            for(ProductData data:proList){
                ProductDataVO vo = new ProductDataVO();
                vo.setProdId(data.getProdId());
                vo.setProdName(data.getProdName());
                vo.setSalePrice(data.getSalePrice());
                vo.setPicUrl(ImageUtil.getImage(data.getImageinfo().getVfsId(),data.getImageinfo().getPicType()));
                //获取缩略图id
               List<ProductImage> iamgeList = data.getThumbnail();
               Map<String,String> map = new HashMap<String,String>();
               if(!CollectionUtil.isEmpty(iamgeList)){
                   for(ProductImage img:iamgeList){
                       map.put(img.getVfsId(), img.getPicType());
                   }  
               }
                vo.setThumnailUrl(ImageUtil.getImages(map));
                results.add(vo);
            }
            pageVo.setResult(results);
            LOG.debug("商品查询出参:"+JSONArray.fromObject(resultInfo).toString());
            responseData = new ResponseData<PageInfo<ProductDataVO>>(ResponseData.AJAX_STATUS_SUCCESS, "查询成功", pageVo);
        } catch (Exception e) {
            responseData = new ResponseData<PageInfo<ProductDataVO>>(ResponseData.AJAX_STATUS_FAILURE, "查询失败");
            LOG.error("获取信息出错：", e);
        }
        return responseData;
    }
    
}
