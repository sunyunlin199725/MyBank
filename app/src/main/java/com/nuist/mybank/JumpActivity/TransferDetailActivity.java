package com.nuist.mybank.JumpActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import com.nuist.mybank.Interface.Service;
import com.nuist.mybank.POJO.ResultBean.GetAccountResult;
import com.nuist.mybank.POJO.ResultBean.GetTransferDetailResult;
import com.nuist.mybank.R;
import com.nuist.mybank.Utils.CommonUtils;
import com.nuist.mybank.Utils.Config;
import com.nuist.mybank.Utils.RetrofitManager;
import com.nuist.mybank.View.CustomActionBar;
import com.squareup.picasso.Picasso;

import java.sql.Timestamp;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class TransferDetailActivity extends AppCompatActivity {

    @BindView(R.id.transferdetail_actionbar)
    CustomActionBar transferdetailActionbar;
    @BindView(R.id.transde_money)
    TextView transdeMoney;
    @BindView(R.id.transde_collno)
    TextView transdeCollno;
    @BindView(R.id.transde_collname)
    TextView transdeCollname;
    @BindView(R.id.transde_text)
    TextView transdeText;
    @BindView(R.id.transde_payname)
    TextView transdePayname;
    @BindView(R.id.transde_payno)
    TextView transdePayno;
    @BindView(R.id.transde_time)
    TextView transdeTime;
    @BindView(R.id.transde_id)
    TextView transdeId;
    private Retrofit mRetrofit = RetrofitManager.getRetrofit();//获取Retrofit
    private Service mService = mRetrofit.create(Service.class);//获取service
    private SharedPreferences mSharedPreferences;//SharedPreferences保存用户信息
    private GetAccountResult.AccountBean mCurrentAccount;//当前用户
    private String Tag = "TransferDetailActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_detail);
        ButterKnife.bind(this);
        transferdetailActionbar.setStyle("账单详情");
        mSharedPreferences = this.getSharedPreferences("user_info", Context.MODE_PRIVATE);
        getTransferDetail(getIntent().getStringExtra("transfer_id"));

    }

    private void getTransferDetail(String transfer_id) {
        mService.getTransferDetail(transfer_id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<GetTransferDetailResult>() {
                    @Override
                    public void accept(GetTransferDetailResult getTransferDetailResult) throws Exception {
                       if(getTransferDetailResult.isSuccess() == true){
                           transdeMoney.setText("-¥"+getTransferDetailResult.getData().getTransfer_money());
                           transdeCollno.setText(CommonUtils.hideCardNo(getTransferDetailResult.getData().getCollection_no()));
                           transdeCollname.setText(getTransferDetailResult.getData().getCollection_name());
                           transdePayno.setText(CommonUtils.hideCardNo(getTransferDetailResult.getData().getPay_no()));
                           Timestamp timestamp = new Timestamp(getTransferDetailResult.getData().getTransfer_time());
                           transdeTime.setText(timestamp.toString());
                           transdeId.setText(getTransferDetailResult.getData().getTransfer_id());
                           String text = getTransferDetailResult.getData().getTransfer_text();
                           if(TextUtils.isEmpty(text)) {
                               transdeText.setText("无");
                           }else{
                               transdeText.setText(text);
                           }
                           getAccount(getTransferDetailResult.getData().getAccount_id());
                       }else{

                       }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(Tag, "错误信息 -->"+throwable.getMessage());
                    }
                });
    }
    /**
     * 获取当前用户
     * @param account_id
     */
    public void getAccount(int account_id){
        mService.getCurrentAccount(account_id)
                .observeOn(AndroidSchedulers.mainThread())//回调在主线程
                .subscribeOn(Schedulers.io())//执行在子线程
                .subscribe(new Consumer<GetAccountResult>() {
                    @Override
                    public void accept(GetAccountResult getAccountResult) throws Exception {
                        if(getAccountResult.isSuccess() == true){
                            mCurrentAccount = getAccountResult.getData();
                            transdePayname.setText(mCurrentAccount.getAccount_name());
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
