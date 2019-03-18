package com.fundrive.navaidlclient.modules;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_message);
        ButterKnife.bind(this);
        tvTitle.setText("自定义消息");
    }

    @OnClick({R.id.btn_send, R.id.btn_return})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.btn_send:
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
                break;
            case R.id.btn_return:
                finish();
                break;
        }
    }
}
