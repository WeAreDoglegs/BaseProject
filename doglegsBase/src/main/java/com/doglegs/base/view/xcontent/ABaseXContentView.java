package com.doglegs.base.view.xcontent;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doglegs.base.R;

import butterknife.ButterKnife;

public abstract class ABaseXContentView extends RelativeLayout {

    ImageView ivState;
    TextView tvStateDesc;
    RelativeLayout llRootLoadContentErrorOrEmpty;
    LinearLayout llRoot;
    Button btnOperation;

    protected View mSuccessView;

    private View rootView;

    private OnXOrderContentLisener onXOrderContentLisener;

    private OnXOperationLisener onXOperationLisener;

    public ABaseXContentView(Context context) {
        this(context, null);
    }

    public ABaseXContentView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ABaseXContentView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        rootView = View.inflate(getContext(), R.layout.view_xcontent, this);
        ivState = rootView.findViewById(R.id.iv_state);
        tvStateDesc = rootView.findViewById(R.id.tv_state_desc);
        btnOperation = rootView.findViewById(R.id.btn_operation);
        btnOperation.setOnClickListener(v -> {
            if (onXOperationLisener != null) {
                onXOperationLisener.onXOperationClick();
            }
        });
        llRoot = rootView.findViewById(R.id.ll_root);
        llRootLoadContentErrorOrEmpty = rootView.findViewById(R.id.rl_root_load_content_error_or_empty);
        llRoot.setOnClickListener(v -> {
            if (onXOrderContentLisener != null) {
                onXOrderContentLisener.onXOrderExceptionClick();
            }
        });
        RelativeLayout root = (RelativeLayout) rootView;
        mSuccessView = createSuccessView();
        root.addView(mSuccessView, 0);
    }

    private View createSuccessView() {
        View view = View.inflate(getContext(), getSuccessfulLayoutId(), null);
        view.setVisibility(View.GONE);
        ButterKnife.bind(this, view);
        initSuccessView(view);
        return view;
    }

    protected abstract int getSuccessfulLayoutId();

    protected abstract void initSuccessView(View view);


    /**
     * 显示数据加载成功视图
     */
    public void showSuccessfulView() {
        if (mSuccessView != null) mSuccessView.setVisibility(View.VISIBLE);
        llRootLoadContentErrorOrEmpty.setVisibility(View.GONE);
    }

    /**
     * 显示数据加载错误视图
     */
    public void showErrorView() {
        showErrorView(getContext().getString(R.string.loaded_error));
    }

    public void showErrorView(String tip) {
        if (mSuccessView != null) mSuccessView.setVisibility(View.GONE);
        ivState.setImageResource(R.mipmap.mui__error_image);
        tvStateDesc.setText(tip);
        llRootLoadContentErrorOrEmpty.setVisibility(View.VISIBLE);
    }

    /**
     * 显示空数据视图
     */
    public void showEmptyView() {
        showEmptyView(getContext().getString(R.string.loaded_empty));
    }

    public void showEmptyView(String tip) {
        if (mSuccessView != null) mSuccessView.setVisibility(View.GONE);
        ivState.setImageResource(R.mipmap.mui__empty_image);
        tvStateDesc.setText(tip);
        llRootLoadContentErrorOrEmpty.setVisibility(View.VISIBLE);
    }

    public void setOperationText(boolean isVisible, String text) {
        if (isVisible) {
            btnOperation.setText(text);
            btnOperation.setVisibility(View.VISIBLE);
        } else {
            btnOperation.setVisibility(View.GONE);
        }
    }

    public interface OnXOrderContentLisener {
        void onXOrderExceptionClick();
    }

    public interface OnXOperationLisener {
        void onXOperationClick();
    }

    public void setOnXOrderContentLisener(OnXOrderContentLisener onXOrderContentLisener) {
        this.onXOrderContentLisener = onXOrderContentLisener;
    }

    public void setOnXOperationLisener(OnXOperationLisener onXOperationLisener) {
        this.onXOperationLisener = onXOperationLisener;
    }

    public void setSuccessfulViewBackgroundColor(int color) {
        mSuccessView.setBackgroundColor(getResources().getColor(color));
    }
}
