package com.ai.slp.mall.web.model.account;

import java.io.Serializable;

public class FileData implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String position;
	
	private String name;

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
