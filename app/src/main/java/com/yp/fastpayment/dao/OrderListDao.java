package com.yp.fastpayment.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.yp.fastpayment.api.response.OrderListRE;
import com.yp.fastpayment.model.orderlist_mode;
import com.yp.fastpayment.util.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class OrderListDao {
    private DBHelper mHelper;
    private SQLiteDatabase mDatabase;

    public OrderListDao(Context context) {
        mHelper = new DBHelper(context);
        mDatabase = mHelper.getWritableDatabase();
    }

//    String getorderlist = "create table orderList" +
//            "(id integer primary key autoincrement, " +
//            "orderNo varchar, " +
//            "createTime varchar, " +
//            "mealTakingNum varchar, " +
//            "mealTime varchar," +
//            "mealTimeEnd varchar," +
//            "reserveType integer," +
//            "mealTimeStart varchar," +
//            "customerName varchar, " +
//            "seatNumber varchar," +
//            "printstatus integer,"+
//            "status integer," +
//            "shopId integer,"+
//            "branchId integer"+
//            ")";
//        db.execSQL(getorderlist);

    public void insertData(OrderListRE.orderlist_data orderList, Integer shopId, Integer branchId){
        ContentValues values = new ContentValues();
        values.put("orderNo", orderList.getOrderNo());
        values.put("createTime", orderList.getCreateTime());
        values.put("mealTakingNum",orderList.getMealTakingNum());
        values.put("mealTime", orderList.getMealTime());
        values.put("mealTimeEnd", orderList.getMealTimeEnd());
        values.put("reserveType", orderList.getReserveType());
        values.put("mealTimeStart", orderList.getMealTimeStart());
        values.put("customerName", orderList.getCustomerName());
        values.put("seatNumber", orderList.getSeatNumber());
        values.put("status", orderList.getStatus());
        values.put("printstatus", 0);
        values.put("shopId", shopId);
        values.put("branchId", branchId);
        mDatabase.insert("orderList", null, values);

//        for (MeshOrderItemVO itemVO : orderVO.getOrderItemList()) {
//
//            ContentValues orderItem = new ContentValues();
//
//            orderItem.put("orderNo", orderVO.getOrderNo());
//            orderItem.put("productId", itemVO.getProductId());
//            orderItem.put("productName", itemVO.getProductName());
//            orderItem.put("price", itemVO.getPrice());
//            orderItem.put("quantity", itemVO.getQuantity());
//
//            mDatabase.insert("order_item", null, orderItem);
//        }
    }
    public void updatePrintState(int state, String orderNo) {
        ContentValues values = new ContentValues();
        values.put("printstatus", state);
        mDatabase.update("orderList", values, " orderNo = '" + orderNo + "'", null);
    }


    public void updateState(int state, String orderNo) {
        ContentValues values = new ContentValues();
        values.put("status", state);
        mDatabase.update("orderList", values, " orderNo = '" + orderNo + "'", null);
    }

    public orderlist_mode queryOrderByOrderNo(String orderNo) {
        String sql = "select * from orderList where orderNo = ?";
        Cursor cursor = mDatabase.rawQuery(sql, new String[]{orderNo});

        if (cursor == null) {
            return null;
        }

        while (cursor.moveToNext()) {
            orderlist_mode orderlist_mode = new orderlist_mode();
            orderlist_mode.setOrderNo(cursor.getString(1));
            orderlist_mode.setCreateTime(cursor.getString(2));
            orderlist_mode.setMealTakingNum(cursor.getString(3));
            orderlist_mode.setMealTime(cursor.getString(4));
            orderlist_mode.setMealTimeEnd(cursor.getString(5));
            orderlist_mode.setReserveType(cursor.getInt(6));
            orderlist_mode.setMealTimeStart(cursor.getString(7));
            orderlist_mode.setCustomerName(cursor.getString(8));;
            orderlist_mode.setSeatNumber(cursor.getString(9));
            orderlist_mode.setPrintState(cursor.getInt(10));
            orderlist_mode.setStatus(cursor.getInt(11));
            orderlist_mode.setShopId(cursor.getInt(12));
            orderlist_mode.setBranchId(cursor.getInt(13));

            return orderlist_mode;
        }

        return null;
    }

    public List<orderlist_mode> query(Integer shopId, Integer branchId) {
        String sql = "select * from orderList where shopId = ? and branchId = ? order by id desc";
        Cursor cursor = mDatabase.rawQuery(sql, new String[]{shopId.toString(), branchId.toString()});

        if (cursor == null) {
            return null;
        }

        List<orderlist_mode> orderInfoList = new ArrayList<>();
        while (cursor.moveToNext()) {
            orderlist_mode orderlist_mode = new orderlist_mode();
            orderlist_mode.setOrderNo(cursor.getString(1));
            orderlist_mode.setCreateTime(cursor.getString(2));
            orderlist_mode.setMealTakingNum(cursor.getString(3));
            orderlist_mode.setMealTime(cursor.getString(4));
            orderlist_mode.setMealTimeEnd(cursor.getString(5));
            orderlist_mode.setReserveType(cursor.getInt(6));
            orderlist_mode.setMealTimeStart(cursor.getString(7));
            orderlist_mode.setCustomerName(cursor.getString(8));;
            orderlist_mode.setSeatNumber(cursor.getString(9));
            orderlist_mode.setPrintState(cursor.getInt(10));
            orderlist_mode.setStatus(cursor.getInt(11));
            orderlist_mode.setShopId(cursor.getInt(12));
            orderlist_mode.setBranchId(cursor.getInt(13));
            orderInfoList.add(orderlist_mode);
        }
        return orderInfoList;
    }

    public List<orderlist_mode> querybystyle(Integer style,Integer shopId, Integer branchId) {
        String sql = "select * from orderList where shopId = ? and branchId = ? and reserveType = ? order by id desc";
        Cursor cursor = mDatabase.rawQuery(sql, new String[]{shopId.toString(), branchId.toString(),style.toString()});

        if (cursor == null) {
            return null;
        }

        List<orderlist_mode> orderInfoList = new ArrayList<>();
        while (cursor.moveToNext()) {
            orderlist_mode orderlist_mode = new orderlist_mode();
            orderlist_mode.setOrderNo(cursor.getString(1));
            orderlist_mode.setCreateTime(cursor.getString(2));
            orderlist_mode.setMealTakingNum(cursor.getString(3));
            orderlist_mode.setMealTime(cursor.getString(4));
            orderlist_mode.setMealTimeEnd(cursor.getString(5));
            orderlist_mode.setReserveType(cursor.getInt(6));
            orderlist_mode.setMealTimeStart(cursor.getString(7));
            orderlist_mode.setCustomerName(cursor.getString(8));;
            orderlist_mode.setSeatNumber(cursor.getString(9));
            orderlist_mode.setPrintState(cursor.getInt(10));
            orderlist_mode.setStatus(cursor.getInt(11));
            orderlist_mode.setShopId(cursor.getInt(12));
            orderlist_mode.setBranchId(cursor.getInt(13));
            orderInfoList.add(orderlist_mode);
        }
        return orderInfoList;
    }
}
