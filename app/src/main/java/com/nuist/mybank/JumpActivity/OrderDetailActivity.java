package com.nuist.mybank.JumpActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.WriterException;
import com.nuist.mybank.Interface.Service;
import com.nuist.mybank.POJO.ResultBean.GetOrderDetailResult;
import com.nuist.mybank.POJO.ResultBean.GetOrderResult;
import com.nuist.mybank.R;
import com.nuist.mybank.Utils.CommonUtils;
import com.nuist.mybank.Utils.QRUtil.BarCodeUtils;
import com.nuist.mybank.Utils.RetrofitManager;
import com.nuist.mybank.View.CustomActionBar;

import java.sql.Timestamp;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class OrderDetailActivity extends AppCompatActivity {

    @BindView(R.id.orderdetail_actionbar)
    CustomActionBar orderdetailActionbar;
    @BindView(R.id.orderde_money)
    TextView orderdeMoney;
    @BindView(R.id.orderde_businame)
    TextView orderdeBusiname;
    @BindView(R.id.orderde_payno)
    TextView orderdePayno;
    @BindView(R.id.orderde_status)
    TextView orderdeStatus;
    @BindView(R.id.orderde_time)
    TextView orderdeTime;
    @BindView(R.id.orderde_id)
    TextView orderdeId;
    @BindView(R.id.OrderQrCode_iv)
    ImageView OrderQrCodeIv;
    @BindView(R.id.orderde_id2)
    TextView orderdeId2;
    private Retrofit mRetrofit = RetrofitManager.getRetrofit();//获取Retrofit
    private Service mService = mRetrofit.create(Service.class);//获取service
    private SharedPreferences mSharedPreferences;//SharedPreferences保存用户信息
    private String Tag = "OrderDetailActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        ButterKnife.bind(this);
        orderdetailActionbar.setStyle("订单详情");
        mSharedPreferences = this.getSharedPreferences("user_info", Context.MODE_PRIVATE);
        getOrderDetail(getIntent().getStringExtra("order_id"));
    }

    private void getOrderDetail(String order_id) {
        mService.getOrderDetail(order_id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<GetOrderDetailResult>() {
                    @Override
                    public void accept(GetOrderDetailResult getOrderDetailResult) throws Exception {
                        if(getOrderDetailResult.isSuccess() == true){
                            orderdeMoney.setText(getOrderDetailResult.getData().getOrder_money()+"元");
                            orderdeBusiname.setText(getOrderDetailResult.getData().getBusiness_name());
                            orderdePayno.setText(CommonUtils.hideCardNo(getOrderDetailResult.getData().getBc_no()));
                            String status = getOrderDetailResult.getData().getOrder_status();
                            if(status.equals("0")){
                                orderdeStatus.setText("交易成功");
                            }else{
                                orderdeStatus.setText("交易失败");
                            }
                            Timestamp timestamp = new Timestamp(getOrderDetailResult.getData().getOrder_time());
                            orderdeTime.setText(timestamp.toString());
                            orderdeId.setText(getOrderDetailResult.getData().getOrder_id());
                            orderdeId2.setText(getOrderDetailResult.getData().getOrder_id());
                            CreateBarCode(getOrderDetailResult.getData().getOrder_id());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(Tag, "错误信息 -->"+throwable.getMessage());
                    }
                });
    }
    private void CreateBarCode(String url){
        DisplayMetrics dm = getResources().getDisplayMetrics();
        int width = dm.widthPixels;
        try {
            OrderQrCodeIv.setImageBitmap(BarCodeUtils.CreateBarCode(url, width / 5 * 4, width / 5));//创建条形码
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }
}
