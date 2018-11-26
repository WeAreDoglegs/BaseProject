package com.doglegs.base.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.doglegs.base.R;
import com.doglegs.base.R2;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoadingView extends LinearLayout {


    @BindView(R2.id.iv_loading)
    AppCompatImageView ivLoading;
    @BindView(R2.id.rl_root)
    RelativeLayout rlRoot;

    private boolean isPlayed = false;

    private RotateAnimation mRotateAnimation;

    public LoadingView(Context context) {
        this(context, null);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        View view = View.inflate(getContext(), R.layout.view_loading, this);
        ButterKnife.bind(this, view);
        setBackgroundResource(R.drawable.selector_btn_enable);
        mRotateAnimation = (RotateAnimation) AnimationUtils.loadAnimation(getContext(), R.anim.rotate_loading);
        mRotateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                isPlayed = true;
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                isPlayed = false;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    public void startLoading() {
        if (!isPlayed) {
            setEnabled(false);
            ivLoading.startAnimation(mRotateAnimation);
        }
    }

    public void endLoading() {
        if (isPlayed) {
            setEnabled(true);
            ivLoading.getAnimation().cancel();
        }
    }

    public boolean isPlayed() {
        return isPlayed;
    }

}
