package com.jingdong.model.IModel;

import android.content.Context;

import com.jingdong.bean.BaseBean;
import com.jingdong.net.OnNetListener;

import java.util.Map;

/**
 * 时间:2017/12/16 9:37
 * 作者:韩帅帅
 * 详情:
 */

public interface IAddCardMolde {
    void addCard(Context context,Map<String, String> params, OnNetListener<BaseBean> onNetListener);
}
