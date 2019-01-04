package com.it_tao.smartlock.thread;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/15 0015.
 */

public class ClockThread extends Thread {

     ArrayList<Long>  timeList  = new ArrayList<>();
    ArrayList<AlarmManager>   mAlarManagerList  = new ArrayList<>();
    ArrayList<PendingIntent>    pendingIntentList  = new ArrayList<>();

    long oneDayTimemillis = 24 * 60 * 60 * 1000;
    
    
    
    
    
    
    public ClockThread(long time , AlarmManager mAlarManager , PendingIntent pendingIntent) {
        
        this.timeList.add( time) ;
        this.mAlarManagerList.add(mAlarManager)    ;
        this.pendingIntentList.add( pendingIntent) ;
        
    }

    
    public void run() {
        
        for (int i =0 ; i<mAlarManagerList.size() ;i++) {
            Log.e(" clock ", "设置闹钟 ");

            mAlarManagerList.get(i).setRepeating(AlarmManager.RTC_WAKEUP, timeList.get(i), oneDayTimemillis, pendingIntentList.get(i));
            try {
                Thread.sleep(5 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Log.e(" clock ", "闹钟 设置完成");
        }
    }
    
    public void  updataData (long time , AlarmManager mAlarManager , PendingIntent pendingIntent) {
        this.timeList.add( time) ;
        this.mAlarManagerList.add(mAlarManager)    ;
        this.pendingIntentList.add( pendingIntent) ;


    }
}
