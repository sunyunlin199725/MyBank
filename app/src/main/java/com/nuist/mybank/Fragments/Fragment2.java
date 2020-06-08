package com.nuist.mybank.Fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.nuist.mybank.Adapter.StaggerViewAdapter;
import com.nuist.mybank.Interface.DataToFragment2;
import com.nuist.mybank.Interface.Service;
import com.nuist.mybank.JumpActivity.OrderActivity;
import com.nuist.mybank.POJO.ResultBean.GetChartResult;
import com.nuist.mybank.POJO.ResultBean.GetGoodsResult;
import com.nuist.mybank.POJO.StaggerItem;
import com.nuist.mybank.R;
import com.nuist.mybank.Utils.CommonUtils;
import com.nuist.mybank.Utils.Config;
import com.nuist.mybank.Utils.RetrofitManager;
import com.nuist.mybank.View.LooperPagerView.PageItem;
import com.nuist.mybank.View.LooperPagerView.SunLooerPager;
import com.squareup.picasso.Picasso;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment2 extends Fragment implements DataToFragment2 {

    @BindView(R.id.recyclerview_stagger)
    RecyclerView mRecyclerView;
    @BindView(R.id.fresh_layout_stagger)
    SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.fragment2_searchet)
    EditText fragment2Searchet;
    @BindView(R.id.fragment2_searchiv)
    ImageView fragment2Searchiv;
    private SunLooerPager mSunLooerPager;//轮播图视图
    private List<PageItem> mData = new ArrayList<>();//轮播图资源
    private StaggerViewAdapter mStaggerViewAdapter;//StaggerView适配器
    private List<StaggerItem> mStaggerData = new ArrayList<>();//模拟数据
    private List<GetChartResult.ChartBean> mChartBeans;//得到的mchart
    private String Tag = "Fragment2";
    private Retrofit mRetrofit = RetrofitManager.getRetrofit();//获取Retrofit
    private Service mService = mRetrofit.create(Service.class);//获取service
    private SharedPreferences mSharedPreferences;//SharedPreferences保存用户信息

    public Fragment2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.e(Tag, "oncreateView..");
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fragment2, container, false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e(Tag, "onActivityCreate...");
        mSharedPreferences = getActivity().getSharedPreferences("user_info", Context.MODE_PRIVATE);
        ButterKnife.bind(this, getActivity());
        mSunLooerPager = getActivity().findViewById(R.id.sun_looper_pager);
        initPagerView();
        getAllGoods();
        //处理下拉刷新
        HandlerDownPullUpdate();
    }

    //视图设置资源
    private void initPagerView() {
        if (mChartBeans != null) {//当网络正常请求到数据时
            Log.e(Tag, "有网络");
            for (GetChartResult.ChartBean item : mChartBeans) {
                mData.add(new PageItem(item.getChart_title(), Config.baseurl + item.getChart_pic()));
                Log.e(Tag, Config.baseurl + item.getChart_pic());
            }
            Log.e(Tag, "initView...");
            //setData两个参数，一个内部的InnerAdapter对象，另一个绑定标题名称的接口监听
            //利用InnerAdapter对象设置图片数据，BindTitleListener设置图片对应标题
            mSunLooerPager.setData(new SunLooerPager.InnerAdapter() {
                @Override
                protected int getDataSize() {
                    return mData.size();
                }

                @Override
                protected View gerSubView(ViewGroup container, int position) {
                    ImageView iv = new ImageView(container.getContext());
                    Picasso.with(container.getContext()).load(mData.get(position).getPicResID()).into(iv);
                    iv.setScaleType(ImageView.ScaleType.FIT_XY);
                    return iv;
                }
            }, new SunLooerPager.BindTitleListener() {
                @Override
                public String getTitle(int position) {
                    return mData.get(position).getTitle();
                }
            });
            initEvent();
        } else {//无网络时
            mData.clear();
            Log.e(Tag, "无网络");
            PageItem pageItem = new PageItem();
            pageItem.setTitle("无网络");
            pageItem.setPiclocalID(R.mipmap.noweb);
            mData.add(pageItem);
            mSunLooerPager.setData(new SunLooerPager.InnerAdapter() {
                @Override
                protected int getDataSize() {
                    return mData.size();
                }

                @Override
                protected View gerSubView(ViewGroup container, int position) {
                    ImageView iv = new ImageView(container.getContext());
                    Picasso.with(container.getContext()).load(mData.get(position).getPiclocalID()).into(iv);
                    iv.setScaleType(ImageView.ScaleType.FIT_XY);
                    return iv;
                }
            }, new SunLooerPager.BindTitleListener() {
                @Override
                public String getTitle(int position) {
                    return "无网络";
                }
            });
            initEvent();
        }

    }

    //调用内部实现的ViewPage点击的监听，实现点击事件
    private void initEvent() {
        Log.e(Tag, "initEvent...");
        mSunLooerPager.setOnItemClickListener(new SunLooerPager.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (mSharedPreferences.getInt("account_id", 000) == 000) {
                    Toast.makeText(getActivity(), "请先登录！", Toast.LENGTH_SHORT).show();
                    startActivity(CommonUtils.getloginIntent(getActivity()));
                } else {
                    Intent intent = new Intent(getActivity(), OrderActivity.class);
                    intent.putExtra("goods_name", mChartBeans.get(position).getGoods_name());
                    startActivity(intent);
                }

            }
        });
    }

    private void showStagger(boolean isVertical, boolean isReverse, List<StaggerItem> Data) {
        //设置Recyclerview设置布局管理器
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2, isVertical ? StaggeredGridLayoutManager.VERTICAL : StaggeredGridLayoutManager.HORIZONTAL);
        //设置正向还是反向
        manager.setReverseLayout(isReverse);
        mRecyclerView.setLayoutManager(manager);
        //设置adapter
        mStaggerViewAdapter = new StaggerViewAdapter(Data);
        //设置Recyclerview
        mRecyclerView.setAdapter(mStaggerViewAdapter);
        mRecyclerView.setItemViewCacheSize(3);
        //设置监听
        initListenter();
    }

    //设置监听，实现条目点击
    private void initListenter() {

        mStaggerViewAdapter.setOnItemClickListener(new StaggerViewAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(int position) {
                if (mSharedPreferences.getInt("account_id", 000) == 000) {
                    Toast.makeText(getActivity(), "请先登录！", Toast.LENGTH_SHORT).show();
                    startActivity(CommonUtils.getloginIntent(getActivity()));
                } else {
                    Intent intent = new Intent(getActivity(), OrderActivity.class);
                    intent.putExtra("goods_name", mStaggerData.get(position).getTitle());
                    startActivity(intent);
                }

            }
        });

    }

    //处理下拉刷新的任务
    private void HandlerDownPullUpdate() {
        mRefreshLayout.setEnabled(true);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //在这里面执行刷新数据的操作，
                /**
                 * 当我们去在顶部下拉的这个方法就会被触发，
                 * 但是这个方法是MainTread是主线程，不可以执行耗时操作，
                 * 一般来说我们开一个线程请求数据
                 * 这里演示直接添加一条数据，
                 */
                mService.getAllGoods()
                        .observeOn(AndroidSchedulers.mainThread())//回调在主线程
                        .subscribeOn(Schedulers.io())//执行在子线程
                        .subscribe(new Consumer<GetGoodsResult>() {
                            @Override
                            public void accept(GetGoodsResult getGoodsResult) throws Exception {
                                if (getGoodsResult.isSuccess() == true) {
                                    mStaggerData.clear();
                                    for (GetGoodsResult.GoodsInfoBean goodsInfoBean : getGoodsResult.getData()) {
                                        StaggerItem item = new StaggerItem();
                                        item.setTitle(goodsInfoBean.getGoods_name());
                                        item.setTimestamp(new Timestamp(goodsInfoBean.getPublic_time()));
                                        item.setPic(Config.baseurl + goodsInfoBean.getGoods_pic());
                                        item.setText(goodsInfoBean.getBusiness().getBusiness_name());
                                        mStaggerData.add(item);
                                    }
                                    mStaggerViewAdapter.notifyDataSetChanged();
                                    mRefreshLayout.setRefreshing(false);
                                }
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                Log.e(Tag, "错误信息---》" + throwable.getMessage());
                                mRefreshLayout.setRefreshing(false);
                            }
                        });
            }
        });
    }

    /**
     * 获取所有商品信息
     */
    private void getAllGoods() {
        mService.getAllGoods()
                .observeOn(AndroidSchedulers.mainThread())//回调在主线程
                .subscribeOn(Schedulers.io())//执行在子线程
                .subscribe(new Consumer<GetGoodsResult>() {
                    @Override
                    public void accept(GetGoodsResult getGoodsResult) throws Exception {
                        if (getGoodsResult.isSuccess() == true) {
                            for (GetGoodsResult.GoodsInfoBean goodsInfoBean : getGoodsResult.getData()) {
                                StaggerItem item = new StaggerItem();
                                item.setTitle(goodsInfoBean.getGoods_name());
                                item.setTimestamp(new Timestamp(goodsInfoBean.getPublic_time()));
                                item.setPic(Config.baseurl + goodsInfoBean.getGoods_pic());
                                item.setText(goodsInfoBean.getBusiness().getBusiness_name());
                                mStaggerData.add(item);
                            }
                            showStagger(true, false, mStaggerData);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }
    //根据搜索框查询商品信息
    private void getGoodsWithName(String toString) {
        mService.getGoodsWithName(toString)
                .observeOn(AndroidSchedulers.mainThread())//回调在主线程
                .subscribeOn(Schedulers.io())//执行在子线程
                .subscribe(new Consumer<GetGoodsResult>() {
                    @Override
                    public void accept(GetGoodsResult getGoodsResult) throws Exception {
                        if (getGoodsResult.isSuccess() == true) {
                            mStaggerData.clear();
                            for (GetGoodsResult.GoodsInfoBean goodsInfoBean : getGoodsResult.getData()) {
                                StaggerItem item = new StaggerItem();
                                item.setTitle(goodsInfoBean.getGoods_name());
                                item.setTimestamp(new Timestamp(goodsInfoBean.getPublic_time()));
                                item.setPic(Config.baseurl + goodsInfoBean.getGoods_pic());
                                item.setText(goodsInfoBean.getBusiness().getBusiness_name());
                                mStaggerData.add(item);
                            }
                            showStagger(true, false, mStaggerData);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }
    @Override
    public void getChartData(List<GetChartResult.ChartBean> results) {
        this.mChartBeans = results;
        Log.e(Tag, mChartBeans.toString());
    }


    @OnClick(R.id.fragment2_searchiv)
    public void onViewClicked() {
        if (mSharedPreferences.getInt("account_id", 000) == 000) {
            Toast.makeText(getActivity(), "请先登录！", Toast.LENGTH_SHORT).show();
            startActivity(CommonUtils.getloginIntent(getActivity()));
        } else {
            if (TextUtils.isEmpty(fragment2Searchet.getText().toString())){
                Toast.makeText(getActivity(), "搜索框未填数据", Toast.LENGTH_SHORT).show();
            }else{
                getGoodsWithName(fragment2Searchet.getText().toString());
            }
        }
    }


}
