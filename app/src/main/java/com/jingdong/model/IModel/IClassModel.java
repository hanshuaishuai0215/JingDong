package com.jingdong.model.IModel;


import android.content.Context;

import com.jingdong.bean.Catagory;
import com.jingdong.bean.ProductCatagoryBean;
import com.jingdong.net.OnNetListener;

/**
 * 时间:2017/12/3 21:16
 * 作者:韩帅帅
 * 详情:
 */
public interface IClassModel {

    public void getCatagory(Context context, OnNetListener<Catagory> onNetListener);

    public void getProductCatagory(Context context,String cid, OnNetListener<ProductCatagoryBean> onNetListener);
}
