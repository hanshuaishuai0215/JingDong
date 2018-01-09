package com.jingdong.model;

import android.content.Context;

import com.google.gson.Gson;
import com.jingdong.bean.GoodsCardBean;
import com.jingdong.model.IModel.IGoodsCardModel;
import com.jingdong.net.Api;
import com.jingdong.net.HttpUtils;
import com.jingdong.net.OnNetListener;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 时间:2017/12/16 12:01
 * 作者:韩帅帅
 * 详情:
 */

public class GoodsCardModel extends BaseModel implements IGoodsCardModel {
    @Override
    public void getCarts(Context context, Map<String, String> params, final OnNetListener<GoodsCardBean> onNetListener) {
        HttpUtils.getHttpUtils(context).doPost(Api.SELECT_CARD, params, new Callback() {
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
                final GoodsCardBean cardBean = new Gson().fromJson(str, GoodsCardBean.class);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        onNetListener.onSuccess(cardBean);
                    }
                });
            }
        });
    }
}
