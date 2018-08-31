package com.fundrive.navaidlclient.modules;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.fundrive.navaidlclient.Constant;
import com.fundrive.navaidlclient.R;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FavoriteGuidanceActivity extends BaseActivity {

    @BindView(R.id.sp_mode)
    Spinner spMode;
    @BindView(R.id.sp_where)
    Spinner spWhere;
    private  int naiMode = 1;//模拟导航
    private  int toWhere = 1;//家

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_guidance);
        ButterKnife.bind(this);
        spMode.setSelection(1);
        spWhere.setSelection(1);
        spMode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                naiMode = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spWhere.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                toWhere = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @OnClick(R.id.btn_commit)
    public void onViewClicked() {
        makeJson();
    }

    //组装json
    private void makeJson() {
        JSONObject cmdJson = new JSONObject();
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("iaFavType", naiMode);
            jsonObject.put("guideType", toWhere);

            cmdJson.put(Constant.CMD_KEY, Constant.IA_CMD_FAVORITE_GUIDANCE);
            cmdJson.put(Constant.JSON_KEY, jsonObject);
            System.out.println(jsonObject.toString());
            String message = cmdJson.toString();
            sendMessage(message);
            Log.d(TAG, "makeJson: " + message);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
