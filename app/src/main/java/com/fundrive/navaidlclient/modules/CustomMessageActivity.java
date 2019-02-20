package com.fundrive.navaidlclient.modules;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fundrive.navaidlclient.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CustomMessageActivity extends BaseActivity {

    @BindView(R.id.et_cmd)
    EditText etCmd;
    @BindView(R.id.et_message)
    EditText etMessage;
    @BindView(R.id.btn_send)
    Button btnSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_message);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_send)
    public void onViewClicked() {
        String trim = etCmd.getText().toString().trim();
        if (TextUtils.isEmpty(trim)) {
            Toast.makeText(this, "cmd 不能为空！", Toast.LENGTH_SHORT).show();
            return;
        }
        String message = etMessage.getText().toString().trim();
        if (TextUtils.isEmpty(message)) {
            Toast.makeText(this, "message 不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            Integer cmd = Integer.decode(trim);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
