package com.yp.fastpayment.ui;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.yanzhenjie.recyclerview.OnItemClickListener;
import com.yanzhenjie.recyclerview.OnItemMenuClickListener;
import com.yanzhenjie.recyclerview.SwipeMenu;
import com.yanzhenjie.recyclerview.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.SwipeMenuItem;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;
import com.yp.fastpayment.BuildConfig;
import com.yp.fastpayment.R;
import com.yp.fastpayment.adapter.OrderListAdapter;
import com.yp.fastpayment.adapter.OrderListAdapter2;
import com.yp.fastpayment.api.MyRetrofit;
import com.yp.fastpayment.api.request.OrderListRequest;
import com.yp.fastpayment.api.request.orderdetail_rq;
import com.yp.fastpayment.api.request.orderlist_rq;
import com.yp.fastpayment.api.response.OrderDetailRE;
import com.yp.fastpayment.constant.Constants;
import com.yp.fastpayment.dao.OrderInfoDao;
import com.yp.fastpayment.dao.OrderListDao;
import com.yp.fastpayment.model.OrderInfo;
import com.yp.fastpayment.interfaces.OnOrderItemClickListenr;
import com.yp.fastpayment.model.orderdetail_model;
import com.yp.fastpayment.model.orderlist_mode;
import com.yp.fastpayment.thread.MsgSynchTask;
import com.yp.fastpayment.util.BluetoothUtil;
import com.yp.fastpayment.util.ESCUtil;
import com.yp.fastpayment.util.GsonUtil;
import com.yp.fastpayment.util.QrcodeUtil;
import com.yp.fastpayment.util.SharedPreferenceUtil;

import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import me.jessyan.autosize.utils.AutoSizeUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author jay
 * @date 2019-10-10
 * description：
 */
public class OrderListActivity extends BaseActivity implements View.OnClickListener, OnOrderItemClickListenr, OnItemClickListener {
    TextView tv_merchant_name;
    TextView tv_shop_name;
    TextView version;
    SwipeRecyclerView swipe_recycler_view;
    LinearLayout ll_shop_name;
    OrderListAdapter orderListAdapter;
    OrderListAdapter2 orderListAdapter2;
    Spinner spinner_style;
    int pos_style=0;
    ArrayList<String> mstyle=new ArrayList<>(Arrays.asList("全部", "堂食", "自提"));
    ArrayAdapter<String> spinneradatper;
    private static final String TAG = "OrderListActivity";
    List<OrderInfo> orderInfoList = new ArrayList<>();
    List<orderlist_mode> orderlist_modes=new ArrayList<>();
    OrderInfoDao orderDao;
    OrderListDao orderListDao;

    private AlertDialog.Builder builder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                playMusic(1);
                updateOrderInfo();
                return;
            }
        }
    };

    @Override
    protected int layoutId() {
        return R.layout.activity_order_list;
    }

    ScheduledExecutorService scheduledExecutorServiceWithMsg;

    @Override
    protected void initData() {
        orderDao = new OrderInfoDao(this);
        orderListDao=new OrderListDao(this);

        scheduledExecutorServiceWithMsg = Executors.newScheduledThreadPool(10);
        scheduledExecutorServiceWithMsg.scheduleWithFixedDelay(new MsgSynchTask(this, handler),
                30, 10, TimeUnit.SECONDS);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        scheduledExecutorServiceWithMsg.shutdown();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        updateOrderInfo();
    }

    public void updateOrderInfo() {
        Log.d(TAG, "orderDao.queryOrderList, shopId==" + Constants.shopId + ", branchId==" + Constants.branchId);
//        orderInfoList = orderDao.query(Constants.shopId, Constants.branchId);
        if(pos_style==0){
            orderlist_modes=orderListDao.query(Constants.shopId, Constants.branchId);
        }else {
            orderlist_modes=orderListDao.querybystyle(pos_style,Constants.shopId, Constants.branchId);
        }
        Log.d(" orderlist_modes====", orderlist_modes.toString());

        Log.d(TAG, "orderInfoList==" + GsonUtil.GsonString(orderInfoList));
        //orderListAdapter.setOrderInfoList(orderInfoList);
        orderListAdapter2.setOrderInfoList(orderlist_modes);
    }

    @Override
    protected void initView() {
        initPop();
        ll_shop_name = findViewById(R.id.ll_shop_name);
        tv_merchant_name = findViewById(R.id.tv_merchant_name);
        tv_shop_name = findViewById(R.id.tv_shop_name);
        version=findViewById(R.id.versionname);
        ll_shop_name.setOnClickListener(this);
        swipe_recycler_view = findViewById(R.id.swipe_recycler_view);
        swipe_recycler_view.setLayoutManager(new LinearLayoutManager(mContext));
        orderListAdapter = new OrderListAdapter(this, this);
        orderListAdapter2 =new OrderListAdapter2(this,this);
        swipe_recycler_view.setOnItemMenuClickListener(mItemMenuClickListener); // Item的Menu点击。
//        swipe_recycler_view.setOnItemMenuClickListener(mItemMenuClickListener); // Item的Menu点击。
        swipe_recycler_view.setSwipeMenuCreator(mSwipeMenuCreator); // 菜单创建器。
        swipe_recycler_view.setOnItemClickListener(this);
        //swipe_recycler_view.setAdapter(orderListAdapter);
        swipe_recycler_view.setAdapter(orderListAdapter2);
        spinner_style=findViewById(R.id.order_style);
        spinneradatper=new ArrayAdapter<String>(this,R.layout.item_order_spinner,mstyle);
        spinneradatper.setDropDownViewResource(R.layout.item_order_spinner_drop);
        spinner_style.setAdapter(spinneradatper);
        tv_merchant_name.setText(Constants.shopName);
        tv_shop_name.setText(Constants.branchName);
        version.setText("v "+BuildConfig.VERSION_NAME);
        Log.d(TAG, "orderInfoList============================" + GsonUtil.GsonString(orderInfoList));

        spinner_style.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:pos_style=0;
                        break;
                    case 1:pos_style=1;
                        break;
                    case 2:pos_style=2;
                        break;
                }
                updateOrderInfo();
//                List<orderlist_mode> mm;
//                if(i==0){
//                    mm=orderListDao.query(Constants.shopId, Constants.branchId);
//                }else {
//                    mm=orderListDao.querybystyle(i,Constants.shopId, Constants.branchId);
//                }
//                if(mm.size()>0){
//                    updateOrderInfo();
//                    orderListAdapter2.setOrderInfoList(mm);
//                }else {
//                    showToast("当前用餐方式无订单");
//                }

               Log.d("点击的是",pos_style+"");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }



    /**
     * 菜单创建器。
     */
    private SwipeMenuCreator mSwipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int position) {
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            int width = AutoSizeUtils.dp2px(mContext, 106);

            SwipeMenuItem printItem = new SwipeMenuItem(mContext).setBackgroundColor(
                    getResources().getColor(R.color.print_bg))
                    .setText("打印")
                    .setTextColor(Color.WHITE)
                    .setTextSize(AutoSizeUtils.sp2px(mContext, 12))
                    .setWidth(width)
                    .setHeight(height);

            SwipeMenuItem printAgainItem = new SwipeMenuItem(mContext).setBackgroundColor(
                    getResources().getColor(R.color.print_again_bg))
                    .setText("再次打印")
                    .setTextColor(Color.WHITE)
                    .setTextSize(AutoSizeUtils.sp2px(mContext, 11))
                    .setWidth(width)
                    .setHeight(height);

            //根据打印状态显示
//            if (orderInfoList.get(position).getPrintState() == 1) {
//                swipeRightMenu.addMenuItem(printAgainItem);
//            } else {
//                swipeRightMenu.addMenuItem(printItem);
//            }

            if (orderlist_modes.get(position).getPrintState() == 1) {
                swipeRightMenu.addMenuItem(printAgainItem);
            } else {
                swipeRightMenu.addMenuItem(printItem);
            }

            SwipeMenuItem detailsItem = new SwipeMenuItem(mContext).setBackgroundColor(
                    getResources().getColor(R.color.details_bg))
                    .setText("查看详情")
                    .setTextColor(Color.WHITE)
                    .setTextSize(AutoSizeUtils.sp2px(mContext, 11))
                    .setWidth(width)
                    .setHeight(height);
            swipeRightMenu.addMenuItem(detailsItem);// 添加一个按钮到右侧侧菜单。

        }
    };

    /**
     * RecyclerView的Item的Menu点击监听。
     */
    private OnItemMenuClickListener mItemMenuClickListener = new OnItemMenuClickListener() {
        @Override
        public void onItemClick(SwipeMenuBridge menuBridge, int position) {
            menuBridge.closeMenu();
            final int pos=position;
            int direction = menuBridge.getDirection(); // 左侧还是右侧菜单。
            int menuPosition = menuBridge.getPosition(); // 菜单在RecyclerView的Item中的Position。
            if (direction == SwipeRecyclerView.RIGHT_DIRECTION) {
                if (menuPosition == 0) {
                    int flag=orderListDao.queryOrderByOrderNo(orderlist_modes.get(position).getOrderNo()).getPrintState();
                    printOrder(position);
//                    if(flag==0){
//                        printOrder(position);
//                    }else {
//                        builder=new AlertDialog.Builder(OrderListActivity.this);
//                        View v= getLayoutInflater().inflate(R.layout.item_dailog_print,null,false);
//                        builder.setView(v);
//                        TextView cancle=v.findViewById(R.id.cancel);
//                        TextView sure=v.findViewById(R.id.sure);
//                        final AlertDialog alert = builder.create();
//                        alert.show();
//                        sure.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                printOrder(pos);
//                                alert.dismiss();
//                                Toast.makeText(OrderListActivity.this,"打印成功",Toast.LENGTH_LONG).show();
//                            }
//                        });
//                        cancle.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                alert.dismiss();
//                            }
//                        });
//                    }
                } else if (menuPosition == 1) {
                    openDetails(position);
                }
            }
        }
    };

    /**
     * 打印第几个订单
     *
     * @param pos
     */
    private void printOrder(int pos) {
//        showToast("打印list第" + pos + "项");
        //doPrintOrder(orderInfoList.get(pos));
        final orderdetail_rq orderlist_rq=new orderdetail_rq();
        orderlist_rq.setShopId(Constants.shopId.toString());
        orderlist_rq.setOrderNo(orderlist_modes.get(pos).getOrderNo());
        MyRetrofit.getApiService2().GetOrderDetail(orderlist_rq).enqueue(new Callback<OrderDetailRE>() {
            @Override
            public void onResponse(Call<OrderDetailRE> call, Response<OrderDetailRE> response) {
                OrderDetailRE.orderDetail_data data =response.body().getData();
                doPrintOrder2(data);
            }

            @Override
            public void onFailure(Call<OrderDetailRE> call, Throwable t) {
                Log.d(TAG, "GetOrderDetail onFailure==" + t.getMessage());
                Log.d(TAG, "GetOrderDetail onFailure==" + t.getCause());
                Log.d(TAG, "GetOrderDetail onFailure==" + call.toString());
                showToast("网络异常");
            }
        });

    }

    private void openDetails(int pos) {

        Intent intent = new Intent(mContext, OrderDetailsActivity.class);
        //Constants.curOrderInfo = orderInfoList.get(pos);
        Constants.orderNo=orderlist_modes.get(pos).getOrderNo();
        Log.d("Constants.orderNo======",Constants.orderNo);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_shop_name:
                popupWindow.showAsDropDown(ll_shop_name, 20, 20);
                bgAlpha(0.5f);
                break;
            case R.id.tv_change_user:
                popupWindow.dismiss();
                startActivity(new Intent(mContext, SelectShopActivity.class));
                finish();
                break;
            case R.id.tv_login_out:
                popupWindow.dismiss();
                SharedPreferenceUtil.getInstance(OrderListActivity.this).putString("user","");
                SharedPreferenceUtil.getInstance(OrderListActivity.this).putString("pwd","");
                startActivity(new Intent(mContext, LoginActivity.class));
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemCallback(int pos) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        bgAlpha(1);
    }

    @Override
    public void onItemClick(View view, int adapterPosition) {
        openDetails(adapterPosition);
    }

    PopupWindow popupWindow;

    void initPop() {
        View view = LayoutInflater.from(this).inflate(R.layout.pop_layout, null);
        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
//        popupWindow.setContentView(LayoutInflater.from(this).inflate(R.layout.pop_layout, null));
        popupWindow.setOutsideTouchable(true);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                //popupwindow消失时使背景不透明
                bgAlpha(1f);
            }
        });

        view.findViewById(R.id.tv_change_user).setOnClickListener(this);
        view.findViewById(R.id.tv_login_out).setOnClickListener(this);
    }

    private void bgAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getWindow().setAttributes(lp);
    }

    @Override
    public void onBackPressed() {
        if (popupWindow.isShowing()) {
            popupWindow.dismiss();
        } else {
            super.onBackPressed();
        }
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
        //插入二维码操作
        // "mealCode": 1234,
        //1234
        //{"mealCode": 1234}
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

        orderDao.updatePrintState(1, orderInfo.getOrderNo());

        orderListAdapter.setOrderInfoList(orderInfoList);
    }

    public void doPrintOrder2(OrderDetailRE.orderDetail_data da) {

        List<orderlist_mode> mo=new ArrayList<>();
        if(pos_style==0){
            mo=orderListDao.query(Constants.shopId, Constants.branchId);
        }else {
            mo=orderListDao.querybystyle(pos_style,Constants.shopId, Constants.branchId);
        }
        for(orderlist_mode i:mo){
            if(i.getOrderNo().equals(da.getOrderNo())){
                i.setPrintState(1);
            }
        }
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
        //插入二维码操作
        // "mealCode": 1234,
        //1234
        //{"mealCode": 1234}
        byte[] data = new byte[0];
//        try {
//            byte[] code = TextUtils.isEmpty(orderInfo.getMealCode()) ? null : QrcodeUtil.draw2PxPoint(QrcodeUtil.generateBitmap(new JSONObject().put("mealCode", orderInfo.getMealCode()).toString(), 200, 200));
//            data = ESCUtil.generateMockData(orderInfo, code);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        // 4: Using InnerPrinter print data
        BluetoothSocket socket = null;
        try {
            socket = BluetoothUtil.getSocket(device);

            BluetoothUtil.sendData(ESCUtil.generateMockData2(da, this), socket);
        } catch (IOException e) {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }



        orderListDao.updatePrintState(1,da.getOrderNo());
        orderListAdapter2.setOrderInfoList(mo);
    }


    MediaPlayer mediaPlayer;

    void playMusic(int type) {
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                }
            });
        }
        mediaPlayer.reset();
        if (type == 1) {
            try {
                mediaPlayer.setDataSource(this, Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.neworder));
                mediaPlayer.prepareAsync();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
