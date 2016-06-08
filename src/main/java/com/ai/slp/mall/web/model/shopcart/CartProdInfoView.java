package com.ai.slp.mall.web.model.shopcart;

import java.sql.Timestamp;

/**
 * Created by jackieliu on 16/6/8.
 */
public class CartProdInfoView {
    private String skuId;
    private String productName;
    private String productId;
    private String saleAttrs;
    private long buyNum;
    private String state;
    private long usableNum;
    private String vfsId;
    private String picType;
    private String picUrl;
    private Timestamp insertTime;
    private Long salePrice;
    private long prodPrice;

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getSaleAttrs() {
        return saleAttrs;
    }

    public void setSaleAttrs(String saleAttrs) {
        this.saleAttrs = saleAttrs;
    }

    public long getBuyNum() {
        return buyNum;
    }

    public void setBuyNum(long buyNum) {
        this.buyNum = buyNum;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public long getUsableNum() {
        return usableNum;
    }

    public void setUsableNum(long usableNum) {
        this.usableNum = usableNum;
    }

    public String getVfsId() {
        return vfsId;
    }

    public void setVfsId(String vfsId) {
        this.vfsId = vfsId;
    }

    public String getPicType() {
        return picType;
    }

    public void setPicType(String picType) {
        this.picType = picType;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public Timestamp getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Timestamp insertTime) {
        this.insertTime = insertTime;
    }

    public Long getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Long salePrice) {
        this.salePrice = salePrice;
    }

    public long getProdPrice() {
        return prodPrice;
    }

    public void setProdPrice(long prodPrice) {
        this.prodPrice = prodPrice;
    }
}
