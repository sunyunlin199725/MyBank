package com.nuist.mybank.JumpActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.nuist.mybank.R;
import com.nuist.mybank.Utils.SysApplication;
import com.nuist.mybank.View.CustomActionBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingActivity extends AppCompatActivity {

    @BindView(R.id.setting_actionbar)
    CustomActionBar settingActionbar;
    @BindView(R.id.infomodify_cv)
    CardView infomodifyCv;
    @BindView(R.id.advice_cv)
    CardView adviceCv;
    @BindView(R.id.day_cv)
    CardView dayCv;
    @BindView(R.id.help_cv)
    CardView helpCv;
    @BindView(R.id.settingcommit_btn)
    Button settingcommitBtn;
    private SharedPreferences mSharedPreferences;//SharedPreferences保存用户信息
    private SharedPreferences.Editor mEditor;//用于向SharedPreferences中添加数据
    private Activity mActivity = this;//当前Activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        settingActionbar.setStyle("设置");
        mSharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE);
    }

    @OnClick({R.id.infomodify_cv, R.id.advice_cv, R.id.day_cv, R.id.help_cv, R.id.settingcommit_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.infomodify_cv:
                Intent intent = new Intent(SettingActivity.this,UpdateInfoActivity.class);
                startActivity(intent);
                break;
            case R.id.advice_cv:
                Intent feedBackintent = new Intent(SettingActivity.this,FeedBackActivity.class);
                startActivity(feedBackintent);
                break;
            case R.id.day_cv:
                Intent finaintent = new Intent(SettingActivity.this,FinacalendarActivity.class);
                startActivity(finaintent);
                break;
            case R.id.help_cv:
                Intent helpintent = new Intent(SettingActivity.this,HelpActivity.class);
                startActivity(helpintent);
                break;
            case R.id.settingcommit_btn:
                Toast.makeText(this,"退出成功！欢迎使用！",Toast.LENGTH_SHORT).show();
                mEditor = mSharedPreferences.edit();
                mEditor.remove("account_id");
                mEditor.remove("username");
                mEditor.commit();
                mActivity.finish();
                //SysApplication.getInstance().exit();
                break;
        }
    }
}
