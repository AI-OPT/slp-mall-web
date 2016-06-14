package com.ai.slp.mall.web.model.order;

import com.ai.slp.order.api.orderlist.param.QueryOrderListRequest;

public class OrderListQueryParams extends QueryOrderListRequest{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 查询方式 1：普通 2：高级
	 */
	private String searchType;
	
	/**
	 * 1:近3月订单 2：本年订单 3：所有历史订单
	 */
	private String selectTime;
	
	/**
	 * 订单状态
	 */
	private String states;

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public String getSelectTime() {
		return selectTime;
	}

	public void setSelectTime(String selectTime) {
		this.selectTime = selectTime;
	}

	public String getStates() {
		return states;
	}

	public void setStates(String states) {
		this.states = states;
	}

}
