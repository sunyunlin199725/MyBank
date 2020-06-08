package com.nuist.mybank.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nuist.mybank.POJO.ResultBean.GetBankCardResult;
import com.nuist.mybank.POJO.SpinnerItem;
import com.nuist.mybank.R;

import org.w3c.dom.Comment;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class SpinnerAdapter extends BaseAdapter {
    private List<GetBankCardResult.BankCardBean> mData = new ArrayList<>();
    private LayoutInflater inflater;
    private String Tag = "SpinnerAdapter";

    public SpinnerAdapter(Context context,List<GetBankCardResult.BankCardBean> mList){
        this.mData = mList;
        this.inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SpinnerAdapter.ViewHoder viewHoder;
        //如果view未被实例化过，缓存池中没有对应的缓存
        if (convertView == null) {
            viewHoder = new SpinnerAdapter.ViewHoder();
            convertView = inflater.inflate(R.layout.item_spinner_view, null);

            //对viewHolder的属性进行赋值
            viewHoder.banklogo = (ImageView) convertView.findViewById(R.id.spitem_banklogo);
            viewHoder.bankname =(TextView) convertView.findViewById(R.id.spitem_bankname);
            viewHoder.bankno =(TextView) convertView.findViewById(R.id.spitem_bankno);

            //通过setTag将convertView与viewHolder关联
            convertView.setTag(viewHoder);
        } else {//如果缓存池中有对应的view缓存，则直接通过getTag取出viewHolder
            viewHoder = (SpinnerAdapter.ViewHoder) convertView.getTag();
        }
        // 取出bean对象
        GetBankCardResult.BankCardBean bean = mData.get(position);

        String bank_name = bean.getBank().getBank_name();
        String str = bean.getBc_no();
        viewHoder.bankname.setText(bank_name);
        viewHoder.bankno.setText("["+str.substring(str.length()-4)+"]");
        Log.e(Tag,str);
        if(bank_name.equals("中国邮政储蓄银行")){
            viewHoder.banklogo.setImageResource(R.drawable.ic_youzheng);
        }else if(bank_name.equals("中国农业银行")){
            viewHoder.banklogo.setImageResource(R.drawable.ic_nongye);
        }else if(bank_name.equals("中国建设银行")){
            viewHoder.banklogo.setImageResource(R.drawable.ic_jianshe);
        }else{
            viewHoder.banklogo.setImageResource(R.drawable.ic_nongshang);
        }
        return convertView;
    }
    class ViewHoder{
        public ImageView banklogo;
        public TextView bankname;
        public TextView bankno;
    }
}
