package com.fundrive.navaidlclient.modules;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
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

public class SearchActivity extends BaseActivity {
    //标题
    @BindView(R.id.tv_title)
    TextView tvTitle;
    //搜索类型
    @BindView(R.id.sp_search_type)
    Spinner sp_search_type;
    //深度信息
    @BindView(R.id.sp_detial_info)
    Spinner sp_detial_info;
    //排序方式
    @BindView(R.id.sp_sort_type)
    Spinner sp_sort_type;
    //选择中心类型
    @BindView(R.id.sp_center_type)
    Spinner sp_center_type;
    //中心表现形式
    @BindView(R.id.sp_center_exp)
    Spinner sp_center_exp;
    //中心名称
    @BindView(R.id.et_search_center_exp)
    EditText et_search_center_exp;
    //中心经度
    @BindView(R.id.et_center_long)
    EditText et_center_long;
    //中心纬度
    @BindView(R.id.et_center_lat)
    EditText et_center_lat;
    //中心字符串类型layout
    @BindView(R.id.ll_search_string_type)
    LinearLayout ll_search_string_type;
    //中心经纬度layout
    @BindView(R.id.ll_search_location_type)
    LinearLayout ll_search_location_type;
    //搜索范围类型
    @BindView(R.id.sp_scope_type)
    Spinner sp_scope_type;
    //搜索范围表现形式
    @BindView(R.id.sp_scope_exp)
    Spinner sp_scope_exp;
    //搜索范围
    @BindView(R.id.et_search_center_scope)
    EditText et_search_center_scope;
    //搜索内容
    @BindView(R.id.et_search_content)
    EditText et_search_content;
    //搜索半径
    @BindView(R.id.et_search_radius)
    EditText et_search_radius;
    //每页显示数据条数
    @BindView(R.id.et_page_size)
    EditText et_page_size;

    private String message;
    private PageInfoBean.Lists protocolData;
    private int lists_index;
    private int searchType;
    private int detailInfo;
    private int sortType;
    private int centerType;
    private int centerExp;
    private int scopeType;
    private int scopeExp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);


        Intent intent = getIntent();
        protocolData = (PageInfoBean.Lists) intent.getSerializableExtra("PageInfoBean");
        lists_index = intent.getIntExtra("lists_index",0);
        tvTitle.setText(protocolData.getName());
        dataRecover();
        initListener();


    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            JSONObject sendJson = new JSONObject();
            sendJson.put("poiSearchType",searchType);
            sendJson.put("poiSortBy",sortType);
            sendJson.put("detail",detailInfo);
            sendJson.put("poiSearchContents",et_search_content.getText().toString().trim());
            sendJson.put("searchRadius",et_search_radius.getText().toString().trim());
            sendJson.put("poiDataPageSize",et_page_size.getText().toString().trim());

            JSONObject searchCenter = new JSONObject();
            searchCenter.put("centerType",centerType);
            searchCenter.put("centerExpression",centerExp);
            if (centerExp == 0) {
                searchCenter.put("centerPoint",et_search_center_exp.getText().toString().trim());
            }else if (centerExp == 1) {
                JSONObject centerPoint = new JSONObject();
                centerPoint.put("longitude",et_center_long.getText().toString().trim());
                centerPoint.put("latitude",et_center_lat.getText().toString().trim());
                searchCenter.put("centerPoint",centerPoint);
            }

            JSONObject searchScope = new JSONObject();
            searchScope.put("scopeType",scopeType);
            searchScope.put("scopeExpression",scopeExp);
            searchScope.put("searchScope",et_search_center_scope.getText().toString().trim());

            sendJson.put("poiSearchCenter",searchCenter);
            sendJson.put("poiSearchScope",searchScope);

            protocolData.setSendJson(sendJson.toString());
            Resource.pageInfoBean.getLists().remove(lists_index);
            Resource.pageInfoBean.getLists().add(lists_index,protocolData);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    //数据恢复
    private void dataRecover() {
        if (!TextUtils.isEmpty(protocolData.getSendJson())) {
            try {
                JSONObject object = new JSONObject(protocolData.getSendJson());
                JSONObject searchCenter = object.getJSONObject("poiSearchCenter");
                JSONObject searchScope = object.getJSONObject("poiSearchScope");

                searchType = object.getInt("poiSearchType");
                sp_search_type.setSelection(searchType);
                detailInfo = object.getInt("detail");
                sp_detial_info.setSelection(detailInfo);
                sortType = object.getInt("poiSortBy");
                sp_sort_type.setSelection(sortType);

                centerType = searchCenter.getInt("centerType");
                sp_center_type.setSelection(centerType);
                centerExp = searchCenter.getInt("centerExpression");
                sp_center_exp.setSelection(centerExp);

                if (centerExp == 0){
                    ll_search_string_type.setVisibility(View.VISIBLE);
                    ll_search_location_type.setVisibility(View.GONE);
                    et_search_center_exp.setText(searchCenter.getString("centerPoint"));
                }else if (centerExp == 1){
                    ll_search_string_type.setVisibility(View.GONE);
                    ll_search_location_type.setVisibility(View.VISIBLE);
                    JSONObject centerPoint = searchCenter.getJSONObject("centerPoint");
                    et_center_long.setText(centerPoint.getString("longitude"));
                    et_center_lat.setText(centerPoint.getString("latitude"));
                }

                scopeType = searchScope.getInt("scopeType");
                sp_scope_type.setSelection(scopeType);
                scopeExp = searchScope.getInt("scopeExpression");
                sp_scope_exp.setSelection(scopeExp);

                et_search_center_scope.setText(searchScope.getString("searchScope"));
                et_search_content.setText(object.getString("poiSearchContents"));
                et_search_radius.setText(object.getString("searchRadius"));
                et_page_size.setText(object.getString("poiDataPageSize"));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else{
            ll_search_string_type.setVisibility(View.VISIBLE);
            ll_search_location_type.setVisibility(View.GONE);
        }
    }
    //spinner监听
    private void initListener() {
        sp_search_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                searchType = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        sp_detial_info.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                detailInfo = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        sp_sort_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sortType = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        sp_center_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                centerType = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        sp_center_exp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                centerExp = position;
                if (centerExp == 0){
                    ll_search_string_type.setVisibility(View.VISIBLE);
                    ll_search_location_type.setVisibility(View.GONE);
                }else if (centerExp == 1) {
                    ll_search_string_type.setVisibility(View.GONE);
                    ll_search_location_type.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        sp_scope_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                scopeType = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        sp_scope_exp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                scopeExp = position;
                if (scopeExp == 0){
                    et_search_center_scope.setText("上海");
                    et_search_center_scope.setInputType(InputType.TYPE_CLASS_TEXT);
                }else if (scopeExp == 1) {
                    et_search_center_scope.setText("310000");
                    et_search_center_scope.setInputType(InputType.TYPE_CLASS_NUMBER);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
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
        try {
            JSONObject sendJson = new JSONObject();
            sendJson.put("poiSearchType",searchType+1);
            sendJson.put("poiSortBy",sortType+1);
            if (detailInfo == 0) {
                sendJson.put("detail",false);
            }else if (detailInfo == 1) {
                sendJson.put("detail",true);
            }

            sendJson.put("poiSearchContents",et_search_content.getText().toString().trim());
            sendJson.put("searchRadius",Integer.parseInt(et_search_radius.getText().toString().trim()));
            sendJson.put("poiDataPageSize",Integer.parseInt(et_page_size.getText().toString().trim()));

            JSONObject searchCenter = new JSONObject();
            searchCenter.put("centerType",centerType+1);
            searchCenter.put("centerExpression",centerExp+1);
            if (centerExp == 0) {
                searchCenter.put("centerPoint",et_search_center_exp.getText().toString().trim());
            }else if (centerExp == 1) {
                JSONObject centerPoint = new JSONObject();
                centerPoint.put("longitude",Integer.parseInt(et_center_long.getText().toString().trim()));
                centerPoint.put("latitude",Integer.parseInt(et_center_lat.getText().toString().trim()));
                searchCenter.put("centerPoint",centerPoint);
            }

            JSONObject searchScope = new JSONObject();
            searchScope.put("scopeType",scopeType+1);
            searchScope.put("scopeExpression",scopeExp+1);
            if (scopeExp == 0) {
                searchScope.put("searchScope",et_search_center_scope.getText().toString().trim());
            }else if (scopeExp == 1) {
                searchScope.put("searchScope",Integer.parseInt(et_search_center_scope.getText().toString().trim()));
            }


            sendJson.put("poiSearchCenter",searchCenter);
            sendJson.put("poiSearchScope",searchScope);

            JSONObject cmdJson = new JSONObject();
            cmdJson.put(Constant.CMD_KEY, Integer.parseInt("3000",16));
            cmdJson.put(Constant.JSON_KEY, sendJson);

            message = cmdJson.toString();
            sendMessage(message);
            Log.d(TAG, "makeJson: " + message);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
