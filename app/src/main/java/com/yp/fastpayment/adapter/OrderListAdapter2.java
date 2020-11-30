package com.yp.fastpayment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.yp.fastpayment.R;
import com.yp.fastpayment.interfaces.OnOrderItemClickListenr;
import com.yp.fastpayment.model.OrderInfo;
import com.yp.fastpayment.model.orderlist_mode;
import com.yp.fastpayment.util.PriceUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class OrderListAdapter2 extends RecyclerView.Adapter<OrderListAdapter2.ViewHolder> {
    Context context;

    OnOrderItemClickListenr iSeleteShopListen;
    List<orderlist_mode> orderInfoList;

    public OrderListAdapter2(Context context, OnOrderItemClickListenr iSeleteShopListen) {
        this.context = context;
        this.iSeleteShopListen = iSeleteShopListen;
        orderInfoList=new ArrayList<>();
    }

    public void setOrderInfoList(List<orderlist_mode> orderList) {
        orderInfoList.clear();
        orderInfoList.addAll(orderList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_list, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        orderlist_mode orderInfo=orderInfoList.get(position);
        if (orderInfo.getSeatNumber()==null||orderInfo.getSeatNumber().equals("")){
            holder.item_tv_order_serial_num.setText("取餐号 " + orderInfo.getMealTakingNum());
            holder.item_tv_order_style.setText("自提");
        }else {
            holder.item_tv_order_serial_num.setText("座位号 " + orderInfo.getSeatNumber());
            holder.item_tv_order_style.setText("堂食");
        }
 //       holder.item_tv_order_serial_num.setText("取餐号 " + orderInfo.getSerial());
        holder.item_tv_order_num.setText("订单号： " + orderInfo.getOrderNo());
//        holder.item_tv_order_price.setText("¥" + PriceUtil.changeF2Y(orderInfo.getRealfee()));
//        holder.item_tv_order_date.setText(simpleDateFormat.format(orderInfo.getCreateTime()));
        holder.item_tv_order_date.setText(switchCreateTime(orderInfo.getCreateTime()));
        if (orderInfo.getPrintState()==1){
            holder.item_tv_order_print_state.setText("已打印");
            holder.item_tv_order_print_state.setTextColor(context.getResources().getColor(R.color.print_text));
            holder.item_tv_order_print_state.setBackgroundResource(R.drawable.printed_bg);
        }else {
            holder.item_tv_order_print_state.setText("未打印");
            holder.item_tv_order_print_state.setTextColor(context.getResources().getColor(R.color.white));
            holder.item_tv_order_print_state.setBackgroundResource(R.drawable.unprint_bg);
        }
    }

    @Override
    public int getItemCount() {
        return orderInfoList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView item_tv_order_serial_num;
        TextView item_tv_order_num;
        TextView item_tv_order_price;
        TextView item_tv_order_date;
        TextView item_tv_order_print_state;
        TextView item_tv_order_style;
        ConstraintLayout ll_order_layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            item_tv_order_serial_num = itemView.findViewById(R.id.item_tv_order_serial_num);
            item_tv_order_num = itemView.findViewById(R.id.item_tv_order_num);
            item_tv_order_price = itemView.findViewById(R.id.item_tv_order_price);
            item_tv_order_date = itemView.findViewById(R.id.item_tv_order_date);
            item_tv_order_print_state = itemView.findViewById(R.id.item_tv_order_print_state);
            ll_order_layout = itemView.findViewById(R.id.ll_order_layout);
            item_tv_order_style=itemView.findViewById(R.id.item_tv_order_style);
        }
    }

    /**
     * 时间格式转换
     */
    public static String switchCreateTime(String createTime) {
        String formatStr2 = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");//注意格式化的表达式
        try {
            Date time = format.parse(createTime );
            String date = time.toString();
            //将西方形式的日期字符串转换成java.util.Date对象
            SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", java.util.Locale.US);
            Date datetime = (Date) sdf.parse(date);
            //再转换成自己想要显示的格式
            formatStr2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(datetime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formatStr2;
    }
}
