package com.fundrive.navaidlclient.utils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fundrive.navaidlclient.MainActivity;



public class VersionUpdateUtils {

    private final String TAG = this.getClass().getName();
    private final int UPDATA_NONEED = 0;
    private final int UPDATA_CLIENT = 1;
    private final int GET_UNDATAINFO_ERROR = 2;
    private final int SDCARD_NOMOUNTED = 3;
    private final int DOWN_ERROR = 4;
    private Button getVersion;
    //	private UpdataInfo info;
    private String localVersion;
    private String updateurl = "http://203.195.167.189:8080/v1/apk/RkD0OUjiZCLqYUTsbAWu";
    private Activity context;
    private File file;


    public VersionUpdateUtils(Activity context) {
        // TODO Auto-generated constructor stub
        this.context = context;
    }

    public void requestDialog(){
        Message msg = new Message();
        msg.what = UPDATA_CLIENT;
        handler.sendMessage(msg);
    }

    public void installApk(){
//        File file = new File(Environment.getExternalStorageDirectory(), "app-release.apk");
        installApk(file);
    }

    public void checkVersionUpdate(){
        String url = "http://203.195.167.189:8080/v1/client/checkAppVersion2";
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest request = new StringRequest(Method.POST, url, new Listener<String>() {
            @Override
            public void onResponse(String arg0) {
                // TODO Auto-generated method stub
                Log.i("hebaodan","更新json = "+arg0);
                try {
                    JSONObject object = new JSONObject(arg0);
                    JSONObject object1 = object.getJSONObject("data");
                    boolean isUpdate= object1.getBoolean("update");
                    if (isUpdate){
                        Message msg = new Message();
                        msg.what = UPDATA_CLIENT;
                        handler.sendMessage(msg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError arg0) {
                // TODO Auto-generated method stub
                Toast.makeText(context, "获取服务器更新信息失败", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> map = new HashMap<>();
                try {
                    localVersion = getVersionName();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                map.put("projectName", "NavAidlClient");
                map.put("curVersion", localVersion);
                return map;
            }
        };
        queue.add(request);
        queue.cancelAll(request);
    }

    /**
     * 获取versionName
     * @return
     * @throws Exception
     */
    private String getVersionName() throws Exception {
        //getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(),
                0);
        return packInfo.versionName;
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            switch (msg.what) {
                case UPDATA_NONEED:
//				Toast.makeText(getApplicationContext(), "不需要更新",
//						Toast.LENGTH_SHORT).show();
                case UPDATA_CLIENT:
                    //对话框通知用户升级程序
                    showUpdataDialog();
                    break;
                case GET_UNDATAINFO_ERROR:
                    //服务器超时
                    Toast.makeText(context, "获取服务器更新信息失败", Toast.LENGTH_LONG).show();
                    break;
                case DOWN_ERROR:
                    //下载apk失败
                    Toast.makeText(context, "下载新版本失败", Toast.LENGTH_LONG).show();
                    break;
            }
        }
    };
    /*
     *
     * 弹出对话框通知用户更新程序
     *
     * 弹出对话框的步骤：
     *  1.创建alertDialog的builder.
     *  2.要给builder设置属性, 对话框的内容,样式,按钮
     *  3.通过builder 创建一个对话框
     *  4.对话框show()出来
     */
    protected void showUpdataDialog() {
        AlertDialog.Builder builer = new Builder(context);
        builer.setTitle("版本升级");
        builer.setMessage("检测到新版本，是否升级到新版本");
        //当点确定按钮时从服务器上下载 新的apk 然后安装   װ
        builer.setPositiveButton("立即升级", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Log.i(TAG, "下载apk,更新");
                downLoadApk();
            }
        });
		builer.setNegativeButton("下次再说", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				//do sth
			}
		});
        AlertDialog dialog = builer.create();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }
    /*
     * 从服务器中下载APK
     */
    protected void downLoadApk() {
        final ProgressDialog pd;    //进度条对话框
        pd = new  ProgressDialog(context);
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.setMessage("正在下载更新");
        pd.setCancelable(false);
        pd.setCanceledOnTouchOutside(false);
        pd.show();
        new Thread(){
            @Override
            public void run() {
                try {
                    Log.i("hebaodan", "updateurl =="+updateurl);
                    //下载apk
                    file = DownLoadManager.getFileFromServer(updateurl, pd);
                    pd.dismiss(); //结束掉进度条对话框
                    //版本适配和安装
                    checkInstallPermission();

                } catch (Exception e) {
                    pd.dismiss();
                    Message msg = new Message();
                    msg.what = DOWN_ERROR;
                    handler.sendMessage(msg);
                    e.printStackTrace();
                    Log.i("hebaodan",e.getMessage());
                }
            }}.start();
    }

    public void checkInstallPermission() {
        // 兼容Android 8.0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            //先获取是否有安装未知来源应用的权限
            boolean haveInstallPermission = context.getPackageManager().canRequestPackageInstalls();
            if (!haveInstallPermission) {//没有权限
                // 弹窗，并去设置页面授权
                startInstallPermissionSettingActivity();
//                ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.REQUEST_INSTALL_PACKAGES}, MainActivity.INSTALL_PACKAGES_REQUESTCODE);
            } else {
                installApk();
            }
        } else {
            installApk();
        }
    }
    /**
     * 打开未知应用界面
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void startInstallPermissionSettingActivity() {

            context.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    AlertDialog.Builder builer = new Builder(context);
                    builer.setTitle("权限申请");
                    builer.setMessage("需要打开未知应用安装权限才能完成安装！");
                    //当点确定按钮时从服务器上下载 新的apk 然后安装   װ
                    builer.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Log.i(TAG, "下载apk,更新");
                            Uri packageURI = Uri.parse("package:" + context.getPackageName());
                            Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, packageURI);
                            context.startActivityForResult(intent, MainActivity.REQUEST_INSTALL);
                            dialog.dismiss();
                        }
                    });
                    builer.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO Auto-generated method stub
                            //do sth
                        }
                    });
                    AlertDialog dialog = builer.create();
                    dialog.setCancelable(false);
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.show();
                }
            });
    }

    //安装apk
    protected void installApk(File file) {
        Intent intent = new Intent();
        //执行动作
        intent.setAction(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri;
        //判断版本是否是 7.0 及 7.0 以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            uri = FileProvider.getUriForFile(context,
                    "fd_aidl_client",
                    file);
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        } else {
            uri = Uri.fromFile(file);
        }



        //执行的数据类型
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        context.startActivity(intent);
        FileUtils.deleteFile(context.getCacheDir()+"/"+ FileUtils.fileName);
    }

}
