package com.nuist.mybank.JumpActivity.AddCard;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nuist.mybank.Interface.Service;
import com.nuist.mybank.JumpActivity.OrderActivity;
import com.nuist.mybank.POJO.AccountInfo.BankCard;
import com.nuist.mybank.POJO.AccountInfo.GoodsCode;
import com.nuist.mybank.POJO.AccountInfo.Ordertable;
import com.nuist.mybank.POJO.ResultBean.GetAccountResult;
import com.nuist.mybank.POJO.ResultBean.GetBCResult;
import com.nuist.mybank.POJO.ResultBean.ResultObjectBean;
import com.nuist.mybank.Popupwindow.SelectPopupWindow;
import com.nuist.mybank.R;
import com.nuist.mybank.Utils.CommonUtils;
import com.nuist.mybank.Utils.RetrofitManager;
import com.nuist.mybank.View.CustomActionBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class BindCardActivity extends AppCompatActivity {

    @BindView(R.id.bindcard_actionbar)
    CustomActionBar bindcardActionbar;
    @BindView(R.id.bcitem_banklogo)
    ImageView bcitemBanklogo;
    @BindView(R.id.bcitem_bankname)
    TextView bcitemBankname;
    @BindView(R.id.bcitem_bankno)
    TextView bcitemBankno;
    @BindView(R.id.acname_tv)
    TextView acnameTv;
    @BindView(R.id.acid_tv)
    TextView acidTv;
    @BindView(R.id.bcconfirm_btn)
    Button bcconfirmBtn;
    @BindView(R.id.bc_cardView)
    CardView bcCardView;
    private Retrofit mRetrofit = RetrofitManager.getRetrofit();//获取Retrofit
    private Service mService = mRetrofit.create(Service.class);//获取service
    private SharedPreferences mSharedPreferences;//SharedPreferences保存用户信息
    private GetAccountResult.AccountBean mCurrentAccount;//当前用户
    private GetBCResult.BcBean mBankCard;//当前银行卡
    private SelectPopupWindow menuWindow;//输入密码弹窗；
    private String Tag = "BindCardActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_card);
        ButterKnife.bind(this);
        bindcardActionbar.setStyle("绑定银行卡");
        mSharedPreferences = this.getSharedPreferences("user_info", Context.MODE_PRIVATE);
        getCurrentAccount(mSharedPreferences.getInt("account_id", 000));
        getBankCard(getIntent().getStringExtra("bc_no"));
    }

    private void getBankCard(String bc_no) {
        mService.getBankCardFromBcno(bc_no)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<GetBCResult>() {
                    @Override
                    public void accept(GetBCResult getBCResult) throws Exception {
                        if (getBCResult.isSuccess() == true) {
                            mBankCard = getBCResult.getData();
                            bcitemBankno.setText(CommonUtils.hideCardNo(mBankCard.getBc_no()));
                            bcitemBankname.setText(mBankCard.getBank().getBank_name());
                            changeStyle(mBankCard.getBank().getBank_name());
                        }

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(Tag, throwable.getMessage());
                    }
                });
    }

    private void changeStyle(String bank_name) {
        if (bank_name.equals("中国邮政储蓄银行")) {
            bcCardView.setCardBackgroundColor(this.getResources().getColor(R.color.coloryouzhengcard));
            bcitemBanklogo.setImageResource(R.drawable.ic_youzheng);
        } else if (bank_name.equals("中国农业银行")) {
            bcCardView.setCardBackgroundColor(this.getResources().getColor(R.color.colornongyecard));
            bcitemBanklogo.setImageResource(R.drawable.ic_nongye);
        } else if (bank_name.equals("中国建设银行")) {
            bcCardView.setCardBackgroundColor(this.getResources().getColor(R.color.colorjianshecard));
            bcitemBanklogo.setImageResource(R.drawable.ic_jianshe);
        } else {
            bcCardView.setCardBackgroundColor(this.getResources().getColor(R.color.textgrey));
            bcitemBanklogo.setImageResource(R.drawable.ic_nongshang);
        }
    }

    public void getCurrentAccount(int account_id) {
        mService.getCurrentAccount(account_id)
                .observeOn(AndroidSchedulers.mainThread())//回调在主线程
                .subscribeOn(Schedulers.io())//执行在子线程
                .subscribe(new Consumer<GetAccountResult>() {
                    @Override
                    public void accept(GetAccountResult getAccountResult) throws Exception {
                        mCurrentAccount = getAccountResult.getData();
                        if (getAccountResult.isSuccess() == true) {

                            if (mCurrentAccount.getAccount_name() != null) {
                                acnameTv.setText(CommonUtils.nameDesensitization(mCurrentAccount.getAccount_name()));
                            }
                            if (mCurrentAccount.getUser_idcard() != null) {
                                acidTv.setText(CommonUtils.hideIdCard(mCurrentAccount.getUser_idcard(), 1, 1));
                            }
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(Tag, throwable.getMessage());
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
                String bcpwd = mBankCard.getBc_pwd();
                if (bcpwd.equals(psw)) {
                    BankCard bankCard = new BankCard();
                    bankCard.setBc_no(mBankCard.getBc_no());
                    bankCard.setUser_id(mCurrentAccount.getAccount_id());
                    bindBankCard(bankCard);
                } else {
                    Toast.makeText(BindCardActivity.this, "密码错误！", Toast.LENGTH_SHORT).show();
                }

            }

        }
    };
    @OnClick(R.id.bcconfirm_btn)
    public void onViewClicked() {
        if(mBankCard.getUser_id() >0){
            if(mBankCard.getUser_id() == mCurrentAccount.getAccount_id()){
                Toast.makeText(BindCardActivity.this,"您已绑定此卡",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(BindCardActivity.this,"此卡已被绑定",Toast.LENGTH_SHORT).show();
            }

        }else{
            showPopupPwdwindow();

        }
    }

    private void bindBankCard(BankCard bankCard) {
        mService.bindBankCard(bankCard)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<ResultObjectBean>() {
                    @Override
                    public void accept(ResultObjectBean resultObjectBean) throws Exception {
                        Toast.makeText(BindCardActivity.this,resultObjectBean.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(Tag, throwable.getMessage());
                    }
                });
    }


}
