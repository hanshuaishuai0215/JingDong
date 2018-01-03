package com.jingdong.view.frafment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.jingdong.R;

/**
 * 时间:2017/12/3 21:08
 * 作者:韩帅帅
 * 详情:
 */
public class FaxianFragment extends Fragment {

    private View view;
    private WebView mFaxianWebview;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.faxian_fragment, null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mFaxianWebview = (WebView) view.findViewById(R.id.faxian_webview);
        WebSettings webSettings = mFaxianWebview.getSettings();
        //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings.setJavaScriptEnabled(true);
        //支持自动加载图片
        webSettings.setLoadsImagesAutomatically(true);
        //设置编码格式
        webSettings.setDefaultTextEncodingName("utf-8");
        //WebView加载web资源
        mFaxianWebview.loadUrl("https://h5.m.jd.com/active/faxian/list/article-list.html");
       ///覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        mFaxianWebview.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });
    }
    //销毁Webview
    @Override
    public void onDestroy() {
        if (mFaxianWebview != null) {
            mFaxianWebview.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            mFaxianWebview.clearHistory();
            ((ViewGroup) mFaxianWebview.getParent()).removeView(mFaxianWebview);
            mFaxianWebview.destroy();
            mFaxianWebview = null;
        }
        super.onDestroy();
    }

}
