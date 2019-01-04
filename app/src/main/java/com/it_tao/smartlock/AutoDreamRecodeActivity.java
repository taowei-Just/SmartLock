package com.it_tao.smartlock;

import android.app.Activity;
import android.content.Intent;
import android.media.projection.MediaProjectionManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;

import com.it_tao.smartlock.adapter.DreamRecodeAdapter;
import com.it_tao.smartlock.thread.FileRename;
import com.it_tao.smartlock.thread.RecodeSoundThread;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2017/5/17 0017.
 */

public class AutoDreamRecodeActivity extends Activity implements View.OnClickListener {


    private Button btStart;
    private Button btPause;
    private Button btStop;
    private Button btSmartRec;
    private SeekBar sbValuem;
    private ListView lvRec;
    private String path;
    String TAG = "AutoDreamRecodeActivity";
    private RecodeSoundThread mRecodeThread;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_dream_recode);
        initview();
//        initdata();
        new FileRename().start();
        
        shotScreem();
        
        
    }

    private void shotScreem() {

        MediaProjectionManager mMP = (MediaProjectionManager) getSystemService(MEDIA_PROJECTION_SERVICE);

//        startActivityForResult(mMP.createScreenCaptureIntent(),0001);

        
        
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        
        if (requestCode == 0001 ){
           
            
        }
    }

    private void initdata() {


        path = Environment.getRootDirectory().getAbsolutePath();
        
        //system
        Log.e(TAG," Environment.getRootDirectory().getAbsolutePath():" + path);
        
         path = Environment.getExternalStorageDirectory().getAbsolutePath();
        
        // 挂载内置存储
        Log.e(TAG," Environment.getExternalStorageDirectory().getAbsolutePath():" + path+"/DreanRecode");
        File folder = new File(path);
        if (!folder.exists()){
            
            folder.mkdirs();
            
        }
        
        path += "/"+new SimpleDateFormat("yyyy-mm-dd HH时dd分ss秒").format(new Date(System.currentTimeMillis()))+".3gp";
        
      
        
       File file =  new File(path);
        if (!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        mRecodeThread = new RecodeSoundThread(path);
      
        
        
        
        
    }

    private void initview() {


        btStart = (Button) findViewById(R.id.bt_start_rec);
        btPause = (Button)   findViewById(R.id.bt_pause_rec);
        btStop = (Button)  findViewById(R.id.bt_stop_rec);
        btSmartRec = (Button)  findViewById(R.id.bt_smart_rec);
        sbValuem = (SeekBar)  findViewById(R.id.sb_valuem);
        lvRec = (ListView)  findViewById(R.id.lv_rec);
        ImageView iv_title_back = (ImageView) findViewById(R.id.iv_title_back);
        iv_title_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        
        btStart.setOnClickListener(this);
        btPause.setOnClickListener(this);
        btStop.setOnClickListener(this);
        btSmartRec.setOnClickListener(this);
       
        sbValuem.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        
        
        DreamRecodeAdapter mDreamAdapter = new DreamRecodeAdapter();
        lvRec.setAdapter(mDreamAdapter);
        
        
        
        
        
    }


 
    public void onClick(View view) {
        
        switch (view.getId()){
            
     
            
            case  R.id.bt_start_rec:
                
                startRecode();
            
            break;
            
              
            case  R.id.bt_pause_rec:
                
                pauseRecode();
            
            break;
            
              
            case  R.id.bt_stop_rec:
                stopRecode();
                
            
            break;
            
              
            case  R.id.bt_smart_rec:
                
                smartRec();
            
            break;
            
            
            
            
            
            
        }
        
        
        
        
        
        
    }

    private void startRecode() {
        
        
        
        btStart.setText("录制中..");
        btPause.setClickable(true);
        btStart.setClickable(false);
        btStop.setClickable(true);
        if (mRecodeThread!=null){
            
            
            mRecodeThread.start();
            
        }
        

        
        
    }

    private void pauseRecode() {
        
        btStart.setText("继续");
        
        
        
    }

    private void stopRecode() {
         btStart.setText("结束录制");
        btPause.setClickable(false);
        btStart.setClickable(true);
        
        mRecodeThread.startRecode();
        
        
    }

    private void smartRec() {
        
        btSmartRec.setText("等待中..");
        
        btPause.setClickable(false);  
         btPause.setClickable(true);
        btStart.setClickable(false);
        btStop.setClickable(true);
        
        //获取环境音量
        
        startAcquireValuem();
        
        
    }

    private void startAcquireValuem() {
        //从设置中查找
        
        
        
    }
}
