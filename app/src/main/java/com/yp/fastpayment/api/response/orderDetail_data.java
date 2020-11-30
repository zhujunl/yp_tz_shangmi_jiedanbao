package com.yp.fastpayment.api.response;

import java.util.List;

public class orderDetail_data {
    private String orderNo;
    private String createTime;
    private String targetDate;
    private String mealTime;
    private int status;
    private int totalFee;
    private int totalCount;
    private String mealTimeStart;
    private String mealTimeEnd;
    private String mealTakingNum;
    private String seatNumber;
    private int mealType;
    private int discount;
    private String payType;
    private String note;
    private String updataTime;
    private String memberName;
    private String phone;
    private List<meal_item> itemList;

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

    public String getTargetDate() {
        return targetDate;
    }

    public void setTargetDate(String targetDate) {
        this.targetDate = targetDate;
    }

    public String getMealTime() {
        return mealTime;
    }

    public void setMealTime(String mealTime) {
        this.mealTime = mealTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(int totalFee) {
        this.totalFee = totalFee;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public String getMealTimeStart() {
        return mealTimeStart;
    }

    public void setMealTimeStart(String mealTimeStart) {
        this.mealTimeStart = mealTimeStart;
    }

    public String getMealTimeEnd() {
        return mealTimeEnd;
    }

    public void setMealTimeEnd(String mealTimeEnd) {
        this.mealTimeEnd = mealTimeEnd;
    }

    public String getMealTakingNum() {
        return mealTakingNum;
    }

    public void setMealTakingNum(String mealTakingNum) {
        this.mealTakingNum = mealTakingNum;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public int getMealType() {
        return mealType;
    }

    public void setMealType(int mealType) {
        this.mealType = mealType;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getUpdataTime() {
        return updataTime;
    }

    public void setUpdataTime(String updataTime) {
        this.updataTime = updataTime;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<meal_item> getItemList() {
        return itemList;
    }

    public void setItemList(List<meal_item> itemList) {
        this.itemList = itemList;
    }
}
