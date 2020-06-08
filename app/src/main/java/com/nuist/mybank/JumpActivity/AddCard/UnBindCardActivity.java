package com.nuist.mybank.JumpActivity.AddCard;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nuist.mybank.Interface.Service;
import com.nuist.mybank.POJO.AccountInfo.BankCard;
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
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class UnBindCardActivity extends AppCompatActivity {

    @BindView(R.id.unbindcard_actionbar)
    CustomActionBar unbindcardActionbar;
    @BindView(R.id.ubcitem_bankname)
    TextView ubcitemBankname;
    @BindView(R.id.ubcitem_bankno)
    TextView ubcitemBankno;
    @BindView(R.id.ubc_cardView)
    CardView ubcCardView;
    @BindView(R.id.uacname_tv)
    TextView uacnameTv;
    @BindView(R.id.uacid_tv)
    TextView uacidTv;
    @BindView(R.id.ubcconfirm_btn)
    Button ubcconfirmBtn;
    @BindView(R.id.ubcitem_banklogo)
    ImageView ubcitemBanklogo;
    private Retrofit mRetrofit = RetrofitManager.getRetrofit();//获取Retrofit
    private Service mService = mRetrofit.create(Service.class);//获取service
    private SharedPreferences mSharedPreferences;//SharedPreferences保存用户信息
    private GetAccountResult.AccountBean mCurrentAccount;//当前用户
    private GetBCResult.BcBean mBankCard;//当前银行卡
    private SelectPopupWindow menuWindow;//输入密码弹窗；
    private String Tag = "UnBindCardActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_un_bind_card);
        ButterKnife.bind(this);
        unbindcardActionbar.setStyle("解绑银行卡");
        mSharedPreferences = this.getSharedPreferences("user_info", Context.MODE_PRIVATE);
        getCurrentAccount(mSharedPreferences.getInt("account_id", 000));
        getBankCard(getIntent().getStringExtra("bc_no"));
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
                                uacnameTv.setText(CommonUtils.nameDesensitization(mCurrentAccount.getAccount_name()));
                            }
                            if (mCurrentAccount.getUser_idcard() != null) {
                                uacidTv.setText(CommonUtils.hideIdCard(mCurrentAccount.getUser_idcard(), 1, 1));
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
    private void getBankCard(String bc_no) {
        mService.getBankCardFromBcno(bc_no)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<GetBCResult>() {
                    @Override
                    public void accept(GetBCResult getBCResult) throws Exception {
                        if (getBCResult.isSuccess() == true) {
                            mBankCard = getBCResult.getData();
                            ubcitemBankno.setText(CommonUtils.hideCardNo(mBankCard.getBc_no()));
                            ubcitemBankname.setText(mBankCard.getBank().getBank_name());
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
            ubcCardView.setCardBackgroundColor(this.getResources().getColor(R.color.coloryouzhengcard));
            ubcitemBanklogo.setImageResource(R.drawable.ic_youzheng);
        } else if (bank_name.equals("中国农业银行")) {
            ubcCardView.setCardBackgroundColor(this.getResources().getColor(R.color.colornongyecard));
            ubcitemBanklogo.setImageResource(R.drawable.ic_nongye);
        } else if (bank_name.equals("中国建设银行")) {
            ubcCardView.setCardBackgroundColor(this.getResources().getColor(R.color.colorjianshecard));
            ubcitemBanklogo.setImageResource(R.drawable.ic_jianshe);
        } else {
            ubcCardView.setCardBackgroundColor(this.getResources().getColor(R.color.textgrey));
            ubcitemBanklogo.setImageResource(R.drawable.ic_nongshang);
        }
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
                    unbindBankCard(bankCard);
                } else {
                    Toast.makeText(UnBindCardActivity.this, "密码错误！", Toast.LENGTH_SHORT).show();
                }

            }

        }
    };
    @OnClick(R.id.ubcconfirm_btn)
    public void onViewClicked() {
        showPopupPwdwindow();
    }
    private void unbindBankCard(BankCard bankCard) {
        mService.UnbindBankCard(bankCard)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<ResultObjectBean>() {
                    @Override
                    public void accept(ResultObjectBean resultObjectBean) throws Exception {
                        Toast.makeText(UnBindCardActivity.this,resultObjectBean.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(Tag, throwable.getMessage());
                    }
                });
    }
}
