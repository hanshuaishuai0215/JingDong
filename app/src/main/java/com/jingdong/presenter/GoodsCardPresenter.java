package com.jingdong.presenter;

import com.jingdong.bean.GoodsCardBean;
import com.jingdong.model.GoodsCardModel;
import com.jingdong.model.IModel.IGoodsCardModel;
import com.jingdong.net.OnNetListener;
import com.jingdong.view.IView.IGoodsCardFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 时间:2017/12/16 12:00
 * 作者:韩帅帅
 * 详情:
 */

public class GoodsCardPresenter {
    private IGoodsCardModel iGoodsCardModel;
    private IGoodsCardFragment iGoodsCardFragment;

    public GoodsCardPresenter(IGoodsCardFragment iGoodsCardFragment) {
        this.iGoodsCardFragment = iGoodsCardFragment;
        iGoodsCardModel = new GoodsCardModel();
    }
    public void getCards(String uid) {
        Map<String, String> params = new HashMap<>();
        params.put("uid", uid);
        params.put("source","android");
        iGoodsCardModel.getCarts(params, new OnNetListener<GoodsCardBean>() {
            @Override
            public void onSuccess(GoodsCardBean goodsCardBean) {
                List<List<GoodsCardBean.DataBean.ListBean>> child = new ArrayList<>();
                for (int i = 0; i < goodsCardBean.getData().size(); i++) {
                    child.add(goodsCardBean.getData().get(i).getList());
                }
                if (iGoodsCardFragment != null) {
                    iGoodsCardFragment.show(goodsCardBean.getData(), child);
                }
            }

            @Override
            public void onFailure(Exception e) {

            }
        });
    }

    public void dettach() {
        iGoodsCardFragment = null;
    }
}
