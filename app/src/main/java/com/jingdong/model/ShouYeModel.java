package com.jingdong.model;

import com.google.gson.Gson;
import com.jingdong.bean.MyRvMiddleBean;
import com.jingdong.bean.ShouYeBean;
import com.jingdong.model.IModel.IShouYeModel;
import com.jingdong.net.Api;
import com.jingdong.net.HttpUtils;
import com.jingdong.net.OnNetListener;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 时间:2017/12/5 9:03
 * 作者:韩帅帅
 * 详情:
 */

public class ShouYeModel extends BaseModel implements IShouYeModel{

    @Override
    public void getBannerUrl(final OnNetListener<ShouYeBean> onNetListener) {
        HttpUtils.getHttpUtils().doGet(Api.ZHUYEURL, new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        onNetListener.onFailure(e);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str = response.body().string();
                final ShouYeBean shouYeBean = new Gson().fromJson(str, ShouYeBean.class);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        onNetListener.onSuccess(shouYeBean);
                    }
                });
            }
        });
    }

    @Override
    public void getMiddleViewUrl(final OnNetListener<MyRvMiddleBean> onNetListener) {
        HttpUtils.getHttpUtils().doGet(Api.ZHUYEMIDDLEVIEW, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                onNetListener.onFailure(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str = response.body().string();
                final MyRvMiddleBean middleBean = new Gson().fromJson(str, MyRvMiddleBean.class);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        onNetListener.onSuccess(middleBean);
                    }
                });
            }
        });
    }
}
