package com.it_tao.smartlock;

import com.it_tao.smartlock.helper.SmartHelper;

/**
 * Created by Administrator on 2017/3/24 0024.
 */

public class ScreenState {

   
    public SmartHelper smartHelper;

    public ScreenState(SmartHelper smartHelper) {
        this.smartHelper = smartHelper ;
    }

    public void openScreen(boolean boo) {
    }

    ;

    public void closeScreen() {
    }

    ;

    public void interrept() {
    }

    ;

    public boolean isScreen() {
        return smartHelper.isLocked();
    }

    
}
