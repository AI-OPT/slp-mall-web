package com.ai.slp.mall.web.model;

import java.io.Serializable;

public class QueryProductRequest implements Serializable {

	
	private static final long serialVersionUID = 1L;
	private String productId;
	private String productName;
	private String proferType;
	private String activeDate;
	private String invalidDate;
	private Integer pageNo;
	private Integer pageSize;
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProferType() {
		return proferType;
	}
	public void setProferType(String proferType) {
		this.proferType = proferType;
	}
	public String getActiveDate() {
		return activeDate;
	}
	public void setActiveDate(String activeDate) {
		this.activeDate = activeDate;
	}
	public String getInvalidDate() {
		return invalidDate;
	}
	public void setInvalidDate(String invalidDate) {
		this.invalidDate = invalidDate;
	}
	public Integer getPageNo() {
		return pageNo;
	}
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	
}
