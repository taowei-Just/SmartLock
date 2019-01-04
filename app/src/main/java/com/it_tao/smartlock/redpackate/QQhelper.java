package com.it_tao.smartlock.redpackate;

import android.content.Context;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.it_tao.smartlock.service.accessibility.RedPackateAccessibility;

import java.util.List;

import huanyang.gloable.gloable.utils.LogUtil;

import static android.R.attr.tag;

/**
 * Created by Tao on 2017/11/28 0028.
 */

public class QQhelper extends RedpackateHelper {

    private String TAG = getClass().getName();

    public QQhelper(Context context) {
        super(context);
    }

    public void checkNotify(AccessibilityEvent event) {

        super.checkNotify(event, "[QQ红包]");

    }

    public void checkContent(AccessibilityEvent event, RedPackateAccessibility service) {


        if (event != null) {
            AccessibilityNodeInfo source = event.getSource();
            if (source == null) {

            }
            source = service.getRootInActiveWindow();
            if (source == null) {
                return;
            }

            //com.tencent.mobileqq:id/name
            List<AccessibilityNodeInfo> nodeInfosByText = source.findAccessibilityNodeInfosByText("点击拆开");

            //com.tencent.mobileqq:id/name
            List<AccessibilityNodeInfo> commandInfosByText = source.findAccessibilityNodeInfosByText("口令红包");

           //com.tencent.mobileqq:id/name
            List<AccessibilityNodeInfo> clickInfosByText = source.findAccessibilityNodeInfosByText("点击输入口令");

          //com.tencent.mobileqq:id/ivTitleBtnRightImage
            List<AccessibilityNodeInfo> crowdInfosByText = source.findAccessibilityNodeInfosByText("群资料卡");


            List<AccessibilityNodeInfo> nameS = source.findAccessibilityNodeInfosByViewId("com.tencent.mobileqq:id/name");

            if (nameS != null) {

                for (AccessibilityNodeInfo nodeInfo : nameS) {


                    LogUtil.e(TAG," 查询红包信息 " + nodeInfo.toString());

                }
            }
            if (nodeInfosByText != null) {

                for (AccessibilityNodeInfo nodeInfo : nodeInfosByText) {

                    preformClick(nodeInfo, null);

                }
            }

            if (commandInfosByText!=null){

                for (AccessibilityNodeInfo nodeInfo : commandInfosByText){

                    if (!nodeInfo.getText().toString().contains("口令红包已拆开" ))
                    preformClick(nodeInfo, null);
                }
            }
            if (clickInfosByText!=null){

                for (AccessibilityNodeInfo nodeInfo : clickInfosByText){

                    preformClick(nodeInfo, null);
                }
            }


            // 检查是否为群组
            // 群组抢自己红包
            //
        }
    }
}
