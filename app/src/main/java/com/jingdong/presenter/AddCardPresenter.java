package com.jingdong.presenter;

import android.content.Context;
import android.widget.Toast;

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

    public void addCard(final Context context, String pid, String uid){
        Map<String, String> params = new HashMap<>();
        params.put("uid", uid);
        params.put("pid", pid);
        params.put("source", "android");
        iAddCardMolde.addCard(context,params, new OnNetListener<BaseBean>() {
            @Override
            public void onSuccess(BaseBean baseBean) {
                if (iProDetailActivity != null) {
                    iProDetailActivity.show(baseBean);
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
        iProDetailActivity = null;
    }
}

