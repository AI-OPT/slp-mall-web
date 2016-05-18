package com.ai.slp.mall.web.model.verify;

public class PhoneVerifyCodeReq {
    
    private String phone;           //手机号
    private String phoneVerifyCode; //短信验证码
    private String codeTemplateId;  //短信模板ID

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhoneVerifyCode() {
        return phoneVerifyCode;
    }

    public void setPhoneVerifyCode(String phoneVerifyCode) {
        this.phoneVerifyCode = phoneVerifyCode;
    }

    public String getCodeTemplateId() {
        return codeTemplateId;
    }

    public void setCodeTemplateId(String codeTemplateId) {
        this.codeTemplateId = codeTemplateId;
    }

}
