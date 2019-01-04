package com.it_tao.smartlock.inetrface;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/3/23 0023.
 */

public interface OperationKillProcess {
    
    
    void addProcessName(String name);
    void addProcessName(ArrayList<String> nameList);
    void removeProcessName(String name);
    void stopKill();
    
    
}



 