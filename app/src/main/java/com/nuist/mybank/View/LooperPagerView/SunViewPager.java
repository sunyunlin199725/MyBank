package com.nuist.mybank.View.LooperPagerView;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

public class SunViewPager extends ViewPager {

    private long delayTime = 4000;//延迟时间
    private String Tag = "sunViewPager";
    private boolean isClick = false;//是否点击事件
    private float downX;//手点击的x坐标
    private float downY;//手点击的y坐标
    private long downTime;//手点击的时间
    private OnPageItemClickListener mItemClickListener = null;//ViewPage的点击监听接口

    public SunViewPager(@NonNull Context context) {
        this(context,null);
    }

    public SunViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //设置viewpager的触碰事件
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch(action){
                    case MotionEvent.ACTION_DOWN://手触摸，按压
                        downX = event.getX();//获取坐标
                        downY = event.getY();//获取坐标
                        downTime = System.currentTimeMillis();//获取当前系统时间
                        stopLooper();
                        break;
                    case MotionEvent.ACTION_UP://手松开
                        float dx = Math.abs(event.getX()-downX);//移动的x坐标距离
                        float dy = Math.abs(event.getY()-downY);//移动的y坐标距离
                        long dTime = System.currentTimeMillis()-downTime;//手按压的时间
                        if(dx <= 5 && dy <= 5 && dTime <= 1000){//判断是否为点击事件
                            isClick = true;
                        }else {
                            isClick = false;
                        }
                        if(isClick && mItemClickListener != null){//如果判断是点击事件
                            mItemClickListener.onItemClick(getCurrentItem());
                        }
                        startLooper();
                        break;
                }
                return false;
            }
        });
    }
    //对外部提供page点击事件监听的设置接口
    public void setPagerItemClickListener(OnPageItemClickListener onPageItemClickListener){
        this.mItemClickListener = onPageItemClickListener;
    }
    //设置page点击事件监听接口
    public interface OnPageItemClickListener{
        void onItemClick(int position);
    }
    //对外提供设置页面跳转延迟时间的接口
    public void setDelayTime(long delayTime){
        this.delayTime = delayTime;
    }

    /**
     * 下面是设置ViewPager的自动播放
     */
    @Override
    protected void onAttachedToWindow() {
        Log.e(Tag,"onAttachedToWindow");
        super.onAttachedToWindow();
        startLooper();
    }

    private void startLooper() {
        removeCallbacks(mTask);
        postDelayed(mTask,delayTime);
    }
    private Runnable mTask = new Runnable() {
        @Override
        public void run() {
            int currentItem = getCurrentItem();
            currentItem++;
            setCurrentItem(currentItem);
            postDelayed(this,delayTime);
        }
    };
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.e(Tag,"onDetachedFromWindow");
        stopLooper();
    }
    private void stopLooper() {
        removeCallbacks(mTask);

    }
}
