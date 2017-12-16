package com.jingdong.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;
/**
 * 时间:2017/12/16 11:47
 * 作者:韩帅帅
 * 详情:
 */
public class VpDetailsAdapter extends FragmentPagerAdapter {
    private List<Fragment> list;

    public VpDetailsAdapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
        this.list = list;
    }


    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }
}