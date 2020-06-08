package com.nuist.mybank.Adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nuist.mybank.POJO.ResultBean.GetCodesResult;
import com.nuist.mybank.R;

import java.sql.Timestamp;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CodeListAdapter extends RecyclerView.Adapter<CodeListAdapter.InnerHolder> {
    private List<GetCodesResult.GoodsCodeBean> mData;
    private OnItemClickListener mOnItemClickListener;

    public CodeListAdapter(List<GetCodesResult.GoodsCodeBean> mData){
         this.mData = mData;
    }
    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.item_codeslist_view,null);
        return new InnerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
         holder.setData(mData.get(position),position);
    }

    @Override
    public int getItemCount() {
        if (mData != null) {
            return mData.size();
        }
        return  0;
    }

    //定义监听方法
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
    /**
     * 编写回调的步骤
     * 1.创建这个接口
     * 2.定义接口内部的方法
     * 3.提供设置接口的方法
     * 4.接口方法的调用
     */
    public interface OnItemClickListener{
        void OnItemClick(int position);
    }
    public class InnerHolder extends RecyclerView.ViewHolder {
        private TextView goodsname;
        private TextView goodsmoney;
        private TextView goodstime;
        private TextView goodscode;
        private TextView goodsstatus;
        private int mCurrentPosition;

        public InnerHolder(View itemView){
            super(itemView);
            goodsname = itemView.findViewById(R.id.goodsname_tv);
            goodsmoney = itemView.findViewById(R.id.goodsmoney_tv);
            goodstime = itemView.findViewById(R.id.goodstime_tv);
            goodscode = itemView.findViewById(R.id.goodscode_tv);
            goodsstatus = itemView.findViewById(R.id.goods_status);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.OnItemClick(mCurrentPosition);
                    }
                }
            });
        }

        public void setData(GetCodesResult.GoodsCodeBean goodsCodeBean, int position) {
            goodsname.setText(goodsCodeBean.getGoods_name());
            goodsmoney.setText(""+goodsCodeBean.getGoods_money());
            Timestamp timestamp = new Timestamp(goodsCodeBean.getTrans_time());
            goodstime.setText(timestamp.toString());
            goodscode.setText(goodsCodeBean.getGoods_code());
            if(goodsCodeBean.getStatus().equals("0")){
                goodsstatus.setText("待取货");
            }else{
                goodsstatus.setText("已取货");
            }

            mCurrentPosition = position;
        }
    }
}
