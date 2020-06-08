package com.nuist.mybank.JumpActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;

import com.nuist.mybank.Adapter.TransactionAdapter;
import com.nuist.mybank.Interface.Service;

import com.nuist.mybank.POJO.ResultBean.GetPayResult;
import com.nuist.mybank.R;
import com.nuist.mybank.Utils.RetrofitManager;
import com.nuist.mybank.View.CustomActionBar;

import java.sql.Date;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class QueryTransaActivity extends AppCompatActivity {

    @BindView(R.id.querytransa_actionbar)
    CustomActionBar querytransaActionbar;
    @BindView(R.id.startDate_et)
    EditText startDateEt;
    @BindView(R.id.endDate_et)
    EditText endDateEt;
    @BindView(R.id.queryPay_tv)
    TextView queryPayTv;
    @BindView(R.id.queryTransa_recyclerview)
    RecyclerView mRecyclerView;

    private Retrofit mRetrofit = RetrofitManager.getRetrofit();//获取Retrofit
    private Service mService = mRetrofit.create(Service.class);//获取service
    private SharedPreferences mSharedPreferences;//SharedPreferences保存用户信息
    private List<GetPayResult.PayBean> mList;//获取的订单数据
    private TransactionAdapter mTransactionAdapter;//适配器
    private Date startDate;//起始日期
    private Date endDate;//结束日期
    private String Tag = "QueryTransaActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_transa);
        ButterKnife.bind(this);
        mSharedPreferences = this.getSharedPreferences("user_info", Context.MODE_PRIVATE);
        querytransaActionbar.setStyle("自定义查询");
    }

    /**
     * @param listener 选择日期确定后执行的接口
     * @param curDate  当前显示的日期
     * @return
     * @description 选择日期弹出框
     * @time 2020-1-6 14:23
     */
    public void showDatePickDialog(DatePickerDialog.OnDateSetListener listener, String curDate) {
        Calendar calendar = Calendar.getInstance();
        int year = 0, month = 0, day = 0;
        try {
            year = Integer.parseInt(curDate.substring(0, curDate.indexOf("-")));
            month = Integer.parseInt(curDate.substring(curDate.indexOf("-") + 1, curDate.lastIndexOf("-"))) - 1;
            day = Integer.parseInt(curDate.substring(curDate.lastIndexOf("-") + 1, curDate.length()));
        } catch (Exception e) {
            e.printStackTrace();
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);
        }

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, DatePickerDialog.THEME_HOLO_LIGHT, listener, year, month, day);
        datePickerDialog.show();
    }

    public void getCustomizeDatePay(Map<String,Object> params) {
        mService.getCustomizeDatePay(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<GetPayResult>() {
                    @Override
                    public void accept(GetPayResult getPayResult) throws Exception {
                        if (getPayResult.isSuccess() == true) {
                            mList = getPayResult.getData();
                            showList(true,false,mList);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(Tag, throwable.getMessage());
                    }
                });
    }

    private void showList(boolean isVertical,boolean isReverse,List<GetPayResult.PayBean> mData) {
        //设置Recyclerview设置布局管理器
        LinearLayoutManager manager = new LinearLayoutManager(this);
        //设置水平还是垂直
        manager.setOrientation(isVertical ? LinearLayoutManager.VERTICAL : LinearLayoutManager.HORIZONTAL);
        //设置正向还是反向
        manager.setReverseLayout(isReverse);

        mRecyclerView.setLayoutManager(manager);
        //设置adapter
        mTransactionAdapter = new TransactionAdapter(R.layout.item_paylist_view,mData);
        //更换动画效果
        mTransactionAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        //设置Recyclerview
        mRecyclerView.setAdapter(mTransactionAdapter);
        //设置监听
        initListViewListener();
    }

    private void initListViewListener() {
        mTransactionAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                String str = mList.get(position).getTrans_id();
                String type = str.substring(0,2);//交易类型
                Log.e(Tag,type);
                if(type.equals("01")){//转账类型
                    Intent transintent = new Intent(QueryTransaActivity.this, TransferDetailActivity.class);
                    transintent.putExtra("transfer_id",mList.get(position).getTrans_id());
                    startActivity(transintent);
                }else if(type.equals("02")){//订单类型
                    Intent orderintent = new Intent(QueryTransaActivity.this, OrderDetailActivity.class);
                    orderintent.putExtra("order_id",mList.get(position).getTrans_id());
                    startActivity(orderintent);
                }else{
                    Toast.makeText(QueryTransaActivity.this,"未知类型"+position,Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @OnClick({R.id.startDate_et, R.id.endDate_et, R.id.queryPay_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.startDate_et:
                showDatePickDialog(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        Date date = new Date(year - 1900, month, day);
                        startDate = date;
                        startDateEt.setText(year + "-" + (month + 1) + "-" + day);
                    }
                }, startDateEt.getText().toString());
                break;
            case R.id.endDate_et:
                showDatePickDialog(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        Date date = new Date(year - 1900, month, day);
                        endDate = date;
                        endDateEt.setText(year + "-" + (month + 1) + "-" + day);
                    }
                }, startDateEt.getText().toString());
                break;
            case R.id.queryPay_tv:
                if(TextUtils.isEmpty(startDateEt.getText().toString())){
                    Toast.makeText(QueryTransaActivity.this,"起始日期不能为空！",Toast.LENGTH_SHORT).show();
                    break;
                }
                if(TextUtils.isEmpty(endDateEt.getText().toString())){
                    Toast.makeText(QueryTransaActivity.this,"结束日期不能为空！",Toast.LENGTH_SHORT).show();
                    break;
                }
                Map<String,Object> params = new HashMap<>();
                params.put("account_id",mSharedPreferences.getInt("account_id",000));
                params.put("startDate",startDate);
                params.put("endDate",endDate);
                getCustomizeDatePay(params);
                break;
        }
    }
}
