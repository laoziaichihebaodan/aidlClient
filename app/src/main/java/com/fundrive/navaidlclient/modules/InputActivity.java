package com.fundrive.navaidlclient.modules;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.fundrive.navaidlclient.Constant;
import com.fundrive.navaidlclient.R;
import com.fundrive.navaidlclient.view.MyEditText;
import com.fundrive.navaidlclient.view.NavInputConnection;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class InputActivity extends BaseActivity {

    @BindView(R.id.et_input)
    MyEditText etInput;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
        ButterKnife.bind(this);
        tvTitle.setText("键盘输入字符串");
        etInput.setInputTextCallback(new NavInputConnection.InputText() {
            @Override
            public void inPut(CharSequence text) {
                makeJson(text);
                etInput.setText(text);
            }
        });
    }

    @OnClick({R.id.btn_return})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_return:
                finish();
                break;
        }
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
