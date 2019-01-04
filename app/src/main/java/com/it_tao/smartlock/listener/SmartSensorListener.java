package com.it_tao.smartlock.listener;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Build;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

import com.it_tao.smartlock.MainActivity;
import com.it_tao.smartlock.ScreenClose;
import com.it_tao.smartlock.ScreenLighten;
import com.it_tao.smartlock.ScreenState;
import com.it_tao.smartlock.helper.SmartHelper;
import com.it_tao.smartlock.inetrface.OperationLock;
import com.it_tao.smartlock.thread.lockTimeThread;

/**
 * Created by Administrator on 2017/3/22 0022.
 */

public class SmartSensorListener implements SensorEventListener, OperationLock {


    private SmartHelper mHelper;
    private Context context;
    private long lokcTime;
    private long autoLokcTime;
    private long lockStarttimeMillis;
    private long aLong;
    private long intervalTime;

    private boolean enableLock = false;
    private boolean isDownLock = false;
    private boolean isUpLock = false;
    private boolean isAllowLighting = true;

    private Thread[] mLockPool = new Thread[1];

    private ScreenState screenState;


    public SmartSensorListener(Context context, SmartHelper smartHelper) {
        this.mHelper = smartHelper;
        this.context = context;
        this.screenState = new ScreenState(smartHelper);
    }

    public void onSensorChanged(SensorEvent event) {

        float[] values = event.values;

        Log.e(MainActivity.Tag, " 事件类型 " + values[0] + "-----------------------------------\n\n\n\n");


        if (enableLock) {

            if (isApproach(values[0])) {
                Log.e(MainActivity.Tag, "接近 " + values[0]);

                lockStarttimeMillis = SystemClock.uptimeMillis();
                isDownLock = true;

                //按下
                if (screenState.isScreen()) {

                    screenState = new ScreenLighten(mHelper);
                    //亮屏状态


                    if (mLockPool[0] != null) {
                        mLockPool[0].interrupt();

                    }
                    mLockPool[0] = new lockTimeThread(screenState, autoLokcTime);
                    mLockPool[0].start();


                } else {
                    screenState = new ScreenClose(mHelper);
                    //灭屏状态
                }


            } else {

                Log.e(MainActivity.Tag, "离开 " + values[0] + " 间隔Time" + aLong);

                //取消延时锁屏
                if (mLockPool[0] != null) {
                    if (mLockPool[0].isAlive()) {
                        mLockPool[0].interrupt();
                    }
                }

                //离开
                long lockOuttimeMillis = SystemClock.uptimeMillis();

                aLong = lockOuttimeMillis - lockStarttimeMillis;

                if (aLong >= lokcTime && lockStarttimeMillis > 0) {

                    Log.e(MainActivity.Tag, "锁屏 " + lockStarttimeMillis + " 间隔Time" + aLong);
                    screenState.closeScreen();
                }

                if ((aLong >= intervalTime) && lockStarttimeMillis > 0) {

                    Log.e(MainActivity.Tag, "亮屏 " + aLong);
                    screenState.openScreen(isAllowLighting);

                }


                if (!screenState.isScreen() && (aLong >= lokcTime + intervalTime) && lockStarttimeMillis > 0) {
                    screenState = new ScreenClose(mHelper);
                    screenState.openScreen(isAllowLighting);
                }

                aLong = lockStarttimeMillis = 0;

            }

        }
    }

    private boolean isApproach(float values) {


        if (chackVersion() == LOW_VERSIO_NMODE) {

            if (values == 0.0)

                return true;


        } else if (chackVersion() == HIGHT_VERSION_MODE) {

            if (values == 0.0 || values == 1.0)
                return true;

        }

        return false;
    }


    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public boolean isEnableLock() {
        return enableLock;
    }


    public void startLock() {


        enableLock = true;
        Toast.makeText(context, "已允许锁屏！", Toast.LENGTH_LONG).show();

    }


    public void stopLock() {

        enableLock = false;
        if (mLockPool[0] != null) {
            if (mLockPool[0].isAlive()) {
                mLockPool[0].interrupt();
            }
        }
        Toast.makeText(context, "已禁止锁屏！", Toast.LENGTH_LONG).show();

    }


    public void allowLighting( ) {
        Log.e(MainActivity.Tag, "允许亮屏");
        isAllowLighting = true;
    }

    @Override
    public void disallowLighting( ) {
        Log.e(MainActivity.Tag, "拒绝亮屏");
        isAllowLighting = false;
    }

    @Override
    public void setEnLockTime(long time) {

        lokcTime = time;
        Log.e(MainActivity.Tag, "setEnLockTime 得到数值 " + time);
    }

    @Override
    public void setUnLockInterval(long time) {
        intervalTime = time;
        Log.e(MainActivity.Tag, "setUnLockInterval 得到数值 " + time);
    }

    @Override
    public void setAutoTime(long time) {
        autoLokcTime = time;

        Log.e(MainActivity.Tag, "setAutoTime 得到数值 " + time);
    }

    int LOW_VERSIO_NMODE = 15;
    int HIGHT_VERSION_MODE = 17;

    int CURRENT_VERSION_MODE;
    

    public int chackVersion() {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
            CURRENT_VERSION_MODE = LOW_VERSIO_NMODE;

        } else {


            System.out.println( "当前手机版本为： " + Build.MODEL   +"   2或者 ：" +Build.BRAND);

            if (Build.MODEL.equals("M3s")){

                CURRENT_VERSION_MODE = LOW_VERSIO_NMODE ;
                
            }else {


                CURRENT_VERSION_MODE = HIGHT_VERSION_MODE;
            }



        }


        return CURRENT_VERSION_MODE;
    }

}
