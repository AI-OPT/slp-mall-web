package com.ai.slp.mall.web.model.product;

import java.io.Serializable;
import java.util.Map;

import com.ai.slp.product.api.webfront.param.FastSkuProdInfo;

public class PhoneFee implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Map<String,String> content; //面额
	private Map<String, FastSkuProdInfo> skuInfo; //sku
	public Map<String, String> getContent() {
		return content;
	}
	public void setContent(Map<String, String> content) {
		this.content = content;
	}
	public Map<String, FastSkuProdInfo> getSkuInfo() {
		return skuInfo;
	}
	public void setSkuInfo(Map<String, FastSkuProdInfo> skuInfo) {
		this.skuInfo = skuInfo;
	}
	
}
