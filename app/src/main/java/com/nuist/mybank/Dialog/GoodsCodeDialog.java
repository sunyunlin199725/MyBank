package com.nuist.mybank.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.nuist.mybank.Interface.Service;
import com.nuist.mybank.POJO.ResultBean.GetCodesResult;
import com.nuist.mybank.POJO.ResultBean.ResultObjectBean;
import com.nuist.mybank.R;

import com.nuist.mybank.Utils.QRUtil.QRCodeUtils;
import com.nuist.mybank.Utils.RetrofitManager;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class GoodsCodeDialog extends AlertDialog implements View.OnClickListener{

    private Retrofit mRetrofit = RetrofitManager.getRetrofit();//获取Retrofit
    private Service mService = mRetrofit.create(Service.class);//获取service
    private GetCodesResult.GoodsCodeBean data;//当前收货码数据
    private Context mContext;
    private TextView goodscode;//收货码
    private ImageView imagecode;//生成二维码
    private TextView confirm;//确认收货
    private TextView delete;//删除

    public GoodsCodeDialog(Context context, GetCodesResult.GoodsCodeBean data) {
        super(context);
        this.mContext = context;
        this.data = data;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goodscodedialog);
        initView();
    }

    private void initView() {
        goodscode = findViewById(R.id.goods_code);
        imagecode = findViewById(R.id.image_code);
        confirm = findViewById(R.id.confirm_tv);
        delete = findViewById(R.id.delete_tv);
        goodscode.setText(data.getGoods_code());
        CreateaQrImage(data.getGoods_code());
        confirm.setOnClickListener(this);
        delete.setOnClickListener(this);
    }

    private void CreateaQrImage(String url) {
        DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
        int width = dm.widthPixels;
        /**content 转码内容
         *R.color.white #ffffff 背景底色
         *R.color.mainTextColor #333333 二维码绘制色
         *上方的俩种颜色设置之后，是我们常见的白色黑线
         * ivQrCode 承载二维码的Imageview
         */
        QRCodeUtils.builder(url)       //创建二维码
                .backColor(mContext.getResources().getColor(R.color.white))
                .codeColor(mContext.getResources().getColor(R.color.black))
                .codeSide(width / 5 * 3).
                into(imagecode);//用于显示的ImageView

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.confirm_tv:
                if(data.getStatus().equals("1")){
                    Toast.makeText(mContext,"已收货",Toast.LENGTH_SHORT).show();
                }else{
                    updateGoodsCode(data.getGoods_code());
                }
                dismiss();
                break;
            case R.id.delete_tv:
                deleteGoodsCode(data.getGoods_code());
                dismiss();
                break;
        }
    }

    private void deleteGoodsCode(String goods_code) {
        mService.deleteGoodsCode(goods_code)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<ResultObjectBean>() {
                    @Override
                    public void accept(ResultObjectBean resultObjectBean) throws Exception {
                        if(resultObjectBean.isSuccess()){
                            Toast.makeText(mContext,resultObjectBean.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("goodscodedialog",throwable.getMessage());
                    }
                });
    }

    private void updateGoodsCode(String goods_code) {
        mService.updateGoodsCode(goods_code)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<ResultObjectBean>() {
                    @Override
                    public void accept(ResultObjectBean resultObjectBean) throws Exception {
                        if(resultObjectBean.isSuccess()){
                            Toast.makeText(mContext,resultObjectBean.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("goodscodedialog",throwable.getMessage());
                    }
                });
    }
}
