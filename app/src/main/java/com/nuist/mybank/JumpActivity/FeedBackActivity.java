package com.nuist.mybank.JumpActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.nuist.mybank.Interface.Service;
import com.nuist.mybank.POJO.AccountInfo.FeedBack;
import com.nuist.mybank.POJO.ResultBean.ResultObjectBean;
import com.nuist.mybank.R;
import com.nuist.mybank.Utils.RetrofitManager;
import com.nuist.mybank.View.CustomActionBar;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class FeedBackActivity extends AppCompatActivity {

    @BindView(R.id.feedBack_actionbar)
    CustomActionBar feedBackActionbar;
    @BindView(R.id.fbtext_et)
    EditText fbtextEt;
    @BindView(R.id.fbemail_et)
    EditText fbemailEt;
    @BindView(R.id.fbcommit_btn)
    Button fbcommitBtn;
    private Retrofit mRetrofit = RetrofitManager.getRetrofit();//获取Retrofit
    private Service mService = mRetrofit.create(Service.class);//获取service
    private String Tag = "FeedBackActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);
        ButterKnife.bind(this);
        feedBackActionbar.setStyle("反馈");
    }

    public void addFeedBack(FeedBack feedBack){
        mService.addFeedBack(feedBack)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<ResultObjectBean>() {
                    @Override
                    public void accept(ResultObjectBean resultObjectBean) throws Exception {
                        if(resultObjectBean.isSuccess() == true){
                            Toast.makeText(FeedBackActivity.this,resultObjectBean.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(Tag, "错误信息 -->"+throwable.getMessage());
                    }
                });
    }
    @OnClick(R.id.fbcommit_btn)
    public void onViewClicked() {
        if(TextUtils.isEmpty(fbtextEt.getText().toString())){
            Toast.makeText(FeedBackActivity.this,"请填写反馈！",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(fbemailEt.getText().toString())){
            Toast.makeText(FeedBackActivity.this,"请填写联系方式！",Toast.LENGTH_SHORT).show();
            return;
        }
        FeedBack feedBack = new FeedBack();
        feedBack.setFb_text(fbtextEt.getText().toString());
        feedBack.setFb_email(fbemailEt.getText().toString());
        addFeedBack(feedBack);
    }
}
