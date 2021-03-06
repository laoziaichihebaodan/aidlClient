package com.fundrive.navaidlclient.modules;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Switch;
import android.widget.TextView;

import com.fundrive.navaidlclient.Constant;
import com.fundrive.navaidlclient.R;
import com.fundrive.navaidlclient.Resource;
import com.fundrive.navaidlclient.bean.PageInfoBean;
import com.fundrive.navaidlclient.position.PointActivity;
import com.fundrive.navaidlclient.position.Points;
import com.fundrive.navaidlclient.view.MultiSelectPopupWindows;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RouteByConditionActivity extends BaseActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_by_condition);
        ButterKnife.bind(this);
        tvTitle.setText("条件算路");

        Intent intent = getIntent();
        protocolData = (PageInfoBean.Lists) intent.getSerializableExtra("PageInfoBean");

        lists_index = intent.getIntExtra("lists_index",0);
        if (protocolData.getSendJson()!=null && !protocolData.getSendJson().isEmpty()){
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
                final MultiSelectPopupWindows productsMultiSelectPopupWindows = new MultiSelectPopupWindows(RouteByConditionActivity.this, tv_select_preference, 100, list_mutilSelectValue);
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

            protocolData.setSendJson(obj_sendJson_m.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Resource.pageInfoBean.getLists().remove(lists_index);
        Resource.pageInfoBean.getLists().add(lists_index, protocolData);
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
                if (obj_sendJson != null){
                    try {
                        if (!str_end_point_message.isEmpty()){
                            str_endPoint = str_end_point_message;
                        } else {
                            str_endPoint = obj_sendJson.getJSONObject("endPoint").toString();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                intent.putExtra("json", 1);
                intent.putExtra("point",str_endPoint);
                startActivityForResult(intent,1);
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
                makeJson();
                showSendDialog(message);
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
    private void makeJson() {
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
}
