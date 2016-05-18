package com.ai.slp.mall.web.model;

import com.ai.opt.base.vo.BaseInfo;

public class PaymentLogVo extends BaseInfo{
    private static final long serialVersionUID = 1L;
    private String custName;
    private String custGrade;
    private String paySerialCode;
    private String startTime;
    private String endTime;
    private String bottomAmount;
    private String topAmount;
    public String getCustName() {
        return custName;
    }
    public void setCustName(String custName) {
        this.custName = custName;
    }
    public String getCustGrade() {
        return custGrade;
    }
    public void setCustGrade(String custGrade) {
        this.custGrade = custGrade;
    }
    public String getPaySerialCode() {
        return paySerialCode;
    }
    public void setPaySerialCode(String paySerialCode) {
        this.paySerialCode = paySerialCode;
    }
    public String getStartTime() {
        return startTime;
    }
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
    public String getEndTime() {
        return endTime;
    }
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
    public String getBottomAmount() {
        return bottomAmount;
    }
    public void setBottomAmount(String bottomAmount) {
        this.bottomAmount = bottomAmount;
    }
    public String getTopAmount() {
        return topAmount;
    }
    public void setTopAmount(String topAmount) {
        this.topAmount = topAmount;
    }
    
}
