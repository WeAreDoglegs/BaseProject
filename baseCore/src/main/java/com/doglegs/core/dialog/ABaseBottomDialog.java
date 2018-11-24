package com.doglegs.core.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.doglegs.core.R;

import butterknife.ButterKnife;

/**
 * @author : Mai_Xiao_Peng
 * @email : Mai_Xiao_Peng@163.com
 * @time : 2018/7/31 10:08
 * @describe :
 */


public abstract class ABaseBottomDialog extends Dialog {

    public ABaseBottomDialog(@NonNull Context context) {
        this(context, R.style.BaseDialog);
    }

    public ABaseBottomDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        initDialog();
        initView();
    }

    private void initDialog() {
        View contentView = LayoutInflater.from(getContext()).inflate(getLayoutId(), null);
        ButterKnife.bind(this, contentView);
        setContentView(contentView);
        ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
        layoutParams.width = getContext().getResources().getDisplayMetrics().widthPixels;
        contentView.setLayoutParams(layoutParams);
        getWindow().setGravity(Gravity.BOTTOM);
        getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
    }

    public abstract int getLayoutId();

    protected abstract void initView();

}
