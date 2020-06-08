package com.nuist.mybank.JumpActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.nuist.mybank.Interface.Service;
import com.nuist.mybank.POJO.AccountInfo.Account;
import com.nuist.mybank.POJO.ResultBean.GetAccountResult;
import com.nuist.mybank.POJO.ResultBean.ResultObjectBean;
import com.nuist.mybank.POJO.ResultBean.UpdateInfoResult;
import com.nuist.mybank.R;
import com.nuist.mybank.Utils.Config;
import com.nuist.mybank.Utils.RetrofitManager;
import com.nuist.mybank.View.CustomActionBar;
import com.squareup.picasso.Picasso;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class ModifyPwdActivity extends AppCompatActivity {

    @BindView(R.id.modifypwd_actionbar)
    CustomActionBar modifypwdActionbar;
    @BindView(R.id.modifyold_et)
    EditText modifyoldEt;
    @BindView(R.id.modifynew_et)
    EditText modifynewEt;
    @BindView(R.id.modifynew2_et)
    EditText modifynew2Et;
    @BindView(R.id.modifycommit_btn)
    Button modifycommitBtn;
    private Retrofit mRetrofit = RetrofitManager.getRetrofit();//获取Retrofit
    private Service mService = mRetrofit.create(Service.class);//获取service
    private GetAccountResult.AccountBean mCurrentAccount;//当前用户
    private SharedPreferences mSharedPreferences;//SharedPreferences保存用户信息
    private String Tag = "ModifyPwdActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_pwd);
        ButterKnife.bind(this);
        modifypwdActionbar.setStyle("修改密码");
        mSharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE);
        getCurrentAccount(mSharedPreferences.getInt("account_id",000));
    }
    /**
     * 获取当前用户
     * @param account_id
     */
    public void getCurrentAccount(int account_id){
        mService.getCurrentAccount(account_id)
                .observeOn(AndroidSchedulers.mainThread())//回调在主线程
                .subscribeOn(Schedulers.io())//执行在子线程
                .subscribe(new Consumer<GetAccountResult>() {
                    @Override
                    public void accept(GetAccountResult getAccountResult) throws Exception {
                        mCurrentAccount = getAccountResult.getData();
                        Log.e(Tag, mCurrentAccount.toString());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(Tag, throwable.getMessage());
                    }
                });
    }
    public void modifypwd(Account account){
        mService.modifyPwd(account)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<UpdateInfoResult>() {
                    @Override
                    public void accept(UpdateInfoResult updateInfoResult) throws Exception {
                       if (updateInfoResult.isSuccess() == true){
                           Toast.makeText(ModifyPwdActivity.this,updateInfoResult.getMessage(),Toast.LENGTH_SHORT).show();
                       }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(Tag,throwable.getMessage());
                    }
                });
    }
    @OnClick(R.id.modifycommit_btn)
    public void onViewClicked() {
        String oldpwd = modifyoldEt.getText().toString();
        String newpwd = modifynewEt.getText().toString();
        String newpwd2 = modifynew2Et.getText().toString();
        if(TextUtils.isEmpty(oldpwd)){
            Toast.makeText(ModifyPwdActivity.this,"旧密码不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(newpwd)){
            Toast.makeText(ModifyPwdActivity.this,"新密码不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(newpwd2)){
            Toast.makeText(ModifyPwdActivity.this,"请确认新密码",Toast.LENGTH_SHORT).show();
            return;
        }
        if(oldpwd.equals(mCurrentAccount.getUser_pwd())){//旧密码正确
            if(newpwd.equals(oldpwd)){
                Toast.makeText(ModifyPwdActivity.this,"新旧密码不能相同",Toast.LENGTH_SHORT).show();
            }else{
                if(newpwd.equals(newpwd2)){//两次输入相同
                    Account account = new Account();
                    account.setAccount_id(mCurrentAccount.getAccount_id());
                    account.setUser_pwd(modifynewEt.getText().toString());
                    modifypwd(account);

                }else{
                    Toast.makeText(ModifyPwdActivity.this,"两次新密码不相同",Toast.LENGTH_SHORT).show();
                }

            }
        }else{//旧密码错误
            Toast.makeText(ModifyPwdActivity.this,"旧密码错误",Toast.LENGTH_SHORT).show();
        }
    }
}
