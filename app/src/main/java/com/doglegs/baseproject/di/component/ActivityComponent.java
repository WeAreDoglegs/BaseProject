package com.doglegs.baseproject.di.component;

import com.doglegs.baseproject.di.module.ActivityModule;
import com.doglegs.baseproject.di.module.DialogModule;
import com.doglegs.baseproject.di.module.RetrofitModule;
import com.doglegs.baseproject.di.scope.ActivityScope;
import com.doglegs.baseproject.di.scope.RetrofitScope;
import com.doglegs.baseproject.start.ui.activity.LoginRegisteredActivity;
import com.doglegs.baseproject.start.ui.activity.SplashActivity;

import dagger.Component;

@RetrofitScope
@ActivityScope
@Component(dependencies = AppComponent.class, modules = {ActivityModule.class, RetrofitModule.class, DialogModule.class})
public interface ActivityComponent {

    void inject(SplashActivity splashActivity);
    void inject(LoginRegisteredActivity loginRegisteredActivity);
}
