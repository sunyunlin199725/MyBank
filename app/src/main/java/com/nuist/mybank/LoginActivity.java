package com.nuist.mybank;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.nuist.mybank.Dialog.FingerprintDialog;
import com.nuist.mybank.Dialog.InputDialog;
import com.nuist.mybank.Interface.Service;
import com.nuist.mybank.JumpActivity.UpdateInfoActivity;
import com.nuist.mybank.POJO.ResultBean.GetAccountResult;
import com.nuist.mybank.POJO.ResultBean.LoginResult;
import com.nuist.mybank.POJO.ResultBean.ResultObjectBean;
import com.nuist.mybank.Utils.CommonUtils;
import com.nuist.mybank.Utils.FingerprintManagerUtil;
import com.nuist.mybank.Utils.RetrofitManager;
import com.nuist.mybank.View.CustomActionBar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.hardware.fingerprint.FingerprintManagerCompat;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.login_actionbar)
    CustomActionBar loginActionbar;
    @BindView(R.id.username_et)
    EditText usernameEt;
    @BindView(R.id.userpwd_et)
    EditText userpwdEt;
    @BindView(R.id.login_btn)
    Button loginBtn;
    @BindView(R.id.forgetpwd_tv)
    TextView forgetpwdTv;
    @BindView(R.id.newregister_tv)
    TextView newregisterTv;
    private GetAccountResult.AccountBean mCurrentAccount;//当前用户
    private Retrofit mRetrofit = RetrofitManager.getRetrofit();//获取Retrofit
    private Service mService = mRetrofit.create(Service.class);//获取service
    private SharedPreferences mSharedPreferences;//SharedPreferences保存用户信息
    private SharedPreferences.Editor mEditor;//用于向SharedPreferences中添加数据
    private String Tag = "LoginActivity";
    private FingerprintDialog mDialog;//验证指纹弹窗
    private Activity mActivity = this;//代表这个Activity


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        loginActionbar.setStyle("用户登录");
        mSharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE);
        initView();

    }

    private void initView() {
       usernameEt.setText(mSharedPreferences.getString("username",null));
       if(mSharedPreferences.getBoolean("useFingerprint",false)){

       }

    }

    @OnClick({R.id.login_btn, R.id.forgetpwd_tv, R.id.newregister_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_btn:
                mEditor = mSharedPreferences.edit();
                mEditor.putString("username",usernameEt.getText().toString());
                mEditor.commit();
                Log.e(Tag,"usernameEt ---->"+usernameEt.getText().toString());
                Login(usernameEt.getText().toString(),userpwdEt.getText().toString());

                break;
            case R.id.forgetpwd_tv:
                Toast.makeText(this,"请联系管理员！",Toast.LENGTH_SHORT).show();
                break;
            case R.id.newregister_tv:
                Intent registerintent = new Intent(this,RegisterActivity.class);
                startActivity(registerintent);
                break;
        }
    }

    //调用指纹验证
    private void startVerification() {
        FingerprintManagerUtil.startFingerprinterVerification(this, new FingerprintManagerUtil.FingerprintListenerAdapter() {

            // 手机或系统不支持指纹验证时回调
            @Override
            public void onNonsupport() {
                Log.e(Tag, "onNonsupport");
                Toast.makeText(LoginActivity.this, "不支持指纹验证", Toast.LENGTH_SHORT).show();
            }

            // 手机支持指纹验证，但是还没有录入指纹时回调
            @Override
            public void onEnrollFailed() {
                Log.e(Tag, "onEnrollFailed");
                Toast.makeText(LoginActivity.this, "没有录入指纹", Toast.LENGTH_SHORT).show();
            }

            //可以进行指纹验证时回调，该方法主要作用用于在进行指纹验证之前做一些操作，比如弹出对话
            @Override
            public void onAuthenticationStart() {
                mDialog = new FingerprintDialog(LoginActivity.this);
                mDialog.show();
            }
            //指纹验证成功
            @Override
            public void onAuthenticationSucceeded(FingerprintManagerCompat.AuthenticationResult result) {
                mDialog.dismiss();
                Log.e(Tag, "onAuthenticationSucceeded result = [" + result + "]");
                Toast.makeText(LoginActivity.this, "验证成功", Toast.LENGTH_SHORT).show();
            }
            // 验证失败  指纹验证失败后,指纹传感器不会立即关闭指纹验证,
            // 系统会提供5次重试的机会,即调用5次onAuthenticationFailed()后,才会调用onAuthenticationError()
            @Override
            public void onAuthenticationFailed() {
                Log.e(Tag, "onAuthenticationFailed");
                Toast.makeText(LoginActivity.this, "验证失败", Toast.LENGTH_SHORT).show();
            }
            //验证失败的错误信息
            @Override
            public void onAuthenticationError(int errMsgId, CharSequence errString) {
                Log.i(Tag, "onAuthenticationError errMsgId = [" + errMsgId + "], errString = [" + errString + "]");
                Toast.makeText(LoginActivity.this, "提示: " + errString, Toast.LENGTH_SHORT).show();
            }
            // 验证帮助
            @Override
            public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
                Log.e(Tag, "onAuthenticationHelp helpMsgId = [" + helpMsgId + "], helpString = [" + helpString + "]");
                //  Toast.makeText(ZxingActivity.this, "提示: " + helpString, Toast.LENGTH_SHORT).show();

            }
        });
    }

    //登录验证
    private void Login(String name,String pwd){
        if(!CommonUtils.isNetworkAvailable(this)){
            Toast.makeText(LoginActivity.this, "网络不可用", Toast.LENGTH_SHORT).show();
        }else{
            mService.login(name,pwd)
                    .observeOn(AndroidSchedulers.mainThread())//回调在主线程
                    .subscribeOn(Schedulers.io())//执行在子线程
                    .subscribe(new Consumer<LoginResult>() {
                        @Override
                        public void accept(LoginResult result) throws Exception {
                            Toast.makeText(LoginActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                            if(result.isSuccess() == true){
                                mEditor = mSharedPreferences.edit();
                                mEditor.putInt("account_id", result.getData());
                                mEditor.commit();
                                Log.e(Tag,"account_id --->"+result.getData());
                                mActivity.finish();
                            }

                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            Log.e(Tag,"throwable --->"+throwable.toString());
                        }
                    });
        }

    }
}
