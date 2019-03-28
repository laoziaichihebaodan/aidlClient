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
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.fundrive.andrive.INavRemoteNotifier;
import com.fundrive.andrive.INavRemoteRequest;
import com.fundrive.navaidlclient.adapter.PageInfoAdapter;
import com.fundrive.navaidlclient.bean.CmdBean;
import com.fundrive.navaidlclient.bean.PageInfoBean;
import com.fundrive.navaidlclient.modules.AuthorNumberActivity;
import com.fundrive.navaidlclient.modules.ControlMutimediaActivity;
import com.fundrive.navaidlclient.modules.CustomMessageActivity;
import com.fundrive.navaidlclient.modules.FavoriteGuidanceActivity;
import com.fundrive.navaidlclient.modules.GetFavoritePointActivity;
import com.fundrive.navaidlclient.modules.GetPoiPageDataActivity;
import com.fundrive.navaidlclient.modules.GetPonitInfoActivity;
import com.fundrive.navaidlclient.modules.GuideSoundTypeActivity;
import com.fundrive.navaidlclient.modules.HelpInfoActivity;
import com.fundrive.navaidlclient.modules.InputActivity;
import com.fundrive.navaidlclient.modules.LanguageActivity;
import com.fundrive.navaidlclient.modules.ListAnimationActivity;
import com.fundrive.navaidlclient.modules.MapDisplayModeActivity;
import com.fundrive.navaidlclient.modules.MutimediaInformationActivity;
import com.fundrive.navaidlclient.modules.OpActivity;
import com.fundrive.navaidlclient.modules.PackInfoActivity;
import com.fundrive.navaidlclient.modules.RoutStateActivity;
import com.fundrive.navaidlclient.modules.RouteByConditionActivity;
import com.fundrive.navaidlclient.modules.RouteConditionActivity;
import com.fundrive.navaidlclient.modules.ScaleMapActivity;
import com.fundrive.navaidlclient.modules.SearchPoiByConditionActivity;
import com.fundrive.navaidlclient.modules.SelectPoiSearchCenter;
import com.fundrive.navaidlclient.modules.SelectRouteGuideActivity;
import com.fundrive.navaidlclient.modules.SetBoardcastModeActivity;
import com.fundrive.navaidlclient.modules.SetDisplayScreenActivity;
import com.fundrive.navaidlclient.modules.SetMuteActivity;
import com.fundrive.navaidlclient.modules.SetRouteViewModeActivity;
import com.fundrive.navaidlclient.modules.SetValumeActivity;
import com.fundrive.navaidlclient.modules.ShowHideActivity;
import com.fundrive.navaidlclient.modules.ShowTargetVolumeActivity;
import com.fundrive.navaidlclient.modules.SwitchMapViewActivity;
import com.fundrive.navaidlclient.modules.SwitchNavActivity;
import com.fundrive.navaidlclient.modules.SwitchWindowModeActivity;
import com.fundrive.navaidlclient.modules.TimeInfoActivity;
import com.fundrive.navaidlclient.modules.TmcActivity;
import com.fundrive.navaidlclient.modules.UpdateFavActivity;
import com.fundrive.navaidlclient.modules.WritingStateActivity;
import com.google.gson.internal.bind.ReflectiveTypeAdapterFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {

    INavRemoteRequest mNavService;
    boolean mBind = false;
    private ListView lv;
    private AutoCompleteTextView editText;
    private Button button;
//    private ArrayAdapter<PageInfoBean> adapter;
    private PageInfoAdapter adapter;
    private int REQUEST_CODE = 1;
    private String fileName = "data.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //检查读写权限
        checkPermission();
    }

    private void init(){
        lv = findViewById(R.id.lv);
        lv.setTextFilterEnabled(true);
        button = findViewById(R.id.btn_clear);
        editText = findViewById(R.id.et_content);
        findViewById(R.id.btn_return).setVisibility(View.GONE);
        button.setOnClickListener(this);
        adapter = new PageInfoAdapter(this,Resource.pageInfoBeans);
        lv.setAdapter(adapter);

//        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, Resource.pageInfoBeans);
        lv.setAdapter(adapter);
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
        startService(intent);
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
            System.out.println(ia_cmd);
            System.out.println("" + ia_json);
            Toast.makeText(MainActivity.this, "cmd = " + ia_cmd + "\n json = " + ia_json, Toast.LENGTH_LONG).show();
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

        if (((PageInfoBean)adapter.getItem(i)).getItem() == null){
            sendMessage(Integer.parseInt(((PageInfoBean)adapter.getItem(i)).getCmd(),16));
        } else {
            Intent intent = new Intent(MainActivity.this, OpActivity.class);
            intent.putExtra("PageInfoBean", (PageInfoBean)adapter.getItem(i));
            startActivity(intent);
        }

//        switch (bean.getCMD()) {
//            case Constant.IA_CMD_SET_AUTHORIZE_SERIAL_NUMBER:
//                startActivity(new Intent(this, AuthorNumberActivity.class));
//                break;
//            case Constant.IA_CMD_SET_MAP_DISPLAY_MODE:
//                startActivity(new Intent(this, MapDisplayModeActivity.class));
//                break;
//            case Constant.IA_CMD_SET_SOUND_VOLUME:
//                startActivity(new Intent(this, SetValumeActivity.class));
//                break;
//            case Constant.IA_CMD_SET_MUTE_SWITCH:
//                intent = new Intent(this, SetMuteActivity.class);
//                intent.putExtra("title", "设置静音");
//                intent.putExtra("key", "muteState");
//                intent.putExtra(Constant.CMD_KEY, Constant.IA_CMD_SET_MUTE_SWITCH);
//                startActivity(intent);
//                break;
//            case Constant.IA_CMD_SET_ROUTE_CONDITION:
//                startActivity(new Intent(this, RouteConditionActivity.class));
//                break;
//            case Constant.IA_CMD_SET_TIME_INFOMATION:
//                startActivity(new Intent(this, TimeInfoActivity.class));
//                break;
//            case Constant.IA_CMD_SET_TYPE_WRITING:
//                sendMessage(Constant.IA_CMD_SET_TYPE_WRITING);
//                break;
//            case Constant.IA_CMD_SET_LANGUAGE:
//                startActivity(new Intent(this, LanguageActivity.class));
//                break;
//            case Constant.IA_CMD_SET_MULTIMEDIA_INFOMATION:
//                startActivity(new Intent(this, MutimediaInformationActivity.class));
//                break;
//            case Constant.IA_CMD_SET_TBT_SWITCH_STATUS:
//                intent = new Intent(this, SetMuteActivity.class);
//                intent.putExtra("key", "state");
//                intent.putExtra("title", "导航信息发送开关");
//                intent.putExtra(Constant.CMD_KEY, Constant.IA_CMD_SET_TBT_SWITCH_STATUS);
//                startActivity(intent);
//                break;
//            case Constant.IA_CMD_SET_GUIDENCE_SOUND_TYPE:
//                startActivity(new Intent(this, GuideSoundTypeActivity.class));
//                break;
//            case Constant.IA_CMD_UPDATE_IATARGET_WRITING_STATUS:
//                startActivity(new Intent(this, WritingStateActivity.class));
//                break;
//            case Constant.IA_CMD_UPDATE_IATARGET_ROUTE_STATUS:
//                startActivity(new Intent(this, RoutStateActivity.class));
//                break;
//            case Constant.IA_CMD_SAVE_DATA:
//                sendMessage(Constant.IA_CMD_SAVE_DATA);
//                break;
//            case Constant.IA_CMD_SHOW_OR_HIDE:
//                startActivity(new Intent(this, ShowHideActivity.class));
//                break;
//            case Constant.IA_CMD_ZOOM_MAP:
//                startActivity(new Intent(this, ScaleMapActivity.class));
//                break;
//            case Constant.IA_CMD_GET_NAVI_INFO:
//                sendMessage(Constant.IA_CMD_GET_NAVI_INFO);
//                break;
//            case Constant.IA_CMD_REPEAT_NAVI_SOUND:
//                sendMessage(Constant.IA_CMD_REPEAT_NAVI_SOUND);
//                break;
//            case Constant.IA_CMD_MOVE_UI:
//                sendMessage(Constant.IA_CMD_MOVE_UI);
//                break;
//            case Constant.IA_CMD_CHANGE_NAVI_UI_VISUAL_ATTRIBUTES:
//                sendMessage(Constant.IA_CMD_CHANGE_NAVI_UI_VISUAL_ATTRIBUTES);
//                break;
//            case Constant.IA_CMD_SHOW_DANAMIC_INFOMATION:
//                sendMessage(Constant.IA_CMD_SHOW_DANAMIC_INFOMATION);
//                break;
//            case Constant.IA_CMD_SART_OR_STOP_NAVI_GUIDE:
//                startActivity(new Intent(this, SwitchNavActivity.class));
//                break;
//            case Constant.IA_CMD_GET_NAVI_STATUAS:
//                sendMessage(Constant.IA_CMD_GET_NAVI_STATUAS);
//                break;
//            case Constant.IA_CMD_SHOW_TARGET_SOUND_VOLUME:
//                startActivity(new Intent(this, ShowTargetVolumeActivity.class));
//                break;
//            case Constant.IA_CMD_DELET_NAVI_ROUTE:
//                sendMessage(Constant.IA_CMD_DELET_NAVI_ROUTE);
//                break;
//            case Constant.IA_CMD_GET_GPS_INFO:
//                sendMessage(Constant.IA_CMD_GET_GPS_INFO);
//                break;
//            case Constant.IA_CMD_BATTERY_LOW:
//                sendMessage(Constant.IA_CMD_BATTERY_LOW);
//                break;
//            case Constant.IA_CMD_SET_MAP_VIEW_MODE:
//                startActivity(new Intent(this, SwitchMapViewActivity.class));
//                break;
//            case Constant.IA_CMD_ROUTE_BY_CONDITION:
//                startActivity(new Intent(this, RouteByConditionActivity.class));
//                break;
//            case Constant.IA_CMD_SET_DISPLAY_SCREEN_FOR_NAVAPP:
//                startActivity(new Intent(this, SetDisplayScreenActivity.class));
//                break;
//            case Constant.IA_CMD_CURRENT_UI_LIST_ANIMATION:
//                startActivity(new Intent(this, ListAnimationActivity.class));
//                break;
//            case Constant.IA_CMD_FAVORITE_GUIDANCE:
//                startActivity(new Intent(this, FavoriteGuidanceActivity.class));
//                break;
//            case Constant.IA_CMD_LOCATE_THE_CAR:
//                sendMessage(Constant.IA_CMD_LOCATE_THE_CAR);
//                break;
//            case Constant.IA_CMD_NAVAPP_CONTROL_MULTIMEDIA:
//                startActivity(new Intent(this, ControlMutimediaActivity.class));
//                break;
//            case Constant.IA_CMD_BROWSE_HELP_INFORMATION:
//                startActivity(new Intent(this, HelpInfoActivity.class));
//                break;
//            case Constant.IA_CMD_START_GUIDING_WITH_ROUTE:
//                startActivity(new Intent(this, SelectRouteGuideActivity.class));
//                break;
//            case Constant.IA_CMD_CHANGE_NAVAPP_WINDOW_MODE:
//                startActivity(new Intent(this, SwitchWindowModeActivity.class));
//                break;
//            case Constant.IA_CMD_GET_NAVAPP_WINDOW_MODE:
//                sendMessage(Constant.IA_CMD_GET_NAVAPP_WINDOW_MODE);
//                break;
//            case Constant.IA_CMD_GET_FAVORITE_POINT:
//                startActivity(new Intent(this, GetFavoritePointActivity.class));
//                break;
//            case Constant.IA_CMD_UPDATE_FAVORITE_POINT:
//                startActivity(new Intent(this, UpdateFavActivity.class));
//                break;
//            case Constant.IA_CMD_UPDATE_FAVORITE_POINT_AND_GUESS:
//                intent = new Intent(this, UpdateFavActivity.class);
//                intent.putExtra("cmd", Constant.IA_CMD_UPDATE_FAVORITE_POINT_AND_GUESS);
//                intent.putExtra("title","猜测家和公司");
//                startActivity(intent);
//                break;
//            case Constant.IA_CMD_UPDATE_FAVORITE_POINT_AND_NAVI:
//                intent = new Intent(this, UpdateFavActivity.class);
//                intent.putExtra("cmd", Constant.IA_CMD_UPDATE_FAVORITE_POINT_AND_NAVI);
//                intent.putExtra("title","更新收藏并导航");
//                startActivity(intent);
//                break;
//            case Constant.IA_CMD_UPDATE_KEYBOARD_INPUT:
//                startActivity(new Intent(this, InputActivity.class));
//                break;
//            case Constant.IA_CMD_SEARCH_POI_BY_CONDITION:
//                startActivity(new Intent(this, SearchPoiByConditionActivity.class));
//                break;
//            case Constant.IA_CMD_SELECT_POI_SEARCH_CENTER:
//                startActivity(new Intent(this, SelectPoiSearchCenter.class));
//                break;
//            case Constant.IA_CMD_GET_POI_PAGE_DATA:
//                startActivity(new Intent(this, GetPoiPageDataActivity.class));
//                break;
//            case Constant.IA_CMD_GET_SPECIFIC_POINT_INFO:
//                startActivity(new Intent(this, GetPonitInfoActivity.class));
//                break;
//            case Constant.IA_CMD_ENABLE_SPEEDLIMIT_WARNING:
//                intent = new Intent(this, SetMuteActivity.class);
//                intent.putExtra("title", "开启/关闭超速提醒");
//                intent.putExtra("key", "enable");
//                intent.putExtra(Constant.CMD_KEY, Constant.IA_CMD_ENABLE_SPEEDLIMIT_WARNING);
//                startActivity(intent);
//                break;
//            case Constant.IA_CMD_ENABLE_CAMERA_WARNING:
//                intent = new Intent(this, SetMuteActivity.class);
//                intent.putExtra("title", "开启/关闭电子警察");
//                intent.putExtra("key", "enable");
//                intent.putExtra(Constant.CMD_KEY, Constant.IA_CMD_ENABLE_CAMERA_WARNING);
//                startActivity(intent);
//                break;
//            case Constant.IA_CMD_ENABLE_TMC:
//                intent = new Intent(this, SetMuteActivity.class);
//                intent.putExtra("title", "开启/关闭实时路况");
//                intent.putExtra("key", "enable");
//                intent.putExtra(Constant.CMD_KEY, Constant.IA_CMD_ENABLE_TMC);
//                startActivity(intent);
//                break;
//            case Constant.IA_CMD_SET_SOUND_CRUISE:
//                intent = new Intent(this, SetMuteActivity.class);
//                intent.putExtra("title", "开启/关闭巡航播报");
//                intent.putExtra("key", "enable");
//                intent.putExtra(Constant.CMD_KEY, Constant.IA_CMD_SET_SOUND_CRUISE);
//                startActivity(intent);
//                break;
//            case Constant.IA_CMD_SET_ROUTE_VIEW_MODE:
//                startActivity(new Intent(this, SetRouteViewModeActivity.class));
//                break;
//            case Constant.IA_CMD_SET_BOARDCAST_MODE:
//                startActivity(new Intent(this, SetBoardcastModeActivity.class));
//                break;
//            case Constant.IA_CMD_GET_REMAINING_ROUTEINFO:
//                sendMessage(Constant.IA_CMD_GET_REMAINING_ROUTEINFO);
//                break;
//            case Constant.IA_CMD_ENABLE_AVOID_RESTRICTION_ROADS:
//                intent = new Intent(this, SetMuteActivity.class);
//                intent.putExtra("title", "开启/关闭避开限行道路功能");
//                intent.putExtra("key", "enable");
//                intent.putExtra(Constant.CMD_KEY, Constant.IA_CMD_ENABLE_AVOID_RESTRICTION_ROADS);
//                startActivity(intent);
//                break;
//            case Constant.IA_CMD_TMC_BROADCAST:
//                startActivity(new Intent(this, TmcActivity.class));
//                break;
//            case 0:
//                startActivity(new Intent(this, CustomMessageActivity.class));
//                break;
//            case Constant.IA_CMD_GO_PARKING_INFO:
//                startActivity(new Intent(this, PackInfoActivity.class));
//                break;
//        }

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
        if (Resource.device_model == ShareConfiguration.MODEL_CLIENT) {
            Resource.udpClient(message);
        } else {
            Resource.callAidlFun(message);
        }
    }

    @Override
    public void onClick(View v) {
        editText.setText("");
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
    private String readString(File parent, String name) {
        int len = 0;
        StringBuffer str = new StringBuffer("");
        File file = new File(parent, name);
        if (!file.exists()) {
            Toast.makeText(MainActivity.this, "本地json文件不存在", Toast.LENGTH_LONG).show();
            return null;
        }
        try {
            FileInputStream is = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader in = new BufferedReader(isr);
            String line = null;
            while ((line = in.readLine()) != null) {
                if (len != 0){  // 处理换行符的问题
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
        //判断sd卡是否存在
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(android.os.Environment.MEDIA_MOUNTED);
        Log.i("hebaodan", "sd卡是否存在 = " + sdCardExist);
        if (sdCardExist) {
            File sdDir = Environment.getExternalStorageDirectory();//获取根目录
            String strJson = readString(sdDir, fileName);
            if (strJson == null) {
                return false;
            }
            Log.i("hebaodan", "strJson = " + strJson);
            Resource.pageInfoBeans = PageInfoBean.getPageInfoBeanList(strJson);
            Log.i("hebaodan", "pageinfobean = " + PageInfoBean.getPageInfoBeanList(strJson));
            return true;
        }
        return false;
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
                if (parseFileInfo()) {
                    init();
                }

            }
        }

    }
}
