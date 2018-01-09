package com.jingdong.presenter;

import android.content.Context;
import android.widget.Toast;

import com.jingdong.bean.MyRvMiddleBean;
import com.jingdong.bean.ShouYeBean;
import com.jingdong.model.IModel.IShouYeModel;
import com.jingdong.model.ShouYeModel;
import com.jingdong.net.OnNetListener;
import com.jingdong.view.IView.IShouYeFragment;

/**
 * 时间:2017/12/5 9:13
 * 作者:韩帅帅
 * 详情:
 */

public class ShouYePresenter {
    private IShouYeModel iShouYeModel;
    private IShouYeFragment iShouYeFragment;

    public ShouYePresenter(IShouYeFragment iShouYeFragment) {
        this.iShouYeModel = new ShouYeModel();
        this.iShouYeFragment = iShouYeFragment;
    }
    public void getBannerUrl(final Context context){
        iShouYeModel.getBannerUrl(context,new OnNetListener<ShouYeBean>() {
            @Override
            public void onSuccess(ShouYeBean shouYeBean) {
                if (iShouYeFragment != null) {
                    iShouYeFragment.shouBanner(shouYeBean.getData());
                    iShouYeFragment.shouYeRV(shouYeBean);
                }
            }

            @Override
            public void onFailure(Exception e) {
                    Toast.makeText(context, "对于请求失败这事,就不劳揭穿了!!!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void getMiddleViewUrl(final Context context){
        iShouYeModel.getMiddleViewUrl(context,new OnNetListener<MyRvMiddleBean>() {
            @Override
            public void onSuccess(MyRvMiddleBean myRvMiddleBean) {
                iShouYeFragment.middleView(myRvMiddleBean.getData());
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
        iShouYeFragment = null;
    }
}
