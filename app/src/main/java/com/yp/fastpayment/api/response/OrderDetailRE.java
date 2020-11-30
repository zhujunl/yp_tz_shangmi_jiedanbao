package com.yp.fastpayment.api.response;

import java.util.List;

public class OrderDetailRE {
    private int code;
    private String message;
    private orderDetail_data data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public orderDetail_data getData() {
        return data;
    }

    public void setData(orderDetail_data data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "OrderDetail{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

    public static class orderDetail_data{
        private String branchName;
        private String orderNo;
        private String createTime;
        private String targetDate;
        private String mealTime;
        private int status;
        private int totalFee;
        private int totalCount;
        private int realFee;
        private String mealTimeStart;
        private String mealTimeEnd;
        private String mealTakingNum;
        private String seatNumber;
        private int mealType;
        private int discount;
        private Long packFee;
        private String payType;
        private String note;
        private String updateTime;
        private String memberName;
        private String phone;
        private List<meal_item> itemList;

        public String getBranchName() {
            return branchName;
        }

        public void setBranchName(String branchName) {
            this.branchName = branchName;
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

        public int getRealFee() {
            return realFee;
        }

        public void setRealFee(int realFee) {
            this.realFee = realFee;
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

        public Long getPackFee() {
            return packFee;
        }

        public void setPackFee(Long packFee) {
            this.packFee = packFee;
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

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
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
        //        val branchName: String,
//        val orderNo: String,
//        val createTime: String,
//        val targetDate: String,
//        val mealTime: String,
//        val status: Int,
//        val totalFee: Int,
//        val totalCount: Int,
//        val mealTimeStart :String,
//        val mealTimeEnd : String,
//        val mealTakingNum: String,
//        val seatNumber: String,
//        val mealType: Int,
//        val discount: Int,
//        val payType: String,
//        val note: String,
//        val updateTime: String,
//        val memberName: String,
//        val phone: String,
//        val itemList:List<item_meal>
//    ):Serializable{
//            data class item_meal(
//                    val name:String,
//                    val count:Int,
//                    val fee : Int
//            ):Serializable
//        }
    }
}
