package com.fundrive.navaidlclient.modules;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.fundrive.navaidlclient.Resource;
import com.fundrive.navaidlclient.ShareConfiguration;

public class BaseActivity extends AppCompatActivity {
    protected String TAG;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = this.getClass().getSimpleName();
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
}
