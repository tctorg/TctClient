package com.tct.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by libin on 2015/5/21.
 */
public class Configuration {
    Properties mProperties = null;

    static Configuration mInstance = new Configuration();

    public Configuration() {
        mProperties = new Properties();

    }

    public static void load(String path){
        File file = new File(path);
        try {
            mInstance.mProperties.load(new FileInputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String get(String key) {
        if (mInstance.mProperties.containsKey(key))
            return mInstance.mProperties.getProperty(key);
        else
            return "";
    }
}
