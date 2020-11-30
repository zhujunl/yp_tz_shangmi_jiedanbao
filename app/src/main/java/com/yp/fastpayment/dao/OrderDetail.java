package com.yp.fastpayment.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.yp.fastpayment.api.response.MeshOrderItemVO;
import com.yp.fastpayment.api.response.OrderDetailRE;
import com.yp.fastpayment.api.response.meal_item;
import com.yp.fastpayment.model.orderdetail_model;
import com.yp.fastpayment.util.DBHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderDetail {
    private DBHelper mHelper;
    private SQLiteDatabase mDatabase;

    public OrderDetail(DBHelper mHelper, SQLiteDatabase mDatabase) {
        this.mHelper = mHelper;
        this.mDatabase = mDatabase;
    }


//    String getorderdetail="create table orderdetail" +
//            "(id integer primary key autoincrement, " +
//            "branchName varchar, " +
//            "orderNo varchar," +
//            "createTime varchar," +
//            "targetDate varchar,"+
//            "mealTime varchar,"+
//            "status integer," +
//            "totalFee integer," +
//            "totalCount integer," +
//            "mealTimeStart varchar,"+
//            "mealTimeEnd varchar,"+
//            "mealTakingNum varchar,"+
//            "seatNumber varchar,"+
//            "mealType varchar,"+
//            "discount integer,"+
//            "payType varchar,"+
//            "note varchar,"+
//            "updateTime varchar,"+
//            "memberName varchar,"+
//            "phone varchar,"+
//            "neam varchar,"+
//            "count integer,"+
//            "fee integer"+
//            ")";
//        db.execSQL(getorderdetail);
    public void insertData(OrderDetailRE.orderDetail_data orderList, String orderNo){
        ContentValues values = new ContentValues();
        values.put("orderNo", orderNo);
        values.put("createTime", orderList.getCreateTime());
        values.put("targetDate",orderList.getTargetDate());
        values.put("mealTime", orderList.getMealTime());
        values.put("status", orderList.getStatus());
        values.put("totalFee",orderList.getTotalFee());
        values.put("totalCount",orderList.getTotalCount());
        values.put("mealTimeStart", orderList.getMealTimeStart());
        values.put("mealTimeEnd", orderList.getMealTimeEnd());
        values.put("mealTakingNum",orderList.getMealTakingNum());
        values.put("seatNumber", orderList.getSeatNumber());
        values.put("mealType",orderList.getMealType());
        values.put("discount",orderList.getDiscount());
        values.put("payType",orderList.getPayType());
        values.put("note",orderList.getNote());
        values.put("updateTime",orderList.getUpdateTime());
        values.put("membername",orderList.getMemberName());
        values.put("phone",orderList.getPhone());
        values.put("printState", 0);
        mDatabase.insert("orderdetail", null, values);

            for (meal_item itemVO : orderList.getItemList()) {

                ContentValues orderItem = new ContentValues();

                orderItem.put("orderNo", orderList.getOrderNo());
                orderItem.put("name", itemVO.getName());
                orderItem.put("count", itemVO.getCount());
                orderItem.put("fee", itemVO.getFee());

                mDatabase.insert("meal_item", null, orderItem);
            }
    }
    public void updatePrintState(int state, String orderNo) {
        ContentValues values = new ContentValues();
        values.put("printState", state);
        mDatabase.update("orderdetail", values, " orderNo = '" + orderNo + "'", null);
    }


    public void updateState(int state, String orderNo) {
        ContentValues values = new ContentValues();
        values.put("status", state);
        mDatabase.update("orderdetail", values, " orderNo = '" + orderNo + "'", null);
    }

//    public List<OrderDetailRE.orderDetail_data> query(String orderNO) {
//        String sql = "select * from orderdetail where orderNo = ?";
//        Cursor cursor = mDatabase.rawQuery(sql, new String[]{orderNO});
//
//        if (cursor == null) {
//            return null;
//        }
//
//        List<OrderDetailRE.orderDetail_data> orderInfoList = new ArrayList<>();
//        while (cursor.moveToNext()) {
//            orderdetail_model orin = new orderdetail_model();
//            orin.setOrderNo(cursor.getString(1));
//            orin.setCreateTime(cursor.getString(2));
//            orin.setTargetDate(cursor.getString(3));
//            orin.setMealTime(cursor.getString(4));
//            orin.setStatus(cursor.getInt(5));
//            orin.setTotalFee(cursor.getInt(6));
//            orin.setTotalCount(cursor.getInt(7));
//            orin.setMealTimeStart(cursor.getString(8));
//            orin.setMealTimeEnd(cursor.getString(9));
//            orin.setMealTakingNum(cursor.getString(10));
//            orin.setSeatNumber(cursor.getString(11));
//            orin.setMealType(cursor.getInt(12));
//            orin.setDiscount(cursor.getInt(13));
//            orin.setPayType(cursor.getString(14));
//            orin.setNote(cursor.getString(15));
//            orin.setUpdataTime(cursor.getString(16));
//            orin.setMemberName(cursor.getString(17));
//            orin.setPhone(cursor.getString(18));
//            orin.setPrintstatus(cursor.getInt(19));
//            List<meal_item> orderItemVOList = queryByOrderNo(orin.getOrderNo());
//            orin.setItemList(orderItemVOList);
//            orderInfoList.add(orin);
//        }
//        return orderInfoList;
//    }
//
//    public List<meal_item> queryByOrderNo(String orderNo) {
//        String sql = "select * from order_item where orderNo = '" + orderNo + "'";
//        Cursor cursor = mDatabase.rawQuery(sql, null);
//
//        if (cursor == null) {
//            return null;
//        }
//        List<meal_item> orderItemVOList = new ArrayList<>();
//        while (cursor.moveToNext()) {
//
//            meal_item orderItemVO = new meal_item();
//
//            orderItemVO.setProductId(cursor.getInt(2));
//            orderItemVO.setProductName(cursor.getString(3));
//            orderItemVO.setPrice(Long.valueOf(cursor.getInt(4)));
//            orderItemVO.setQuantity(cursor.getInt(5));
//            orderItemVOList.add(orderItemVO);
//
//        }
//        return orderItemVOList;
//    }
}
