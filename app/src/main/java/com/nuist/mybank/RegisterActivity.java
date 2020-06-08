package com.nuist.mybank;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.nuist.mybank.Interface.Service;
import com.nuist.mybank.POJO.AccountInfo.Account;
import com.nuist.mybank.POJO.ResultBean.ResultObjectBean;
import com.nuist.mybank.Utils.RetrofitManager;
import com.nuist.mybank.View.CustomActionBar;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.register_actionbar)
    CustomActionBar registerActionbar;
    @BindView(R.id.register_accountname_et)
    EditText registerAccountnameEt;
    @BindView(R.id.register_useridcard_et)
    EditText registerUseridcardEt;
    @BindView(R.id.register_username_et)
    EditText registerUsernameEt;
    @BindView(R.id.register_userpwd_et)
    EditText registerUserpwdEt;
    @BindView(R.id.register_userpwd2_et)
    EditText registerUserpwd2Et;
    @BindView(R.id.register_btn_commit)
    Button registerBtnCommit;
    private String Tag = "RegisterActivity";
    private Retrofit mRetrofit = RetrofitManager.getRetrofit();//获取Retrofit
    private Service mService = mRetrofit.create(Service.class);//获取service
    private Boolean agreeToRegister = true;//是否允许注册
    private Boolean pwdLegality = true;//密码是否合法
    private Activity mActivity = this;//当前Activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        registerActionbar.setStyle("新用户注册");
        initListener();
    }

    private void initListener() {
        registerUsernameEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){//失去焦点时
                    String username = registerUsernameEt.getText().toString();
                    Log.e(Tag,"用户账户为--->"+username);
                    mService.judge(username)
                            .observeOn(AndroidSchedulers.mainThread())//回调在主线程
                            .subscribeOn(Schedulers.io())//执行在子线程
                            .subscribe(new Consumer<ResultObjectBean>() {
                                @Override
                                public void accept(ResultObjectBean resultObjectBean) throws Exception {
                                    Toast.makeText(RegisterActivity.this,resultObjectBean.getMessage(),Toast.LENGTH_SHORT).show();
                                    if(resultObjectBean.isSuccess() == false){
                                        agreeToRegister = false;
                                    }else{
                                        agreeToRegister = true;
                                    }
                                }
                            }, new Consumer<Throwable>() {
                                @Override
                                public void accept(Throwable throwable) throws Exception {
                                    Log.e(Tag,"错误信息为--->"+throwable.toString());
                                }
                            });


                }
            }
        });
        registerUserpwd2Et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){//失去焦点时
                    String pwd = registerUserpwdEt.getText().toString();
                    String pwd2 = registerUserpwd2Et.getText().toString();
                    if(!pwd.equals(pwd2)){
                        Toast.makeText(RegisterActivity.this,"两次密码不相同！",Toast.LENGTH_SHORT).show();
                        pwdLegality = false;
                    }else if(pwd.length() < 5){
                        Toast.makeText(RegisterActivity.this,"密码过于简单！",Toast.LENGTH_SHORT).show();
                        pwdLegality = false;
                    }else{
                        pwdLegality = true;
                    }
                }
            }
        });
    }

    @OnClick(R.id.register_btn_commit)
    public void onViewClicked() {
        Account account = new Account();
        account.setAccount_name(registerAccountnameEt.getText().toString());
        account.setUser_idcard(registerUseridcardEt.getText().toString());
        account.setUser_header("images/header.png");
        account.setUser_name(registerUsernameEt.getText().toString());
        account.setUser_pwd(registerUserpwdEt.getText().toString());
        if(agreeToRegister == true && pwdLegality == true){
            RegisterUser(account);
        }else{
            Toast.makeText(this,"请重新检查和填写表单！",Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * 用户注册
     * @param account
     */
    private void RegisterUser(Account account) {
       mService.register(account)
               .observeOn(AndroidSchedulers.mainThread())//回调在主线程
               .subscribeOn(Schedulers.io())//执行在子线程
               .subscribe(new Consumer<ResultObjectBean>() {
                   @Override
                   public void accept(ResultObjectBean resultObjectBean) throws Exception {
                       Toast.makeText(RegisterActivity.this, resultObjectBean.getMessage(), Toast.LENGTH_SHORT).show();
                       if(resultObjectBean.isSuccess() == true){
                           mActivity.finish();
                       }
                   }
               }, new Consumer<Throwable>() {
                   @Override
                   public void accept(Throwable throwable) throws Exception {
                       Log.e(Tag,"错误信息为--->"+throwable.toString());
                   }
               });
    }


}
