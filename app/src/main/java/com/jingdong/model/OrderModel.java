package com.jingdong.model;

import android.content.Context;

import com.google.gson.Gson;
import com.jingdong.bean.BaseBean;
import com.jingdong.bean.GetOrdersBean;
import com.jingdong.model.IModel.IOrderModel;
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
 * 时间:2018/1/5 20:27
 * 作者:韩帅帅
 * 详情:
 */

public class OrderModel extends BaseModel implements IOrderModel{
    /**
     * 创建订单
     * @param uid
     * @param price
     * @param onNetListener
     */
    @Override
    public void createOrder(Context context, String uid, String price, final OnNetListener<BaseBean> onNetListener) {
        String url = String.format(Api.CREATEORDER,uid,price);
        HttpUtils.getHttpUtils(context).doGet(url, new Callback() {
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

    /**
     * 显示订单列表
     * @param uid
     * @param onNetWorkListener
     */
    @Override
    public void getOrders(Context context,String uid,String status, final OnNetListener<GetOrdersBean> onNetWorkListener) {
        Map<String, String> params = new HashMap<>();
        params.put("uid", uid);
        if (status != null){
            params.put("status",status);
        }
        HttpUtils.getHttpUtils(context).doPost(Api.GETORDERS, params, new Callback() {
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
                final GetOrdersBean getOrdersBean = new Gson().fromJson(string, GetOrdersBean.class);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        onNetWorkListener.onSuccess(getOrdersBean);
                    }
                });

            }
        });
    }
}
