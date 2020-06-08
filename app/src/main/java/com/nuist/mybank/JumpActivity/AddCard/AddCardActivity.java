package com.nuist.mybank.JumpActivity.AddCard;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.nuist.mybank.Interface.Service;
import com.nuist.mybank.POJO.ResultBean.GetBankCardResult;
import com.nuist.mybank.POJO.ResultBean.ResultObjectBean;
import com.nuist.mybank.R;
import com.nuist.mybank.Utils.RetrofitManager;
import com.nuist.mybank.View.CustomActionBar;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.card.payment.CardIOActivity;
import io.card.payment.CreditCard;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class AddCardActivity extends AppCompatActivity {

    private static final int MY_SCAN_REQUEST_CODE = 1001;
    @BindView(R.id.addcard_actionbar)
    CustomActionBar addcardActionbar;
    @BindView(R.id.ac_bankcard)
    CardView acBankcard;
    @BindView(R.id.addcard_et)
    EditText addcardEt;
    @BindView(R.id.addcardconfirm_btn)
    Button addcardconfirmBtn;
    private Retrofit mRetrofit = RetrofitManager.getRetrofit();//获取Retrofit
    private Service mService = mRetrofit.create(Service.class);//获取service
    private Activity mActivity = this;
    private String Tag = "AddCardActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);
        ButterKnife.bind(this);
        addcardActionbar.setStyle("绑定银行卡");
    }
    public void isCardExist(String bc_no){
        mService.isCardExist(bc_no)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<ResultObjectBean>() {
                    @Override
                    public void accept(ResultObjectBean resultObjectBean) throws Exception {
                        if(resultObjectBean.isSuccess() == false){
                            Toast.makeText(AddCardActivity.this,resultObjectBean.getMessage(),Toast.LENGTH_SHORT).show();
                        }else{
                            Intent intent = new Intent(AddCardActivity.this,BindCardActivity.class);
                            intent.putExtra("bc_no",bc_no);
                            startActivity(intent);
                            mActivity.finish();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(Tag, "错误信息为 --》" + throwable.getMessage());
                    }
                });
    }
    public void scanCard(){
        Intent scanIntent = new Intent(this, CardIOActivity.class)
                .putExtra(CardIOActivity.EXTRA_REQUIRE_EXPIRY, false)
                .putExtra(CardIOActivity.EXTRA_REQUIRE_POSTAL_CODE, false)
                .putExtra(CardIOActivity.EXTRA_HIDE_CARDIO_LOGO, true)//去除水印
                .putExtra(CardIOActivity.EXTRA_SUPPRESS_MANUAL_ENTRY, true)//去除键盘
                .putExtra(CardIOActivity.EXTRA_LANGUAGE_OR_LOCALE, "zh-Hans");//设置提示为中文
        // MY_SCAN_REQUEST_CODE is arbitrary and is only used within this activity.
        startActivityForResult(scanIntent, MY_SCAN_REQUEST_CODE);
    }
    @OnClick({R.id.ac_bankcard, R.id.addcardconfirm_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ac_bankcard:
                scanCard();
                break;
            case R.id.addcardconfirm_btn:
                if(TextUtils.isEmpty(addcardEt.getText().toString())){
                    Toast.makeText(AddCardActivity.this,"卡号不能为空",Toast.LENGTH_SHORT).show();
                }else{
                    isCardExist(addcardEt.getText().toString());
                }
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == MY_SCAN_REQUEST_CODE) {
            String resultDisplayStr;
            if (data != null && data.hasExtra(CardIOActivity.EXTRA_SCAN_RESULT)) {
                CreditCard scanResult = data.getParcelableExtra(CardIOActivity.EXTRA_SCAN_RESULT);
                resultDisplayStr = scanResult.getRedactedCardNumber();
                addcardEt.setText(resultDisplayStr);
            }
            else {
                resultDisplayStr = "请手动输入银行卡号";
                addcardEt.setHint(resultDisplayStr);
            }

        }
    }
}
