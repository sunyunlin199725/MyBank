package com.nuist.mybank.JumpActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.nuist.mybank.Adapter.CodeListAdapter;
import com.nuist.mybank.Dialog.GoodsCodeDialog;
import com.nuist.mybank.Interface.Service;
import com.nuist.mybank.POJO.ResultBean.GetCodesResult;
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
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class GoodsCodeActivity extends AppCompatActivity {

    @BindView(R.id.goodscode_actionbar)
    CustomActionBar goodscodeActionbar;
    @BindView(R.id.goodscode_listview)
    RecyclerView mRecyclerView;
    @BindView(R.id.goodscode_refresh)
    SwipeRefreshLayout goodscodeRefresh;
    private Retrofit mRetrofit = RetrofitManager.getRetrofit();//获取Retrofit
    private Service mService = mRetrofit.create(Service.class);//获取service
    private List<GetCodesResult.GoodsCodeBean> mList;//获取的订单数据
    private SharedPreferences mSharedPreferences;//SharedPreferences保存用户信息
    private CodeListAdapter mCodeListAdapter;//codelist适配器
    private GoodsCodeDialog mDialog;
    private String Tag = "GoodsCodeActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_code);
        ButterKnife.bind(this);
        goodscodeActionbar.setStyle("取货码");
        mSharedPreferences = this.getSharedPreferences("user_info", Context.MODE_PRIVATE);
        getCodeFromAcid(mSharedPreferences.getInt("account_id",000));
        //处理上拉刷新
        HandlerDownPullUpdate();
    }

    /**
     * 处理下拉刷新
     */
    private void HandlerDownPullUpdate() {
        goodscodeRefresh.setEnabled(true);
        goodscodeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //在这里面执行刷新数据的操作，
                /**
                 * 当我们去在顶部下拉的这个方法就会被触发，
                 * 但是这个方法是MainTread是主线程，不可以执行耗时操作，
                 * 一般来说我们开一个线程请求数据
                 * 这里演示直接添加一条数据，
                 */
                mService.getCodesFromAcid(mSharedPreferences.getInt("account_id",000))
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(new Consumer<GetCodesResult>() {
                            @Override
                            public void accept(GetCodesResult getCodesResult) throws Exception {
                                mList.clear();
                                if(getCodesResult.isSuccess() == true){
                                    mList = getCodesResult.getData();
                                }
                                showList(true,false,mList);
                                goodscodeRefresh.setRefreshing(false);
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                Log.e(Tag, "错误信息 -->"+throwable.getMessage());
                                goodscodeRefresh.setRefreshing(false);
                            }
                        });


            }
        });
    }

    private void initListViewListener() {
        mCodeListAdapter.setOnItemClickListener(new CodeListAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(int position) {
                //todo
                mDialog = new GoodsCodeDialog(GoodsCodeActivity.this,mList.get(position));
                mDialog.show();
            }
        });
    }
    public void getCodeFromAcid(int account_id){
        mService.getCodesFromAcid(account_id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<GetCodesResult>() {
                    @Override
                    public void accept(GetCodesResult getCodesResult) throws Exception {
                        if(getCodesResult.isSuccess() == true){
                            Log.e(Tag, getCodesResult.toString());
                            mList = getCodesResult.getData();
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
    private void showList(boolean isVertical,boolean isReverse,List<GetCodesResult.GoodsCodeBean> mData) {
        //设置Recyclerview设置布局管理器
        LinearLayoutManager manager = new LinearLayoutManager(this);
        //设置水平还是垂直
        manager.setOrientation(isVertical ? LinearLayoutManager.VERTICAL : LinearLayoutManager.HORIZONTAL);
        //设置正向还是反向
        manager.setReverseLayout(isReverse);

        mRecyclerView.setLayoutManager(manager);
        //设置adapter
        mCodeListAdapter = new CodeListAdapter(mData);
        //设置Recyclerview
        mRecyclerView.setAdapter(mCodeListAdapter);
        //设置监听
        initListViewListener();
    }
}
