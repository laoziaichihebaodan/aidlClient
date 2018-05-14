package com.fundrive.navaidlclient.modules;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

import com.fundrive.navaidlclient.Constant;
import com.fundrive.navaidlclient.R;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShowTargetVolumeActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.np_max)
    NumberPicker npMax;
    @BindView(R.id.np_current)
    NumberPicker npCurrent;
    @BindView(R.id.btn_commit)
    Button btnCommit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_target_volume);
        ButterKnife.bind(this);
        npMax.setValue(100);
        npMax.setMinValue(0);
        npMax.setMaxValue(100);
        npCurrent.setMaxValue(100);
        npCurrent.setMinValue(0);
        npCurrent.setValue(30);
        btnCommit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        makeJson(npMax.getValue(),npCurrent.getValue());
    }

    //组装json
    private void makeJson(int max, int current) {
        JSONObject cmdJson = new JSONObject();
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("targetMaxVolume", max);
            jsonObject.put("targetCurrentVolume", current);

            cmdJson.put(Constant.CMD_KEY, Constant.IA_CMD_SHOW_TARGET_SOUND_VOLUME);
            cmdJson.put(Constant.JSON_KEY, jsonObject);

            String message = cmdJson.toString();
            sendMessage(message);
            Log.d(TAG, "makeJson: " + message);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
