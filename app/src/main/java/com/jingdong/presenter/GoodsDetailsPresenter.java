package com.jingdong.presenter;

import com.jingdong.bean.GoodsDetailsBean;
import com.jingdong.model.GoodsDetailsModel;
import com.jingdong.model.IModel.IGoodsDetailsModel;
import com.jingdong.net.OnNetListener;
import com.jingdong.view.IView.IPro_GoodsFragment;

/**
 * 时间:2017/12/16 10:54
 * 作者:韩帅帅
 * 详情:
 */

public class GoodsDetailsPresenter {
    private IGoodsDetailsModel iGoodsDetailsModel;
    private IPro_GoodsFragment iPro_goodsFragment;

    public GoodsDetailsPresenter(IPro_GoodsFragment iPro_goodsFragment) {
        this.iPro_goodsFragment = iPro_goodsFragment;
        iGoodsDetailsModel = new GoodsDetailsModel();
    }

    public void getProductDetail(String pid) {
        iGoodsDetailsModel.getProductDetail(pid, new OnNetListener<GoodsDetailsBean>() {
            @Override
            public void onSuccess(GoodsDetailsBean goodsDetailsBean) {
                if (iPro_goodsFragment != null) {
                    iPro_goodsFragment.show(goodsDetailsBean);
                }
            }

            @Override
            public void onFailure(Exception e) {

            }
        });
    }



    public void dettach() {
        iPro_goodsFragment = null;
    }
}
