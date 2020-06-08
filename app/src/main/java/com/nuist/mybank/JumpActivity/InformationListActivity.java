package com.nuist.mybank.JumpActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.nuist.mybank.Adapter.ListViewAdapter;
import com.nuist.mybank.Interface.Service;
import com.nuist.mybank.POJO.ResultBean.GetInfoResult;
import com.nuist.mybank.POJO.ResultBean.GetTransferResult;
import com.nuist.mybank.R;
import com.nuist.mybank.Utils.CommonUtils;
import com.nuist.mybank.Utils.RetrofitManager;
import com.nuist.mybank.View.CustomActionBar;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class InformationListActivity extends AppCompatActivity {

    @BindView(R.id.infolist_actionbar)
    CustomActionBar infolistActionbar;
    @BindView(R.id.infolist_recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.infolist_refresh)
    SwipeRefreshLayout mRefreshLayout;
    private Retrofit mRetrofit = RetrofitManager.getRetrofit();//获取Retrofit
    private Service mService = mRetrofit.create(Service.class);//获取service
    private ListViewAdapter mListViewAdapter;//listView适配器
    private List<GetInfoResult.InfoBean> mList;//获取的信息公告数据
    private SharedPreferences mSharedPreferences;//SharedPreferences保存用户信息
    private String Tag = "InformationListActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_list);
        ButterKnife.bind(this);
        infolistActionbar.setStyle("信息公告");
        mSharedPreferences = this.getSharedPreferences("user_info", Context.MODE_PRIVATE);
        //获取公告数据
        getAllInfo();
        HandlerDownPullUpdate();
    }

    private void getAllInfo() {
        mService.getAllInfo()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<GetInfoResult>() {
                    @Override
                    public void accept(GetInfoResult getInfoResult) throws Exception {
                        if(getInfoResult.isSuccess() == true){
                            mList = getInfoResult.getData();
                            showList(true,false,mList);
                        }else{
                            //todo
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(Tag,"错误信息为 -->" +throwable.getMessage());
                    }
                });
    }
    private void showList(boolean isVertical,boolean isReverse,List<GetInfoResult.InfoBean> mData) {
        //设置Recyclerview设置布局管理器
        LinearLayoutManager manager = new LinearLayoutManager(this);
        //设置水平还是垂直
        manager.setOrientation(isVertical ? LinearLayoutManager.VERTICAL : LinearLayoutManager.HORIZONTAL);
        //设置正向还是反向
        manager.setReverseLayout(isReverse);

        mRecyclerView.setLayoutManager(manager);
        //设置adapter
        mListViewAdapter = new ListViewAdapter(this,mData);
        //设置Recyclerview
        mRecyclerView.setAdapter(mListViewAdapter);
        //设置监听
        initListViewListener();
    }

    private void initListViewListener(){
        mListViewAdapter.setOnItemClickListener(new ListViewAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(int position) {
                if(mSharedPreferences.getInt("account_id",000) == 000){
                    Toast.makeText(InformationListActivity.this,"请先登录！",Toast.LENGTH_SHORT).show();
                    startActivity(CommonUtils.getloginIntent(InformationListActivity.this));
                }else{
                    Intent intent = new Intent(InformationListActivity.this, InformationDetailActivity.class);
                    intent.putExtra("info_title",mList.get(position).getInfo_title());
                    startActivity(intent);
                }
            }
        });
    }
    private void HandlerDownPullUpdate() {
        mRefreshLayout.setEnabled(true);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //在这里面执行刷新数据的操作，
                mService.getAllInfo()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(new Consumer<GetInfoResult>() {
                            @Override
                            public void accept(GetInfoResult getInfoResult) throws Exception {
                                mList.clear();
                                if(getInfoResult.isSuccess() == true){
                                    mList = getInfoResult.getData();
                                    showList(true,false,mList);
                                    mRefreshLayout.setRefreshing(false);
                                }else{
                                    //todo
                                    mRefreshLayout.setRefreshing(false);
                                }
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                Log.e(Tag,"错误信息为 -->" +throwable.getMessage());
                                mRefreshLayout.setRefreshing(false);
                            }
                        });
            }
        });
    }
}
