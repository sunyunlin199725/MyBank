package com.nuist.mybank.Fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nuist.mybank.Interface.Service;
import com.nuist.mybank.JumpActivity.HelpActivity;
import com.nuist.mybank.JumpActivity.MoneySumActivity;
import com.nuist.mybank.JumpActivity.MyOrderListActivity;
import com.nuist.mybank.JumpActivity.SecurityActivity;
import com.nuist.mybank.JumpActivity.SettingActivity;
import com.nuist.mybank.JumpActivity.UpdateInfoActivity;
import com.nuist.mybank.POJO.ResultBean.GetAccountResult;
import com.nuist.mybank.POJO.ResultBean.GetMoneyResult;
import com.nuist.mybank.Popupwindow.SelectPopupWindow;
import com.nuist.mybank.R;
import com.nuist.mybank.Utils.CircleImageView;
import com.nuist.mybank.Utils.CommonUtils;
import com.nuist.mybank.Utils.Config;
import com.nuist.mybank.Utils.RetrofitManager;
import com.squareup.picasso.Picasso;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
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
public class Fragment4 extends Fragment {


    @BindView(R.id.header_iv)
    CircleImageView headerIv;
    @BindView(R.id.username_tv)
    TextView usernameTv;
    @BindView(R.id.phone_tv)
    TextView phoneTv;
    @BindView(R.id.moreinfo_iv)
    ImageView moreinfoIv;
    @BindView(R.id.header_info)
    RelativeLayout headerInfo;
    @BindView(R.id.fragment4_card)
    CardView fragment4Card;
    @BindView(R.id.order_tv)
    TextView orderTv;
    @BindView(R.id.setting_tv)
    TextView settingTv;
    @BindView(R.id.secure_tv)
    TextView secureTv;
    @BindView(R.id.help_tv)
    TextView helpTv;
    @BindView(R.id.fragment4_card1)
    CardView fragment4Card1;
    @BindView(R.id.accounttext_tv)
    TextView accounttextTv;
    @BindView(R.id.eye_iv)
    ImageView eyeIv;
    @BindView(R.id.querymore_iv)
    ImageView querymoreIv;
    @BindView(R.id.linear)
    LinearLayout linear;
    @BindView(R.id.money_tv)
    TextView moneyTv;
    @BindView(R.id.fragment4_card2)
    CardView fragment4Card2;
    @BindView(R.id.querymoney_tv)
    TextView querymoneyTv;
    @BindView(R.id.fresh_layout_fragment4)
    SwipeRefreshLayout mRefreshLayout;
    private Boolean showMoney = true;
    private SharedPreferences mSharedPreferences;//SharedPreferences保存用户信息
    private Retrofit mRetrofit = RetrofitManager.getRetrofit();//获取Retrofit
    private Service mService = mRetrofit.create(Service.class);//获取service
    private GetAccountResult.AccountBean mCurrentAccount;//当前用户
    private SelectPopupWindow menuWindow;//输入密码弹窗；
    private double accountmoney;
    private String Tag = "Fragment4";

    public Fragment4() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fragment4, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mSharedPreferences = getActivity().getSharedPreferences("user_info", Context.MODE_PRIVATE);
        ButterKnife.bind(this, getActivity());
        //处理上拉刷新
        HandlerDownPullUpdate();
        getCurrentAccount();

    }

    @OnClick({R.id.header_info, R.id.order_tv, R.id.setting_tv, R.id.secure_tv, R.id.help_tv, R.id.eye_iv, R.id.querymore_iv, R.id.querymoney_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.header_info:
                if (mSharedPreferences.getInt("account_id", 000) == 000) {
                    Toast.makeText(getActivity(), "请先登录！", Toast.LENGTH_SHORT).show();
                    startActivity(CommonUtils.getloginIntent(getActivity()));
                } else {
                    Intent toUpdateintent = new Intent(getActivity(), UpdateInfoActivity.class);
                    startActivity(toUpdateintent);
                }
                break;
            case R.id.order_tv:
                if (mSharedPreferences.getInt("account_id", 000) == 000) {
                    Toast.makeText(getActivity(), "请先登录！", Toast.LENGTH_SHORT).show();
                    startActivity(CommonUtils.getloginIntent(getActivity()));
                } else {
                   Intent orderintent = new Intent(getActivity(), MyOrderListActivity.class);
                   startActivity(orderintent);
                }
                break;
            case R.id.setting_tv:
                if (mSharedPreferences.getInt("account_id", 000) == 000) {
                    Toast.makeText(getActivity(), "请先登录！", Toast.LENGTH_SHORT).show();
                    startActivity(CommonUtils.getloginIntent(getActivity()));
                } else {
                    Intent intent = new Intent(getActivity(), SettingActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.secure_tv:
                if (mSharedPreferences.getInt("account_id", 000) == 000) {
                    Toast.makeText(getActivity(), "请先登录！", Toast.LENGTH_SHORT).show();
                    startActivity(CommonUtils.getloginIntent(getActivity()));
                } else {
                    Intent intent = new Intent(getActivity(), SecurityActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.help_tv:
                if (mSharedPreferences.getInt("account_id", 000) == 000) {
                    Toast.makeText(getActivity(), "请先登录！", Toast.LENGTH_SHORT).show();
                    startActivity(CommonUtils.getloginIntent(getActivity()));
                } else {
                    Intent intent = new Intent(getActivity(), HelpActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.eye_iv:
                getAndChangeMoney();
                break;
            case R.id.querymore_iv:
                if (mSharedPreferences.getInt("account_id", 000) == 000) {
                    Toast.makeText(getActivity(), "请先登录！", Toast.LENGTH_SHORT).show();
                    startActivity(CommonUtils.getloginIntent(getActivity()));
                } else {
                    Intent intent = new Intent(getActivity(),MoneySumActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.querymoney_tv:
                if (mSharedPreferences.getInt("account_id", 000) == 000) {
                    Toast.makeText(getActivity(), "请先登录！", Toast.LENGTH_SHORT).show();
                    startActivity(CommonUtils.getloginIntent(getActivity()));
                } else {
                    showPopupPwdwindow();
                }
                break;
        }
    }


    /**
     * 隐藏获取账户总金额
     **/
    private void getAndChangeMoney() {
        if(accountmoney > 0){
            if (showMoney == true) {
                eyeIv.setImageResource(R.mipmap.biyan);
                moneyTv.setText("* * * *");
                showMoney = false;
            } else {
                eyeIv.setImageResource(R.mipmap.yanjing);
                moneyTv.setText(accountmoney+"元");
                showMoney = true;
            }
        }else{
            if (showMoney == true) {
                eyeIv.setImageResource(R.mipmap.biyan);
                moneyTv.setText("* * * *");
                showMoney = false;
            } else {
                eyeIv.setImageResource(R.mipmap.yanjing);
                moneyTv.setText("- - - -");
                showMoney = true;
            }
        }
    }
    //处理下拉刷新的任务
    private void HandlerDownPullUpdate() {
        mRefreshLayout.setEnabled(true);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //更新UI
                int account_id = mSharedPreferences.getInt("account_id",000);
                if (account_id == 000) {
                    mRefreshLayout.setRefreshing(false);
                    Toast.makeText(getActivity(), "请先登录！", Toast.LENGTH_SHORT).show();
                    startActivity(CommonUtils.getloginIntent(getActivity()));
                } else {
                    mService.getCurrentAccount(account_id)
                            .observeOn(AndroidSchedulers.mainThread())//回调在主线程
                            .subscribeOn(Schedulers.io())//执行在子线程
                            .subscribe(new Consumer<GetAccountResult>() {
                                @Override
                                public void accept(GetAccountResult getAccountResult) throws Exception {
                                    Log.e(Tag, getAccountResult.toString());
                                    mCurrentAccount = getAccountResult.getData();
                                    if(getAccountResult.isSuccess() == true){
                                        Picasso.with(getActivity()).load(Config.baseurl + getAccountResult.getData().getUser_header()).into(headerIv);
                                        usernameTv.setText(getAccountResult.getData().getAccount_name());
                                        phoneTv.setText(getAccountResult.getData().getUser_phone());
                                        mRefreshLayout.setRefreshing(false);
                                    }
                                }
                            }, new Consumer<Throwable>() {
                                @Override
                                public void accept(Throwable throwable) throws Exception {
                                    Log.e(Tag, throwable.getMessage());
                                    mRefreshLayout.setRefreshing(false);
                                }
                            });
                }


            }
        });
    }
    /**
     * 获取当前用户
     */
    public void getCurrentAccount(){
        //更新UI
        int account_id = mSharedPreferences.getInt("account_id",000);
        if (account_id == 000) {
            Toast.makeText(getActivity(), "请先登录！", Toast.LENGTH_SHORT).show();
        }else{
            mService.getCurrentAccount(account_id)
                    .observeOn(AndroidSchedulers.mainThread())//回调在主线程
                    .subscribeOn(Schedulers.io())//执行在子线程
                    .subscribe(new Consumer<GetAccountResult>() {
                        @Override
                        public void accept(GetAccountResult getAccountResult) throws Exception {
                            mCurrentAccount = getAccountResult.getData();
                            if(getAccountResult.isSuccess() == true){
                                if(mCurrentAccount.getUser_header() != null){
                                    Picasso.with(getActivity()).load(Config.baseurl + mCurrentAccount.getUser_header()).into(headerIv);
                                }
                                if(mCurrentAccount.getAccount_name() != null){
                                    usernameTv.setText(mCurrentAccount.getAccount_name());
                                }
                                if(mCurrentAccount.getUser_phone() != null){
                                    phoneTv.setText(mCurrentAccount.getUser_phone());
                                }

                            }
                            Log.e(Tag, mCurrentAccount.toString());
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            Log.e(Tag, throwable.toString());
                        }
                    });
        }

    }

    public void showPopupPwdwindow() {
        menuWindow = new SelectPopupWindow(getActivity(), new SelectPopupWindow.OnPopWindowClickListener() {
            @Override
            public void onPopWindowClickListener(String psw, boolean complete) {
                if (complete) {
                    String str = mCurrentAccount.getUser_idcard();
                    String bcpwd = str.substring(str.length()-6);
                    if (bcpwd.equals(psw)) {
                        getAccountMoney(mSharedPreferences.getInt("account_id",000));
                    }else{
                        Toast.makeText(getActivity(),"输入有误！",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        Rect rect = new Rect();
        getActivity().getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        int winHeight = getActivity().getWindow().getDecorView().getHeight();
        menuWindow.showAtLocation(getActivity().getWindow().getDecorView(), Gravity.BOTTOM, 0, winHeight - rect.bottom);
        menuWindow.setTiptext("输入身份证后六位");
    }

    private void getAccountMoney(int account_id) {
        mService.getAccountMoney(account_id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<GetMoneyResult>() {
                    @Override
                    public void accept(GetMoneyResult getMoneyResult) throws Exception {
                        if(getMoneyResult.isSuccess() == true){
                            accountmoney = getMoneyResult.getData();
                            moneyTv.setText(accountmoney+"元");
                        }else{
                            //todo
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(Tag, throwable.toString());
                    }
                });
    }


}
