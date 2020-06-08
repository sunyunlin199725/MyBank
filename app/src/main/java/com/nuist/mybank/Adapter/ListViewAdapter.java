package com.nuist.mybank.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.nuist.mybank.POJO.ResultBean.GetInfoResult;
import com.nuist.mybank.R;
import com.nuist.mybank.Utils.Config;
import com.squareup.picasso.Picasso;

import java.sql.Timestamp;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ListViewAdapter extends RecyclerView.Adapter<ListViewAdapter.InnerHolder> {

    private List<GetInfoResult.InfoBean> mdata;
    private OnItemClickListener mOnItemClickListener;
    private Context mContext;
    public ListViewAdapter(Context context,List<GetInfoResult.InfoBean> list) {
        this.mContext = context;
        this.mdata = list;
    }

    /**
     * 这个方法用于创建条目的view
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //传进去的这个view就是条目的界面
        //拿到view
        //创建InnerHolder
        View view = View.inflate(parent.getContext(), R.layout.item_list_view,null);
        return new InnerHolder(view);
    }
    /**
     * 绑定holder，设置数据
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
        holder.setData(mdata.get(position), position);
    }

    @Override
    public int getItemCount() {
        if (mdata != null) {
           return mdata.size();
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

        private  TextView infotitle;
        private  TextView infotime;
        private  TextView infotext;
        private ImageView infopic;
        private int mMposition;

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            //找到条目的控件
            infotitle = itemView.findViewById(R.id.info_title);
            infotime = itemView.findViewById(R.id.info_time);
            infotext = itemView.findViewById(R.id.info_text);
            infopic = itemView.findViewById(R.id.info_pic);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.OnItemClick(mMposition);
                    }
                }
            });

        }
        /**
         * 这个方法设置数据
         */
        public void setData(GetInfoResult.InfoBean infoBean, int position) {
            mMposition = position;
            infotitle.setText(infoBean.getInfo_title());
            Picasso.with(mContext).load(Config.baseurl +infoBean.getInfo_pic()).into(infopic);
            Timestamp timestamp = new Timestamp(infoBean.getInfo_time());
            infotime.setText(timestamp.toString());
            infotext.setText(infoBean.getInfo_text());
        }
    }
}
