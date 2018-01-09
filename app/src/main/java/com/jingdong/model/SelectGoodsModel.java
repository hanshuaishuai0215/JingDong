package com.jingdong.model;

import android.content.Context;

import com.google.gson.Gson;
import com.jingdong.bean.InfoDetailsBean;
import com.jingdong.model.IModel.ISelectGoodsModel;
import com.jingdong.net.Api;
import com.jingdong.net.HttpUtils;
import com.jingdong.net.OnNetListener;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 时间:2018/1/6 10:24
 * 作者:韩帅帅
 * 详情:
 */

public class SelectGoodsModel extends BaseModel implements ISelectGoodsModel{
    @Override
    public void getOrders(Context context, String keywords, String sort, String page, final OnNetListener<InfoDetailsBean> onNetWorkListener) {
        Map<String, String> params = new HashMap<>();
        params.put("keywords", keywords);
        if (!sort.equals("")){
            params.put("sort", sort);
        }else if (!page.equals("")){
            params.put("page", page);
        }
        HttpUtils.getHttpUtils(context).doPost(Api.SEARCHPRODUCTS, params, new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        onNetWorkListener.onFailure(e);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                final InfoDetailsBean infoDetailsBean = new Gson().fromJson(string, InfoDetailsBean.class);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        onNetWorkListener.onSuccess(infoDetailsBean);
                    }
                });
            }
        });
    }
}
