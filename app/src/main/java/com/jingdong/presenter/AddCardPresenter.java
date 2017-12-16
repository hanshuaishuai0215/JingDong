package com.jingdong.presenter;

import com.jingdong.bean.BaseBean;
import com.jingdong.model.AddCardModel;
import com.jingdong.model.IModel.IAddCardMolde;
import com.jingdong.net.OnNetListener;
import com.jingdong.view.IView.IProDetailActivity;

import java.util.HashMap;
import java.util.Map;

/**
 * 时间:2017/12/16 9:33
 * 作者:韩帅帅
 * 详情:
 */

public class AddCardPresenter {
    private IAddCardMolde iAddCardMolde;
    private IProDetailActivity iProDetailActivity;

    public AddCardPresenter(IProDetailActivity iProDetailActivity) {
        this.iProDetailActivity = iProDetailActivity;
        iAddCardMolde = new AddCardModel();
    }

    public void addCard(String pid,String uid){
        Map<String, String> params = new HashMap<>();
        params.put("uid", uid);
        params.put("pid", pid);
        params.put("source", "android");
        iAddCardMolde.addCard(params, new OnNetListener<BaseBean>() {
            @Override
            public void onSuccess(BaseBean baseBean) {
                if (iProDetailActivity != null) {
                    iProDetailActivity.show(baseBean.getMsg());
                }
            }

            @Override
            public void onFailure(Exception e) {

            }
        });
    }


    public void dettach() {
        iProDetailActivity = null;
    }
}

