package com.doglegs.base.view.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

import com.doglegs.base.R;
import com.doglegs.base.R2;
import com.doglegs.core.dialog.ABaseBottomDialog;

import butterknife.OnClick;


public class ChoosePictureDialog extends ABaseBottomDialog {

    private OnChooseClickLisener onChooseClickLisener;

    public ChoosePictureDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.dialog_choose_pic;
    }

    @Override
    protected void initView() {

    }

    @OnClick({R2.id.tv_take_photo, R2.id.tv_album, R2.id.tv_cancel})
    public void onViewClicked(View view) {
        if (view.getId() == R.id.tv_cancel) {
            dismiss();
        } else if (view.getId() == R.id.tv_take_photo) {
            if (onChooseClickLisener != null) {
                onChooseClickLisener.onTakePhotoClick();
            }
        } else if (view.getId() == R.id.tv_album) {
            if (onChooseClickLisener != null) {
                onChooseClickLisener.onAlbumClick();
            }
        }
    }

    public interface OnChooseClickLisener {
        void onTakePhotoClick();

        void onAlbumClick();
    }

    public void setOnChooseClickLisener(OnChooseClickLisener onChooseClickLisener) {
        this.onChooseClickLisener = onChooseClickLisener;
    }
}
