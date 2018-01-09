package com.jingdong.presenter;

import android.content.Context;
import android.widget.Toast;

import com.jingdong.bean.BaseBean;
import com.jingdong.bean.GetOrdersBean;
import com.jingdong.model.IModel.IOrderModel;
import com.jingdong.model.OrderModel;
import com.jingdong.net.OnNetListener;
import com.jingdong.view.IView.IGoodsCardFragment;
import com.jingdong.view.IView.IOrderListActivity;

/**
 * 时间:2018/1/5 20:34
 * 作者:韩帅帅
 * 详情:
 */

public class OrderPresenter {
    private IGoodsCardFragment iGoodsCardFragment;
    private IOrderListActivity iOrderListActivity;
    private IOrderModel iOrderModel;

    public OrderPresenter(IGoodsCardFragment iGoodsCardFragment) {
        this.iGoodsCardFragment = iGoodsCardFragment;
        iOrderModel = new OrderModel();
    }

    public OrderPresenter(IOrderListActivity iOrderListActivity) {
        this.iOrderListActivity = iOrderListActivity;
        iOrderModel = new OrderModel();
    }

    /**
     * 创建订单
     */
    public void createOrder(final Context context, String uid, String price) {
        iOrderModel.createOrder(context,uid, price, new OnNetListener<BaseBean>() {
            @Override
            public void onSuccess(BaseBean baseBean) {
                if (iGoodsCardFragment != null) {
                    iGoodsCardFragment.showCreateOrder(baseBean);
                }
            }

            @Override
            public void onFailure(Exception e) {
                Toast.makeText(context,"对于请求失败这事,就不劳揭穿了!!!",Toast.LENGTH_SHORT).show();
            }
        });
    }
    /**
     * 订单数据展示
     */
    public void getOrders(final Context context, String status){

        iOrderModel.getOrders(context,"71",status, new OnNetListener<GetOrdersBean>() {
            @Override
            public void onSuccess(GetOrdersBean getOrdersBean) {
                if (iOrderListActivity != null) {
                    iOrderListActivity.showOrder(getOrdersBean.getData());
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
        iOrderListActivity = null;
        iGoodsCardFragment = null;
    }
}
