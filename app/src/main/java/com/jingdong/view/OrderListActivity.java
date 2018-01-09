package com.jingdong.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.jingdong.R;
import com.jingdong.adapter.OrderAdapter;
import com.jingdong.bean.GetOrdersBean;
import com.jingdong.presenter.OrderPresenter;
import com.jingdong.view.IView.IOrderListActivity;

import java.util.List;

public class OrderListActivity extends AppCompatActivity implements View.OnClickListener,IOrderListActivity {

    private ImageView mBackOrder;
    private ImageView mIvType;
    /**
     * 待支付
     */
    private TextView mDaizhifu;
    /**
     * 已支付
     */
    private TextView mYizhifu;
    /**
     * 已取消
     */
    private TextView mYiquxiao;
    private RecyclerView mRv;
    private OrderPresenter orderPresenter;
    private PopupWindow popupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);
        orderPresenter = new OrderPresenter(this);
        orderPresenter.getOrders(this,"0");
        initView();
    }

    private void initView() {
        mBackOrder = (ImageView) findViewById(R.id.backOrder);
        mBackOrder.setOnClickListener(this);
        mIvType = (ImageView) findViewById(R.id.ivType);
        mIvType.setOnClickListener(this);
        mDaizhifu = (TextView) findViewById(R.id.daizhifu);
        mDaizhifu.setOnClickListener(this);
        mYizhifu = (TextView) findViewById(R.id.yizhifu);
        mYizhifu.setOnClickListener(this);
        mYiquxiao = (TextView) findViewById(R.id.yiquxiao);
        mYiquxiao.setOnClickListener(this);
        mRv = (RecyclerView) findViewById(R.id.rv);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backOrder:
                this.finish();
                break;
            case R.id.ivType:
                //弹出popupwindow
                final View view = View.inflate(OrderListActivity.this, R.layout.pop_item, null);
                TextView tv1 = view.findViewById(R.id.tv1);
                TextView tv2 = view.findViewById(R.id.tv2);
                TextView tv3 = view.findViewById(R.id.tv3);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                popupWindow = new PopupWindow(view, layoutParams.width, layoutParams.height);
                popupWindow.showAsDropDown(mIvType, 0, 30);
                tv1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(OrderListActivity.this, "待支付", Toast.LENGTH_SHORT).show();
                        orderPresenter.getOrders(OrderListActivity.this,"0");
                        popupWindow.dismiss();
                    }
                });
                tv2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(OrderListActivity.this, "已支付", Toast.LENGTH_SHORT).show();
                        orderPresenter.getOrders(OrderListActivity.this,"1");
                        popupWindow.dismiss();
                    }
                });
                tv3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(OrderListActivity.this, "已取消", Toast.LENGTH_SHORT).show();
                        orderPresenter.getOrders(OrderListActivity.this,"2");
                        popupWindow.dismiss();
                    }
                });
                break;
            case R.id.daizhifu:
                if(popupWindow != null){
                    popupWindow.dismiss();
                }
                orderPresenter.getOrders(this,"0");
                break;
            case R.id.yizhifu:
                if(popupWindow != null){
                    popupWindow.dismiss();
                }
                orderPresenter.getOrders(this,"1");
                break;
            case R.id.yiquxiao:
                if(popupWindow != null){
                    popupWindow.dismiss();
                }
                orderPresenter.getOrders(this,"2");
                break;
        }
    }

    @Override
    public void showOrder(List<GetOrdersBean.DataBean> beanList) {
        int size = beanList.size();
        mRv.setLayoutManager(new LinearLayoutManager(this));
        OrderAdapter orderAdapter = new OrderAdapter(OrderListActivity.this, beanList);
        mRv.setAdapter(orderAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        orderPresenter.Dettach();
    }
}
