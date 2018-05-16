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

public class SetDisplayScreenActivity extends BaseActivity {

    @BindView(R.id.sp_screen)
    Spinner spScreen;
    private int screenId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_display_screen);
        ButterKnife.bind(this);
        spScreen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                screenId = position;
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
            jsonObject.put("iaDisPlayScreenId", screenId);
            cmdJson.put(Constant.CMD_KEY, Constant.IA_CMD_SET_DISPLAY_SCREEN_FOR_NAVAPP);
            cmdJson.put(Constant.JSON_KEY, jsonObject);

            String message = cmdJson.toString();
            sendMessage(message);
            Log.d(TAG, "makeJson: " + message);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
