package com.doglegs.base.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.doglegs.base.R;
import com.doglegs.base.R2;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoadingDialog extends Dialog {

    @BindView(R2.id.iv_loading_state)
    ImageView ivLoadingState;
    @BindView(R2.id.tv_state)
    TextView tvState;

    private Context mContext;
    private RotateAnimation mRotateAnimation;
    private boolean isPlayed;

    public LoadingDialog(Context context) {
        this(context, R.style.BaseDialogNullBackground);
    }


    public LoadingDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        mContext = context;
        initView(context);
    }

    private void initView(Context context) {
        View view = View.inflate(context, R.layout.view_toast, null);
        ButterKnife.bind(this, view);
        setContentView(view);
        getWindow().setGravity(Gravity.CENTER);
        setCanceledOnTouchOutside(false);
        setCancelable(false);
        mRotateAnimation = (RotateAnimation) AnimationUtils.loadAnimation(getContext(), R.anim.rotate_loading_1000);
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

    public void setImageResource(int srcId) {
        ivLoadingState.setImageResource(srcId);
    }

    public void setText(int resId) {
        tvState.setText(mContext.getResources().getString(resId));
    }

    public void setText(CharSequence s) {
        tvState.setText(s);
    }

    public void startLoading() {
        if (!isPlayed) {
            ivLoadingState.setImageResource(R.mipmap.ic_login_loading);
            ivLoadingState.startAnimation(mRotateAnimation);
        }
    }

    public void endLoading() {
        if (isPlayed) {
            isPlayed = false;
            ivLoadingState.getAnimation().cancel();
        }
    }

    public void loadingSuccess() {
        ivLoadingState.setImageResource(R.mipmap.ic_login_success);
    }

    public boolean isPlayed() {
        return isPlayed;
    }

}
