package com.yp.fastpayment.api.request;

public class orderstatus_rq {
    private int shopId;
    private String  orderNo;
    private int status;

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "orderstatus_rq{" +
                "shopId=" + shopId +
                ", orderNo='" + orderNo + '\'' +
                ", status=" + status +
                '}';
    }
}
