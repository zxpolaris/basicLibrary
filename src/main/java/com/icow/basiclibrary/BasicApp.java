package com.icow.basiclibrary;

import android.app.Application;

/**
 * @author zhujun on 2017/11/2
 */

public class BasicApp extends Application {
    protected static BasicApp instance;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static BasicApp getInstance() {
        return instance;
    }
}
