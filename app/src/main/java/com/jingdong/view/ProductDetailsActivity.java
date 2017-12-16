package com.jingdong.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.jingdong.R;
import com.jingdong.adapter.VpDetailsAdapter;
import com.jingdong.presenter.AddCardPresenter;
import com.jingdong.view.IView.IProDetailActivity;
import com.jingdong.view.frafment.Pro_AppraiseFragment;
import com.jingdong.view.frafment.Pro_DetailsFragment;
import com.jingdong.view.frafment.Pro_GoodsFragment;

import java.util.ArrayList;
import java.util.List;
/**
 * 时间:2017/12/16 12:32
 * 作者:韩帅帅
 * 详情:
 */
public class ProductDetailsActivity extends AppCompatActivity implements View.OnClickListener,IProDetailActivity {

    private ImageView mIvBack;
    /**
     * 商品
     */
    private RadioButton mRbGoods;
    /**
     * 详情
     */
    private RadioButton mRbDetails;
    /**
     * 评价
     */
    private RadioButton mRbAppraise;
    private RadioGroup mRg;
    private ImageView mIvShare;
    private ImageView mIvMsg;
    private ViewPager mVp;
    private LinearLayout mLlSupplier;
    private LinearLayout mLlShop;
    private LinearLayout mLlAttention;
    private LinearLayout mLlCard;
    private AddCardPresenter addCardPresenter;
    private String pid;
    /**
     * 加入购物车
     */
    private TextView mTvAddCard;
    private ShareListener shareListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        //接收pid
        Intent intent = getIntent();
        pid = intent.getStringExtra("pid");
        getSharedPreferences("user",Context.MODE_PRIVATE).edit().putString("pid",pid).commit();
        Log.e("ProductDetailsActivity","pid:"+pid);
        addCardPresenter = new AddCardPresenter(this);
        initView();
        //给viewPager填充内容
        Pro_GoodsFragment goodsFragment = new Pro_GoodsFragment();
        Pro_DetailsFragment detailsFragment = new Pro_DetailsFragment();
        Pro_AppraiseFragment appraiseFragment = new Pro_AppraiseFragment();
        List<Fragment> list = new ArrayList<>();
        list.add(goodsFragment);
        list.add(detailsFragment);
        list.add(appraiseFragment);
        VpDetailsAdapter adapter = new VpDetailsAdapter(getSupportFragmentManager(), list);
        mVp.setAdapter(adapter);

        //传pid
        Bundle bundle = new Bundle();
        bundle.putString("pid", pid);
        goodsFragment.setArguments(bundle);
    }

    private void initView() {
        mIvBack = (ImageView) findViewById(R.id.ivBack);
        mRbGoods = (RadioButton) findViewById(R.id.rbGoods);
        mRbDetails = (RadioButton) findViewById(R.id.rbDetails);
        mRbAppraise = (RadioButton) findViewById(R.id.rbAppraise);
        mRg = (RadioGroup) findViewById(R.id.rg);
        mIvShare = (ImageView) findViewById(R.id.ivShare);
        mIvShare.setOnClickListener(this);
        mIvMsg = (ImageView) findViewById(R.id.ivMsg);
        mVp = (ViewPager) findViewById(R.id.vp);
        mLlSupplier = (LinearLayout) findViewById(R.id.llSupplier);
        mLlShop = (LinearLayout) findViewById(R.id.llShop);
        mLlAttention = (LinearLayout) findViewById(R.id.llAttention);
        mLlCard = (LinearLayout) findViewById(R.id.llCard);
        mLlCard.setOnClickListener(this);
        mTvAddCard = (TextView) findViewById(R.id.tvAddCard);
        mTvAddCard.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivShare:
                if (shareListener != null) {
                    shareListener.share();
                }
                break;
            case R.id.llCard:
                //跳转到购物车界面
                Intent intent = new Intent(ProductDetailsActivity.this, GoodsCardActivity.class);
                startActivity(intent);
                break;
            case R.id.tvAddCard:
                //加入购物车
                SharedPreferences sp = getSharedPreferences("user", Context.MODE_PRIVATE);
                addCardPresenter.addCard(pid, sp.getString("uid", ""));
                break;
        }
    }

    public void setShareListener(ShareListener shareListener) {
        this.shareListener = shareListener;
    }

    public interface ShareListener {
        void share();
    }

    @Override
    public void show(String str) {
        Toast.makeText(ProductDetailsActivity.this, str, Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        addCardPresenter.dettach();
    }
}
