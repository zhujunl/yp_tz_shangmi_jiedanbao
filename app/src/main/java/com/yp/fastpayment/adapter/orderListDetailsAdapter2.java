package com.yp.fastpayment.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yp.fastpayment.R;
import com.yp.fastpayment.api.response.meal_item;
import com.yp.fastpayment.entity.GoodsInfo;
import com.yp.fastpayment.ui.OrderDetailsActivity;
import com.yp.fastpayment.ui.OrderListActivity;

import java.util.ArrayList;
import java.util.List;

public class orderListDetailsAdapter2  extends RecyclerView.Adapter<orderListDetailsAdapter2.ViewHolder> {
    List<meal_item> goodsInfos;

    public orderListDetailsAdapter2() {
        goodsInfos = new ArrayList<>();
    }

    public void setList(List<meal_item> list) {
        goodsInfos.clear();
        goodsInfos.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public orderListDetailsAdapter2.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_details, parent, false);
        return new orderListDetailsAdapter2.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull orderListDetailsAdapter2.ViewHolder holder, int position) {
        meal_item goodsInfo=goodsInfos.get(position);
        holder.item_tv_details_name.setText(goodsInfo.getName());
        holder.item_tv_details_num.setText("x"+goodsInfo.getCount());
        holder.item_tv_details_price.setText("¥"+ OrderDetailsActivity.fenToYuan(String.valueOf(goodsInfo.getFee())));
    }

    @Override
    public int getItemCount() {
        return goodsInfos.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView item_tv_details_name;
        TextView item_tv_details_num;
        TextView item_tv_details_price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            item_tv_details_name = itemView.findViewById(R.id.item_tv_details_name);
            item_tv_details_num = itemView.findViewById(R.id.item_tv_details_num);
            item_tv_details_price = itemView.findViewById(R.id.item_tv_details_price);
        }
    }
}
