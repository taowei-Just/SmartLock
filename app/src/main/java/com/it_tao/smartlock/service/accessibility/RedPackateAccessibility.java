package com.it_tao.smartlock.service.accessibility;

import android.accessibilityservice.AccessibilityService;
import android.content.Context;
import android.os.Handler;
import android.view.accessibility.AccessibilityEvent;

import com.it_tao.smartlock.redpackate.AialylHelper;
import com.it_tao.smartlock.redpackate.PacketSettings;
import com.it_tao.smartlock.redpackate.QQhelper;
import com.it_tao.smartlock.redpackate.WechartHelper;

import huanyang.gloable.gloable.Shared;
import huanyang.gloable.gloable.utils.LogUtil;
import huanyang.gloable.gloable.utils.SharedUtlis;

/**
 * Created by Tao on 2017/11/28 0028.
 */

public class RedPackateAccessibility extends AccessibilityService {

    private WechartHelper wechartHelper;
    private QQhelper qQhelper;
    private AialylHelper aialylHelper;
    private String TAG = getClass().getName();
    private PacketSettings wechartSeetings;
    private SharedUtlis sharedUtlis;
    private PacketSettings qqSeetings;


    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        try {
            sortingType(event);
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.e(TAG, e.toString());
        }

    }

    private void preformWechartData() {

        boolean isWechartOpen = sharedUtlis.getBoolean(Shared.isWechartOpen, false);
        boolean isWechartJumpDelay = sharedUtlis.getBoolean(Shared.isWechartJumpDelay, false);
        boolean isWechartRobDelay = sharedUtlis.getBoolean(Shared.isWechartRobDelay, false);
        boolean isWechartOpenLight = sharedUtlis.getBoolean(Shared.isWechartOpenLight, false);
        boolean isWechartRandomJumpTime = sharedUtlis.getBoolean(Shared.isWechartRandomJumpTime, false);
        boolean isWechartRandomRobTime = sharedUtlis.getBoolean(Shared.isWechartRandomRobTime, false);
        boolean isWechartSelf = sharedUtlis.getBoolean(Shared.isWechartSelf, false);
        String wechartJumpTime = sharedUtlis.getString(Shared.wechartJumpTime, "");
        String wechartRobTime = sharedUtlis.getString(Shared.wechartRobTime, "");
        boolean isAudioOpen = sharedUtlis.getBoolean(Shared.isAudioOpen, false);
        boolean isBeepOpen = sharedUtlis.getBoolean(Shared.isBeepOpen, false);
        wechartSeetings = new PacketSettings();

        wechartSeetings.setOpen(isWechartOpen);

        wechartSeetings.setAudioOpen(isAudioOpen);
        wechartSeetings.setBeepOpen(isBeepOpen);

        wechartSeetings.setIsdaleyJump(isWechartJumpDelay);

        wechartSeetings.setTimeWaiting(isWechartRobDelay);

        wechartSeetings.setAutoLight(isWechartOpenLight);
        wechartSeetings.setRandomJumpTime(isWechartRandomJumpTime);
        wechartSeetings.setRandomRobTime(isWechartRandomRobTime);
        wechartSeetings.setSelfPacket(isWechartSelf);
        try {

            Integer DaleyJumpTime = Integer.valueOf(wechartJumpTime);
            wechartSeetings.setDaleyJumpTime(DaleyJumpTime);

        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
            Integer DaleyJumpTime = Integer.valueOf(wechartRobTime);
            wechartSeetings.setWaitTime(DaleyJumpTime);
        } catch (Exception e) {
            e.printStackTrace();
        }

        LogUtil.e(TAG, " wechart 抢红包参数 " + wechartSeetings.toString());

        wechartHelper.setPacketSettings(wechartSeetings);
    }

    private void preformQQData() {


        boolean isQQOpen = sharedUtlis.getBoolean(Shared.isQQOpen, false);
        boolean isQQJumpDelay = sharedUtlis.getBoolean(Shared.isQQJumpDelay, false);
        boolean isQQRobDelay = sharedUtlis.getBoolean(Shared.isQQRobDelay, false);
        boolean isQQOpenLight = sharedUtlis.getBoolean(Shared.isQQOpenLight, false);
        boolean isQQRandomJumpTime = sharedUtlis.getBoolean(Shared.isQQRandomJumpTime, false);
        boolean isQQRandomRobTime = sharedUtlis.getBoolean(Shared.isQQRandomRobTime, false);

        boolean isQQSelf = sharedUtlis.getBoolean(Shared.isQQSelf, false);
        boolean isAudioOpen = sharedUtlis.getBoolean(Shared.isAudioOpen, false);
        boolean isBeepOpen = sharedUtlis.getBoolean(Shared.isBeepOpen, false);

        String QQJumpTime = sharedUtlis.getString(Shared.QQJumpTime, "");
        String QQRobTime = sharedUtlis.getString(Shared.QQRobTime, "");

        qqSeetings = new PacketSettings();

        qqSeetings.setOpen(isQQOpen);
        qqSeetings.setAudioOpen(isAudioOpen);
        qqSeetings.setBeepOpen(isBeepOpen);

        qqSeetings.setIsdaleyJump(isQQJumpDelay);

        qqSeetings.setTimeWaiting(isQQRobDelay);

        qqSeetings.setAutoLight(isQQOpenLight);
        qqSeetings.setRandomJumpTime(isQQRandomJumpTime);
        qqSeetings.setRandomRobTime(isQQRandomRobTime);
        qqSeetings.setSelfPacket(isQQSelf);
        try {

            Integer DaleyJumpTime = Integer.valueOf(QQJumpTime);
            qqSeetings.setDaleyJumpTime(DaleyJumpTime);

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Integer DaleyJumpTime = Integer.valueOf(QQRobTime);
            qqSeetings.setWaitTime(DaleyJumpTime);
        } catch (Exception e) {
            e.printStackTrace();
        }

        LogUtil.e(TAG, " QQ抢红包参数 " + qqSeetings.toString());

        qQhelper.setPacketSettings(qqSeetings);
    }

    private void sortPackage(AccessibilityEvent event, boolean isNotify) {
        CharSequence packageName = event.getPackageName();


        if (packageName.equals("com.tencent.mobileqq")) {
//qq

            LogUtil.e(TAG, "QQ事件" + event.toString() + "\n  " + event.getClassName());

            preformQQData();
            if (qQhelper == null || qQhelper.getPacketSettings() == null || !qQhelper.getPacketSettings().isOpen()) {
                return;
            }

            if (isNotify) {
                qQhelper.checkNotify(event);
            } else {
                qQhelper.checkContent(event, this);

            }


        } else if (packageName.equals("com.tencent.mm")) {

            preformWechartData();


// 微信
            LogUtil.e(TAG, "微信事件" + event.toString() + "\n  " + event.getClassName());

            if (wechartHelper == null || wechartHelper.getPacketSettings() == null || !wechartHelper.getPacketSettings().isOpen()) {
                return;
            }
            if (isNotify) {
                wechartHelper.checkNotify(event);

            } else {
                if (event.getClassName().toString().equals("com.tencent.mm.plugin.luckymoney.ui.En_fba4b94f") || event.getClassName().toString().equals("com.tencent.mm.plugin.luckymoney.ui.LuckyMoneyReceiveUI")) {
                    if (!event.getText().contains("正在加载"))
                        wechartHelper.openPacket(event, this);

                } else if (event.getClassName().toString().equals("com.tencent.mm.plugin.luckymoney.ui.LuckyMoneyDetailUI")) {


                    wechartHelper.backMain(event, this);

                } else if (event.getClassName().toString().equals("com.tencent.mm.ui.base.p")) {


                } else if (event.getClassName().toString().equals("com.tencent.mm.ui.LauncherUI")) {

                } else {
                    wechartHelper.checkContent(event, this);
                }
            }

        } else if (packageName.equals("")) {
            if (isNotify) {

            } else {

            }

        } else if (event.getClassName().equals("cooperation.qwallet.plugin.QWalletPluginProxyActivity")) {

            qQhelper.backMain(event, this);


        }
    }

    private void sortingType(AccessibilityEvent event) {

        switch (event.getEventType()) {


            case AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED:

                sortPackage(event, true);

                break;

            case AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED:
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:
            case AccessibilityEvent.TYPE_WINDOWS_CHANGED:

                sortPackage(event, false);
                break;

        }
    }

    @Override
    public void onInterrupt() {

    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();


        initData();

    }

    private void initData() {

        sharedUtlis = new SharedUtlis(this, Shared.config);
        wechartHelper = new WechartHelper(this, wechartSeetings);
        qQhelper = new QQhelper(this);
        aialylHelper = new AialylHelper(this);

    }
}
