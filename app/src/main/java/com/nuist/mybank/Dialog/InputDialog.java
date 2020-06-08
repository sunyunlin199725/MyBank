package com.nuist.mybank.Dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.nuist.mybank.R;


/**
 *自定义的Dialog,拥有一个输入框
 * @author Return
 */

public class InputDialog extends AlertDialog implements View.OnClickListener {
    private EditText inputMessage;//输入框
    private Button confirm;//确定按钮
    private Button cancel;//取消按钮
    private OnEditInputFinishedListener mListener; //接口



    public interface OnEditInputFinishedListener{
        void editInputFinished(String message);
    }

    public InputDialog(Context context, OnEditInputFinishedListener mListener) {
        super(context);
        this.mListener=mListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inputdialog);
        inputMessage=this.findViewById(R.id.et_input);
        confirm=this.findViewById(R.id.btn_confirm);
        cancel=this.findViewById(R.id.btn_cancel);
        confirm.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_confirm:
                if (mListener != null) {
                    String message = inputMessage.getText().toString();
                    mListener.editInputFinished(message);
                }
                dismiss();
                break;
            case R.id.btn_cancel:
                dismiss();
                break;
        }
    }
}
