package com.nuist.mybank.Dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.nuist.mybank.R;


public class FingerprintDialog  extends AlertDialog implements View.OnClickListener {

    private TextView dismiss_tv;//取消按钮

    public FingerprintDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fingerprintdialog);
        dismiss_tv = findViewById(R.id.dismiss_tv);
        dismiss_tv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.dismiss_tv:
                dismiss();
                break;
        }
    }
}
