package com.nuist.mybank.JumpActivity;


import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.nuist.mybank.Adapter.SpinnerAdapter;
import com.nuist.mybank.Interface.Service;
import com.nuist.mybank.POJO.AccountInfo.GoodsCode;
import com.nuist.mybank.POJO.AccountInfo.Ordertable;
import com.nuist.mybank.POJO.ResultBean.GetBankCardResult;
import com.nuist.mybank.POJO.ResultBean.GetGoodsFrNaResult;
import com.nuist.mybank.POJO.ResultBean.ResultObjectBean;
import com.nuist.mybank.Popupwindow.SelectPopupWindow;
import com.nuist.mybank.R;
import com.nuist.mybank.Utils.Config;
import com.nuist.mybank.Utils.RetrofitManager;
import com.nuist.mybank.View.CustomActionBar;
import com.squareup.picasso.Picasso;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class OrderActivity extends AppCompatActivity {

    @BindView(R.id.order_actionbar)
    CustomActionBar orderActionbar;
    @BindView(R.id.goods_pic)
    ImageView goodsPic;
    @BindView(R.id.goods_name)
    TextView goodsName;
    @BindView(R.id.business_name)
    TextView businessName;
    @BindView(R.id.goods_describe)
    TextView goodsDescribe;
    @BindView(R.id.public_time)
    TextView publicTime;
    @BindView(R.id.login_btn)
    Button loginBtn;
    @BindView(R.id.payment_spinner)
    Spinner paymentSpinner;
    @BindView(R.id.goods_money)
    TextView goodsMoney;
    private Retrofit mRetrofit = RetrofitManager.getRetrofit();//获取Retrofit
    private Service mService = mRetrofit.create(Service.class);//获取service
    private List<GetBankCardResult.BankCardBean> mList = new ArrayList<>();//获得的用户银行卡集合
    private SharedPreferences mSharedPreferences;//SharedPreferences保存用户信息
    private GetGoodsFrNaResult.GoodsInfoBean mGoodsInfoBean;//当前商品信息
    private SelectPopupWindow menuWindow;//输入密码弹窗；
    private int mCurrentposition;//当前卡号
    private String Tag = "OrderActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        ButterKnife.bind(this);
        mSharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE);
        getBankCard(mSharedPreferences.getInt("account_id", 000));
        getGoodsFromName(getIntent().getStringExtra("goods_name"));
        initListener();
        Log.e(Tag, getIntent().getStringExtra("goods_name"));
    }

    private void initListener() {
        orderActionbar.setStyle("商品详情");
        paymentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mCurrentposition = position;
                Log.e(Tag, mList.get(mCurrentposition).getBc_no());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void getGoodsFromName(String goods_name) {
        mService.getGoodsFromName(goods_name)
                .observeOn(AndroidSchedulers.mainThread())//回调在主线程
                .subscribeOn(Schedulers.io())//执行在子线程
                .subscribe(new Consumer<GetGoodsFrNaResult>() {
                    @Override
                    public void accept(GetGoodsFrNaResult getGoodsFrNaResult) throws Exception {
                        Log.e(Tag, getGoodsFrNaResult.getMessage());
                        if (getGoodsFrNaResult.isSuccess() == true) {
                            mGoodsInfoBean = getGoodsFrNaResult.getData();
                            goodsName.setText(getGoodsFrNaResult.getData().getGoods_name());
                            goodsDescribe.setText(getGoodsFrNaResult.getData().getGoods_describe());
                            businessName.setText(getGoodsFrNaResult.getData().getBusiness().getBusiness_name());
                            goodsMoney.setText("¥"+getGoodsFrNaResult.getData().getGoods_money());
                            Timestamp timestamp = new Timestamp(getGoodsFrNaResult.getData().getPublic_time());
                            publicTime.setText(timestamp.toString());
                            Picasso.with(OrderActivity.this).load(Config.baseurl + getGoodsFrNaResult.getData().getGoods_pic()).into(goodsPic);
                        } else {
                            //todo
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(Tag, "错误信息为 --》" + throwable.getMessage());
                    }
                });
    }

    //打开输入密码的对话框
    public void showPopupPwdwindow() {
        menuWindow = new SelectPopupWindow(this, mOnPopWindowClickListener);
        Rect rect = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        int winHeight = getWindow().getDecorView().getHeight();
        menuWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, winHeight - rect.bottom);
    }

    //弹窗监听
    SelectPopupWindow.OnPopWindowClickListener mOnPopWindowClickListener = new SelectPopupWindow.OnPopWindowClickListener() {
        @Override
        public void onPopWindowClickListener(String psw, boolean complete) {
            if (complete) {
                //当前银行卡密码
                String bcpwd = mList.get(mCurrentposition).getBc_pwd();
                Log.e(Tag, mList.get(mCurrentposition).getBc_no());
                if (bcpwd.equals(psw)) {
                    if(mList.get(mCurrentposition).getBc_money() < mGoodsInfoBean.getGoods_money()){//银行卡余额不足
                        Toast.makeText(OrderActivity.this, "银行卡余额不足！", Toast.LENGTH_SHORT).show();
                    }else{
                        //1.添加订单数据
                        Ordertable ordertable = new Ordertable();
                        ordertable.setOrder_money((float) mGoodsInfoBean.getGoods_money());
                        ordertable.setAccount_id(mSharedPreferences.getInt("account_id", 000));
                        ordertable.setBusiness_name(mGoodsInfoBean.getBusiness().getBusiness_name());
                        ordertable.setBc_no(mList.get(mCurrentposition).getBc_no());
                        addOrdertable(ordertable);

                        //2.生成取货码
                        GoodsCode goodsCode = new GoodsCode();
                        goodsCode.setGoods_name(mGoodsInfoBean.getGoods_name());
                        goodsCode.setGoods_money((float) mGoodsInfoBean.getGoods_money());
                        goodsCode.setAccount_id(mSharedPreferences.getInt("account_id", 000));
                        addGoodsCode(goodsCode);
                    }
                } else {
                    Toast.makeText(OrderActivity.this, "密码错误！", Toast.LENGTH_SHORT).show();
                }

            }

        }
    };

    /**
     * 获取账户所绑定银行卡
     *
     * @param account_id
     */
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
                            SpinnerAdapter spinnerAdapter = new SpinnerAdapter(OrderActivity.this, mList);
                            paymentSpinner.setAdapter(spinnerAdapter);
                        } else {
                            //todo
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(Tag, "错误信息为 --》" + throwable.getMessage());
                    }
                });
    }

    //添加订单数据
    private void addOrdertable(Ordertable ordertable) {
        mService.addOrdertable(ordertable)
                .observeOn(AndroidSchedulers.mainThread())//回调在主线程
                .subscribeOn(Schedulers.io())//执行在子线程
                .subscribe(new Consumer<ResultObjectBean>() {
                    @Override
                    public void accept(ResultObjectBean resultObjectBean) throws Exception {
                        if (resultObjectBean.isSuccess() == true) {
                            Toast.makeText(OrderActivity.this, resultObjectBean.getMessage(), Toast.LENGTH_SHORT).show();
                        } else {
                            //todo
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(Tag, "错误信息为 --》" + throwable.getMessage());
                    }
                });
    }

    public void addGoodsCode(GoodsCode goodsCode) {
        mService.addGoodsCode(goodsCode)
                .observeOn(AndroidSchedulers.mainThread())//回调在主线程
                .subscribeOn(Schedulers.io())//执行在子线程
                .subscribe(new Consumer<ResultObjectBean>() {
                    @Override
                    public void accept(ResultObjectBean resultObjectBean) throws Exception {
                        if (resultObjectBean.isSuccess() == true) {
                            Toast.makeText(OrderActivity.this, resultObjectBean.getMessage(), Toast.LENGTH_SHORT).show();
                        } else {
                            //todo
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(Tag, "错误信息为 --》" + throwable.getMessage());
                    }
                });
    }

    @OnClick(R.id.login_btn)
    public void onViewClicked() {
        showPopupPwdwindow();
    }

}
