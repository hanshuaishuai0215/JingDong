package com.jingdong.view.IView;

import com.jingdong.bean.MyRvMiddleBean;
import com.jingdong.bean.ShouYeBean;

import java.util.List;

/**
 * 时间:2017/12/5 9:16
 * 作者:韩帅帅
 * 详情:
 */

public interface IShouYeFragment {
    public void shouBanner(List<ShouYeBean.DataBean> list);
    public void middleView(List<MyRvMiddleBean.DataBean> list);
    public void shouYeRV(ShouYeBean shouYeBean);
}
