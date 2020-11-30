package com.yp.fastpayment.api.request;

public class orderlist_rq {
    private String shopId;
    private String branchId;

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    @Override
    public String toString() {
        return "orderlist_rq{" +
                "shopId='" + shopId + '\'' +
                ", branchId='" + branchId + '\'' +
                '}';
    }
}
