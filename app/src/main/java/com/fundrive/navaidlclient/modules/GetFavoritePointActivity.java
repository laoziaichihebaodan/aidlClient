package com.fundrive.navaidlclient.modules;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import com.fundrive.navaidlclient.Constant;
import com.fundrive.navaidlclient.R;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GetFavoritePointActivity extends BaseActivity {

    @BindView(R.id.sp_type)
    Spinner spType;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    private int favorType ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_favorite_point);
        ButterKnife.bind(this);
        tvTitle.setText("获取收藏点");
        spType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                favorType = position;
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
            jsonObject.put("iaFavType", favorType);
            cmdJson.put(Constant.CMD_KEY,Constant.IA_CMD_GET_FAVORITE_POINT);
            cmdJson.put(Constant.JSON_KEY, jsonObject);

            String message = cmdJson.toString();
            sendMessage(message);
            Log.d(TAG, "makeJson: "+message);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
