package com.it_tao.smartlock.recevice;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.it_tao.smartlock.health.HealthService;
import com.it_tao.smartlock.service.SmartService;
import com.it_tao.smartlock.helper.SmartHelper;
import com.it_tao.smartlock.utils.ChackAppStats;
import com.jaredrummler.android.processes.ProcessManager;
import com.jaredrummler.android.processes.models.AndroidAppProcess;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import huanyang.gloable.gloable.utils.LogUtil;

/**
 * Created by Administrator on 2017/3/23 0023.
 */

public class UnlockRecevice extends BroadcastReceiver {

    private int killSize = 3;
    private ConnectivityManager mConnectivityManager;
    private NetworkInfo netInfo;
    private String TAG = getClass().getName();

    public UnlockRecevice() {


    }


    //    private  String  SYSTAM_UPDATA_PACKGE_NAME = "com.it_tao.netstateutils" ;
    private String SYSTAM_UPDATA_PACKGE_NAME = "com.qiku.android.ota";
    private String SYSTAM_UPDATA_PACKGE_NAME_N4S = "com.yulong.android.ota";

    private ArrayList<String> mKillArray = new ArrayList<String>();
    SmartHelper helper;

    public UnlockRecevice(SmartHelper screenState) {
        this.helper = screenState;
        mKillArray.add(SYSTAM_UPDATA_PACKGE_NAME);
        mKillArray.add(SYSTAM_UPDATA_PACKGE_NAME_N4S);

    }


    public void onReceive(final Context context, Intent intent) {
        String action = intent.getAction();
        SharedPreferences config = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = config.edit();
        switch (action) {
            case Intent.ACTION_BOOT_COMPLETED:
                edit.putBoolean(ChackAppStats.CONFIG_RECEIVE_BOOT, true);
                edit.commit();

                Toast.makeText(context, "开机啦!", Toast.LENGTH_LONG).show();

                Intent sIntent = new Intent(context, SmartService.class);
                sIntent.putExtra("fromeBoot", true);

                context.startService(sIntent);

                sIntent = new Intent(context, HealthService.class);
                intent.putExtra("isFirst", true);
                context.startService(sIntent);

//                chackKillProcess(context);
                break;

            case Intent.ACTION_SCREEN_ON:
//                Toast.makeText(context, "屏幕打开!", Toast.LENGTH_SHORT).show();
                helper.setScreen(true);
//                chackKillProcess(context);

                break;


            case Intent.ACTION_SCREEN_OFF:

                LogUtil.e(TAG,"屏幕关闭");
                helper.setScreen(false);
                SmartHelper smartHelper = SmartHelper.getinstance(context);

                smartHelper.unlock();

                break;

            case Intent.ACTION_SHUTDOWN:
                //重置开机启动标志 
                Toast.makeText(context, "准备关机!", Toast.LENGTH_LONG).show();
                edit.putBoolean(ChackAppStats.CONFIG_RECEIVE_BOOT, false);
                edit.commit();
                break;

            case ConnectivityManager.CONNECTIVITY_ACTION:
                chackKillProcess(context);
                mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                netInfo = mConnectivityManager.getActiveNetworkInfo();
                if (netInfo != null && netInfo.isAvailable()) {

                    /////////////网络连接
                    String name = netInfo.getTypeName();

                    if (netInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                        /////WiFi网络

                    } else if (netInfo.getType() == ConnectivityManager.TYPE_ETHERNET) {
                        /////有线网络

                    } else if (netInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                        /////////3g网络

                    }


                } else {
                    ////////网络断开
                    Toast.makeText(context, "无网络", Toast.LENGTH_SHORT).show();
                }
        }


    }

    private void chackKillProcess(final Context context) {

        new Thread(new Runnable() {

            public void run() {

                for (int h = 0; h < killSize; h++) {
                    try {
                        Thread.sleep(1 * 400);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        final ActivityManager mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

                        List<AndroidAppProcess> runningProcesses = ProcessManager.getRunningAppProcesses();

                        Log.e("屏幕打开 ", "processName :" + +runningProcesses.size());

                        if (runningProcesses != null && runningProcesses.size() > 0) {

                            for (AndroidAppProcess info : runningProcesses) {

                                if (info != null) {
                                    final String processName = info.getPackageName();

                                    Log.e("应用列表", "processName :" + processName + " " + runningProcesses.size());

                                    if (mKillArray != null && mKillArray.size() > 0) {

                                        for (int i = 0; i < mKillArray.size(); i++) {

                                            if (processName.equals(mKillArray.get(i))) {

                                                int pid = info.pid;
                                                int indext = runningProcesses.indexOf(info);
                                                if (isProcessInTheForeground(runningProcesses, pid)) {

                                                    Log.e("屏幕打开", "在前台  杀死他 !" + processName);

                                                    Intent intent1 = new Intent(Intent.ACTION_MAIN);
                                                    intent1.addCategory(Intent.CATEGORY_HOME);
                                                    intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                                                    context.startActivity(intent1);

                                                    new Thread(new Runnable() {

                                                        public void run() {
                                                            try {
                                                                Thread.sleep(1 * 10);
                                                            } catch (InterruptedException e) {
                                                                e.printStackTrace();
                                                            } finally {

                                                                mActivityManager.killBackgroundProcesses(processName);
                                                            }
                                                        }
                                                    }).start();

                                                } else {
                                                    Log.e("屏幕打开", "不在前台  杀死他 !" + processName);
                                                    mActivityManager.killBackgroundProcesses(processName);
//                                mActivityManager.restartPackage(processName);
                                                }

                                            }


                                        }

                                    }


                                }


                            }
                        }


                    }

                }
            }
        }).start();
    }


    public boolean isProcessInTheForeground(List<AndroidAppProcess> processes, int pid) {
//        List processes = ProcessManager.getRunningAppProcesses();
//        int myPid = Process.myPid();
        Iterator iterator = processes.iterator();

        AndroidAppProcess process;
        do {
            if (!iterator.hasNext()) {
                return false;
            }

            process = (AndroidAppProcess) iterator.next();
        } while (process.pid != pid || !process.foreground);

        return true;
    }


    public void updataKill(String packName) {
        mKillArray.add(packName);
    }

    public void updataKill(ArrayList<String> list) {
        mKillArray.addAll(list);
    }

}
