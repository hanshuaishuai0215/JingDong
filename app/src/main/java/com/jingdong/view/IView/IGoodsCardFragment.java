package com.jingdong.view.IView;

import com.jingdong.bean.GoodsCardBean;

import java.util.List;

/**
 * 时间:2017/12/16 11:59
 * 作者:韩帅帅
 * 详情:
 */

public interface IGoodsCardFragment {
    void show(List<GoodsCardBean.DataBean> group, List<List<GoodsCardBean.DataBean.ListBean>> child);
}
