package com.jingdong.view.frafment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.jingdong.R;
import com.jingdong.bean.GoodsDetailsBean;
import com.jingdong.presenter.GoodsDetailsPresenter;
import com.jingdong.utils.GlideImageLoader;
import com.jingdong.view.IView.IPro_GoodsFragment;
import com.jingdong.view.ProductDetailsActivity;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.youth.banner.Banner;

import java.util.Arrays;

/**
 * 时间:2017/12/16 10:11
 * 作者:韩帅帅
 * 详情:
 */

public class Pro_GoodsFragment extends Fragment implements IPro_GoodsFragment, ProductDetailsActivity.ShareListener {
    private View view;
    private Banner mBanner;
    private TextView mTvTitle;
    private TextView mTvSubhead;
    private GoodsDetailsPresenter goodsDetailsPresenter;
    private GoodsDetailsBean goodsDetailsBean;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_goods_pro, null);
        //拿到Pid
        Bundle bundle = getArguments();
        String pid = bundle.getString("pid");
        //注册监听
        ((ProductDetailsActivity) getActivity()).setShareListener(this);
        goodsDetailsPresenter = new GoodsDetailsPresenter(this);
        //调用接口
        goodsDetailsPresenter.getProductDetail(getActivity(),pid);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mBanner = (Banner) view.findViewById(R.id.banner);
        //设置图片加载器
        mBanner.setImageLoader(new GlideImageLoader());
        mTvTitle = (TextView) view.findViewById(R.id.tvTitle);
        mTvSubhead = (TextView) view.findViewById(R.id.tvSubhead);
    }

    @Override
    public void show(GoodsDetailsBean goodsDetailsBean) {
        this.goodsDetailsBean = goodsDetailsBean;
        String images = goodsDetailsBean.getData().getImages();
        String[] split = images.split("\\|");
        //设置图片集合
        mBanner.setImages(Arrays.asList(split));
        //banner设置方法全部调用完毕时最后调用
        mBanner.start();
        mTvTitle.setText(goodsDetailsBean.getData().getTitle());
        mTvSubhead.setText(goodsDetailsBean.getData().getSubhead());
    }

    @Override
    public void share() {
        shareWeb(R.drawable.flag_02);
    }

    /**
     * 销毁
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        goodsDetailsPresenter.Dettach();
    }

    /**
     * 分享
     *
     * @param thumb_img
     */
    private void shareWeb(int thumb_img) {
        if (goodsDetailsBean == null) {
            Toast.makeText(getActivity(), "数据还未加载完成，请稍后分享", Toast.LENGTH_SHORT).show();
            return;
        }
        UMImage thumb = new UMImage(getActivity(), thumb_img);
        UMWeb web = new UMWeb(goodsDetailsBean.getData().getDetailUrl());
        web.setThumb(thumb);
        web.setDescription(goodsDetailsBean.getData().getSubhead());
        web.setTitle(goodsDetailsBean.getData().getTitle());
        new ShareAction(getActivity()).withMedia(web)
                .setDisplayList(SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                .setCallback(new MyUMShareListener())
                .open();
    }

    class MyUMShareListener implements UMShareListener {

        @Override
        public void onStart(SHARE_MEDIA share_media) {

        }

        @Override
        public void onResult(SHARE_MEDIA share_media) {
            Toast.makeText(getActivity(), "分享成功", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(SHARE_MEDIA share_media, Throwable throwable) {

        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {
            Toast.makeText(getActivity(), "取消了", Toast.LENGTH_SHORT).show();
        }
    }
}
