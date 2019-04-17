package com.fundrive.navaidlclient.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fundrive.navaidlclient.R;
import com.fundrive.navaidlclient.bean.PageInfoBean;

import java.util.ArrayList;
import java.util.List;

public class SearchPopupWindowsAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private List<PageInfoBean.MutilSelectValue> itemList = new ArrayList<>();

    public SearchPopupWindowsAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    /**
     * 设置为新的数据，旧数据会被清空
     *
     * @param itemList
     */
    public void setItems(List<PageInfoBean.MutilSelectValue> itemList) {
        this.itemList.clear();
        this.itemList = itemList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Object getItem(int i) {
        return itemList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public List<PageInfoBean.MutilSelectValue> getMutilSelectValues(){
        return itemList;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.item_list_selector, viewGroup, false);
        if (view != null) {
            LinearLayout linearLayout = view.findViewById(R.id.relativeLayout_search);
            TextView textView = view.findViewById(R.id.textView_listView_selector);
            textView.setText(itemList.get(i).getName());
            final ImageView imageView = view.findViewById(R.id.image_search_check);
            imageView.setImageResource(itemList.get(i).isSelect() ? R.mipmap.icon_selected : R.mipmap.icon_unselected);
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemList.get(i).setSelect(!itemList.get(i).isSelect());
                    imageView.setImageResource(itemList.get(i).isSelect() ? R.mipmap.icon_selected : R.mipmap.icon_unselected);
                }
            });
        }
        return view;
    }

}
