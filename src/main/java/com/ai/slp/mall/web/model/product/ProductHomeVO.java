package com.ai.slp.mall.web.model.product;

import com.ai.slp.product.api.webfront.param.ProductHomeResponse;

public class ProductHomeVO extends ProductHomeResponse{
    private static final long serialVersionUID = 1L;
    //图片路径
    private String picUrl;

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }
    

}
