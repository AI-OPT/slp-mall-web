package com.ai.slp.mall.web.model;

import java.util.List;

/**
 * 标准业务资费新增vo
 * @author wangluyang
 *
 */
public class AddStandPostageVo {
	
	private String priceName;
	private List<PostagePeriod> periods;
	private String periodsJsonStr;
	private String cycleType;
	private String cycleAmount;
	private String standardPrice;
	
	private String unit;
	
	private String standardId;
	private String comments;
	private String cycleId;
	private String price;
	private String priceType;
	private String serviceType;
	private String status;
	
	private String subjectCode;
	
	public String getPriceName() {
		return priceName;
	}
	public void setPriceName(String priceName) {
		this.priceName = priceName;
	}
	public List<PostagePeriod> getPeriods() {
		return periods;
	}
	public void setPeriods(List<PostagePeriod> periods) {
		this.periods = periods;
	}
	public String getPeriodsJsonStr() {
		return periodsJsonStr;
	}
	public void setPeriodsJsonStr(String periodsJsonStr) {
		this.periodsJsonStr = periodsJsonStr;
	}
	public String getCycleType() {
		return cycleType;
	}
	public void setCycleType(String cycleType) {
		this.cycleType = cycleType;
	}
	public String getCycleAmount() {
		return cycleAmount;
	}
	public void setCycleAmount(String cycleAmount) {
		this.cycleAmount = cycleAmount;
	}
	public String getStandardPrice() {
		return standardPrice;
	}
	public void setStandardPrice(String standardPrice) {
		this.standardPrice = standardPrice;
	}
	public String getStandardId() {
		return standardId;
	}
	public void setStandardId(String standardId) {
		this.standardId = standardId;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getCycleId() {
		return cycleId;
	}
	public void setCycleId(String cycleId) {
		this.cycleId = cycleId;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getPriceType() {
		return priceType;
	}
	public void setPriceType(String priceType) {
		this.priceType = priceType;
	}
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getSubjectCode() {
		return subjectCode;
	}
	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}
}
