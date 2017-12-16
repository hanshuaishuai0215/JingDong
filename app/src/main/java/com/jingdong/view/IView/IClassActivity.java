package com.jingdong.view.IView;


import com.jingdong.bean.Catagory;
import com.jingdong.bean.ProductCatagoryBean;

import java.util.List;

/**
 * 时间:2017/12/3 21:19
 * 作者:韩帅帅
 * 详情:
 */
public interface IClassActivity {
    public void showData(List<Catagory.DataBean> list);
    public void showElvData(List<ProductCatagoryBean.DataBean> list);
}
