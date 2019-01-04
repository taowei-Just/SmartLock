package com.it_tao.smartlock.redpackate;

/**
 * Created by Tao on 2017/12/2 0002.
 */

public class PacketSettings {

     // 抢自己的红包
    boolean isSelfPacket ;

     // 抢红包开关
    boolean isOpen ;

        // 自动亮屏
    boolean isAutoLight;

      //延时抢红包
    boolean isTimeWaiting ;

    //延时跳转
    boolean isdaleyJump;

    //延时跳转
    int daleyJumpTime = 300;

    //延时时间
    long waitTime = 300;

    // 随机时间
    long defauleRanomtime = 5*1000;

    boolean isRandomJumpTime;

    boolean isRandomRobTime;


    boolean isAudioOpen ;
    boolean isBeepOpen ;


    public boolean isBeepOpen() {
        return isBeepOpen;
    }

    public void setBeepOpen(boolean beepOpen) {
        isBeepOpen = beepOpen;
    }

    public boolean isAudioOpen() {
        return isAudioOpen;
    }

    public void setAudioOpen(boolean audioOpen) {
        isAudioOpen = audioOpen;
    }

    public boolean isRandomJumpTime() {
        return isRandomJumpTime;
    }

    public void setRandomJumpTime(boolean randomJumpTime) {
        isRandomJumpTime = randomJumpTime;
    }

    public boolean isRandomRobTime() {
        return isRandomRobTime;
    }

    public void setRandomRobTime(boolean randomRobTime) {
        isRandomRobTime = randomRobTime;
    }

    public boolean isdaleyJump() {
        return isdaleyJump;
    }

    public void setIsdaleyJump(boolean isdaleyJump) {
        this.isdaleyJump = isdaleyJump;
    }

    public int getDaleyJumpTime() {
        return daleyJumpTime;
    }

    public void setDaleyJumpTime(int daleyJumpTime) {
        this.daleyJumpTime = daleyJumpTime;
    }

    public long getDefauleRanomtime() {
        return defauleRanomtime;
    }

    public void setDefauleRanomtime(long defauleRanomtime) {
        this.defauleRanomtime = defauleRanomtime;
    }

    public boolean isSelfPacket() {
        return isSelfPacket;
    }

    public void setSelfPacket(boolean selfPacket) {
        isSelfPacket = selfPacket;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public boolean isAutoLight() {
        return isAutoLight;
    }

    public void setAutoLight(boolean autoLight) {
        isAutoLight = autoLight;
    }

    public boolean isTimeWaiting() {
        return isTimeWaiting;
    }

    public void setTimeWaiting(boolean timeWaiting) {
        isTimeWaiting = timeWaiting;
    }

    public long getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(long waitTime) {
        this.waitTime = waitTime;
    }

    @Override
    public String toString() {
        return "PacketSettings{" +
                "抢自己红包=" + isSelfPacket +
                ", 打开抢红包=" + isOpen +
                ",   自动亮屏=" + isAutoLight +
                ",   延时抢=" + isTimeWaiting +
                ",   延时跳转=" + isdaleyJump +
                ",   延时跳转时间=" + daleyJumpTime +
                ",  延时抢红包时间=" + waitTime +
                ",   时间随机=" + defauleRanomtime +
                ",  随即跳转=" + isRandomJumpTime +
                ",   随即抢=" + isRandomRobTime +
                ",  使用震动=" + isBeepOpen +
                ",    使用提示音=" + isAudioOpen +
                '}';
    }


}
