package com.it_tao.smartlock.health;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;

import android.support.v4.content.ContextCompat;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.it_tao.smartlock.R;
import com.it_tao.smartlock.health.window.LifeNotifyView;
import com.it_tao.smartlock.health.window.ViewLifeListener;
import com.it_tao.smartlock.inetrface.Healer;
import com.it_tao.smartlock.recevice.HealthAlarmReceiver;
import com.it_tao.smartlock.utils.DataUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

import huanyang.gloable.gloable.Filed;
import huanyang.gloable.gloable.utils.LogUtil;

import static android.content.Context.MODE_PRIVATE;
import static android.support.v7.widget.StaggeredGridLayoutManager.TAG;

/**
 * Created by Administrator on 2017/3/27 0027.
 */

public class HealthHelper implements Healer, ViewLifeListener {


    private static HealthHelper healthHelper;
    private final SharedPreferences configShared;
    private WindowManager mWin;
    private LifeNotifyView mWinView;
    private Context context;
    private WindowManager.LayoutParams params;

    boolean isShowing = false;
    private TextView tv_hour;
    private TextView tv_week;
    private TextView tv_prompt_1;
    private TextView tv_prompt_2;
    private TextView tv_surplus_time;

    private long firstTime;

    private long intervalTime;


    String[] percent = new String[]{"50%", "60%", "70%", "80%", "90%"};

    Handler handler = new Handler() {

        public void handleMessage(Message msg) {

        }
    };

    private final Runnable runnable;
    private static  PendingIntent sleepBroadcast;
    private static PendingIntent weakBroadcast;

    public static HealthHelper getinstance(Context context) {

        if (healthHelper == null) {

            healthHelper = new HealthHelper(context);
        }
        return healthHelper;
    }

    HealthHelper(Context context) {
        this.context = context;
        init(context);
        configShared = context.getSharedPreferences("config", MODE_PRIVATE);

        runnable = new Runnable() {
            public void run() {
                updataView();
                handler.postDelayed(runnable, 1000);
            }
        };
    }

    public void openWindow() {

        if (!isShowing) {
            isShowing = true;
            mWin.addView(mWinView, params);
            handler.post(runnable);
        }
    }


    public void closeWindow() {
        try {
            if (mWin != null) {
                isShowing = false;
                mWin.removeView(mWinView);
                handler.removeCallbacks(runnable);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }


    }


    public void setSleep(long time) {

    }


    public void setAwake(long time) {

    }


    private void init(Context context) {

        mWinView = (LifeNotifyView) View.inflate(context, R.layout.sleep_layout, null);
        tv_hour = (TextView) mWinView.findViewById(R.id.tv_hour);
        tv_week = (TextView) mWinView.findViewById(R.id.tv_week);
        tv_prompt_1 = (TextView) mWinView.findViewById(R.id.tv_prompt_1);
        tv_prompt_2 = (TextView) mWinView.findViewById(R.id.tv_prompt_2);
        tv_surplus_time = (TextView) mWinView.findViewById(R.id.tv_surplus_time);

        mWin = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        params = new WindowManager.LayoutParams();
        params.type = WindowManager.LayoutParams.TYPE_TOAST;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.flags = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        params.gravity = Gravity.CENTER;
        mWinView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);


        mWinView.addLifeListener(this);

        mWinView.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {


                }

                return false;
            }
        });

    }


    private void updataView() {

        String top_Time = new SimpleDateFormat("HH:mm:ss").format(new Date(System.currentTimeMillis()));
        String top_data = new SimpleDateFormat("MM月dd日").format(new Date(System.currentTimeMillis()));

        long terminateTime = configShared.getLong("TerminateTime", -1);

        String surplus = new SimpleDateFormat("HH小时mm分钟").format(new Date(caculateTime(terminateTime)));


        tv_hour.setText(top_Time);
        tv_week.setText(top_data + " " + DataUtils.getWeekString());

        tv_prompt_1.setText(R.string.sleep___);
        SpannableStringBuilder builder = new SpannableStringBuilder();
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(ContextCompat.getColor(context, R.color.color_orange));
        builder.append(percent[3]);
        builder.setSpan(colorSpan, 0, builder.length(), SpannableStringBuilder.SPAN_EXCLUSIVE_EXCLUSIVE);

        tv_prompt_2.setText(context.getString(R.string.sleep_) + builder.toString() +
                context.getString(R.string.sleep__));

        tv_surplus_time.setText(surplus);

    }

    private long caculateTime(long terminateTime) {

        long TimeMillis = Math.abs(terminateTime - System.currentTimeMillis());
        long onDay = 24 * 60 * 60 * 1000;
        long time;

        if (TimeMillis > onDay) {
            time = TimeMillis % onDay;
        } else {

            time = TimeMillis;
        }

        return time;
    }


    @Override
    public void onDetachedFromWindow() {

        LogUtil.e(TAG, "onDetachedFromWindow");
    }

    @Override
    public void onAttachedToWindow() {
        LogUtil.e(TAG, "onAttachedToWindow");

    }


    public PendingIntent setWakeUpAlarm(long triggerAtMillis, long intervalMillis, int flag) {

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, HealthAlarmReceiver.class);
        intent.putExtra(Filed.extra, "isAwaken");

        weakBroadcast = PendingIntent.getBroadcast(context, flag, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, triggerAtMillis, intervalMillis, weakBroadcast);

        return weakBroadcast;
    }

    public PendingIntent setSleepUpAlarm(long triggerAtMillis, long intervalMillis, int flag) {

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, HealthAlarmReceiver.class);
        intent.putExtra(Filed.extra, "isSleep");

        sleepBroadcast = PendingIntent.getBroadcast(context, flag, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, triggerAtMillis, intervalMillis, sleepBroadcast);
        return sleepBroadcast;
    }

    public void setFirstTime(long firstTime) {
        this.firstTime = firstTime;
    }

    public void setIntervalTime(long intervalTime) {
        this.intervalTime = intervalTime;
    }

    public void cancelAlarm(PendingIntent sleepBroadcast, PendingIntent weakBroadcast) {

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        alarmManager.cancel(this.sleepBroadcast);
        alarmManager.cancel(this.weakBroadcast);


    }
}
