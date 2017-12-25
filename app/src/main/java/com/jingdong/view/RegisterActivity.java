package com.jingdong.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.jingdong.R;
import com.jingdong.presenter.RegisterPresenter;
import com.jingdong.view.IView.IRegisterActivity;

/**
 * 时间:2017/12/3 21:19
 * 作者:韩帅帅
 * 详情:
 */
public class RegisterActivity extends AppCompatActivity implements IRegisterActivity, View.OnClickListener {

    private ImageView mIvBack;
    /**
     * 手机号
     */
    private EditText mEtPhone;
    /**
     * 请输入密码
     */
    private EditText mEtPwd;
    private ImageView mIvPwd;
    /**
     * 注册
     */
    private Button mBtLogin;
    private RegisterPresenter registerPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        registerPresenter = new RegisterPresenter(this);
        initView();
    }

    private void initView() {
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mEtPhone = (EditText) findViewById(R.id.et_phone);
        mEtPwd = (EditText) findViewById(R.id.et_pwd);
        mIvPwd = (ImageView) findViewById(R.id.iv_pwd);
        mBtLogin = (Button) findViewById(R.id.bt_login);
        mBtLogin.setOnClickListener(this);
        mIvBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_login:
                //注册
                registerPresenter.register();
                break;
            case R.id.iv_back:
                this.finish();
                break;
        }
    }

    @Override
    public String getAccount() {
        return mEtPhone.getText().toString().trim();
    }

    @Override
    public String getPwd() {
        return mEtPwd.getText().toString().trim();
    }

    @Override
    public void finishAc() {
        finish();
    }

    @Override
    public void show(String str) {
        Toast.makeText(RegisterActivity.this, str, Toast.LENGTH_SHORT).show();
    }
}
