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
import android.widget.Toast;

import com.nuist.mybank.Adapter.SpinnerAdapter;
import com.nuist.mybank.Interface.Service;
import com.nuist.mybank.POJO.AccountInfo.Transfer;
import com.nuist.mybank.POJO.ResultBean.GetBankCardResult;
import com.nuist.mybank.POJO.ResultBean.ResultObjectBean;
import com.nuist.mybank.Popupwindow.SelectPopupWindow;
import com.nuist.mybank.R;
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

public class TranferActivity extends AppCompatActivity {

    @BindView(R.id.transfer_actionbar)
    CustomActionBar transferActionbar;
    @BindView(R.id.collectionname_et)
    EditText collectionnameEt;
    @BindView(R.id.collectionno_et)
    EditText collectionnoEt;
    @BindView(R.id.card_spinner)
    Spinner cardSpinner;
    @BindView(R.id.transfermoney_et)
    EditText transfermoneyEt;
    @BindView(R.id.transfer_commit)
    Button transferCommit;
    @BindView(R.id.transfertext_et)
    EditText transfertextEt;
    private Retrofit mRetrofit = RetrofitManager.getRetrofit();//获取Retrofit
    private Service mService = mRetrofit.create(Service.class);//获取service
    private List<GetBankCardResult.BankCardBean> mList = new ArrayList<>();//获得的用户银行卡集合
    private SharedPreferences mSharedPreferences;//SharedPreferences保存用户信息
    private SelectPopupWindow menuWindow;//输入密码弹窗；
    private int mCurrentposition;//当前选中的银行卡序号
    private Boolean allowcommit = true;
    private String Tag = "TransferActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tranfer);
        ButterKnife.bind(this);
        transferActionbar.setStyle("转账");
        mSharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE);
        getBankCard(mSharedPreferences.getInt("account_id", 000));
        initListener();

    }

    private void initListener() {
        //下拉菜单选择监听
        cardSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mCurrentposition = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //转账卡号合法性判断
        collectionnoEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {//失去焦点时
                    String bc_no = collectionnoEt.getText().toString();
                    mService.isCardExist(bc_no)
                            .observeOn(AndroidSchedulers.mainThread())//回调在主线程
                            .subscribeOn(Schedulers.io())//执行在子线程
                            .subscribe(new Consumer<ResultObjectBean>() {
                                @Override
                                public void accept(ResultObjectBean resultObjectBean) throws Exception {
                                    if (resultObjectBean.isSuccess() == false) {
                                        Toast.makeText(TranferActivity.this, resultObjectBean.getMessage(), Toast.LENGTH_SHORT).show();
                                        allowcommit = false;
                                    }else{
                                        allowcommit = true;
                                    }
                                }
                            }, new Consumer<Throwable>() {
                                @Override
                                public void accept(Throwable throwable) throws Exception {
                                    Log.e(Tag,"错误信息为--->"+throwable.getMessage());
                                }
                            });

                }
            }
        });
        //交易金额合法性判断
        transfermoneyEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {//失去焦点时
                    if(!TextUtils.isEmpty(transfermoneyEt.getText().toString())){
                        if(Float.valueOf(transfermoneyEt.getText().toString()) > 5000.0){
                            Toast.makeText(TranferActivity.this,"超过单笔限额！",Toast.LENGTH_SHORT).show();
                            allowcommit = false;
                        }else if(Float.valueOf(transfermoneyEt.getText().toString()) > mList.get(mCurrentposition).getBc_money()){
                            Toast.makeText(TranferActivity.this,"账户余额不足！",Toast.LENGTH_SHORT).show();
                            allowcommit = false;
                        }else{
                            allowcommit = true;
                        }
                    }
                }
            }
        });
    }

    /**
     * 获取用户绑定的银行卡
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
                            //设置下拉弹窗
                            SpinnerAdapter spinnerAdapter = new SpinnerAdapter(TranferActivity.this, mList);
                            cardSpinner.setAdapter(spinnerAdapter);
                        } else {

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

                String bcpwd = mList.get(mCurrentposition).getBc_pwd();

                if (bcpwd.equals(psw)) {
                    Transfer transfer = new Transfer();
                    transfer.setPay_no(mList.get(mCurrentposition).getBc_no());
                    transfer.setAccount_id(mSharedPreferences.getInt("account_id", 000));
                    transfer.setCollection_no(collectionnoEt.getText().toString());
                    transfer.setCollection_name(collectionnameEt.getText().toString());
                    transfer.setTransfer_money(Float.valueOf(transfermoneyEt.getText().toString()));
                    transfer.setTransfer_text(transfertextEt.getText().toString());
                    Log.e(Tag,transfer.toString());
                    addTransfer(transfer);
                    Toast.makeText(TranferActivity.this,"正在处理...",Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(TranferActivity.this, "密码错误！", Toast.LENGTH_SHORT).show();
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
                       Toast.makeText(TranferActivity.this,resultObjectBean.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(Tag,"错误信息---》"+throwable.getMessage());
                    }
                });
    }
    @OnClick({R.id.collectionno_et, R.id.transfer_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.collectionno_et:
                break;
            case R.id.transfer_commit:
                String collectionname = collectionnameEt.getText().toString();
                String colletcionno = collectionnoEt.getText().toString();
                String payno = mList.get(mCurrentposition).getBc_no();
                String transfermoney = transfermoneyEt.getText().toString();
                if(TextUtils.isEmpty(collectionname)||TextUtils.isEmpty(colletcionno)||TextUtils.isEmpty(payno)||TextUtils.isEmpty(transfermoney)){
                    Toast.makeText(TranferActivity.this,"表单项不能为空！",Toast.LENGTH_SHORT).show();
                }else{
                    if(allowcommit == true){
                        showPopupPwdwindow();
                    }else{
                        Toast.makeText(TranferActivity.this," 表单信息存在错误！",Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }
}
