package com.ai.slp.mall.web.model.user;

import java.io.Serializable;

/**
 * 身份安全确认信息
 * @author jiaxs
 *
 */
public class SafetyConfirmData implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 账号ID
	 */
	private Long accountId;
	/**
	 * 验证方式
	 */
	private String confirmType;
	/**
	 * 图形验证码
	 */
	private String pictureVerifyCode;
	/**
	 * 验证码
	 */
	private String verifyCode;
	
	/**
	 * 手机号
	 * @return
	 * @author zhangyh7
	 * @ApiDocMethod
	 */
	private String userMp;
	
	public Long getAccountId() {
		return accountId;
	}
	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	public String getConfirmType() {
		return confirmType;
	}
	public void setConfirmType(String confirmType) {
		this.confirmType = confirmType;
	}
	public String getPictureVerifyCode() {
		return pictureVerifyCode;
	}
	public void setPictureVerifyCode(String pictureVerifyCode) {
		this.pictureVerifyCode = pictureVerifyCode;
	}
	public String getVerifyCode() {
		return verifyCode;
	}
	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
    public String getUserMp() {
        return userMp;
    }
    public void setUserMp(String userMp) {
        this.userMp = userMp;
    }
}
