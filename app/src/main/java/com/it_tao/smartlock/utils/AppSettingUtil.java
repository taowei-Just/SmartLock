package com.it_tao.smartlock.utils;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.accessibility.AccessibilityManager;

import java.util.Iterator;
import java.util.List;

import huanyang.gloable.gloable.utils.LogUtil;

/**
 * Created by Tao on 2017/11/27 0027.
 */

public class AppSettingUtil {

    private static String TAG = "AppSettingUtil";

    /**
     * 检测辅助功能是否开启<br>
     * 方 法 名：isAccessibilitySettingsOn <br>
     * 创 建 人 <br>
     * 创建时间：2016-6-22 下午2:29:24 <br>
     * 修 改 人： <br>
     * 修改日期： <br>
     * @param mContext
     * @return boolean
     */
    public static boolean isAccessibilitySettingsOn(Context mContext , String packageName,String classPath) {
        int accessibilityEnabled = 0;
        // TestService为对应的服务
//        final String service = getPackageName() + "/" + TestService.class.getCanonicalName();
        LogUtil.i(TAG, "service:" + classPath);
        // com.z.buildingaccessibilityservices/android.accessibilityservice.AccessibilityService
        classPath = packageName+"/"+classPath;
        try {
            accessibilityEnabled = Settings.Secure.getInt(mContext.getApplicationContext().getContentResolver(),
                    Settings.Secure.ACCESSIBILITY_ENABLED);
            LogUtil.e(TAG, "accessibilityEnabled = " + accessibilityEnabled);
        } catch (Settings.SettingNotFoundException e) {
            LogUtil.e(TAG, "Error finding setting, default accessibility to not found: " + e.getMessage());
        }
        TextUtils.SimpleStringSplitter mStringColonSplitter = new TextUtils.SimpleStringSplitter(':');

        if (accessibilityEnabled == 1) {
            LogUtil.e(TAG, "***ACCESSIBILITY IS ENABLED*** -----------------");
            String settingValue = Settings.Secure.getString(mContext.getApplicationContext().getContentResolver(),
                    Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
            // com.z.buildingaccessibilityservices/com.z.buildingaccessibilityservices.TestService
            if (settingValue != null) {
                mStringColonSplitter.setString(settingValue);
                while (mStringColonSplitter.hasNext()) {
                    String accessibilityService = mStringColonSplitter.next();

                    LogUtil.e(TAG, "-------------- > accessibilityService :: " + accessibilityService + " " + classPath);
                    if (accessibilityService.equalsIgnoreCase(classPath)) {
                        LogUtil.e(TAG, "We've found the correct setting - accessibility is switched on!");
                        return true;
                    }
                }
            }
        } else {
            LogUtil.e(TAG, "***ACCESSIBILITY IS DISABLED***");
        }
        return false;
    }

    public static boolean isAccessibleEnabled(Context paramContext, String packageName,String classPath)
    {
        Log.e("info", "start");
        AccessibilityManager localAccessibilityManager;
        try
        {
            localAccessibilityManager = (AccessibilityManager)paramContext.getSystemService(Context.ACCESSIBILITY_SERVICE);
            if (localAccessibilityManager == null)
            {
                Log.e("manager", "is null");
                return false;
            }
            if (!localAccessibilityManager.isEnabled())
            {
                Log.e("manager", "is not enable");
                return false;
            }
        }
        catch (Exception localException)
        {
            localException.printStackTrace();
            return false;
        }
        Log.e("manager", "is enable");
        List localList = localAccessibilityManager.getEnabledAccessibilityServiceList(-1);
        if (localList == null)
        {
            Log.e("runningServices", "is not null");
            return false;
        }
        Iterator localIterator = localList.iterator();
        boolean  i;
        while (true)
        {
            boolean bool = localIterator.hasNext();
            i = false;
            if (!bool)
                break;
            AccessibilityServiceInfo localAccessibilityServiceInfo = (AccessibilityServiceInfo)localIterator.next();
            if ((localAccessibilityServiceInfo == null) || (localAccessibilityServiceInfo.getId() == null))
                continue;
            Log.e("包名：", localAccessibilityServiceInfo.getId());
            Log.e("包名2", paramContext.getPackageName() + "/com.wwxyz.wd.service.MoneyService");
            if (!localAccessibilityServiceInfo.getId().equals(packageName + "/"+classPath))
                continue;
            Log.d("ssss", "isAccessibleEnabled: true");
            i = true;
        }
        return i;
    }



    public static  void jumpAccessibility (Context mContext){

        Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
        mContext.startActivity(intent);
    }




}
