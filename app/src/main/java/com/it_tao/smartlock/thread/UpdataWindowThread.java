package com.it_tao.smartlock.thread;

import android.content.Context;
import android.os.Handler;

/**
 * Created by Administrator on 2017/4/11 0011.
 */

public class UpdataWindowThread extends Thread {

    Context context;
    Handler handler;

    public UpdataWindowThread(Context context, Handler handler) {

        this.context = context;
        this.handler = handler;
    }

    public void run() {

        while (true) {
            handler.sendEmptyMessage(0);
            try {
                Thread.sleep(1 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }
}
