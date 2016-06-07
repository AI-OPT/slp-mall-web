package com.ai.slp.mall.web.model.product;

import java.util.List;

import com.ai.slp.product.api.webfront.param.ProductAttrInfo;

public class ProductCommonVO {
    private List<ProductAttrInfo> agentList;
    private List<ProductAttrInfo> accountList;
    private List<ProductAttrInfo> areaList;
    public List<ProductAttrInfo> getAgentList() {
        return agentList;
    }
    public void setAgentList(List<ProductAttrInfo> agentList) {
        this.agentList = agentList;
    }
    public List<ProductAttrInfo> getAccountList() {
        return accountList;
    }
    public void setAccountList(List<ProductAttrInfo> accountList) {
        this.accountList = accountList;
    }
    public List<ProductAttrInfo> getAreaList() {
        return areaList;
    }
    public void setAreaList(List<ProductAttrInfo> areaList) {
        this.areaList = areaList;
    }
    

}
