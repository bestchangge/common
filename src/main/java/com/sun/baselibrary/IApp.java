package com.sun.baselibrary;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.sun.baselibrary.http.HttpUtils;

/**
 * @author Sun
 * @created: 2019/5/31 23:32
 * @description:
 */
public class IApp extends Application {
    private static IApp mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        initHttp();
    }
    private void initHttp(){
        HttpUtils.getInstance().init(this);
    }

    public static IApp getInstance() {
        return mInstance;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
