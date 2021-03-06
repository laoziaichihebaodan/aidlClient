package com.fundrive.navaidlclient;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.design.widget.TabLayout;
import com.androidkun.xtablayout.XTabLayout;
import com.fundrive.andrive.INavRemoteNotifier;
import com.fundrive.andrive.INavRemoteRequest;
import com.fundrive.navaidlclient.adapter.PageInfoAdapter;
import com.fundrive.navaidlclient.bean.PageInfoBean;
import com.fundrive.navaidlclient.modules.BaseActivity;
import com.fundrive.navaidlclient.modules.CalculationAndNaviActivity;
import com.fundrive.navaidlclient.modules.Combined_GuessHomeAndNavi;
import com.fundrive.navaidlclient.modules.Combined_SearchActivity;
import com.fundrive.navaidlclient.modules.GuessHomeAndCompanyActivity;
import com.fundrive.navaidlclient.modules.ListAnimationActivity;
import com.fundrive.navaidlclient.modules.MutimediaInformationActivity;
import com.fundrive.navaidlclient.modules.OpActivity;
import com.fundrive.navaidlclient.modules.RouteByConditionActivity;
import com.fundrive.navaidlclient.modules.SearchActivity;
import com.fundrive.navaidlclient.modules.TimeInfoActivity;
import com.fundrive.navaidlclient.utils.FileUtils;
import com.fundrive.navaidlclient.utils.VersionUpdateUtils;
import com.google.gson.Gson;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements AdapterView.OnItemClickListener, View.OnClickListener {
    @BindView(R.id.lv)
    ListView lv;
    @BindView(R.id.btn_clear)
    Button button;
    @BindView(R.id.tv_change)
    TextView tv_change;
    @BindView(R.id.et_content)
    AutoCompleteTextView editText;
    @BindView(R.id.tablayout)
    XTabLayout tabLayout;

    INavRemoteRequest mNavService;
    boolean mBind = false;
//    private ArrayAdapter<PageInfoBean> adapter;
    private PageInfoAdapter adapter;
    public static final int REQUEST_CODE = 1;//读写权限code
    public static final int REQUEST_INSTALL = 11;//安装权限code
    public static final int INSTALL_PACKAGES_REQUESTCODE = 2;//安卓8.0安装权限code
    private VersionUpdateUtils updateUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //检查更新
        updateUtils = new VersionUpdateUtils(this);
        updateUtils.checkVersionUpdate();
//        updateUtils.startInstallPermissionSettingActivity();
        //检查读写权限
        checkPermission();
        //防止写入文件过大，分割成多个文件
        try {
            FileUtils.splitFile(Environment.getExternalStorageDirectory().toString()+"/"+FileUtils.notifyFileName,FileUtils.notifyFileFormat,3,10);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_INSTALL) {
            Log.i("hebaodan","未知应用安装授权成功");
            updateUtils.installApk();
        } else{
            Log.i("hebaodan","未知应用安装授权失败");
            Toast.makeText(this,"请打开未知应用安装权限",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(adapter != null){
            adapter.setData(Resource.pageInfoBean.getLists(Resource.pageInfoBean.getListType().get((tabLayout.getSelectedTabPosition() == -1)?0:tabLayout.getSelectedTabPosition())));
            adapter.notifyDataSetChanged();
            if (TextUtils.isEmpty(editText.getText().toString().trim()))
                adapter.getFilter().filter(editText.getText());//搜索文本为空时，清除ListView的过滤
            else
                adapter.getFilter().filter(editText.getText().toString().trim());//设置过滤关键字
        }
    }

    private void init(){
        tv_change.setVisibility(View.VISIBLE);
        tv_change.setOnClickListener(this);
        lv.setTextFilterEnabled(true);
        findViewById(R.id.btn_return).setVisibility(View.GONE);
        button.setOnClickListener(this);

        adapter = new PageInfoAdapter(this, Resource.pageInfoBean.getLists(Resource.pageInfoBean.getListType().get((tabLayout.getSelectedTabPosition() == -1)?0:tabLayout.getSelectedTabPosition())));
        lv.setAdapter(adapter);

        if (Resource.pageInfoBean.getListType() != null) {
            for (int i = 0; i < Resource.pageInfoBean.getListType().size(); i++) {
                tabLayout.addTab(tabLayout.newTab().setText(Resource.pageInfoBean.getListType().get(i)));
            }
        }

        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setOnTabSelectedListener(new XTabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(XTabLayout.Tab tab) {
                List<PageInfoBean.Lists> list_lists = Resource.pageInfoBean.getLists(tab.getText().toString());
                adapter.setData(list_lists);
                if (TextUtils.isEmpty(editText.getText().toString().trim()))
                    adapter.getFilter().filter(editText.getText());//搜索文本为空时，清除ListView的过滤
                else
                    adapter.getFilter().filter(editText.getText().toString().trim());//设置过滤关键字
            }

            @Override
            public void onTabUnselected(XTabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(XTabLayout.Tab tab) {

            }
        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                adapter.getFilter().filter(s);
                if (TextUtils.isEmpty(s.toString().trim()))
                    adapter.getFilter().filter(s);//搜索文本为空时，清除ListView的过滤
                else
                    adapter.getFilter().filter(s.toString().trim());//设置过滤关键字
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        //editText.setAdapter(adapter);
        lv.setOnItemClickListener(this);
        Resource.init(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Bind to Navigation Service
        //Intent intent = new Intent("NavigationAIDLService");
        //intent.setPackage("com.fundrive.andrive");
        //Intent intent = new Intent("NavService");
        Intent intent = new Intent();
        ComponentName navService = new ComponentName("com.fundrive.naviwidgetdemo",
                "com.fundrive.naviwidgetdemo.NavService");
        intent.setComponent(navService);
        //服务所在应用程序没有启动的时候8.0会报错
//        startService(intent);
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
        public void onNotify(final int ia_cmd, final String ia_json) throws RemoteException {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SS");// HH:mm:ss:SS
            Date date = new Date(System.currentTimeMillis());//获取当前时间
            String data = simpleDateFormat.format(date)+": cmd = "+Integer.toHexString(ia_cmd) + "---json = "+ia_json;
            FileUtils.writeFile(data,Environment.getExternalStorageDirectory(),FileUtils.notifyFileName+FileUtils.notifyFileFormat,true);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < Resource.list_observer.size(); i++) {
                        Resource.list_observer.get(i).update(ia_cmd,ia_json);
                    }
                }
            });
            sendMessageFromServerToClient(ia_cmd,ia_json);
            Log.e("zzz","server send:"+data);
        }

        @Override
        public void onTTS(String tts) throws RemoteException {
            System.out.println("" + tts);
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
        PageInfoBean.Lists lists = (PageInfoBean.Lists) adapter.getItem(i);
        int lists_index = 0;

        for (PageInfoBean.Lists lists_i:Resource.pageInfoBean.getLists()){
            if (lists_i == null){
                continue;
            }
            if (lists.getCmd().equals(lists_i.getCmd()) && lists.getName().equals(lists_i.getName())){
                lists_index = Resource.pageInfoBean.getLists().indexOf(lists_i);
            }
        }
        if (lists.getItem() == null) {
            sendMessage(Integer.parseInt(lists.getCmd(), 16));
        } else {
            Intent intent;
            switch (Integer.parseInt(lists.getCmd(),16)){
                case 0x201B:
                    intent = new Intent(MainActivity.this, GuessHomeAndCompanyActivity.class);
                    break;
                case 0x1005:
                    intent = new Intent(MainActivity.this, TimeInfoActivity.class);
                    break;
                case 0x1008:
                    intent = new Intent(MainActivity.this, MutimediaInformationActivity.class);
                    break;
                case 0x200F:
                    intent = new Intent(MainActivity.this, RouteByConditionActivity.class);
                    break;
                case 0x2011:
                    intent = new Intent(MainActivity.this, ListAnimationActivity.class);
                    break;
                case 0x3000:
                    intent = new Intent(MainActivity.this, SearchActivity.class);
                    break;
                case 0xC201B:
                    intent = new Intent(MainActivity.this, Combined_GuessHomeAndNavi.class);
                    break;
                case 0xC3000:
                    intent = new Intent(MainActivity.this, Combined_SearchActivity.class);
                    break;
                case 0xC200F:
                    intent = new Intent(MainActivity.this, CalculationAndNaviActivity.class);
                    break;
                default:
                    intent = new Intent(MainActivity.this, OpActivity.class);
            }
            intent.putExtra("PageInfoBean", lists);
            intent.putExtra("lists_index", lists_index);
            startActivity(intent);
        }
    }

    /**
     * 服务端发送消息给客户端
     */
    protected void sendMessageFromServerToClient(int cmd,String json) {
        JSONObject cmdJson = new JSONObject();
        try {
            cmdJson.put(Constant.CMD_KEY, cmd);
            cmdJson.put(Constant.JSON_KEY, json);
            Resource.sendFromServerToClient(cmdJson.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送空消息
     */
    protected void sendMessage(int cmd) {
        JSONObject cmdJson = new JSONObject();
        try {
            cmdJson.put(Constant.CMD_KEY, cmd);
            cmdJson.put(Constant.JSON_KEY, "");
            sendMessage(cmdJson.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送消息
     *
     * @param message
     */
    protected void sendMessage(String message) {
        showSendDialog(message);
        if (Resource.device_model == ShareConfiguration.MODEL_CLIENT) {
            Resource.udpClient(message);
        } else {
            Resource.callAidlFun(message);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_clear:
                editText.setText("");
                break;
            case R.id.tv_change:
                Resource.changeWlan();
                break;
        }

    }

    /**
     * Called when a button is clicked (the button in the layout file attaches to
     * this method with the android:onClick attribute)
     */
//    public void onSend2NaviClick(View v) throws RemoteException {
//        if (mBind) {
//            // Call a method from the LocalService.
//            // However, if this call were something that might hang, then this request should
//            // occur in a separate thread to avoid slowing down the activity performance.
//            mNavService.request(0x2003, "{\"operationType\":1,\"screenId\":1}");
//            //Toast.makeText(this, "number: " + num, Toast.LENGTH_SHORT).show();
//        }
//    }
    private String readString(File cach_file) {
        int len = 0;
        StringBuffer str = new StringBuffer("");
        if (!cach_file.exists()) {
            Toast.makeText(MainActivity.this, "json文件不存在:", Toast.LENGTH_LONG).show();
            return null;
        }
        try {
            FileInputStream is = new FileInputStream(cach_file);
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader in = new BufferedReader(isr);
            String line = null;
            while ((line = in.readLine()) != null) {
                if (len != 0) {  // 处理换行符的问题
                    str.append("\r\n" + line);
                } else {
                    str.append(line);
                }
                len++;
            }
            in.close();
            is.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Log.e("hebaodan", e.getMessage());
            return null;
        }
        return str.toString();
    }

    //获取&&解析json文件
    public boolean parseFileInfo() {
        File cach_file = new File(getCacheDir(), FileUtils.fileName);
        if (!cach_file.exists()) {
            Log.i("hebaodan", "copy from asset to cache");
            FileUtils.copyFilesFromAssets(MainActivity.this, cach_file);
        }

        String strJson = readString(cach_file);
        if (strJson == null) {
            Log.i("hebaodan", "strJson is null ");
            return false;
        }
        Log.i("hebaodan", "strJson = " + strJson);
        if (Resource.pageInfoBean == null) {
            Resource.pageInfoBean = PageInfoBean.getPageInfoBeanList(strJson);
        }
        Log.i("hebaodan", "pageinfobean = " + Resource.pageInfoBean.getPageInfoBeanList(strJson));
        return true;
    }



    //检查申请权限
    private void checkPermission() {
        //检查权限（NEED_PERMISSION）是否被授权 PackageManager.PERMISSION_GRANTED表示同意授权
        if (!hasPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            //用户已经拒绝过一次，再次弹出权限申请对话框需要给用户一个解释
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission
                    .WRITE_EXTERNAL_STORAGE)) {
                Toast.makeText(this, "请开通相关权限，否则无法正常使用本应用！", Toast.LENGTH_SHORT).show();
            }
            //申请权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);

        } else {
            if (parseFileInfo()) {
                init();
            }

            Log.i("hebaodan", "checkPermission: 已经授权！");
        }
    }

    /**
     * 判断是否有某个权限
     *
     * @param context
     * @param permission
     * @return
     */
    public static boolean hasPermission(Context context, String permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { // Android 6.0判断，6.0以下跳过。在清单文件注册即可，不用动态请求，这里直接视为有权限
            if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "授权成功！", Toast.LENGTH_SHORT).show();
                Log.i("hebaodan_permission", "checkPermission: 读写权限已经授权！3333");
                if (parseFileInfo()) {
                    init();
                }
            }
        }
//        else if (requestCode == REQUEST_CODE) {
//            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                Toast.makeText(this, "授权成功！", Toast.LENGTH_SHORT).show();
//                Log.i("hebaodan_permission", "checkPermission: 安装权限已经授权！");
//                updateUtils.installApk();
//            }
//        }

    }
    private long firstTime = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        long secondTime = System.currentTimeMillis();
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (secondTime - firstTime < 2000) {
                if (Resource.pageInfoBean != null) {
                    Gson gson = new Gson();
                    FileUtils.writeFile(gson.toJson(Resource.pageInfoBean),getCacheDir(),FileUtils.fileName,false);
                }
                System.exit(0);
            } else {
                Toast.makeText(MainActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                firstTime = System.currentTimeMillis();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
