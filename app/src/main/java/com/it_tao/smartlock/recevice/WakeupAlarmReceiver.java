package com.it_tao.smartlock.recevice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.it_tao.smartlock.health.HealthHelper;

/**
 * Created by Administrator on 2017/3/31 0031.
 */

public class WakeupAlarmReceiver extends BroadcastReceiver {


    private HealthHelper mHealthHelper;
    
    
    public void onReceive(Context context, Intent intent ) {

        Toast.makeText(context,"闹钟",Toast.LENGTH_LONG).show();
        
        mHealthHelper = HealthHelper.getinstance(context);

        String extra = (String) intent.getExtras().get("extra");


        if (extra.equals("isSleep")){
 
            showWindow(context);
 
        }else if(extra.equals("isSleep")){
             
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
