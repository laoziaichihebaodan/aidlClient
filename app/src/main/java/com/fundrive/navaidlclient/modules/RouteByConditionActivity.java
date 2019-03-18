package com.fundrive.navaidlclient.modules;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import com.fundrive.navaidlclient.Constant;
import com.fundrive.navaidlclient.R;
import com.fundrive.navaidlclient.position.PointActivity;
import com.fundrive.navaidlclient.position.Points;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RouteByConditionActivity extends BaseActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_by_condition);
        ButterKnife.bind(this);
        tvTitle.setText("条件算路");
    }

    @OnClick({R.id.set_start, R.id.btn_end_pos, R.id.set_way_pos1, R.id.set_way_pos2, R.id.set_way_pos3, R.id.btn_commit, R.id.btn_return})
    public void onViewClicked(View view) {

        Intent intent = new Intent(this, PointActivity.class);
        switch (view.getId()) {
            case R.id.set_start:
                if (setStart.isChecked()){
                    intent.putExtra("json", 0);
                    startActivity(intent);
                }

                break;
            case R.id.btn_end_pos:
                intent.putExtra("json", 1);
                startActivity(intent);
                break;
            case R.id.set_way_pos1:
                if (setWayPos1.isChecked()) {
                    intent.putExtra("json", 2);
                    startActivity(intent);
                }
                break;
            case R.id.set_way_pos2:
                if (setWayPos2.isChecked()) {
                    intent.putExtra("json", 3);
                    startActivity(intent);
                }
                break;
            case R.id.set_way_pos3:
                if (setWayPos3.isChecked()) {
                    intent.putExtra("json", 4);
                    startActivity(intent);
                }
                break;
            case R.id.btn_commit:
                deleteRoute = deleteMode.isChecked();
                startNavi = isStart.isChecked();
                makeJson();
                break;
            case R.id.btn_return:
                finish();
                break;
        }
    }

    //组装json
    private void makeJson() {
        JSONObject cmdJson = new JSONObject();
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("startNavi",startNavi);
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

            String message = cmdJson.toString();
            sendMessage(message);
            Log.d(TAG, "makeJson: " + message);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
