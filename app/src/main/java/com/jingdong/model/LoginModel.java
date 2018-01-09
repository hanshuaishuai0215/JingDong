package com.jingdong.model;

import android.content.Context;

import com.google.gson.Gson;
import com.jingdong.bean.LoginBean;
import com.jingdong.model.IModel.ILoginModel;
import com.jingdong.net.Api;
import com.jingdong.net.HttpUtils;
import com.jingdong.net.OnNetListener;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 时间:2017/12/3 21:17
 * 作者:韩帅帅
 * 详情:
 */

public class LoginModel extends BaseModel implements ILoginModel {
    @Override
    public void login(Context context, String account, String pwd, final OnNetListener<LoginBean> onNetListener) {
        Map<String, String> params = new HashMap<>();
        params.put("mobile", account);
        params.put("password", pwd);
        HttpUtils.getHttpUtils(context).doPost(Api.LOGIN, params, new Callback() {
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
                String string = response.body().string();
                final LoginBean loginBean = new Gson().fromJson(string, (Type) LoginBean.class);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        onNetListener.onSuccess(loginBean);
                    }
                });
            }
        });
    }



}
