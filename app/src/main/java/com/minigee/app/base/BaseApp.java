package com.minigee.app.base;

import android.app.Application;

import com.minigee.app.BuildConfig;

import org.xutils.x;
/**
 * Created by Zhou on 2015-11-18.
 */
public class BaseApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();


        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG);
    }
}
