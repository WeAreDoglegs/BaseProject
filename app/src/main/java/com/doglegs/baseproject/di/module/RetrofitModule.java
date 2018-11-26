package com.doglegs.baseproject.di.module;

import com.doglegs.baseproject.di.scope.RetrofitScope;
import com.doglegs.baseproject.start.model.IStartApi;
import com.doglegs.baseproject.start.model.StartRetrofit;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class RetrofitModule {

    @Provides
    @RetrofitScope
    public StartRetrofit provideLoginRetrofit(Retrofit retrofit) {
        return new StartRetrofit(retrofit.create(IStartApi.class));
    }

}
