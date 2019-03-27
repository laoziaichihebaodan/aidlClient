package com.fundrive.navaidlclient.modules;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fundrive.navaidlclient.R;
import com.fundrive.navaidlclient.Resource;
import com.fundrive.navaidlclient.bean.PageInfoBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OpActivity extends AppCompatActivity {

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

    private PageInfoBean protocolData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_op);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        protocolData = (PageInfoBean) intent.getSerializableExtra("PageInfoBean");

        ininView();

    }

    @OnClick({R.id.btn_apply, R.id.iv_return})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_apply:
                //应用
                break;
            case R.id.iv_return:
                finish();
                break;
        }
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
            ll_params.height = 200;
            ll_root.addView(linearLayout,ll_params);

            final int finalI = i;
            if (page_type.equals("edittext")){
                if (page.getName().isEmpty()){
                    EditText et = new EditText(this);
                    et.setText(page.getValue());
                    et.setSelection(page.getValue().length());
                    linearLayout.addView(et);
                    LinearLayout.LayoutParams et_params = (LinearLayout.LayoutParams) et.getLayoutParams();
                    et_params.setMargins(20,0,50,0);
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
                    tv_params.setMargins(30,0,30,0);

                    EditText et = new EditText(this);
                    et.setText(page.getValue());
                    et.setSelection(page.getValue().length());
                    linearLayout.addView(et);
                    LinearLayout.LayoutParams et_params = (LinearLayout.LayoutParams) et.getLayoutParams();
                    et_params.setMargins(20,0,50,0);
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
                final List<String> list_spinnerValue_value = new ArrayList<>();
                for (PageInfoBean.SpinnerValue spinnerValue:list_spinnerValue){
                    list_spinnerValue_name.add(spinnerValue.getName());
                    list_spinnerValue_value.add(spinnerValue.getValue());
                }

                ArrayAdapter<String> adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,list_spinnerValue_name);
                Spinner spinner = new Spinner(this);
                spinner.setAdapter(adapter);
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

            }
        }
    }










}
