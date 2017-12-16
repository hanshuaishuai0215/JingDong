package com.jingdong.model;

import com.google.gson.Gson;
import com.jingdong.bean.InfoDetailsBean;
import com.jingdong.model.IModel.IInfoDetailsModel;
import com.jingdong.net.Api;
import com.jingdong.net.HttpUtils;
import com.jingdong.net.OnNetListener;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 时间:2017/12/3 21:17
 * 作者:韩帅帅
 * 详情:
 */

public class InfoDetailsModel extends BaseModel implements IInfoDetailsModel{

    @Override
    public void getInfoDetails(Map<String, String> params, final OnNetListener<InfoDetailsBean> onNetListener) {
        HttpUtils.getHttpUtils().doPost(Api.PRODUCT_CATAGORY_LIST, params, new Callback() {
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
                final InfoDetailsBean detailsBean = new Gson().fromJson(str, InfoDetailsBean.class);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        onNetListener.onSuccess(detailsBean);
                    }
                });
            }
        });
    }
}
