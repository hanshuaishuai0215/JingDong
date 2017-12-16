package com.jingdong.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jingdong.R;
import com.jingdong.bean.ProductCatagoryBean;

import java.util.List;

/**
 * 时间:2017/12/3 21:06
 * 作者:韩帅帅
 * 详情:
 */

public class ElvRvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<ProductCatagoryBean.DataBean.ListBean> listBeen;
    //2、定义一个属性
    private OnItemClickListener onItemClickListener;

    //1、接口回调第一步，先定义一个接口
    public interface OnItemClickListener {
        public void onItemClick(ProductCatagoryBean.DataBean.ListBean listBean);
    }

    //3、定义一个方法
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public ElvRvAdapter(Context context, List<ProductCatagoryBean.DataBean.ListBean> listBeen) {
        this.context = context;
        this.listBeen = listBeen;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.elv_rv_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ProductCatagoryBean.DataBean.ListBean listBean = listBeen.get(position);
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        myViewHolder.tv.setText(listBean.getName());
        myViewHolder.iv.setImageURI(listBean.getIcon());
        myViewHolder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(listBean);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listBeen.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private SimpleDraweeView iv;
        private TextView tv;
        private LinearLayout ll;

        public MyViewHolder(View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.iv);
            tv = itemView.findViewById(R.id.tv);
            ll = itemView.findViewById(R.id.ll);
        }
    }

}
