package com.jingdong.view.IView;

import com.jingdong.bean.InfoDetailsBean;

import java.util.List;

/**
 * 时间:2017/12/15 19:58
 * 作者:韩帅帅
 * 详情:
 */

public interface IInfoDetailsActivity {
    public void showList(List<InfoDetailsBean.DataBean> dataBeanList);
    public void showSelectList(List<InfoDetailsBean.DataBean> dataBeanList);
}
