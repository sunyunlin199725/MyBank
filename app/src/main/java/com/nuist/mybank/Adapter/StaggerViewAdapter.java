package com.nuist.mybank.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.nuist.mybank.POJO.StaggerItem;
import com.nuist.mybank.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class StaggerViewAdapter extends RecyclerView.Adapter<StaggerViewAdapter.InnerHolder>{
    private List<StaggerItem> mdata;//资源数据
    private OnItemClickListener mOnItemClickListener;//点击监听
    private Context mContext;

    public StaggerViewAdapter(List<StaggerItem> list){
        this.mdata = list;
    }
    @NonNull
    @Override
    public StaggerViewAdapter.InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = View.inflate(parent.getContext(), R.layout.item_stagger_view,null);
        return new InnerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
        holder.setData(mdata.get(position), position);
    }

    @Override
    public int getItemCount() {
        if (mdata != null) {
            return mdata.size();
        }
        return 0;
    }
     public void setOnItemClickListener(OnItemClickListener listener){
        this.mOnItemClickListener = listener;
     }

    /**
     * 编写回调的步骤
     * 1.创建这个接口
     * 2.定义接口内部的方法
     * 3.提供设置接口的方法
     * 4.接口方法的调用
     */
    public interface  OnItemClickListener{
        void OnItemClick(int position);
    }

    public class InnerHolder extends RecyclerView.ViewHolder{

        private ImageView pic_iv;
        private TextView title;
        private TextView content;
        private TextView date;
        private int mposition;
        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            pic_iv = itemView.findViewById(R.id.pic_iv);
            title = itemView.findViewById(R.id.title_tv);
            content = itemView.findViewById(R.id.content_tv);
            date = itemView.findViewById(R.id.date_tv);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.OnItemClick(mposition);
                }
            });
        }

        public void setData(StaggerItem staggerItem, int position) {
            title.setText(staggerItem.getTitle());
            date.setText(staggerItem.getTimestamp().toString());
            Picasso.with(mContext).load(staggerItem.getPic()).into(pic_iv);
            content.setText(staggerItem.getText());
            mposition = position;
        }
    }
}

