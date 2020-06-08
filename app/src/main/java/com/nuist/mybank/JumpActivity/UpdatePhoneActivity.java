package com.nuist.mybank.JumpActivity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import com.nuist.mybank.POJO.ResultBean.GetBankCardResult;
import com.nuist.mybank.POJO.ResultBean.GetPhoneResult;
import com.nuist.mybank.POJO.ResultBean.ResultObjectBean;
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

public class UpdatePhoneActivity extends AppCompatActivity {

    @BindView(R.id.phone_actionbar)
    CustomActionBar phoneActionbar;
    @BindView(R.id.phone_et)
    EditText phoneEt;
    @BindView(R.id.money10_tv)
    TextView money10Tv;
    @BindView(R.id.money20_tv)
    TextView money20Tv;
    @BindView(R.id.money30_tv)
    TextView money30Tv;
    @BindView(R.id.money50_tv)
    TextView money50Tv;
    @BindView(R.id.money100_tv)
    TextView money100Tv;
    @BindView(R.id.money200_tv)
    TextView money200Tv;
    @BindView(R.id.money300_tv)
    TextView money300Tv;
    @BindView(R.id.money400_tv)
    TextView money400Tv;
    @BindView(R.id.money500_tv)
    TextView money500Tv;
    @BindView(R.id.phoneconfirm_btn)
    Button phoneconfirmBtn;
    @BindView(R.id.phone_spinner)
    Spinner phoneSpinner;
    private Retrofit mRetrofit = RetrofitManager.getRetrofit();//获取Retrofit
    private Service mService = mRetrofit.create(Service.class);//获取service
    private GetPhoneResult.PhoneBean mPhoneBean;
    private List<GetBankCardResult.BankCardBean> mList = new ArrayList<>();//获得的用户银行卡集合
    private SharedPreferences mSharedPreferences;//SharedPreferences保存用户信息
    private int mCurrentposition;//当前选中的银行卡序号
    private int lastviewid = R.id.money10_tv;
    private float money = 0;
    private String Tag = "UpdatePhoneActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_phone);
        ButterKnife.bind(this);
        phoneActionbar.setStyle("话费充值");
        mSharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE);
        getBankCard(mSharedPreferences.getInt("account_id", 000));
        initListener();
    }

    private void initListener() {
        phoneSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mCurrentposition = position;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void judgePhone() {
        String phone_number = phoneEt.getText().toString();
        mService.getPhone(phone_number)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<GetPhoneResult>() {
                    @Override
                    public void accept(GetPhoneResult getPhoneResult) throws Exception {
                        if (getPhoneResult.isSuccess() == true) {
                            mPhoneBean = getPhoneResult.getData();
                            Transfer transfer = new Transfer();
                            transfer.setPay_no(mList.get(mCurrentposition).getBc_no());
                            transfer.setAccount_id(mSharedPreferences.getInt("account_id", 000));
                            transfer.setCollection_no(mPhoneBean.getBusiness().getBusiness_cardno());
                            transfer.setCollection_name(mPhoneBean.getBusiness().getBusiness_name());
                            transfer.setTransfer_money(money);
                            transfer.setTransfer_text(CommonUtils.hidePhoneNo(mPhoneBean.getPhone_number()) + "话费充值");
                            addTransfer(transfer);
                            updatePhone(mPhoneBean.getPhone_number(),money);

                        } else {
                            Toast.makeText(UpdatePhoneActivity.this, getPhoneResult.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(Tag, "错误信息为--->" + throwable.getMessage());
                    }
                });
    }

    public void changeColor(int id) {
        TextView lastTextview;
        TextView newTextview;
        if (id == lastviewid) {
            lastTextview = findViewById(id);
            lastTextview.setTextColor(Color.RED);
        } else {
            lastTextview = findViewById(lastviewid);
            newTextview = findViewById(id);
            lastTextview.setTextColor(this.getResources().getColor(R.color.tip_text_gray));
            newTextview.setTextColor(Color.RED);
            lastviewid = id;
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
                            //设置下拉弹窗
                            SpinnerAdapter spinnerAdapter = new SpinnerAdapter(UpdatePhoneActivity.this, mList);
                            phoneSpinner.setAdapter(spinnerAdapter);
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
    public void addTransfer(Transfer transfer){
        mService.addTransfer(transfer)
                .observeOn(AndroidSchedulers.mainThread())//回调在主线程
                .subscribeOn(Schedulers.io())//执行在子线程
                .subscribe(new Consumer<ResultObjectBean>() {
                    @Override
                    public void accept(ResultObjectBean resultObjectBean) throws Exception {
                        if(resultObjectBean.isSuccess() == false){
                            Toast.makeText(UpdatePhoneActivity.this,resultObjectBean.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(Tag,"错误信息---》"+throwable.getMessage());
                    }
                });
    }
    public void updatePhone(String phone_number,double phone_money){
        mService.updatePhone(phone_number,phone_money)
                .observeOn(AndroidSchedulers.mainThread())//回调在主线程
                .subscribeOn(Schedulers.io())//执行在子线程
                .subscribe(new Consumer<ResultObjectBean>() {
                    @Override
                    public void accept(ResultObjectBean resultObjectBean) throws Exception {
                       Toast.makeText(UpdatePhoneActivity.this,resultObjectBean.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(Tag,"错误信息---》"+throwable.getMessage());
                    }
                });
    }
    @OnClick({R.id.money10_tv, R.id.money20_tv, R.id.money30_tv, R.id.money50_tv, R.id.money100_tv, R.id.money200_tv, R.id.money300_tv, R.id.money400_tv, R.id.money500_tv, R.id.phoneconfirm_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.money10_tv:
                changeColor(R.id.money10_tv);
                money = 10;
                break;
            case R.id.money20_tv:
                changeColor(R.id.money20_tv);
                money = 20;
                break;
            case R.id.money30_tv:
                changeColor(R.id.money30_tv);
                money = 30;
                break;
            case R.id.money50_tv:
                changeColor(R.id.money50_tv);
                money = 50;
                break;
            case R.id.money100_tv:
                changeColor(R.id.money100_tv);
                money = 100;
                break;
            case R.id.money200_tv:
                changeColor(R.id.money200_tv);
                money = 200;
                break;
            case R.id.money300_tv:
                changeColor(R.id.money300_tv);
                money = 300;
                break;
            case R.id.money400_tv:
                changeColor(R.id.money400_tv);
                money = 400;
                break;
            case R.id.money500_tv:
                changeColor(R.id.money500_tv);
                money = 500;
                break;
            case R.id.phoneconfirm_btn:
                if (TextUtils.isEmpty(phoneEt.getText().toString())) {
                    Toast.makeText(UpdatePhoneActivity.this, "号码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (money == 0.0) {
                    Toast.makeText(UpdatePhoneActivity.this, "请选择充值金额", Toast.LENGTH_SHORT).show();
                    return;
                }
                judgePhone();
                break;
        }
    }
}
