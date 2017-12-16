package com.jingdong.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.jingdong.R;
import com.jingdong.bean.ProductCatagoryBean;
import com.jingdong.view.InfoDetailsActivity;

import java.util.List;

/**
 * 时间:2017/12/3 21:07
 * 作者:韩帅帅
 * 详情:
 */

public class RightAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<String> groupList;//一级列表数据
    private List<List<ProductCatagoryBean.DataBean.ListBean>> childList;//二级列表数据
    private LayoutInflater inflater;

    public RightAdapter(Context context, List<String> groupList, List<List<ProductCatagoryBean.DataBean.ListBean>> childList) {
        this.context = context;
        this.groupList = groupList;
        this.childList = childList;
        inflater = LayoutInflater.from(context);
    }



    @Override
    public int getGroupCount() {
        return groupList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childList.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder holder;
        View view;
        if (convertView == null) {
            holder = new GroupViewHolder();
            view = inflater.inflate(R.layout.elv_item1, null);
            holder.tv = view.findViewById(R.id.tv);
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (GroupViewHolder) view.getTag();
        }
        String str = groupList.get(groupPosition);
        holder.tv.setText(str);
        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder holder;
        View view;
        if (convertView == null) {
            holder = new ChildViewHolder();
            view = inflater.inflate(R.layout.elv_item2, null);
            holder.rv = view.findViewById(R.id.rv);
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (ChildViewHolder) view.getTag();
        }
        List<ProductCatagoryBean.DataBean.ListBean> listBeen = childList.get(groupPosition);
        //1.给RecyclerView设置布局管理器
        holder.rv.setLayoutManager(new GridLayoutManager(context, 3));//这个布局管理器，是类似gridcview的效果
        //2.设置适配器
        ElvRvAdapter elvRvAdapter = new ElvRvAdapter(context, listBeen);
        holder.rv.setAdapter(elvRvAdapter);
        elvRvAdapter.setOnItemClickListener(new ElvRvAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ProductCatagoryBean.DataBean.ListBean listBean) {
                //就是跳转
                Intent intent = new Intent(context, InfoDetailsActivity.class);
                intent.putExtra("pscid", listBean.getPscid()+"");
                context.startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    class GroupViewHolder {
        TextView tv;
    }

    class ChildViewHolder {
        RecyclerView rv;
    }
}
