package com.fundrive.navaidlclient.modules;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.fundrive.navaidlclient.Constant;
import com.fundrive.navaidlclient.R;
import com.fundrive.navaidlclient.Resource;
import com.fundrive.navaidlclient.adapter.RouteCalculationResultAdapter;
import com.fundrive.navaidlclient.bean.Observer;
import com.fundrive.navaidlclient.bean.PageInfoBean;
import com.fundrive.navaidlclient.bean.RouteInfo;
import com.fundrive.navaidlclient.position.PointActivity;
import com.fundrive.navaidlclient.position.Points;
import com.fundrive.navaidlclient.view.MultiSelectPopupWindows;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CalculationAndNaviActivity extends BaseActivity implements Observer{

    @BindView(R.id.scrollView)
    ScrollView scrollView;

    @BindView(R.id.ll_calculation)
    LinearLayout ll_calculation;
    @BindView(R.id.ll_calculation_result)
    LinearLayout ll_calculation_result;
    @BindView(R.id.ll_navigating)
    LinearLayout ll_navigating;

    @BindView(R.id.tv_calculation_result_title)
    TextView tv_calculation_result_title;
    @BindView(R.id.lv_calculation_result)
    ListView lv_calculation_result;

    @BindView(R.id.tv_tbt)
    TextView tv_tbt;
    @BindView(R.id.tv_remainingTimeAndDistance)
    TextView tv_remainingTimeAndDistance;

    @BindView(R.id.sp_guideType)
    Spinner sp_guideType;
    @BindView(R.id.sp_isFamiliarRoad)
    Spinner sp_isFamiliarRoad;

    @BindView(R.id.tv_select_preference)
    TextView tv_select_preference;
    @BindView(R.id.delete_mode)
    Switch deleteMode;
    @BindView(R.id.is_start)
    Switch isStart;
    @BindView(R.id.set_start)
    Switch setStart;
    @BindView(R.id.btn_end_pos)
    Button btnEndPos;
    @BindView(R.id.set_way_pos1)
    Switch setWayPos1;
    @BindView(R.id.set_way_pos2)
    Switch setWayPos2;
    @BindView(R.id.set_way_pos3)
    Switch setWayPos3;
    @BindView(R.id.btn_commit)
    Button btnCommit;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    private boolean deleteRoute = false;
    private boolean startNavi = false;

    private String message;

    public static JSONObject startPoint = Points.pointJson(351,
            12151236, 3129925,
            12151236, 3129925,
            4294967295l,
            0,
            0,
            "五角场",
            "上海市杨浦区55路下行(世界路新江湾城-南浦大桥)", "", "上海市杨浦区",
            "公交车站");
    public static JSONObject endPoint = Points.pointJson(351,
            11632902, 3990550,
            11632902, 3990550,
            4294967295L,
            0,
            0,
            "会城门",
            "北京市海淀区65路下行(北京西站-动物园枢纽站)",
            "",
            "北京市海淀区",
            "公交车站");

    public static JSONObject wayPoint1 = Points.pointJson(
            351,
            11879601, 3208640,
            11879601, 3208640,
            4294967295l,
            0,
            0,
            "南京站",
            "江苏省南京市玄武区地铁1号线下行(中国药科大学站-迈皋桥站)",
            "",
            "江苏省南京市",
            "公交车站");


    public static JSONObject wayPoint2 = Points.pointJson(
            207,
            11448715, 3800845,
            11448615, 3801072,
            4294967295L,
            0, 0, "石家庄火车站(石家庄站",
            "河北省石家庄市桥西区新石南路与京广西街交汇附近",
            "",
            "河北省石家庄市",
            "火车站");


    public static JSONObject wayPoint3 = Points.pointJson(
            351,
            11632902, 3990550,
            11632902, 3990550,
            4294967295l,
            0,
            0,
            "会城门",
            "北京市海淀区65路下行(北京西站-动物园枢纽站)",
            "",
            "北京市海淀区",
            "公交车站");

    private PageInfoBean.Lists protocolData;
    private int lists_index;
    private JSONObject obj_sendJson;

    //避让类型取值范围
    private int[] types = {1, 1<<1 , 1<<8, 1<<9, 1<<10, 1<<11,1<<12,1<<13,1<<14, 1<<24,1<<25, 1<<26};
    int page_value = 0;
    List<PageInfoBean.MutilSelectValue> list_mutilSelectValue;

    private String search_endPoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculation_and_navi);
        ButterKnife.bind(this);
        tvTitle.setText("组合:算路_导航");

        Intent intent = getIntent();

        search_endPoint = intent.getStringExtra("search_endPoint");
        try {
            if (search_endPoint!= null && !search_endPoint.isEmpty()){
                endPoint = new JSONObject(search_endPoint);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        protocolData = (PageInfoBean.Lists) intent.getSerializableExtra("PageInfoBean");
        lists_index = intent.getIntExtra("lists_index",0);
        if (protocolData != null && protocolData.getSendJson()!=null && !protocolData.getSendJson().isEmpty()){
            try {
                obj_sendJson = new JSONObject(protocolData.getSendJson());
                isStart.setChecked(obj_sendJson.getBoolean("startNavi"));
                deleteMode.setChecked(obj_sendJson.getBoolean("deleteCurRoute"));
                setStart.setChecked(obj_sendJson.optJSONObject("startPoint") != null);
                setWayPos1.setChecked(obj_sendJson.optJSONObject("routeWay1") != null);
                setWayPos2.setChecked(obj_sendJson.optJSONObject("routeWay2") != null);
                setWayPos3.setChecked(obj_sendJson.optJSONObject("routeWay3") != null);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        list_mutilSelectValue = new ArrayList<>();
        try {
            if (obj_sendJson == null) {
                list_mutilSelectValue.add(new PageInfoBean.MutilSelectValue("最短路线", "1", false));
                list_mutilSelectValue.add(new PageInfoBean.MutilSelectValue("高速优先", "2", false));
                list_mutilSelectValue.add(new PageInfoBean.MutilSelectValue("避让收费", "256", false));
                list_mutilSelectValue.add(new PageInfoBean.MutilSelectValue("避让轮渡", "512", false));
                list_mutilSelectValue.add(new PageInfoBean.MutilSelectValue("避让隧道(仅在v2版引擎中支持)", "1024", false));
                list_mutilSelectValue.add(new PageInfoBean.MutilSelectValue("避让高速", "2048", false));
                list_mutilSelectValue.add(new PageInfoBean.MutilSelectValue("避让城市快速路", "4096", false));
                list_mutilSelectValue.add(new PageInfoBean.MutilSelectValue("避让高架(仅在v2版引擎中支持)", "8192", false));
                list_mutilSelectValue.add(new PageInfoBean.MutilSelectValue("避让未铺设道路(仅在v2版引擎中支持)", "16384", false));
                list_mutilSelectValue.add(new PageInfoBean.MutilSelectValue("避让拥堵(仅在v2版引擎中支持)", "16777216", false));
                list_mutilSelectValue.add(new PageInfoBean.MutilSelectValue("避让因为交通事件阻断的道路(仅在v2版引擎中支持)", "33554432", false));
                list_mutilSelectValue.add(new PageInfoBean.MutilSelectValue("算路时是否考虑红绿灯代价,WARNING:仅供调试试用", "67108864", false));
            } else {
                int iaRoutePreference = obj_sendJson.getInt("iaRoutePreference");
                list_mutilSelectValue.add(new PageInfoBean.MutilSelectValue("最短路线", "1", (iaRoutePreference & 1) !=0));
                list_mutilSelectValue.add(new PageInfoBean.MutilSelectValue("高速优先", "2", (iaRoutePreference & 2) !=0));
                list_mutilSelectValue.add(new PageInfoBean.MutilSelectValue("避让收费", "256", (iaRoutePreference & 256) !=0));
                list_mutilSelectValue.add(new PageInfoBean.MutilSelectValue("避让轮渡", "512", (iaRoutePreference & 512) !=0));
                list_mutilSelectValue.add(new PageInfoBean.MutilSelectValue("避让隧道(仅在v2版引擎中支持)", "1024", (iaRoutePreference & 1024) !=0));
                list_mutilSelectValue.add(new PageInfoBean.MutilSelectValue("避让高速", "2048", (iaRoutePreference & 2048) !=0));
                list_mutilSelectValue.add(new PageInfoBean.MutilSelectValue("避让城市快速路", "4096", (iaRoutePreference & 4096) !=0));
                list_mutilSelectValue.add(new PageInfoBean.MutilSelectValue("避让高架(仅在v2版引擎中支持)", "8192", (iaRoutePreference & 8192) !=0));
                list_mutilSelectValue.add(new PageInfoBean.MutilSelectValue("避让未铺设道路(仅在v2版引擎中支持)", "16384", (iaRoutePreference & 16384) !=0));
                list_mutilSelectValue.add(new PageInfoBean.MutilSelectValue("避让拥堵(仅在v2版引擎中支持)", "16777216", (iaRoutePreference & 16777216) !=0));
                list_mutilSelectValue.add(new PageInfoBean.MutilSelectValue("避让因为交通事件阻断的道路(仅在v2版引擎中支持)", "33554432", (iaRoutePreference & 33554432) !=0));
                list_mutilSelectValue.add(new PageInfoBean.MutilSelectValue("算路时是否考虑红绿灯代价,WARNING:仅供调试试用", "67108864", (iaRoutePreference & 67108864) !=0));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        tv_select_preference.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final MultiSelectPopupWindows productsMultiSelectPopupWindows = new MultiSelectPopupWindows(CalculationAndNaviActivity.this, tv_select_preference, 100, list_mutilSelectValue);
                productsMultiSelectPopupWindows.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        List<PageInfoBean.MutilSelectValue> list_after_select = productsMultiSelectPopupWindows.getMutilSelectValues();
                        page_value = 0;
                        for (PageInfoBean.MutilSelectValue mutilSelectValue:list_after_select){
                            if (mutilSelectValue.isSelect()){
                                page_value |= Integer.parseInt(mutilSelectValue.getValue());
                            }
                        }
                        try {
                            if (obj_sendJson != null) {
                                obj_sendJson.put("iaRoutePreference", page_value);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });

        Resource.registerObserver(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        try {
            JSONObject obj_sendJson_m = new JSONObject();

            obj_sendJson_m.put("startNavi", isStart.isChecked());
            obj_sendJson_m.put("iaRoutePreference", page_value);
            obj_sendJson_m.put("deleteCurRoute", deleteMode.isChecked());

            if (setStart.isChecked()) {
                obj_sendJson_m.put("startPoint", startPoint);
            }

            obj_sendJson_m.put("endPoint", endPoint);

            if (setWayPos1.isChecked()) {
                obj_sendJson_m.put("routeWay1", wayPoint1);
            }
            if (setWayPos2.isChecked()) {
                obj_sendJson_m.put("routeWay2", wayPoint2);
            }
            if (setWayPos3.isChecked()) {
                obj_sendJson_m.put("routeWay3", wayPoint3);
            }

            if (protocolData != null){
                protocolData.setSendJson(obj_sendJson_m.toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Resource.pageInfoBean.getLists().remove(lists_index);
        Resource.pageInfoBean.getLists().add(lists_index, protocolData);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Resource.removeObserver(this);
    }

    @OnClick({R.id.set_start, R.id.btn_end_pos, R.id.set_way_pos1, R.id.set_way_pos2, R.id.set_way_pos3, R.id.btn_commit, R.id.btn_return,R.id.is_start,R.id.delete_mode})
    public void onViewClicked(View view) {

        Intent intent = new Intent(this, PointActivity.class);
        switch (view.getId()) {
            case R.id.is_start:
                if (obj_sendJson != null) {
                    try {
                        obj_sendJson.put("startNavi", isStart.isChecked());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.delete_mode:
                if (obj_sendJson != null) {
                    try {
                        obj_sendJson.put("deleteCurRoute", deleteMode.isChecked());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.set_start:
                if (setStart.isChecked()){
                    String str_startPoint = "";
                    if (obj_sendJson != null){
                        try {
                            if (!str_start_point_message.isEmpty()){
                                str_startPoint = str_start_point_message;
                            } else {
                                str_startPoint = obj_sendJson.getJSONObject("startPoint").toString();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    intent.putExtra("json",0);
                    intent.putExtra("point",str_startPoint);
                    startActivityForResult(intent,0);
                }

                break;
            case R.id.btn_end_pos:
                String str_endPoint = "";
                try {
                    if (!str_end_point_message.isEmpty()) {
                        str_endPoint = str_end_point_message;
                    } else if (search_endPoint != null) {
                        str_endPoint = search_endPoint;
                    } else {
                        if (obj_sendJson != null) {
                            str_endPoint = obj_sendJson.getJSONObject("endPoint").toString();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                intent.putExtra("json", 1);
                intent.putExtra("point", str_endPoint);
                startActivityForResult(intent, 1);
                break;
            case R.id.set_way_pos1:
                if (setWayPos1.isChecked()) {
                    String str_routeWay1 = "";
                    if (obj_sendJson != null){
                        try {
                            if (!str_route_way_1_point_message.isEmpty()){
                                str_routeWay1 = str_route_way_1_point_message;
                            } else {
                                str_routeWay1 = obj_sendJson.getJSONObject("routeWay1").toString();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    intent.putExtra("json", 2);
                    intent.putExtra("point",str_routeWay1);
                    startActivityForResult(intent,2);
                }
                break;
            case R.id.set_way_pos2:
                if (setWayPos2.isChecked()) {
                    String str_routeWay2 = "";
                    if (obj_sendJson != null){
                        try {
                            if (!str_route_way_2_point_message.isEmpty()){
                                str_routeWay2 = str_route_way_2_point_message;
                            } else {
                                str_routeWay2 = obj_sendJson.getJSONObject("routeWay2").toString();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    intent.putExtra("json", 3);
                    intent.putExtra("point",str_routeWay2);
                    startActivityForResult(intent,3);
                }
                break;
            case R.id.set_way_pos3:
                if (setWayPos3.isChecked()) {
                    String str_routeWay3 = "";
                    if (obj_sendJson != null){
                        try {
                            if (!str_route_way_3_point_message.isEmpty()){
                                str_routeWay3 = str_route_way_3_point_message;
                            } else {
                                str_routeWay3 = obj_sendJson.getJSONObject("routeWay3").toString();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    intent.putExtra("json", 4);
                    intent.putExtra("point",str_routeWay3);
                    startActivityForResult(intent,4);
                }
                break;
            case R.id.btn_commit:
                deleteRoute = deleteMode.isChecked();
                startNavi = isStart.isChecked();
                makeRouteCalculationJson();
                break;
            case R.id.btn_return:
                finish();
                break;
        }
    }

    String str_start_point_message = "";
    String str_end_point_message = "";
    String str_route_way_1_point_message = "";
    String str_route_way_2_point_message = "";
    String str_route_way_3_point_message = "";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 2) {
            String str_point_message = data.getStringExtra("message");
            if (!str_point_message.isEmpty()) {
                try {
                    switch (requestCode) {
                        case 0:
                            str_start_point_message = str_point_message;
                            startPoint = new JSONObject(str_start_point_message);
                            break;
                        case 1:
                            str_end_point_message = str_point_message;
                            endPoint = new JSONObject(str_end_point_message);
                            break;
                        case 2:
                            str_route_way_1_point_message = str_point_message;
                            wayPoint1 = new JSONObject(str_route_way_1_point_message);
                            break;
                        case 3:
                            str_route_way_2_point_message = str_point_message;
                            wayPoint2 = new JSONObject(str_route_way_2_point_message);
                            break;
                        case 4:
                            str_route_way_3_point_message = str_point_message;
                            wayPoint3 = new JSONObject(str_route_way_3_point_message);
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //组装json
    private void makeRouteCalculationJson() {
        JSONObject cmdJson = new JSONObject();
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("startNavi",startNavi);
            jsonObject.put("iaRoutePreference", page_value);
            jsonObject.put("deleteCurRoute", deleteRoute);

            if (setStart.isChecked()) {
                jsonObject.put("startPoint", startPoint);
            }

            jsonObject.put("endPoint", endPoint);

            if (setWayPos1.isChecked()) {
                jsonObject.put("routeWay1", wayPoint1);
            }
            if (setWayPos2.isChecked()) {
                jsonObject.put("routeWay2", wayPoint2);
            }
            if (setWayPos3.isChecked()) {
                jsonObject.put("routeWay3", wayPoint3);
            }

            cmdJson.put(Constant.CMD_KEY, Constant.IA_CMD_ROUTE_BY_CONDITION);
            cmdJson.put(Constant.JSON_KEY, jsonObject);

            message = cmdJson.toString();
            sendMessage(message);
            Log.d(TAG, "makeJson: " + message);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private List<RouteInfo> list = new ArrayList<>();
    private int navMode;//导航模式
    private boolean isFamiliarRoad;
    @Override
    public void update(int cmd, String message) {
        if (cmd == 0xC002){
            ll_calculation_result.setVisibility(View.VISIBLE);
            ll_navigating.setVisibility(View.GONE);

            scrollView.post(new Runnable() {
                public void run() {
                    scrollView.fullScroll(View.FOCUS_DOWN);
                }
            });

            RouteInfo routeInfo =null;
            try {
                JSONObject obj = new JSONObject(message);
                JSONArray arr_routeInfos = obj.optJSONArray("routeResult");
                if (!list.isEmpty()){
                    list.clear();
                }
                for (int i = 0; i < arr_routeInfos.length(); i++) {
                    JSONObject obj_routeInfo = (JSONObject) arr_routeInfos.get(i);
                    routeInfo = new RouteInfo();
                    routeInfo.setRouteNumber(obj_routeInfo.optInt("routeNumber"));
                    routeInfo.setDescription(obj_routeInfo.optString("routeDescription"));
                    routeInfo.setRouteTime(obj_routeInfo.optInt("routeTime"));
                    routeInfo.setRouteLength(obj_routeInfo.optInt("routeLength"));
                    routeInfo.setNormalWayLength(obj_routeInfo.optInt("nomalWayLen"));
                    routeInfo.setHighWayLength(obj_routeInfo.optInt("highWayLen"));
                    routeInfo.setTrafficlightCount(obj_routeInfo.optInt("trafficlightCount"));
                    routeInfo.setTollChargr(obj_routeInfo.optInt("tollCharge"));
                    routeInfo.setTollStationCount(obj_routeInfo.optInt("tollStationCount"));
                    list.add(routeInfo);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            RouteCalculationResultAdapter adapter = new RouteCalculationResultAdapter(CalculationAndNaviActivity.this,list);
            lv_calculation_result.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            setListViewHeightBasedOnChildren(lv_calculation_result);

            lv_calculation_result.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    makeNaviJson(list.get(position));
                }
            });
            sp_guideType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    navMode = position;
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
            sp_isFamiliarRoad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (0 == position) {
                        isFamiliarRoad = false;
                    } else {
                        isFamiliarRoad = true;
                    }
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        } else if (cmd == 0xA000){
            ll_calculation_result.setVisibility(View.GONE);
            ll_navigating.setVisibility(View.VISIBLE);

            try {
                JSONObject obj = new JSONObject(message);
                tv_tbt.setText(lengthTransformation(obj.optInt("turnDistanc"))+"进入"+obj.optString("nextRoadName"));
                tv_remainingTimeAndDistance.setText("剩余"+lengthTransformation(obj.optInt("toDestinationDistance"))+timeTransformation(obj.optInt("remainingTimeToDestination")));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (cmd == 0xB007){
            try{
                JSONObject obj = new JSONObject(message);
                if (obj.optInt("guidenceStatus") == 1){
                    ll_calculation_result.setVisibility(View.GONE);
                    ll_navigating.setVisibility(View.VISIBLE);
                }else if (obj.optInt("guidenceStatus") == 2){
                    ll_calculation_result.setVisibility(View.GONE);
                    ll_navigating.setVisibility(View.GONE);
                }
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
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

    private void makeNaviJson(RouteInfo routeInfo){
        try {
            JSONObject cmdJson = new JSONObject();
            JSONObject jsonObject = new JSONObject();

            jsonObject.put("routeNumber",routeInfo.getRouteNumber());
            jsonObject.put("guideType",navMode);
            jsonObject.put("isFamiliarRoad",isFamiliarRoad);

            cmdJson.put(Constant.CMD_KEY, Constant.IA_CMD_START_GUIDING_WITH_ROUTE);
            cmdJson.put(Constant.JSON_KEY, jsonObject);
            message = cmdJson.toString();
            sendMessage(message);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
}
