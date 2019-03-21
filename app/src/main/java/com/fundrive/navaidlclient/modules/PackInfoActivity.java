package com.fundrive.navaidlclient.modules;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.fundrive.navaidlclient.Constant;
import com.fundrive.navaidlclient.R;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PackInfoActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.pack_in)
    TextView pack_in;
    @BindView(R.id.pack_out)
    TextView pack_out;
    @BindView(R.id.pack_state_update)
    TextView pack_state_update;
    @BindView(R.id.pack_pay_failure)
    TextView pack_pay_failure;
    @BindView(R.id.pack_pay_success)
    TextView pack_pay_success;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pack_info);

        ButterKnife.bind(this);
        tvTitle.setText("停车无忧");

    }
    @OnClick({R.id.pack_in, R.id.pack_out,R.id.pack_state_update,R.id.pack_pay_failure,R.id.pack_pay_success,R.id.btn_return})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.pack_in://进入停车场
                makeJson(1);
                break;
            case R.id.pack_out://离开停车场
                makeJson(2);
                break;
            case R.id.pack_state_update://更新停车场
                makeJson(3);
                break;
            case R.id.pack_pay_failure://支付失败
                makeJson(4);
                break;
            case R.id.pack_pay_success://支付成功
                makeJson(5);
                break;
            case R.id.btn_return:
                finish();
                break;
        }
    }

    private void makeJson(int num) {
        JSONObject cmdJson = new JSONObject();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("parkingAction", num);
            if (num == 1){
                jsonObject.put("parkingName","xxx停车场");
            }
            cmdJson.put(Constant.CMD_KEY, Constant.IA_CMD_GO_PARKING_INFO);
            cmdJson.put(Constant.JSON_KEY, jsonObject);

            String message = cmdJson.toString();
            sendMessage(message);
            Log.d(TAG, "makeJson: "+message);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
