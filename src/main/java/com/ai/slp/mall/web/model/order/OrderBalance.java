package com.ai.slp.mall.web.model.order;

public class OrderBalance {
    /**
     * 订单ID
     */
    private String orderId;

    /**
     * 订单类型
     */
    private String orderType;

    /**
     * 金额
     */
    private long orderAmount;

    public String getOrderId() {
        return orderId;
    }

    public String getOrderType() {
        return orderType;
    }

    public long getOrderAmount() {
        return orderAmount;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public void setOrderAmount(long orderAmount) {
        this.orderAmount = orderAmount;
    }

}
