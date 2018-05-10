package com.fundrive.navaidlclient.modules;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.fundrive.navaidlclient.Constant;
import com.fundrive.navaidlclient.R;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 音量设置
 */
public class SetValumeActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.seekBar)
    SeekBar seekBar;
    @BindView(R.id.tv_value)
    TextView tvValue;
    @BindView(R.id.btn_set_volume)
    Button btnSetVolume;
    @BindView(R.id.btn_spec)
    Button btnSpec;
    @BindView(R.id.btn_set_sub)
    Button btnSetSub;
    @BindView(R.id.btn_sub)
    Button btnSub;
    @BindView(R.id.btn_add)
    Button btnAdd;
    private int progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_valume);
        ButterKnife.bind(this);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvValue.setText("" + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    @OnClick({R.id.btn_set_volume, R.id.btn_spec, R.id.btn_set_sub, R.id.btn_sub, R.id.btn_add})
    public void onViewClicked(View view) {
        progress = seekBar.getProgress();
        switch (view.getId()) {

            case R.id.btn_spec:
                makeJson(progress, 1);
                break;
            case R.id.btn_set_volume:
                makeJson(progress, 2);
                break;
            case R.id.btn_set_sub:
                makeJson(progress, 3);
                break;
            case R.id.btn_sub:
                seekBar.setProgress(progress - 5);
                break;
            case R.id.btn_add:
                seekBar.setProgress(progress + 5);
                break;
        }
    }

    //组装json
    private void makeJson(int num, int mode) {
        JSONObject cmdJson = new JSONObject();
        JSONObject jsonObject = new JSONObject();
        JSONObject volumeJson = new JSONObject();
        try {
            volumeJson.put("opType", mode);
            volumeJson.put("volume", num);

            jsonObject.put("volumeOperation", volumeJson);
            cmdJson.put(Constant.CMD_KEY, Constant.IA_CMD_SET_SOUND_VOLUME);
            cmdJson.put(Constant.JSON_KEY, jsonObject);

            String message = cmdJson.toString();
            sendMessage(message);
            Log.d(TAG, "makeJson: " + message);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
