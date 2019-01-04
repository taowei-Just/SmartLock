package com.it_tao.smartlock.thread;

import android.os.SystemClock;
import android.util.Log;

import com.it_tao.smartlock.MainActivity;
import com.it_tao.smartlock.ScreenState;
import com.it_tao.smartlock.helper.SmartHelper;

/**
 * Created by Administrator on 2017/3/23 0023.
 */

public class lockTimeThread extends Thread {
    
    
    
   private long overTime   ;
    
    private long time ;

    SmartHelper smartHelper ;
    
    ScreenState screenState ;
    
    public lockTimeThread(ScreenState smartHelper , long time) {
        this.screenState =smartHelper ;
        this.overTime = time ;
        Log.e(MainActivity.Tag , "创建线程 传入自动锁屏时间："  +time) ;
    }

    public void run() {

        Log.e(MainActivity.Tag, "lockTimeThread  计时线程开始！");
        long l = SystemClock.uptimeMillis();

        try {
            time =overTime ;
            while (time!=0){
                
                Thread.sleep(100);
                time-=100 ;
                  
            }
              screenState.closeScreen();

        }catch (Exception e){
            
            e.printStackTrace();
            
        }finally {
            Log.e(MainActivity.Tag , "lockTimeThread  计时线程结束！标准 :" +overTime  + "  耗时 " + String.valueOf(SystemClock.uptimeMillis()-l) );
        }

    
    }
}
