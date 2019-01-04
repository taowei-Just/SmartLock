package com.it_tao.smartlock.health.window;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;

/**
 * Created by Tao on 2017/11/23 0023.
 */

public class LifeNotifyView extends LinearLayout {

    ArrayList<ViewLifeListener> lifeListeners;


    public void addLifeListener(ViewLifeListener lifeListener) {
        if (lifeListeners == null)
            lifeListeners = new ArrayList<>();

        lifeListeners.add(lifeListener);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    public LifeNotifyView(Context context) {
        super(context);
    }

    public LifeNotifyView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LifeNotifyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public void addOnAttachStateChangeListener(OnAttachStateChangeListener listener) {
        super.addOnAttachStateChangeListener(listener);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        if (lifeListeners != null && lifeListeners.size() > 0) {
            for (ViewLifeListener listener : lifeListeners) {

                if (listener != null)
                    listener.onDetachedFromWindow();
            }

        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        if (lifeListeners != null && lifeListeners.size() > 0) {
            for (ViewLifeListener listener : lifeListeners) {

                if (listener != null)
                    listener.onAttachedToWindow();

            }

        }
    }


}
