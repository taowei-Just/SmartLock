package com.it_tao.smartlock.thread;

import android.media.MediaRecorder;
import android.util.Log;

import java.io.IOException;

/**
 * Created by Administrator on 2017/5/17 0017.
 */

public class RecodeSoundThread extends Thread {


    String  path;
    private MediaRecorder mMediaRecode;

    public RecodeSoundThread(String path ) {
        this.path =path ;
    }

    public void run() {


        mMediaRecode = new MediaRecorder();
        
        mMediaRecode.setAudioSource(MediaRecorder.AudioSource.MIC);
         
        mMediaRecode.setOutputFormat(MediaRecorder.OutputFormat.AAC_ADTS);
      
        mMediaRecode.setOutputFile(path);
       
        mMediaRecode.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_WB);
        startRecode();
         Log.e("" , "  获取音量信息 " );
        
        
        while (true) {
            int maxValuem = mMediaRecode.getMaxAmplitude();
            Log.e("" , "   maxValuem " + maxValuem);

            try {
                Thread.sleep(2*500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
          
    }
    
    
    
    public void  startRecode(){
        
        if (mMediaRecode!=null) {
            try {
                mMediaRecode.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }

            mMediaRecode.start();
        }
        
    }
    public void stopRecode(){
        
        if (mMediaRecode!=null) {
            mMediaRecode.stop();
            mMediaRecode.release();
            mMediaRecode = null;
        }
        
        interrupt();
    }
    public void pause(){
        
        
        
//        mMediaRecode.pause();
    }
    
    
    
    
    
    
    
    
    
}
