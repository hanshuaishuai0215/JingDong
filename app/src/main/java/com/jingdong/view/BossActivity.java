package com.jingdong.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.jingdong.R;
import com.jingdong.view.frafment.FaxianFragment;
import com.jingdong.view.frafment.FenleiFragment;
import com.jingdong.view.frafment.GouwuFragment;
import com.jingdong.view.frafment.MineFragment;
import com.jingdong.view.frafment.ShouyeFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 时间:2017/12/3 21:18
 * 作者:韩帅帅
 * 详情:
 */
public class BossActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewPager mVp;
    private RadioButton mShouye;
    private RadioButton mFenlei;
    private RadioButton mFaxian;
    private RadioButton mGouwu;
    private RadioButton mMine;
    private List<Fragment> list_f;
    private boolean noScroll = true; //true 代表不能滑动 //false 代表能滑动
    private RadioGroup mRg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boss);
        int goCar = getIntent().getIntExtra("goCar", 0);
        initView();
        newFragment();
        goCar(goCar);
    }

    private void newFragment() {
        list_f = new ArrayList<>();
        FaxianFragment faxianFragment = new FaxianFragment();
        GouwuFragment gouwuFragment = new GouwuFragment();
        MineFragment mineFragment = new MineFragment();
        ShouyeFragment shouyeFragment = new ShouyeFragment();
        FenleiFragment fenleiFragment = new FenleiFragment();
        list_f.add(shouyeFragment);
        list_f.add(fenleiFragment);
        list_f.add(faxianFragment);
        list_f.add(gouwuFragment);
        list_f.add(mineFragment);
        mVp.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return list_f.get(position);
            }

            @Override
            public int getCount() {
                return list_f.size();
            }

        });
    }

    private void initView() {
        mVp = (ViewPager) findViewById(R.id.vp);
        mShouye = (RadioButton) findViewById(R.id.shouye);
        mFenlei = (RadioButton) findViewById(R.id.fenlei);
        mFaxian = (RadioButton) findViewById(R.id.faxian);
        mGouwu = (RadioButton) findViewById(R.id.gouwu);
        mMine = (RadioButton) findViewById(R.id.mine);
        mVp.setOnClickListener(this);
        mShouye.setOnClickListener(this);
        mFenlei.setOnClickListener(this);
        mFaxian.setOnClickListener(this);
        mGouwu.setOnClickListener(this);
        mMine.setOnClickListener(this);
        mRg = (RadioGroup) findViewById(R.id.rg);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.vp:
                break;
            case R.id.shouye:
                mVp.setCurrentItem(0);
                break;
            case R.id.fenlei:
                mVp.setCurrentItem(1);
                break;
            case R.id.faxian:
                mVp.setCurrentItem(2);
                break;
            case R.id.gouwu:
                mVp.setCurrentItem(3);
                break;
            case R.id.mine:
                mVp.setCurrentItem(4);
                break;
        }
    }

    private void goCar(final int page) {
        switch (page) {
            case 0:
                mVp.setCurrentItem(0);
                mRg.check(mShouye.getId());
                break;
            case 1:
                mVp.setCurrentItem(1);
                mRg.check(mFenlei.getId());
                break;
            case 2:
                mVp.setCurrentItem(2);
                mRg.check(mFaxian.getId());
                break;
            case 3:
                mVp.setCurrentItem(3);
                mRg.check(mGouwu.getId());
                break;
            case 4:
                mVp.setCurrentItem(4);
                mRg.check(mMine.getId());
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }
}
