package com.it_tao.smartlock;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;


import com.it_tao.smartlock.adapter.GuidAdapter;
import com.it_tao.smartlock.listener.GuidRecyleClickListener;
import com.it_tao.smartlock.recevice.HealthAlarmReceiver;
import com.it_tao.smartlock.service.SmartService;
import com.it_tao.smartlock.service.audio.AudioService;
import com.it_tao.smartlock.thread.FileRename;
import com.it_tao.smartlock.twocamera.DrawerActivity;
import com.it_tao.smartlock.ui.RedPacketActivity;
import com.it_tao.smartlock.utils.ChackAppStats;
import com.it_tao.smartlock.utils.StorageUtil;

import com.tencent.bugly.crashreport.CrashReport;

import java.util.ArrayList;

public class GuidActivity extends Activity implements GuidRecyleClickListener {

    private RecyclerView rv_list;
    int count = 4;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_guid);
        startService(new Intent(this, AudioService.class));

        rv_list = (RecyclerView) findViewById(R.id.gv_list);
        ImageView  iv_title_back = (ImageView) findViewById(R.id.iv_title_back);

        iv_title_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(GuidActivity.this, AlertDialog.THEME_HOLO_LIGHT);
                builder.setMessage("是否退出程序")
                .setTitle("提示")
                .setNegativeButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setPositiveButton("取消",null);




            }
        });

        initdata();
        StorageUtil.init(this);
//        

        GuidAdapter guidAdapter = new GuidAdapter(this, count);
        rv_list.setAdapter(guidAdapter);

        guidAdapter.notifyDataSetChanged();
        guidAdapter.setOnItemListener(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);

//        rv_list.setLayoutManager(linearLayoutManager);
        rv_list.setLayoutManager(gridLayoutManager);



    }

    private void initdata() {

        AlarmManager mAlarManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        Intent alarmIntent = new Intent(this, HealthAlarmReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 2, alarmIntent, 0);
//        mAlarManager.setRepeating(AlarmManager.RTC_WAKEUP,System.currentTimeMillis(), 5000, pendingIntent);


        switch (ChackAppStats.chackBootComplit(this)) {

            case ChackAppStats.BOOT_COMPLIT_ALLOW:

                Toast.makeText(GuidActivity.this, "允许开机启动！", Toast.LENGTH_LONG).show();


                break;
            case ChackAppStats.BOOT_COMPLIT_SYSTEM_DISALLOW:
                Toast.makeText(GuidActivity.this, "系统拒绝开机启动！", Toast.LENGTH_LONG).show();

                break;
            case ChackAppStats.BOOT_COMPLIT_PREMONITION_MISS:

                Toast.makeText(GuidActivity.this, "开机启动权限丢失", Toast.LENGTH_LONG).show();

                break;

            case ChackAppStats.BOOT_COMPLIT_CONNOT_DETECTION:

                Toast.makeText(GuidActivity.this, "无法检测！", Toast.LENGTH_LONG).show();

                break;


        }


//        PMan.get(this,"2eaa453a4a93e23d209cad8c58dbb43e",  "01",true);

//        Log.e( "推广",PMan .TAG +" 初始化完毕");

        ArrayList<View> mViewList = new ArrayList();

//        System.out.println("jar包内参数 ："+h.a);

    }


    public void onItemClick(View v, int position) {
        Intent intent;
        switch (position) {
            case 0:
                intent = new Intent(GuidActivity.this, MainActivity.class);
                startActivity(intent);

                break;
            case 1:


                intent = new Intent(GuidActivity.this, SmartSleepActivity.class);
                startActivity(intent);


                break;
            case 2:
//                Toast.makeText(GuidActivity.this,"欢迎进入梦话录音机",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(GuidActivity.this, AutoDreamRecodeActivity.class));

                break;
            case 3:
//                Toast.makeText(GuidActivity.this,"功能有待开发敬请期待，Thank's You！",Toast.LENGTH_SHORT).show();

                startActivity(new Intent(GuidActivity.this, RedPacketActivity.class));


                break;

            case 4:

                //手电筒
                startActivity(new Intent(GuidActivity.this, RedPacketActivity.class));


                break;

            case 5:
                //
                //画画板
                startActivity(new Intent(GuidActivity.this, DrawerActivity.class));

                break;


        }

    }


}
