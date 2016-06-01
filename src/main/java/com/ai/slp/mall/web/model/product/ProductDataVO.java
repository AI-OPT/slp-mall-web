package com.ai.slp.mall.web.model.product;

import java.util.List;

import com.ai.slp.product.api.webfront.param.ProductData;

public class ProductDataVO extends ProductData {
    private static final long serialVersionUID = 1L;

    /**
     * 主图片路径
     */
    private String picUrl;

    /**
     * 缩略图路径
     */
    private List<String> thumnailUrl;

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public List<String> getThumnailUrl() {
        return thumnailUrl;
    }

    public void setThumnailUrl(List<String> thumnailUrl) {
        this.thumnailUrl = thumnailUrl;
    }

}
