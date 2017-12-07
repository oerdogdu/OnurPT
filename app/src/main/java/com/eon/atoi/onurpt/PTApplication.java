package com.eon.atoi.onurpt;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.support.multidex.MultiDex;

import com.facebook.accountkit.AccountKit;

/**
 * Created by Atoi on 1.12.2017.
 */

public class PTApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AccountKit.initialize(getApplicationContext());
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);
    }
}
