package com.it_tao.smartlock;

import com.it_tao.smartlock.helper.SmartHelper;

/**
 * Created by Administrator on 2017/3/24 0024.
 */

public class ScreenClose extends ScreenState {
    
    

    public ScreenClose(SmartHelper smartHelper) {
        super(smartHelper);
    }

    public void openScreen(boolean boo) {
    
        if(boo) {
            smartHelper.unlock();
        }
    }

    @Override
    public void closeScreen() {
        super.closeScreen();
    }

    @Override
    public void interrept() {
        super.interrept();
    }
 
}
