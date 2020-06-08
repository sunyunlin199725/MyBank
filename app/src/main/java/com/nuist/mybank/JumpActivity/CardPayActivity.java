package com.nuist.mybank.JumpActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.nuist.mybank.Adapter.TransactionAdapter;
import com.nuist.mybank.Interface.Service;
import com.nuist.mybank.POJO.ResultBean.GetPayResult;
import com.nuist.mybank.POJO.ResultBean.GetTransferResult;
import com.nuist.mybank.R;
import com.nuist.mybank.Utils.RetrofitManager;
import com.nuist.mybank.View.CustomActionBar;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class CardPayActivity extends AppCompatActivity {

    @BindView(R.id.cardpay_actionbar)
    CustomActionBar cardpayActionbar;
    @BindView(R.id.cardpay_recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.cardpay_refresh)
    SwipeRefreshLayout cardpayRefresh;
    private Retrofit mRetrofit = RetrofitManager.getRetrofit();//获取Retrofit
    private Service mService = mRetrofit.create(Service.class);//获取service
    private List<GetPayResult.PayBean> mList;//获取的收支信息数据
    private SharedPreferences mSharedPreferences;//SharedPreferences保存用户信息
    private TransactionAdapter mTransactionAdapter;
    private String Tag = "CardPayActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_pay);
        ButterKnife.bind(this);
        cardpayActionbar.setStyle("银行卡收支信息");
        mSharedPreferences = this.getSharedPreferences("user_info", Context.MODE_PRIVATE);
        getCardPay(getIntent().getStringExtra("card_no"));
        HandlerDownPullUpdate();
    }
    private void showList(boolean isVertical,boolean isReverse,List<GetPayResult.PayBean> mData) {
        //设置Recyclerview设置布局管理器
        LinearLayoutManager manager = new LinearLayoutManager(this);
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
                    Intent transintent = new Intent(CardPayActivity.this, TransferDetailActivity.class);
                    transintent.putExtra("transfer_id",mList.get(position).getTrans_id());
                    startActivity(transintent);
                }else if(type.equals("02")){//订单类型
                    Intent orderintent = new Intent(CardPayActivity.this, OrderDetailActivity.class);
                    orderintent.putExtra("order_id",mList.get(position).getTrans_id());
                    startActivity(orderintent);
                }else{
                    Toast.makeText(CardPayActivity.this,"未知类型"+position,Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void getCardPay(String card_no){
        mService.getCardPay(card_no)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<GetPayResult>() {
                    @Override
                    public void accept(GetPayResult getPayResult) throws Exception {
                        if(getPayResult.isSuccess() == true){
                            mList = getPayResult.getData();
                            showList(true,false,mList);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(Tag, "错误信息 -->"+throwable.getMessage());
                    }
                });
    }
    private void HandlerDownPullUpdate() {
        cardpayRefresh.setEnabled(true);
        cardpayRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //在这里面执行刷新数据的操作，
                /**
                 * 当我们去在顶部下拉的这个方法就会被触发，
                 * 但是这个方法是MainTread是主线程，不可以执行耗时操作，
                 * 一般来说我们开一个线程请求数据
                 * 这里演示直接添加一条数据，
                 */
                mService.getCardPay(getIntent().getStringExtra("card_no"))
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(new Consumer<GetPayResult>() {
                            @Override
                            public void accept(GetPayResult getPayResult) throws Exception {
                                mList.clear();
                                if (getPayResult.isSuccess() == true) {
                                    mList = getPayResult.getData();
                                }
                                showList(true, false, mList);
                                cardpayRefresh.setRefreshing(false);
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                Log.e(Tag, "错误信息 -->" + throwable.getMessage());
                                cardpayRefresh.setRefreshing(false);
                            }
                        });
            }
        });
    }
}
