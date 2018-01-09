package com.jingdong.model;

import android.content.Context;

import com.google.gson.Gson;
import com.jingdong.bean.BaseBean;
import com.jingdong.model.IModel.IAddCardMolde;
import com.jingdong.net.Api;
import com.jingdong.net.HttpUtils;
import com.jingdong.net.OnNetListener;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 时间:2017/12/16 9:37
 * 作者:韩帅帅
 * 详情:
 */

public class AddCardModel extends BaseModel implements IAddCardMolde{
    @Override
    public void addCard(Context context, Map<String, String> params, final OnNetListener<BaseBean> onNetListener) {
        HttpUtils.getHttpUtils(context).doPost(Api.ADD_CARD, params, new Callback() {
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
                final BaseBean baseBean = new Gson().fromJson(str, BaseBean.class);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        onNetListener.onSuccess(baseBean);
                    }
                });
            }
        });
    }
}
