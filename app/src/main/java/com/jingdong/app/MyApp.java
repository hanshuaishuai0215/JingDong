package com.jingdong.app;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.umeng.socialize.PlatformConfig;

/**
 * 时间:2017/12/3 21:07
 * 作者:韩帅帅
 * 详情:
 */

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        PlatformConfig.setWeixin("","");
        PlatformConfig.setQQZone("1106526118","uVPEG59tvi9d3IyG");
        PlatformConfig.setSinaWeibo("","","");
        Fresco.initialize(this);
    }
}
