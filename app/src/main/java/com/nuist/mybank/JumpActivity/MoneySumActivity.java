package com.nuist.mybank.JumpActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.nuist.mybank.Adapter.CardListAdapter;
import com.nuist.mybank.Adapter.OrderListAdapter;
import com.nuist.mybank.Dialog.FingerprintDialog;
import com.nuist.mybank.Interface.Service;
import com.nuist.mybank.POJO.BankCardView;
import com.nuist.mybank.POJO.ResultBean.GetAccountResult;
import com.nuist.mybank.POJO.ResultBean.GetBankCardResult;
import com.nuist.mybank.POJO.ResultBean.GetMoneyResult;
import com.nuist.mybank.POJO.ResultBean.GetOrderResult;
import com.nuist.mybank.Popupwindow.SelectPopupWindow;
import com.nuist.mybank.R;
import com.nuist.mybank.Utils.Config;
import com.nuist.mybank.Utils.FingerprintManagerUtil;
import com.nuist.mybank.Utils.RetrofitManager;
import com.nuist.mybank.View.CustomActionBar;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.hardware.fingerprint.FingerprintManagerCompat;
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

public class MoneySumActivity extends AppCompatActivity {

    @BindView(R.id.moneysum_actionbar)
    CustomActionBar moneysumActionbar;
    @BindView(R.id.sum_eye_iv)
    ImageView sumEyeIv;
    @BindView(R.id.sum_querymoney_tv)
    TextView sumQuerymoneyTv;
    @BindView(R.id.sum_money_tv)
    TextView sumMoneyTv;
    @BindView(R.id.sum_recyclerview)
    RecyclerView mRecyclerView;
    private CardListAdapter mCardListAdapter;
    private SharedPreferences mSharedPreferences;//SharedPreferences保存用户信息
    private Retrofit mRetrofit = RetrofitManager.getRetrofit();//获取Retrofit
    private Service mService = mRetrofit.create(Service.class);//获取service
    private List<GetBankCardResult.BankCardBean> mList;
    private SelectPopupWindow menuWindow;//输入密码弹窗；
    private GetAccountResult.AccountBean mCurrentAccount;//当前用户
    private Boolean showMoney = true;
    private Double accountmoney = 0.0;
    private String Tag = "MoneySumActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money_sum);
        ButterKnife.bind(this);
        moneysumActionbar.setStyle("余额查询");
        mSharedPreferences = getSharedPreferences("user_info", Context.MODE_PRIVATE);;
        getCurrentAccount();
        getBankCards(mSharedPreferences.getInt("account_id",000));
    }


    @OnClick({R.id.sum_eye_iv, R.id.sum_querymoney_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.sum_eye_iv:
                getAndChangeMoney();
                break;
            case R.id.sum_querymoney_tv:
                showPopupPwdwindow();
                break;
        }
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
                            sumMoneyTv.setText(accountmoney+"元");
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
    /**
     * 隐藏获取账户总金额
     **/
    private void getAndChangeMoney() {
        if(accountmoney > 0){
            if (showMoney == true) {
                sumEyeIv.setImageResource(R.mipmap.biyan);
                sumMoneyTv.setText("* * * *");
                showMoney = false;
            } else {
                sumEyeIv.setImageResource(R.mipmap.yanjing);
                sumMoneyTv.setText(accountmoney+"元");
                showMoney = true;
            }
        }else{
            if (showMoney == true) {
                sumEyeIv.setImageResource(R.mipmap.biyan);
                sumMoneyTv.setText("* * * *");
                showMoney = false;
            } else {
                sumEyeIv.setImageResource(R.mipmap.yanjing);
                sumMoneyTv.setText("- - - -");
                showMoney = true;
            }
        }
    }

    private void showList(boolean isVertical, boolean isReverse, List<GetBankCardResult.BankCardBean> mData) {
        //设置Recyclerview设置布局管理器
        LinearLayoutManager manager = new LinearLayoutManager(this);
        //设置水平还是垂直
        manager.setOrientation(isVertical ? LinearLayoutManager.VERTICAL : LinearLayoutManager.HORIZONTAL);
        //设置正向还是反向
        manager.setReverseLayout(isReverse);

        mRecyclerView.setLayoutManager(manager);
        //设置adapter
        mCardListAdapter = new CardListAdapter(R.layout.item_cardlist_view,mData);
        //更换动画效果
        mCardListAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        //设置Recyclerview
        mRecyclerView.setAdapter(mCardListAdapter);
        //设置监听
        initListViewListener();
    }

    private void initListViewListener() {
       mCardListAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
           @Override
           public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
               showPopupPwdwindow(view,position);
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
            Toast.makeText(this, "请先登录！", Toast.LENGTH_SHORT).show();
        }else{
            mService.getCurrentAccount(account_id)
                    .observeOn(AndroidSchedulers.mainThread())//回调在主线程
                    .subscribeOn(Schedulers.io())//执行在子线程
                    .subscribe(new Consumer<GetAccountResult>() {
                        @Override
                        public void accept(GetAccountResult getAccountResult) throws Exception {
                            if(getAccountResult.isSuccess() == true){
                                mCurrentAccount = getAccountResult.getData();
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
    /**
     * 获取银行卡
     * @param account_id
     */
    private void getBankCards(int account_id) {
        mService.getBankCard(account_id)
                .observeOn(AndroidSchedulers.mainThread())//回调在主线程
                .subscribeOn(Schedulers.io())//执行在子线程
                .subscribe(new Consumer<GetBankCardResult>() {
                    @Override
                    public void accept(GetBankCardResult getBankCardResult) throws Exception {
                        if (getBankCardResult.isSuccess() == true) {
                            mList = getBankCardResult.getData();
                            showList(true,false,mList);
                        } else {
                           Toast.makeText(MoneySumActivity.this, getBankCardResult.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(Tag,"错误信息为 --》"+throwable.getMessage());
                    }
                });
    }
       //打开输入密码的对话框
    public void showPopupPwdwindow(View view, int position) {

        menuWindow = new SelectPopupWindow(this, new SelectPopupWindow.OnPopWindowClickListener() {
            @Override
            public void onPopWindowClickListener(String psw, boolean complete) {
                if (complete) {
                    String bcpwd = mList.get(position).getBc_pwd();
                    if (bcpwd.equals(psw)) {
                        TextView money = view.findViewById(R.id.clmoney_tv);
                        money.setText(mList.get(position).getBc_money()+"元");
                    }else{
                        Toast.makeText(MoneySumActivity.this,"密码错误！",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        Rect rect = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        int winHeight = getWindow().getDecorView().getHeight();
        menuWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, winHeight - rect.bottom);

    }
    public void showPopupPwdwindow() {
        menuWindow = new SelectPopupWindow(this, new SelectPopupWindow.OnPopWindowClickListener() {
            @Override
            public void onPopWindowClickListener(String psw, boolean complete) {
                if (complete) {
                    String str = mCurrentAccount.getUser_idcard();
                    String bcpwd = str.substring(str.length()-6);
                    if (bcpwd.equals(psw)) {
                        getAccountMoney(mSharedPreferences.getInt("account_id",000));
                    }else{
                        Toast.makeText(MoneySumActivity.this,"输入有误！",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        Rect rect = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        int winHeight = getWindow().getDecorView().getHeight();
        menuWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, winHeight - rect.bottom);
        menuWindow.setTiptext("输入身份证后六位");
    }


}
