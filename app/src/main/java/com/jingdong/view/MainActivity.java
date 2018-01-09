package com.jingdong.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jingdong.R;
import com.jingdong.bean.LoginBean;
import com.jingdong.presenter.MainPresenter;
import com.jingdong.view.IView.IMainActivity;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import java.util.Map;
/**
 * 时间:2017/12/3 21:19
 * 作者:韩帅帅
 * 详情:
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener,IMainActivity {

    /**
     * 分享到QQ
     */
    private Button mBtn1;
    /**
     * QQ登录
     */
    private Button mBtn2;
    /**
     * 请输入手机号
     */
    private EditText mEtCount;
    /**
     * 请输入密码
     */
    private EditText mEtPwd;
    /**
     * 登录
     */
    private Button mButton;
    /**
     * 注册
     */
    private Button mButton2;
    /**
     * 登录
     */
    private TextView mTextView;
    /**
     * 登录
     */
    private Button mBtnLogin;
    /**
     * 注册
     */
    private Button mBtnRegister;
    private MainPresenter mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainPresenter = new MainPresenter(this);
        initView();
    }

    private void ShareWeb(int thumb_img) {
        UMImage thumb = new UMImage(MainActivity.this, thumb_img);
        UMWeb web = new UMWeb("http://geektyper.com/");
        web.setThumb(thumb);
        web.setDescription("骚年,你的功力还差远呢!!");
        web.setTitle("HackerTyper Neo");
        new ShareAction(MainActivity.this).withMedia(web).setPlatform(SHARE_MEDIA.QZONE).setCallback(shareListener).share();
    }

    private UMShareListener shareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Toast.makeText(MainActivity.this, "成功了", Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(MainActivity.this, "失败了" + t.getMessage(), Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(MainActivity.this, "取消了", Toast.LENGTH_LONG).show();
        }
    };

    UMAuthListener authListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            Toast.makeText(MainActivity.this, "登录成功!!!", Toast.LENGTH_LONG).show();
            //保存uid
            SharedPreferences sp = getSharedPreferences("user", Context.MODE_PRIVATE);
            SharedPreferences.Editor edit = sp.edit();
            edit.putString("uid","1775");
            edit.putString("uName",data.get("name"));
            edit.putString("headimg",data.get("profile_image_url"));
            edit.commit();
            Intent intent = new Intent(MainActivity.this, BossActivity.class);
            startActivity(intent);
        }
        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Toast.makeText(MainActivity.this, "错误", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText(MainActivity.this, "取消", Toast.LENGTH_LONG).show();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    private void initView() {
        mBtn1 = (Button) findViewById(R.id.btn1);
        mBtn1.setOnClickListener(this);
        mBtn2 = (Button) findViewById(R.id.btn2);
        mBtn2.setOnClickListener(this);
        mEtCount = (EditText) findViewById(R.id.et_count);
        mEtPwd = (EditText) findViewById(R.id.et_pwd);
        mButton2 = (Button) findViewById(R.id.btn_register);
        mButton2.setOnClickListener(this);
        mTextView = (TextView) findViewById(R.id.textView);
        mEtCount.setOnClickListener(this);
        mEtPwd.setOnClickListener(this);
        mBtnLogin = (Button) findViewById(R.id.btn_Login);
        mBtnLogin.setOnClickListener(this);
        mBtnRegister = (Button) findViewById(R.id.btn_register);
        mBtnRegister.setOnClickListener(this);
        mTextView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn1:
                //分享到QQ
                ShareWeb(R.drawable.flag_02);
                break;
            case R.id.btn2:
                //授权第三方登录
                UMShareAPI.get(this).getPlatformInfo(this, SHARE_MEDIA.QQ, authListener);//可以获取到用户信息
                //UMShareAPI.get(MainActivity.this).doOauthVerify(MainActivity.this, SHARE_MEDIA.QQ, authListener);//获取不到用户信息
                break;
            case R.id.btn_register:
                mainPresenter.register();
                break;
            case R.id.et_count:
                break;
            case R.id.et_pwd:
                break;
            case R.id.btn_Login:
                mainPresenter.login(this);
                break;
            case R.id.textView:
                break;
        }
    }
    @Override
    public String getAccount() {
        return mEtCount.getText().toString().trim();
    }

    @Override
    public String getPwd() {
        return mEtPwd.getText().toString().trim();
    }

    @Override
    public void show(String str) {
        Toast.makeText(MainActivity.this, str, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void toRegisterAc() {
        Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    @Override
    public void toClassAc(LoginBean loginBean) {
        if (loginBean.getCode().equals("1")){
            mEtCount.setText(null);
            mEtPwd.setText(null);
            return;
        }
        //保存uid
        SharedPreferences sp = getSharedPreferences("user", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("uid",loginBean.getData().getUid()+"");
        edit.putString("uName",loginBean.getData().getUsername());
        edit.commit();
        Intent intent = new Intent(MainActivity.this, BossActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainPresenter.Dettach();
    }
}
