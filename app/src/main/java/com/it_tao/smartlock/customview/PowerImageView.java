package com.it_tao.smartlock.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by Administrator on 2017/6/3 0003.
 */


public class PowerImageView extends ImageView {

    // 自动播放
    boolean auto_player = false;
    //播放状态
    int gifState = -1;

    //播放时间间隔
    long intervalTime = 24;
    //gif时长
    long overallTime;
    //当前进度
    int currentFrameNumber;
    long currentFrameTime;
    //资源类型
    
    //资源路径
    String imgPath;

    public PowerImageView(Context context) {
        super(context);
    }

    public PowerImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PowerImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

   public enum PowerImageState {
         

        state_playing(1),
         state_pause(2),
          state_over(3),;

       PowerImageState(int i) {
           
       }
   }


    public void playGif() {
        //不显示播放按钮


    }

    public void pauseGif() {
        //


    }

    public void endGif() {
    }


}
