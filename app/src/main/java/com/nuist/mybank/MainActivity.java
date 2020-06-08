package com.nuist.mybank;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

import android.Manifest;
import android.content.ComponentCallbacks;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.nuist.mybank.Fragments.Fragment1;
import com.nuist.mybank.Fragments.Fragment2;
import com.nuist.mybank.Fragments.Fragment3;
import com.nuist.mybank.Fragments.Fragment4;
import com.nuist.mybank.Interface.DataToFragment2;
import com.nuist.mybank.Interface.Service;
import com.nuist.mybank.JumpActivity.PayReceiveActivity;
import com.nuist.mybank.POJO.ResultBean.GetChartResult;
import com.nuist.mybank.Utils.RetrofitManager;
import com.nuist.mybank.Utils.SysApplication;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView navigation;  //底部导航栏
    private Fragment1 fragment1; //碎片一，首页
    private Fragment2 fragment2; //碎片二，商家优惠
    private Fragment3 fragment3;  //碎片三，财富管理
    private Fragment4 fragment4;  //碎片四，我的信息
    private Fragment[] fragments;  //碎片数组
    private int lastfragment;//用于记录上个选择的Fragment
    private final static int REQUEST_CODE = 1001;//二维码扫描请求码
    private SharedPreferences mSharedPreferences;//SharedPreferences保存用户信息
    private Retrofit mRetrofit = RetrofitManager.getRetrofit();//获取Retrofit
    private Service mService = mRetrofit.create(Service.class);//获取service
    private List<GetChartResult.ChartBean> mChartBeans;//得到的mchart
    private DataToFragment2 mDataToFragment2;
    private String Tag = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Picasso.with(this).setLoggingEnabled(true);
        getAllChart();
        //可以在控制台显示Picasso运行的日志
        mSharedPreferences = getSharedPreferences("user_info", MODE_PRIVATE);
        SysApplication.getInstance().addActivity(this);
        initView();

    }

    private void initView() {
        fragment1 = new Fragment1();
        fragment2 = new Fragment2();
        fragment3 = new Fragment3();
        fragment4 = new Fragment4();
        mDataToFragment2 = fragment2;
        fragments=new Fragment[]{fragment1,fragment2,fragment3,fragment4};
        lastfragment=0;

        getSupportFragmentManager().beginTransaction().replace(R.id.main_view, fragment1).show(fragment1).commit();
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }
    //选择底部导航栏项目的监听
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    if(lastfragment!=0){
                        switchFrame(lastfragment,0);
                        lastfragment=0;
                    }
                    return true;
                case R.id.navigation_discover:

                    if(lastfragment!=1){
                        switchFrame(lastfragment,1);
                        lastfragment=1;
                        mDataToFragment2.getChartData(mChartBeans);
                    }
                    return true;
                case R.id.navigation_wealth:

                    if(lastfragment!=2){
                        switchFrame(lastfragment,2);
                        lastfragment=2;
                    }
                    return true;
                case R.id.navigation_info:

                    if(lastfragment!=3){
                        switchFrame(lastfragment,3);
                        lastfragment=3;
                    }
                    return true;
            }
            return false;
        }
    };
    
    public void switchFrame(int lastfragment,int index){
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.hide(fragments[lastfragment]);
        if(fragments[index].isAdded()==false){
            fragmentTransaction.add(R.id.main_view,fragments[index]);
        }
        fragmentTransaction.show(fragments[index]).commitAllowingStateLoss();
    }

    //二维码条形码扫描的回调
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            IntentResult scanResult = IntentIntegrator.parseActivityResult(resultCode, data);
            final String qrContent = scanResult.getContents();
            if(qrContent != null&&qrContent.contains("-")&&(qrContent.startsWith("01")||qrContent.startsWith("02"))){
                Intent payreceiveintent = new Intent(this, PayReceiveActivity.class);
                payreceiveintent.putExtra("scanResult",qrContent);
                startActivity(payreceiveintent);
            }else{
                Toast.makeText(this, "扫描结果:" + qrContent, Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 获取所有轮播图照片
     */
    public void getAllChart(){
        Log.e(Tag,"getAllChart...");
        mService.getAllChart()
                .observeOn(AndroidSchedulers.mainThread())//回调在主线程
                .subscribeOn(Schedulers.io())//执行在子线程
                .subscribe(new Consumer<GetChartResult>() {
                    @Override
                    public void accept(GetChartResult getChartResult) throws Exception {
                        mChartBeans = getChartResult.getData();//得到请求获得的数据
                        Log.e(Tag,mChartBeans.size()+"");
                        Log.e(Tag, getChartResult.toString());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(Tag, throwable.toString());
                    }
                });
    }

}
