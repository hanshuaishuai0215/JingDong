package com.jingdong.presenter;

import android.content.Context;
import android.widget.Toast;

import com.jingdong.bean.InfoDetailsBean;
import com.jingdong.model.IModel.ISelectGoodsModel;
import com.jingdong.model.SelectGoodsModel;
import com.jingdong.net.OnNetListener;
import com.jingdong.view.IView.IInfoDetailsActivity;

/**
 * 时间:2018/1/6 10:34
 * 作者:韩帅帅
 * 详情:
 */

public class SelectGoodsPresenter {
    private IInfoDetailsActivity infoDetailsActivity;
    private  ISelectGoodsModel iSelectGoodsModel;

    public SelectGoodsPresenter(IInfoDetailsActivity infoDetailsActivity) {
        this.infoDetailsActivity = infoDetailsActivity;
        iSelectGoodsModel = new SelectGoodsModel();
    }
    public void SelectGoods(final Context context, String keywords, String sort, String page){
        iSelectGoodsModel.getOrders(context,keywords, sort, page, new OnNetListener<InfoDetailsBean>() {
            @Override
            public void onSuccess(InfoDetailsBean infoDetailsBean) {
                if (infoDetailsActivity != null){
                    infoDetailsActivity.showSelectList(infoDetailsBean.getData());
                }
            }

            @Override
            public void onFailure(Exception e) {
                    Toast.makeText(context,"对于请求失败这事,就不劳揭穿了!!!",Toast.LENGTH_SHORT).show();
            }
        });
    }
    /**
     * 销毁
     */
    public void Dettach() {
        infoDetailsActivity = null;
    }
}
