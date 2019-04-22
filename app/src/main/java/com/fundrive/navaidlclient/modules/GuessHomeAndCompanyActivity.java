package com.fundrive.navaidlclient.modules;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.fundrive.navaidlclient.Constant;
import com.fundrive.navaidlclient.R;
import com.fundrive.navaidlclient.Resource;
import com.fundrive.navaidlclient.bean.PageInfoBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GuessHomeAndCompanyActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_tips)
    TextView tv_tips;

    @BindView(R.id.et_home_1_start_time)
    EditText et_home_1_start_time;
    @BindView(R.id.et_home_1_radius)
    EditText et_home_1_radius;
    @BindView(R.id.et_home_1_lon)
    EditText et_home_1_lon;
    @BindView(R.id.et_home_1_lat)
    EditText et_home_1_lat;

    @BindView(R.id.et_home_2_start_time)
    EditText et_home_2_start_time;
    @BindView(R.id.et_home_2_radius)
    EditText et_home_2_radius;
    @BindView(R.id.et_home_2_lon)
    EditText et_home_2_lon;
    @BindView(R.id.et_home_2_lat)
    EditText et_home_2_lat;

    @BindView(R.id.et_company_1_start_time)
    EditText et_company_1_start_time;
    @BindView(R.id.et_company_1_radius)
    EditText et_company_1_radius;
    @BindView(R.id.et_company_1_lon)
    EditText et_company_1_lon;
    @BindView(R.id.et_company_1_lat)
    EditText et_company_1_lat;

    @BindView(R.id.et_company_2_start_time)
    EditText et_company_2_start_time;
    @BindView(R.id.et_company_2_radius)
    EditText et_company_2_radius;
    @BindView(R.id.et_company_2_lon)
    EditText et_company_2_lon;
    @BindView(R.id.et_company_2_lat)
    EditText et_company_2_lat;

    private String message;

    private PageInfoBean.Lists protocolData;
    private int lists_index;
    private JSONObject obj_sendJson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess_home_and_company);
        ButterKnife.bind(this);

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
        et_home_1_lon.setInputType(InputType.TYPE_CLASS_NUMBER);
        et_home_1_lon.setKeyListener(DigitsKeyListener.getInstance("0123456789."));
        et_home_1_lat.setInputType(InputType.TYPE_CLASS_NUMBER);
        et_home_1_lat.setKeyListener(DigitsKeyListener.getInstance("0123456789."));
        et_home_2_lon.setInputType(InputType.TYPE_CLASS_NUMBER);
        et_home_2_lon.setKeyListener(DigitsKeyListener.getInstance("0123456789."));
        et_home_2_lat.setInputType(InputType.TYPE_CLASS_NUMBER);
        et_home_2_lat.setKeyListener(DigitsKeyListener.getInstance("0123456789."));
        et_company_1_lon.setInputType(InputType.TYPE_CLASS_NUMBER);
        et_company_1_lon.setKeyListener(DigitsKeyListener.getInstance("0123456789."));
        et_company_1_lat.setInputType(InputType.TYPE_CLASS_NUMBER);
        et_company_1_lat.setKeyListener(DigitsKeyListener.getInstance("0123456789."));
        et_company_2_lon.setInputType(InputType.TYPE_CLASS_NUMBER);
        et_company_2_lon.setKeyListener(DigitsKeyListener.getInstance("0123456789."));
        et_company_2_lat.setInputType(InputType.TYPE_CLASS_NUMBER);
        et_company_2_lat.setKeyListener(DigitsKeyListener.getInstance("0123456789."));

        if (protocolData.getSendJson()!=null && !protocolData.getSendJson().isEmpty()){
            try {
                obj_sendJson = new JSONObject(protocolData.getSendJson());

                JSONArray arr_homeList = obj_sendJson.getJSONArray("homeList");
                if (arr_homeList.length() == 2) {
                    JSONObject obj_home_1 = arr_homeList.getJSONObject(0);
                    et_home_1_start_time.setText(obj_home_1.getJSONArray("startTime").get(0).toString());
                    et_home_1_radius.setText(String.valueOf(obj_home_1.getInt("radius")));
                    String lonAndLat_1 = obj_home_1.getString("lonLat");
                    et_home_1_lon.setText(lonAndLat_1.substring(0,lonAndLat_1.indexOf(",")));
                    et_home_1_lat.setText(lonAndLat_1.substring(lonAndLat_1.indexOf(",")+1,lonAndLat_1.length()));

                    JSONObject obj_home_2 = arr_homeList.getJSONObject(1);
                    et_home_2_start_time.setText(obj_home_2.getJSONArray("startTime").get(0).toString());
                    et_home_2_radius.setText(String.valueOf(obj_home_2.getInt("radius")));
                    String lonAndLat_2 = obj_home_2.getString("lonLat");
                    et_home_2_lon.setText(lonAndLat_2.substring(0,lonAndLat_2.indexOf(",")));
                    et_home_2_lat.setText(lonAndLat_2.substring(lonAndLat_2.indexOf(",")+1,lonAndLat_2.length()));
                }

                JSONArray arr_companyList = obj_sendJson.getJSONArray("companyList");
                if (arr_companyList.length() == 2) {
                    JSONObject obj_company_1 = arr_companyList.getJSONObject(0);
                    et_company_1_start_time.setText(obj_company_1.getJSONArray("startTime").get(0).toString());
                    et_company_1_radius.setText(String.valueOf(obj_company_1.getInt("radius")));
                    String lonAndLat_1 = obj_company_1.getString("lonLat");
                    et_company_1_lon.setText(lonAndLat_1.substring(0,lonAndLat_1.indexOf(",")));
                    et_company_1_lat.setText(lonAndLat_1.substring(lonAndLat_1.indexOf(",")+1,lonAndLat_1.length()));

                    JSONObject obj_company_2 = arr_companyList.getJSONObject(1);
                    et_company_2_start_time.setText(obj_company_2.getJSONArray("startTime").get(0).toString());
                    et_company_2_radius.setText(String.valueOf(obj_company_2.getInt("radius")));
                    String lonAndLat_2 = obj_company_2.getString("lonLat");
                    et_company_2_lon.setText(lonAndLat_2.substring(0,lonAndLat_2.indexOf(",")));
                    et_company_2_lat.setText(lonAndLat_2.substring(lonAndLat_2.indexOf(",")+1,lonAndLat_2.length()));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        et_home_1_start_time.setSelection(et_home_1_start_time.getText().length());
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            if (obj_sendJson != null) {
                obj_sendJson.put("timestemp",System.currentTimeMillis());

                JSONArray arr_homeList = obj_sendJson.getJSONArray("homeList");
                if (arr_homeList.length() == 2){
                    JSONObject obj_home_1 = arr_homeList.getJSONObject(0);
                    obj_home_1.put("lonLat",et_home_1_lon.getText()+","+et_home_1_lat.getText());
                    obj_home_1.put("radius",Integer.parseInt(et_home_1_radius.getText().toString()));
                    obj_home_1.put("startTime",new JSONArray().put(et_home_1_start_time.getText().toString()));
                    arr_homeList.put(0,obj_home_1);

                    JSONObject obj_home_2 = arr_homeList.getJSONObject(1);
                    obj_home_2.put("lonLat",et_home_2_lon.getText()+","+et_home_2_lat.getText());
                    obj_home_2.put("radius",Integer.parseInt(et_home_2_radius.getText().toString()));
                    obj_home_2.put("startTime",new JSONArray().put(et_home_2_start_time.getText().toString()));
                    arr_homeList.put(1,obj_home_2);
                }
                obj_sendJson.put("homeList",arr_homeList);

                JSONArray arr_companyList = obj_sendJson.getJSONArray("companyList");
                if (arr_companyList.length() == 2){
                    JSONObject obj_company_1 = arr_companyList.getJSONObject(0);
                    obj_company_1.put("lonLat",et_company_1_lon.getText()+","+et_company_1_lat.getText());
                    obj_company_1.put("radius",Integer.parseInt(et_company_1_radius.getText().toString()));
                    obj_company_1.put("startTime",new JSONArray().put(et_company_1_start_time.getText()));
                    arr_companyList.put(0,obj_company_1);

                    JSONObject obj_company_2 = arr_companyList.getJSONObject(1);
                    obj_company_2.put("lonLat",et_company_2_lon.getText()+","+et_company_2_lat.getText());
                    obj_company_2.put("radius",Integer.parseInt(et_company_2_radius.getText().toString()));
                    obj_company_2.put("startTime",new JSONArray().put(et_company_2_start_time.getText()));
                    arr_companyList.put(1,obj_company_2);
                }
                obj_sendJson.put("companyList",arr_companyList);

                protocolData.setSendJson(obj_sendJson.toString());
            } else {
                JSONObject obj_sendJson_m = new JSONObject();
                JSONArray arr_home_list = new JSONArray();
                JSONObject obj_home_1 = new JSONObject();
                JSONObject obj_home_2 = new JSONObject();
                JSONArray arr_company_list = new JSONArray();
                JSONObject obj_company_1 = new JSONObject();
                JSONObject obj_company_2 = new JSONObject();
                JSONArray arr_home_1_start_time = new JSONArray();
                JSONArray arr_home_2_start_time = new JSONArray();
                JSONArray arr_company_1_start_time = new JSONArray();
                JSONArray arr_company_2_start_time = new JSONArray();

                obj_sendJson_m.put("timestemp", System.currentTimeMillis());

                arr_home_1_start_time.put(et_home_1_start_time.getText().toString());
                obj_home_1.put("startTime", arr_home_1_start_time);
                obj_home_1.put("radius", Integer.parseInt(et_home_1_radius.getText().toString()));
                obj_home_1.put("lonLat", et_home_1_lon.getText().toString() + "," + et_home_1_lat.getText());
                arr_home_list.put(obj_home_1);
                arr_home_2_start_time.put(et_home_2_start_time.getText().toString());
                obj_home_2.put("startTime", arr_home_2_start_time);
                obj_home_2.put("radius", Integer.parseInt(et_home_2_radius.getText().toString()));
                obj_home_2.put("lonLat", et_home_2_lon.getText().toString() + "," + et_home_2_lat.getText());
                arr_home_list.put(obj_home_2);
                obj_sendJson_m.put("homeList", arr_home_list);

                arr_home_1_start_time.put(et_company_1_start_time.getText().toString());
                obj_company_1.put("startTime", arr_company_1_start_time);
                obj_company_1.put("radius", Integer.parseInt(et_company_1_radius.getText().toString()));
                obj_company_1.put("lonLat", et_company_1_lon.getText().toString() + "," + et_company_1_lat.getText());
                arr_company_list.put(obj_company_1);
                arr_home_2_start_time.put(et_company_2_start_time.getText().toString());
                obj_company_2.put("startTime", arr_company_2_start_time);
                obj_company_2.put("radius", Integer.parseInt(et_company_2_radius.getText().toString()));
                obj_company_2.put("lonLat", et_company_2_lon.getText().toString() + "," + et_company_2_lat.getText());
                arr_company_list.put(obj_company_2);
                obj_sendJson_m.put("companyList", arr_company_list);

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
                showSendDialog(message);
                break;
            case R.id.btn_return:
                finish();
                break;
        }
    }

    private void makeJson() {
        JSONObject cmdJson = new JSONObject();
        JSONObject jsonObject = new JSONObject();
        JSONArray arr_home_list = new JSONArray();
        JSONObject obj_home_1 = new JSONObject();
        JSONObject obj_home_2 = new JSONObject();
        JSONArray arr_company_list = new JSONArray();
        JSONObject obj_company_1 = new JSONObject();
        JSONObject obj_company_2 = new JSONObject();
        JSONArray arr_home_1_start_time = new JSONArray();
        JSONArray arr_home_2_start_time = new JSONArray();
        JSONArray arr_company_1_start_time = new JSONArray();
        JSONArray arr_company_2_start_time = new JSONArray();

        try {
            jsonObject.put("timestemp",System.currentTimeMillis());

            arr_home_1_start_time.put(et_home_1_start_time.getText().toString());
            obj_home_1.put("startTime",arr_home_1_start_time);
            obj_home_1.put("radius",Integer.parseInt(et_home_1_radius.getText().toString().equals("")?"10":et_home_1_radius.getText().toString()));
            obj_home_1.put("lonLat",et_home_1_lon.getText().toString()+","+et_home_1_lat.getText());
            arr_home_list.put(obj_home_1);
            arr_home_2_start_time.put(et_home_2_start_time.getText().toString());
            obj_home_2.put("startTime",arr_home_2_start_time);
            obj_home_2.put("radius",Integer.parseInt(et_home_2_radius.getText().toString().equals("")?"10":et_home_2_radius.getText().toString()));
            obj_home_2.put("lonLat",et_home_2_lon.getText().toString()+","+et_home_2_lat.getText());
            arr_home_list.put(obj_home_2);
            jsonObject.put("homeList",arr_home_list);

            arr_home_1_start_time.put(et_company_1_start_time.getText().toString());
            obj_company_1.put("startTime",arr_company_1_start_time);
            obj_company_1.put("radius",Integer.parseInt(et_company_1_radius.getText().toString().equals("")?"10":et_company_1_radius.getText().toString()));
            obj_company_1.put("lonLat",et_company_1_lon.getText().toString()+","+et_company_1_lat.getText());
            arr_company_list.put(obj_company_1);
            arr_home_2_start_time.put(et_company_2_start_time.getText().toString());
            obj_company_2.put("startTime",arr_company_2_start_time);
            obj_company_2.put("radius",Integer.parseInt(et_company_2_radius.getText().toString().equals("")?"10":et_company_2_radius.getText().toString()));
            obj_company_2.put("lonLat",et_company_2_lon.getText().toString()+","+et_company_2_lat.getText());
            arr_company_list.put(obj_company_2);
            jsonObject.put("companyList",arr_company_list);

            cmdJson.put(Constant.CMD_KEY, Integer.parseInt("201B",16));
            cmdJson.put(Constant.JSON_KEY, jsonObject);

            message = cmdJson.toString();
            sendMessage(message);
            Log.d(TAG, "makeJson: " + message);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
