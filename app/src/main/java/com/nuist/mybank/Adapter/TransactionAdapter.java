package com.nuist.mybank.Adapter;

import android.content.Context;
import android.content.SharedPreferences;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.nuist.mybank.POJO.ResultBean.GetPayResult;
import com.nuist.mybank.R;
import com.nuist.mybank.Utils.CommonUtils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import androidx.annotation.Nullable;


public class TransactionAdapter extends BaseQuickAdapter<GetPayResult.PayBean, BaseViewHolder> {
    private SharedPreferences mSharedPreferences;//SharedPreferences保存用户信息
    public TransactionAdapter(int layoutResId, @Nullable List<GetPayResult.PayBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GetPayResult.PayBean item) {
        mSharedPreferences = mContext.getSharedPreferences("user_info", Context.MODE_PRIVATE);
        Timestamp timestamp = new Timestamp(item.getTrans_time());
        Date date = new Date(timestamp.getTime());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String day = format.format(date);
        helper.setText(R.id.pay_time,day);
        if(item.getPay_id() == mSharedPreferences.getInt("account_id",000)){//如果是支出
            helper.setImageResource(R.id.pay_header,R.mipmap.zhichu);
            helper.setText(R.id.pay_no,CommonUtils.hideCardNo(item.getPay_no()));
            helper.setText(R.id.pay_money,"-¥"+item.getTrans_money()+"元");
            helper.setTextColor(R.id.pay_money,mContext.getResources().getColor(R.color.red));
        }else if(item.getCollection_id() == mSharedPreferences.getInt("account_id",000)){//如果是收入
            helper.setImageResource(R.id.pay_header,R.mipmap.shouru);
            helper.setText(R.id.pay_no,CommonUtils.hideCardNo(item.getCollection_no()));
            helper.setText(R.id.pay_money,"+¥"+item.getTrans_money()+"元");
            helper.setTextColor(R.id.pay_money,mContext.getResources().getColor(R.color.textgreen));
        }else{
            //todo
        }
    }
}
