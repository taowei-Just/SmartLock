package huanyang.gloable.gloable.utils;

/*
 * 文件名:     LogUtil
 * 创建者:     阿钟
 * 创建时间:   2016/10/22 20:21
 * 描述:       输出Log的工具类
 */

import android.os.Environment;
import android.util.Log;

 

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import huanyang.gloable.gloable.AppInfo;

public class LogUtil {

    //Log开关
    private static boolean isOpen = true;
    private static boolean isSave = true;
      static String TAG = "RedLog";

    /**
     * 设置是否输出Log
     *
     * @param b
     */
    public static void setSwitch(boolean b) {
        isOpen = b;
        
        
    }

    //**********************Debug***********//
    public static void d(String tag, String msg) {
        if (isOpen) Log.d(tag, msg);
    }

    public static void d(String tag, int msg) {
        if (isOpen) Log.d(tag, String.valueOf(msg));
    }

    public static void d(String tag, float msg) {
        if (isOpen) Log.d(tag, String.valueOf(msg));
    }

    public static void d(String tag, long msg) {
        if (isOpen) Log.d(tag, String.valueOf(msg));
    }

    public static void d(String tag, double msg) {
        if (isOpen) Log.d(tag, String.valueOf(msg));
    }

    public static void d(String tag, boolean msg) {
        if (isOpen) Log.d(tag, String.valueOf(msg));
    }

    //**********************Info***********//
    public static void i(String tag, String msg) {
        if (isOpen) Log.i(tag, msg);
    }

    public static void i(String tag, int msg) {
        if (isOpen) Log.i(tag, String.valueOf(msg));
    }

    public static void i(String tag, float msg) {
        if (isOpen) Log.i(tag, String.valueOf(msg));
    }


    public static void i(String tag, long msg) {
        if (isOpen) Log.i(tag, String.valueOf(msg));
    }

    public static void i(String tag, double msg) {
        if (isOpen) Log.i(tag, String.valueOf(msg));
    }

    public static void i(String tag, boolean msg) {
        if (isOpen) Log.i(tag, String.valueOf(msg));
    }

    //**********************Error***********//
    public static void e(String tag, String msg) {
     if (isOpen) Log.e(TAG+tag, msg);
        
        saveLogToFile(Environment.getExternalStorageDirectory().toString()+"/"+ AppInfo.packageName+"/Log/"+ tag+"/Log_E.txt", msg);
    }
    public static void e(String tag, String msg,boolean isSave) {
    	if (isOpen) Log.e(TAG+tag, msg);
    	
    	if(isSave)
    	saveLogToFile(Environment.getExternalStorageDirectory().toString()+"/"+AppInfo.packageName+"/Log/"+ tag+"/Log_E.txt", msg);
    }

    public static void e(String tag, int msg) {
        if (isOpen) Log.e(tag, String.valueOf(msg));
    }

    public static void e(String tag, float msg) {
        if (isOpen) Log.e(tag, String.valueOf(msg));
    }

    public static void e(String tag, Long msg) {
        if (isOpen) Log.e(tag, String.valueOf(msg));
    }

    public static void e(String tag, double msg) {
        if (isOpen) Log.e(tag, String.valueOf(msg));
    }

    public static void e(String tag, boolean msg) {
        if (isOpen) Log.e(tag, String.valueOf(msg));
    }
    
    
    public static  void saveLogToFile(String path ,String str){

        if (!isSave)
            return;

    	File createFile = createFile(path);

    	String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis()));

    	str = time+"\n"+str +"\n  分割线 ================================== \n";

    	try {
			FileOutputStream outputStream = new FileOutputStream(createFile, true);
			outputStream.write(str.getBytes());
			outputStream.close();
		} catch ( Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    	
    	
    	
    	
    } 
    
    public static void mkdirs(String paramString)
    {
      File localFile = new File(paramString);
      if (!localFile.exists())
        localFile.mkdirs();
    }

    public static File  createFile(String paramString)
    {
    	  File localFile = new File(paramString);
      try
      {
        String[] arrayOfString = paramString.split("/");
        String str1 = arrayOfString[(-1 + arrayOfString.length)];
        String str2 = paramString.substring(0, paramString.length() - str1.length());
      
        if (!new File(str2).exists())
        	mkdirs(str2);
        if (!localFile.exists())
          localFile.createNewFile();
        return localFile;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
	return localFile;
    }

}
