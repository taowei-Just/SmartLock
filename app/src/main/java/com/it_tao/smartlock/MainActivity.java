package com.it_tao.smartlock;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.it_tao.smartlock.helper.SmartHelper;
import com.it_tao.smartlock.listener.SmartSensorListener;
import com.it_tao.smartlock.health.HealthService;
import com.it_tao.smartlock.service.SmartService;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import static com.it_tao.smartlock.R.id.iv_title_back;

public class MainActivity extends Activity implements View.OnClickListener,
        SeekBar.OnSeekBarChangeListener ,CompoundButton.OnCheckedChangeListener {

    private Intent mSmartIntent;
    private MyConnection myConnection;
    private SensorManager mSenManager;
    private SmartSensorListener sensorListener;

    public static String Tag = "屏幕打开";


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            if (msg.what == SmartService.REGESTER_WHST) {

                SmartHelper helper = SmartHelper.getinstance(MainActivity.this);
                helper.openAdmin(MainActivity.this,null);

            } else if (msg.what == SmartService.REPLAY_BINDER) {
                if (msg.obj != null)
                    smartBinder = (SmartService.SmartBinder) msg.obj;
                
                if (isAllowStart){

                if (smartBinder!=null) 
                    smartBinder.startLock();
                    
                    isAllowStart =false ;
                }
               

            }
            

        }
    };
    private SmartService smartService;
    private SeekBar sbInterval;
    private SeekBar sbLock;
    private SeekBar sbAuto;
    private Button btClose;
    private Button btStart;
    private Button btStop;
    private TextView tv_interval;
    private TextView tv_auto;
    private TextView tv_lock;
    private SmartService.SmartBinder smartBinder;

    int autoIndex  ;
    int lockIndex  ;
    int intervalIndex  ;
    private SharedPreferences configShared;
    private SharedPreferences.Editor mEdit;
    private TextView tv_version;
    private Switch sw_auto;
    private boolean isAllowLighting;
    private Switch sw_sleep;
    private Messenger mMessenger;

    private ImageView iv_title_back;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_main);
 

        mMessenger = new Messenger(handler);

        mSmartIntent = new Intent(this, SmartService.class);

        mSmartIntent.putExtra("ActiveMess", mMessenger);
        
        myConnection = new MyConnection();

        startService(mSmartIntent);
        bindService(mSmartIntent, myConnection, BIND_AUTO_CREATE);

        configShared = getSharedPreferences("config", MODE_PRIVATE);
        mEdit = configShared.edit();

        initView();

//        initdata();

    }


    private void initView() {

        tv_lock = (TextView) findViewById(R.id.tv_lock_prog);
        tv_auto = (TextView) findViewById(R.id.tv_auto_prog);
        tv_interval = (TextView) findViewById(R.id.tv_interval_prog);
        tv_version = (TextView) findViewById(R.id.tv_version);
        
        sw_auto = (Switch) findViewById(R.id.sw_auto);
        sw_sleep = (Switch) findViewById(R.id.sw_sleep);

        btStop = (Button) findViewById(R.id.bt_stop_rec);
        btStart = (Button) findViewById(R.id.bt_start_rec);
        btClose = (Button) findViewById(R.id.bt_close_service);

        iv_title_back = (ImageView) findViewById(R.id.iv_title_back);


        sbAuto = (SeekBar) findViewById(R.id.sb_auto_time);
        sbLock = (SeekBar) findViewById(R.id.sb_lock_time);
        sbInterval = (SeekBar) findViewById(R.id.sb_interval_time);


        btStart.setOnClickListener(this);
        btStop.setOnClickListener(this);
        btClose.setOnClickListener(this);
        iv_title_back.setOnClickListener(this);

        sbAuto.setOnSeekBarChangeListener(this);
        sbLock.setOnSeekBarChangeListener(this);
        sbInterval.setOnSeekBarChangeListener(this);
        
        sw_auto.setOnCheckedChangeListener(this);
        sw_sleep.setOnCheckedChangeListener(this);

        PackageManager pakgerM = getPackageManager();
        try {
            PackageInfo info = pakgerM.getPackageInfo(getPackageName(), 0);
            

            tv_version.setText("版本："+info.versionName);
        } catch ( Exception e) {
            e.printStackTrace();
        }


       SlidingMenu slidingMenu = new SlidingMenu(this);
        
        slidingMenu.setContent(View.inflate(this,R.layout.activity_main,null));
        slidingMenu.setMode(SlidingMenu.LEFT);
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        slidingMenu.setShadowWidthRes(R.dimen.activity_horizontal_margin);
        slidingMenu.setShadowDrawable(R.color.colorAccent);
        slidingMenu.setBehindOffset(300);
        slidingMenu.setFadeDegree(0.35f);
        slidingMenu.attachToActivity(this,SlidingMenu.SLIDING_CONTENT);


       
     

    }
boolean isAllowStart = false ;

    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.bt_start_rec:
                
                startService(mSmartIntent);
                bindService(mSmartIntent, myConnection, BIND_AUTO_CREATE);

                isAllowStart = true ;
                if (smartBinder!=null) {
                    smartBinder.startLock();
                   
                }
                break;
            case R.id.bt_stop_rec:

                if (smartBinder != null)
                    smartBinder.stopLock();

                break;

            case R.id.iv_title_back:

               finish();

                break;
            case R.id.bt_close_service:
 
                if(smartBinder!=null) {
                    try {
                        unbindService(myConnection);
                        stopService(mSmartIntent);
                        smartBinder = null;
                    }catch (Exception e){
                        
                        e.printStackTrace();
                    }
                }
                break;


        }

    }

    private void initdata() {


        autoIndex = configShared.getInt("AutoTime", 2);
        lockIndex = configShared.getInt("LockTime", 2);
        intervalIndex = configShared.getInt("IntervalTime", 2);
        isAllowLighting = configShared.getBoolean("IsAllowLighting", false);
        
        Log.e(Tag, "  autoIndex   " + autoIndex + "  lockIndex   " + lockIndex + "  intervalIndex  " + intervalIndex);

        if(isAllowLighting){
          
            smartBinder.allowLighting();

        }else {

            smartBinder.disallowLighting();
        }
        
        sw_auto.setChecked(isAllowLighting);
        
        mEdit.putBoolean("IsAllowLighting", isAllowLighting);
        mEdit.commit();
        sbLock.setProgress(lockIndex);
        sbAuto.setProgress(autoIndex);
        sbInterval.setProgress(intervalIndex);


    }

    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        if (seekBar == sbLock) {

            tv_lock.setText(progress * 100 + "/ms");
            if (smartBinder != null)
                smartBinder.setEnLockTime(progress * 100);


            mEdit.putInt("LockTime", progress);


        } else if (seekBar == sbAuto) {

            tv_auto.setText(progress* 500 + "/ms");
            if (smartBinder != null)
                smartBinder.setAutoTime(progress * 500);

            mEdit.putInt("AutoTime", progress);

        } else if (seekBar == sbInterval) {
            tv_interval.setText(progress*200 + "/ms");
            if (smartBinder != null)
                smartBinder.setUnLockInterval(progress * 200);

            mEdit.putInt("IntervalTime", progress);


        }

        mEdit.commit();
    }


    public void onStartTrackingTouch(SeekBar seekBar) {

    }


    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        
        if (buttonView == sw_auto ){
            
            if ( smartBinder!=null){
            if(isChecked){
                smartBinder.allowLighting();
                
            }else {
                
                smartBinder.disallowLighting();
            }}
            
            mEdit.putBoolean("IsAllowLighting", isChecked);
            mEdit.commit();
            
        }else if (sw_sleep ==buttonView){


            Intent intent = new Intent(MainActivity.this, HealthService.class);
            intent.putExtra("ActiveMess" ,mMessenger);
            intent.putExtra("isFirst" , true);
            
            startService(intent);

        }

    }


    class MyConnection implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

//            Toast.makeText(DrawerActivity.this ,"绑定成功",Toast.LENGTH_SHORT).show();
            smartBinder = (SmartService.SmartBinder) service;
           
            initdata();

        }


        public void onServiceDisconnected(ComponentName name) {


        }
    }


    protected void onDestroy() {
        super.onDestroy();

        if (myConnection != null) {

            unbindService(myConnection);

        }

        if (mSenManager != null) {
            mSenManager.unregisterListener(sensorListener);

        }

    }

 
    
}
