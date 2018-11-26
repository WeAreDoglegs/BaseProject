package com.doglegs.base.view.xcontent;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.doglegs.base.R;
import com.doglegs.base.R2;

import butterknife.BindView;

public class XListContentView extends ABaseXContentView {

    @BindView(R2.id.rv_order_list)
    RecyclerView rvOrderList;

    public XListContentView(Context context) {
        super(context);
    }

    public XListContentView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public XListContentView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected int getSuccessfulLayoutId() {
        return R.layout.view_xcontent_order_list;
    }

    protected void initSuccessView(View view) {
        rvOrderList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    }

    public RecyclerView getRvOrderList() {
        return rvOrderList;
    }

    public void setRecyclerViewBackgroundColor(int color) {
        rvOrderList.setBackgroundColor(getResources().getColor(color));
    }

}
