package com.jingdong.utils;

import android.app.Activity;

import java.util.LinkedList;
import java.util.List;

/**
 * 时间:2017/12/3 21:17
 * 作者:韩帅帅
 * 详情:用于保存已经开启的Activity
 */

public class ActivityStroreUtil {
    private static List<Activity> list = new LinkedList<>();

    /**
     * 用集合保存所有开启的Activity
     *
     * @param ac
     */
    public static void addActivity(Activity ac) {
        list.add(ac);
    }

    /**
     * 关闭已经开启的Activity
     */
    public void finishAll() {
        for (Activity ac : list) {
            if (ac != null)
                ac.finish();

        }
    }

}
