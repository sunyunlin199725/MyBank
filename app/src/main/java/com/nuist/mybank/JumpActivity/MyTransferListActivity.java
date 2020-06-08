package com.nuist.mybank.JumpActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.nuist.mybank.Adapter.OrderListAdapter;
import com.nuist.mybank.Adapter.TransferListAdapter;
import com.nuist.mybank.Interface.Service;
import com.nuist.mybank.POJO.ResultBean.GetOrderResult;
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

public class MyTransferListActivity extends AppCompatActivity {

    @BindView(R.id.transferlist_actionbar)
    CustomActionBar transferlistActionbar;
    @BindView(R.id.transferlist_recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.transferlist_refresh)
    SwipeRefreshLayout transferlistRefresh;
    private Retrofit mRetrofit = RetrofitManager.getRetrofit();//获取Retrofit
    private Service mService = mRetrofit.create(Service.class);//获取service
    private List<GetTransferResult.TransferBean> mList;//获取的订单数据
    private SharedPreferences mSharedPreferences;//SharedPreferences保存用户信息
    private TransferListAdapter mTransferListAdapter;
    private String Tag = "MyTransferListActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_transfer_list);
        ButterKnife.bind(this);
        mSharedPreferences = this.getSharedPreferences("user_info", Context.MODE_PRIVATE);
        transferlistActionbar.setStyle("转账记录");
        getMyTransfer(mSharedPreferences.getInt("account_id",000));
        HandlerDownPullUpdate();
    }



    private void showList(boolean isVertical,boolean isReverse,List<GetTransferResult.TransferBean> mData) {
        //设置Recyclerview设置布局管理器
        LinearLayoutManager manager = new LinearLayoutManager(this);
        //设置水平还是垂直
        manager.setOrientation(isVertical ? LinearLayoutManager.VERTICAL : LinearLayoutManager.HORIZONTAL);
        //设置正向还是反向
        manager.setReverseLayout(isReverse);

        mRecyclerView.setLayoutManager(manager);
        //设置adapter
        mTransferListAdapter = new TransferListAdapter(R.layout.item_transferlist_view,mData);
        //更换动画效果
        mTransferListAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        //设置Recyclerview
        mRecyclerView.setAdapter(mTransferListAdapter);
        //设置监听
        initListViewListener();
    }
    private void initListViewListener() {
       mTransferListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
           @Override
           public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
              Intent intent = new Intent(MyTransferListActivity.this,TransferDetailActivity.class);
              intent.putExtra("transfer_id",mList.get(position).getTransfer_id());
              startActivity(intent);
           }
       });
    }
    private void getMyTransfer(int account_id) {
       mService.getMyTransfer(account_id)
               .observeOn(AndroidSchedulers.mainThread())
               .subscribeOn(Schedulers.io())
               .subscribe(new Consumer<GetTransferResult>() {
                   @Override
                   public void accept(GetTransferResult getTransferResult) throws Exception {
                       if(getTransferResult.isSuccess() ==true){
                           mList = getTransferResult.getData();
                           showList(true,false,mList);
                       }else{
                           Toast.makeText(MyTransferListActivity.this,"您还没有转账记录！",Toast.LENGTH_SHORT).show();
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
        transferlistRefresh.setEnabled(true);
        transferlistRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //在这里面执行刷新数据的操作，
                /**
                 * 当我们去在顶部下拉的这个方法就会被触发，
                 * 但是这个方法是MainTread是主线程，不可以执行耗时操作，
                 * 一般来说我们开一个线程请求数据
                 * 这里演示直接添加一条数据，
                 */
                mService.getMyTransfer(mSharedPreferences.getInt("account_id",000))
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(new Consumer<GetTransferResult>() {
                            @Override
                            public void accept(GetTransferResult getTransferResult) throws Exception {
                                mList.clear();
                                if(getTransferResult.isSuccess() == true) {
                                   mList = getTransferResult.getData();
                                }else {
                                   //todo
                                }
                                showList(true,false,mList);
                                transferlistRefresh.setRefreshing(false);
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                Log.e(Tag, "错误信息 -->"+throwable.getMessage());
                                transferlistRefresh.setRefreshing(false);
                            }
                        });
            }
        });
    }

}
