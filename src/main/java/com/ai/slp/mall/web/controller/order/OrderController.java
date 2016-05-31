package com.ai.slp.mall.web.controller.order;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.ai.opt.base.vo.PageInfo;
import com.ai.opt.base.vo.ResponseHeader;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.web.model.ResponseData;
import com.ai.slp.mall.web.constants.SLPMallConstants.ExceptionCode;
import com.ai.slp.order.api.orderlist.interfaces.IOrderListSV;
import com.ai.slp.order.api.orderlist.param.OrdOrderParams;
import com.ai.slp.order.api.orderlist.param.QueryOrderListRequest;
import com.ai.slp.order.api.orderlist.param.QueryOrderListResponse;

@RestController
@RequestMapping("/order")
public class OrderController {

	private static final Logger LOG = Logger.getLogger(OrderController.class);

	@RequestMapping("/list")
	public ModelAndView orderList(HttpServletRequest request) {
		return new ModelAndView("jsp/order/order_list");
	}
	
	@RequestMapping("/getOrderListData")
	@ResponseBody
	public ResponseData<PageInfo<OrdOrderParams>> getOrderListData(HttpServletRequest request,QueryOrderListRequest queryRequest ){
		ResponseData<PageInfo<OrdOrderParams>> responseData = null;
		try {
			IOrderListSV iOrderListSV = DubboConsumerFactory.getService("iOrderListSV");
			QueryOrderListResponse orderListResponse = iOrderListSV.queryOrderList(queryRequest);
			ResponseHeader responseHeader = orderListResponse.getResponseHeader();
			if(responseHeader.isSuccess()){
				PageInfo<OrdOrderParams> pageInfo=orderListResponse.getPageInfo() ;
				responseData = new ResponseData<PageInfo<OrdOrderParams>>(ExceptionCode.SUCCESS, "查询成功", pageInfo);
			}else{
				responseData = new ResponseData<PageInfo<OrdOrderParams>>(ExceptionCode.SYSTEM_ERROR, "查询失败", null);
			}
		} catch (Exception e) {
			LOG.error("查询订单列表失败：", e);
			e.printStackTrace();
			responseData = new ResponseData<PageInfo<OrdOrderParams>>(ExceptionCode.SYSTEM_ERROR, "查询失败", null);
		}
		return responseData;
	}
}
