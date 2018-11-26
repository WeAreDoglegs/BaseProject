package com.doglegs.base.view.xcontent;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.doglegs.base.R;
import com.doglegs.base.R2;

import butterknife.BindView;

/**
 * desc: 通知列表页面
 */

public class XNoticeContentView extends ABaseXContentView {

    @BindView(R2.id.rv_notice_list)
    RecyclerView rvNoticeList;

    public XNoticeContentView(Context context) {
        super(context);
    }

    public XNoticeContentView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public XNoticeContentView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected int getSuccessfulLayoutId() {
        return R.layout.view_xcontent_notice_list;
    }

    protected void initSuccessView(View view) {
        rvNoticeList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    }

    public RecyclerView getRvNoticeList() {
        return rvNoticeList;
    }

    public void setRecyclerViewBackgroundColor(int color) {
        rvNoticeList.setBackgroundColor(getResources().getColor(color));
    }

}
