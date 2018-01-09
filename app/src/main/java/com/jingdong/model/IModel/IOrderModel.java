package com.jingdong.model.IModel;

import android.content.Context;

import com.jingdong.bean.BaseBean;
import com.jingdong.bean.GetOrdersBean;
import com.jingdong.net.OnNetListener;

/**
 * 时间:2018/1/5 20:28
 * 作者:韩帅帅
 * 详情:
 */

public interface IOrderModel {
    //创建订单
    public void createOrder(Context context, String uid, String price, OnNetListener<BaseBean> onNetListener);
    //显示订单
    void getOrders(Context context,String uid, String status,OnNetListener<GetOrdersBean> onNetWorkListener);
}
