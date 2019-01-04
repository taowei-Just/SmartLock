package com.it_tao.smartlock.health;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Messenger;

import com.it_tao.smartlock.inetrface.Healer;


import java.util.Calendar;

import huanyang.gloable.gloable.Filed;
import huanyang.gloable.gloable.Shared;
import huanyang.gloable.gloable.utils.LogUtil;
import huanyang.gloable.gloable.utils.SharedUtlis;
import huanyang.gloable.gloable.utils.TimeUtil;

import static android.support.v7.widget.StaggeredGridLayoutManager.TAG;


/**
 * Created by Administrator on 2017/3/27 0027.
 */

public class HealthService extends Service {

    private Messenger mMessenger;
    private HealthHelper mHealthHelper;
    private HealthBinder healthBinder;
    public static int FIRST_SEND_BINDER = 0x300000;

    long oneDayTimemillis = 24 * 60 * 60 * 1000;

    private boolean isFirst = false;
    private SharedUtlis sharedUtlis;
    private long nightTime;
    private long dayTime;
    private PendingIntent sleepPendingIntent;
    private PendingIntent weakPendingIntent;

    @Override
    public void onCreate() {
        super.onCreate();

        sharedUtlis = new SharedUtlis(getApplicationContext(), Shared.config);

        mHealthHelper= new HealthHelper(getApplicationContext());
        initDataTime();

    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    public int onStartCommand(Intent intent, int flags, int startId) {

        if (intent != null) {
            Bundle extras = intent.getExtras();
            if (extras != null && extras.get(Filed.sleepMessengerKey) != null) {
                mMessenger = (Messenger) extras.get(Filed.sleepMessengerKey);

            }

            boolean isSmartSleep = sharedUtlis.getBoolean(Shared.isSmartSleepKey, false);

            LogUtil.e(TAG," 开启智能睡眠 " +String.valueOf(isSmartSleep));
            if (isSmartSleep) {
                // 开启智能睡眠


                long sleepTime =   sharedUtlis.getLong(Shared.sleepTimeKey, nightTime);


                long weakTime = sharedUtlis.getLong(Shared.weakTimeKey, dayTime);


                LogUtil.e(TAG,"收到 睡眠时间为 " +sleepTime+"  : "+ TimeUtil.timeStamp2Date(sleepTime,"yyyy-MM-dd HH:mm:ss.SS"));

                LogUtil.e(TAG,"收到 唤醒时间为 " +weakTime+"  : "+   TimeUtil.timeStamp2Date(weakTime,"yyyy-MM-dd HH:mm:ss.SS"));

                long thizSleepTime  = sleepTime>System.currentTimeMillis()? sleepTime : findSecondDayTime(sleepTime);

                long thizWeakTime  =  weakTime>System.currentTimeMillis()? weakTime : findSecondDayTime(weakTime);

                LogUtil.e(TAG,"计算 睡眠时间为 " +  TimeUtil.timeStamp2Date(thizSleepTime,"yyyy-MM-dd HH:mm:ss.SS"));

                LogUtil.e(TAG,"计算  1、唤醒时间为 " +  TimeUtil.timeStamp2Date(thizWeakTime,"yyyy-MM-dd HH:mm:ss.SS"));


                thizWeakTime =  thizSleepTime>thizWeakTime? thizWeakTime+oneDayTimemillis:thizWeakTime;

                LogUtil.e(TAG,"计算 2、唤醒时间为 " +  TimeUtil.timeStamp2Date(thizWeakTime,"yyyy-MM-dd HH:mm:ss.SS"));


                LogUtil.e(TAG,"闹钟设置完毕 睡眠时间为 " + TimeUtil.timeStamp2Date(thizSleepTime,"yyyy-MM-dd HH:mm:ss.SS"));
                LogUtil.e(TAG,"闹钟设置完毕 唤醒时间为 " +  TimeUtil.timeStamp2Date(thizWeakTime,"yyyy-MM-dd HH:mm:ss.SS"));

                oneDayTimemillis = 2*60*1000;


                sleepPendingIntent = mHealthHelper.setSleepUpAlarm(thizSleepTime, oneDayTimemillis, 100);

                weakPendingIntent = mHealthHelper.setWakeUpAlarm(thizWeakTime, oneDayTimemillis, 101);


            }else {
                mHealthHelper.cancelAlarm(sleepPendingIntent,weakPendingIntent);
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private void initDataTime() {
        Calendar calendar = Calendar.getInstance();

        calendar.set(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),
                calendar.get(Calendar.HOUR_OF_DAY),
                23,
                00);

        nightTime = calendar.getTimeInMillis();

        calendar.set(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),
                calendar.get(Calendar.HOUR_OF_DAY),
                07,
                30);


        dayTime = calendar.getTimeInMillis();
    }

    private long findSecondDayTime(long dayTime) {


        if (dayTime<System.currentTimeMillis()){


            findSecondDayTime( dayTime+=oneDayTimemillis);
        }


        return  dayTime ;
    }


    public class HealthBinder extends Binder implements Healer {

        public HealthService getService() {
            return HealthService.this;

        }


        public void openWindow() {

            mHealthHelper.openWindow();
        }


        public void closeWindow() {
            mHealthHelper.closeWindow();
        }


        public void setSleep(long time) {
            mHealthHelper.setSleep(time);
        }


        public void setAwake(long time) {
            mHealthHelper.setAwake(time);
        }
    }


    public void onDestroy() {
        healthBinder.closeWindow();

        super.onDestroy();
    }
}
