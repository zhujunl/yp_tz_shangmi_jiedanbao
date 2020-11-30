package com.yp.fastpayment.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    // 数据库文件名
    public static final String DB_NAME = "shangmi92.db";

    // 数据库表名
    // 数据库版本号
    public static final int DB_VERSION = 8;


    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    // 当数据库文件创建时，执行初始化操作，并且只执行一次
    @Override
    public void onCreate(SQLiteDatabase db) {

        // 建表 商户配置表
        String sql3 = "create table shopConfig" +
                "(id integer primary key autoincrement, " +
                "shopId integer, " +
                "branchId integer, " +
                "cashierDeskId integer ," +
                "shopName varchar ," +
                "username varchar, " +
                "autoLogin integer, " +
                "autoPrint integer, " +
                "password varchar "
                + ")";

        db.execSQL(sql3);


        // 建表 订单表
        String orderInfoSql = "create table mesh_order" +
                "(id integer primary key autoincrement, " +
                "shopId integer, " +
                "customerId integer, " +
                "branchId integer, " +
                "serial varchar, " +
                "orderNo varchar, " +
                "customerName varchar, " +
                "customerPhone varchar, " +
                "realfee integer, " +
                "paytime varchar, " +
                "printState integer," +
                "totalfee integer," +
                "itemCount integer," +
                "note varchar," +
                "mealCode varchar" +
                ")";

        db.execSQL(orderInfoSql);

        // 建表 订单明细表
        String orderItemSql = "create table order_item" +
                "(id integer primary key autoincrement, " +
                "orderNo varchar, " +
                "productId integer, " +
                "productName varchar, " +
                "price integer, " +
                "quantity integer" +
                ")";

        db.execSQL(orderItemSql);


        String getorderlist = "create table orderList" +
                "(id integer primary key autoincrement, " +
                "orderNo varchar, " +
                "createTime varchar, " +
                "mealTakingNum varchar, " +
                "mealTime varchar," +
                "mealTimeEnd varchar," +
                "reserveType integer," +
                "mealTimeStart varchar," +
                "customerName varchar, " +
                "seatNumber varchar," +
                "printstatus integer,"+
                "status integer," +
                "shopId integer,"+
                "branchId integer,"+
                "mealCode varchar" +
                ")";
        db.execSQL(getorderlist);


        String getorderdetail="create table orderdetail" +
                "(id integer primary key autoincrement, " +
                "orderNo varchar," +
                "createTime varchar," +
                "targetDate varchar,"+
                "mealTime varchar,"+
                "status integer," +
                "totalFee integer," +
                "totalCount integer," +
                "mealTimeStart varchar,"+
                "mealTimeEnd varchar,"+
                "mealTakingNum varchar,"+
                "seatNumber varchar,"+
                "mealType varchar,"+
                "discount integer,"+
                "payType varchar,"+
                "note varchar,"+
                "updateTime varchar,"+
                "memberName varchar,"+
                "phone varchar,"+
                "printstatus integer"+
                ")";
        db.execSQL(getorderdetail);

        String mealitem = "create table mealitem" +
                "(id integer primary key autoincrement, " +
                "orderNo varchar, " +
                "name varchar,"+
                "count integer,"+
                "fee integer"+
                ")";

        db.execSQL(mealitem);
    }


    // 当数据库版本更新执行该方法
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        System.out.println("oldVersion : " + oldVersion);
    }


}
