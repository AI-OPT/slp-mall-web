package com.ai.slp.mall.web.model.product;

import java.io.Serializable;
import java.util.Map;

import com.ai.slp.product.api.webfront.param.FastSkuProdInfo;

public class FastProductInfo implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Map<String, FastSkuProdInfo> PhoneInfoMap;

	public Map<String, FastSkuProdInfo> getPhoneInfoMap() {
		return PhoneInfoMap;
	}

	public void setPhoneInfoMap(Map<String, FastSkuProdInfo> phoneInfoMap) {
		PhoneInfoMap = phoneInfoMap;
	}
	
	
}
