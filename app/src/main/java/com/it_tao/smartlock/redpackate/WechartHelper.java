package com.it_tao.smartlock.redpackate;

import android.accessibilityservice.AccessibilityService;

import android.content.Context;
import android.graphics.Rect;
import android.os.Handler;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import huanyang.gloable.gloable.utils.LogUtil;

/**
 * Created by Tao on 2017/11/28 0028.
 */

public class WechartHelper extends RedpackateHelper {

    private String TAG = getClass().getName();
    private static Integer preformClickTop = -1;
    private static ArrayList<Integer> preformClickTopS = new ArrayList<>();

    public WechartHelper(Context context) {
        super(context);
    }

    public WechartHelper(Context context, PacketSettings packetSettings) {
        super(context, packetSettings);
    }

    private void serchContent() {
    }

    public void checkContent(AccessibilityEvent event, AccessibilityService service) {

        if (event != null) {
            AccessibilityNodeInfo source = event.getSource();
            if (source == null) {

            }
            source = service.getRootInActiveWindow();
            if (source == null) {
                return;
            }

            LogUtil.e(TAG, "检查内容" + source.toString() + "\n  " + source.getClassName());

            List<AccessibilityNodeInfo> nodeInfosByText = source.findAccessibilityNodeInfosByText("领取红包");
            List<AccessibilityNodeInfo> currentPagerText = source.findAccessibilityNodeInfosByText("当前所在页面");
            List<AccessibilityNodeInfo> alreadyGetText = source.findAccessibilityNodeInfosByText("领取了");
            List<AccessibilityNodeInfo> selfGetText = source.findAccessibilityNodeInfosByText("你领取了");
            List<AccessibilityNodeInfo> timeTextByID = source.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/a2");
            List<AccessibilityNodeInfo> packetTextByID = source.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/c2i");

//            LogUtil.e(TAG, "准备检查自己 packetSettings==null ? " + String.valueOf(packetSettings == null));

            if (packetTextByID!=null&&packetTextByID.size()>0){
                 openPacket(event, service);
            }

            if (packetSettings != null && packetSettings.isSelfPacket()) {
                LogUtil.e(TAG, "抢自己的红包执行");
                boolean isCrowd = checkIsCerowd(currentPagerText);
                matchingRect(source, nodeInfosByText, alreadyGetText, timeTextByID);
                if (isCrowd) {
                    List<AccessibilityNodeInfo> selfPacket = source.findAccessibilityNodeInfosByText("查看红包");
                    matchingRect(source, selfPacket, selfGetText, timeTextByID);
//                matchingSelfRect(selfPacket);
                }
            }

            AccessibilityNodeInfo rootInActiveWindow = service.getRootInActiveWindow();

            List<AccessibilityNodeInfo> contactMoreID = source.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/aa6");
            boolean matchMoreID = false;
            if (!matchHeadID(contactMoreID)) {
                if (rootInActiveWindow != null) {
                    contactMoreID = rootInActiveWindow.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/aa6");
                    matchMoreID = matchHeadID(contactMoreID);
                }
            } else {
                matchMoreID = true;
            }

            if (!matchMoreID) {
                List<AccessibilityNodeInfo> contactPacketID = source.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/aol");
                List<AccessibilityNodeInfo> contactPacketIDApv = source.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/apv");

                if (!matchingContactRect(contactPacketID)) {
                    if (rootInActiveWindow != null) {
                        List<AccessibilityNodeInfo> accessibilityNodeInfosByViewId = rootInActiveWindow.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/aol");
                        LogUtil.e(TAG, "rootWindow");
                        matchingContactRect(accessibilityNodeInfosByViewId);
                    }
                }

                if (!matchingContactRect(contactPacketIDApv)) {
                    if (rootInActiveWindow != null) {
                        List<AccessibilityNodeInfo> accessibilityNodeInfosByViewId = rootInActiveWindow.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/apv");
                        LogUtil.e(TAG, "rootWindow");
                        matchingContactRect(accessibilityNodeInfosByViewId);
                    }
                }
            }

        }

        // 开红包

    }

    private boolean matchHeadID(List<AccessibilityNodeInfo> contactHeadID) {
        if (contactHeadID != null && contactHeadID.size() > 0) {
//            for (AccessibilityNodeInfo info : contactHeadID) {
//
//                LogUtil.e(TAG,"更多标签" + info.toString());
//            }
            return true;
        }
        return false;
    }

    private boolean matchingContactRect(List<AccessibilityNodeInfo> contactPacket) {

        if (contactPacket != null && contactPacket.size() > 0) {

            for (AccessibilityNodeInfo info : contactPacket) {

                String s = info.getText().toString();
                if (s.contains("[微信红包]") && s.length() > 6) {
                    LogUtil.e(TAG, " 得到聊天界面红包 " + info.toString());
                    preformClick(info, null);
                    return true;
                }
            }

        }
        return false;
    }

    private void matchingSelfRect(List<AccessibilityNodeInfo> selfPacket) {
        if (selfPacket != null && selfPacket.size() > 0) {

            AccessibilityNodeInfo info = selfPacket.get(selfPacket.size() - 1);

            preformClick(info, null);

        }
    }

    private boolean checkIsCerowd(List<AccessibilityNodeInfo> currentPagerText) {

        if (currentPagerText != null && currentPagerText.size() > 0) {
            for (AccessibilityNodeInfo info : currentPagerText) {

                if (info != null) {
                    CharSequence text = info.getContentDescription();
                    LogUtil.e(TAG, " 判断当前与谁聊天  " + text);

                    try {

                        String str = text.toString();

                        if (str.contains(")的聊天")) {
                            String[] split = str.split("\\)的聊天");
                            String s1 = split[split.length - 1];

                            String[] split1 = s1.split("\\(");

                            String s2 = split1[split1.length - 1];

                            LogUtil.e(TAG, "得到群聊天人数！" + s2);

                            if (Integer.valueOf(s2) > 2)
                                return true;
                        }
                    } catch (Exception e) {
                        e.getStackTrace();
                    }
                }

            }
        }
        return false;
    }

    private void matchingRect(AccessibilityNodeInfo source, List<AccessibilityNodeInfo> nodeInfosByText, List<AccessibilityNodeInfo> alreadyGetText, List<AccessibilityNodeInfo> timeTextByID) {

        Map<Integer, AccessibilityNodeInfo> alreadyInfoMap = null;
        ArrayList<Integer> alreadyTops = null;

        alreadyInfoMap = new HashMap<>();
        alreadyTops = new ArrayList<>();

        if (nodeInfosByText != null) {
            if (alreadyGetText != null && alreadyGetText.size() > 0) {

                for (int i = alreadyGetText.size() - 1; i >= 0; i--) {
                    AccessibilityNodeInfo info = alreadyGetText.get(i);
                    Rect rect = new Rect();
                    info.getBoundsInScreen(rect);
                    LogUtil.e(TAG, " getSource  找到领取信息 " + info.toString() + " 位置 ：" + rect.toString() + " \n" + source.getClassName());

                    int top = rect.top;
                    alreadyInfoMap.put(top, info);
                    alreadyTops.add(top);
                }
            }

            if (nodeInfosByText.size() > 1) {

                Map<Integer, AccessibilityNodeInfo> nodeInfoMap = new HashMap<>();

                ArrayList<Integer> nodeTops = new ArrayList<>();
                for (int i = nodeInfosByText.size() - 1; i >= 0; i--) {
                    AccessibilityNodeInfo info = nodeInfosByText.get(i);
                    Rect rect = new Rect();
                    info.getBoundsInScreen(rect);
                    LogUtil.e(TAG, " getSource  找到红包信息 " + info.toString() + " 位置 ：" + rect.toString() + " \n" + source.getClassName());
                    int top = rect.top;
                    nodeInfoMap.put(top, info);
                    nodeTops.add(top);
                }


                // 获取红包最小top
                // 获取领取文字top 比对 小于红包top的去除
                // 对比红包与文字数量
                // 红包数量大于文字数量则有红包为领取
                // 从红包top值最大值处开始领取红包

                LogUtil.e(TAG, "nodeTops" + nodeTops.toString());
                LogUtil.e(TAG, " alreadyTops " + alreadyTops.toString());

                Integer minNodeTop = -1;

                for (int i = 0; i < nodeTops.size(); i++) {

                    if (i == 0)
                        minNodeTop = nodeTops.get(i);

                    Integer nodeTop = nodeTops.get(i);
                    if (minNodeTop == -1)
                        minNodeTop = nodeTop > minNodeTop ? nodeTop : minNodeTop;
                    else
                        minNodeTop = nodeTop < minNodeTop ? nodeTop : minNodeTop;

                }


                for (int j = 0; j < alreadyTops.size(); j++) {

                    if (alreadyTops.get(j) < minNodeTop) {

                        alreadyTops.remove(alreadyTops.get(j));
                        j--;
                    }

                }

                if (nodeTops.size() > alreadyTops.size()) {
                    // 给红包排序

                    orderNodeTop(nodeTops);
                    // 删除已经点击的红包

                    LogUtil.e(TAG, "nodeTops 2" + nodeTops.toString() + " 上次点击过的节点 preformClickTop " + preformClickTop);

                    if (preformClickTopS.size() > 0) {
                        for (Integer preformClickTop : preformClickTopS) {
                            if (nodeTops.contains(preformClickTop)) {
                                nodeTops.remove(preformClickTop);
                            }
                        }
                    }
                    preformRecode(nodeInfoMap, nodeTops);
                    // 记录点击的红包位置
                }

            } else if (nodeInfosByText.size() == 1) {
                LogUtil.e(TAG, "  当前只有一个红包直接打开 ");
                AccessibilityNodeInfo info = nodeInfosByText.get(0);

                Rect rect = new Rect();
                info.getBoundsInScreen(rect);

                for (int i = 0; i < alreadyTops.size(); i++) {
                    Integer integer = alreadyTops.get(i);

                    if (integer > rect.top) {

                        LogUtil.e(TAG, " 文字位于红包下面 ");
                        return;
                    }
                }
                if (timeTextByID != null) {
                    for (AccessibilityNodeInfo timeInfo : timeTextByID) {

                        if (timeInfo == null)
                            continue;
                        Rect timeRect = new Rect();
                        timeInfo.getBoundsInScreen(timeRect);
                        if (timeRect.top > rect.top) {


                            LogUtil.e(TAG, " 时间位于红包下面 ");
                            return;
                        }


                    }

                }

                preformClick(info, null);
                preformClickTop = -1;
                preformClickTopS.clear();
            } else {

                LogUtil.e(TAG, " 未找到红包 滑动试试 " + source.toString() + " \n" + source.getClassName());
                if (source != null) {
                    for (int i = 0; i < source.getChildCount(); i++) {
//                        preformScroll(source);
                    }
                }

            }
        }
    }


    private void preformRecode(Map<Integer, AccessibilityNodeInfo> nodeInfoMap, ArrayList<Integer> nodeTops) {

        // 点击红包
        LogUtil.e(TAG, " 已点击红包集合 " + preformClickTopS.toString());

        if (preformClickTopS.size() > 0) {
            for (Integer preformClickTop : preformClickTopS) {
                if (nodeInfoMap.containsKey(preformClickTop))
                    nodeInfoMap.remove(preformClickTop);
            }
        }

        if (nodeTops.size() <= 0) {
            LogUtil.e(TAG, "  所有红包点击完毕 ");


            preformClickTopS.clear();
            return;
        }

        preformClickTop = nodeTops.get(0);

        preformClickTopS.add(preformClickTop);

        LogUtil.e(TAG, "  点击位置Top " + preformClickTop + "\n 已点击 " + preformClickTopS.toString());

        preformClick(nodeInfoMap.get(preformClickTop), null);

    }

    private void orderNodeTop(ArrayList<Integer> nodeTops) {

        for (int i = 0; i < nodeTops.size(); i++) {

            if (i == nodeTops.size() - 1)
                break;
            Integer integer = nodeTops.get(i + 1);
            Integer last = nodeTops.get(i);
            if (integer > last) {
                nodeTops.set(i, integer);
                nodeTops.set(i + 1, last);
            }
        }
    }

    private void checkPacketOpen() {



    }

    private void preformScroll(AccessibilityNodeInfo source) {

        if (source != null) {
            if (source.isScrollable()) {

                LogUtil.e(TAG, " 滑动当前 " + source.toString() + " \n" + source.getClassName());

                if (!source.performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD)) {
                    if (!source.performAction(AccessibilityNodeInfo.ACTION_SCROLL_BACKWARD))
                        return;
                }

            } else {
                preformScroll(source.getParent());
            }

        }

    }


    public void checkNotify(AccessibilityEvent event) {

        super.checkNotify(event, "[微信红包]");
    }


    // 拆开红包
    public void openPacket(final AccessibilityEvent event, final AccessibilityService service) {

        AccessibilityNodeInfo activeWindow = service.getRootInActiveWindow();
        AccessibilityNodeInfo source = event.getSource();
//        LogUtil.e(TAG, "打开红包" + activeWindow.getChildCount() + "\n  " + event.getClassName());

        List<AccessibilityNodeInfo> nodeInfosByText = activeWindow.findAccessibilityNodeInfosByText("派完了");
        List<AccessibilityNodeInfo> nodeInfosByText2 = activeWindow.findAccessibilityNodeInfosByText("超过24小时");
        List<AccessibilityNodeInfo> InfosByText = source.findAccessibilityNodeInfosByText("派完了");
        List<AccessibilityNodeInfo> InfosByText2 = source.findAccessibilityNodeInfosByText("超过24小时");

        if (
                (nodeInfosByText != null && nodeInfosByText.size() > 0)
                        || (InfosByText != null && InfosByText.size() > 0)
                        || (nodeInfosByText2 != null && nodeInfosByText2.size() > 0)
                        || (InfosByText2 != null && InfosByText2.size() > 0)
                ) {

            LogUtil.e(TAG, "发现派完了");

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    backMain(event, service);
                }
            }, 300);
            return;
        }

        if (activeWindow != null) {
            if (activeWindow.getChildCount() > 0)
                for (int i = 0; i < activeWindow.getChildCount(); i++) {

                    final AccessibilityNodeInfo child = activeWindow.getChild(i);
                    LogUtil.e(TAG, "  child " + child.toString());

                    if (!child.isClickable())
                        continue;

                    if (packetSettings != null && packetSettings.isTimeWaiting()) {

                        long time;
                        if (packetSettings.isRandomRobTime()) {
                            time = new Random().nextInt((int) packetSettings.getDefauleRanomtime());

                        } else {

                            time = packetSettings.getWaitTime();
                        }


                        LogUtil.e(TAG, "延时抢红包时间 ：" + time);

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                preformClick(child, null);
                            }
                        }, time);
                        return;

                    } else {
                        preformClick(child, null);
                        return;

                    }

                }

        } else if (source != null) {
            if (source.getChildCount() > 0)
                for (int i = 0; i < source.getChildCount(); i++) {

                    final AccessibilityNodeInfo child = source.getChild(i);
                    LogUtil.e(TAG, "  child " + child.toString());

                    if (!child.isClickable())
                        continue;
                    if (packetSettings != null) {
                        long time;
                        if (packetSettings.isRandomRobTime()) {
                            time = new Random().nextInt((int) packetSettings.getDefauleRanomtime());

                        } else {

                            time = packetSettings.getWaitTime();
                        }

                        LogUtil.e(TAG, "延时抢红包时间 ：" + time);

                        if (packetSettings.isTimeWaiting) {

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    preformClick(child, null);
                                }
                            }, time);
                            return;

                        } else {
                            preformClick(child, null);
                            return;

                        }
                    } else {

                        preformClick(child, null);
                        return;

                    }

                }
        } else {
//            backMain(event, service);
        }
    }


}
