package com.jessechiu.android.ontepad;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

// 适配层
public class CostListAdapter extends BaseAdapter {
    private List<CostBean> mList;
    private Context mcontext;
    private LayoutInflater mLayoutInflater;

    // 构造函数
    public CostListAdapter(Context context, List<CostBean> mList){
        this.mcontext = context;
        this.mList = mList;
        // 存储布局数据
        this.mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return this.mList.size();
    }

    @Override
    public Object getItem(int i) {
        return this.mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        ViewHolder holder = null;
        if(view == null){
            // 把 list_item 样式应用上
            view = mLayoutInflater.inflate(R.layout.list_item,null);
            holder = new ViewHolder();
            holder.mTvConstTitle = (TextView) view.findViewById(R.id.tv_title);
            holder.mTvConstDate = (TextView) view.findViewById(R.id.tv_date);
            holder.mTvConstMoney = (TextView) view.findViewById(R.id.tv_costMoney);
            view.setTag(holder);
        }else{
            // 如果已经有缓存了，直接取出来
            holder = (ViewHolder) view.getTag();
        }
        CostBean bean = mList.get(i);
        holder.mTvConstTitle.setText(bean.costTitle);
        holder.mTvConstDate.setText(bean.costDate);
        holder.mTvConstMoney.setText(bean.costMoney);
        return view;
    }

    private static class ViewHolder {
        TextView mTvConstTitle;
        TextView mTvConstDate;
        TextView mTvConstMoney;
    }
}
