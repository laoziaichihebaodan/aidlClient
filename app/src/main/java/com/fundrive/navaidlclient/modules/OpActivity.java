package com.fundrive.navaidlclient.modules;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fundrive.navaidlclient.Constant;
import com.fundrive.navaidlclient.R;
import com.fundrive.navaidlclient.Resource;
import com.fundrive.navaidlclient.bean.PageInfoBean;
import com.fundrive.navaidlclient.view.MultiSelectPopupWindows;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OpActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.iv_return)
    ImageView iv_return;
    @BindView(R.id.tv_tips)
    TextView tv_tips;
    @BindView(R.id.btn_apply)
    Button btn_apply;
    @BindView(R.id.ll_root)
    LinearLayout ll_root;

    private PageInfoBean.Lists protocolData;
    private int lists_index;

    List<PageInfoBean.Page> viewList = new ArrayList<>();
    List<PageInfoBean.SecondKey>  secondFloorKey = new ArrayList<>();
    List<PageInfoBean.ThirdKey>  thirdFloorKey = new ArrayList<>();


    private String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_op);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        protocolData = (PageInfoBean.Lists) intent.getSerializableExtra("PageInfoBean");
        lists_index = intent.getIntExtra("lists_index",0);

        ininView();
        initData();

    }

    @Override
    protected void onPause() {
        super.onPause();

        Resource.pageInfoBean.getLists().remove(lists_index);
        Resource.pageInfoBean.getLists().add(lists_index,protocolData);
    }

    @OnClick({R.id.btn_apply, R.id.iv_return})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_apply:
                //应用
                makeJson();
                showSendDialog(message);
                break;
            case R.id.iv_return:
                finish();
                break;
        }
    }

    private void initData(){
        viewList = protocolData.getItem().getPage();
        secondFloorKey = protocolData.getItem().getSecondKey();
        thirdFloorKey = protocolData.getItem().getThirdKey();
    }

    private void ininView(){
        if (protocolData == null){
            Toast.makeText(this, "未获得到协议数据", Toast.LENGTH_SHORT).show();
            return;
        }
        tv_title.setText(protocolData.getName());

        if (protocolData.getTips().isEmpty()){
            tv_tips.setVisibility(View.GONE);
        } else {
            tv_tips.setVisibility(View.VISIBLE);
            tv_tips.setText(protocolData.getTips().trim());
        }

        List<PageInfoBean.Page> list_page = protocolData.getItem().getPage();
        for (int i=0;i<list_page.size();i++){
            final PageInfoBean.Page page = list_page.get(i);
            String page_type = page.getType();

            LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams ll_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
            ll_params.height = 150;
            if (!page_type.isEmpty()){
                ll_root.addView(linearLayout,ll_params);
            }

            final int finalI = i;
            if (page_type.equals("edittext")){
                if (page.getName().isEmpty()){
                    EditText et = new EditText(this);
                    if (page.getValueType().equals("string")){
                        et.setInputType(InputType.TYPE_CLASS_TEXT);
                    } else if (page.getValueType().equals("int")){
                        et.setInputType(InputType.TYPE_CLASS_NUMBER);
                    }
                    et.setText(page.getValue());
                    et.setSelection(page.getValue().length());
                    linearLayout.addView(et);
                    LinearLayout.LayoutParams et_params = (LinearLayout.LayoutParams) et.getLayoutParams();
                    et_params.setMargins(20,0,30,0);
                    et_params.width = 0;
                    et_params.weight =1;
                    et.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            page.setValue(String.valueOf(s));
                            protocolData.getItem().getPage().set(finalI,page);
                        }
                    });
                } else {
                    TextView tv = new TextView(this);
                    tv.setTextSize(20);
                    tv.setText(page.getName());
                    linearLayout.addView(tv);
                    LinearLayout.LayoutParams tv_params = (LinearLayout.LayoutParams) tv.getLayoutParams();
                    tv_params.setMargins(30,0,0,0);

                    EditText et = new EditText(this);
                    if (page.getValueType().equals("string")){
                        et.setInputType(InputType.TYPE_CLASS_TEXT);
                    } else if (page.getValueType().equals("int")){
                        et.setInputType(InputType.TYPE_CLASS_NUMBER);
                    }
                    et.setText(page.getValue());
                    et.setSelection(page.getValue().length());
                    linearLayout.addView(et);
                    LinearLayout.LayoutParams et_params = (LinearLayout.LayoutParams) et.getLayoutParams();
                    et_params.setMargins(0,0,30,0);
                    if (i<list_page.size()-1 && list_page.get(i+1).getName().equals(page.getName())){
                        et_params.width = 300;
                    } else {
                        et_params.weight = 1;
                        et_params.width = 0;
                    }
                    et.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            page.setValue(String.valueOf(s));
                            protocolData.getItem().getPage().set(finalI,page);
                        }
                    });

                    if (i<list_page.size()-1 && list_page.get(i+1).getName().equals(page.getName())){
                        final PageInfoBean.Page page2 = list_page.get(i+1);
                        EditText et2 = new EditText(this);
                        if (page.getValueType().equals("string")){
                            et2.setInputType(InputType.TYPE_CLASS_TEXT);
                        } else if (page.getValueType().equals("int")){
                            et2.setInputType(InputType.TYPE_CLASS_NUMBER);
                        }
                        et2.setText(page2.getValue());
                        et2.setSelection(page2.getValue().length());
                        linearLayout.addView(et2);
                        LinearLayout.LayoutParams et_params2 = (LinearLayout.LayoutParams) et2.getLayoutParams();
                        et_params2.setMargins(0,0,30,0);
                        et_params2.width = 300;
                        et2.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                                page2.setValue(String.valueOf(s));
                                protocolData.getItem().getPage().set(finalI+1,page2);
                            }
                        });
                        i++;
                    }
                }
            }else if (page_type.equals("spinner")){
                TextView tv = new TextView(this);
                tv.setTextSize(20);
                tv.setText(page.getName());
                linearLayout.addView(tv);
                LinearLayout.LayoutParams tv_params = (LinearLayout.LayoutParams) tv.getLayoutParams();
                tv_params.setMargins(30,0,30,0);

                List<PageInfoBean.SpinnerValue> list_spinnerValue = page.getSpinnerValue();
                List<String> list_spinnerValue_name = new ArrayList<>();
                int select_index = 0;
                final List<String> list_spinnerValue_value = new ArrayList<>();
                for (PageInfoBean.SpinnerValue spinnerValue:list_spinnerValue){
                    list_spinnerValue_name.add(spinnerValue.getName());
                    list_spinnerValue_value.add(spinnerValue.getValue());
                    if (spinnerValue.getValue().equals(page.getValue())){
                        select_index = list_spinnerValue.indexOf(spinnerValue);
                    }
                }

                ArrayAdapter<String> adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,list_spinnerValue_name);
                Spinner spinner = new Spinner(this);
                spinner.setAdapter(adapter);
                spinner.setSelection(select_index);
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        page.setValue(list_spinnerValue_value.get(position));
                        protocolData.getItem().getPage().set(finalI,page);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
                linearLayout.addView(spinner);

            }else if (page_type.equals("mutilselect")){
                TextView tv = new TextView(this);
                tv.setTextSize(20);
                tv.setText(page.getName());
                linearLayout.addView(tv);
                LinearLayout.LayoutParams tv_params = (LinearLayout.LayoutParams) tv.getLayoutParams();
                tv_params.setMargins(30,0,30,0);

                final TextView tv_select = new TextView(this);
                tv_select.setTextSize(20);
                tv_select.setText("选择偏好");
                linearLayout.addView(tv_select);
                LinearLayout.LayoutParams tv_select_params = (LinearLayout.LayoutParams) tv_select.getLayoutParams();
                tv_select_params.setMargins(0,0,30,0);
                tv_select_params.weight = 1;
                tv_select_params.width = 0;

                tv_select.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final MultiSelectPopupWindows productsMultiSelectPopupWindows = new MultiSelectPopupWindows(OpActivity.this, tv_select, 100, page.getMutilSelectValue());
                        productsMultiSelectPopupWindows.setOnDismissListener(new PopupWindow.OnDismissListener() {
                            @Override
                            public void onDismiss() {
                                List<PageInfoBean.MutilSelectValue> list_after_select = productsMultiSelectPopupWindows.getMutilSelectValues();
                                int page_value = 0;
                                page.setMutilSelectValue(list_after_select);
                                for (PageInfoBean.MutilSelectValue mutilSelectValue:list_after_select){
                                    if (mutilSelectValue.isSelect()){
                                        page_value |= Integer.parseInt(mutilSelectValue.getValue());
                                    }
                                }
                                page.setValue(String.valueOf(page_value));
                                protocolData.getItem().getPage().set(finalI,page);
                            }
                        });
                    }
                });

            }
        }
    }
    private void makeJson() {

        List<PageInfoBean.Page> firstFloorView = new ArrayList<>();
        List<PageInfoBean.Page> secondFloorView = new ArrayList<>();
        List<PageInfoBean.Page> thirdFloorView = new ArrayList<>();
        for (int i = 0; i < viewList.size(); i++) {
            String floor = viewList.get(i).getFloor();
            if (floor.equals("1")){
                firstFloorView.add(viewList.get(i));
            }else if (floor.equals("2")){
                secondFloorView.add(viewList.get(i));
            }else if (floor.equals("3")){
                thirdFloorView.add(viewList.get(i));
            }
        }

        JSONObject cmdJson = new JSONObject();
        JSONObject jsonObject1 = new JSONObject();
        try {

            for (int i = 0; i < firstFloorView.size(); i++) {
                putJson(jsonObject1,firstFloorView.get(i).getValueType(),firstFloorView.get(i).getKey(),firstFloorView.get(i).getValue());
            }
            for (int i = 0; i < secondFloorKey.size(); i++) {
                JSONObject jsonObject2 = new JSONObject();
                for (int j = 0; j < secondFloorView.size(); j++) {
                    if (secondFloorKey.get(i).getName().equals(secondFloorView.get(j).getParentKey())){
//                        jsonObject2.put(secondFloorView.get(j).getKey(),secondFloorView.get(j).getValue());
                        putJson(jsonObject2,secondFloorView.get(j).getValueType(),secondFloorView.get(j).getKey(),secondFloorView.get(j).getValue());
                    }
                }
                for (int k = 0; k < thirdFloorKey.size(); k++) {
                    JSONObject jsonObject3 = new JSONObject();
                    int num = 0;
                    for (int l = 0; l < thirdFloorView.size(); l++) {
                        if (secondFloorKey.get(i).getName().equals(thirdFloorView.get(l).getGrandParentKey()) && thirdFloorKey.get(k).getNama().equals(thirdFloorView.get(l).getParentKey())){
//                            jsonObject3.put(thirdFloorView.get(l).getKey(),thirdFloorView.get(l).getValue());
                            putJson(jsonObject3,thirdFloorView.get(l).getValueType(),thirdFloorView.get(l).getKey(),thirdFloorView.get(l).getValue());
                            num++;
                        }
                    }
                    if (num != 0) {
                        jsonObject2.put(thirdFloorKey.get(k).getNama(), jsonObject3);
                    }
                }

                jsonObject1.put(secondFloorKey.get(i).getName(),jsonObject2);
            }



            cmdJson.put(Constant.CMD_KEY, Integer.parseInt(protocolData.getCmd(),16));
            cmdJson.put(Constant.JSON_KEY, jsonObject1);

            message = cmdJson.toString();
            sendMessage(message);
            Log.i(TAG, "makeJson: "+message);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void putJson(JSONObject object,String valueType,String key,String value) throws JSONException {
        switch (valueType){
            case "int":
                object.put(key,Integer.parseInt(value));
                break;
            case "string":
                object.put(key,value);
                break;
            case "boolean":
                boolean bValue = value.equals("true");
                object.put(key,bValue);
                break;
            case "long":
                object.put(key,Long.parseLong(value));
                break;
        }
    }
}
