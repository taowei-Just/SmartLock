package com.it_tao.smartlock;

import android.app.Application;

import com.tencent.bugly.crashreport.CrashReport;

/**
 * Created by Tao on 2017/12/28 0028.
 */

public class MyApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        CrashReport.initCrashReport(getApplicationContext(), "4b3b3d9c29", false);
    }
}
