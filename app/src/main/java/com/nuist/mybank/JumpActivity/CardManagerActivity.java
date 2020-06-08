package com.nuist.mybank.JumpActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nuist.mybank.Adapter.CardViewPagerAdapter;
import com.nuist.mybank.Dialog.FingerprintDialog;
import com.nuist.mybank.Interface.Service;
import com.nuist.mybank.JumpActivity.AddCard.AddCardActivity;
import com.nuist.mybank.JumpActivity.AddCard.UnBindCardActivity;
import com.nuist.mybank.POJO.BankCardView;
import com.nuist.mybank.POJO.ResultBean.GetBankCardResult;
import com.nuist.mybank.Popupwindow.SelectPopupWindow;
import com.nuist.mybank.R;
import com.nuist.mybank.Utils.FingerprintManagerUtil;
import com.nuist.mybank.Utils.Formatter.ScalePageTransformer;
import com.nuist.mybank.Utils.RetrofitManager;
import com.nuist.mybank.View.CustomActionBar;
import com.nuist.mybank.View.LooperPagerView.SizeUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.hardware.fingerprint.FingerprintManagerCompat;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class CardManagerActivity extends AppCompatActivity {

    @BindView(R.id.bankcard_viewpager)
    ViewPager mViewPager;
    @BindView(R.id.cardname_tv)
    TextView cardnameTv;
    @BindView(R.id.hideeye_iv)
    ImageView hideeyeIv;
    @BindView(R.id.cardquery_tv)
    TextView cardqueryTv;
    @BindView(R.id.cardmoney_tv)
    TextView cardmoneyTv;
    @BindView(R.id.cardmanage_actionbar)
    CustomActionBar cardmanageActionbar;
    @BindView(R.id.cardunBind_tv)
    TextView cardunBindTv;
    @BindView(R.id.linear)
    LinearLayout linear;
    private Boolean showMoney = true;
    private List<BankCardView> mData = new ArrayList<>();//ViewPager数据源
    private Retrofit mRetrofit = RetrofitManager.getRetrofit();//获取Retrofit
    private Service mService = mRetrofit.create(Service.class);//获取service
    private List<GetBankCardResult.BankCardBean> mList = new ArrayList<>();//获得的用户银行卡集合
    private SharedPreferences mSharedPreferences;//SharedPreferences保存用户信息
    private int Currentposition = 0;//当前页码
    private SelectPopupWindow menuWindow;//输入密码弹窗；
    private String cardmoney = "- - - -";
    private String Tag = "CardManagerActivity";
    private FingerprintDialog mDialog;
    private CardViewPagerAdapter mViewPageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_manager);
        ButterKnife.bind(this);
        cardmanageActionbar.setStyle("银行卡管理", R.mipmap.icon_add, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CardManagerActivity.this, AddCardActivity.class);
                startActivity(intent);
            }
        });
        mSharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE);
        getBankCard(mSharedPreferences.getInt("account_id", 000));


    }

    private void initViewPager(List<BankCardView> mData) {
        mViewPageAdapter = new CardViewPagerAdapter(mData);
        mViewPager.setAdapter(mViewPageAdapter);
        mViewPager.setPageTransformer(false, new ScalePageTransformer(false));
        mViewPager.setPageMargin(SizeUtils.dip2px(this, 20));
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //在滚动的时候进行回调
            }

            @Override
            public void onPageSelected(int position) {
                Currentposition = position;
                //当页面选择完成
                cardnameTv.setText(mData.get(position).getBank_name());
                cardmoneyTv.setText("- - - -");
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mViewPageAdapter.setOnItemClickListener(new CardViewPagerAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(int position) {
                Intent intent = new Intent(CardManagerActivity.this, CardPayActivity.class);
                intent.putExtra("card_no", mData.get(position).getBc_no());
                startActivity(intent);
            }
        });
    }

    @OnClick({R.id.hideeye_iv, R.id.cardquery_tv,R.id.cardunBind_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.hideeye_iv:
                getAndChangeMoney();
                break;
            case R.id.cardquery_tv:
                if (mList.size() >= 1) {
                    startVerification();
                } else {
                    Toast.makeText(CardManagerActivity.this, "您没有银行卡，请绑定", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.cardunBind_tv:
                Toast.makeText(CardManagerActivity.this, "解绑", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CardManagerActivity.this, UnBindCardActivity.class);
                intent.putExtra("bc_no",mList.get(Currentposition).getBc_no());
                startActivity(intent);
                break;
        }
    }

    /**
     * 隐藏获取账户总金额
     **/
    private void getAndChangeMoney() {
        if (showMoney == true) {
            hideeyeIv.setImageResource(R.mipmap.biyan);
            cardmoneyTv.setText("* * * *");
            showMoney = false;
        } else {
            hideeyeIv.setImageResource(R.mipmap.yanjing);
            cardmoneyTv.setText("- - - -");
            showMoney = true;
        }
    }

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
                            for (GetBankCardResult.BankCardBean bankCardBean : mList) {
                                BankCardView bankCardView = new BankCardView();
                                bankCardView.setBank_name(bankCardBean.getBank().getBank_name());
                                bankCardView.setBc_no(bankCardBean.getBc_no());
                                bankCardView.setBc_type(bankCardBean.getBc_type());
                                mData.add(bankCardView);
                            }
                            initViewPager(mData);

                        } else {
                            Toast.makeText(CardManagerActivity.this, getBankCardResult.getMessage(), Toast.LENGTH_SHORT).show();
                            BankCardView bankCardView = new BankCardView();
                            bankCardView.setBank_name("您没有绑定银行卡");
                            bankCardView.setBc_no("****************");
                            bankCardView.setBc_type('1');
                            mData.add(bankCardView);
                            initViewPager(mData);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(Tag, "错误信息为 --》" + throwable.getMessage());
                    }
                });
    }

    //调用指纹验证
    private void startVerification() {
        FingerprintManagerUtil.startFingerprinterVerification(this, new FingerprintManagerUtil.FingerprintListenerAdapter() {

            // 手机或系统不支持指纹验证时回调
            @Override
            public void onNonsupport() {
                Log.e(Tag, "onNonsupport");
                Toast.makeText(CardManagerActivity.this, "不支持指纹验证", Toast.LENGTH_SHORT).show();
            }

            // 手机支持指纹验证，但是还没有录入指纹时回调
            @Override
            public void onEnrollFailed() {
                Log.e(Tag, "onEnrollFailed");
                Toast.makeText(CardManagerActivity.this, "没有录入指纹", Toast.LENGTH_SHORT).show();
            }

            //可以进行指纹验证时回调，该方法主要作用用于在进行指纹验证之前做一些操作，比如弹出对话
            @Override
            public void onAuthenticationStart() {
                mDialog = new FingerprintDialog(CardManagerActivity.this);
                mDialog.show();
            }

            //指纹验证成功
            @Override
            public void onAuthenticationSucceeded(FingerprintManagerCompat.AuthenticationResult result) {
                mDialog.dismiss();
                Log.e(Tag, "onAuthenticationSucceeded result = [" + result + "]");
                Toast.makeText(CardManagerActivity.this, "验证成功", Toast.LENGTH_SHORT).show();
                cardmoneyTv.setText(mList.get(Currentposition).getBc_money() + "");
                cardmoney = mList.get(Currentposition).getBc_money() + "";
            }

            // 验证失败  指纹验证失败后,指纹传感器不会立即关闭指纹验证,
            // 系统会提供5次重试的机会,即调用5次onAuthenticationFailed()后,才会调用onAuthenticationError()
            @Override
            public void onAuthenticationFailed() {
                Log.e(Tag, "onAuthenticationFailed");
                Toast.makeText(CardManagerActivity.this, "验证失败", Toast.LENGTH_SHORT).show();
            }

            //验证失败的错误信息
            @Override
            public void onAuthenticationError(int errMsgId, CharSequence errString) {
                Log.i(Tag, "onAuthenticationError errMsgId = [" + errMsgId + "], errString = [" + errString + "]");
                Toast.makeText(CardManagerActivity.this, "解锁失败，请手动输入密码！", Toast.LENGTH_SHORT).show();
                mDialog.dismiss();
                showPopupPwdwindow();
            }

            // 验证帮助
            @Override
            public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
                Log.e(Tag, "onAuthenticationHelp helpMsgId = [" + helpMsgId + "], helpString = [" + helpString + "]");
                //  Toast.makeText(ZxingActivity.this, "提示: " + helpString, Toast.LENGTH_SHORT).show();

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
                //todo 在这里实现输入密码验证，并完成后续工作
                Toast.makeText(CardManagerActivity.this, "您输入的密码是" + psw, Toast.LENGTH_SHORT).show();
            }

        }
    };
}
