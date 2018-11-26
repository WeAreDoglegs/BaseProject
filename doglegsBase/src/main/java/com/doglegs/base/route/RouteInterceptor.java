package com.doglegs.base.route;

import android.content.Context;
import android.util.Log;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;

@Interceptor(priority = 1)
public class RouteInterceptor implements IInterceptor {

    private String TAG = getClass().getSimpleName();

    @Override
    public void process(Postcard postcard, InterceptorCallback callback) {
        Log.e(TAG, "routeInterceptor process" + postcard.getPath());
        callback.onContinue(postcard);
    }

    @Override
    public void init(Context context) {
        Log.e(TAG, "routeInterceptor init");
    }

}
