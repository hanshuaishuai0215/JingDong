package com.jingdong.view.frafment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.jingdong.R;
import com.jingdong.adapter.ElvAdapter;
import com.jingdong.bean.GoodsCardBean;
import com.jingdong.presenter.GoodsCardPresenter;
import com.jingdong.view.IView.IGoodsCardFragment;

import java.util.List;

/**
 * 时间:2017/12/3 21:08
 * 作者:韩帅帅
 * 详情:
 */
public class GouwuFragment extends Fragment implements IGoodsCardFragment {
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.gouwu_fragment, null);
        goodsCardPresenter = new GoodsCardPresenter(this);
        goodsCardPresenter.getCards(getActivity().getSharedPreferences("user", Context.MODE_PRIVATE).getString("uid", ""));
        initView(view);
        //给全选设置点击事件
        mCbAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (adapter != null) {
                    adapter.setAllChecked(mCbAll.isChecked());
                }
            }
        });
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        goodsCardPresenter.dettach();
    }

    public static void setMoney(double price) {
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

    private void initView(View view) {
        mElv = (ExpandableListView) view.findViewById(R.id.elv);
        mCbAll = (CheckBox) view.findViewById(R.id.cbAll);
        mTvTotal = (TextView) view.findViewById(R.id.tvTotal);
        mTvCount = (TextView) view.findViewById(R.id.tvCount);
    }
}
