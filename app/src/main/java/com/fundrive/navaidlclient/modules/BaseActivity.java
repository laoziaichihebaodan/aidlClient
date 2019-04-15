package com.fundrive.navaidlclient.modules;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.TextView;

import com.fundrive.navaidlclient.R;
import com.fundrive.navaidlclient.Resource;
import com.fundrive.navaidlclient.ShareConfiguration;

public class BaseActivity extends AppCompatActivity {
    protected String TAG;
    private Dialog sendDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = this.getClass().getSimpleName();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (sendDialog != null && sendDialog.isShowing()){
            sendDialog.cancel();
        }
    }

    protected void showSendDialog(String message){
        sendDialog = new AlertDialog.Builder(this).create();
        sendDialog.show();
        sendDialog.setContentView(R.layout.send_dialog_bg);
        TextView tv_send = sendDialog.findViewById(R.id.tv_send);
        tv_send.setText(message);
        tv_send.setTextIsSelectable(true);
    }

    /**
     * 发送消息
     * @param message
     */
    protected void sendMessage(String message) {
        if (Resource.device_model == ShareConfiguration.MODEL_CLIENT) {
            Resource.udpClient(message);
        } else {
            Resource.callAidlFun(message);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
