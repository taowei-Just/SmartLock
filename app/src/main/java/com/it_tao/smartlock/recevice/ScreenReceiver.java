package com.it_tao.smartlock.recevice;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.it_tao.smartlock.health.HealthService;
import com.it_tao.smartlock.service.SmartService;

/**
 * Created by Administrator on 2017/4/11 0011.
 */

public class ScreenReceiver extends BroadcastReceiver {

    static ScreenReceiver  mThis = null;
    
    public static ScreenReceiver getInstance(){
        if (mThis==null){
             
            mThis = new ScreenReceiver();
            
        }
        return  mThis ;
        
    }
    
    public void onReceive(Context context, Intent intent) {

              String action = intent.getAction();
        
            Intent sIntent = new Intent(context, SmartService.class);
            sIntent.putExtra("fromeBoot",true);
            context.startService(sIntent);

            sIntent = new Intent(context, HealthService.class);
            intent.putExtra("isFirst" , true);
            context.startService(sIntent);

        
            Toast.makeText(context , " 当前电量"+ intent.getIntExtra("level", 0) ,Toast.LENGTH_SHORT).show();


 
    }
}
