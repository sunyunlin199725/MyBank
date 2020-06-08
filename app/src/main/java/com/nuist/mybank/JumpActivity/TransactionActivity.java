package com.nuist.mybank.JumpActivity;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.nuist.mybank.Adapter.TabFragmentAdapter;
import com.nuist.mybank.Fragments.ViewPagerFragment1;
import com.nuist.mybank.Fragments.ViewPagerFragment2;
import com.nuist.mybank.R;
import com.nuist.mybank.View.CustomActionBar;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;

public class TransactionActivity extends AppCompatActivity {

    @BindView(R.id.transaction_actionbar)
    CustomActionBar transactionActionbar;
    @BindView(R.id.tablayout)
    TabLayout mTabLayout;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    private ViewPagerFragment1 mFragment1;
    private ViewPagerFragment2 mFragment2;
    private List<Fragment> mFragments = new ArrayList<>();
    private TabFragmentAdapter mTabFragmentAdapter;
    private int mIndex=0;
    private String mTitles[] = {"交易明细", "收支总览"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        ButterKnife.bind(this);
        transactionActionbar.setStyle("明细查询");
        initViewPagerAndTab();
    }

    private void initViewPagerAndTab() {
        mFragment1 = new ViewPagerFragment1();
        mFragment2 = new ViewPagerFragment2();
        mFragments.add(mFragment1);
        mFragments.add(mFragment2);
        mTabFragmentAdapter = new TabFragmentAdapter(mFragments, mTitles, getSupportFragmentManager(), this);
       // mViewPager.setOffscreenPageLimit(2);// 设置预加载Fragment个数
        mViewPager.setAdapter(mTabFragmentAdapter);
        mViewPager.setCurrentItem(0);// 设置当前显示标签页为第一页
        // 将ViewPager和TabLayout绑定
        mTabLayout.setupWithViewPager(mViewPager);
        // 设置tab文本的没有选中（第一个参数）和选中（第二个参数）的颜色
        //mTabLayout.setTabTextColors(Color.BLACK,R.color.select);
    }


}
