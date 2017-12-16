package com.jingdong.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.jingdong.R;
import com.jingdong.adapter.ElvAdapter;
import com.jingdong.bean.GoodsCardBean;
import com.jingdong.presenter.GoodsCardPresenter;
import com.jingdong.view.IView.IGoodsCardActivity;

import java.util.List;

public class GoodsCardActivity extends AppCompatActivity implements IGoodsCardActivity{

    private ExpandableListView mElv;
    /**
     * 全选
     */
    private CheckBox mCb;
    /**
     * 合计:
     */
    private TextView mTvTotal;
    /**
     * 去结算(0)
     */
    private TextView mTvCount;
    private GoodsCardPresenter goodsCardPresenter;
    private ElvAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_card);
        initView();
        goodsCardPresenter = new GoodsCardPresenter(this);
        goodsCardPresenter.getCards(getSharedPreferences("user", Context.MODE_PRIVATE).getString("uid", ""));
        //给全选设置点击事件
        mCb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (adapter != null) {
                    adapter.setAllChecked(mCb.isChecked());
                }
            }
        });
    }

    private void initView() {
        mElv = (ExpandableListView) findViewById(R.id.elv);
        mCb = (CheckBox) findViewById(R.id.cb);
        mTvTotal = (TextView) findViewById(R.id.tvTotal);
        mTvCount = (TextView) findViewById(R.id.tvCount);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        goodsCardPresenter.dettach();
    }
    public void setMoney(double price) {
        mTvTotal.setText("合计：" + price);
    }

    public void setCount(int count) {
        mTvCount.setText("去结算(" + count + ")");
    }

    public void setAllSelect(boolean bool) {
        mCb.setChecked(bool);
    }

    @Override
    public void show(List<GoodsCardBean.DataBean> group, List<List<GoodsCardBean.DataBean.ListBean>> child) {
        //设置适配器
        adapter = new ElvAdapter(this, group, child);
        mElv.setAdapter(adapter);

        for (int i = 0; i < group.size(); i++) {
            mElv.expandGroup(i);
        }
    }
}
