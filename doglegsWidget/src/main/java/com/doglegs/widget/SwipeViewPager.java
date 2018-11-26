package com.doglegs.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

/**
 * des : swipe viewpager 手势冲突
 */


public class SwipeViewPager extends ViewPager {

    private float startY;
    private float startX;
    // 记录swipe是否拖拽的标记
    private boolean mIsSwipeDragger;
    private final int mTouchSlop;

    public SwipeViewPager(@NonNull Context context) {
        super(context);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public SwipeViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                // 记录手指按下的位置
                startY = ev.getY();
                startX = ev.getX();
                // 初始化标记
                mIsSwipeDragger = false;
                break;
            case MotionEvent.ACTION_MOVE:
                // 如果swiperefresh正在拖拽中，那么不拦截它的事件，直接return false；
                if (mIsSwipeDragger) {
                    return false;
                }

                // 获取当前手指位置
                float endY = ev.getY();
                float endX = ev.getX();
                float distanceX = Math.abs(endX - startX);
                float distanceY = Math.abs(endY - startY);
                // 如果X轴位移小于于Y轴位移，那么将事件交给swiperefresh处理。
                if (distanceY > mTouchSlop && distanceY > distanceX) {
                    mIsSwipeDragger = true;
                    return false;
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                // 初始化标记
                mIsSwipeDragger = false;
                break;
        }
        // 如果是Y轴位移小于X轴，事件交给viewpager处理。
        return super.onInterceptTouchEvent(ev);
    }

}
