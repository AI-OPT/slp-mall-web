package com.ai.slp.mall.web.controller.order;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.ai.net.xss.util.StringUtil;
import com.ai.opt.base.vo.PageInfo;
import com.ai.opt.base.vo.ResponseHeader;
import com.ai.opt.sdk.dubbo.util.DubboConsumerFactory;
import com.ai.opt.sdk.util.BeanUtils;
import com.ai.opt.sdk.util.DateUtil;
import com.ai.opt.sdk.web.model.ResponseData;
import com.ai.slp.mall.web.constants.SLPMallConstants.ExceptionCode;
import com.ai.slp.mall.web.model.order.OrderListQueryParams;
import com.ai.slp.order.api.orderlist.interfaces.IOrderListSV;
import com.ai.slp.order.api.orderlist.param.OrdOrderVo;
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
	public ResponseData<PageInfo<OrdOrderVo>> getOrderListData(HttpServletRequest request,OrderListQueryParams queryParams ){
		ResponseData<PageInfo<OrdOrderVo>> responseData = null;
		try {
			String searchType = queryParams.getSearchType();
			QueryOrderListRequest queryRequest = new QueryOrderListRequest();
			BeanUtils.copyProperties(queryRequest, queryParams);
			if("1".equals(searchType)){
				String selectTime = queryParams.getSelectTime();
				queryRequest.setOrderTimeBegin(null);
				queryRequest.setOrderTimeEnd(null);
				if("1".equals(selectTime)){//3月内
					String startDateStr = getBeforeMonthDate(3);
					queryRequest.setOrderTimeBegin(startDateStr);
					String endDateStr = DateUtil.getDateString("yyyy-MM-dd HH:mm:ss");
					queryRequest.setOrderTimeEnd(endDateStr);
				}else if("2".equals(selectTime)){//当年
					String startDateStr = getYearBeginDate();
					queryRequest.setOrderTimeBegin(startDateStr);
					String endDateStr = DateUtil.getDateString("yyyy-MM-dd HH:mm:ss");
					queryRequest.setOrderTimeEnd(endDateStr);
				}
			}else{
				String orderTimeBegin = queryRequest.getOrderTimeBegin();
				if(!StringUtil.isBlank(orderTimeBegin)){
					queryRequest.setOrderTimeBegin(orderTimeBegin+" 00:00:00");
				}
				String orderTimeEnd = queryRequest.getOrderTimeEnd();
				if(!StringUtil.isBlank(orderTimeEnd)){
					queryRequest.setOrderTimeEnd(orderTimeEnd+" 23:59:59");
				}
			}
			IOrderListSV iOrderListSV = DubboConsumerFactory.getService("iOrderListSV");
			QueryOrderListResponse orderListResponse = iOrderListSV.queryOrderList(queryRequest);
			ResponseHeader responseHeader = orderListResponse.getResponseHeader();
			if(responseHeader.isSuccess()){
				PageInfo<OrdOrderVo> pageInfo=orderListResponse.getPageInfo() ;
				responseData = new ResponseData<PageInfo<OrdOrderVo>>(ExceptionCode.SUCCESS, "查询成功", pageInfo);
			}else{
				responseData = new ResponseData<PageInfo<OrdOrderVo>>(ExceptionCode.SYSTEM_ERROR, "查询失败", null);
			}
		} catch (Exception e) {
			LOG.error("查询订单列表失败：", e);
			e.printStackTrace();
			responseData = new ResponseData<PageInfo<OrdOrderVo>>(ExceptionCode.SYSTEM_ERROR, "查询失败", null);
		}
		return responseData;
	}
	
	/**
	 * 获得本年开始日期
	 * @param beforeMonth
	 * @return
	 */
	private String getYearBeginDate() {
		String dateString = DateUtil.getDateString("yyyy-MM-dd");
		String[] dataArray = dateString.split("-");
		int year = Integer.parseInt(dataArray[0]);
		return year+"-01-01 00:00:00";
	}

	/**
	 * 获得提前几月日期（1号）
	 * @param beforeMonth
	 * @return
	 */
	private String getBeforeMonthDate(int beforeMonth) {
		String dateString = DateUtil.getDateString("yyyy-MM-dd");
		String[] dataArray = dateString.split("-");
		int year = Integer.parseInt(dataArray[0]);
		int month = Integer.parseInt(dataArray[1]);
		String startDateStr = null;
		int startMonth = month;
		if(month>2){
			startMonth = month - (beforeMonth-1);
		}else{
			startMonth = 12 + (month-(beforeMonth-1));
		}
		if(startMonth<10){
			startDateStr = year+"-0"+ startMonth +"-01 00:00:00";
		}else{
			startDateStr = year+"-"+ startMonth +"-01 00:00:00";
		}
		return startDateStr;
	}
}
