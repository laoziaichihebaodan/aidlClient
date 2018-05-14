package com.fundrive.navaidlclient.modules;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import com.fundrive.navaidlclient.Constant;
import com.fundrive.navaidlclient.DialogUtils;
import com.fundrive.navaidlclient.R;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RouteByConditionActivity extends BaseActivity {

    @BindView(R.id.delete_mode)
    Switch deleteMode;
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
    private boolean deleteRoute = false;

    private String startPoint = "{\n" +
            "    \"iaPoiType\": 351,\n" +
            "    \"iaPoiPos\": {\n" +
            "      \"longitude\": 12151236,\n" +
            "      \"latitude\": 3129925\n" +
            "    },\n" +
            "    \"iaPoiDisPos\": {\n" +
            "      \"longitude\": 12151236,\n" +
            "      \"latitude\": 3129925\n" +
            "    },\n" +
            "    \"iaPoiId\": 4294967295,\n" +
            "    \"iaChildPoiNum\": 0,\n" +
            "    \"iaCompoundId\": 0,\n" +
            "    \"iaPoiName\": \"五角场\",\n" +
            "    \"iaPoiAdress\": \"上海市杨浦区55路下行(世界路新江湾城-南浦大桥)\",\n" +
            "    \"iaPoiPhone\": \"\",\n" +
            "    \"iaRegionName\": \"上海市杨浦区\",\n" +
            "    \"iaPoiTypeName\": \"公交车站\"\n" +
            "  }";

    private String endPoint = " {\n" +
            "    \"iaPoiType\": 351,\n" +
            "    \"iaPoiPos\": {\n" +
            "      \"longitude\": 11632902,\n" +
            "      \"latitude\": 3990550\n" +
            "    },\n" +
            "    \"iaPoiDisPos\": {\n" +
            "      \"longitude\": 11632902,\n" +
            "      \"latitude\": 3990550\n" +
            "    },\n" +
            "    \"iaPoiId\": 4294967295,\n" +
            "    \"iaChildPoiNum\": 0,\n" +
            "    \"iaCompoundId\": 0,\n" +
            "    \"iaPoiName\": \"会城门\",\n" +
            "    \"iaPoiAdress\": \"北京市海淀区65路下行(北京西站-动物园枢纽站)\",\n" +
            "    \"iaPoiPhone\": \"\",\n" +
            "    \"iaRegionName\": \"北京市海淀区\",\n" +
            "    \"iaPoiTypeName\": \"公交车站\"\n" +
            "  }";

    private String wayPoint1 = "{\n" +
            "    \"iaPoiType\": 351,\n" +
            "    \"iaPoiPos\": {\n" +
            "      \"longitude\": 11879601,\n" +
            "      \"latitude\": 3208640\n" +
            "    },\n" +
            "    \"iaPoiDisPos\": {\n" +
            "      \"longitude\": 11879601,\n" +
            "      \"latitude\": 3208640\n" +
            "    },\n" +
            "    \"iaPoiId\": 4294967295,\n" +
            "    \"iaChildPoiNum\": 0,\n" +
            "    \"iaCompoundId\": 0,\n" +
            "    \"iaPoiName\": \"南京站\",\n" +
            "    \"iaPoiAdress\": \"江苏省南京市玄武区地铁1号线下行(中国药科大学站-迈皋桥站)\",\n" +
            "    \"iaPoiPhone\": \"\",\n" +
            "    \"iaRegionName\": \"江苏省南京市\",\n" +
            "    \"iaPoiTypeName\": \"公交车站\"\n" +
            "  }";

    private String wayPoint2 = "{\n" +
            "    \"iaPoiType\": 207,\n" +
            "    \"iaPoiPos\": {\n" +
            "      \"longitude\": 11448715,\n" +
            "      \"latitude\": 3800845\n" +
            "    },\n" +
            "    \"iaPoiDisPos\": {\n" +
            "      \"longitude\": 11448615,\n" +
            "      \"latitude\": 3801072\n" +
            "    },\n" +
            "    \"iaPoiId\": 4294967295,\n" +
            "    \"iaChildPoiNum\": 0,\n" +
            "    \"iaCompoundId\": 0,\n" +
            "    \"iaPoiName\": \"石家庄火车站(石家庄站)\",\n" +
            "    \"iaPoiAdress\": \"河北省石家庄市桥西区新石南路与京广西街交汇附近\",\n" +
            "    \"iaPoiPhone\": \"\",\n" +
            "    \"iaRegionName\": \"河北省石家庄市\",\n" +
            "    \"iaPoiTypeName\": \"火车站\"\n" +
            "  }";
    private String wayPoint3 = "{\n" +
            "    \"iaPoiType\": 124,\n" +
            "    \"iaPoiPos\": {\n" +
            "      \"longitude\": 11718431,\n" +
            "      \"latitude\": 3908446\n" +
            "    },\n" +
            "    \"iaPoiDisPos\": {\n" +
            "      \"longitude\": 11718428,\n" +
            "      \"latitude\": 3908463\n" +
            "    },\n" +
            "    \"iaPoiId\": 4294967295,\n" +
            "    \"iaChildPoiNum\": 0,\n" +
            "    \"iaCompoundId\": 0,\n" +
            "    \"iaPoiName\": \"天津京剧院\",\n" +
            "    \"iaPoiAdress\": \"天津市河西区环湖中路6号\",\n" +
            "    \"iaPoiPhone\": \"\",\n" +
            "    \"iaRegionName\": \"天津市河西区\",\n" +
            "    \"iaPoiTypeName\": \"剧场、戏院、音乐厅\"\n" +
            "  }";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_by_condition);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.set_start, R.id.btn_end_pos, R.id.set_way_pos1, R.id.set_way_pos2, R.id.set_way_pos3, R.id.btn_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.set_start:
                if(setStart.isChecked())
                DialogUtils.initDialog(this, startPoint, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startPoint =((EditText)v).getText().toString().trim();
                    }
                });
                break;
            case R.id.btn_end_pos:
                DialogUtils.initDialog(this, endPoint, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        endPoint =((EditText)v).getText().toString().trim();
                    }
                });
                break;
            case R.id.set_way_pos1:
                if (setWayPos1.isChecked()) {
                    DialogUtils.initDialog(this, wayPoint1, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            wayPoint1 = ((EditText)v).getText().toString().trim();
                        }
                    });
                }
                break;
            case R.id.set_way_pos2:
                if (setWayPos2.isChecked()) {
                    DialogUtils.initDialog(this, wayPoint2, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            wayPoint2 = ((EditText)v).getText().toString().trim();
                        }
                    });
                }
                break;
            case R.id.set_way_pos3:
                if (setWayPos3.isChecked()) {
                    DialogUtils.initDialog(this, wayPoint3, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            wayPoint3 = ((EditText)v).getText().toString().trim();
                        }
                    });
                }
                break;
            case R.id.btn_commit:
                deleteRoute = deleteMode.isChecked();
                makeJson();
                break;
        }
    }

    //组装json
    private void makeJson() {
        JSONObject cmdJson = new JSONObject();
        JSONObject jsonObject = new JSONObject();

        try {
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
