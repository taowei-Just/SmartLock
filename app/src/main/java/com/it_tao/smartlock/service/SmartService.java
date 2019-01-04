package com.it_tao.smartlock.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.net.ConnectivityManager;
import android.os.Binder;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.ImageView;

import com.it_tao.smartlock.ScreenState;
import com.it_tao.smartlock.helper.SmartHelper;
import com.it_tao.smartlock.inetrface.OperationLock;
import com.it_tao.smartlock.listener.SmartSensorListener;
import com.it_tao.smartlock.recevice.UnlockRecevice;

/**
 * Created by Administrator on 2017/3/22 0022.
 */

public class SmartService extends Service {

    private Messenger activeMessager;
    public static int REGESTER_WHST = 0x10000;
    private SensorManager mSenManager;
    private SmartSensorListener sensorListener;
    private UnlockRecevice unlockRecevice;
    private SmartHelper smartHelper;
    private ScreenState screenState;
    private SmartBinder smartBinder;
    public static int REPLAY_BINDER = 0x20000;
    private int autoIndex;
    private int lockIndex;
    private int intervalIndex;
    private SharedPreferences configShared;
    private WindowManager mWin;
    private ImageView mWinView;
    private boolean isAllowLighting;

    public IBinder onBind(Intent intent) {


        initService();

        return smartBinder;
    }

    private void initService() {

        if (!isInit) {
            isInit = true;
            mSenManager = (SensorManager) getSystemService(SENSOR_SERVICE);

            unlockRecevice = new UnlockRecevice(smartHelper);
            screenState = new ScreenState(smartHelper);

            sensorListener = new SmartSensorListener(SmartService.this, smartHelper);

            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(Intent.ACTION_SCREEN_ON);
            intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
            intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);

//            registerReceiver(unlockRecevice, intentFilter);

            smartBinder = new SmartBinder();
        }
    }

    boolean isInit = false;

    public int onStartCommand(Intent intent, int flags, int startId) {
        Message message;
        smartHelper = SmartHelper.getinstance(this);
        configShared = getSharedPreferences("config", MODE_PRIVATE);
        if (intent != null && intent.getExtras().getBoolean("fromeBoot", false)) {
            initService();
            srtDefaultConfig();
//            smartBinder.startLock();
        }
        if (smartBinder == null)
            smartBinder = new SmartBinder();


        boolean active = smartHelper.chackActive();

        try {
            Messenger activeMess = (Messenger) intent.getExtras().get("ActiveMess");
            if (activeMess != null)
                this.activeMessager = activeMess;
        } catch (Exception e) {

            e.printStackTrace();
        }


        if (!active && (activeMessager != null)) {
            message = new Message();

            message.what = REGESTER_WHST;

            try {

                activeMessager.send(message);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        message = new Message();
        message.what = REPLAY_BINDER;
        message.obj = smartBinder;
        try {
            if (activeMessager != null) {
                activeMessager.send(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

//        mSenManager.registerListener(sensorListener, mSenManager.getDefaultSensor(Sensor.TYPE_PROXIMITY),SensorManager.SENSOR_DELAY_NORMAL);

        createWindow();
        return super.onStartCommand(intent, flags, startId);

    }

    private void srtDefaultConfig() {

        autoIndex = configShared.getInt("AutoTime", 2);
        lockIndex = configShared.getInt("LockTime", 2);
        intervalIndex = configShared.getInt("IntervalTime", 2);
        isAllowLighting = configShared.getBoolean("IsAllowLighting", false);

        if (isAllowLighting) {
            smartBinder.allowLighting();
        } else {
            smartBinder.disallowLighting();
        }

        smartBinder.setUnLockInterval(intervalIndex * 200);
        smartBinder.setEnLockTime(lockIndex * 100);
        smartBinder.setAutoTime(autoIndex * 500);


    }

    private void createWindow() {


        mWin = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        mWinView = new ImageView(this);
        mWinView.setBackgroundColor(Color.RED);

        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
        params.type = WindowManager.LayoutParams.TYPE_PHONE;
        params.height = 1;
        params.width = 1;
        params.gravity = Gravity.LEFT;

//        mWin.addView(mWinView,params);


    }


    public void onDestroy() {
        super.onDestroy();


        if (mSenManager != null) {
            mSenManager.unregisterListener(sensorListener);

        }

        if (unlockRecevice != null) {


//            unregisterReceiver(unlockRecevice);
        }

        if (mWin != null) {
            try {
                mWin.removeView(mWinView);
            } catch (Exception e) {
                e.printStackTrace();

            }


        }

    }


    public class SmartBinder extends Binder implements OperationLock {


        public SmartService getService() {

            return SmartService.this;
        }


        public void startLock() {


            if (!sensorListener.isEnableLock()) {
                mSenManager.registerListener(sensorListener, mSenManager.getDefaultSensor(Sensor.TYPE_PROXIMITY), SensorManager.SENSOR_DELAY_NORMAL);
            }
            sensorListener.startLock();
        }

        @Override
        public void stopLock() {
            sensorListener.stopLock();
            mSenManager.unregisterListener(sensorListener);
        }

        @Override
        public void setEnLockTime(long time) {
            sensorListener.setEnLockTime(time);
        }


        public void setUnLockInterval(long time) {
            sensorListener.setUnLockInterval(time);

        }


        public void setAutoTime(long time) {
            sensorListener.setAutoTime(time);
        }


        public void allowLighting() {
            sensorListener.allowLighting();
        }


        public void disallowLighting() {
            sensorListener.disallowLighting();
        }
    }


}
