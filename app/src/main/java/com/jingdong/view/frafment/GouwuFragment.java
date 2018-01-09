package com.jingdong.view.frafment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jingdong.R;
import com.jingdong.adapter.ElvAdapter;
import com.jingdong.bean.BaseBean;
import com.jingdong.bean.GoodsCardBean;
import com.jingdong.presenter.GoodsCardPresenter;
import com.jingdong.presenter.OrderPresenter;
import com.jingdong.view.IView.IGoodsCardFragment;
import com.jingdong.view.MainActivity;
import com.jingdong.view.OrderListActivity;

import java.util.List;

/**
 * 时间:2017/12/3 21:08
 * 作者:韩帅帅
 * 详情:
 */
public class GouwuFragment extends BaseFragment implements IGoodsCardFragment, View.OnClickListener {
    private View view;
    private ExpandableListView mElv;
    /**
     * 合计:
     */
    private static TextView mTvTotal;
    /**
     * 去结算(0)
     */
    private static TextView mTvCount;
    private GoodsCardPresenter goodsCardPresenter;
    private ElvAdapter adapter;
    /**
     * 全选
     */
    private static CheckBox mCbAll;
    private ImageView mIvNullCar;
    /**
     * 点击登陆
     */
    private Button mBtnGoLogin;
    private GouwuFragment gouwuFragment;
    private boolean isReady = false;
    private OrderPresenter orderPresenter;
    private static double priceAll;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //取消预加载
        if (view == null) {
            view = inflater.inflate(R.layout.gouwu_fragment, null);
            initView(view);
            isReady = true;
            delayLoad();
            Log.d("info", "onCreateView");
        } else {
            Log.d("info", "rootView != null");
        }
        // Cache rootView.
        // remove rootView from its parent
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            parent.removeView(view);
        }
        return view;
    }

    /**
     * 销毁
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (goodsCardPresenter != null) {
            goodsCardPresenter.Dettach();
        } else if (orderPresenter != null) {
            orderPresenter.Dettach();
        }

    }

    public static void setMoney(double price) {
        priceAll = price;
        mTvTotal.setText("合计：" + price);
    }

    public static void setCount(int count) {
        mTvCount.setText("去结算(" + count + ")");
    }

    public static void setAllSelect(boolean bool) {
        mCbAll.setChecked(bool);
    }

    @Override
    public void show(List<GoodsCardBean.DataBean> group, List<List<GoodsCardBean.DataBean.ListBean>> child) {
        //设置适配器
        adapter = new ElvAdapter(getActivity(), group, child);
        mElv.setAdapter(adapter);
        for (int i = 0; i < group.size(); i++) {
            mElv.expandGroup(i);
        }
    }

    @Override
    public void showCreateOrder(BaseBean baseBean) {
        Toast.makeText(getActivity(), baseBean.getMsg(), Toast.LENGTH_SHORT).show();
        if (baseBean.getCode().equals("0")) {
            Intent intent = new Intent(getActivity(), OrderListActivity.class);
            startActivity(intent);
        }
    }

    private void initView(View view) {
        mElv = (ExpandableListView) view.findViewById(R.id.elv);
        mCbAll = (CheckBox) view.findViewById(R.id.cbAll);
        mTvTotal = (TextView) view.findViewById(R.id.tvTotal);
        mTvCount = (TextView) view.findViewById(R.id.tvCount);
        mIvNullCar = (ImageView) view.findViewById(R.id.ivNullCar);
        mBtnGoLogin = (Button) view.findViewById(R.id.btnGoLogin);
        mBtnGoLogin.setOnClickListener(this);
        mTvCount.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnGoLogin:
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                break;
            case R.id.tvCount:
                orderPresenter.createOrder(getActivity(), "72", priceAll + "");
                break;
        }
    }

    //取消预加载  负责会提前土司
    @Override
    protected void delayLoad() {
        if (!isReady || !isVisible) {
            return;
        }
        //　This is a random widget, it will be instantiation in init()
        if (gouwuFragment == null) {
            init();
        }
    }

    private void init() {
        gouwuFragment = new GouwuFragment();
        initView(view);
        goodsCardPresenter = new GoodsCardPresenter(this);
        orderPresenter = new OrderPresenter(this);
        String string = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE).getString("uid", "");
        if (string.length() < 1) {
            Toast.makeText(getActivity(), "请登录!!!", Toast.LENGTH_SHORT).show();
        } else {
            goodsCardPresenter.getCards(getActivity(), string);
            mElv.setVisibility(View.VISIBLE);//mElv处于可见状态
            mIvNullCar.setVisibility(View.GONE);//mIvNullCar处于隐藏状态
            mBtnGoLogin.setVisibility(View.GONE);//mBtnGoLogin处于隐藏状态
        }
        //给全选设置点击事件
        mCbAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (adapter != null) {
                    adapter.setAllChecked(mCbAll.isChecked());
                }
            }
        });
    }
}
