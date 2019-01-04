package com.it_tao.smartlock.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.SystemClock;
import android.util.Log;

/**
 * Created by Administrator on 2017/4/15 0015.
 */

public class ChackAppStats {

    //已申请权限系统允许
    public  final static int BOOT_COMPLIT_ALLOW = 0x000001;
    //已申请权限系统不允许
    public  final static int BOOT_COMPLIT_SYSTEM_DISALLOW = 0x000002;
    //未申请权限
    public  final static int BOOT_COMPLIT_PREMONITION_MISS = 0x000003;
    // 未完成一次开机过程  无法检测
    public  final static int BOOT_COMPLIT_CONNOT_DETECTION = 0x000004;
    
    public  final static String CONFIG_RECEIVE_BOOT = "IsReceiveBoot";

 


    public static int chackBootComplit(Context context) {

        //检查权限 
        boolean  hasPermission  = false ;

        PackageManager packageManager = context.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), PackageManager.GET_PERMISSIONS);

            String[] permissions = packageInfo.requestedPermissions;
            long firstInstallTime = packageInfo.firstInstallTime;
            long bootComplit = System.currentTimeMillis() - SystemClock.uptimeMillis();

            Log.e(" permission","系统权限 ： " +  permissions.toString() + " 第一次开机时间 ： " + bootComplit 
                    +" 应用安装时间 ：" +   firstInstallTime  +"  转换时间 " + ( firstInstallTime - bootComplit)/(60*60*1000)  + " 小时");
            
            
            for (String s : permissions) {
                System.out.println(s);
                if (s.equals("android.permission.RECEIVE_BOOT_COMPLETED")){
                    hasPermission = true ;
                }
            }
          
            if(!hasPermission)
                return  BOOT_COMPLIT_PREMONITION_MISS ;

            // 检查权限使用
            //   最近一次开机 发生在什么时间 是否在app安装之前 
            SharedPreferences config = context.getSharedPreferences("config", Context.MODE_PRIVATE);
    
            boolean isReceiveBoot = config.getBoolean(ChackAppStats.CONFIG_RECEIVE_BOOT, false);
            
            if (bootComplit > firstInstallTime){
                //开机时间大于安装时间 在app 安装后开过机器 
 
                if (isReceiveBoot){
                    //允许开机启动
                    return BOOT_COMPLIT_ALLOW ;
                }else {
                     
                    return  BOOT_COMPLIT_SYSTEM_DISALLOW ;
                }
 
            }else {
                
                return BOOT_COMPLIT_CONNOT_DETECTION ;
                
                
                
            }
             

        } catch (PackageManager.NameNotFoundException e) {
            Log.e(" permission" , e.toString());
        }

        return BOOT_COMPLIT_CONNOT_DETECTION;

    }
}
