package com.ai.slp.mall.web.model.account;

import java.io.Serializable;

import com.ai.opt.sdk.util.StringUtil;

public class UploadAccountData implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 文件类型
	 */
	private String dataType;
	/**
	 * 账期
	 */
	private String accountPeriod;
	/**
	 * 文件流水类型
	 */
	private String dataObj;
	/**
	 * 文件位置
	 */
	private String filePosition;
	/**
	 * 文件名称
	 */
	private String fileName;
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public String getAccountPeriod() {
		return accountPeriod;
	}
	public void setAccountPeriod(String accountPeriod) {
		if(!StringUtil.isBlank(accountPeriod)){
			accountPeriod = accountPeriod.replaceAll("-", "");
		}
		this.accountPeriod = accountPeriod;
	}
	public String getDataObj() {
		return dataObj;
	}
	public void setDataObj(String dataObj) {
		this.dataObj = dataObj;
	}
	public String getFilePosition() {
		return filePosition;
	}
	public void setFilePosition(String filePosition) {
		this.filePosition = filePosition;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
