package com.fundrive.navaidlclient.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fundrive.navaidlclient.R;
import com.fundrive.navaidlclient.bean.RouteInfo;

import java.util.List;

/**
 * Created by fduser on 2019/5/20.
 * 算路结果列表适配器
 */

public class RouteCalculationResultAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private List<RouteInfo> list;

    public RouteCalculationResultAdapter(Context context, List<RouteInfo> list){
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyHolder holder;
        if (convertView == null){
            holder = new MyHolder();
            convertView = inflater.inflate(R.layout.route_calculation_result_item,null);
            holder.tv_description = convertView.findViewById(R.id.tv_description);
            holder.tv_routeTime = convertView.findViewById(R.id.tv_routeTime);
            holder.tv_routeLength = convertView.findViewById(R.id.tv_routeLength);
            holder.tv_trafficlightCount = convertView.findViewById(R.id.tv_trafficlightCount);
            holder.tv_tollChargr = convertView.findViewById(R.id.tv_tollChargr);
            convertView.setTag(holder);
        } else {
            holder = (MyHolder) convertView.getTag();
        }

        RouteInfo routeInfo = list.get(position);
        holder.tv_description.setText(routeInfo.getRouteNumber()+"."+routeInfo.getDescription());
        holder.tv_routeTime.setText(timeTransformation(routeInfo.getRouteTime()));
        holder.tv_routeLength.setText(lengthTransformation(routeInfo.getRouteLength()));
        holder.tv_trafficlightCount.setText("红绿灯"+routeInfo.getTrafficlightCount()+"个");
        holder.tv_tollChargr.setText("收费"+routeInfo.getTollChargr()+"元");

        return convertView;
    }

    private String timeTransformation(int second){
        String hourAndMinute = "";
        int hour = second/60/60;
        int minute = second/60%60;
        if (hour > 0){
            hourAndMinute += hour+"小时";
            if (minute > 0){
                hourAndMinute += minute+"分钟";
            }
        }else {
            if (minute > 0){
                hourAndMinute += second/6/10.0+"分钟";
            }
        }
        return hourAndMinute;
    }

    private String lengthTransformation(int m){
        String result = "";
        int km = m/1000;

        if (km > 0){
            if (km > 10){
                result += km+"公里";
            } else {
                result += m/100/10.0+"公里";
            }
        }else {
            result = m+"米";
        }
        return result;
    }

    class MyHolder{
        TextView tv_description,tv_routeTime,tv_routeLength,tv_trafficlightCount,tv_tollChargr;
    }
}
