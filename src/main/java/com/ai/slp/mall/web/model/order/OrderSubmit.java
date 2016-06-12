package com.ai.slp.mall.web.model.order;

import java.util.List;

import com.ai.slp.order.api.ordertradecenter.param.OrdFeeInfo;
import com.ai.slp.order.api.ordertradecenter.param.OrdProductResInfo;

public class OrderSubmit {
    /**
     * 订单ID
     */
    private long orderId;

    /**
     * 运费
     */
    private double expFee;

    /**
     * 账户余额
     */
    private double balanceFee;

    /**
     * 可用余额
     */
    private double balance;

    /**
     * 产品列表
     */
    private List<OrdProductResInfo> ordProductResList;

    /**
     * 订单费用
     */
    private OrdFeeInfo ordFeeInfo;

    public long getOrderId() {
        return orderId;
    }

    public double getExpFee() {
        return expFee;
    }

    public double getBalanceFee() {
        return balanceFee;
    }

    public double getBalance() {
        return balance;
    }

    public List<OrdProductResInfo> getOrdProductResList() {
        return ordProductResList;
    }

    public OrdFeeInfo getOrdFeeInfo() {
        return ordFeeInfo;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public void setExpFee(double expFee) {
        this.expFee = expFee;
    }

    public void setBalanceFee(double balanceFee) {
        this.balanceFee = balanceFee;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setOrdProductResList(List<OrdProductResInfo> ordProductResList) {
        this.ordProductResList = ordProductResList;
    }

    public void setOrdFeeInfo(OrdFeeInfo ordFeeInfo) {
        this.ordFeeInfo = ordFeeInfo;
    }

}
