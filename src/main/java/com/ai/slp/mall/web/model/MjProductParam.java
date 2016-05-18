package com.ai.slp.mall.web.model;

import java.util.List;

public class MjProductParam {

	/**
	 * 优惠产品类型，必填
	 */
	private String productType;
	
	
	/**
	 * 优惠活动名称
	 */
	private String programName;
	
	/**
	 * 规则金额
	 */
	private String ruleAmount;
	/**
	 * 规则单位
	 */
	private String ruleUnit;
	/**
	 * 满减金额
	 */
	private String reduceAmount;
	
	/**
	 * 生效日期，必填
	 */
	private String activeDate;
	
	/**
	 * 失效时间，必填
	 */
	private String invalidDate;
	/**
	 * 备注
	 */
	private String comments;
	/**
	 * 已选择的产品列表,必填
	 */
	private List<String> productList;
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public String getProgramName() {
		return programName;
	}
	public void setProgramName(String programName) {
		this.programName = programName;
	}
	public String getRuleAmount() {
		return ruleAmount;
	}
	public void setRuleAmount(String ruleAmount) {
		this.ruleAmount = ruleAmount;
	}
	public String getRuleUnit() {
		return ruleUnit;
	}
	public void setRuleUnit(String ruleUnit) {
		this.ruleUnit = ruleUnit;
	}
	
	public String getReduceAmount() {
		return reduceAmount;
	}
	public void setReduceAmount(String reduceAmount) {
		this.reduceAmount = reduceAmount;
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
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public List<String> getProductList() {
		return productList;
	}
	public void setProductList(List<String> productList) {
		this.productList = productList;
	}
	
	
	
}
