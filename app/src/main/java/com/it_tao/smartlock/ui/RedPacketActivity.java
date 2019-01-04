package com.it_tao.smartlock.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.it_tao.smartlock.R;
import com.it_tao.smartlock.adapter.QQSettingAdapter;
import com.it_tao.smartlock.adapter.WechartSettingAdapter;
import com.it_tao.smartlock.helper.SmartHelper;
import com.it_tao.smartlock.service.accessibility.RedPackateAccessibility;
import com.it_tao.smartlock.service.audio.AudioService;
import com.it_tao.smartlock.utils.AppSettingUtil;

import huanyang.gloable.gloable.Shared;
import huanyang.gloable.gloable.utils.LogUtil;
import huanyang.gloable.gloable.utils.SharedUtlis;
import huanyang.gloable.gloable.utils.ToastTools;

import static android.R.attr.id;
import static android.content.ContentValues.TAG;
import static com.it_tao.smartlock.R.id.lv_wechart_setting;
import static com.it_tao.smartlock.R.id.rl_beep_switch;


/**
 * Created by Tao on 2017/11/28 0028.
 */

public class RedPacketActivity extends Activity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {


    private String tag = getClass().getName();
    private SharedUtlis sharedUtlis;
    private ImageView ivTitleBack;
    private CheckBox cbWechartSwitch;
    private CheckBox cbWechartSetting;
    private TextView tvRestartHelper;
    private TextView iv_wechart_packet_text;
    private TextView iv_wechart_more_text;
    private RelativeLayout rl_wechart_switch;
    private RelativeLayout rl_wechart_showList;
    private ListView lv_wechart_setting;
    private WechartSettingAdapter wechartSettingAdapter;
    private RelativeLayout rl_qq_switch;
    private RelativeLayout rl_qq_showList;
    private CheckBox cb_qq_switch;
    private CheckBox cb_qq_setting;
    private TextView iv_qq_packet_text;
    private TextView iv_qq_more_text;
    private ListView lv_qq_setting;
    private QQSettingAdapter qqSettingAdapter;
    private TextView iv__beep__text;
    private TextView iv_hint_audio_text;
    private CheckBox cbBeepSetting;
    private CheckBox cbAudioSetting;
    private RelativeLayout rl_Beep_switch;
    private RelativeLayout rl_Audio_switch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_packet);
        initview();
        initdata();
    }

    private void initview() {

        ivTitleBack = (ImageView) findViewById(R.id.iv_title_back);
        tvRestartHelper = (TextView) findViewById(R.id.tv_restart_helper);

        rl_wechart_switch = (RelativeLayout) findViewById(R.id.rl_wechart_switch);
        rl_wechart_showList = (RelativeLayout) findViewById(R.id.rl_wechart_showList);

        rl_Audio_switch = (RelativeLayout) findViewById(R.id.rl_hint_audio_switch);
        rl_Beep_switch = (RelativeLayout) findViewById(R.id.rl_beep_switch);

        cbWechartSwitch = (CheckBox) findViewById(R.id.cb_wechart_switch);
        cbWechartSetting = (CheckBox) findViewById(R.id.cb_wechart_setting);

        cbAudioSetting = (CheckBox) findViewById(R.id.cb__hint_audio_);
        cbBeepSetting = (CheckBox) findViewById(R.id.cb__beep_);

        iv_wechart_packet_text = (TextView) findViewById(R.id.iv_wechart_packet_text);
        iv_wechart_more_text = (TextView) findViewById(R.id.iv_wechart_more_text);

        lv_wechart_setting = (ListView) findViewById(R.id.lv_wechart_setting);


        rl_qq_switch = (RelativeLayout) findViewById(R.id.rl_qq_switch);
        rl_qq_showList = (RelativeLayout) findViewById(R.id.rl_qq_showList);


        cb_qq_switch = (CheckBox) findViewById(R.id.cb_qq_switch);
        cb_qq_setting = (CheckBox) findViewById(R.id.cb_qq_setting);

        iv_qq_packet_text = (TextView) findViewById(R.id.iv_qq_packet_text);
        iv_qq_more_text = (TextView) findViewById(R.id.iv_qq_more_text);

        iv_hint_audio_text = (TextView) findViewById(R.id.iv_hint_audio_text);
        iv__beep__text = (TextView) findViewById(R.id.iv__beep__text);

        lv_qq_setting = (ListView) findViewById(R.id.lv_qq_setting);

    }

    private void initdata() {

        sharedUtlis = new SharedUtlis(this, Shared.config);

        rl_Audio_switch.setOnClickListener(this);
        rl_Beep_switch.setOnClickListener(this);
        cbAudioSetting.setOnCheckedChangeListener(this);
        cbBeepSetting.setOnCheckedChangeListener(this);


        ivTitleBack.setOnClickListener(this);
        tvRestartHelper.setOnClickListener(this);

        rl_wechart_switch.setOnClickListener(this);
        rl_wechart_showList.setOnClickListener(this);

        cbWechartSwitch.setOnCheckedChangeListener(this);
        cbWechartSetting.setOnCheckedChangeListener(this);


        wechartSettingAdapter = new WechartSettingAdapter(this);

        lv_wechart_setting.setAdapter(wechartSettingAdapter);

        rl_qq_switch.setOnClickListener(this);
        rl_qq_showList.setOnClickListener(this);

        cb_qq_switch.setOnCheckedChangeListener(this);
        cb_qq_setting.setOnCheckedChangeListener(this);

        qqSettingAdapter = new QQSettingAdapter(this);
        wechartSettingAdapter = new WechartSettingAdapter(this);

        lv_wechart_setting.setAdapter(wechartSettingAdapter);
        lv_qq_setting.setAdapter(qqSettingAdapter);

        cbWechartSwitch.setChecked(sharedUtlis.getBoolean(Shared.isWechartOpen, false));

        cb_qq_switch.setChecked(sharedUtlis.getBoolean(Shared.isQQOpen, false));

        cbAudioSetting.setChecked(sharedUtlis.getBoolean(Shared.isAudioOpen,false));
        cbBeepSetting.setChecked(sharedUtlis.getBoolean(Shared.isBeepOpen,false));

//        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
//
//        long[] pattern = {10,250, 80, 200,80,300,80,400,100,250, 80, 200,80,300,80,400}; // 停止 开启 停止 开启
//        vibrator.vibrate(pattern, -1);
//        vibrator.cancel();
    }


    private void checkAccessibility() {

        SmartHelper helper = SmartHelper.getinstance(this);
        helper.openAdmin(this, "抢红包帮你亮屏");
        if (!AppSettingUtil.isAccessibilitySettingsOn(getApplicationContext(), getPackageName(), RedPackateAccessibility.class.getCanonicalName())) {

            LogUtil.e(tag, "未开启");
            AppSettingUtil.jumpAccessibility(this);

            ToastTools.showLong(RedPacketActivity.this, "找到“辅助功能”或“无障碍”-“抢红包就靠我”打开就可以抢红包啦！");
        }
    }


    @Override
    public void onClick(View v) {

        if (v == ivTitleBack) {
            finish();
        } else if (v == tvRestartHelper) {
            AppSettingUtil.jumpAccessibility(this);

        } else if (v == rl_wechart_switch) {

            cbWechartSwitch.setChecked(!cbWechartSwitch.isChecked());

        } else if (v == rl_wechart_showList) {

            cbWechartSetting.setChecked(!cbWechartSetting.isChecked());

        } else if (v == rl_qq_switch) {

            cb_qq_switch.setChecked(!cb_qq_switch.isChecked());

        } else if (v == rl_qq_showList) {

            cb_qq_setting.setChecked(!cb_qq_setting.isChecked());

        }else if (v == rl_Audio_switch) {

            cbAudioSetting.setChecked(!cbAudioSetting.isChecked());

        }else if (v == rl_Beep_switch) {

            cbBeepSetting.setChecked(!cbBeepSetting.isChecked());
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        if (buttonView == cbWechartSwitch) {

            sharedUtlis.putBoolean(Shared.isWechartOpen, isChecked);

            if (isChecked) {
                iv_wechart_packet_text.setTextColor(Color.RED);

                checkAccessibility();
            } else {
                iv_wechart_packet_text.setTextColor(Color.DKGRAY);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        cbWechartSetting.setChecked(false);
                        iv_wechart_more_text.setTextColor(Color.DKGRAY);
                    }
                }, 200);
            }

        } else if (buttonView == cbWechartSetting) {

            if (isChecked) {
                iv_wechart_more_text.setTextColor(Color.RED);

                if (cbWechartSwitch.isChecked()) {
                    showListview();
                } else {
                    ToastTools.showShort(RedPacketActivity.this, "打开“抢微信红包”开关，就可以进行设置哦！");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            cbWechartSetting.setChecked(false);
                            iv_wechart_more_text.setTextColor(Color.DKGRAY);
                        }
                    }, 500);
                }
            } else {
                closeListv();
                iv_wechart_more_text.setTextColor(Color.DKGRAY);
            }
        } else if (buttonView == cbAudioSetting) {

            sharedUtlis.putBoolean(Shared.isAudioOpen, isChecked);

            if (isChecked) {
                iv_hint_audio_text.setTextColor(Color.RED);
//                checkAccessibility();

                Intent service = new Intent(RedPacketActivity.this, AudioService.class);

                service.putExtra("from","redpacket");
                startService(service);

            } else {
                iv_hint_audio_text.setTextColor(Color.DKGRAY);
                sharedUtlis.putBoolean(Shared.isAudioOpen, isChecked);
            }

        }else if (buttonView == cbBeepSetting) {

            sharedUtlis.putBoolean(Shared.isBeepOpen, isChecked);

            if (isChecked) {
                iv__beep__text.setTextColor(Color.RED);
                Intent service = new Intent(RedPacketActivity.this, AudioService.class);
                service.putExtra("testbeep",true);
                startService(service);
            } else {
                iv__beep__text.setTextColor(Color.DKGRAY);
                sharedUtlis.putBoolean(Shared.isBeepOpen, isChecked);
            }

        }else if (buttonView == cb_qq_switch) {

            sharedUtlis.putBoolean(Shared.isQQOpen, isChecked);

            if (isChecked) {
                iv_qq_packet_text.setTextColor(Color.RED);
                checkAccessibility();
            } else {
                iv_qq_packet_text.setTextColor(Color.DKGRAY);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        cb_qq_setting.setChecked(false);
                        iv_qq_more_text.setTextColor(Color.DKGRAY);
                    }
                }, 200);

            }

        } else if (buttonView == cb_qq_setting) {


            if (isChecked) {
                iv_qq_more_text.setTextColor(Color.RED);
                if (cb_qq_switch.isChecked()) {
                    lv_qq_setting.setVisibility(View.VISIBLE);
                    qqSettingAdapter.notifyDataSetChanged();
                } else {

                    ToastTools.showShort(RedPacketActivity.this, "打开“抢微信红包”开关，就可以进行设置哦！");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            cb_qq_setting.setChecked(false);
                            iv_qq_more_text.setTextColor(Color.DKGRAY);
                        }
                    }, 500);
                }
            } else {

                lv_qq_setting.setVisibility(View.GONE);
                qqSettingAdapter.notifyDataSetChanged();
                iv_qq_more_text.setTextColor(Color.DKGRAY);
            }
        }
    }

    private void closeListv() {
        lv_wechart_setting.setVisibility(View.GONE);
        wechartSettingAdapter.notifyDataSetChanged();
    }

    private void showListview() {

        lv_wechart_setting.setVisibility(View.VISIBLE);
        wechartSettingAdapter.notifyDataSetChanged();

    }
}
