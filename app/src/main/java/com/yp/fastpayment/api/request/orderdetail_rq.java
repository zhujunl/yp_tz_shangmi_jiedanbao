package com.yp.fastpayment.api.request;

public class orderdetail_rq {
    private String shopId;
    private String orderNo;

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    @Override
    public String toString() {
        return "orderdetail_rq{" +
                "shopId='" + shopId + '\'' +
                ", orderNo='" + orderNo + '\'' +
                '}';
    }
}
