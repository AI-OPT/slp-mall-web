package com.ai.slp.mall.web.model.product;

import java.io.Serializable;
import java.util.List;

public class FastProductResponse implements Serializable {

	
	private static final long serialVersionUID = 1L;

	private List<PhoneFee> phoneFee;

	public List<PhoneFee> getPhoneFee() {
		return phoneFee;
	}

	public void setPhoneFee(List<PhoneFee> phoneFee) {
		this.phoneFee = phoneFee;
	}
	
}
