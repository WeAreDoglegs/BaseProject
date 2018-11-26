package com.doglegs.baseproject.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.doglegs.baseproject.R;
import com.doglegs.core.utils.ScreenUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ConfirmDialog extends Dialog {

    @BindView(R.id.tv_dialog_title)
    TextView tvDialogTitle;
    @BindView(R.id.tv_cancel)
    TextView tvCancel;
    @BindView(R.id.tv_confirm)
    TextView tvConfirm;

    private OnDialogListener onDialogListener;

    public ConfirmDialog(@NonNull Context context) {
        this(context, R.style.BaseDialog);
    }

    public ConfirmDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        initDialog();
    }

    private void initDialog() {
        View contentView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_confirm, null);
        ButterKnife.bind(this, contentView);
        setContentView(contentView);
        ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
        layoutParams.width = (int) (ScreenUtils.getScreenWidth() * 0.8);
        contentView.setLayoutParams(layoutParams);
        getWindow().setGravity(Gravity.CENTER);
        setCanceledOnTouchOutside(false);
    }

    @OnClick({R.id.tv_cancel, R.id.tv_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel:
                dismiss();
                break;
            case R.id.tv_confirm:
                if (onDialogListener != null) {
                    onDialogListener.onConfirmClickListener();
                }
                break;
        }
    }

    public void setTitle(String title) {
        tvDialogTitle.setText(title);
    }

    public void setTitle(CharSequence title) {
        tvDialogTitle.setText(title);
    }

    public void setConfirmText(String text) {
        tvConfirm.setText(text);
    }

    public void setCancelText(String text) {
        tvCancel.setText(text);
    }

    public void setTitleFontSize(int size) {
        tvDialogTitle.setTextSize(size);
    }

    public interface OnDialogListener {
        void onConfirmClickListener();
    }

    public void setOnDialogListener(OnDialogListener onDialogListener) {
        this.onDialogListener = onDialogListener;
    }
}
