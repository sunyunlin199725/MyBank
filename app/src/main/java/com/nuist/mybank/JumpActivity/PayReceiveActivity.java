package com.nuist.mybank.JumpActivity;

import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.nuist.mybank.Adapter.SpinnerAdapter;
import com.nuist.mybank.Interface.Service;
import com.nuist.mybank.POJO.AccountInfo.Transfer;
import com.nuist.mybank.POJO.ResultBean.GetAccountFromBcnoResult;
import com.nuist.mybank.POJO.ResultBean.GetBankCardResult;
import com.nuist.mybank.POJO.ResultBean.ResultObjectBean;
import com.nuist.mybank.Popupwindow.SelectPopupWindow;
import com.nuist.mybank.R;
import com.nuist.mybank.Utils.CommonUtils;
import com.nuist.mybank.Utils.RetrofitManager;
import com.nuist.mybank.View.CustomActionBar;

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

public class PayReceiveActivity extends AppCompatActivity {

    @BindView(R.id.payreceive_actionbar)
    CustomActionBar payreceiveActionbar;
    @BindView(R.id.tiptext)
    TextView tiptext;
    @BindView(R.id.money_et)
    EditText moneyEt;
    @BindView(R.id.beizhutext_et)
    EditText beizhutextEt;
    @BindView(R.id.btn_tijiao)
    Button btnTijiao;
    @BindView(R.id.card_spinner)
    Spinner cardSpinner;
    private String[] mResults;//请求类型和卡号
    private GetAccountFromBcnoResult.AccountBean DestAccount;
    private Retrofit mRetrofit = RetrofitManager.getRetrofit();//获取Retrofit
    private Service mService = mRetrofit.create(Service.class);//获取service
    private SharedPreferences mSharedPreferences;//SharedPreferences保存用户信息
    private List<GetBankCardResult.BankCardBean> mList = new ArrayList<>();//获得的用户银行卡集合
    private SelectPopupWindow menuWindow;//输入密码弹窗；
    private int mCurrentposition;//当前选中的银行卡序号
    private String Tag = "PayReceiveActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_receive);
        ButterKnife.bind(this);
        payreceiveActionbar.setStyle("收付款");
        mSharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE);
        getBankCard(mSharedPreferences.getInt("account_id",000));
        initView();

    }

    private void initView() {
        String scanResult = getIntent().getStringExtra("scanResult");
        mResults = scanResult.split("-");
        getAcName(mResults[1]);
        cardSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               mCurrentposition = position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                 //todo
            }
        });
    }

    /**
     * 获取银行卡所绑定的账户，并更新UI，设置提醒
     * @param bc_no
     */
    private void getAcName(String bc_no) {
        mService.getAcnameFromBcno(bc_no)
                .observeOn(AndroidSchedulers.mainThread())//回调在主线程
                .subscribeOn(Schedulers.io())//执行在子线程
                .subscribe(new Consumer<GetAccountFromBcnoResult>() {
                    @Override
                    public void accept(GetAccountFromBcnoResult getAccountFromBcnoResult) throws Exception {
                        if (getAccountFromBcnoResult.isSuccess() == true) {
                            DestAccount = getAccountFromBcnoResult.getData();
                            String str = mResults[1].substring(mResults[1].length() - 4);
                            if (mResults[0].equals("01")) {
                                tiptext.setText("您正收取个人账户 " + CommonUtils.nameDesensitization(DestAccount.getAccount_name()) + " [" + str + "] 金额");
                            } else if (mResults[0].equals("02")) {
                                tiptext.setText("您正在向个人账户 " + CommonUtils.nameDesensitization(DestAccount.getAccount_name()) + " [" + str + "] 支付");
                            } else {
                                tiptext.setText("");
                            }
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(Tag, "错误信息为 --》" + throwable.getMessage());
                    }
                });
    }
    /**
     * 获取账户所绑定银行卡
     * @param account_id
     */
    private void getBankCard(int account_id){
        mService.getBankCard(account_id)
                .observeOn(AndroidSchedulers.mainThread())//回调在主线程
                .subscribeOn(Schedulers.io())//执行在子线程
                .subscribe(new Consumer<GetBankCardResult>() {
                    @Override
                    public void accept(GetBankCardResult getBankCardResult) throws Exception {
                        if (getBankCardResult.isSuccess() == true) {
                            mList = getBankCardResult.getData();
                            Log.e(Tag,mList.toString());
                            SpinnerAdapter spinnerAdapter = new SpinnerAdapter(PayReceiveActivity.this,mList);
                            cardSpinner.setAdapter(spinnerAdapter);
                        } else {
                          //todo
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(Tag,"错误信息为 --》"+throwable.getMessage());
                    }
                });
    }
    @OnClick({R.id.beizhutext_et, R.id.btn_tijiao})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.beizhutext_et:
                break;
            case R.id.btn_tijiao:
                //todo
                judge();
                break;
        }
    }

    /**
     * 判断支付模式，并封装请求数据
     */
    public void judge(){
        Transfer transfer = new Transfer();
        if(TextUtils.isEmpty(moneyEt.getText().toString())){//金额输入框为空
            Toast.makeText(PayReceiveActivity.this,"金额不能为空！",Toast.LENGTH_SHORT).show();
        }else{//金额不为空
            if (mResults[0].equals("01")) {//收取账户金额
                if(Float.valueOf(moneyEt.getText().toString()) > 5000.0){
                    Toast.makeText(PayReceiveActivity.this,"超过单笔限额！",Toast.LENGTH_SHORT).show();
                }else{
                    transfer.setPay_no(mResults[1]);//付款卡
                    transfer.setAccount_id(DestAccount.getAccount_id());//付款账户ID
                    transfer.setCollection_no(mList.get(mCurrentposition).getBc_no());//收款卡
                    transfer.setCollection_name(mSharedPreferences.getString("username",null));
                    transfer.setTransfer_money(Float.valueOf(moneyEt.getText().toString()));
                    transfer.setTransfer_text(beizhutextEt.getText().toString());
                    //todo
                    addTransfer(transfer);
                    Toast.makeText(PayReceiveActivity.this,"正在处理...",Toast.LENGTH_SHORT).show();
                }

            } else if (mResults[0].equals("02")) {//支付账户金额
                //超过单笔限额
                if(Float.valueOf(moneyEt.getText().toString()) > 5000.0){
                    Toast.makeText(PayReceiveActivity.this,"超过单笔限额！",Toast.LENGTH_SHORT).show();
                }else if(Float.valueOf(moneyEt.getText().toString()) > mList.get(mCurrentposition).getBc_money()){//银行卡余额不足
                    Toast.makeText(PayReceiveActivity.this,"账户余额不足！",Toast.LENGTH_SHORT).show();
                }else{
                    showPopupPwdwindow();//输入支付密码
                }
            } else {//其他待开发的模式
                //todo

            }
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
                String bcpwd = mList.get(mCurrentposition).getBc_pwd();//当前银行卡密码
                if (bcpwd.equals(psw)) {
                    Transfer transfer = new Transfer();
                    transfer.setPay_no(mList.get(mCurrentposition).getBc_no());//付款卡
                    transfer.setAccount_id(mSharedPreferences.getInt("account_id",000));//付款账户ID
                    transfer.setCollection_no(mResults[1]);//收款卡
                    transfer.setCollection_name(DestAccount.getAccount_name());//收款人姓名
                    transfer.setTransfer_money(Float.valueOf(moneyEt.getText().toString()));//收款金额
                    transfer.setTransfer_text(beizhutextEt.getText().toString());//备注信息
                    addTransfer(transfer);
                    Toast.makeText(PayReceiveActivity.this,"正在处理...",Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(PayReceiveActivity.this, "密码错误！", Toast.LENGTH_SHORT).show();
                }

            }

        }
    };
    /**
     * 增加转账记录
     * @param transfer
     */
    public void addTransfer(Transfer transfer){
        mService.addTransfer(transfer)
                .observeOn(AndroidSchedulers.mainThread())//回调在主线程
                .subscribeOn(Schedulers.io())//执行在子线程
                .subscribe(new Consumer<ResultObjectBean>() {
                    @Override
                    public void accept(ResultObjectBean resultObjectBean) throws Exception {
                        Toast.makeText(PayReceiveActivity.this,resultObjectBean.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(Tag,"错误信息---》"+throwable.getMessage());
                    }
                });
    }
}
