package com.nuist.mybank.Fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.nuist.mybank.Adapter.TransactionAdapter;
import com.nuist.mybank.Adapter.TransferListAdapter;
import com.nuist.mybank.Interface.Service;
import com.nuist.mybank.JumpActivity.OrderDetailActivity;
import com.nuist.mybank.JumpActivity.QueryTransaActivity;
import com.nuist.mybank.JumpActivity.TransferDetailActivity;
import com.nuist.mybank.POJO.ResultBean.GetPayResult;
import com.nuist.mybank.POJO.ResultBean.GetTransferResult;
import com.nuist.mybank.R;
import com.nuist.mybank.Utils.RetrofitManager;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewPagerFragment1 extends Fragment {


    @BindView(R.id.oneday_tv)
    TextView onedayTv;
    @BindView(R.id.oneweek_tv)
    TextView oneweekTv;
    @BindView(R.id.onemonth_tv)
    TextView onemonthTv;
    @BindView(R.id.more_tv)
    TextView moreTv;
    @BindView(R.id.transaction_recyclerview)
    RecyclerView mRecyclerView;
    private Retrofit mRetrofit = RetrofitManager.getRetrofit();//获取Retrofit
    private Service mService = mRetrofit.create(Service.class);//获取service
    private SharedPreferences mSharedPreferences;//SharedPreferences保存用户信息
    private List<GetPayResult.PayBean> mList;//获取的订单数据
    private TransactionAdapter mTransactionAdapter;
    private String Tag = "ViewPagerFragment1";
    public ViewPagerFragment1() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_pager_fragment1, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ButterKnife.bind(this,getActivity());
        mSharedPreferences = getActivity().getSharedPreferences("user_info", Context.MODE_PRIVATE);
    }
    private void showList(boolean isVertical,boolean isReverse,List<GetPayResult.PayBean> mData) {
        //设置Recyclerview设置布局管理器
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        //设置水平还是垂直
        manager.setOrientation(isVertical ? LinearLayoutManager.VERTICAL : LinearLayoutManager.HORIZONTAL);
        //设置正向还是反向
        manager.setReverseLayout(isReverse);

        mRecyclerView.setLayoutManager(manager);
        //设置adapter
        mTransactionAdapter = new TransactionAdapter(R.layout.item_paylist_view,mData);
        //更换动画效果
        mTransactionAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        //设置Recyclerview
        mRecyclerView.setAdapter(mTransactionAdapter);
        //设置监听
        initListViewListener();
    }

    private void initListViewListener() {
        mTransactionAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                String str = mList.get(position).getTrans_id();
                String type = str.substring(0,2);//交易类型
                Log.e(Tag,type);
                if(type.equals("01")){//转账类型
                    Intent transintent = new Intent(getActivity(), TransferDetailActivity.class);
                    transintent.putExtra("transfer_id",mList.get(position).getTrans_id());
                    startActivity(transintent);
                }else if(type.equals("02")){//订单类型
                    Intent orderintent = new Intent(getActivity(), OrderDetailActivity.class);
                    orderintent.putExtra("order_id",mList.get(position).getTrans_id());
                    startActivity(orderintent);
                }else{
                    Toast.makeText(getActivity(),"未知类型"+position,Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void getOneDayPay(int account_id){
        mService.getOneDayPay(account_id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<GetPayResult>() {
                    @Override
                    public void accept(GetPayResult getPayResult) throws Exception {
                        if(getPayResult.isSuccess() == true){
                            mList = getPayResult.getData();
                            showList(true,false,mList);
                        }else{
                            Toast.makeText(getActivity(),"您还没有收支信息！",Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(Tag, "错误信息 -->"+throwable.getMessage());
                    }
                });
    }
    public void getOneWeekPay(int account_id){
        mService.getOneWeekPay(account_id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<GetPayResult>() {
                    @Override
                    public void accept(GetPayResult getPayResult) throws Exception {
                        if(getPayResult.isSuccess() == true){
                            mList = getPayResult.getData();
                            showList(true,false,mList);
                        }else{
                            Toast.makeText(getActivity(),"您还没有收支信息！",Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(Tag, "错误信息 -->"+throwable.getMessage());
                    }
                });
    }
    public void getOneMonthPay(int account_id){
        mService.getOneMonthPay(account_id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<GetPayResult>() {
                    @Override
                    public void accept(GetPayResult getPayResult) throws Exception {
                        if(getPayResult.isSuccess() == true){
                            mList = getPayResult.getData();
                            showList(true,false,mList);
                        }else{
                            Toast.makeText(getActivity(),"您还没有收支信息！",Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(Tag, "错误信息 -->"+throwable.getMessage());
                    }
                });
    }

    @OnClick({R.id.oneday_tv, R.id.oneweek_tv, R.id.onemonth_tv, R.id.more_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.oneday_tv:
                getOneDayPay(mSharedPreferences.getInt("account_id",000));
                break;
            case R.id.oneweek_tv:
                getOneWeekPay(mSharedPreferences.getInt("account_id",000));
                break;
            case R.id.onemonth_tv:
                getOneMonthPay(mSharedPreferences.getInt("account_id",000));
                break;
            case R.id.more_tv:
                Intent intent = new Intent(getActivity(), QueryTransaActivity.class);
                startActivity(intent);
                break;
        }
    }
}
