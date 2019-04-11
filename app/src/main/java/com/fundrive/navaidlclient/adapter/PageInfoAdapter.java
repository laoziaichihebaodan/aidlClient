package com.fundrive.navaidlclient.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.fundrive.navaidlclient.R;
import com.fundrive.navaidlclient.bean.PageInfoBean;

import java.util.ArrayList;
import java.util.List;

public class PageInfoAdapter extends BaseAdapter implements Filterable {
    private Context context;
    private List<PageInfoBean.Lists> data;
    private List<PageInfoBean.Lists> backData;
    private MyFilter mFilter;

    public PageInfoAdapter(Context context, List<PageInfoBean.Lists> data) {
        this.context = context;
        this.data = data;
        this.backData = data;
    }

    public void setData(List<PageInfoBean.Lists> data) {
        this.backData = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View item;

        if (view == null) {
            item = View.inflate(context, R.layout.item_pageinfo_list, null);
        } else {
            item = view;
        }
        TextView tv = item.findViewById(R.id.item_pageinfo_title);
        tv.setText(data.get(i).getName());
        if (data.get(i).getType().trim().equals("title")) {
            tv.setBackgroundResource(R.color.bg_title);
            tv.setTextColor(context.getResources().getColor(R.color.colorAccent));
        } else {
            tv.setBackgroundResource(R.color.transparent);
            tv.setTextColor(context.getResources().getColor(R.color.text_main_body));
        }
        return item;
    }

    //当ListView调用setTextFilter()方法的时候，便会调用该方法
    @Override
    public Filter getFilter() {
        if (mFilter == null) {
            mFilter = new MyFilter();
        }
        return mFilter;
    }

    //我们需要定义一个过滤器的类来定义过滤规则
    class MyFilter extends Filter {
        //我们在performFiltering(CharSequence charSequence)这个方法中定义过滤规则
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            FilterResults result = new FilterResults();
            List<PageInfoBean.Lists> list;
            if (TextUtils.isEmpty(charSequence)) {//当过滤的关键字为空的时候，我们则显示所有的数据
                list = backData;
            } else {//否则把符合条件的数据对象添加到集合中
                list = new ArrayList<>();
                for (PageInfoBean.Lists bean : backData) {
                    if (bean.getName().contains(charSequence) && !bean.getType().trim().equals("title")) {
                        list.add(bean);
                    }

                }
            }
            result.values = list; //将得到的集合保存到FilterResults的value变量中
            result.count = list.size();//将集合的大小保存到FilterResults的count变量中

            return result;
        }

        //在publishResults方法中告诉适配器更新界面
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            data = (List<PageInfoBean.Lists>) filterResults.values;
            if (filterResults.count > 0) {
                notifyDataSetChanged();//通知数据发生了改变
            } else {
                notifyDataSetInvalidated();//通知数据失效
            }
        }
    }

}
