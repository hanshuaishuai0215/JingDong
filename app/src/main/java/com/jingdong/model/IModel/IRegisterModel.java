package com.jingdong.model.IModel;

import android.content.Context;

import com.jingdong.bean.BaseBean;
import com.jingdong.net.OnNetListener;

/**
 * 时间:2017/12/3 21:16
 * 作者:韩帅帅
 * 详情:
 */

public interface IRegisterModel {
    public void register(Context context, String account, String pwd, OnNetListener<BaseBean> onNetListener);
}
