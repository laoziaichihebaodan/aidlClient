package com.fundrive.navaidlclient.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.fundrive.navaidlclient.R;

import java.util.List;

public class AnimationListAdapter extends BaseAdapter {
    private List<Integer> animations;

    public AnimationListAdapter(List<Integer> animations) {
        this.animations = animations;
    }

    public void setAnimations(List<Integer> animations){
        this.animations = animations;
    }

    @Override
    public int getCount() {
        return animations.size();
    }

    @Override
    public Object getItem(int position) {
        return animations.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_animate,null);
            holder = new ViewHolder();
            holder.tvName = convertView.findViewById(R.id.tv_num);
            holder.spinner = convertView.findViewById(R.id.sp_animation);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvName.setText(position + 1 + "");
        Integer integer = animations.get(position);
        holder.spinner.setSelection(integer);

        holder.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int poi, long id) {
                animations.set(position, poi);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return convertView;
    }

    class ViewHolder {
        TextView tvName;
        Spinner spinner;
    }
}
