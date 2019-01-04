package com.it_tao.smartlock.recevice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.it_tao.smartlock.health.HealthHelper;

import huanyang.gloable.gloable.Filed;
import huanyang.gloable.gloable.Shared;
import huanyang.gloable.gloable.utils.LogUtil;
import huanyang.gloable.gloable.utils.TimeUtil;

import static android.content.ContentValues.TAG;

/**
 * Created by Administrator on 2017/3/31 0031.
 */

public class HealthAlarmReceiver extends BroadcastReceiver {


    private HealthHelper mHealthHelper;


    public void onReceive(Context context, Intent intent ) {
 
        mHealthHelper = HealthHelper.getinstance(context);

        String extra =   intent.getExtras().getString(Filed.extra,"");

        Toast.makeText(context,"闹钟  " + extra + TimeUtil.TimeNowWithFormat("yyyy-MM-dd HH:mm:ss"),Toast.LENGTH_LONG).show();

        LogUtil.e(TAG," extra " +extra);

        if (extra.equals("isSleep")){
 
            showWindow(context);
 
        }else if(extra.equals("isAwaken")){
             
            distoryWindow(context);
            
        }
        
        
    }

    private void distoryWindow(Context context) {

        mHealthHelper.closeWindow();
    }

    private void showWindow(Context context) {

        mHealthHelper.openWindow();
 
    }
}
