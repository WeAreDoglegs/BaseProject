package com.doglegs.baseproject.di.component;

import com.doglegs.baseproject.di.module.DialogModule;
import com.doglegs.baseproject.di.module.FragmentModule;
import com.doglegs.baseproject.di.module.RetrofitModule;
import com.doglegs.baseproject.di.scope.FragmentScope;
import com.doglegs.baseproject.di.scope.RetrofitScope;

import dagger.Component;

@RetrofitScope
@FragmentScope
@Component(dependencies = AppComponent.class, modules = {FragmentModule.class, RetrofitModule.class, DialogModule.class})
public interface FragmentComponent {

    //void inject(MainFragment mainFragment);

}
