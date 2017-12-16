package com.jingdong.model;

import com.google.gson.Gson;
import com.jingdong.bean.GoodsDetailsBean;
import com.jingdong.model.IModel.IGoodsDetailsModel;
import com.jingdong.net.Api;
import com.jingdong.net.HttpUtils;
import com.jingdong.net.OnNetListener;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 时间:2017/12/16 10:56
 * 作者:韩帅帅
 * 详情:
 */

public class GoodsDetailsModel extends BaseModel implements IGoodsDetailsModel{
    @Override
    public void getProductDetail(String pid, final OnNetListener<GoodsDetailsBean> onNetListener) {
        String url = String.format(Api.PRODUCT_DETAIL, pid);
        HttpUtils.getHttpUtils().doGet(url, new Callback() {
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
                final GoodsDetailsBean goodsDetailsBean = new Gson().fromJson(str, GoodsDetailsBean.class);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        onNetListener.onSuccess(goodsDetailsBean);
                    }
                });
            }
        });
    }
}
