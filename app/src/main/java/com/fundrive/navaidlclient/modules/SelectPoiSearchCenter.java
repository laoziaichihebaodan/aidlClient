package com.fundrive.navaidlclient.modules;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.fundrive.navaidlclient.Constant;
import com.fundrive.navaidlclient.R;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SelectPoiSearchCenter extends BaseActivity {
    @BindView(R.id.et_index)
    EditText etIndex;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    //IA_CMD_SELECT_POI_SEARCH_CENTER
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_poi_search_center);
        ButterKnife.bind(this);
        tvTitle.setText("搜索中心点");
    }

    @OnClick({R.id.btn_return, R.id.btn_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_return:
                finish();
                break;
            case R.id.btn_commit:
                makeJson();
                break;
        }
    }

    private void makeJson() {
        JSONObject cmdJson = new JSONObject();
        JSONObject jsonObject = new JSONObject();
        try {
            String strIndex = etIndex.getText().toString().trim();
            Integer index = Integer.decode(strIndex);
            jsonObject.put("poiSearchCenterIndex", index);
            cmdJson.put(Constant.CMD_KEY, Constant.IA_CMD_SELECT_POI_SEARCH_CENTER);
            cmdJson.put(Constant.JSON_KEY, jsonObject);

            String message = cmdJson.toString();
            sendMessage(message);
            Log.d(TAG, "makeJson: " + message);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
