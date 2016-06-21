package com.ai.slp.mall.web.model.order;

import java.io.Serializable;

public class OrderBalance implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 订单ID
     */
    private String orderId;

    /**
     * 扣款流水
     */
    private String serialNo;

    public String getOrderId() {
        return orderId;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

}
