
package com.it_tao.smartlock.customview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Interpolator;

import com.it_tao.smartlock.R;

 

public class HomeArcView extends View {
    //圆环颜色 
    private int[] doughnutColors = {Color.parseColor("#42e7e0"), Color.parseColor("#91ffa1"), Color.parseColor("#5ef0c9")}; 
    private int roundColor= ContextCompat.getColor(getContext(), R.color.color_darkg_blue);
    private Paint paint_white;
    private static float currentValue = 0f;
    private Paint paint = new Paint();
    private float arc_y = 0f;
    private float arc_y_1 = 0f;
    private int score, mPage;
    private int pointCount = 2;
    private float tb;
    private float doughnutWidth;
    private RectF rectf;

    public HomeArcView(Context context, int score) {
        super(context);
        setValue(score);
    }

    public HomeArcView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HomeArcView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    private void initPaint() {
        paint.reset();
        paint.setAntiAlias(true);
    }

    public void setValue(int score) {
        this.score = score;
        Resources res = getResources();
        tb = res.getDimension(R.dimen.activity_horizontal_margin);
        doughnutWidth = 0.25f * tb;
        //外圈圆
        rectf = new RectF();
        rectf.set(doughnutWidth * 2, doughnutWidth * 2, 17.5f * tb, 17.5f * tb);

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(currentValue, 360f);
        valueAnimator.setInterpolator(new Interpolator() {
            @Override
            public float getInterpolation(float v) {
                return 1 - (1 - v) * (1 - v) * (1 - v);
            }
        });
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                currentValue = (float) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.start();


        //圆圈带颜色
        paint_white = new Paint();
        paint_white.setAntiAlias(true);
        paint_white.setColor(roundColor);
        paint_white.setStrokeWidth(tb * 0.2f);
        paint_white.setTextAlign(Paint.Align.CENTER);
        paint_white.setStyle(Paint.Style.STROKE);

        //外圈圆第一部分动画，0 至 score-1
        this.getViewTreeObserver().addOnPreDrawListener(
                new ViewTreeObserver.OnPreDrawListener() {
                    public boolean onPreDraw() {
                        new thread();
                        getViewTreeObserver().removeOnPreDrawListener(this);
                        return false;
                    }
                });
        //外圈圆第二部分动画，score+1 至 100
        this.getViewTreeObserver().addOnPreDrawListener(
                new ViewTreeObserver.OnPreDrawListener() {
                    public boolean onPreDraw() {
                        new threadone();
                        getViewTreeObserver().removeOnPreDrawListener(this);
                        return false;
                    }
                });
        //外圈小圆动画，0 到 score
        this.getViewTreeObserver().addOnPreDrawListener(
                new ViewTreeObserver.OnPreDrawListener() {
                    public boolean onPreDraw() {
                        new threadtwo();
                        getViewTreeObserver().removeOnPreDrawListener(this);
                        return false;
                    }
                });

    }

    @Override
    protected void onDraw(Canvas canvas) {

        initPaint();
        //渐变色圆
        drawSq(canvas);
        //外圈小圆
        drawPoint(canvas);
        //外圈圆第一部分
        drawOneSq(canvas);
        //外圈圆第二部分
        drawTwoSq(canvas);


    }

    /**
     * 画内圈渐变圆
     */
    private void drawSq(Canvas canvas) {
        RectF rectF = new RectF(doughnutWidth * 6, doughnutWidth * 6, 16.5f * tb, 16.5f * tb);
        paint.setStrokeWidth(doughnutWidth * 2);
        paint.setStyle(Paint.Style.STROKE);
        if (doughnutColors.length > 1) {
            paint.setShader(new SweepGradient(7.5f * tb, 7.5f * tb, doughnutColors, null));
        } else {
            paint.setColor(doughnutColors[0]);
        }
        canvas.drawArc(rectF, 0, currentValue, false, paint);
    }


    /**
     * 画外圆第一部分
     */
    private void drawOneSq(Canvas canvas) {
        canvas.rotate(0, getWidth() / 2, getHeight() / 2);
        canvas.drawArc(rectf, -90, arc_y, false, paint_white);
    }

    class thread implements Runnable {
        private Thread thread;
        private int statek;
        int count;

        public thread() {
            thread = new Thread(this);
            thread.start();
        }

        public void run() {
            while (true) {
                switch (statek) {
                    case 0:
                        try {
                            Thread.sleep(400);
                            statek = 1;
                        } catch (InterruptedException e) {
                        }
                        break;
                    case 1:
                        try {
                            Thread.sleep(15);
                            arc_y += 3.6f;
                            count++;
                            postInvalidate();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        break;
                }
                if (count >= score - 2)
                    break;
            }
        }
    }

    /**
     * 画外圆第二部分
     */
    private void drawTwoSq(Canvas canvas) {
        canvas.rotate((float) ((score + 2) * 3.6), getWidth() / 2, getHeight() / 2);
        canvas.drawArc(rectf, -90, arc_y_1, false, paint_white);
    }

    class threadone implements Runnable {
        private Thread thread;
        private int statek = 1;
        int count = score + 2;

        public threadone() {
            thread = new Thread(this);
            thread.start();
        }

        public void run() {
            while (true) {
                switch (statek) {
                    case 1:
                        try {
                            Thread.sleep(5);
                            arc_y_1 += 3.6f;
                            count++;
                            postInvalidate();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        break;
                }
                if (count >= 100)
                    break;
            }
        }
    }

    /**
     * 画外圆圆点
     */
    private void drawPoint(Canvas canvas) {

        Paint paint_big_text = new Paint();
        paint_big_text.setAntiAlias(true);
        paint_big_text.setColor(Color.WHITE);
        paint_big_text.setTextAlign(Paint.Align.CENTER);
        paint_big_text.setStyle(Paint.Style.FILL);
        initPaint();
        canvas.drawCircle((float) (9.0f * tb + 8.5f * tb * Math.sin(3.6 * pointCount * Math.PI / 180)),
                (float) (9.0f * tb - 8.5f * tb * Math.cos(3.6 * pointCount * Math.PI / 180)), 10, paint_big_text);

    }

    class threadtwo implements Runnable {
        private Thread thread;
        private int statek = 0;

        public threadtwo() {
            thread = new Thread(this);
            thread.start();
        }

        public void run() {
            while (true) {
                switch (statek) {
                    case 0:
                        try {
                            Thread.sleep(400);
                            statek = 1;
                        } catch (InterruptedException e) {
                        }
                        break;
                    case 1:
                        try {
                            Thread.sleep(15);
                            pointCount++;
                            postInvalidate();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        break;
                }
                if (pointCount >= score)
                    break;
            }
        }
    }
}