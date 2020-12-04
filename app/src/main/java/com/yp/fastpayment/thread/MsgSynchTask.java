package com.yp.fastpayment.thread;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.yp.fastpayment.api.MyCallback;
import com.yp.fastpayment.api.MyRetrofit;
import com.yp.fastpayment.api.request.OrderListRequest;
import com.yp.fastpayment.api.request.ordercount_rq;
import com.yp.fastpayment.api.request.orderlist_rq;
import com.yp.fastpayment.api.response.OrderListRE;
import com.yp.fastpayment.api.response.OrderListResponse;
import com.yp.fastpayment.api.response.OrderVO;
import com.yp.fastpayment.api.response.ordercountRE;
import com.yp.fastpayment.constant.Constants;
import com.yp.fastpayment.dao.OrderInfoDao;
import com.yp.fastpayment.dao.OrderListDao;
import com.yp.fastpayment.model.OrderInfo;
import com.yp.fastpayment.model.orderlist_mode;
import com.yp.fastpayment.ui.LoginActivity;
import com.yp.fastpayment.ui.OrderListActivity;
import com.yp.fastpayment.util.GsonUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MsgSynchTask implements Runnable {


    private static final String TAG = "MsgSynchTask";
    Handler handler;

    OrderInfoDao orderDao;
    OrderListRequest request;

    OrderListDao orderListDao;
    List<orderlist_mode> orderlist_modes=new ArrayList<>();
    ordercount_rq ordercount_rq;
    orderlist_rq orderlist_rq;
    List<String> cancelList=new ArrayList<>();

    int count;


    
    public MsgSynchTask(Context context, Handler handler) {
        this.context = context;
        orderDao = new OrderInfoDao(context);
        orderListDao=new OrderListDao(context);
        orderlist_modes=orderListDao.query(Constants.shopId,Constants.branchId);
        count=orderlist_modes.size();
        this.handler = handler;

        request = new OrderListRequest();
        request.setBranchId(Constants.branchId);
        request.setDeviceId(LoginActivity.deviceId);
        request.setShopId(Constants.shopId);
        request.setPage(1);
        request.setEmployeeId(Constants.employeeId);

        ordercount_rq=new ordercount_rq();
        ordercount_rq.setShopId(Constants.shopId);
        ordercount_rq.setBranchId(Constants.branchId);

        orderlist_rq=new orderlist_rq();
        orderlist_rq.setShopId(Constants.shopId.toString());
        orderlist_rq.setBranchId(Constants.branchId.toString());
        Log.d(TAG, "shangmishouchiOrderList==" + GsonUtil.GsonString(request));

        Log.d(TAG, "new MsgSynchTask====");
    }

    Context context;


    @Override
    public void run() {
        /**
         * 根据长度增加订单列表数据库
         */
        MyRetrofit.getApiService2().getCount(ordercount_rq).enqueue(new Callback<ordercountRE>() {
            @Override
            public void onResponse(Call<ordercountRE> call, Response<ordercountRE> response) {
                Log.d("getcount==","获取订单数量==="+response.body().getData());
                if((response.body().getData().getLen()-response.body().getData().getCancelList().size())>count){
                    Log.d("长度大小=====","刷新");

                    MyRetrofit.getApiService2().GetOrderList(orderlist_rq).enqueue(new Callback<OrderListRE>() {
                        @Override
                        public void onResponse(Call<OrderListRE> call, Response<OrderListRE> response) {
                            List<OrderListRE.orderlist_data> data=response.body().getData();
                            boolean flag=false;
                            for (OrderListRE.orderlist_data orderVO : data) {
                                if(orderVO.getStatus()!=11){
                                    Log.d(TAG, "orderVO==" + GsonUtil.GsonString(orderVO));
                                    orderlist_mode temp = orderListDao.queryOrderByOrderNo(orderVO.getOrderNo());
                                    if (temp == null) {
                                        flag = true;
                                        orderListDao.insertData(orderVO, Constants.shopId, Constants.branchId);
                                    }
                                }
                            }
                            count=orderListDao.query(Constants.shopId,Constants.branchId).size();
                            if (flag) {
                                handler.sendEmptyMessage(1);
                            }

                        }

                        @Override
                        public void onFailure(Call<OrderListRE> call, Throwable t) {
                            Log.d(TAG, "GetOrderList onFailure==" + t.getMessage());
                            Log.d(TAG, "GetOrderList onFailure==" + t.getCause());
                            Log.d(TAG, "GetOrderList onFailure==" + call.toString());
                        }
                    });
//
                }
            }

            @Override
            public void onFailure(Call<ordercountRE> call, Throwable t) {
                Log.d(TAG, "getCount onFailure==" + t.getMessage());
                Log.d(TAG, "getCount onFailure==" + t.getCause());
                Log.d(TAG, "getCount onFailure==" + call.toString());
            }
        });
/**
 * 访问被取消的订单是否有增加
 */
        MyRetrofit.getApiService2().getCount(ordercount_rq).enqueue(new Callback<ordercountRE>() {
            @Override
            public void onResponse(Call<ordercountRE> call, Response<ordercountRE> response) {
                boolean flag=false;
                if (response.body().getData().getCancelList().size()>0){
                    if (cancelList.size()<response.body().getData().getCancelList().size()){
                        List<String> data=response.body().getData().getCancelList();
                        if(cancelList.size()==0){cancelList=data;}
                        else {
                            for (int i=0;i<data.size();i++){
                                for (int j=0;j<cancelList.size();j++){
                                    if(!cancelList.get(j).equals(data.get(i))){
                                        cancelList.add(data.get(i));
                                    }
                                }
                            }
                        }
                    }
                    for (int i=0;i<cancelList.size();i++){
                        orderListDao.clearList(cancelList.get(i),Constants.shopId,Constants.branchId);
                    }
                    flag=true;
                }
                if (flag) {
                    handler.sendEmptyMessage(2);
                }
            }

            @Override
            public void onFailure(Call<ordercountRE> call, Throwable t) {
                Log.d(TAG, "GetOrderList onFailure==" + t.getMessage());
                Log.d(TAG, "GetOrderList onFailure==" + t.getCause());
                Log.d(TAG, "GetOrderList onFailure==" + call.toString());
            }
        });

    }
}
