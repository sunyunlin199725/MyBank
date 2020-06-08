package com.nuist.mybank.JumpActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.nuist.mybank.Adapter.StaggerViewAdapter;
import com.nuist.mybank.Interface.Service;
import com.nuist.mybank.POJO.ResultBean.GetGoodsResult;
import com.nuist.mybank.POJO.StaggerItem;
import com.nuist.mybank.R;
import com.nuist.mybank.Utils.CommonUtils;
import com.nuist.mybank.Utils.Config;
import com.nuist.mybank.Utils.RetrofitManager;
import com.nuist.mybank.View.CustomActionBar;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class GoodsMarketActivity extends AppCompatActivity {

    @BindView(R.id.goodsmaket_actionbar)
    CustomActionBar goodsmaketActionbar;
    @BindView(R.id.goodsmarket_recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.fresh_layout_stagger)
    SwipeRefreshLayout mRefreshLayout;
    private Retrofit mRetrofit = RetrofitManager.getRetrofit();//获取Retrofit
    private Service mService = mRetrofit.create(Service.class);//获取service
    private SharedPreferences mSharedPreferences;//SharedPreferences保存用户信息
    private StaggerViewAdapter mStaggerViewAdapter;//StaggerView适配器
    private List<StaggerItem> mStaggerData = new ArrayList<>();//模拟数据
    private String Tag = "GoodsMarketActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_market);
        ButterKnife.bind(this);
        mSharedPreferences = getSharedPreferences("user_info", Context.MODE_PRIVATE);
        goodsmaketActionbar.setStyle("商品列表");
        getAllGoods();
        HandlerDownPullUpdate();
    }
    /**
     * 获取所有商品信息
     */
    private  void getAllGoods(){
        mService.getAllGoods()
                .observeOn(AndroidSchedulers.mainThread())//回调在主线程
                .subscribeOn(Schedulers.io())//执行在子线程
                .subscribe(new Consumer<GetGoodsResult>() {
                    @Override
                    public void accept(GetGoodsResult getGoodsResult) throws Exception {
                        if(getGoodsResult.isSuccess() == true){
                            for (GetGoodsResult.GoodsInfoBean goodsInfoBean:getGoodsResult.getData()) {
                                StaggerItem item = new StaggerItem();
                                item.setTitle(goodsInfoBean.getGoods_name());
                                item.setTimestamp( new Timestamp(goodsInfoBean.getPublic_time()));
                                item.setPic(Config.baseurl + goodsInfoBean.getGoods_pic());
                                item.setText(goodsInfoBean.getBusiness().getBusiness_name());
                                mStaggerData.add(item);
                            }
                            showStagger(true,false,mStaggerData);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(Tag,throwable.getMessage());
                    }
                });
    }
    //显示瀑布流
    private void showStagger(boolean isVertical,boolean isReverse,List<StaggerItem> Data) {
        //设置Recyclerview设置布局管理器
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2,isVertical ? StaggeredGridLayoutManager.VERTICAL : StaggeredGridLayoutManager.HORIZONTAL);
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
                if(mSharedPreferences.getInt("account_id",000) == 000){
                    Toast.makeText(GoodsMarketActivity.this,"请先登录！",Toast.LENGTH_SHORT).show();
                    startActivity(CommonUtils.getloginIntent(GoodsMarketActivity.this));
                }else{
                    Intent intent = new Intent(GoodsMarketActivity.this, OrderActivity.class);
                    intent.putExtra("goods_name",mStaggerData.get(position).getTitle());
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
                                if(getGoodsResult.isSuccess() == true){
                                    mStaggerData.clear();
                                    for (GetGoodsResult.GoodsInfoBean goodsInfoBean:getGoodsResult.getData()) {
                                        StaggerItem item = new StaggerItem();
                                        item.setTitle(goodsInfoBean.getGoods_name());
                                        item.setTimestamp( new Timestamp(goodsInfoBean.getPublic_time()));
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
                                Log.e(Tag,"错误信息---》"+throwable.getMessage());
                                mRefreshLayout.setRefreshing(false);
                            }
                        });
            }
        });
    }
}
