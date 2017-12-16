package com.jingdong.net;

/**
 * 时间:2017/12/3 21:18
 * 作者:韩帅帅
 * 详情:
 */

public interface OnNetListener <T>{
    //成功回调
    public void onSuccess(T t);
    //失败回调
    public void onFailure(Exception e);
}
