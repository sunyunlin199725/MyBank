package com.nuist.mybank.JumpActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nuist.mybank.Interface.Service;
import com.nuist.mybank.POJO.ResultBean.GetInfoDetailResult;
import com.nuist.mybank.POJO.ResultBean.GetInfoResult;
import com.nuist.mybank.R;
import com.nuist.mybank.Utils.Config;
import com.nuist.mybank.Utils.RetrofitManager;
import com.nuist.mybank.View.CustomActionBar;
import com.squareup.picasso.Picasso;

import java.sql.Timestamp;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class InformationDetailActivity extends AppCompatActivity {

    @BindView(R.id.infodetail_actionbar)
    CustomActionBar infodetailActionbar;
    @BindView(R.id.infopic_iv)
    ImageView infopicIv;
    @BindView(R.id.infobankpic_iv)
    ImageView infobankpicIv;
    @BindView(R.id.infoname_tv)
    TextView infonameTv;
    @BindView(R.id.infobankname_tv)
    TextView infobanknameTv;
    @BindView(R.id.infodescribe_tv)
    TextView infodescribeTv;
    @BindView(R.id.infotime_tv)
    TextView infotimeTv;
    @BindView(R.id.confirm_btn)
    Button confirmBtn;
    private Retrofit mRetrofit = RetrofitManager.getRetrofit();//获取Retrofit
    private Service mService = mRetrofit.create(Service.class);//获取service
    private SharedPreferences mSharedPreferences;//SharedPreferences保存用户信息
    private GetInfoDetailResult.infoBean mInfoBean;//获取到的数据
    private String Tag = "InformationDetailActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_detail);
        ButterKnife.bind(this);
        infodetailActionbar.setStyle("公告详情");
        mSharedPreferences = this.getSharedPreferences("user_info", Context.MODE_PRIVATE);
        getInfoFromtitle(getIntent().getStringExtra("info_title"));
    }

    private void getInfoFromtitle(String info_title) {
        mService.getInfoFromTitle(info_title)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<GetInfoDetailResult>() {
                    @Override
                    public void accept(GetInfoDetailResult getInfoDetailResult) throws Exception {
                        if(getInfoDetailResult.isSuccess() == true){
                            mInfoBean = getInfoDetailResult.getData();
                            Picasso.with(InformationDetailActivity.this).load(Config.baseurl + mInfoBean.getInfo_pic()).into(infopicIv);
                            if(mInfoBean.getBank().getBank_name().equals("中国邮政储蓄银行")){
                                infobankpicIv.setImageResource(R.drawable.ic_youzheng);
                            }else if(mInfoBean.getBank().getBank_name().equals("中国农业银行")){
                                infobankpicIv.setImageResource(R.drawable.ic_nongye);
                            }else if(mInfoBean.getBank().getBank_name().equals("中国建设银行")){
                                infobankpicIv.setImageResource(R.drawable.ic_jianshe);
                            }else{
                                infobankpicIv.setImageResource(R.drawable.ic_nongshang);
                            }
                            infonameTv.setText(mInfoBean.getInfo_title());
                            infobanknameTv.setText(mInfoBean.getBank().getBank_name());
                            infodescribeTv.setText(mInfoBean.getInfo_text());
                            Timestamp timestamp = new Timestamp(mInfoBean.getInfo_time());
                            infotimeTv.setText(timestamp.toString());
                        }else{
                            //todo
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(Tag, "错误信息 -->"+throwable.getMessage());
                    }
                });
    }

    @OnClick(R.id.confirm_btn)
    public void onViewClicked() {
        this.finish();
    }
}
