package com.jingdong.view.frafment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.jingdong.R;
import com.jingdong.adapter.MyRvMiddleAdapter;
import com.jingdong.adapter.MyShouYeDianNaoAdapter;
import com.jingdong.adapter.MyShouYePhoneAdapter;
import com.jingdong.bean.MyRvMiddleBean;
import com.jingdong.bean.ShouYeBean;
import com.jingdong.presenter.ShouYePresenter;
import com.jingdong.utils.GlideImageLoader;
import com.jingdong.utils.LooperTextView;
import com.jingdong.view.BossActivity;
import com.jingdong.view.CustomScanActivity;
import com.jingdong.view.IView.IShouYeFragment;
import com.jingdong.view.SelectShopActivity;
import com.jingdong.view.ShouYeWebView;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 时间:2017/12/3 21:16
 * 作者:韩帅帅
 * 详情:
 */
public class ShouyeFragment extends Fragment implements IShouYeFragment, View.OnClickListener {

    private Banner banner;
    private List<String> list = new ArrayList<String>();
    private ShouYePresenter shouYePresenter;
    private View view;
    private RecyclerView rv;
    private TextView tv_time;
    private SimpleDateFormat format;
    private Handler handler = new Handler();
    private long recLen;
    private Banner mShouyebanner;
    private RecyclerView mZhuyerecycleview;
    private TextView mShouyeTime;
    private RecyclerView mZhuyerecycleview2;
    private TextView mShouyetuijian;
    private RecyclerView mZhuyerecycleview3;
    /**
     * 搜索笔记本
     */
    private EditText mSelectShop;
    private LooperTextView mLtv;
    private ImageView mErClick;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.shouye_fragment, null);
        shouYePresenter = new ShouYePresenter(this);
        shouYePresenter.getBannerUrl(getActivity());
        shouYePresenter.getMiddleViewUrl(getActivity());
        initView(view);
        mLtv.setTipList(generateTips());
        return view;
    }

    /**
     * 中间的RecyclerView
     */
    @Override
    public void middleView(List<MyRvMiddleBean.DataBean> list) {
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.HORIZONTAL, false);
        rv.setLayoutManager(manager);
        MyRvMiddleAdapter adapter = new MyRvMiddleAdapter(getActivity(), list);
        rv.setAdapter(adapter);
    }

    @Override
    public void shouYeRV(ShouYeBean shouYeBean) {
        /**
         * 设置秒杀倒计时
         */
        ShouYeBean.MiaoshaBean miaoshaBean = shouYeBean.getMiaosha();
        recLen = miaoshaBean.getTime();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                recLen -= 1000;
                format = new SimpleDateFormat("HH:mm:ss");
                final String str = format.format(recLen);
                tv_time.setText(str);
                handler.postDelayed(this, 1000);
            }
        };
        handler.postDelayed(runnable, 100);
        /**
         * 设置到RecycleView
         */
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 1, GridLayoutManager.HORIZONTAL, false);
        mZhuyerecycleview2.setLayoutManager(manager);
        MyShouYePhoneAdapter adapter = new MyShouYePhoneAdapter(getActivity(), miaoshaBean.getList());
        mZhuyerecycleview2.setAdapter(adapter);
        /**
         * 设置首页推荐
         */
        ShouYeBean.TuijianBean tuijian = shouYeBean.getTuijian();
        mShouyetuijian.setText(tuijian.getName());
        GridLayoutManager manager2 = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
        mZhuyerecycleview3.setLayoutManager(manager2);
        MyShouYeDianNaoAdapter adapter2 = new MyShouYeDianNaoAdapter(getActivity(), tuijian.getList());
        mZhuyerecycleview3.setAdapter(adapter2);
    }

    /**
     * 顶部的Banner以及banner的点击事件
     *
     * @param list
     */
    @Override
    public void shouBanner(final List<ShouYeBean.DataBean> list) {
        List<String> images = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            images.add(list.get(i).getIcon());
        }
        banner.setImageLoader(new GlideImageLoader());
        banner.setImages(images);
        banner.start();
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                if (list.get(position).getUrl().length() < 1) {
                    return;
                }
                Toast.makeText(getActivity(), "准备跳转到WebView!!!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), ShouYeWebView.class);
                intent.putExtra("WebViewUrl", list.get(position).getUrl());
                getActivity().startActivity(intent);
            }
        });
    }

    private void initView(View view) {
        banner = (Banner) view.findViewById(R.id.shouyebanner);
        rv = (RecyclerView) view.findViewById(R.id.zhuyerecycleview);
        tv_time = (TextView) view.findViewById(R.id.shouye_time);
        mShouyebanner = (Banner) view.findViewById(R.id.shouyebanner);
        mZhuyerecycleview = (RecyclerView) view.findViewById(R.id.zhuyerecycleview);
        mShouyeTime = (TextView) view.findViewById(R.id.shouye_time);
        mZhuyerecycleview2 = (RecyclerView) view.findViewById(R.id.zhuyerecycleview2);
        mShouyetuijian = (TextView) view.findViewById(R.id.shouyetuijian);
        mShouyebanner.setOnClickListener(this);
        mZhuyerecycleview.setOnClickListener(this);
        mShouyeTime.setOnClickListener(this);
        mZhuyerecycleview2.setOnClickListener(this);
        mShouyetuijian.setOnClickListener(this);
        mZhuyerecycleview3 = (RecyclerView) view.findViewById(R.id.zhuyerecycleview3);
        mSelectShop = (EditText) view.findViewById(R.id.selectShop);
        mSelectShop.setOnClickListener(this);
        mLtv = (LooperTextView) view.findViewById(R.id.ltv);
        mErClick = (ImageView) view.findViewById(R.id.ErClick);
        mErClick.setOnClickListener(this);
        //解决ScrollView嵌套RecyclerView导致滑动不流畅的问题
        mZhuyerecycleview3.setNestedScrollingEnabled(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.selectShop:
                Intent intent = new Intent(getActivity(), SelectShopActivity.class);
                startActivity(intent);
                break;
            case R.id.ErClick:
                ErClick();
                break;
        }
    }

    /**
     * 二维码扫描
     */
    private void ErClick() {
        //假如你要用的是fragment进行界面的跳转
        IntentIntegrator intentIntegrator = IntentIntegrator.forSupportFragment(ShouyeFragment.this).setCaptureActivity(CustomScanActivity.class);
        intentIntegrator
                .setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)
                .setPrompt("将二维码/条码放入框内，即可自动扫描")//写那句提示的话
                .setOrientationLocked(false)//扫描方向固定
                .setCaptureActivity(CustomScanActivity.class) // 设置自定义的activity是CustomActivity
                .initiateScan(); // 初始化扫描
    }

    /**
     * 首页跑马灯效果
     *
     * @return
     */
    private List<String> generateTips() {
        List<String> tips = new ArrayList<>();
        tips.add("AI就要掌控世界了？绝对没你想得那么快！");
        tips.add("衣服大一号,人就瘦一圈?");
        tips.add("闪瞎:全球最贵五辆摩托车");
        tips.add("一半受访者会被类人机器人吓跑!");
        tips.add("深度学习索引速度更快、占用空间更少");
        tips.add("资源| 谷歌开源TFGAN：轻量级生成对抗网络工具库?");
        tips.add("谷歌团队越狱苹果系统");
        return tips;
    }
    //获取扫描的结果
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (intentResult != null) {
            if (intentResult.getContents() == null) {
            } else {
                Intent intent  = new Intent(getActivity(), BossActivity.class);
                intent.putExtra("page",2);
                intent.putExtra("ScanResult",intentResult.getContents());
                startActivity(intent);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        shouYePresenter.Dettach();
    }
}