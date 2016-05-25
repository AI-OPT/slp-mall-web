package com.ai.slp.mall.web.controller.search;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.ai.opt.base.vo.PageInfo;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.web.model.ResponseData;
import com.ai.slp.mall.web.util.ImageUtil;
import com.ai.slp.product.api.webfront.interfaces.ISearchProductSV;
import com.ai.slp.product.api.webfront.param.ProductData;
import com.ai.slp.product.api.webfront.param.ProductQueryRequest;
import com.ai.slp.product.api.webfront.param.ProductQueryResponse;

import net.sf.json.JSONArray;

@RestController
@RequestMapping("/search")
public class SearchController {
    private static final Logger LOG = Logger.getLogger(SearchController.class);
	@RequestMapping("/list")
	public ModelAndView index(HttpServletRequest request) {

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
    public ResponseData<PageInfo<ProductData>> getList(HttpServletRequest request){
        ISearchProductSV iPaymentQuerySV = DubboConsumerFactory.getService("iSearchProductSV");
        ProductQueryRequest req = new ProductQueryRequest();
        ResponseData<PageInfo<ProductData>> responseData = null;
        PageInfo<ProductData> pageInfo = new PageInfo<ProductData> ();
        String strPageNo=(null==request.getParameter("pageNo"))?"1":request.getParameter("pageNo");
        String strPageSize=(null==request.getParameter("pageSize"))?"10":request.getParameter("pageSize");
        pageInfo.setPageNo(Integer.parseInt(strPageNo));
        pageInfo.setPageSize(Integer.parseInt(strPageSize));
        try {
            req.setPageInfo(pageInfo);
            ProductQueryResponse resultInfo = iPaymentQuerySV.queryProductPage(req);
            PageInfo<ProductData> result= resultInfo.getPageInfo();
            List<ProductData> list = result.getResult();
            result.setCount(10);
            for(ProductData data:list){
                data.setPictureUrl(ImageUtil.getImage());
                data.setPictureUrlList(ImageUtil.getImages());
            }
            LOG.debug("商品查询出参:"+JSONArray.fromObject(resultInfo).toString());
            responseData = new ResponseData<PageInfo<ProductData>>(ResponseData.AJAX_STATUS_SUCCESS, "查询成功", result);
        } catch (Exception e) {
            responseData = new ResponseData<PageInfo<ProductData>>(ResponseData.AJAX_STATUS_FAILURE, "查询失败");
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
