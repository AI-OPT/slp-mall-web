package com.ai.slp.mall.web.model;

import java.util.List;

public class EditProductParam {

	/**
	 * 产品Id
	 */
	private String productId;
	
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

	/**
	 * 操作员Id
	 */
	private String operatorId;
	
	/**
	 * 满赠数据
	 */
	private List<String> presentList;
	/**
	 * 资费编码
	 */
	private String priceCode;
	
	
	/**
	 * 赠品生效日期
	 */
	private String pactiveTime;
	/**
	 * 赠品失效日期
	 */
	private String pinavalidTime;
	/**
	 * 生效标记
	 */
	private String activeFlag;
	/**
	 * 生效周期
	 */
	private String activeCycle;
	
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
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
	public String getOperatorId() {
		return operatorId;
	}
	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}
	public List<String> getPresentList() {
		return presentList;
	}
	public void setPresentList(List<String> presentList) {
		this.presentList = presentList;
	}
	public String getPriceCode() {
		return priceCode;
	}
	public void setPriceCode(String priceCode) {
		this.priceCode = priceCode;
	}
	public String getPactiveTime() {
		return pactiveTime;
	}
	public void setPactiveTime(String pactiveTime) {
		this.pactiveTime = pactiveTime;
	}
	public String getPinavalidTime() {
		return pinavalidTime;
	}
	public void setPinavalidTime(String pinavalidTime) {
		this.pinavalidTime = pinavalidTime;
	}
	public String getActiveFlag() {
		return activeFlag;
	}
	public void setActiveFlag(String activeFlag) {
		this.activeFlag = activeFlag;
	}
	public String getActiveCycle() {
		return activeCycle;
	}
	public void setActiveCycle(String activeCycle) {
		this.activeCycle = activeCycle;
	}
	
	
	
	
}
