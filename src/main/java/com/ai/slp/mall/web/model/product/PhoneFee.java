package com.ai.slp.mall.web.model.product;

import java.io.Serializable;
import java.util.Map;

import com.ai.slp.product.api.webfront.param.FastSkuProdInfo;

public class PhoneFee implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String content; //面额
	private  FastSkuProdInfo skuInfo; //sku
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public FastSkuProdInfo getSkuInfo() {
		return skuInfo;
	}
	public void setSkuInfo(FastSkuProdInfo skuInfo) {
		this.skuInfo = skuInfo;
	}
	
	
}
