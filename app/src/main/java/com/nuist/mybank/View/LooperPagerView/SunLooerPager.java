package com.nuist.mybank.View.LooperPagerView;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.nuist.mybank.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class SunLooerPager extends LinearLayout {

    private SunViewPager mViewPager;//对应轮播图图片
    private LinearLayout mPointContainer;//整个View最后的线性布局，用于放置滑动点
    private TextView mTitle;//轮播图的标题
    private BindTitleListener mTitleSetListener = null;//绑定标题的接口
    private InnerAdapter mInnerAdapter;//为Viewpager绑定资源的Adapter
    private OnItemClickListener mOnItemClickListener = null;

    public SunLooerPager(Context context) {
       this(context,null);
    }

    public SunLooerPager(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SunLooerPager(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //绑定布局文件
        LayoutInflater.from(context).inflate(R.layout.looper_page_layout,this,true);
        init();
    }

    private void init() {
        initView();
        initEvent();
    }
    //绑定布局文件中的各个子项
    private void initView() {
        mViewPager = this.findViewById(R.id.looper_pager_vp);
        mPointContainer = this.findViewById(R.id.looper_point_container_lv);
        mTitle = this.findViewById(R.id.looper_title_tv);
    }
    //设置ViewPage的事件回调
    private void initEvent() {
        //这是自定义的页面点击事件监听
        mViewPager.setPagerItemClickListener(new SunViewPager.OnPageItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //传入当前点击的页面序号
                if(mOnItemClickListener != null && mInnerAdapter != null){
                    int realPosition = position % mInnerAdapter.getDataSize();
                    mOnItemClickListener.onItemClick(realPosition);
                }
            }
        });
        //页面切换监听
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //切换的一个回调方法

            }
            @Override
            public void onPageSelected(int position) {
                if(mInnerAdapter != null){
                    int realPosition = position % mInnerAdapter.getDataSize();
                    //切换停下来的回调
                    //停下来以后设置标题
                    if (mTitleSetListener != null) {
                        mTitle.setText(mTitleSetListener.getTitle(realPosition));
                    }
                    //切换指示器焦点
                    updateIndicator();
                }

            }
            @Override
            public void onPageScrollStateChanged(int state) {
                //状态的改变

            }
        });
    }
    //定义绑定图片标题的接口
    public interface BindTitleListener {
        String getTitle(int position);
    }

    /**
     *  对外提供设置标题和图片资源的接口函数
     * @param innerAdapter 设置ViewPager的适配器
     * @param listener 设置图片标题的接口
     */
    public void setData(InnerAdapter innerAdapter,BindTitleListener listener){
        this.mTitleSetListener = listener;
        mViewPager.setAdapter(innerAdapter);
        mViewPager.setCurrentItem(Integer.MAX_VALUE/2+1);
        this.mInnerAdapter = innerAdapter;
        if (listener != null) {
            mTitle.setText(listener.getTitle(mViewPager.getCurrentItem() % mInnerAdapter.getDataSize()));
        }
        //可以得到数据的个数，通过数据的个数，来动态创建圆点，indicator
        updateIndicator();
    }

    /**
     * 自定义内部抽象类，实现PagerAdapter的相关方法，对需要外部用户设置的，
     * 设置抽象方法，传入设置的值，外部实现抽象方法
     * getDataSize()传入页面的数量
     * gerSubView（）外部用户设置View,绑定数据源图片，传入内部
     */
    public abstract static class InnerAdapter extends PagerAdapter{
        @Override
        public int getCount() {
            return Integer.MAX_VALUE;//设置无限循环
        }
        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }
        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, final int position) {
            final int realPosition = position % getDataSize();
            View itemView = gerSubView(container,realPosition);
           /* itemView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    //todo
                    mItemClickListener.onItemClick(realPosition);
                }
            });*/
            container.addView(itemView);//添加外部传入的view,添加到组容器里
            return itemView;
        }

        protected abstract int getDataSize();

        protected abstract View gerSubView(ViewGroup container, int position);
    }

    /**
     * 对外提供点击事件监听
     * @param listener
     */
    public void setOnItemClickListener(OnItemClickListener listener){
        this.mOnItemClickListener = listener;
    }
    public interface OnItemClickListener{
       void onItemClick(int position);
    }
    //设置更新滑动点
    private void updateIndicator() {
        if (mInnerAdapter != null) {
            int count = mInnerAdapter.getDataSize();
            mPointContainer.removeAllViews();
            for(int i=0;i<count;i++){
                View view = new View(getContext());
                if(mViewPager.getCurrentItem() % mInnerAdapter.getDataSize() == i){
                    view.setBackgroundColor(Color.parseColor("#ff0000"));//设置当前滑动点为红色
                }else{
                    view.setBackgroundColor(Color.parseColor("#ffffff"));//其他滑动点为白色
                }
                //设置大小
                LayoutParams layoutParams = new LayoutParams(SizeUtils.dip2px(getContext(),25),SizeUtils.dip2px(getContext(),5));
                layoutParams.setMargins(SizeUtils.dip2px(getContext(),5),0,SizeUtils.dip2px(getContext(),5),0);
                view.setLayoutParams(layoutParams);
                //添加到容器里
                mPointContainer.addView(view);
            }
        }
    }


}
