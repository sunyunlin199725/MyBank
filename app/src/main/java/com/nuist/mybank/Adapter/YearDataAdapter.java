package com.nuist.mybank.Adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.nuist.mybank.POJO.ResultBean.GetYearDataResult;
import com.nuist.mybank.R;

import java.util.List;

import androidx.annotation.Nullable;

public class YearDataAdapter extends BaseQuickAdapter<GetYearDataResult.YearMapBean, BaseViewHolder> {
    private boolean ispay;
    public YearDataAdapter(int layoutResId, @Nullable List<GetYearDataResult.YearMapBean> data,boolean ispay) {
        super(layoutResId, data);
        this.ispay = ispay;
    }

    @Override
    protected void convert(BaseViewHolder helper, GetYearDataResult.YearMapBean item) {
        if(ispay){
            helper.setText(R.id.monthdata_tv,item.getYears());
            helper.setImageResource(R.id.monthdata_pic,R.mipmap.zhi);
            helper.setText(R.id.monthdata_money,"-¥"+item.getMoney()+"元");
            helper.setText(R.id.monthdata_tip,"支出总额：");
            helper.setTextColor(R.id.monthdata_money,mContext.getResources().getColor(R.color.red));
        }else{
            helper.setText(R.id.monthdata_tv,item.getYears());
            helper.setImageResource(R.id.monthdata_pic,R.mipmap.shou);
            helper.setText(R.id.monthdata_money,"+¥"+item.getMoney()+"元");
            helper.setText(R.id.monthdata_tip,"收入总额：");
            helper.setTextColor(R.id.monthdata_money,mContext.getResources().getColor(R.color.textgreen));
        }
    }
}
