package com.it_tao.smartlock.castom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.widget.ImageView;

/**
 * Created by Administrator on 2017/3/27 0027.
 */

public class WindowImage extends ImageView {

    private int width;
    private int height;
    String sleepTalk = "有充足的睡眠，明天才有精神打小怪兽哦！" ;

    public WindowImage(Context context) {
        super(context);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        width = getWidth();
        height = getHeight();


    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        Paint paint = new Paint();
        paint.setStrokeWidth(3);
        paint.setAntiAlias(true);
        paint.setTextSize(30f);
        paint.setColor(Color.BLUE);
        float v = paint.measureText(sleepTalk);
        float startx = (width - v) / 2;
        

        canvas.drawText(sleepTalk,startx,height/2 ,paint);


    }
}
