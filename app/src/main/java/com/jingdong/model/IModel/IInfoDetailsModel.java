package com.jingdong.model.IModel;

import android.content.Context;

import com.jingdong.bean.InfoDetailsBean;
import com.jingdong.net.OnNetListener;

import java.util.Map;

/**
 * 时间:2017/12/3 21:16
 * 作者:韩帅帅
 * 详情:
 */

public interface IInfoDetailsModel {
  public void getInfoDetails(Context context, Map<String,String> params, OnNetListener<InfoDetailsBean> onNetListener);
}
