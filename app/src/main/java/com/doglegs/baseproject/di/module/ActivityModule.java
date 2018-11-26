package com.doglegs.baseproject.di.module;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;

import com.doglegs.baseproject.di.scope.ActivityScope;
import com.tbruyelle.rxpermissions2.RxPermissions;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {

    private Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    @ActivityScope
    public Activity provideActivity() {
        return activity;
    }

    @Provides
    @ActivityScope
    public Context provideContext() {
        return activity;
    }

    @Provides
    @ActivityScope
    public RxPermissions provideRxPermissions(Activity activity) {
        return new RxPermissions(activity);
    }

//    @Provides
//    @ActivityScope
//    public MediaPlayer provideNewOrderMediaPlayer(Activity activity) {
//        MediaPlayer mediaPlayer = MediaPlayer.create(activity, R.raw.new_order);
//        mediaPlayer.setLooping(false);
//        return mediaPlayer;
//    }

}
