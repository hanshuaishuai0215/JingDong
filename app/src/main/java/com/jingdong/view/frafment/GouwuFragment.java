package com.jingdong.view.frafment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jingdong.R;
/**
 * 时间:2017/12/3 21:08
 * 作者:韩帅帅
 * 详情:
 */
public class GouwuFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.gouwu_fragment,null);
        return view;
    }
}
