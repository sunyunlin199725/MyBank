package com.nuist.mybank.Adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.nuist.mybank.POJO.ResultBean.GetTransferResult;
import com.nuist.mybank.R;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import androidx.annotation.Nullable;

public class TransferListAdapter extends BaseQuickAdapter<GetTransferResult.TransferBean, BaseViewHolder> {

    public TransferListAdapter(int layoutResId, @Nullable List<GetTransferResult.TransferBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GetTransferResult.TransferBean item) {
        Timestamp timestamp = new Timestamp(item.getTransfer_time());
        Date date = new Date(timestamp.getTime());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String day = format.format(date);
        helper.setText(R.id.mytran_time,day);
        helper.setText(R.id.mytran_toname,"转账 "+item.getCollection_name());
        helper.setText(R.id.mytran_money,"¥-"+item.getTransfer_money()+"元");
    }
}
