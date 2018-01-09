package com.jingdong.presenter;

import android.content.Context;
import android.widget.Toast;

import com.jingdong.bean.InfoDetailsBean;
import com.jingdong.model.IModel.IInfoDetailsModel;
import com.jingdong.model.InfoDetailsModel;
import com.jingdong.net.OnNetListener;
import com.jingdong.view.IView.IInfoDetailsActivity;

import java.util.HashMap;

/**
 * 时间:2017/12/15 20:14
 * 作者:韩帅帅
 * 详情:
 */

public class InfoDetailsPresenter {
    private IInfoDetailsModel infoDetailsModel;
    private IInfoDetailsActivity iInfoDetailsActivity;

    public InfoDetailsPresenter(IInfoDetailsActivity iInfoDetailsActivity) {
        this.iInfoDetailsActivity = iInfoDetailsActivity;
        infoDetailsModel = new InfoDetailsModel();
    }
    public void getProductDetail(final Context context, String pscid, String page, String sort){
        HashMap<String, String> params = new HashMap<>();
        params.put("pscid",pscid);
        params.put("page",page);
        params.put("sort",sort);
        params.put("source","android");
        infoDetailsModel.getInfoDetails(context,params, new OnNetListener<InfoDetailsBean>() {
            @Override
            public void onSuccess(InfoDetailsBean infoDetailsBean) {
                if (iInfoDetailsActivity != null) {
                    iInfoDetailsActivity.showList(infoDetailsBean.getData());
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
        iInfoDetailsActivity = null;
    }
}
