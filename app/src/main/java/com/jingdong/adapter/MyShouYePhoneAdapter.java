package com.jingdong.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jingdong.R;
import com.jingdong.bean.ShouYeBean;

import java.util.List;

/**
 * 时间:2017/12/7 20:57
 * 作者:韩帅帅
 * 详情:
 */

public class MyShouYePhoneAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context context;
    private List<ShouYeBean.MiaoshaBean.ListBeanX> list;

    public MyShouYePhoneAdapter(Context context, List<ShouYeBean.MiaoshaBean.ListBeanX> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.zhuyerecycleview2_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        ShouYeBean.MiaoshaBean.ListBeanX beanX = list.get(position);
        String[] split = beanX.getImages().split("\\|");
        Glide.with(context).load(split[0]).into(myViewHolder.iv);
        myViewHolder.price_new.setText(beanX.getPrice()+"");
        myViewHolder.price_lod.setText(beanX.getSalenum()+"");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder{
        private ImageView iv;
        private TextView price_new;
        private TextView price_lod;
        public MyViewHolder(View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.zhuyerecycleview2_iv);
            price_new = itemView.findViewById(R.id.zhuyerecycleview2_tv_price_new);
            price_lod = itemView.findViewById(R.id.zhuyerecycleview2_tv_price_lod);
            price_lod.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        }
    }
}
