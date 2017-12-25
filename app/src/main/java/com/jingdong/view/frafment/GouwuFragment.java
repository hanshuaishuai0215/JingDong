package com.jingdong.view.frafment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.jingdong.R;
import com.jingdong.view.GoodsCardActivity;

/**
 * 时间:2017/12/3 21:08
 * 作者:韩帅帅
 * 详情:
 */
public class GouwuFragment extends Fragment implements View.OnClickListener {

    private View view;
    /**
     * 点我加载购物车
     */
    private Button mBtn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.gouwu_fragment, null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mBtn = (Button) view.findViewById(R.id.btn);
        mBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn:
                Intent intent = new Intent(getActivity(), GoodsCardActivity.class);
                getActivity().startActivity(intent);
                break;
        }
    }
}
