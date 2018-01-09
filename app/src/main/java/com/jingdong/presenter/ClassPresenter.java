package com.jingdong.presenter;

import android.content.Context;
import android.widget.Toast;

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

    public void getProductCatagory(final Context context, String cid) {
        iclassModel.getProductCatagory(context,cid, new OnNetListener<ProductCatagoryBean>() {
            @Override
            public void onSuccess(ProductCatagoryBean productCatagoryBean) {
                if (iClassActivity != null) {
                    //给二级列表设置数据
                    iClassActivity.showElvData(productCatagoryBean.getData());
                }
            }

            @Override
            public void onFailure(Exception e) {
                    Toast.makeText(context, "对于请求失败这事,就不劳揭穿了!!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //获取分类
    public void getCatagory(final Context context) {
        iclassModel.getCatagory(context,new OnNetListener<Catagory>() {
            @Override
            public void onSuccess(Catagory catagory) {
                if (iClassActivity != null) {
                    iClassActivity.showData(catagory.getData());
                    //拿到右侧第一条数据
                    List<Catagory.DataBean> dataBean = catagory.getData();
                    int cid = dataBean.get(0).getCid();
                    //获取右侧的数据
                    getProductCatagory(context,cid + "");
                }
            }

            @Override
            public void onFailure(Exception e) {
                Toast.makeText(context, "对于请求失败这事,就不劳揭穿了!!!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    /**
     * 销毁
     */
    public void Dettach() {
        iClassActivity = null;
    }
}
