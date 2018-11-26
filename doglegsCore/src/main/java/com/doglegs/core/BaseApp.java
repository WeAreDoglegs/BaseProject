package com.doglegs.core;

import android.app.Activity;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.doglegs.core.utils.Utils;

import java.util.HashSet;
import java.util.Set;

public abstract class BaseApp extends MultiDexApplication {

    public final String TAG = getClass().getSimpleName();

    public static Set<Activity> sAllActivities;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
        onAppCreate();
    }

    public abstract void onAppCreate();

    public static void addActivity(Activity act) {
        if (sAllActivities == null) {
            sAllActivities = new HashSet<>();
        }
        sAllActivities.add(act);
    }

    public static void removeActivity(Activity act) {
        if (sAllActivities != null) {
            sAllActivities.remove(act);
        }
    }

    public static void exitApp() {
        if (sAllActivities != null) {
            synchronized (sAllActivities) {
                for (Activity act : sAllActivities) {
                    act.finish();
                }
            }
        }
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

}
