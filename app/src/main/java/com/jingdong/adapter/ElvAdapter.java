package com.jingdong.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jingdong.R;
import com.jingdong.bean.GoodsCardBean;
import com.jingdong.bean.PriceAndCount;
import com.jingdong.presenter.DeleteCardPresenter;
import com.jingdong.view.IView.IDelCard;
import com.jingdong.view.frafment.GouwuFragment;

import java.util.List;
/**
 * 时间:2017/12/16 12:29
 * 作者:韩帅帅
 * 详情:
 */
public class ElvAdapter extends BaseExpandableListAdapter implements IDelCard {
    private Context context;
    private List<GoodsCardBean.DataBean> group;
    private List<List<GoodsCardBean.DataBean.ListBean>> child;
    private final LayoutInflater inflater;
    private final DeleteCardPresenter deleteCartPresenter;
    private int gPosition;
    private int cPosition;

    public ElvAdapter(Context context, List<GoodsCardBean.DataBean> group, List<List<GoodsCardBean.DataBean.ListBean>> child) {
        this.context = context;
        this.group = group;
        this.child = child;
        inflater = LayoutInflater.from(context);
        deleteCartPresenter = new DeleteCardPresenter(this);
    }

    @Override
    public int getGroupCount() {
        return group.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return child.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return group.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return child.get(groupPosition).get(childPosition);
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
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View view;
        final GroupViewHolder holder;
        if (convertView == null) {
            view = inflater.inflate(R.layout.elv_group, null);
            holder = new GroupViewHolder();
            holder.tv = view.findViewById(R.id.tvGroup);
            holder.cbGroup = view.findViewById(R.id.cbGroup);
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (GroupViewHolder) view.getTag();
        }
        final GoodsCardBean.DataBean dataBean = group.get(groupPosition);
        holder.tv.setText(dataBean.getSellerName());
        holder.cbGroup.setChecked(dataBean.isChecked());


        holder.cbGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataBean.setChecked(holder.cbGroup.isChecked());
                //改变子列表中的所有checkbox的状态
                setChildsChecked(groupPosition, holder.cbGroup.isChecked());
                //改变全选状态
                GouwuFragment.setAllSelect(isGroupCbChecked());
                //计算钱和数量
                setPriceAndCount();
                //刷新列表
                notifyDataSetChanged();
            }
        });
        return view;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View view;
        final ChildViewHolder holder;
        if (convertView == null) {
            view = inflater.inflate(R.layout.elv_child, null);
            holder = new ChildViewHolder();
            holder.iv = view.findViewById(R.id.iv);
            holder.tvTitle = view.findViewById(R.id.tvTitle);
            holder.tvSubhead = view.findViewById(R.id.tvSubhead);
            holder.tvPrice = view.findViewById(R.id.tvPrice);
            holder.cbChild = view.findViewById(R.id.cbChild);
            holder.btDel = view.findViewById(R.id.btDel);
            holder.tvNum = view.findViewById(R.id.tvNum);
            holder.ivDel = view.findViewById(R.id.ivDel);
            holder.ivAdd = view.findViewById(R.id.ivAdd);
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (ChildViewHolder) view.getTag();
        }
        final GoodsCardBean.DataBean.ListBean listBean = child.get(groupPosition).get(childPosition);
        String images = listBean.getImages();
        Glide.with(context).load(images.split("\\|")[0]).into(holder.iv);
        holder.tvTitle.setText(listBean.getTitle());
        holder.cbChild.setChecked(child.get(groupPosition).get(childPosition).isChecked());
        holder.tvSubhead.setText(listBean.getSubhead());
        holder.tvPrice.setText(listBean.getPrice() + "元");
        holder.tvNum.setText(listBean.getCount() + "");

        holder.btDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //记录一下点击的groupPosition和childPosition
                gPosition = groupPosition;
                cPosition = childPosition;
                SharedPreferences sp = context.getSharedPreferences("user", Context.MODE_PRIVATE);
                String uid = sp.getString("uid", "");
                String pid = sp.getString("pid", "");
                deleteCartPresenter.deleteCart(context,uid, pid);
            }
        });

        //给减号设置点击事件
        holder.ivDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int c = listBean.getCount();
                if (c <= 1) {
                    c = 1;
                    Toast.makeText(context,"宝贝不能被减少了呢!!!",Toast.LENGTH_SHORT).show();
                } else {
                    c--;
                }
                setNum(listBean, holder, c);
            }
        });
        //给加号设置点击事件
        holder.ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int c = listBean.getCount();
                c++;
                setNum(listBean, holder, c);
            }
        });

        //给子列表的checkbox设置点击事件
        holder.cbChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击checkbox的时候，给对应的属性值设置true或者false
                listBean.setChecked(holder.cbChild.isChecked());
                group.get(groupPosition).setChecked(isChildsCbChecked(groupPosition));
                //如果某个一级列表下的二级列表全部选中时，则要判断其它的一级列表是否都选中，去改变“全选”状态
                GouwuFragment.setAllSelect(isGroupCbChecked());
                setPriceAndCount();
                //刷新页面
                notifyDataSetChanged();
            }
        });
        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    /**
     * 删除
     */
    @Override
    public void Onsuccess() {
        Toast.makeText(context, "删除成功", Toast.LENGTH_SHORT).show();
        List<GoodsCardBean.DataBean.ListBean> listBeans = child.get(gPosition);
        if (listBeans != null && listBeans.size() > 0) {
            listBeans.remove(cPosition);
        }

        if (listBeans != null && listBeans.size() == 0) {
            child.remove(gPosition);
            group.remove(gPosition);
        }
        //计算数量和钱
        setPriceAndCount();
        //如果某个一级列表下的二级列表全部选中时，则要判断其它的一级列表是否都选中，去改变“全选”状态
        GouwuFragment.setAllSelect(isGroupCbChecked());
        //刷新列表
        notifyDataSetChanged();

    }

    class GroupViewHolder {
        TextView tv;
        CheckBox cbGroup;
    }

    class ChildViewHolder {
        ImageView iv;
        TextView tvTitle;
        TextView tvSubhead;
        TextView tvPrice;
        CheckBox cbChild;
        Button btDel;
        TextView tvNum;
        ImageView ivDel;
        ImageView ivAdd;
    }

    /**
     * 设置数量
     * @param listBean
     * @param holder
     * @param c
     */
    private void setNum(GoodsCardBean.DataBean.ListBean listBean, ChildViewHolder holder, int c) {
        //改变bean里的那个值
        listBean.setCount(c);
        holder.tvNum.setText(c + "");
        //计算钱和数量，并显示
        setPriceAndCount();
        //刷新页面
        notifyDataSetChanged();
    }

    /**
     * 改变子列表中的所有checkbox的状态
     *
     * @param groupPosition
     * @param bool
     */
    private void setChildsChecked(int groupPosition, boolean bool) {
        List<GoodsCardBean.DataBean.ListBean> listBeans = child.get(groupPosition);
        for (int i = 0; i < listBeans.size(); i++) {
            GoodsCardBean.DataBean.ListBean listBean = listBeans.get(i);
            listBean.setChecked(bool);
        }
    }

    /**
     * 判断一级列表是否全部选中
     *
     * @return
     */
    private boolean isGroupCbChecked() {
        if (group.size() == 0) {
            return false;
        }
        for (int i = 0; i < group.size(); i++) {
            GoodsCardBean.DataBean dataBean = group.get(i);
            if (!dataBean.isChecked()) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断同一商家下的子列表checkbox是否都选中
     *
     * @param groupPosition
     * @return
     */
    private boolean isChildsCbChecked(int groupPosition) {
        List<GoodsCardBean.DataBean.ListBean> listBeans = child.get(groupPosition);
        for (int i = 0; i < listBeans.size(); i++) {
            GoodsCardBean.DataBean.ListBean listBean = listBeans.get(i);
            if (!listBean.isChecked()) {
                return false;
            }
        }
        return true;
    }

    /**
     * 设置钱和数量
     */
    private void setPriceAndCount() {
        PriceAndCount priceAndCount = computePrice();
        GouwuFragment.setMoney(priceAndCount.getPrice());
        GouwuFragment.setCount(priceAndCount.getCount());
    }

    /**
     * 计算钱和数量
     */
    private PriceAndCount computePrice() {
        double sum = 0;
        int count = 0;
        for (int i = 0; i < child.size(); i++) {
            List<GoodsCardBean.DataBean.ListBean> listBeans = child.get(i);
            for (int j = 0; j < listBeans.size(); j++) {
                GoodsCardBean.DataBean.ListBean listBean = listBeans.get(j);
                //表示是否选中
                if (listBean.isChecked()) {
                    count += listBean.getCount();
                    sum += listBean.getPrice() * listBean.getCount();
                }

            }
        }
        return new PriceAndCount(sum, count);
    }


    /**
     * 改变全选状态
     *
     * @param bool
     */
    public void setAllChecked(boolean bool) {
        for (int i = 0; i < group.size(); i++) {
            GoodsCardBean.DataBean dataBean = group.get(i);
            dataBean.setChecked(bool);
            setChildsChecked(i, bool);
        }
        setPriceAndCount();
        //刷新页面
        notifyDataSetChanged();
    }
}