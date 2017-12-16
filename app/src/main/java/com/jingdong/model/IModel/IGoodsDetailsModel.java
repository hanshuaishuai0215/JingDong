package com.jingdong.model.IModel;

import com.jingdong.bean.GoodsDetailsBean;
import com.jingdong.net.OnNetListener;

/**
 * 时间:2017/12/16 10:56
 * 作者:韩帅帅
 * 详情:
 */

public interface IGoodsDetailsModel {
    void getProductDetail(String pid, final OnNetListener<GoodsDetailsBean> onNetListener);
}
