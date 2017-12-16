package com.jingdong.presenter;

import com.jingdong.bean.Catagory;
import com.jingdong.bean.ProductCatagoryBean;
import com.jingdong.model.ClassModel;
import com.jingdong.model.IModel.IClassModel;
import com.jingdong.net.OnNetListener;
import com.jingdong.view.IView.IClassActivity;

import java.util.List;

/**
 * 时间:2017/12/3 21:18
 * 作者:韩帅帅
 * 详情:
 */

public class ClassPresenter {

    private final IClassModel iclassModel;
    private IClassActivity iClassActivity;

    public ClassPresenter(IClassActivity iClassActivity) {
        this.iClassActivity = iClassActivity;
        iclassModel = new ClassModel();
    }

    public void getProductCatagory(String cid) {
        iclassModel.getProductCatagory(cid, new OnNetListener<ProductCatagoryBean>() {
            @Override
            public void onSuccess(ProductCatagoryBean productCatagoryBean) {
                //给二级列表设置数据
                iClassActivity.showElvData(productCatagoryBean.getData());
            }

            @Override
            public void onFailure(Exception e) {

            }
        });
    }

    //获取分类
    public void getCatagory() {
        iclassModel.getCatagory(new OnNetListener<Catagory>() {
            @Override
            public void onSuccess(Catagory catagory) {
                iClassActivity.showData(catagory.getData());
                //拿到右侧第一条数据
                List<Catagory.DataBean> dataBean = catagory.getData();
                int cid = dataBean.get(0).getCid();
                //获取右侧的数据
                getProductCatagory(cid + "");
            }

            @Override
            public void onFailure(Exception e) {

            }
        });
    }
}
