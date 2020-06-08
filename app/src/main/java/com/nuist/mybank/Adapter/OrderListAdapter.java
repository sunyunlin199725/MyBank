package com.nuist.mybank.Adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.nuist.mybank.POJO.ResultBean.GetOrderResult;
import com.nuist.mybank.R;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import androidx.annotation.Nullable;

public class OrderListAdapter extends BaseQuickAdapter<GetOrderResult.OrderBean, BaseViewHolder> {


    public OrderListAdapter(int layoutResId, @Nullable List<GetOrderResult.OrderBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GetOrderResult.OrderBean item){
        Timestamp timestamp = new Timestamp(item.getOrder_time());
        Date date = new Date(timestamp.getTime());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String day = format.format(date);
        String orderstatus;
        if(item.getOrder_status().equals("0")){
            orderstatus = "交易成功";
        }else{
            orderstatus = "交易失败";
        }
        helper.setText(R.id.myorder_business_name,item.getBusiness_name())
                .setText(R.id.myorder_order_time,day)
                .setText(R.id.myorder_order_money,item.getOrder_money()+"元")
                .setText(R.id.myorder_order_status,orderstatus);
    }
}
