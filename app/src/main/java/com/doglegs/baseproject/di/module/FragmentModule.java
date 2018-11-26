package com.doglegs.baseproject.di.module;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.support.v4.app.Fragment;

import com.doglegs.baseproject.di.scope.FragmentScope;
import com.tbruyelle.rxpermissions2.RxPermissions;

import dagger.Module;
import dagger.Provides;

@Module
public class FragmentModule {

    private Fragment fragment;

    public FragmentModule(Fragment fragment) {
        this.fragment = fragment;
    }

    @Provides
    @FragmentScope
    public Activity provideActivity() {
        return fragment.getActivity();
    }

    @Provides
    @FragmentScope
    public Context provideContext() {
        return fragment.getContext();
    }

    @Provides
    @FragmentScope
    public RxPermissions provideRxPermisions(Activity activity) {
        return new RxPermissions(activity);
    }

//    @Provides
//    @FragmentScope
//    public MediaPlayer provideNewOrderMediaPlayer(Activity activity) {
//        MediaPlayer mediaPlayer = MediaPlayer.create(activity, R.raw.new_order);
//        mediaPlayer.setLooping(false);
//        return mediaPlayer;
//    }

}
