package com.ai.slp.mall.web.model.product;

import java.util.List;


public class ProductImagesVO {
	
	private List<String> bigImagesUrl;
	private List<String> smallImagesUrl;
	
	public List<String> getBigImagesUrl() {
		return bigImagesUrl;
	}
	public void setBigImagesUrl(List<String> bigImagesUrl) {
		this.bigImagesUrl = bigImagesUrl;
	}
	public List<String> getSmallImagesUrl() {
		return smallImagesUrl;
	}
	public void setSmallImagesUrl(List<String> smallImagesUrl) {
		this.smallImagesUrl = smallImagesUrl;
	}
	
}
