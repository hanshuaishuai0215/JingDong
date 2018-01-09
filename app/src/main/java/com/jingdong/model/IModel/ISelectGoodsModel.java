package com.jingdong.model.IModel;

import android.content.Context;

import com.jingdong.bean.InfoDetailsBean;
import com.jingdong.net.OnNetListener;

/**
 * 时间:2018/1/6 10:24
 * 作者:韩帅帅
 * 详情:
 */

public interface ISelectGoodsModel {
    void getOrders(Context context, String keywords, String status, String page, OnNetListener<InfoDetailsBean> onNetWorkListener);
}
