package com.fundrive.navaidlclient.modules;

import android.os.Bundle;
import android.util.Log;

import com.fundrive.navaidlclient.Constant;
import com.fundrive.navaidlclient.R;
import com.fundrive.navaidlclient.view.MyEditText;
import com.fundrive.navaidlclient.view.NavInputConnection;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InputActivity extends BaseActivity {

    @BindView(R.id.et_input)
    MyEditText etInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
        ButterKnife.bind(this);
        etInput.setInputTextCallback(new NavInputConnection.InputText() {
            @Override
            public void inPut(CharSequence text) {
                makeJson(text);
                etInput.setText(text);
            }
        });
    }

    private void makeJson(CharSequence text) {
        JSONObject cmdJson = new JSONObject();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("iaKeyboardInput", text);
            cmdJson.put(Constant.CMD_KEY,Constant.IA_CMD_UPDATE_KEYBOARD_INPUT);
            cmdJson.put(Constant.JSON_KEY, jsonObject);

            String message = cmdJson.toString();
            sendMessage(message);
            Log.d(TAG, "makeJson: "+message);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
