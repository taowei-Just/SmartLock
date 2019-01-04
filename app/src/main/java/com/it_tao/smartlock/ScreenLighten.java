package com.it_tao.smartlock;

import com.it_tao.smartlock.helper.SmartHelper;

/**
 * Created by Administrator on 2017/3/24 0024.
 */

public class ScreenLighten extends ScreenState {
    public ScreenLighten(SmartHelper smartHelper) {
        super(smartHelper);
    }

   
    public void closeScreen() {
       smartHelper.lockScreen();
    }


    @Override
    public void openScreen(boolean boo) {
        super.openScreen(boo);
    }

    @Override
    public void interrept() {
        super.interrept();
    }

    @Override
    public boolean isScreen() {
        return super.isScreen();
    }
}
