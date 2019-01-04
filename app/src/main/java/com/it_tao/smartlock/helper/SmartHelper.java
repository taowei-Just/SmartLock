package com.it_tao.smartlock.helper;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.KeyguardManager;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.PowerManager;
import android.util.Log;
import android.widget.Toast;

import com.it_tao.smartlock.recevice.MyAdiminRecever;

/**
 * Created by Administrator on 2017/3/22 0022.
 */

public class SmartHelper {

    private static SmartHelper smartHelper = null;
    Context context;
    String TAG = "SmartLock_Tag";
    private ComponentName componentName;
    private DevicePolicyManager devicePolicyManager;
    private MyAdiminRecever myAdmin;
    private PowerManager mPowerManager;
    private KeyguardManager mKeyguardManager;
    private PowerManager.WakeLock mWakeLock;
    private KeyguardManager.KeyguardLock mKeyguardLock;


    private boolean isScreen = false;


    public static SmartHelper getinstance(Context context) {
        if (smartHelper == null) {

            SmartHelper helper = new SmartHelper();
            helper.init(context);
            smartHelper = helper;
            return helper;
        } else {
            return smartHelper;
        }

    }

    private void init(Context context) {
        this.context = context;

        devicePolicyManager = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
        mPowerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        mKeyguardManager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);

        mWakeLock = mPowerManager.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP   |PowerManager.SCREEN_DIM_WAKE_LOCK, TAG);

        mKeyguardLock = mKeyguardManager.newKeyguardLock(TAG);

//组件名称
        componentName = new ComponentName(context, MyAdiminRecever.class);

    }


    public void lockScreen() {

        if (chackActive()) {
//锁屏
            if(isLocked()) {
                Log.e("屏幕打开" , "SmartHelper：  锁屏" );
                devicePolicyManager.lockNow();
            }
        } else {
            Toast.makeText(context, "请先开启管理员权限!", Toast.LENGTH_SHORT).show();
//            showOpnDialog();

        }

    }


    private void showOpnDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(context, AlertDialog.THEME_HOLO_LIGHT)
                .setTitle("请开启设备权限")
                .setMessage("开启权限后就可以锁/开屏啦！")
                .setNegativeButton("去设置", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setCancelable(false);

        if (context != null && context instanceof Activity) {
            builder.create().show();
        }

    }

    //开屏
    public void unlock() {
        if (!isLocked()) {
            Log.e("屏幕打开", "SmartHelper：  亮屏  " + isLocked());
            mWakeLock.acquire(200);
            mWakeLock.release();
            mKeyguardLock.reenableKeyguard();
            mKeyguardLock.disableKeyguard();
        }
    }


    public boolean chackActive() {

        if (devicePolicyManager.isAdminActive(componentName)) {

            return true;

        } else {


            return false;
        }
    }


    public void openAdmin(Context context,String str) {

        Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);

        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName);

        if (str == null)
        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "开启我你就可以锁屏啦！！");
 else
            intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, str);

        context.startActivity(intent);

        Toast.makeText(context, "请开启管理员权限!", Toast.LENGTH_SHORT).show();

    }

 
    public boolean isLocked() {

        return mPowerManager.isScreenOn();

    }

    public void setScreen(boolean screen) {
        isScreen = screen;
    }
}
