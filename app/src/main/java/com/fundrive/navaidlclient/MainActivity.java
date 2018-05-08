package com.fundrive.navaidlclient;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.fundrive.andrive.INavRemoteNotifier;
import com.fundrive.andrive.INavRemoteRequest;
import com.fundrive.navaidlclient.bean.CmdBean;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    INavRemoteRequest mNavService;
    boolean mBind = false;
    ListView lv;
    private int intType;
    private String strJson;
    private EditText editText;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = findViewById(R.id.lv);
        lv.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, Resource.beans));
        lv.setOnItemClickListener(this);
        Resource.init(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Bind to Navigation Service
        Intent intent = new Intent("NavigationAIDLService");
        intent.setPackage("com.fundrive.andrive");
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Unbind from the service

        if (mNavService != null) {
            try {
                mNavService.removeListener(iMyNaviNotifyHandler);
                System.out.println("INavRemoteNotifier removed!");
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        unbindService(mConnection);
        mBind = false;
    }

    private INavRemoteNotifier iMyNaviNotifyHandler = new INavRemoteNotifier.Stub() {

        @Override
        public void onNotify(int ia_cmd, String ia_json) throws RemoteException {
            System.out.println(ia_json);
        }
    };

    /**
     * Defines callbacks for service binding, passed to bindService()
     */
    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            mNavService = INavRemoteRequest.Stub.asInterface(service);
            try {

                mNavService.addListener(iMyNaviNotifyHandler);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            mBind = true;
            Resource.initRequest(mNavService, mBind);
            Log.d("wjh", "绑定成功");
            Toast.makeText(MainActivity.this, "绑定成功", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBind = false;
            Toast.makeText(MainActivity.this, "绑定失败", Toast.LENGTH_SHORT).show();
            Log.d("wjh", "绑定失败");
        }
    };

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {

        final CmdBean bean = Resource.beans[i];
        if (Resource.device_model == ShareConfiguration.MODEL_CLIENT) {

            DialogUtils.initDialog(this, bean.getStrJson(), new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        String s = ((EditText) v).getText().toString();
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("intType", bean.getCMD());
                        jsonObject.put("strJson", s);
                        Resource.udpClient(jsonObject.toString());
                        Log.d("MainActivity", "onClick: 组装数据===="+jsonObject.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } else if (Resource.device_model == ShareConfiguration.MODEL_SERVER)
            //调用aidl函数
            DialogUtils.initDialog(this, bean.getStrJson(), new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        String s = ((EditText) v).getText().toString();
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("intType", bean.getCMD());
                        jsonObject.put("strJson", s);
                        Resource.callAidlFun(jsonObject.toString());
                        Log.d("MainActivity", "onClick: 组装数据===="+jsonObject.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

    }


    /** Called when a button is clicked (the button in the layout file attaches to
     * this method with the android:onClick attribute) */
//    public void onSend2NaviClick(View v) throws RemoteException {
//        if (mBind) {
//            // Call a method from the LocalService.
//            // However, if this call were something that might hang, then this request should
//            // occur in a separate thread to avoid slowing down the activity performance.
//            mNavService.request(0x2003, "{\"operationType\":1,\"screenId\":1}");
//            //Toast.makeText(this, "number: " + num, Toast.LENGTH_SHORT).show();
//        }
//    }

}
