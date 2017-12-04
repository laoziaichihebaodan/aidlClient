package com.fundrive.navaidlclient;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.fundrive.andrive.INavRemoteNotifier;
import com.fundrive.andrive.INavRemoteRequest;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    INavRemoteRequest mNavService;
    boolean mBind = false;
    ListView lv ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = findViewById(R.id.lv);
        lv.setAdapter(new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,Resource.strArr));
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
    protected void onStop() {
        super.onStop();

        // Unbind from the service
        if (mBind) {
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
    }

    private INavRemoteNotifier iMyNaviNotifyHandler = new INavRemoteNotifier.Stub() {

        @Override
        public void onNotify(int ia_cmd, String ia_json) throws RemoteException {
            System.out.println(ia_json);
        }
    };

    /** Defines callbacks for service binding, passed to bindService() */
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
            Resource.initRequest(mNavService,mBind);
            Log.d("wjh","绑定成功");
            Toast.makeText(MainActivity.this,"绑定成功",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBind = false;
            Toast.makeText(MainActivity.this,"绑定失败",Toast.LENGTH_SHORT).show();
            Log.d("wjh","绑定失败");
        }
    };

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if (Resource.device_model == ShareConfiguration.MODEL_CLIENT) {
            Resource.data = Integer.toString(i);
            Resource.udpClient();
        } else if (Resource.device_model == ShareConfiguration.MODEL_SERVER)
            //调用aidl函数
            Resource.callAidlFun(i);
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
