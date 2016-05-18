package com.ai.slp.mall.web.model;

/**
 * 标准资费查询vo
 * @author wangluyang
 *
 */
public class StandPostageQueryVo {

	private String standardId;
	private String priceName;
	private String serviceType;
	private String serviceTypeDetail;
	private String priceState;
	
	public String getStandardId() {
		return standardId;
	}
	public void setStandardId(String standardId) {
		this.standardId = standardId;
	}
	public String getPriceName() {
		return priceName;
	}
	public void setPriceName(String priceName) {
		this.priceName = priceName;
	}
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public String getServiceTypeDetail() {
		return serviceTypeDetail;
	}
	public void setServiceTypeDetail(String serviceTypeDetail) {
		this.serviceTypeDetail = serviceTypeDetail;
	}
	public String getPriceState() {
		return priceState;
	}
	public void setPriceState(String priceState) {
		this.priceState = priceState;
	}
}
