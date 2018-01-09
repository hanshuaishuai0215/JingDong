package com.jingdong.view.IView;

import com.jingdong.bean.GetOrdersBean;

import java.util.List;

/**
 * 时间:2018/1/5 21:16
 * 作者:韩帅帅
 * 详情:
 */

public interface IOrderListActivity {
    //显示订单数据
    public void showOrder(List<GetOrdersBean.DataBean> beanList);
}
