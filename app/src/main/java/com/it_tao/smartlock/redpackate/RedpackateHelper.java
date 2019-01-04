package com.it_tao.smartlock.redpackate;

import android.accessibilityservice.AccessibilityService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.it_tao.smartlock.helper.SmartHelper;
import com.it_tao.smartlock.service.audio.AudioService;

import java.util.List;
import java.util.Random;

import huanyang.gloable.gloable.utils.LogUtil;

/**
 * Created by Tao on 2017/11/28 0028.
 */

public class RedpackateHelper {

    private SmartHelper smartHelper;
    public PacketSettings packetSettings;
    AccessibilityService service;

    public PacketSettings getPacketSettings() {
        return packetSettings;
    }

    public void setPacketSettings(PacketSettings packetSettings) {
        this.packetSettings = packetSettings;
    }

    public RedpackateHelper(Context context) {

        smartHelper = SmartHelper.getinstance(context);

    }

    private String TAG = getClass().getName();

    public RedpackateHelper(Context context, PacketSettings packetSettings) {

        service = (AccessibilityService) context;
        smartHelper = SmartHelper.getinstance(context);

        this.packetSettings = packetSettings;


    }

    public void checkNotify(AccessibilityEvent event, String s) {

        if (event != null) {
            List<CharSequence> text = event.getText();
            LogUtil.e(TAG, " 状态栏文字 " + text.toString());

            if (text != null) {
                for (CharSequence str : text) {
                    if (!TextUtils.isEmpty(str) && str.toString().contains(s)) {
                        // 解锁
                        // 跳转
                        LogUtil.e(TAG, "状态栏跳转 ");

                        playRedPacketAudio(service.getApplicationContext());

                        playRedPacketBeep(service.getApplicationContext());

                        Notification parcelableData = (Notification) event.getParcelableData();
                        final PendingIntent contentIntent = parcelableData.contentIntent;

                        if (packetSettings != null && packetSettings.isdaleyJump()) {

                            long time;
                            if (packetSettings.isRandomJumpTime()) {
                                time = new Random().nextInt((int) packetSettings.getDefauleRanomtime());

                            } else {

                                time = packetSettings.getDaleyJumpTime();
                            }
                            LogUtil.e(TAG, "延时抢跳转时间 ：" + time);

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        contentIntent.send();
                                    } catch (PendingIntent.CanceledException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }, time);


                        } else {
                            try {
                                contentIntent.send();
                            } catch (PendingIntent.CanceledException e) {
                                e.printStackTrace();
                            }
                        }


                        if (packetSettings != null) {
                            if (packetSettings.isAutoLight())
                                openScreen();
                        } else {
                            openScreen();
                        }
                    }
                }
            }
        }
    }

    public void openScreen() {
        if (smartHelper != null) {
            smartHelper.unlock();
        }

    }


    public void preformClick(AccessibilityNodeInfo info, String classname) {

        if (info == null)
            return;
        if (info.isClickable()) {
            LogUtil.e(TAG, "点击 " + " \n" + info.getClassName());
            if (info.performAction(AccessibilityNodeInfo.ACTION_CLICK)) {
                return;
            }

        } else {

            List<AccessibilityNodeInfo> redpacket = info.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/ada");
            if (redpacket!=null&&redpacket.size()>0){
                for (AccessibilityNodeInfo i :redpacket ){

                    if (i!=null){
                        if (i.performAction(AccessibilityNodeInfo.ACTION_CLICK)) {
                            return;
                        }
                    }
                }
            }
            LogUtil.e(TAG, "向上查找 " + info.toString() + " \n" + info.getClassName());
            preformClick(info.getParent(), null);
        }

    }

    public void backMain(AccessibilityEvent event, final AccessibilityService service) {

        AccessibilityNodeInfo activeWindow = service.getRootInActiveWindow();

        if (activeWindow != null)
            LogUtil.e(TAG, " 返回 " + activeWindow.getChildCount() + "\n  " + event.getClassName());

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                service.performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);
            }
        }, 1000);
    }

    public void playRedPacketAudio(Context context) {
        if (packetSettings.isAudioOpen) {
            Intent service = new Intent(context, AudioService.class);
            service.putExtra("from", "redpacket");
            context.startService(service);
        }
    }

    public void playRedPacketBeep(Context context) {
        if (packetSettings.isBeepOpen) {
            Intent service = new Intent(context, AudioService.class);
            service.putExtra("beep", true);
            context.startService(service);
        }
    }
}
