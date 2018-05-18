package com.fundrive.navaidlclient.modules;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.fundrive.navaidlclient.Constant;
import com.fundrive.navaidlclient.R;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchPoiByConditionActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.sp_search_type)
    Spinner spSearchType;
    @BindView(R.id.sp_center_type)
    Spinner spCenterType;
    @BindView(R.id.sp_center_exp)
    Spinner spCenterExp;
    @BindView(R.id.et_center_name)
    EditText etCenterName;
    @BindView(R.id.et_long)
    EditText etLong;
    @BindView(R.id.et_lat)
    EditText etLat;
    @BindView(R.id.sp_scope_type)
    Spinner spScopeType;
    @BindView(R.id.sp_scope_exp)
    Spinner spScopeExp;
    @BindView(R.id.et_scope)
    EditText etScope;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.et_radius)
    EditText etRadius;
    @BindView(R.id.et_data_size)
    EditText etDataSize;
    @BindView(R.id.btn_commit)
    Button btnCommit;
    private int searchType;
    private int centerType;
    private int scopeType;
    private int centerExp;
    private int scopeExp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_poi_by_condition);
        ButterKnife.bind(this);
        btnCommit.setOnClickListener(this);
        spSearchType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                searchType = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spCenterExp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                centerExp = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spCenterType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                centerType = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spScopeType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                scopeType = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spScopeExp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                scopeExp = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void makeJson() {
        if (searchType == 0) {
            Toast.makeText(this, "请选择搜索类型", Toast.LENGTH_SHORT).show();
            return;
        }
        if (centerType == 0) {
            Toast.makeText(this, "请选择搜索中心类型", Toast.LENGTH_SHORT).show();
            return;
        }
        if (scopeType == 0) {
            Toast.makeText(this, "请选择搜索范围类型", Toast.LENGTH_SHORT).show();
            return;
        }
        if (centerExp == 0) {
            Toast.makeText(this, "请选择搜索中心表达形式", Toast.LENGTH_SHORT).show();
            return;
        }
        if (scopeExp == 0) {
            Toast.makeText(this, "请选择搜索范围表达形式", Toast.LENGTH_SHORT).show();
            return;
        }

        JSONObject cmdJson = new JSONObject();
        JSONObject jsonObject = new JSONObject();
        JSONObject searchCenterJson = new JSONObject();
        JSONObject scopeJson = new JSONObject();

        try {

            searchCenterJson.put("centerType", centerType);
            searchCenterJson.put("centerExpression", centerExp);
            if(1==centerExp){//字符串形式
                searchCenterJson.put("centerPoint", etCenterName.getText().toString().trim());
            }else {//经纬度
                JSONObject pointJson = new JSONObject();
                pointJson.put("longitude", Long.decode(etLong.getText().toString().trim()));
                pointJson.put("latitude", Long.decode(etLat.getText().toString().trim()));
                searchCenterJson.put("centerPoint", pointJson);
            }

            scopeJson.put("scopeType", scopeType);
            scopeJson.put("scopeExpression", scopeExp);
            if (1 == scopeExp) {//字符串
                scopeJson.put("serchScope", etScope.getText().toString().trim());
            }else {

                String code = etScope.getText().toString().trim();
                if(!TextUtils.isDigitsOnly(code)){
                    Toast.makeText(this, "请输入搜索范围的行政区号", Toast.LENGTH_SHORT).show();
                    return;
                }
                scopeJson.put("serchScope", Integer.decode(code));
            }
            jsonObject.put("poiSearchScope", scopeJson);
            jsonObject.put("poiSearchCenter", searchCenterJson);
            jsonObject.put("poiSearchContents", etContent.getText().toString().trim());
            jsonObject.put("searchRadius", Integer.decode(etRadius.getText().toString().trim()));
            Integer size = Integer.decode(etDataSize.getText().toString().trim());
            jsonObject.put("poiDataPageSize", size < 1 || size > 15 ? 5 : size);
            jsonObject.put("poiSearchType", searchType);


            cmdJson.put(Constant.CMD_KEY,Constant.IA_CMD_SEARCH_POI_BY_CONDITION);
            cmdJson.put(Constant.JSON_KEY, jsonObject);

            String message = cmdJson.toString();
            sendMessage(message);
            Log.d(TAG, "makeJson: "+message);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        makeJson();
    }
}
