package com.jingdong.model.IModel;

import com.jingdong.bean.GoodsCardBean;
import com.jingdong.net.OnNetListener;

import java.util.Map;
/**
 * 时间:2017/12/16 12:30
 * 作者:韩帅帅
 * 详情:
 */
public interface IGoodsCardModel {
    void getCarts(Map<String, String> params, OnNetListener<GoodsCardBean> onNetListener);
}