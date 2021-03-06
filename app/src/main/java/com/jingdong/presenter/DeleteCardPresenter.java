package com.jingdong.presenter;

import android.content.Context;
import android.widget.Toast;

import com.jingdong.bean.BaseBean;
import com.jingdong.model.DeleteCardMolde;
import com.jingdong.model.IModel.IDeleteCardMolde;
import com.jingdong.net.OnNetListener;
import com.jingdong.view.IView.IDelCard;

import java.util.HashMap;
import java.util.Map;

/**
 * 时间:2017/12/16 12:25
 * 作者:韩帅帅
 * 详情:
 */

public class DeleteCardPresenter {
    private IDeleteCardMolde iDeleteCardMolde;
    private IDelCard iDelCard;

    public DeleteCardPresenter(IDelCard iDelCard) {
        this.iDelCard = iDelCard;
        iDeleteCardMolde = new DeleteCardMolde();
    }

    public void deleteCart(final Context context, String uid, String pid) {
        Map<String, String> params = new HashMap<>();
        params.put("uid", uid);
        params.put("pid", pid);
        iDeleteCardMolde.deleteCart(context,params, new OnNetListener<BaseBean>() {
            @Override
            public void onSuccess(BaseBean baseBean) {
                iDelCard.Onsuccess();
            }

            @Override
            public void onFailure(Exception e) {
                Toast.makeText(context, "对于请求失败这事,就不劳揭穿了!!!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
