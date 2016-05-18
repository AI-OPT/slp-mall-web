package com.ai.slp.mall.web.model;

/**
 * 账单优惠产品列表查询Vo
 */
public class BillDiscountQueryVo{

    private String productId;
    private String productName;
    private String discountType;
    private String effectDate;
    private String expireDate;
    
    public String getProductId() {
        return productId;
    }
    public void setProductId(String productId) {
        this.productId = productId;
    }
    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
    public String getDiscountType() {
        return discountType;
    }
    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }
    public String getEffectDate() {
        return effectDate;
    }
    public void setEffectDate(String effectDate) {
        this.effectDate = effectDate;
    }
    public String getExpireDate() {
        return expireDate;
    }
    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }
	
}
