package com.ai.slp.mall.web.model.order;

public class PayOrderRequest {

	private String userId;
	private String orderType;
	private String skuId;
	private String buySum;
	private String basicOrgId;
	private String provinceCode;
	private String chargeFee;
	private String phoneNum;
	private String userType;
	
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getSkuId() {
		return skuId;
	}
	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}
	public String getBuySum() {
		return buySum;
	}
	public void setBuySum(String buySum) {
		this.buySum = buySum;
	}
	public String getBasicOrgId() {
		return basicOrgId;
	}
	public void setBasicOrgId(String basicOrgId) {
		this.basicOrgId = basicOrgId;
	}
	public String getProvinceCode() {
		return provinceCode;
	}
	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}
	public String getChargeFee() {
		return chargeFee;
	}
	public void setChargeFee(String chargeFee) {
		this.chargeFee = chargeFee;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	
	
}
