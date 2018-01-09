package com.jingdong.utils;

import android.content.Context;
import android.content.SharedPreferences;
/**
 * 时间:2018/1/3 19:54
 * 作者:韩帅帅
 * 详情:引导页的工具类
 */
public class SharedPreferencesUtil {

    private static final String spFileName = "welcomePage";
    public static final String FIRST_OPEN = "first_open";

    public static Boolean getBoolean(Context context, String strKey,
                                     Boolean strDefault) {//strDefault  boolean: Value to return if this preference does not exist.
        SharedPreferences setPreferences = context.getSharedPreferences(
                spFileName, Context.MODE_PRIVATE);
        Boolean result = setPreferences.getBoolean(strKey, strDefault);
        return result;
    }

    public static void putBoolean(Context context, String strKey,
                                  Boolean strData) {
        SharedPreferences activityPreferences = context.getSharedPreferences(
                spFileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = activityPreferences.edit();
        editor.putBoolean(strKey, strData);
        editor.commit();
    }

}