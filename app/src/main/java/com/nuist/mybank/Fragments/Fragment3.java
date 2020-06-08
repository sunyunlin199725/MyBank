package com.nuist.mybank.Fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.nuist.mybank.Adapter.CardViewPagerAdapter;
import com.nuist.mybank.Interface.Service;
import com.nuist.mybank.JumpActivity.AddCard.AddCardActivity;
import com.nuist.mybank.JumpActivity.CardManagerActivity;
import com.nuist.mybank.JumpActivity.MoneySumActivity;
import com.nuist.mybank.JumpActivity.MyTransferListActivity;
import com.nuist.mybank.JumpActivity.QueryTransaActivity;
import com.nuist.mybank.JumpActivity.TranferActivity;
import com.nuist.mybank.JumpActivity.TransactionActivity;
import com.nuist.mybank.POJO.BankCardView;
import com.nuist.mybank.POJO.ResultBean.GetBankCardResult;
import com.nuist.mybank.POJO.ResultBean.GetMoneyResult;
import com.nuist.mybank.POJO.ResultBean.GetSevenPayResult;
import com.nuist.mybank.R;
import com.nuist.mybank.Utils.CommonUtils;
import com.nuist.mybank.Utils.Formatter.MyValueFormatter;
import com.nuist.mybank.Utils.Formatter.ScalePageTransformer;
import com.nuist.mybank.Utils.Formatter.ValueFormatter;
import com.nuist.mybank.Utils.RetrofitManager;
import com.nuist.mybank.View.LooperPagerView.SizeUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
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
public class Fragment3 extends Fragment {


    @BindView(R.id.fragment3_eye_iv)
    ImageView fragment3EyeIv;
    @BindView(R.id.fragment3_money)
    TextView fragment3Money;
    @BindView(R.id.queryrecord_tv)
    TextView queryrecordTv;
    @BindView(R.id.barChart)
    BarChart mBarChart;
    @BindView(R.id.fragment3_transfer_tv)
    TextView fragment3TransferTv;
    @BindView(R.id.fragment3_money_tv)
    TextView fragment3MoneyTv;
    @BindView(R.id.fragment3_xykhk_tv)
    TextView fragment3XykhkTv;
    @BindView(R.id.fragment3_xyksq_tv)
    TextView fragment3XyksqTv;
    @BindView(R.id.showbc_TV)
    TextView showbcTV;
    @BindView(R.id.addCard_tv)
    TextView addCardTv;
    @BindView(R.id.fragment3_viewpager)
    ViewPager mViewPager;
    @BindView(R.id.query_tv)
    TextView queryTv;
    private Boolean showMoney = true;
    private SharedPreferences mSharedPreferences;//SharedPreferences保存用户信息
    private CardViewPagerAdapter mViewPageAdapter;
    private List<GetBankCardResult.BankCardBean> mList = new ArrayList<>();//获得的用户银行卡集合
    private List<BankCardView> mData = new ArrayList<>();//ViewPager数据源
    private List<GetSevenPayResult.DataBean> sevenPay = new ArrayList<>();//获取近七笔支出
    private Retrofit mRetrofit = RetrofitManager.getRetrofit();//获取Retrofit
    private Service mService = mRetrofit.create(Service.class);//获取service
    private double monthmoney = 0.0;
    private String Tag = "Fragment3";

    public Fragment3() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fragment3, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mSharedPreferences = getActivity().getSharedPreferences("user_info", Context.MODE_PRIVATE);
        ButterKnife.bind(this, getActivity());
        if(mSharedPreferences.getInt("account_id", 000) == 000){
            Toast.makeText(getActivity(), "请先登录！", Toast.LENGTH_SHORT).show();
        }else{
            getMonthMoney(mSharedPreferences.getInt("account_id", 000));
        }
    }


    @OnClick({R.id.fragment3_eye_iv, R.id.queryrecord_tv, R.id.fragment3_transfer_tv, R.id.fragment3_money_tv, R.id.fragment3_xykhk_tv, R.id.fragment3_xyksq_tv, R.id.showbc_TV, R.id.addCard_tv,R.id.query_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fragment3_transfer_tv:
                if (mSharedPreferences.getInt("account_id", 000) == 000) {
                    Toast.makeText(getActivity(), "请先登录！", Toast.LENGTH_SHORT).show();
                    startActivity(CommonUtils.getloginIntent(getActivity()));
                } else {
                    Intent intent = new Intent(getActivity(), TranferActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.fragment3_money_tv:
                if (mSharedPreferences.getInt("account_id", 000) == 000) {
                    Toast.makeText(getActivity(), "请先登录！", Toast.LENGTH_SHORT).show();
                    startActivity(CommonUtils.getloginIntent(getActivity()));
                } else {
                    Intent intent = new Intent(getActivity(), MoneySumActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.fragment3_xykhk_tv:
                if (mSharedPreferences.getInt("account_id", 000) == 000) {
                    Toast.makeText(getActivity(), "请先登录！", Toast.LENGTH_SHORT).show();
                    startActivity(CommonUtils.getloginIntent(getActivity()));
                } else {
                    Intent intent = new Intent(getActivity(), MyTransferListActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.fragment3_xyksq_tv:
                if (mSharedPreferences.getInt("account_id", 000) == 000) {
                    Toast.makeText(getActivity(), "请先登录！", Toast.LENGTH_SHORT).show();
                    startActivity(CommonUtils.getloginIntent(getActivity()));
                } else {
                    Intent intent = new Intent(getActivity(), QueryTransaActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.fragment3_eye_iv:
                getAndChangeMoney();
                break;
            case R.id.query_tv:
                if (mSharedPreferences.getInt("account_id", 000) == 000) {
                    Toast.makeText(getActivity(), "请先登录！", Toast.LENGTH_SHORT).show();
                    startActivity(CommonUtils.getloginIntent(getActivity()));
                } else {
                    getSevenPay(mSharedPreferences.getInt("account_id", 000));
                }
                break;
            case R.id.queryrecord_tv:
                if (mSharedPreferences.getInt("account_id", 000) == 000) {
                    Toast.makeText(getActivity(), "请先登录！", Toast.LENGTH_SHORT).show();
                    startActivity(CommonUtils.getloginIntent(getActivity()));
                } else {
                    Intent intent = new Intent(getActivity(), TransactionActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.showbc_TV:
                if (mSharedPreferences.getInt("account_id", 000) == 000) {
                    Toast.makeText(getActivity(), "请先登录！", Toast.LENGTH_SHORT).show();
                    startActivity(CommonUtils.getloginIntent(getActivity()));
                } else {
                    getBankCard(mSharedPreferences.getInt("account_id", 000));
                }
                break;
            case R.id.addCard_tv:
                if (mSharedPreferences.getInt("account_id", 000) == 000) {
                    Toast.makeText(getActivity(), "请先登录！", Toast.LENGTH_SHORT).show();
                    startActivity(CommonUtils.getloginIntent(getActivity()));
                } else {
                    Intent intent = new Intent(getActivity(), AddCardActivity.class);
                    startActivity(intent);
                }
                break;
        }
    }

    /**
     * 隐藏获取账户总金额
     **/
    private void getAndChangeMoney() {
        if(monthmoney > 0){
            if (showMoney == true) {
                fragment3EyeIv.setImageResource(R.mipmap.biyan);
                fragment3Money.setText("* * * *");
                showMoney = false;
            } else {
                fragment3EyeIv.setImageResource(R.mipmap.yanjing);
                fragment3Money.setText(monthmoney+"元");
                showMoney = true;
            }
        }else{
            if (showMoney == true) {
                fragment3EyeIv.setImageResource(R.mipmap.biyan);
                fragment3Money.setText("* * * *");
                showMoney = false;
            } else {
                fragment3EyeIv.setImageResource(R.mipmap.yanjing);
                fragment3Money.setText("- - - -");
                showMoney = true;
            }
        }
    }
    /**
     * 初始化ViewPager
     *
     * @param mData
     */
    private void initViewPager(List<BankCardView> mData) {
        mViewPageAdapter = new CardViewPagerAdapter(mData);
        mViewPager.setAdapter(mViewPageAdapter);
        mViewPager.setPageTransformer(false, new ScalePageTransformer(false));
        mViewPager.setPageMargin(SizeUtils.dip2px(getActivity(), 20));
        mViewPager.setOffscreenPageLimit(2);

        mViewPageAdapter.setOnItemClickListener(new CardViewPagerAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(int position) {
                if (mSharedPreferences.getInt("account_id", 000) == 000) {
                    Toast.makeText(getActivity(), "请先登录！", Toast.LENGTH_SHORT).show();
                    startActivity(CommonUtils.getloginIntent(getActivity()));
                } else {
                    Intent intent = new Intent(getActivity(), CardManagerActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    /**
     * 初始化柱状图
     */
    private void initBarChart(List<GetSevenPayResult.DataBean> sevenPay) {
        mBarChart.getDescription().setEnabled(false);
        //设置最大值条目，超出之后不会有值
        mBarChart.setMaxVisibleValueCount(60);
        //分别在x轴和y轴上进行缩放
        mBarChart.setPinchZoom(true);
        //设置剩余统计图的阴影
        mBarChart.setDrawBarShadow(false);
        //设置网格布局
        mBarChart.setGridBackgroundColor(Color.WHITE);
        mBarChart.setDrawGridBackground(true);
        //通过自定义一个x轴标签来实现2,015 有分割符符bug
        ValueFormatter custom = new MyValueFormatter("");
        //获取x轴线
        XAxis xAxis = mBarChart.getXAxis();
        //设置x轴的显示位置
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //是否显示竖直标尺线
        xAxis.setDrawGridLines(false);
        //图表将避免第一个和最后一个标签条目被减掉在图表或屏幕的边缘
        xAxis.setAvoidFirstLastClipping(false);
        //绘制标签  指x轴上的对应数值 默认true
        xAxis.setDrawLabels(true);
        xAxis.setValueFormatter(custom);
        //缩放后x 轴数据重叠问题
        xAxis.setGranularityEnabled(true);
        //获取右边y标签
        YAxis axisRight = mBarChart.getAxisRight();
        axisRight.setAxisMinimum(0);
        //获取左边y轴的标签
        YAxis axisLeft = mBarChart.getAxisLeft();
        //设置Y轴数值 从零开始
        axisLeft.setAxisMinimum(0);

        mBarChart.getAxisLeft().setDrawGridLines(false);
        //设置动画时间
        mBarChart.animateXY(600, 600);

        mBarChart.getLegend().setEnabled(true);

        getChartData(sevenPay); //设置柱形统计图上的值

        mBarChart.getData().setValueTextSize(8);
        for (IDataSet set : mBarChart.getData().getDataSets()) {
            set.setDrawValues(!set.isDrawValuesEnabled());
        }

    }

    /**
     * 图标数据
     */
    private void getChartData(List<GetSevenPayResult.DataBean> sevenPay) {
        ArrayList<BarEntry> values = new ArrayList<>();
        for(int i=0;i<sevenPay.size();i++){
            values.add(new BarEntry(Float.valueOf(i+1), (float) sevenPay.get(sevenPay.size() -i-1).getTrans_money()));
        }
       /* BarEntry barEntry = new BarEntry(Float.valueOf("14"), Float.valueOf("10.5"));
        BarEntry barEntry1 = new BarEntry(Float.valueOf("15"), Float.valueOf("20.1"));
        BarEntry barEntry2 = new BarEntry(Float.valueOf("16"), Float.valueOf("30.5"));
        BarEntry barEntry3 = new BarEntry(Float.valueOf("17"), Float.valueOf("45"));
        BarEntry barEntry4 = new BarEntry(Float.valueOf("18"), Float.valueOf("30.9"));
        BarEntry barEntry5 = new BarEntry(Float.valueOf("19"), Float.valueOf("65.4"));
        BarEntry barEntry6 = new BarEntry(Float.valueOf("20"), Float.valueOf("74"));
        BarEntry barEntry7 = new BarEntry(Float.valueOf("21"), Float.valueOf("18.7"));
        BarEntry barEntry8 = new BarEntry(Float.valueOf("22"), Float.valueOf("30"));
        BarEntry barEntry9 = new BarEntry(Float.valueOf("23"), Float.valueOf("65.7"));
        values.add(barEntry);
        values.add(barEntry1);
        values.add(barEntry2);
        values.add(barEntry3);
        values.add(barEntry4);
        values.add(barEntry5);
        values.add(barEntry6);
        values.add(barEntry7);
        values.add(barEntry8);
        values.add(barEntry9);*/

        BarDataSet barDataSet;
        if (mBarChart.getData() != null && mBarChart.getData().getDataSetCount() > 0) {
            barDataSet = (BarDataSet) mBarChart.getData().getDataSetByIndex(0);
            barDataSet.setValues(values);
            mBarChart.getData().notifyDataChanged();
            mBarChart.notifyDataSetChanged();
        } else {
            barDataSet = new BarDataSet(values, "消费金额");
            barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
            barDataSet.setDrawValues(false);

            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(barDataSet);

            BarData data = new BarData(dataSets);
            mBarChart.setData(data);
            mBarChart.setFitBars(true);
        }
        //绘制图表
        mBarChart.invalidate();
    }

    private void getBankCard(int account_id) {
        mService.getBankCard(account_id)
                .observeOn(AndroidSchedulers.mainThread())//回调在主线程
                .subscribeOn(Schedulers.io())//执行在子线程
                .subscribe(new Consumer<GetBankCardResult>() {
                    @Override
                    public void accept(GetBankCardResult getBankCardResult) throws Exception {
                        if (getBankCardResult.isSuccess() == true) {
                            mList = getBankCardResult.getData();
                            Log.e(Tag, mList.toString());
                            mData.clear();
                            for (GetBankCardResult.BankCardBean bankCardBean : mList) {
                                BankCardView bankCardView = new BankCardView();
                                bankCardView.setBank_name(bankCardBean.getBank().getBank_name());
                                bankCardView.setBc_no(bankCardBean.getBc_no());
                                bankCardView.setBc_type(bankCardBean.getBc_type());
                                mData.add(bankCardView);
                            }
                            initViewPager(mData);

                        } else {
                            mData.clear();
                            Toast.makeText(getActivity(), getBankCardResult.getMessage(), Toast.LENGTH_SHORT).show();
                            BankCardView bankCardView = new BankCardView();
                            bankCardView.setBank_name("您没有绑定银行卡");
                            bankCardView.setBc_no("****************");
                            bankCardView.setBc_type('1');
                            mData.add(bankCardView);
                            initViewPager(mData);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(Tag, "错误信息为 --》" + throwable.getMessage());
                    }
                });
    }

    private void getMonthMoney(int account_id) {
         mService.getMonthMoney(account_id)
                 .observeOn(AndroidSchedulers.mainThread())//回调在主线程
                 .subscribeOn(Schedulers.io())//执行在子线程
                 .subscribe(new Consumer<GetMoneyResult>() {
                     @Override
                     public void accept(GetMoneyResult getMoneyResult) throws Exception {
                         if(getMoneyResult.isSuccess() == true){
                             monthmoney = getMoneyResult.getData();
                             fragment3Money.setText(monthmoney+"元");
                         }

                     }
                 }, new Consumer<Throwable>() {
                     @Override
                     public void accept(Throwable throwable) throws Exception {
                         Log.e(Tag, "错误信息为 --》" + throwable.getMessage());
                     }
                 });
    }

    private void getSevenPay(int account_id){
        mService.getSevenPay(account_id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<GetSevenPayResult>() {
                    @Override
                    public void accept(GetSevenPayResult getSevenPayResult) throws Exception {
                        sevenPay.clear();
                        if(getSevenPayResult.isSuccess() == true){
                            sevenPay = getSevenPayResult.getData();
                            Log.e(Tag,sevenPay.toString());
                            initBarChart(sevenPay);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(Tag, "错误信息为 --》" + throwable.getMessage());
                    }
                });
    }
}
