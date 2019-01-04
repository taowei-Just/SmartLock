package huanyang.gloable.gloable.utils;

import android.graphics.Path;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by ${Tao} on 2017-11-1709.
 */

public class FileUtil {


    public static ArrayList<String> listFiles(String path) throws Exception {


        File file = new File(path);
        if (file.exists()) {
            ArrayList<String> list = new ArrayList<>();

            if (file.isDirectory() && file.exists()) {

                File[] files = file.listFiles();
                for (File f : files) {

                    if (f.exists()) {
                      listFiles(f.getAbsolutePath());
                    }
                }

            } else {

                if (file.exists())
                    list.add(file.getAbsolutePath());
            }
            return list;
        }
        return null;
    }
}
