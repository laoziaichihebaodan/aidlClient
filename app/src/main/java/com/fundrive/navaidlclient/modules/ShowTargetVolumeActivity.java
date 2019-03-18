package com.fundrive.navaidlclient.modules;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.fundrive.navaidlclient.Constant;
import com.fundrive.navaidlclient.R;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShowTargetVolumeActivity extends BaseActivity {

    @BindView(R.id.np_max)
    NumberPicker npMax;
    @BindView(R.id.np_current)
    NumberPicker npCurrent;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_target_volume);
        ButterKnife.bind(this);
        tvTitle.setText("交互目标音量");
        npMax.setValue(100);
        npMax.setMinValue(0);
        npMax.setMaxValue(100);
        npCurrent.setMaxValue(100);
        npCurrent.setMinValue(0);
        npCurrent.setValue(30);
    }

    @OnClick({R.id.btn_commit, R.id.btn_return})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_commit:
                makeJson(npMax.getValue(),npCurrent.getValue());
                break;
            case R.id.btn_return:
                finish();
                break;
        }
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
