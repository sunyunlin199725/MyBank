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
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.nuist.mybank.Adapter.ListViewAdapter;
import com.nuist.mybank.Interface.Service;
import com.nuist.mybank.JumpActivity.AddCard.AddCardActivity;
import com.nuist.mybank.JumpActivity.CardManagerActivity;
import com.nuist.mybank.JumpActivity.GoodsCodeActivity;
import com.nuist.mybank.JumpActivity.GoodsMarketActivity;
import com.nuist.mybank.JumpActivity.InformationDetailActivity;
import com.nuist.mybank.JumpActivity.InformationListActivity;
import com.nuist.mybank.JumpActivity.MyTransferListActivity;
import com.nuist.mybank.JumpActivity.PaymentActivity;
import com.nuist.mybank.JumpActivity.ReceiveActivity;
import com.nuist.mybank.JumpActivity.TranferActivity;
import com.nuist.mybank.JumpActivity.UpdatePhoneActivity;
import com.nuist.mybank.POJO.ResultBean.GetInfoResult;
import com.nuist.mybank.R;
import com.nuist.mybank.Utils.CommonUtils;
import com.nuist.mybank.Utils.QRUtil.QRActivity;
import com.nuist.mybank.Utils.RetrofitManager;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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
public class Fragment1 extends Fragment {

    @BindView(R.id.payment_tv)
    TextView paymentTv;
    @BindView(R.id.receive_tv)
    TextView receiveTv;
    @BindView(R.id.scan_tv)
    TextView scanTv;
    @BindView(R.id.card_tv)
    TextView cardTv;
    @BindView(R.id.transfer_tv)
    TextView transferTv;
    @BindView(R.id.credit_card_tv)
    TextView creditCardTv;
    @BindView(R.id.mobilephone_tv)
    TextView mobilephoneTv;
    @BindView(R.id.finance_tv)
    TextView financeTv;
    @BindView(R.id.payback_tv)
    TextView paybackTv;
    @BindView(R.id.shopping_tv)
    TextView shoppingTv;
    @BindView(R.id.card_manage_tv)
    TextView cardManageTv;
    @BindView(R.id.formore_tv)
    TextView formoreTv;
    @BindView(R.id.more_list_tv)
    TextView moreListTv;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;//展示ListView
    @BindView(R.id.fresh_layout)
    SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.fragment1_searchiv)
    ImageView fragment1Searchiv;
    @BindView(R.id.card_view2)
    CardView cardView2;
    @BindView(R.id.fragment1_searchet)
    EditText fragment1Searchet;
    private Retrofit mRetrofit = RetrofitManager.getRetrofit();//获取Retrofit
    private Service mService = mRetrofit.create(Service.class);//获取service
    private SharedPreferences mSharedPreferences;//SharedPreferences保存用户信息
    private ListViewAdapter mListViewAdapter;//listView适配器
    private List<GetInfoResult.InfoBean> mList;//获取的信息公告数据
    private final static int REQUEST_CODE = 1001;//二维码扫描请求码
    private String Tag = "Fragment1";

    public Fragment1() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fragment1, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ButterKnife.bind(this, getActivity());
        mSharedPreferences = getActivity().getSharedPreferences("user_info", Context.MODE_PRIVATE);
        //获取公告数据
        getAllInfo();
        //处理上拉刷新
        HandlerDownPullUpdate();
    }

    private void getAllInfo() {
        mService.getAllInfo()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<GetInfoResult>() {
                    @Override
                    public void accept(GetInfoResult getInfoResult) throws Exception {
                        if (getInfoResult.isSuccess() == true) {
                            mList = getInfoResult.getData();
                            showList(true, false, mList);
                        } else {
                            //todo
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(Tag, "错误信息为 -->" + throwable.getMessage());
                    }
                });
    }
    private void getInfoWithTitle(String info_title){
        mService.getInfoWithTitle(info_title)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<GetInfoResult>() {
                    @Override
                    public void accept(GetInfoResult getInfoResult) throws Exception {
                        mList.clear();
                        if (getInfoResult.isSuccess() == true) {
                            mList = getInfoResult.getData();
                            showList(true, false, mList);
                        } else {
                            //todo
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(Tag, "错误信息为 -->" + throwable.getMessage());
                    }
                });
    }
    private void showList(boolean isVertical, boolean isReverse, List<GetInfoResult.InfoBean> mData) {
        //设置Recyclerview设置布局管理器
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        //设置水平还是垂直
        manager.setOrientation(isVertical ? LinearLayoutManager.VERTICAL : LinearLayoutManager.HORIZONTAL);
        //设置正向还是反向
        manager.setReverseLayout(isReverse);

        mRecyclerView.setLayoutManager(manager);
        //设置adapter
        mListViewAdapter = new ListViewAdapter(getActivity(), mData);
        //设置Recyclerview
        mRecyclerView.setAdapter(mListViewAdapter);
        //设置监听
        initListViewListener();
    }

    private void initListViewListener() {
        mListViewAdapter.setOnItemClickListener(new ListViewAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(int position) {
                if (mSharedPreferences.getInt("account_id", 000) == 000) {
                    Toast.makeText(getActivity(), "请先登录！", Toast.LENGTH_SHORT).show();
                    startActivity(CommonUtils.getloginIntent(getActivity()));
                } else {
                    Intent intent = new Intent(getActivity(), InformationDetailActivity.class);
                    intent.putExtra("info_title", mList.get(position).getInfo_title());
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
                                if (getInfoResult.isSuccess() == true) {
                                    mList = getInfoResult.getData();
                                    showList(true, false, mList);
                                    mRefreshLayout.setRefreshing(false);
                                } else {
                                    //todo
                                    mRefreshLayout.setRefreshing(false);
                                }
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                Log.e(Tag, "错误信息为 -->" + throwable.getMessage());
                                mRefreshLayout.setRefreshing(false);
                            }
                        });
            }
        });
    }

    @OnClick({R.id.payment_tv, R.id.receive_tv, R.id.scan_tv, R.id.card_tv, R.id.transfer_tv, R.id.credit_card_tv, R.id.mobilephone_tv, R.id.finance_tv, R.id.payback_tv, R.id.shopping_tv, R.id.card_manage_tv, R.id.formore_tv, R.id.more_list_tv, R.id.fragment1_searchiv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fragment1_searchiv:
                if (mSharedPreferences.getInt("account_id", 000) == 000) {
                    Toast.makeText(getActivity(), "请先登录！", Toast.LENGTH_SHORT).show();
                    startActivity(CommonUtils.getloginIntent(getActivity()));
                } else {
                    if (TextUtils.isEmpty(fragment1Searchet.getText().toString())){
                        Toast.makeText(getActivity(), "搜索框未填数据", Toast.LENGTH_SHORT).show();
                    }else{
                        getInfoWithTitle(fragment1Searchet.getText().toString());
                    }
                }
                break;
            case R.id.payment_tv:
                if (mSharedPreferences.getInt("account_id", 000) == 000) {
                    Toast.makeText(getActivity(), "请先登录！", Toast.LENGTH_SHORT).show();
                    startActivity(CommonUtils.getloginIntent(getActivity()));
                } else {
                    Intent payintent = new Intent(getActivity(), PaymentActivity.class);
                    startActivity(payintent);
                }
                break;
            case R.id.receive_tv:
                if (mSharedPreferences.getInt("account_id", 000) == 000) {
                    Toast.makeText(getActivity(), "请先登录！", Toast.LENGTH_SHORT).show();
                    startActivity(CommonUtils.getloginIntent(getActivity()));
                } else {

                    Intent receiveintent = new Intent(getActivity(), ReceiveActivity.class);
                    startActivity(receiveintent);
                }
                break;
            case R.id.scan_tv:
                if (mSharedPreferences.getInt("account_id", 000) == 000) {
                    Toast.makeText(getActivity(), "请先登录！", Toast.LENGTH_SHORT).show();
                    startActivity(CommonUtils.getloginIntent(getActivity()));
                } else {
                    //打开扫描界面
                    IntentIntegrator intentIntegrator = new IntentIntegrator(getActivity());
                    intentIntegrator.setOrientationLocked(false);
                    intentIntegrator.setCaptureActivity(QRActivity.class); // 设置自定义的activity是QRActivity
                    intentIntegrator.setRequestCode(REQUEST_CODE);
                    intentIntegrator.initiateScan();
                }
                break;
            case R.id.card_tv:
                if (mSharedPreferences.getInt("account_id", 000) == 000) {
                    Toast.makeText(getActivity(), "请先登录！", Toast.LENGTH_SHORT).show();
                    startActivity(CommonUtils.getloginIntent(getActivity()));
                } else {
                    Intent intent = new Intent(getActivity(), CardManagerActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.transfer_tv:
                if (mSharedPreferences.getInt("account_id", 000) == 000) {
                    Toast.makeText(getActivity(), "请先登录！", Toast.LENGTH_SHORT).show();
                    startActivity(CommonUtils.getloginIntent(getActivity()));
                } else {
                    Intent intent = new Intent(getActivity(), TranferActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.credit_card_tv:
                if (mSharedPreferences.getInt("account_id", 000) == 000) {
                    Toast.makeText(getActivity(), "请先登录！", Toast.LENGTH_SHORT).show();
                    startActivity(CommonUtils.getloginIntent(getActivity()));
                } else {
                    Intent intent = new Intent(getActivity(), AddCardActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.mobilephone_tv:
                if (mSharedPreferences.getInt("account_id", 000) == 000) {
                    Toast.makeText(getActivity(), "请先登录！", Toast.LENGTH_SHORT).show();
                    startActivity(CommonUtils.getloginIntent(getActivity()));
                } else {
                    Intent intent = new Intent(getActivity(), UpdatePhoneActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.finance_tv:
                if (mSharedPreferences.getInt("account_id", 000) == 000) {
                    Toast.makeText(getActivity(), "请先登录！", Toast.LENGTH_SHORT).show();
                    startActivity(CommonUtils.getloginIntent(getActivity()));
                } else {
                    Intent codeintent = new Intent(getActivity(), GoodsCodeActivity.class);
                    startActivity(codeintent);
                }
                break;
            case R.id.payback_tv:
                if (mSharedPreferences.getInt("account_id", 000) == 000) {
                    Toast.makeText(getActivity(), "请先登录！", Toast.LENGTH_SHORT).show();
                    startActivity(CommonUtils.getloginIntent(getActivity()));
                } else {
                    Intent intent = new Intent(getActivity(), MyTransferListActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.shopping_tv:
                if (mSharedPreferences.getInt("account_id", 000) == 000) {
                    Toast.makeText(getActivity(), "请先登录！", Toast.LENGTH_SHORT).show();
                    startActivity(CommonUtils.getloginIntent(getActivity()));
                } else {
                    Intent marketintent = new Intent(getActivity(), GoodsMarketActivity.class);
                    startActivity(marketintent);
                }
                break;
            case R.id.card_manage_tv:
                if (mSharedPreferences.getInt("account_id", 000) == 000) {
                    Toast.makeText(getActivity(), "请先登录！", Toast.LENGTH_SHORT).show();
                    startActivity(CommonUtils.getloginIntent(getActivity()));
                } else {
                    Intent cardintent = new Intent(getActivity(), CardManagerActivity.class);
                    startActivity(cardintent);
                }
                break;
            case R.id.formore_tv:
                if (mSharedPreferences.getInt("account_id", 000) == 000) {
                    Toast.makeText(getActivity(), "请先登录！", Toast.LENGTH_SHORT).show();
                    startActivity(CommonUtils.getloginIntent(getActivity()));
                } else {
                    Toast.makeText(getActivity(), "敬请期待", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.more_list_tv:
                if (mSharedPreferences.getInt("account_id", 000) == 000) {
                    Toast.makeText(getActivity(), "请先登录！", Toast.LENGTH_SHORT).show();
                    startActivity(CommonUtils.getloginIntent(getActivity()));
                } else {
                    Intent infolistintent = new Intent(getActivity(), InformationListActivity.class);
                    startActivity(infolistintent);

                }
                break;
        }
    }


}
