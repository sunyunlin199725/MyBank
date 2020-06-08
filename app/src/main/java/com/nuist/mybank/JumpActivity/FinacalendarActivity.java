package com.nuist.mybank.JumpActivity;

import android.os.Bundle;

import com.nuist.mybank.R;
import com.nuist.mybank.View.CustomActionBar;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

public class FinacalendarActivity extends AppCompatActivity {

    @BindView(R.id.fina_actionbar)
    CustomActionBar finaActionbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finacalendar);
        ButterKnife.bind(this);
        finaActionbar.setStyle("金融日历");
    }
}
