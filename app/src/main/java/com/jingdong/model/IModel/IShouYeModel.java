package com.jingdong.model.IModel;

import android.content.Context;

import com.jingdong.bean.MyRvMiddleBean;
import com.jingdong.bean.ShouYeBean;
import com.jingdong.net.OnNetListener;

/**
 * 时间:2017/12/5 9:04
 * 作者:韩帅帅
 * 详情:
 */

public interface IShouYeModel {
    public void getBannerUrl(Context context, OnNetListener<ShouYeBean> onNetListener);
    public void getMiddleViewUrl(Context context,OnNetListener<MyRvMiddleBean> onNetListener);
}
