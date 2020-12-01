package com.yp.fastpayment.api;


import com.yp.fastpayment.api.request.InitRequest;
import com.yp.fastpayment.api.request.OrderListRequest;
import com.yp.fastpayment.api.request.ordercount_rq;
import com.yp.fastpayment.api.request.orderdetail_rq;
import com.yp.fastpayment.api.request.orderlist_rq;
import com.yp.fastpayment.api.request.orderstatus_rq;
import com.yp.fastpayment.api.response.InitResponse;
import com.yp.fastpayment.api.response.OrderDetailRE;
import com.yp.fastpayment.api.response.OrderListRE;
import com.yp.fastpayment.api.response.OrderListResponse;
import com.yp.fastpayment.api.response.mealHourRE;
import com.yp.fastpayment.api.response.ordercountRE;
import com.yp.fastpayment.api.response.orderstatusRE;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 */

public interface Api {

    @POST("zjypg/shangmishouchiInit")
    Call<InitResponse> shangmishouchiInit(@Body InitRequest entity);


    @POST("zjypg/shangmishouchiOrderList")
    Call<OrderListResponse> shangmishouchiOrderList(@Body OrderListRequest entity);



    @POST("zjypg/shangmishouchiGetNewOrder")
    Call<OrderListResponse> shangmishouchiGetNewOrder(@Body OrderListRequest entity);

    @POST("api/wxapp/tz/pad/order/list")
    Call<OrderListRE> GetOrderList(@Body orderlist_rq entity);

    @POST("api/wxapp/tz/pad/order/detail")
    Call<OrderDetailRE> GetOrderDetail(@Body orderdetail_rq entity);

    @POST("api/wxapp/tz/order/modifyMealStatus")
    Call<orderstatusRE> UpdataStatus(@Body orderstatus_rq entity);

    @POST("api/wxapp/tz/pad/order/list/count")
    Call<ordercountRE> getCount(@Body ordercount_rq entity);

    @POST("api/wxapp/mealHourConfig/list")
    Call<mealHourRE> getmealHour(@Body orderlist_rq entity);
}