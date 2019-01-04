package com.it_tao.smartlock.inetrface;

/**
 * Created by Administrator on 2017/3/23 0023.
 */

public interface OperationLock {
    
    
    void startLock();
    void stopLock();
    
    void setEnLockTime(long time);
    
    void setUnLockInterval(long time);
    void setAutoTime(long time);
    void allowLighting() ;
    void disallowLighting() ;
    
    
}
