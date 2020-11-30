package com.yp.fastpayment.ui;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yp.fastpayment.R;
import com.yp.fastpayment.adapter.OrderListAdapter2;
import com.yp.fastpayment.adapter.OrderListDetailsAdapter;
import com.yp.fastpayment.adapter.orderListDetailsAdapter2;
import com.yp.fastpayment.api.MyCallback;
import com.yp.fastpayment.api.MyRetrofit;
import com.yp.fastpayment.api.request.orderdetail_rq;
import com.yp.fastpayment.api.request.orderstatus_rq;
import com.yp.fastpayment.api.response.MeshOrderItemVO;
import com.yp.fastpayment.api.response.OrderDetailRE;
import com.yp.fastpayment.api.response.OrderListRE;
import com.yp.fastpayment.api.response.meal_item;
import com.yp.fastpayment.api.response.orderstatusRE;
import com.yp.fastpayment.constant.Constants;
import com.yp.fastpayment.dao.OrderInfoDao;
import com.yp.fastpayment.dao.OrderListDao;
import com.yp.fastpayment.entity.GoodsInfo;
import com.yp.fastpayment.entity.mealinfo;
import com.yp.fastpayment.interfaces.OrderDetailRECallbacks;
import com.yp.fastpayment.model.OrderInfo;
import com.yp.fastpayment.model.orderdetail_model;
import com.yp.fastpayment.model.orderlist_mode;
import com.yp.fastpayment.util.BluetoothUtil;
import com.yp.fastpayment.util.ESCUtil;
import com.yp.fastpayment.util.GsonUtil;
import com.yp.fastpayment.util.PriceUtil;
import com.yp.fastpayment.util.QrcodeUtil;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.yp.fastpayment.util.ESCUtil.generateMockData2;

/**0
 * @author cp
 * @date 2019-10-11
 * description：订单详情
 */
public class OrderDetailsActivity extends BaseActivity {

    private static final String TAG = "OrderDetailsActivity";

    RecyclerView recyclerView;
    OrderListDetailsAdapter orderListDetailsAdapter;
    orderListDetailsAdapter2 orderListDetailsAdapter2;

    TextView tv_order_details_title;
    TextView tv_details_order_num;
    TextView tv_details_order_date;
    TextView tv_details_order_user_name;
    TextView tv_details_order_phone;
    TextView tv_details_total_num;
    TextView tv_details_note;
    TextView tv_details_subtotal_price;
    TextView tv_details_reduction_price;
    TextView tv_details_total_price;
    TextView tv_details_sure_date;
    TextView tv_pick_price;
    LinearLayout updatalin;
    LinearLayout tv_pick_linear;
    Button print;
    Button updata_st;
    List<meal_item> meal_items=new ArrayList<>();

    OrderInfoDao orderDao;
    OrderListDao orderListDao;
    orderlist_mode orderlist_mode;
    OrderDetailRE.orderDetail_data orderDetail_data;

    private AlertDialog.Builder builder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    protected int layoutId() {
        return R.layout.activity_order_details;
    }

    @Override
    protected void initData() {
        orderDao = new OrderInfoDao(this);
        orderListDao=new OrderListDao(this);
        final orderdetail_rq orderdetail_rq=new orderdetail_rq();
        orderdetail_rq.setOrderNo(Constants.orderNo);
        orderdetail_rq.setShopId(Constants.shopId.toString());
        Log.d("rqgetShopId======", orderdetail_rq.getShopId().toString());
        Log.d("getOrderNo======", orderdetail_rq.getOrderNo().toString());
        MyRetrofit.getApiService2().GetOrderDetail(orderdetail_rq).enqueue(new Callback<OrderDetailRE>() {
            @Override
            public void onResponse(Call<OrderDetailRE> call, Response<OrderDetailRE> response) {
                Log.d("meal_items======",response.body().getData().toString());
                OrderDetailRE.orderDetail_data data=response.body().getData();
                orderDetail_data=response.body().getData();
                if(data.getMealType()==1){
                    //标题
                    tv_order_details_title.setText("座位号:" + data.getSeatNumber());
                    tv_pick_linear.setVisibility(View.GONE);
                }else if(data.getMealType()==2){
                    tv_order_details_title.setText("取餐号:" + data.getMealTakingNum());
                    tv_pick_linear.setVisibility(View.VISIBLE);
                    tv_pick_price.setText("￥"+fenToYuan(data.getPackFee().toString()));
                }

                //订单号
                tv_details_order_num.setText(data.getOrderNo());

                tv_details_order_date.setText(OrderListAdapter2.switchCreateTime(data.getCreateTime()));

                tv_details_order_user_name.setText(data.getMemberName());

                tv_details_order_phone.setText(data.getPhone());
                tv_details_note.setText(data.getNote());

                tv_details_total_num.setText("x" + data.getTotalCount());

                tv_details_subtotal_price.setText("¥" + fenToYuan(String.valueOf(data.getTotalFee())));
                tv_details_reduction_price.setText("¥" + fenToYuan(String.valueOf(data.getDiscount())));
                tv_details_total_price.setText("¥" + fenToYuan(String.valueOf(data.getRealFee())));

                if(data.getItemList()!=null){
                    meal_items=data.getItemList();
                    orderListDetailsAdapter2.setList(meal_items);
                }
                orderlist_mode=orderListDao.queryOrderByOrderNo(Constants.orderNo);
                Log.d("orderlist_mode=====",orderlist_mode.toString());
                int printstate=orderlist_mode.getPrintState();
                if(printstate==0){
                    print.setText("打印");
                    print.setTag(0);
                }else {
                    print.setText("再次打印");
                    print.setTag(1);
                }
                if(orderlist_mode.getStatus()==10){
                    updata_st.setText("已核销");
                    updata_st.setEnabled(false);
                    updata_st.setBackgroundResource(R.color.updataed);
                    updatalin.setVisibility(View.VISIBLE);
                    tv_details_sure_date.setText(OrderListAdapter2.switchCreateTime(data.getUpdateTime()));
                }else {
                    updata_st.setText("核销");
                    updatalin.setVisibility(View.GONE);
                }
//                //标题
//                tv_order_details_title.setText("取餐号:" + Constants.curOrderInfo.getSerial());
//                //订单号
//                tv_details_order_num.setText(Constants.curOrderInfo.getOrderNo());
//
//                tv_details_order_date.setText(OrderInfoDao.simpleDateFormat.format(Constants.curOrderInfo.getPaytime()));
//
//                tv_details_order_user_name.setText(Constants.curOrderInfo.getCustomerName());
//
//                tv_details_order_phone.setText(Constants.curOrderInfo.getCustomerPhone());
//                tv_details_note.setText(Constants.curOrderInfo.getNote());
//
//                tv_details_total_num.setText("x" + Constants.curOrderInfo.getOrderItemList().size());
//
//                tv_details_subtotal_price.setText("¥" + PriceUtil.changeF2Y(Constants.curOrderInfo.getTotalfee()));
//                tv_details_reduction_price.setText("¥" + PriceUtil.changeF2Y((Constants.curOrderInfo.getTotalfee() - Constants.curOrderInfo.getRealfee())));
//                tv_details_total_price.setText("¥" + PriceUtil.changeF2Y(Constants.curOrderInfo.getRealfee()));

            }

            @Override
            public void onFailure(Call<OrderDetailRE> call, Throwable t) {
                Log.d(TAG, "OrderListRE onFailure==" + t.getMessage());
                Log.d(TAG, "OrderListRE onFailure==" + t.getCause());
                Log.d(TAG, "OrderListRE onFailure==" + call.toString());
                showToast("网络异常");
            }
        });


//        int printstate=Constants.curOrderInfo.getPrintState();
//        if(printstate==0){
//            print.setText("打印");
//        }else {
//            print.setText("再次打印");
//        }

//        List<GoodsInfo> list=new ArrayList<>();
//        for (MeshOrderItemVO meshOrderItemVO : Constants.curOrderInfo.getOrderItemList()) {
//
//            list.add(new GoodsInfo(meshOrderItemVO.getProductName(),meshOrderItemVO.getQuantity(),PriceUtil.changeF2Y(meshOrderItemVO.getPrice())));
//        }
//        orderListDetailsAdapter.setList(list);
//
    }

    @Override
    protected void initView() {
        tv_order_details_title = findViewById(R.id.tv_order_details_title);
        tv_details_order_num = findViewById(R.id.tv_details_order_num);
        tv_details_order_date = findViewById(R.id.tv_details_order_date);
        tv_details_order_user_name = findViewById(R.id.tv_details_order_user_name);
        tv_details_order_phone = findViewById(R.id.tv_details_order_phone);
        tv_details_total_num = findViewById(R.id.tv_details_total_num);
        tv_details_subtotal_price = findViewById(R.id.tv_details_subtotal_price);
        tv_details_reduction_price = findViewById(R.id.tv_details_reduction_price);
        tv_details_total_price = findViewById(R.id.tv_details_total_price);
        tv_details_note = findViewById(R.id.tv_details_note);
        tv_details_sure_date=findViewById(R.id.tv_details_sure_date);
        tv_pick_price=findViewById(R.id.tv_pick_price);
        updatalin=findViewById(R.id.updatalin);
        print=findViewById(R.id.print);
        updata_st=findViewById(R.id.updata_st);
        tv_pick_linear=findViewById(R.id.tv_pick_linear);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        orderListDetailsAdapter = new OrderListDetailsAdapter();
        orderListDetailsAdapter2= new orderListDetailsAdapter2();
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        //recyclerView.setAdapter(orderListDetailsAdapter);
        recyclerView.setAdapter(orderListDetailsAdapter2);
    }

    public void back(View view) {
        finish();
    }

    public void print(View view) {
        //doPrintOrder(Constants.curOrderInfo);
        if(print.getTag().equals(0)){
            doPrintOrder2(orderDetail_data);

            print.setTag(1);
        }else {
            builder=new AlertDialog.Builder(this);
            View v= getLayoutInflater().inflate(R.layout.item_dailog_print,null,false);
            builder.setView(v);
            TextView cancle=v.findViewById(R.id.cancel);
            TextView sure=v.findViewById(R.id.sure);
            final AlertDialog alert = builder.create();
            alert.show();
            sure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    doPrintOrder2(orderDetail_data);
                    alert.dismiss();
                    Toast.makeText(OrderDetailsActivity.this,"打印成功",Toast.LENGTH_LONG).show();
                }
            });
            cancle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alert.dismiss();
                }
            });

        }

    }

    public void updata(View view){
        final orderstatus_rq orderstatus_rq=new orderstatus_rq();
        orderstatus_rq.setOrderNo(Constants.orderNo);
        orderstatus_rq.setShopId(Constants.shopId);
        orderstatus_rq.setStatus(10);
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// HH:mm:ss
//获取当前时间
        final Date date = new Date(System.currentTimeMillis());
        MyRetrofit.getApiService2().UpdataStatus(orderstatus_rq).enqueue(new Callback<orderstatusRE>() {
            @Override
            public void onResponse(Call<orderstatusRE> call, Response<orderstatusRE> response) {
                if(response.body().getCode()==200){
                    showToast("核销成功");
                    updata_st.setText("已核销");
                    updatalin.setVisibility(View.VISIBLE);
                    tv_details_sure_date.setText(simpleDateFormat.format(date));
                    updata_st.setEnabled(false);
                    updata_st.setBackgroundResource(R.color.updataed);
                    orderListDao.updateState(10,Constants.orderNo);
                }else {
                    showToast(response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<orderstatusRE> call, Throwable t) {

            }
        });
    }

    public void doPrintOrder(OrderInfo orderInfo) {
        // 1: Get BluetoothAdapter
        BluetoothAdapter btAdapter = BluetoothUtil.getBTAdapter();
        if (btAdapter == null) {
            Toast.makeText(getBaseContext(), "Please Open Bluetooth!", Toast.LENGTH_LONG).show();
            return;
        }
        if (!btAdapter.isEnabled()) {
            btAdapter.enable();
            showToast("正在打开蓝牙");
        }
        // 2: Get Sunmi's InnerPrinter BluetoothDevice
        BluetoothDevice device = BluetoothUtil.getDevice(btAdapter);
        if (device == null) {
            Toast.makeText(getBaseContext(), "Please Make Sure Bluetooth have InnterPrinter!",
                    Toast.LENGTH_LONG).show();
            return;
        }

        // 3: Generate a order data
        byte[] data = new byte[0];
        try {
            byte[] code = TextUtils.isEmpty(orderInfo.getMealCode()) ? null : QrcodeUtil.draw2PxPoint(QrcodeUtil.generateBitmap(new JSONObject().put("mealCode", orderInfo.getMealCode()).toString(), 200, 200));
            data = ESCUtil.generateMockData(orderInfo, code);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 4: Using InnerPrinter print data
        BluetoothSocket socket = null;
        try {
            socket = BluetoothUtil.getSocket(device);
            BluetoothUtil.sendData(data, socket);
        } catch (IOException e) {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }

        orderInfo.setPrintState(1);

    }

    public void doPrintOrder2( OrderDetailRE.orderDetail_data orderlist_mode) {
        orderlist_mode om= orderListDao.queryOrderByOrderNo(orderlist_mode.getOrderNo());
        orderListDao.updatePrintState(1,orderlist_mode.getOrderNo());
        // 1: Get BluetoothAdapter
        BluetoothAdapter btAdapter = BluetoothUtil.getBTAdapter();
        if (btAdapter == null) {
            Toast.makeText(getBaseContext(), "Please Open Bluetooth!", Toast.LENGTH_LONG).show();
            return;
        }
        if (!btAdapter.isEnabled()) {
            btAdapter.enable();
            showToast("正在打开蓝牙");
        }
        // 2: Get Sunmi's InnerPrinter BluetoothDevice
        BluetoothDevice device = BluetoothUtil.getDevice(btAdapter);
        if (device == null) {
            Toast.makeText(getBaseContext(), "Please Make Sure Bluetooth have InnterPrinter!",
                    Toast.LENGTH_LONG).show();
            return;
        }

        // 3: Generate a order data
        byte[] data = new byte[0];
//        try {
//            byte[] code = TextUtils.isEmpty(orderlist_mode.getMealCode()) ? null : QrcodeUtil.draw2PxPoint(QrcodeUtil.generateBitmap(new JSONObject().put("mealCode", orderInfo.getMealCode()).toString(), 200, 200));
//            generateMockData2(orderlist_mode,this);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        // 4: Using InnerPrinter print data
        BluetoothSocket socket = null;
        try {
            socket = BluetoothUtil.getSocket(device);
            BluetoothUtil.sendData(ESCUtil.generateMockData2(orderlist_mode, this), socket);
        } catch (IOException e) {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        print.setText("再次打印");
        om.setPrintState(1);

    }
    public static String fenToYuan(String amount){
        NumberFormat format = NumberFormat.getInstance();
        try{
            Number number = format.parse(amount);
            double temp = number.doubleValue() / 100.0;
            format.setGroupingUsed(false);
            // 设置返回的小数部分所允许的最大位数
            format.setMaximumFractionDigits(2);
            amount = format.format(temp);
        } catch (ParseException e){
            e.printStackTrace();
        }
        return amount;
    }
}
