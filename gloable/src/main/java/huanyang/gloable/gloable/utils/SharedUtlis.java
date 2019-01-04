package huanyang.gloable.gloable.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import static android.R.attr.value;

public class SharedUtlis {

    Context context;
    String name;
    private SharedPreferences mPreferences;
    private Editor mEdit;


    public SharedUtlis(Context context, String name) {
 
        this.context = context;
        this.name = name;
        mPreferences = context.getSharedPreferences(name, Context.MODE_MULTI_PROCESS);
        
        mEdit = mPreferences.edit();
    }

    public void putString(String key, String value) {


        mEdit.putString(key, value);
        mEdit.commit();

    }

    public void putBoolean(String key, boolean value) {

        mEdit.putBoolean(key, value);
        mEdit.commit();

    }

    public boolean getBoolean(String key, boolean def) {

        return mPreferences.getBoolean(key, def);


    }

    public String getString(String key, String def) {

        return mPreferences.getString(key, def);


    }
    
   public int getInt(String key,int defo) {

        return mPreferences.getInt(key,defo);


    }
   public long getLong(String key,long defo) {

        return mPreferences.getLong(key,defo);


    }


    public void putInt(String key,int value) {

        mEdit.putInt(key, value);
        mEdit.commit();
        
    }

    public void putLong(String sleepTime, long timeInMillis) {

        mEdit.putLong(sleepTime, timeInMillis);
        mEdit.commit();
    }
}
