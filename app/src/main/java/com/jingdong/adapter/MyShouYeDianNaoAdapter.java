package com.jingdong.adapter;

import android.content.Context;
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

public class MyShouYeDianNaoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context context;
    private List<ShouYeBean.TuijianBean.ListBean> list;

    public MyShouYeDianNaoAdapter(Context context, List<ShouYeBean.TuijianBean.ListBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.zhuyerecycleview3_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        ShouYeBean.TuijianBean.ListBean listBean = list.get(position);
        Glide.with(context).load(listBean.getImages()).into(myViewHolder.iv);
        myViewHolder.xiangqing.setText(listBean.getTitle());
        myViewHolder.price.setText(listBean.getPrice()+"");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder{
        private ImageView iv;
        private TextView xiangqing;
        private TextView price;
        public MyViewHolder(View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.zhuyerecycleview3_iv);
            xiangqing = itemView.findViewById(R.id.zhuyerecycleview3_tv_xiangqing);
            price = itemView.findViewById(R.id.zhuyerecycleview3_tv_price);
        }
    }
}
