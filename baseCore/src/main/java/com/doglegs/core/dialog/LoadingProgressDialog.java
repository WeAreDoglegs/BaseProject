package com.doglegs.core.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.doglegs.core.R;
import com.doglegs.core.R2;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author : Mai_Xiao_Peng
 * @email : Mai_Xiao_Peng@163.com
 * @time : 2018/8/14 11:29
 * @describe :
 */


public class LoadingProgressDialog extends Dialog {

    @BindView(R2.id.tv_load_desc)
    TextView tvLoadDesc;

    public LoadingProgressDialog(@NonNull Context context) {
        this(context, R.style.BaseDialog);
    }

    public LoadingProgressDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        initDialog();
    }

    private void initDialog() {
        View contentView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_loading, null);
        ButterKnife.bind(this, contentView);
        setContentView(contentView);
        getWindow().setGravity(Gravity.CENTER);
        setCanceledOnTouchOutside(false);
        setCancelable(false);
    }

    public void show(String text) {
        tvLoadDesc.setText(text);
        show();
    }

}
