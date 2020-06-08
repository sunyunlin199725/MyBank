package com.nuist.mybank.JumpActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.nuist.mybank.Adapter.SpinnerAdapter;
import com.nuist.mybank.Interface.Service;
import com.nuist.mybank.POJO.ResultBean.GetBankCardResult;

import com.nuist.mybank.R;
import com.nuist.mybank.Utils.QRUtil.QRActivity;
import com.nuist.mybank.Utils.QRUtil.QRCodeWithLogoUtils;
import com.nuist.mybank.Utils.RetrofitManager;
import com.nuist.mybank.View.CustomActionBar;


import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class ReceiveActivity extends AppCompatActivity {

    @BindView(R.id.receive_actionbar)
    CustomActionBar receiveActionbar;
    @BindView(R.id.QrWithLogo_iv)
    ImageView mQrWithLogoImage;
    @BindView(R.id.receive_card_spinner)
    Spinner cardSpinner;
    private Retrofit mRetrofit = RetrofitManager.getRetrofit();//获取Retrofit
    private Service mService = mRetrofit.create(Service.class);//获取service
    private List<GetBankCardResult.BankCardBean> mList = new ArrayList<>();//获得的用户银行卡集合
    private SharedPreferences mSharedPreferences;//SharedPreferences保存用户信息
    private final static int REQUEST_CODE = 1001;//二维码扫描请求码
    private String mCurrentBCno;//当前卡号
    private String Tag = "ReceiveActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive);
        ButterKnife.bind(this);
        receiveActionbar.setStyle("收款码", R.mipmap.more_white, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openContextMenu(receiveActionbar);
            }
        });
        mSharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE);
        getBankCard(mSharedPreferences.getInt("account_id",000));
        initListener();
    }


    private void initListener() {
        cardSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mCurrentBCno = mList.get(position).getBc_no();
                CreateBcWithLogo("02-"+mCurrentBCno);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    //定义ContextMenu的创建方法
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.menu, menu);

    }

    @Override
    protected void onStart() {
        super.onStart();
        registerForContextMenu(receiveActionbar);
        //注册ContextMenu
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterForContextMenu(receiveActionbar);
        //取消注册ContextMenu
    }
    //ContextMenu点击监听
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.menu_payment:
                //todo
                Intent payintent = new Intent(this,PaymentActivity.class);
                startActivity(payintent);
                this.finish();
                Log.e(Tag, "付款码");
                break;
            case R.id.menu_receive:
                //todo
                Log.e(Tag, "收款码");
                break;
            case R.id.menu_scan:
                //todo
                IntentIntegrator intentIntegrator = new IntentIntegrator(this);
                intentIntegrator.setOrientationLocked(false);
                intentIntegrator.setCaptureActivity(QRActivity.class); // 设置自定义的activity是QRActivity
                intentIntegrator.setRequestCode(REQUEST_CODE);
                intentIntegrator.initiateScan();
                break;

        }
        return super.onContextItemSelected(item);
    }
    /**
     * 获取账户所绑定银行卡
     * @param account_id
     */
    private void getBankCard(int account_id){
        mService.getBankCard(account_id)
                .observeOn(AndroidSchedulers.mainThread())//回调在主线程
                .subscribeOn(Schedulers.io())//执行在子线程
                .subscribe(new Consumer<GetBankCardResult>() {
                    @Override
                    public void accept(GetBankCardResult getBankCardResult) throws Exception {
                        if (getBankCardResult.isSuccess() == true) {
                            mList = getBankCardResult.getData();
                            Log.e(Tag,mList.toString());
                            SpinnerAdapter spinnerAdapter = new SpinnerAdapter(ReceiveActivity.this,mList);
                            cardSpinner.setAdapter(spinnerAdapter);
                        } else {

                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(Tag,"错误信息为 --》"+throwable.getMessage());
                    }
                });
    }
    /**
     * 生成带logo图像的二维码
     */
    private void CreateBcWithLogo(String url) {

        //这里的logo是系统自带的，通过BitmapFactory加载进来
        Bitmap logoBmp = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        //通过ZXing框架将地址和logo图片加到里面，生成二维码之后赋值给codeBmp;
        Bitmap codeBmp = QRCodeWithLogoUtils.createQRImage(ReceiveActivity.this, url, logoBmp);
        //最后将得到的codeBmp设置为ImageView的ImageBitmap这样图片就会显示出来了
        mQrWithLogoImage.setImageBitmap(codeBmp);
    }

    //二维码条形码扫描的回调
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            IntentResult scanResult = IntentIntegrator.parseActivityResult(resultCode, data);
            final String qrContent = scanResult.getContents();
            if(qrContent != null&&qrContent.contains("-")&&(qrContent.startsWith("01")||qrContent.startsWith("02"))){
                Toast.makeText(this, "扫描结果:" + qrContent, Toast.LENGTH_SHORT).show();
                Intent payreceiveintent = new Intent(this, PayReceiveActivity.class);
                payreceiveintent.putExtra("scanResult",qrContent);
                startActivity(payreceiveintent);
            }else{
                Toast.makeText(this, "扫描结果:" + qrContent, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
