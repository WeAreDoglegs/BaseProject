package com.doglegs.baseproject.di.module;

import android.app.Activity;

import com.doglegs.base.view.dialog.LoadingDialog;
import com.doglegs.baseproject.widget.dialog.ConfirmDialog;
import com.doglegs.core.dialog.LoadingProgressDialog;

import dagger.Module;
import dagger.Provides;

@Module
public class DialogModule {

    private final Activity activity;

    public DialogModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    public LoadingDialog provideLoadingPopup() {
        return new LoadingDialog(activity);
    }

    @Provides
    public LoadingProgressDialog provideLoadingProgressDialog() {
        return new LoadingProgressDialog(activity);
    }

    @Provides
    public ConfirmDialog provideConfirmDialog() {
        return new ConfirmDialog(activity);
    }

}
