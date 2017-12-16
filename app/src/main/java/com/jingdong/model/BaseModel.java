package com.jingdong.model;

import android.os.Handler;
import android.os.Looper;

/**
 * 时间:2017/12/3 21:08
 * 作者:韩帅帅
 * 详情:所有Model的父类，里面主要是封装了Handler,避免每一个Model都去创建一个Handler
 */


public class BaseModel {
    protected Handler handler = new Handler(Looper.getMainLooper());
}
