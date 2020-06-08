package com.nuist.mybank.Adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.nuist.mybank.POJO.ResultBean.GetBankCardResult;
import com.nuist.mybank.R;

import java.util.List;

import androidx.annotation.Nullable;

public class CardListAdapter extends BaseQuickAdapter<GetBankCardResult.BankCardBean, BaseViewHolder> {

    public CardListAdapter(int layoutResId, @Nullable List<GetBankCardResult.BankCardBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GetBankCardResult.BankCardBean item) {
        String bank_name = item.getBank().getBank_name();
        String str = item.getBc_no();
        helper.setText(R.id.clitem_bankno,"["+str.substring(str.length()-4)+"]");
        helper.setText(R.id.clitem_bankname,bank_name);
        if(bank_name.equals("中国邮政储蓄银行")){
            helper.setImageResource(R.id.clitem_banklogo,R.drawable.ic_youzheng);
        }else if(bank_name.equals("中国农业银行")){
            helper.setImageResource(R.id.clitem_banklogo,R.drawable.ic_nongye);
        }else if(bank_name.equals("中国建设银行")){
            helper.setImageResource(R.id.clitem_banklogo,R.drawable.ic_jianshe);
        }else{
            //todo
            helper.setImageResource(R.id.clitem_banklogo,R.drawable.ic_weal);
        }
        //设置内部控件点击事件
        helper.addOnClickListener(R.id.clmoney_tv);
    }
}
