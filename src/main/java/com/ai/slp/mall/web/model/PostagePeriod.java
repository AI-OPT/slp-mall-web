package com.ai.slp.mall.web.model;

public class PostagePeriod {

	private String serviceType;
	private String subServiceType;
	private String amount;
	private String unit;
	
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public String getSubServiceType() {
		return subServiceType;
	}
	public void setSubServiceType(String subServiceType) {
		this.subServiceType = subServiceType;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
}
