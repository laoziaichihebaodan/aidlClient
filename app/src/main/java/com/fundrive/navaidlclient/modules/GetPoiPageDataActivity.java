package com.fundrive.navaidlclient.modules;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.fundrive.navaidlclient.Constant;
import com.fundrive.navaidlclient.R;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GetPoiPageDataActivity extends BaseActivity {
    @BindView(R.id.sp_page_data_type)
    Spinner spPageDataType;
    @BindView(R.id.et_num)
    EditText etNum;
    private int pageDataType;

    //IA_CMD_GET_POI_PAGE_DATA
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_poi_page_data);
        ButterKnife.bind(this);
        spPageDataType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                pageDataType = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void makeJson() {
        if (0 == pageDataType) {
            Toast.makeText(this, "请选择当前页Poi数据的类别", Toast.LENGTH_SHORT).show();
            return;
        }
        JSONObject cmdJson = new JSONObject();
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put("poiDataType", pageDataType);
            String strNum = etNum.getText().toString().trim();
            Integer page = Integer.decode(strNum);
            jsonObject.put("poiDataPageNum", page < 1 ? 1 : page);
            cmdJson.put(Constant.CMD_KEY, Constant.IA_CMD_GET_POI_PAGE_DATA);
            cmdJson.put(Constant.JSON_KEY, jsonObject);

            String message = cmdJson.toString();
            sendMessage(message);
            Log.d(TAG, "makeJson: " + message);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.btn_commit)
    public void onViewClicked() {
        makeJson();
    }
}
