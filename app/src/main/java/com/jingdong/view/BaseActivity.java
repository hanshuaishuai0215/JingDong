package com.jingdong.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.jingdong.utils.ActivityStroreUtil;
/**
 * 时间:2017/12/3 21:19
 * 作者:韩帅帅
 * 详情:
 */
public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStroreUtil.addActivity(this);
    }

}
