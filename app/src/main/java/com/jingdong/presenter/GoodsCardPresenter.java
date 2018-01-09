package com.jingdong.presenter;

import android.content.Context;
import android.widget.Toast;

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
    public void getCards(final Context context, String uid) {
        Map<String, String> params = new HashMap<>();
        params.put("uid", uid);
        params.put("source","android");
        iGoodsCardModel.getCarts(context,params, new OnNetListener<GoodsCardBean>() {
            @Override
            public void onSuccess(GoodsCardBean goodsCardBean) {
                if (goodsCardBean == null ){
                    Toast.makeText(context,"购物车还没数据呢!赶快宝贝吧!!!", Toast.LENGTH_LONG).show();
                    return;
                }
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
                    Toast.makeText(context, "对于请求失败这事,就不劳揭穿了!!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void Dettach() {
        iGoodsCardFragment = null;
    }
}
