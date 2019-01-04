package com.it_tao.smartlock.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Trace;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;

import com.it_tao.smartlock.R;
import com.tencent.bugly.a;

import junit.framework.Test;

/**
 * Created by Administrator on 2017/5/17 0017.
 */
public class CircleProgressView extends View {


    private int radius  = 100   ;

    private float progress = 1f;
    private int darkg_green;
    private int back_blue;
    private int darkg_blue;
    private Handler handler;
    private int  cicrePadding = 0 ;
    private int height;
    private int width;


    public CircleProgressView(Context context) {
        super(context);
    }

    public CircleProgressView(Context context, AttributeSet attrs) {

        super(context, attrs);

        darkg_green = context.getResources().getColor(R.color.color_darkg_green);
        back_blue = context.getResources().getColor(R.color.color_back_blue);
        darkg_blue = context.getResources().getColor(R.color.color_darkg_blue);

        handler = new Handler();
        getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {

 

            public  boolean   onPreDraw() {

                new Thread(new Runnable() {

                    
                    boolean isAdd =false  ;
                    
                    public void run() {

                        while (true) {

                            if (progress >= 100) {

                                progress = 1;
                            } else {
                                progress +=0.1;

                            }


                            if (!isAdd){

                                if (radius < 100 ){

                                    isAdd =true ;
                                }else {
                                    radius-=1;

                                }



                            }else {

                                if (radius>250){

                                    isAdd = false ;
                                }else {

                                    radius+=1;
                                }
                            }

                            postInvalidate();
                            Log.e("postInvalidate ", " 正在执行 radius " + radius);
                            try {
                                Thread.sleep(10);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start(); 
                
                getViewTreeObserver().removeOnPreDrawListener( this);

                return false ;
               
            }
        });


    }

    public CircleProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        width = getMeasuredWidth() ;

        height = getMeasuredHeight();
        
        radius = (width - 200) / 2;

    }


    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }


    protected void onDraw(Canvas canvas) {
        
        Log.e(" postInvalidate","执行 ondraw "+progress+"    |" +(float)360/100  * progress );

        Paint paint = new Paint();

        canvas.drawColor(back_blue);

        //画内圆
        paint.setColor(darkg_blue);

        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(20);

        canvas.drawCircle(width / 2, height / 2, radius, paint);


        //画进度
//    public void drawArc(float left, float top, float right, float bottom, float startAngle, 
// float sweepAngle, boolean useCenter, Paint paint) {

       

        paint.setColor(darkg_green);


        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
        
        int offset = 50;
        int dotRadius = 20 ;
        cicrePadding = 20;
        int gap  = dotRadius + cicrePadding;
        
//        float arc =  (float)( 2*Math.asin( gap/2/radius ));
       float arc =  (float) Math.toDegrees( 2*Math.asin((float)gap/2/(radius+offset)));
 
        
        Log.e("postInvalidate" , "   半径幅度   " +arc);

        float prog  =  (360-arc)/100f *progress - arc  ;
        
        if (prog <0){
            prog=0 ;
        }
         
   
        canvas.drawArc((width - 2 * radius) / 2 - offset, (height - radius * 2) / 2 - offset, 
                (width - 2 * radius) / 2 + 2 * radius + offset,
                (height - radius * 2) / 2 + 2 * radius + offset, 
                270, prog, false, paint);

//        画圆点
//        canvas.drawCircle();

        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
      
        canvas.drawCircle((float) ( width/2+(radius+offset)*Math.cos(((360-arc)/100f  * progress+270)  *Math.PI/180 )),
                (float) (height/2+(radius+offset)*Math.sin(((360-arc)/100f * progress+270)*Math.PI/180)), dotRadius, paint);


    }

    private void TestMath() {
        
        Log.e("postInvalidate" , "Math.sin(45) : "+Math.sin(0) );
        Log.e("postInvalidate" , "Math.cos(45) : "+Math.cos(180) );
        Log.e("postInvalidate" , "Math.asin(0.5) : "+Math.asin(90) );
        Log.e("postInvalidate" , "Math.acos(0.5) : "+Math.acos(90) );
                        
        
        
    }


}
