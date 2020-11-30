package com.yp.fastpayment.api.response;

public class orderlist_data {
    private String orderNo;
    private String createTime;
    private String mealTakingNum;
    private String mealTime;
    private String mealTimeEnd;
    private int reserveType;
    private String mealTimeStart;
    private String customerName;
    private String seatNumber;
    private int status;

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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
