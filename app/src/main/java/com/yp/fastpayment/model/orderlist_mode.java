package com.yp.fastpayment.model;

public class orderlist_mode {
    private int shopId;
    private int branchId;
    private String orderNo;
    private String createTime;
    private String mealTakingNum;
    private String mealTime;
    private String mealTimeEnd;
    private int reserveType;
    private String mealTimeStart;
    private String customerName;
    private String seatNumber;
    private Integer printState;
    private String mealCode;
    private int status;

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getMealTakingNum() {
        return mealTakingNum;
    }

    public void setMealTakingNum(String mealTakingNum) {
        this.mealTakingNum = mealTakingNum;
    }

    public String getMealTime() {
        return mealTime;
    }

    public void setMealTime(String mealTime) {
        this.mealTime = mealTime;
    }

    public String getMealTimeEnd() {
        return mealTimeEnd;
    }

    public void setMealTimeEnd(String mealTimeEnd) {
        this.mealTimeEnd = mealTimeEnd;
    }

    public int getReserveType() {
        return reserveType;
    }

    public void setReserveType(int reserveType) {
        this.reserveType = reserveType;
    }

    public String getMealTimeStart() {
        return mealTimeStart;
    }

    public void setMealTimeStart(String mealTimeStart) {
        this.mealTimeStart = mealTimeStart;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public Integer getPrintState() {
        return printState;
    }

    public void setPrintState(Integer printState) {
        this.printState = printState;
    }

    public String getMealCode() {
        return mealCode;
    }

    public void setMealCode(String mealCode) {
        this.mealCode = mealCode;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "orderlist_mode{" +
                "shopId=" + shopId +
                ", branchId=" + branchId +
                ", orderNo='" + orderNo + '\'' +
                ", createTime='" + createTime + '\'' +
                ", mealTakingNum='" + mealTakingNum + '\'' +
                ", mealTime='" + mealTime + '\'' +
                ", mealTimeEnd='" + mealTimeEnd + '\'' +
                ", reserveType=" + reserveType +
                ", mealTimeStart='" + mealTimeStart + '\'' +
                ", customerName='" + customerName + '\'' +
                ", seatNumber='" + seatNumber + '\'' +
                ", printState=" + printState +
                ", mealCode='" + mealCode + '\'' +
                ", status=" + status +
                '}';
    }
}
