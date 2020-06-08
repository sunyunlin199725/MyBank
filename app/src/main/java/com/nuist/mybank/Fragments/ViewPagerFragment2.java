package com.nuist.mybank.Fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.nuist.mybank.Adapter.MonthDataAdapter;
import com.nuist.mybank.Adapter.YearDataAdapter;
import com.nuist.mybank.Interface.Service;
import com.nuist.mybank.POJO.ResultBean.GetMonthSumResult;
import com.nuist.mybank.POJO.ResultBean.GetYearDataResult;
import com.nuist.mybank.R;
import com.nuist.mybank.Utils.RetrofitManager;

import java.util.ArrayList;
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
public class ViewPagerFragment2 extends Fragment {


    @BindView(R.id.bill_recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.monthbillpay_tv)
    TextView monthbillpayTv;
    @BindView(R.id.monthbillreceive_tv)
    TextView monthbillreceiveTv;
    @BindView(R.id.yearbillpay_tv)
    TextView yearbillpayTv;
    @BindView(R.id.yearbillreceive_tv)
    TextView yearbillreceiveTv;
    private Retrofit mRetrofit = RetrofitManager.getRetrofit();//获取Retrofit
    private Service mService = mRetrofit.create(Service.class);//获取service
    private List<GetMonthSumResult.MonthSumBean> mList = new ArrayList<>();//获取的月度数据
    private List<GetYearDataResult.YearMapBean> mListYear;//获取的年度数据
    private SharedPreferences mSharedPreferences;//SharedPreferences保存用户信息
    private MonthDataAdapter mMonthDataAdapter;
    private YearDataAdapter mYearDataAdapter;
    private String Tag = "ViewPagerFragment2";

    public ViewPagerFragment2() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_pager_fragment2, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ButterKnife.bind(this, getActivity());
        mSharedPreferences = getActivity().getSharedPreferences("user_info", Context.MODE_PRIVATE);
    }
    private void showMonthList(boolean isVertical,boolean isReverse,List<GetMonthSumResult.MonthSumBean> mData,boolean ispay) {
        //设置Recyclerview设置布局管理器
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        //设置水平还是垂直
        manager.setOrientation(isVertical ? LinearLayoutManager.VERTICAL : LinearLayoutManager.HORIZONTAL);
        //设置正向还是反向
        manager.setReverseLayout(isReverse);

        mRecyclerView.setLayoutManager(manager);
        //设置adapter
        mMonthDataAdapter = new MonthDataAdapter(R.layout.item_monthdata_view,mData,ispay);
        //更换动画效果
        mMonthDataAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        //设置Recyclerview
        mRecyclerView.setAdapter(mMonthDataAdapter);
    }

    private void showYearList(boolean isVertical,boolean isReverse,List<GetYearDataResult.YearMapBean> mData,boolean ispay) {
        //设置Recyclerview设置布局管理器
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        //设置水平还是垂直
        manager.setOrientation(isVertical ? LinearLayoutManager.VERTICAL : LinearLayoutManager.HORIZONTAL);
        //设置正向还是反向
        manager.setReverseLayout(isReverse);

        mRecyclerView.setLayoutManager(manager);
        //设置adapter
        mYearDataAdapter = new YearDataAdapter(R.layout.item_monthdata_view,mData,ispay);
        //更换动画效果
        mYearDataAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        //设置Recyclerview
        mRecyclerView.setAdapter(mYearDataAdapter);
    }
    private void getMonthSum(int account_id){
        mService.getMonthSum(account_id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<GetMonthSumResult>() {
                    @Override
                    public void accept(GetMonthSumResult getMonthSumResult) throws Exception {
                        if(getMonthSumResult.isSuccess() == true){
                            mList = getMonthSumResult.getData();
                            showMonthList(true,false,mList,false);
                        }else{
                            Toast.makeText(getActivity(),"您还没有账单数据",Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(Tag,"错误信息 --》"+throwable.getMessage());
                    }
                });
    }
    private void getMonthExp(int account_id){
        mService.getMonthExp(account_id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<GetMonthSumResult>() {
                    @Override
                    public void accept(GetMonthSumResult getMonthSumResult) throws Exception {
                        if(getMonthSumResult.isSuccess() == true){
                            mList = getMonthSumResult.getData();
                            showMonthList(true,false,mList,true);
                        }else{
                            Toast.makeText(getActivity(),"您还没有账单数据",Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(Tag,"错误信息 --》"+throwable.getMessage());
                    }
                });
    }
    private void getYearSum(int account_id){
        mService.getYearSum(account_id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<GetYearDataResult>() {
                    @Override
                    public void accept(GetYearDataResult getYearDataResult) throws Exception {

                        if(getYearDataResult.isSuccess() == true){
                           mListYear = getYearDataResult.getData();
                           showYearList(true,false,mListYear,false);
                        }else{
                            Toast.makeText(getActivity(),"您还没有账单数据",Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(Tag,"错误信息 --》"+throwable.getMessage());
                    }
                });
    }
    private void getYearExp(int account_id){
        mService.getYearExp(account_id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<GetYearDataResult>() {
                    @Override
                    public void accept(GetYearDataResult getYearDataResult) throws Exception {

                        if(getYearDataResult.isSuccess() == true){
                            mListYear = getYearDataResult.getData();
                            showYearList(true,false,mListYear,true);
                        }else{
                            Toast.makeText(getActivity(),"您还没有账单数据",Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(Tag,"错误信息 --》"+throwable.getMessage());
                    }
                });
    }
    @OnClick({R.id.monthbillpay_tv, R.id.monthbillreceive_tv, R.id.yearbillpay_tv, R.id.yearbillreceive_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.monthbillpay_tv:
                getMonthExp(mSharedPreferences.getInt("account_id",000));
                break;
            case R.id.monthbillreceive_tv:
                getMonthSum(mSharedPreferences.getInt("account_id",000));
                break;
            case R.id.yearbillpay_tv:
                getYearExp(mSharedPreferences.getInt("account_id",000));
                break;
            case R.id.yearbillreceive_tv:
                getYearSum(mSharedPreferences.getInt("account_id",000));
                break;
        }
    }
}
