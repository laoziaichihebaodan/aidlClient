package com.fundrive.navaidlclient.modules;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.fundrive.navaidlclient.Constant;
import com.fundrive.navaidlclient.R;
import com.fundrive.navaidlclient.Resource;
import com.fundrive.navaidlclient.ShareConfiguration;
import com.fundrive.navaidlclient.bean.Observer;
import com.fundrive.navaidlclient.bean.PageInfoBean;
import com.fundrive.navaidlclient.bean.PoiSearchResultBean;
import com.fundrive.navaidlclient.position.Points;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Combined_SearchActivity extends BaseActivity {
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
    //提示
    @BindView(R.id.tv_tips)
    TextView tv_tips;

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
    private Observer response = new SearchResponse();
    private PoiSearchResultBean poiSearchResultBean;
    private AlertDialog searchResultDialog;
    private SearchResultAdapter adapter;
    //保存搜索过的页的poi信息
    private List<PoiSearchResultBean> beanList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combined__search);
        ButterKnife.bind(this);

        Resource.registerObserver(response);
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
//                showSendDialog(message);
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
//            Log.d(TAG, "makeJson: " + message);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private Spinner poiSelectSpinner;
    private List<String> data;
    private boolean firstInit = false;
    private EditText et_page_num;
    private TextView pageNum;
    private TextView pageTotalNum;
    private Button previousPage;
    private Button nextPage;
    private void showSearchResultDialog() {

        final int size = poiSearchResultBean.getCurPoiDatum().size() <= 5 ? poiSearchResultBean.getCurPoiDatum().size() : 5;
        if (searchResultDialog == null) {
            searchResultDialog = new AlertDialog.Builder(this).create();
            View view = LayoutInflater.from(this).inflate(R.layout.layout_list_search_result,null);//View.inflate(getParent(),R.layout.layout_list_search_result,null);
            ListView lv = view.findViewById(R.id.lv_search_result);
            Button initPoiPaint = view.findViewById(R.id.init_poi_paint);
            Button clearPoiPaint = view.findViewById(R.id.clear_poi_paint);
            poiSelectSpinner = view.findViewById(R.id.sp_select_poi);
            previousPage = view.findViewById(R.id.page_previous);
            nextPage = view.findViewById(R.id.page_next);
            pageNum = view.findViewById(R.id.page_cur_num);
            pageTotalNum = view.findViewById(R.id.page_total_num);
            et_page_num = view.findViewById(R.id.et_page_num);
            Button jumpTo = view.findViewById(R.id.page_jump_to);

            jumpTo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String inputNum = et_page_num.getText().toString().trim();
                    if (!TextUtils.isEmpty(inputNum) && Integer.parseInt(inputNum) >= 1 && Integer.parseInt(inputNum) <= poiSearchResultBean.getPioDataPageTotal()){
                        int index = hasAdd(Integer.parseInt(inputNum),poiSearchResultBean.getPoiDataType());
                        if (index != -1){
                            poiSearchResultBean = beanList.get(index);
                            showSearchResultDialog();
                        }else {
                            makeJumpToJson(Integer.parseInt(inputNum));
                        }
                    }else{
                        Toast.makeText(Combined_SearchActivity.this,"请输入页数范围内的数字",Toast.LENGTH_SHORT).show();
                    }
                }
            });



            previousPage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (poiSearchResultBean.getCurPageNum() != 1){
                        int index = hasAdd(poiSearchResultBean.getCurPageNum()-1,poiSearchResultBean.getPoiDataType());
                        if (index != -1){
                            poiSearchResultBean = beanList.get(index);
                            showSearchResultDialog();
                        }else {
                            makePreviousPageJson();
                        }
                    }
                }
            });
            nextPage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (poiSearchResultBean.getCurPageNum() < poiSearchResultBean.getPioDataPageTotal()){
                        int index = hasAdd(poiSearchResultBean.getCurPageNum()+1,poiSearchResultBean.getPoiDataType());
                        if (index != -1){
                            poiSearchResultBean = beanList.get(index);
                            showSearchResultDialog();
                        }else {
                            makeNextPageJson();
                        }
                    }

                }
            });

            data = getStringListData(size);
            ArrayAdapter<String> spAdapter = new ArrayAdapter<>(this,android.R.layout.simple_dropdown_item_1line,android.R.id.text1,data);
            poiSelectSpinner.setAdapter(spAdapter);
            poiSelectSpinner.setSelection(0);
            firstInit = false;
            initPoiPaint.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    makePaintPoiJson(size);
                    poiSelectSpinner.setSelection(0);
                    firstInit = false;
                }
            });

            clearPoiPaint.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    makeClearPoiJson();
                    poiSelectSpinner.setSelection(0);
                    firstInit = false;
                }
            });

            poiSelectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (firstInit || position != 0) {
                        makeSelectPoiJson(position);
                    }
                    firstInit = true;

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            adapter = new SearchResultAdapter(poiSearchResultBean.getCurPoiDatum());
            lv.setAdapter(adapter);
            searchResultDialog.setView(view);
        }else{
           data = getStringListData(size);
           adapter.setCurPoiDatum(poiSearchResultBean.getCurPoiDatum());
           adapter.notifyDataSetChanged();
        }

        pageNum.setText(poiSearchResultBean.getCurPageNum()+"");
        pageTotalNum.setText(poiSearchResultBean.getPioDataPageTotal()+"");
        if (poiSearchResultBean.getPioDataPageTotal() == 1){
            previousPage.setBackgroundResource(R.drawable.bottom_bg_solid_grey);
            nextPage.setBackgroundResource(R.drawable.bottom_bg_solid_grey);
        }else if (poiSearchResultBean.getCurPageNum() == 1){
            previousPage.setBackgroundResource(R.drawable.bottom_bg_solid_grey);
            nextPage.setBackgroundResource(R.drawable.bottom_bg_solid);
        }else if (poiSearchResultBean.getCurPageNum() == poiSearchResultBean.getPioDataPageTotal()){
            previousPage.setBackgroundResource(R.drawable.bottom_bg_solid);
            nextPage.setBackgroundResource(R.drawable.bottom_bg_solid_grey);
        }else{
            previousPage.setBackgroundResource(R.drawable.bottom_bg_solid);
            nextPage.setBackgroundResource(R.drawable.bottom_bg_solid);
        }

        searchResultDialog.show();

    }

    /**
     * 跳转到
     */
    private void makeJumpToJson(int inputNum) {
        try {
            JSONObject sendJson = new JSONObject();
            sendJson.put("poiDataType",poiSearchResultBean.getPoiDataType());
            sendJson.put("poiDataPageNum",inputNum);


            JSONObject cmdJson = new JSONObject();
            cmdJson.put(Constant.CMD_KEY, Integer.parseInt("3002",16));
            cmdJson.put(Constant.JSON_KEY, sendJson);

            message = cmdJson.toString();
            sendMessage(message);
            showSendDialog(message);
            Log.d(TAG, "makeJson: " + message);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 下一页
     */
    private void makeNextPageJson() {
        try {
            JSONObject sendJson = new JSONObject();
            sendJson.put("poiDataType",poiSearchResultBean.getPoiDataType());
            sendJson.put("poiDataPageNum",poiSearchResultBean.getCurPageNum()+1);


            JSONObject cmdJson = new JSONObject();
            cmdJson.put(Constant.CMD_KEY, Integer.parseInt("3002",16));
            cmdJson.put(Constant.JSON_KEY, sendJson);

            message = cmdJson.toString();
            sendMessage(message);
            showSendDialog(message);
            Log.d(TAG, "makeJson: " + message);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 上一页
     */
    private void makePreviousPageJson() {
        try {
            JSONObject sendJson = new JSONObject();
            sendJson.put("poiDataType",poiSearchResultBean.getPoiDataType());
            sendJson.put("poiDataPageNum",poiSearchResultBean.getCurPageNum()-1);


            JSONObject cmdJson = new JSONObject();
            cmdJson.put(Constant.CMD_KEY, Integer.parseInt("3002",16));
            cmdJson.put(Constant.JSON_KEY, sendJson);

            message = cmdJson.toString();
            sendMessage(message);
            showSendDialog(message);
            Log.d(TAG, "makeJson: " + message);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 清除poi画点
     */
    private void makeClearPoiJson() {
        try {
            JSONObject sendJson = new JSONObject();
            sendJson.put("iaAction",3);

            JSONObject cmdJson = new JSONObject();
            cmdJson.put(Constant.CMD_KEY, Integer.parseInt("2034",16));
            cmdJson.put(Constant.JSON_KEY, sendJson);

            message = cmdJson.toString();
            sendMessage(message);
            showSendDialog(message);
            Log.d(TAG, "makeJson: " + message);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 高亮选中poi
     */
    private void makeSelectPoiJson(int id) {
        try {
            JSONObject sendJson = new JSONObject();
            sendJson.put("iaAction",2);
            sendJson.put("iaPoiId",id);

            JSONObject cmdJson = new JSONObject();
            cmdJson.put(Constant.CMD_KEY, Integer.parseInt("2034",16));
            cmdJson.put(Constant.JSON_KEY, sendJson);

            message = cmdJson.toString();
            sendMessage(message);
            showSendDialog(message);
            Log.d(TAG, "makeJson: " + message);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化poi画点
     */
    private void makePaintPoiJson(int size) {
        try {
            JSONObject sendJson = new JSONObject();
            sendJson.put("iaAction",1);

            JSONArray array = new JSONArray();
            for (int i = 0; i < size; i++) {
                JSONObject poi = new JSONObject();
                poi.put("longitude",poiSearchResultBean.getCurPoiDatum().get(i).getIaPoiDisPos().getLongitude());
                poi.put("latitude",poiSearchResultBean.getCurPoiDatum().get(i).getIaPoiDisPos().getLatitude());

                JSONObject poiInfo = new JSONObject();
                poiInfo.put("iaPoiDisPos",poi);
                poiInfo.put("iaPoiId",i);
                poiInfo.put("selected",i == 0);
                array.put(poiInfo);
            }
            sendJson.put("poiList",array);

            JSONObject cmdJson = new JSONObject();
            cmdJson.put(Constant.CMD_KEY, Integer.parseInt("2034",16));
            cmdJson.put(Constant.JSON_KEY, sendJson);

            message = cmdJson.toString();
            sendMessage(message);
            showSendDialog(message);
            Log.d(TAG, "makeJson: " + message);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private List<String> getStringListData(int size) {
        List<String> data = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            data.add("高亮选中第"+(i+1)+"条");
        }
        return data;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Resource.removeObserver(response);
        if (searchResultDialog != null){
            searchResultDialog.cancel();
            searchResultDialog = null;
        }
        Resource.removeObserver(response);
    }

    class SearchResultAdapter extends BaseAdapter{
        private List<PoiSearchResultBean.CurPoiDatum> curPoiDatum;

        public void setCurPoiDatum(List<PoiSearchResultBean.CurPoiDatum> curPoiDatum) {
            this.curPoiDatum = curPoiDatum;
        }

        public SearchResultAdapter(List<PoiSearchResultBean.CurPoiDatum> curPoiDatum) {
            this.curPoiDatum = curPoiDatum;
        }

        @Override
        public int getCount() {
            return curPoiDatum.size();
        }

        @Override
        public Object getItem(int position) {
            return curPoiDatum.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            convertView = View.inflate(Combined_SearchActivity.this,R.layout.item_list_search_result,null);
            final PoiSearchResultBean.CurPoiDatum bean = curPoiDatum.get(position);
            TextView name = convertView.findViewById(R.id.poi_name);
            TextView adress = convertView.findViewById(R.id.poi_adress);
            final Button center = convertView.findViewById(R.id.poi_center);

            if (poiSearchResultBean.getPoiDataType() == 1){
                center.setText("设为中心点");
            }else if (poiSearchResultBean.getPoiDataType() == 2) {
                center.setText("导航");
            }
            name.setText(bean.getIaPoiName());
            adress.setText(bean.getIaPoiAdress());
            center.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (poiSearchResultBean.getPoiDataType() == 1){
                        center.setText("设为中心点");
                        makeCenterJson(position+1);

                    }else if (poiSearchResultBean.getPoiDataType() == 2) {
                        center.setText("去算路");
                        Intent intent = new Intent(Combined_SearchActivity.this,CalculationAndNaviActivity.class);
                        JSONObject obj_endPoint = Points.pointJson(bean.getIaPoiType(),bean.getIaPoiPos().getLongitude(),bean.getIaPoiPos().getLatitude(),
                                bean.getIaPoiDisPos().getLongitude(), bean.getIaPoiDisPos().getLatitude(),Long.decode(bean.getIaPoiId()),bean.getIaChildPoiNum(),0,bean.getIaPoiName(),bean.getIaPoiAdress(),bean.getIaPoiPhone(),bean.getIaRegionName(),
                                bean.getIaPoiTypeName());
                        intent.putExtra("search_endPoint",obj_endPoint.toString());
                        startActivity(intent);
                    }
                }
            });

            return convertView;
        }
    }

    /**
     * 设置为搜索中心点
     * @param position
     */
    private void makeCenterJson(int position) {

        try {
            JSONObject sendJson = new JSONObject();
            sendJson.put("poiSearchCenterIndex",position);

            JSONObject cmdJson = new JSONObject();
            cmdJson.put(Constant.CMD_KEY, Integer.parseInt("3001",16));
            cmdJson.put(Constant.JSON_KEY, sendJson);

            message = cmdJson.toString();
            sendMessage(message);
            Log.d(TAG, "makeJson: " + message);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    protected void sendMessage(String message) {
        if (Resource.device_model == ShareConfiguration.MODEL_CLIENT) {
            Resource.udpClient(message);
        } else {
            Resource.callAidlFun(message);
        }
    }

    class SearchResponse implements Observer {

        @Override
        public void update(final int cmd, final String message) {
            if ("c001".equals(Integer.toHexString(cmd))){
                poiSearchResultBean = new Gson().fromJson(message,PoiSearchResultBean.class);
                if (searchResultDialog !=null && !searchResultDialog.isShowing()){
                    beanList.clear();
                }
                if (poiSearchResultBean != null ){
                    if (hasAdd(poiSearchResultBean.getCurPageNum(),poiSearchResultBean.getPoiDataType()) == -1) {
                        beanList.add(poiSearchResultBean);
                    }
                }
                showSearchResultDialog();
            }
        }
    }

    /**
     * 该页是否已经加载过    加载过就不再请求
     * @param pageNum       要加载的页
     * @param pageType      要加载的数据类型
     * @return              -1 没有加载过  其他 加载过
     */
    private int hasAdd(int pageNum,int pageType){
        int index = -1;
        for (int i = 0; i < beanList.size(); i++) {
            if (pageNum == beanList.get(i).getCurPageNum() && pageType == beanList.get(i).getPoiDataType()){
                index = i;
                break;
            }
        }
        return index;
    }
}
