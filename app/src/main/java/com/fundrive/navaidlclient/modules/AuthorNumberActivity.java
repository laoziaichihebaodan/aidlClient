package com.fundrive.navaidlclient.modules;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.fundrive.navaidlclient.Constant;
import com.fundrive.navaidlclient.JsonKey;
import com.fundrive.navaidlclient.R;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 设置授权序列号
 */
public class AuthorNumberActivity extends BaseActivity {

    @BindView(R.id.et_no)
    EditText etNo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author_number);
        ButterKnife.bind(this);
        etNo.setText("CG0052162470468");
    }

    @OnClick({R.id.btn_commit, R.id.btn_return})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_commit:
                String num = etNo.getText().toString().trim();
                if (TextUtils.isEmpty(num)) {
                    Toast.makeText(this, "授权序列号不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (num.getBytes().length > 31) {
                    Toast.makeText(this, "授权序列号最多为31个字节", Toast.LENGTH_SHORT).show();
                    return;
                }
                makeJson(num);
                break;
            case R.id.btn_return:
                finish();
                break;
        }
    }
    //组装json
    private void makeJson(String num) {
       JSONObject cmdJson = new JSONObject();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(JsonKey.AUTHOR_NUMBER, num);

            cmdJson.put(Constant.CMD_KEY, Constant.IA_CMD_SET_AUTHORIZE_SERIAL_NUMBER);
            cmdJson.put(Constant.JSON_KEY, jsonObject);

            String message = cmdJson.toString();
            sendMessage(message);
            Log.d(TAG, "makeJson: "+message);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
