package com.it_tao.smartlock.thread;

import android.util.Log;

import com.it_tao.smartlock.utils.StorageUtil;

import java.io.EOFException;
import java.io.File;
import java.io.IOException;

/**
 * Created by Administrator on 2017/5/18 0018.
 */

public class FileRename extends Thread {


    String path = "";
    String TAG = "FileRename";
 
    public void run() {

   Log.e(TAG,"EMMC 存储位置 ：" + StorageUtil.getEmmcStorageDirectory()+
                   "\n内部 存储位置 ：" +  StorageUtil.getExternalStorageDirectory()+
                   "\nInternal 存储位置 ：" + StorageUtil.getInternalStorageDirectory()+
                   "\n外部SD卡 存储位置 ：" +  StorageUtil.getSdcard2StorageDirectory()+
                  "\n其他  存储位置 ：" + StorageUtil.getOtherExternalStorageDirectory()
        
        );
        
        path = StorageUtil.getSdcard2StorageDirectory()+"kugou";
        
        path = StorageUtil.getExternalStorageDirectory();

        File file = new File(path);
        serchFileList(file);


    }

    long size  = 0;
    private void serchFileList(File file) {
        if (file.exists()&&file.isDirectory()){

            File[] files = file.listFiles();
         

            for (File f : files ){
                
             
                if (f.isDirectory()){
                    
//                      try {
//                     Log.e(TAG, "getCanonicalPath ————————||   "+f.getCanonicalPath());
//                    
//                      } catch (IOException e) {
//                       e.printStackTrace();
//                     }
                    
                    serchFileList(f);
                    
                       
                    
                    try {
                        
                        Log.e(TAG, "文件夹 ："+f.getCanonicalPath() +"————内文总件大小为 ：" + size/1024+"Kb");
                       
                        size =0;
                        
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }else {
                    
//                       Log.e(TAG,f.getPath()+" 文件大小： "+ f.length() /1024+"Kb\n");
                    
                    size+= f.length();
 
                    
                }
                
            }
            
          


        }
    }
}
