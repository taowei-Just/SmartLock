package com.it_tao.smartlock;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Messenger;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.it_tao.smartlock.health.HealthService;
import com.it_tao.smartlock.widget.DialogTools;
import com.it_tao.smartlock.widget.PickTimeView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import huanyang.gloable.gloable.Filed;
import huanyang.gloable.gloable.Shared;
import huanyang.gloable.gloable.utils.LogUtil;
import huanyang.gloable.gloable.utils.SharedUtlis;
import huanyang.gloable.gloable.utils.TimeUtil;
import huanyang.gloable.gloable.utils.ToastTools;

import static com.it_tao.smartlock.R.id.pv_weak_data;

public class SmartSleepActivity extends Activity {


    private Messenger mMessenger;
    private long sleepTime;
    private long weakTime;

    Handler handler = new Handler();
    private CheckBox cb;

    private PickTimeView pv_data;
    private SharedUtlis sharedUtlis;
    private Button btConfirm;
    private PickTimeView pv_weak_data;
    private String TAG = getClass().getName();
    private boolean isSmartSleep;


    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_layout);

        initView();
        initData();
    }

    private void initData() {
        mMessenger = new Messenger(handler);
        pv_data.setViewType(PickTimeView.TYPE_PICK_TIME);
        pv_weak_data.setViewType(PickTimeView.TYPE_PICK_TIME);
        sharedUtlis = new SharedUtlis(getApplicationContext(), Shared.config);

        isSmartSleep = sharedUtlis.getBoolean("isSmartSleep", false);

        pv_data.setOnSelectedChangeListener(new PickTimeView.onSelectedChangeListener() {

            public void onSelected(PickTimeView view, long timeMillis) {

                LogUtil.e(TAG, " 选中时间 " + timeMillis + "\n", false);
                LogUtil.e(TAG, " 选中时间 转换时间" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(timeMillis)) + "\n", false);

            }
        });


        btConfirm.setOnClickListener(new View.OnClickListener() {

            private boolean checked;

            @Override
            public void onClick(View v) {
                checked = cb.isChecked();
                sharedUtlis = new SharedUtlis(getApplicationContext(), Shared.config);

                isSmartSleep = sharedUtlis.getBoolean(Shared.isSmartSleepKey,isSmartSleep);
                // 获取值保存到本地

                // 确认设置


                // 将值传递到service

                String msg = "";


                if (checked) {


                    msg = "   睡眠助手已经开启\n按时睡觉每天都棒棒哒!";

                    DialogTools.showDialog(SmartSleepActivity.this,"温馨提示",
                            Html.fromHtml("<font color='#6495ED'><big>" + msg+ "</big></font>")
                            ,"好哒",null).setCancelable(true);
                    sharedUtlis.putBoolean(Shared.isSmartSleepKey, cb.isChecked());

                    long timeInMillis = pv_data.getmTimeMillis();
                    LogUtil.e(TAG, " 睡眠 " + timeInMillis + "\n"
                            +TimeUtil.timeStamp2Date(timeInMillis,"yyyy-MM-dd HH:mm:ss.SS"), false);

                    sharedUtlis.putLong(Shared.sleepTimeKey, timeInMillis);


                    long timeInMillis1 = pv_weak_data.getmTimeMillis();
                    LogUtil.e(TAG, " 起床 " + timeInMillis1 + "\n"
                            + TimeUtil.timeStamp2Date(timeInMillis1,"yyyy-MM-dd HH:mm:ss.SS"), false);

                    sharedUtlis.putLong(Shared.weakTimeKey, timeInMillis1);


                    Intent intent = new Intent(SmartSleepActivity.this, HealthService.class);
                    intent.putExtra(Filed.sleepMessengerKey, mMessenger);
                    startService(intent);

                } else  {
                    if( !isSmartSleep ){

                    msg = "睡眠助手没有开启哟 \n" +
                            "要不勾上“开启智能睡眠”试试吧!";
                        DialogTools.showDialog(SmartSleepActivity.this, "温馨提示",
                                Html.fromHtml("<font color='#6495ED'><big>" + msg + "</big></font>")

                                , "懒人模式", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        cb.setChecked(true);
                                    }
                                }, "朕自己来", null);
                    }  else {

                        DialogTools.showDialog(SmartSleepActivity.this, "温馨提示",
                                Html.fromHtml("<font color='#6495ED'><big>" + "开启睡眠助手有益于您的身体健康\n    继续保留睡眠助手功能叭！" + " \n" + "</big></font>")
                                , "继续保持", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        cb.setChecked(true);
                                    }
                                },   "残忍拒绝", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    sharedUtlis.putBoolean(Shared.isSmartSleepKey,false);

                                        Intent intent = new Intent(SmartSleepActivity.this, HealthService.class);
                                        intent.putExtra(Filed.sleepMessengerKey, mMessenger);
                                        startService(intent);
                                    }
                                }).setCancelable(true);

                    }
                }

                ToastTools.showShort(SmartSleepActivity.this, msg);

                Calendar calendar = Calendar.getInstance();
//                LogUtil.e(TAG ," Calendar " +calendar.toString());

//                LogUtil.e(TAG ," getWeekYear " +calendar.getWeekYear());
//                LogUtil.e(TAG ," getWeeksInWeekYear " +calendar.getWeeksInWeekYear());
//                LogUtil.e(TAG ," getFirstDayOfWeek " +calendar.getFirstDayOfWeek());
//                LogUtil.e(TAG ," getTimeInMillis " +calendar.getTimeInMillis());
//                LogUtil.e(TAG ," getMinimalDaysInFirstWeek " +calendar.getMinimalDaysInFirstWeek());
//                LogUtil.e(TAG ," getTime " +calendar.getTime());
//                LogUtil.e(TAG ," getTimeInMillis " +calendar.getTimeInMillis());
//                LogUtil.e(TAG ," getTimeZone " +calendar.getTimeZone());
//                LogUtil.e(TAG ," Calendar.YEAR " +calendar.get(Calendar.YEAR));
//                LogUtil.e(TAG ," Calendar.MONTH " +calendar.get(Calendar.MONTH));
//                LogUtil.e(TAG ," Calendar.HOUR_OF_DAY " +calendar.get(Calendar.HOUR_OF_DAY));
//                LogUtil.e(TAG ," Calendar.DAY_OF_WEEK " +calendar.get(Calendar.DAY_OF_WEEK));
//                LogUtil.e(TAG ," Calendar.DAY_OF_MONTH " +calendar.get(Calendar.DAY_OF_MONTH));
//                LogUtil.e(TAG ," Calendar.DAY_OF_YEAR " +calendar.get(Calendar.DAY_OF_YEAR));
//                LogUtil.e(TAG ," Calendar.DATE " +calendar.get(Calendar.DATE));



                calendar.set(calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH),
                        23,
                        30,
                        00);
                long timeInMillis = calendar.getTimeInMillis();

//                LogUtil.e(TAG ," 得到时间 " +new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SS").format(calendar.getTime()));

                long sleepTime =   sharedUtlis.getLong(Shared.sleepTimeKey, 1);


                long weakTime = sharedUtlis.getLong(Shared.weakTimeKey, 1);


                LogUtil.e(TAG," 主页  睡眠时间为 " +sleepTime+"  : "+ TimeUtil.timeStamp2Date(sleepTime,"yyyy-MM-dd HH:mm:ss.SS"));

                LogUtil.e(TAG," 主页  唤醒时间为 " +weakTime+"  : "+   TimeUtil.timeStamp2Date(weakTime,"yyyy-MM-dd HH:mm:ss.SS"));


            }
        });

        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });


        if (isSmartSleep) {
            cb.setChecked(isSmartSleep);
        } else {
            cb.setChecked(isSmartSleep);
        }

    }

    private void initView() {
        pv_data = (PickTimeView) findViewById(R.id.pv_data);

        pv_weak_data = (PickTimeView) findViewById(R.id.pv_weak_data);
        btConfirm = (Button) findViewById(R.id.bt_confirm);
        cb = (CheckBox) findViewById(R.id.checkBox);

        ImageView iv_title_back = (ImageView) findViewById(R.id.iv_title_back);
        iv_title_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


}
