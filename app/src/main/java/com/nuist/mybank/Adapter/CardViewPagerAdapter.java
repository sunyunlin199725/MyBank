package com.nuist.mybank.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.nuist.mybank.POJO.BankCardView;
import com.nuist.mybank.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.PagerAdapter;

public class CardViewPagerAdapter extends PagerAdapter {

    private List<BankCardView> mData = new ArrayList<>();
    private CardViewPagerAdapter.OnItemClickListener mOnItemClickListener;

    /**
     * 构造器传入数据参数
     * @param mData
     */
    public CardViewPagerAdapter(List<BankCardView> mData){
         this.mData = mData;
    }
    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        View item = LayoutInflater.from(container.getContext()).inflate(R.layout.item_card_view,container,false);
        CardView cardView = item.findViewById(R.id.bankcard);
        ImageView banklogo = item.findViewById(R.id.item_banklogo);
        TextView bankname = item.findViewById(R.id.item_bankname);
        TextView banktype = item.findViewById(R.id.item_banktype);
        TextView bankno = item.findViewById(R.id.item_bankno);

        String str = mData.get(position).getBc_no();
        bankno.setText("**** **** ****" + str.substring(str.length()-4));
        if(mData.get(position).getBc_type() == '1'){
            banktype.setText("储蓄卡");
        }else{
            banktype.setText("信用卡");
        }
        String bank_name = mData.get(position).getBank_name();
        bankname.setText(bank_name);
        if(bank_name.equals("中国邮政储蓄银行")){
            cardView.setCardBackgroundColor(container.getResources().getColor(R.color.coloryouzhengcard));
            banklogo.setImageResource(R.drawable.ic_youzheng);
        }else if(bank_name.equals("中国农业银行")){
            cardView.setCardBackgroundColor(container.getResources().getColor(R.color.colornongyecard));
            banklogo.setImageResource(R.drawable.ic_nongye);
        }else if(bank_name.equals("中国建设银行")){
            cardView.setCardBackgroundColor(container.getResources().getColor(R.color.colorjianshecard));
            banklogo.setImageResource(R.drawable.ic_jianshe);
        }else{
            cardView.setCardBackgroundColor(container.getResources().getColor(R.color.textgrey));
            banklogo.setImageResource(R.drawable.ic_nongshang);
        }
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.OnItemClick(position);
            }
        });

        container.addView(item);
        return item;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
    //定义监听方法
    public void setOnItemClickListener(CardViewPagerAdapter.OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public interface OnItemClickListener{
        void OnItemClick(int position);
    }
}
