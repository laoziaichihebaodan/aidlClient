package com.fundrive.navaidlclient.modules;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import com.fundrive.navaidlclient.Constant;
import com.fundrive.navaidlclient.R;
import com.fundrive.navaidlclient.Resource;
import com.fundrive.navaidlclient.bean.Observer;
import com.fundrive.navaidlclient.bean.PageInfoBean;
import org.json.JSONException;
import org.json.JSONObject;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 组合指令：猜测家和公司并导航
 */
public class Combined_GuessHomeAndNavi extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_tips)
    TextView tv_tips;

    @BindView(R.id.sp_iaFavType)
    Spinner sp_iaFavType;

    @BindView(R.id.et_lon)
    EditText et_lon;
    @BindView(R.id.et_lat)
    EditText et_lat;

    @BindView(R.id.et_start_time_hour)
    EditText et_start_time_hour;
    @BindView(R.id.et_start_time_minute)
    EditText et_start_time_minute;

    @BindView(R.id.et_end_time_hour)
    EditText et_end_time_hour;
    @BindView(R.id.et_end_time_minute)
    EditText et_end_time_minute;

    @BindView(R.id.sp_isFestival)
    Spinner sp_isFestival;

    private String message;
    private PageInfoBean.Lists protocolData;
    private int lists_index;
    private JSONObject obj_sendJson;
    private int iaFavType;
    private boolean isFestival;
    private Observer response = new NaviResponse();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combined__guess_home_and_navi);
        ButterKnife.bind(this);
        Resource.registerObserver(response);
        Intent intent = getIntent();
        protocolData = (PageInfoBean.Lists) intent.getSerializableExtra("PageInfoBean");
        lists_index = intent.getIntExtra("lists_index",0);

        if (protocolData.getTips().isEmpty()){
            tv_tips.setVisibility(View.GONE);
        }else {
            tv_tips.setVisibility(View.VISIBLE);
            tv_tips.setText(protocolData.getTips());
        }

        tvTitle.setText("猜测家和公司");

        if (protocolData.getSendJson()!=null && !protocolData.getSendJson().isEmpty()){
            try {
                obj_sendJson = new JSONObject(protocolData.getSendJson());

                iaFavType = obj_sendJson.getInt("iaFavType");
                sp_iaFavType.setSelection(iaFavType);

                JSONObject obj_lonAndLat = obj_sendJson.getJSONObject("iaPoiPos");
                et_lon.setText(String.valueOf(obj_lonAndLat.getInt("longitude")));
                et_lat.setText(String.valueOf(obj_lonAndLat.getInt("latitude")));
                et_lon.setSelection(et_lon.getText().length());

                JSONObject obj_startTime = obj_sendJson.getJSONObject("startTime");
                et_start_time_hour.setText(String.valueOf(obj_startTime.getInt("hour")));
                et_start_time_minute.setText(String.valueOf(obj_startTime.getInt("minute")));

                JSONObject obj_endTime = obj_sendJson.getJSONObject("endTime");
                et_end_time_hour.setText(String.valueOf(obj_endTime.getInt("hour")));
                et_end_time_minute.setText(String.valueOf(obj_endTime.getInt("minute")));

                isFestival = obj_sendJson.getBoolean("isFestival");
                sp_isFestival.setSelection(isFestival?1:0);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        sp_iaFavType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                iaFavType = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        sp_isFestival.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                isFestival = position!= 0;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            if (obj_sendJson != null) {
                obj_sendJson.put("iaFavType",iaFavType);

                JSONObject obj_iaPoiPos = obj_sendJson.getJSONObject("iaPoiPos");
                obj_iaPoiPos.put("longitude",Integer.parseInt(et_lon.getText().toString()));
                obj_iaPoiPos.put("latitude",Integer.parseInt(et_lat.getText().toString()));

                JSONObject obj_startTime = obj_sendJson.getJSONObject("startTime");
                obj_startTime.put("hour",Integer.parseInt(et_start_time_hour.getText().toString()));
                obj_startTime.put("minute",Integer.parseInt(et_start_time_minute.getText().toString()));

                JSONObject obj_endTime = obj_sendJson.getJSONObject("endTime");
                obj_endTime.put("hour",Integer.parseInt(et_end_time_hour.getText().toString()));
                obj_endTime.put("minute",Integer.parseInt(et_end_time_minute.getText().toString()));

                obj_sendJson.put("isFestival",isFestival);

                protocolData.setSendJson(obj_sendJson.toString());
            } else {
                JSONObject obj_sendJson_m = new JSONObject();

                obj_sendJson_m.put("iaFavType",iaFavType);

                JSONObject obj_iaPoiPos = new JSONObject();
                obj_iaPoiPos.put("longitude",et_lon.getText().toString().isEmpty()?0:Integer.parseInt(et_lon.getText().toString()));
                obj_iaPoiPos.put("latitude",et_lat.getText().toString().isEmpty()?0:Integer.parseInt(et_lat.getText().toString()));
                obj_sendJson_m.put("iaPoiPos",obj_iaPoiPos);

                JSONObject obj_startTime = new JSONObject();
                obj_startTime.put("hour",et_start_time_hour.getText().toString().isEmpty()?0:Integer.parseInt(et_start_time_hour.getText().toString()));
                obj_startTime.put("minute",et_start_time_minute.getText().toString().isEmpty()?0:Integer.parseInt(et_start_time_minute.getText().toString()));
                obj_sendJson_m.put("startTime",obj_startTime);

                JSONObject obj_endTime = new JSONObject();
                obj_endTime.put("hour",et_end_time_hour.getText().toString().isEmpty()?0:Integer.parseInt(et_end_time_hour.getText().toString()));
                obj_endTime.put("minute",et_end_time_minute.getText().toString().isEmpty()?0:Integer.parseInt(et_end_time_minute.getText().toString()));
                obj_sendJson_m.put("endTime",obj_endTime);

                obj_sendJson_m.put("isFestival",isFestival);

                protocolData.setSendJson(obj_sendJson_m.toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Resource.pageInfoBean.getLists().remove(lists_index);
        Resource.pageInfoBean.getLists().add(lists_index,protocolData);
    }

    @OnClick({R.id.btn_commit, R.id.btn_return})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_commit:
                makeJson();
//                showSendDialog(message);
                break;
            case R.id.btn_return:
                finish();
                break;
        }
    }

    private void makeJson() {
        JSONObject cmdJson = new JSONObject();
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("iaFavType",iaFavType);

            JSONObject obj_iaPoiPos = new JSONObject();
            obj_iaPoiPos.put("longitude",et_lon.getText().toString().isEmpty()?0:Integer.parseInt(et_lon.getText().toString()));
            obj_iaPoiPos.put("latitude",et_lat.getText().toString().isEmpty()?0:Integer.parseInt(et_lat.getText().toString()));
            jsonObject.put("iaPoiPos",obj_iaPoiPos);

            JSONObject obj_startTime = new JSONObject();
            obj_startTime.put("hour",et_start_time_hour.getText().toString().isEmpty()?0:Integer.parseInt(et_start_time_hour.getText().toString()));
            obj_startTime.put("minute",et_start_time_minute.getText().toString().isEmpty()?0:Integer.parseInt(et_start_time_minute.getText().toString()));
            jsonObject.put("startTime",obj_startTime);

            JSONObject obj_endTime = new JSONObject();
            obj_endTime.put("hour",et_end_time_hour.getText().toString().isEmpty()?0:Integer.parseInt(et_end_time_hour.getText().toString()));
            obj_endTime.put("minute",et_end_time_minute.getText().toString().isEmpty()?0:Integer.parseInt(et_end_time_minute.getText().toString()));
            jsonObject.put("endTime",obj_endTime);

            jsonObject.put("isFestival",isFestival);

            cmdJson.put(Constant.CMD_KEY, Integer.parseInt("201B",16));
            cmdJson.put(Constant.JSON_KEY, jsonObject);

            message = cmdJson.toString();
            sendMessage(message);
            Log.d(TAG, "makeJson: " + message);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Resource.removeObserver(response);
    }

    public void showNaviDialog(){
        String message = "";
        if (iaFavType == 1){
            message = "要导航到家吗？";
        }else if (iaFavType == 2){
            message = "要导航到公司吗？";
        }
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        try {
                            JSONObject cmdJson = new JSONObject();
                            JSONObject object1 = new JSONObject();
                            object1.put("enable",true);
                            cmdJson.put(Constant.CMD_KEY, Integer.parseInt("201D",16));
                            cmdJson.put(Constant.JSON_KEY, object1);
                            String message = cmdJson.toString();
                            sendMessage(message);
                            showSendDialog(message);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        try {
                            JSONObject cmdJson = new JSONObject();
                            JSONObject object1 = new JSONObject();
                            object1.put("enable",false);
                            cmdJson.put(Constant.CMD_KEY, Integer.parseInt("201D",16));
                            cmdJson.put(Constant.JSON_KEY, object1);
                            String message = cmdJson.toString();
                            sendMessage(message);
                            showSendDialog(message);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                })
                .create()
                .show();

    }

    class NaviResponse implements Observer {

        @Override
        public void update(int cmd, String message) {
            if ("d00c".equals(Integer.toHexString(cmd))){
                try {
                    JSONObject object = new JSONObject(message);
                    if (object.getInt("status") == 1) {
                        showNaviDialog();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
