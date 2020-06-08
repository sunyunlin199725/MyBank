package com.nuist.mybank.JumpActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.nuist.mybank.R;
import com.nuist.mybank.RegisterActivity;
import com.nuist.mybank.View.CustomActionBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SecurityActivity extends AppCompatActivity {

    @BindView(R.id.security_actionbar)
    CustomActionBar securityActionbar;
    @BindView(R.id.pwdmodify_cv)
    CardView pwdmodifyCv;
    @BindView(R.id.newUser_cv)
    CardView newUserCv;
    @BindView(R.id.back_btn)
    Button backBtn;
    private Activity mActivity = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security);
        ButterKnife.bind(this);
        securityActionbar.setStyle("安全中心");
    }

    @OnClick({R.id.pwdmodify_cv, R.id.newUser_cv,R.id.back_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.pwdmodify_cv:
                Intent modifypwdintent = new Intent(SecurityActivity.this,ModifyPwdActivity.class);
                startActivity(modifypwdintent);
                break;
            case R.id.newUser_cv:
                Intent registerintent = new Intent(SecurityActivity.this, RegisterActivity.class);
                startActivity(registerintent);
                break;
            case R.id.back_btn:
                mActivity.finish();
                break;
        }
    }
}
