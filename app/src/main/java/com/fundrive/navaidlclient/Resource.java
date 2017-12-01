package com.fundrive.navaidlclient;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.fundrive.andrive.INavRemoteRequest;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wangjianghua on 2017/8/31.
 */

public class Resource {
    private  static final int PORT = 8888;
    private static final int CONNECTED_SEVER_STATE = 1; //连接服务器状态
    private static final int RECIVEDATA_STATE = 2;      //接收数据
    private static EditText meditText = null;
    public  static String data = null;
    private static boolean StartServer = false;
    private static ShareConfiguration mShareConfiguration = null;
    public  static int device_model = 0;
    private static String serverIpaddress = null;
    public  static String[] strArr = {
            "设置NavApp授权序列号",
            "设置NavApp地图显示模式：白天",
            "设置NavApp地图显示模式：黑夜",
            "设置NavApp音量",
            "设置NavApp静音开",
            "设置NavApp静音关",
            "设置NavApp算路避让规则",
            "设置NavApp的时间信息",
            "设置NavApp的输入法",
            "设置NavApp的语言",
            "设置NavApp的多媒体信息",
            "设置NavApp导航信息发送开启",
            "设置NavApp导航信息发送关闭",
            "设置NavApp导航播报语音类型(随机)",
            "更新交互目标的写状态(随机)",
            "更新交互目标的路径状态(随机)",
            "启动导航程序",
            "退出导航程序",
            "保存NavApp数据",
            "显示NavApp",
            "隐藏NavApp",
            "缩小地图",
            "放大地图",
            "获取NavApp的导航引导信息",
            "重播当前导航语音提示语句",
            "画面迁移",
            "改变NavApp的UI视觉属性",
            "NavApp显示动态信息",
            "开始导航",
            "停止导航",
            "获取NavApp的当前状态",
            "NavApp显示交互目标的音量(随机)",
            "删除NavApp的当前路线",
            "获取GPS信息",
            "切换地图视图模式(随机)",
            "条件算路",
            "设置NavApp画面所的在显示屏幕",
            "条件搜索POI"
    };

    public static void showInfo(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
    public static Context ctx;
    static INavRemoteRequest NavService;
    static boolean bind;
    public static void init(Context context,INavRemoteRequest mNavService,boolean mBind) {
        ctx = context;
        NavService = mNavService;
        bind = mBind;
        mShareConfiguration = new ShareConfiguration(ctx, ShareConfiguration.SETTING_INFOS);
        initWlan();
    }

    private static MyHandler myHandler = new MyHandler();
    public static class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what)
            {
                case CONNECTED_SEVER_STATE:
                    switch (msg.arg1){
                        case 0:Resource.showInfo(ctx,"未找到服务器.");break;
                        case 1:Resource.showInfo(ctx,"服务端连接失败.");break;
                        case 2:Resource.showInfo(ctx,"发送消息成功");break;
                        case 3:Resource.showInfo(ctx,"发送消息失败");break;
                        default:break;
                    }
                    break;
                case RECIVEDATA_STATE:
                    String data = msg.obj.toString();
                    Resource.showInfo(ctx,data);
                    Resource.sendBroad(ctx,Integer.parseInt(data));
                    break;
                default: break;
            }
        }
    }
    public static void udpClient() {
        if(serverIpaddress == null || serverIpaddress == "") {
            showInfo(ctx,"IP地址不正确.");
            return;
        }
        new Thread() {
            @Override
            public void run() {
                Message msg = new Message();
                InetAddress addr = null;
                DatagramSocket dsocket = null;
                try{
                    addr = InetAddress.getByName(serverIpaddress);
                }catch (UnknownHostException e){
                    msg.what = CONNECTED_SEVER_STATE;
                    msg.arg1 = 0;
                    myHandler.sendMessage(msg);
                    e.printStackTrace();
                }
                try{
                    dsocket = new DatagramSocket();
                }catch (SocketException e) {
                    e.printStackTrace();
                    msg.what = CONNECTED_SEVER_STATE;
                    msg.arg1 = 1;
                    myHandler.sendMessage(msg);
                }
                DatagramPacket dpacket = new DatagramPacket(data.getBytes(),data.length(), addr,PORT);
                try{
                    dsocket.send(dpacket);
                    msg.what = CONNECTED_SEVER_STATE;
                    msg.arg1 = 2;
                    myHandler.sendMessage(msg);
                }catch (IOException e){
                    e.printStackTrace();
                    msg.what = CONNECTED_SEVER_STATE;
                    msg.arg1 = 3;
                    myHandler.sendMessage(msg);
                }
                dsocket.close();
            }
        }.start();
    }

    public static void sendBroad(Context context,int position) {

        int intType = 0x2003;
        String strJson = null;
        strJson = "{\"operationType\":1,\"screenId\":1}";
//        switch (position) {
//            case 0://导航Active
//                intType = NaviConstant.DATA_TYPE_STRING;
//                strType = NaviConstant.Active;
//                break;
//            case 1://停止模拟导航
//                intContent = 0;
//                intType = NaviConstant.DATA_TYPE_INT;
//                strType = NaviConstant.SIMULATION_NAVIGATION;
//                break;
//            case 2://开始模拟导航
//                intContent = 1;
//                intType = NaviConstant.DATA_TYPE_INT;
//                strType = NaviConstant.SIMULATION_NAVIGATION;
//                break;
//            case 3://通知退出导航
//                intType = NaviConstant.DATA_TYPE_STRING;
//                strType = NaviConstant.EXIT_NAVIGATION;
//                break;
//            case 4://隐藏当前导航画面
//                intType = NaviConstant.DATA_TYPE_STRING;
//                strType = NaviConstant.HIDE_VIEW;
//                break;
//            case 5://点击导航按钮后，显示导航画面
//                intType = NaviConstant.DATA_TYPE_STRING;
//                strType = NaviConstant.SHOW_VIEW;
//                break;
//            case 6://白天
//                strContent = "0";
//                intType = NaviConstant.DATA_TYPE_STRING;
//                strType = NaviConstant.DAY_NIGHT;
//                break;
//            case 7://黑夜
//                strContent = "1";
//                intType = NaviConstant.DATA_TYPE_STRING;
//                strType = NaviConstant.DAY_NIGHT;
//                break;
//            case 8://中文
//                strContent = "0";
//                intType = NaviConstant.DATA_TYPE_STRING;
//                strType = NaviConstant.LANGUAGE;
//                break;
//            case 9://英文
//                strContent = "1";
//                intType = NaviConstant.DATA_TYPE_STRING;
//                strType = NaviConstant.LANGUAGE;
//                break;
//            case 10://TBT提示开
//                intContent = 1;
//                intType = NaviConstant.DATA_TYPE_INT;
//                strType = NaviConstant.TBT;
//                break;
//            case 11://TBT提示关
//                intContent = 0;
//                intType = NaviConstant.DATA_TYPE_INT;
//                strType = NaviConstant.SDCARD_STATU;
//                break;
//            case 12://SD存在状态  0:不存在
//                intContent = 0;
//                intType = NaviConstant.DATA_TYPE_INT;
//                strType = NaviConstant.SDCARD_STATU;
//                break;
//            case 13://SD存在状态  1:存在
//                intContent = 1;
//                intType = NaviConstant.DATA_TYPE_INT;
//                strType = NaviConstant.SDCARD_STATU;
//                break;
//            case 14://通知导航保存数据
//                intType = NaviConstant.DATA_TYPE_STRING;
//                strType = NaviConstant.SAVE_DATA;
//                break;
//            case 15://时间格式12小时
//                intContent = 12;
//                intType = NaviConstant.DATA_TYPE_INT;
//                strType = NaviConstant.TIME_FORMAT;
//                break;
//            case 16://时间格式24小时
//                intContent = 24;
//                intType = NaviConstant.DATA_TYPE_INT;
//                strType = NaviConstant.TIME_FORMAT;
//                break;
//            case 17://获取GPS时间
//                strContent = "24";
//                intType = NaviConstant.DATA_TYPE_STRING;
//                strType = NaviConstant.REQUEST_SYSTIME;
//                break;
//            case 18://系统音量值
//                strContent = "24";
//                intType = NaviConstant.DATA_TYPE_STRING;
//                strType = NaviConstant.SET_SYSTEM_VOLUME;
//                break;
//            case 19://导航音量值
//                strContent = "10";
//                intType = NaviConstant.DATA_TYPE_STRING;
//                strType = NaviConstant.SET_NAVI_VOLUME;
//                break;
//            case 20://导航静音
//                intContent = 1;
//                intType = NaviConstant.DATA_TYPE_INT;
//                strType = NaviConstant.SET_NAVI_MUTE;
//                break;
//            case 21://导航关闭静音
//                intContent = 0;
//                intType = NaviConstant.DATA_TYPE_INT;
//                strType = NaviConstant.SET_NAVI_MUTE;
//                break;
//            case 22://
//                strContent = "中文测试";
//                intType = NaviConstant.DATA_TYPE_STRING;
//                strType = NaviConstant.BLUETOOTH_CALL;
//                break;
//            case 23://音频源切换/音频源不可用
//                intContent = 0;
//                intContent1 = 0;
//                len = 2;
//                intType = NaviConstant.DATA_TYPE_STRUCT;
//                strType = NaviConstant.SET_AUDIO_SOURCE;
//                break;
//            case 24://FM使用当地交通台
//                intContent = 3;
//                intContent1 = 998;
//                intContent2 = 5;
//                len = 3;
//                intType = NaviConstant.DATA_TYPE_STRUCT;
//                strType = NaviConstant.FM_DATA_TO;
//                break;
//            case 25://"USB多媒体曲目信息
//                strContent = "黄梅戏";
//                intType = NaviConstant.DATA_TYPE_STRING;
//                strType = NaviConstant.USB_RECORD;
//                break;
//            case 26://Bluetooth曲目信息
//                strContent = "中国好歌曲";
//                intType = NaviConstant.DATA_TYPE_STRING;
//                strType = NaviConstant.BLUETOOTH_RECORD;
//                break;
//            case 27://Bluetooth当蓝牙通话中，通知导航静音
//                strContent = "1";
//                intType = NaviConstant.DATA_TYPE_STRING;
//                strType = NaviConstant.BLUETOOTH_CALL;
//                break;
//            case 28://Bluetooth当蓝牙通话结束，通知导航开启声音
//                strContent = "0";
//                intType = NaviConstant.DATA_TYPE_STRING;
//                strType = NaviConstant.BLUETOOTH_CALL;
//                break;
//            case 29://音频源切换0:默认
//                intContent = 1;
//                intContent1 = 0;
//                len = 2;
//                intType = NaviConstant.DATA_TYPE_STRUCT;
//                strType = NaviConstant.SET_AUDIO_SOURCE;
//                break;
//            case 30://音频源切换1：FM
//                intContent = 1;
//                intContent1 = 1;
//                len = 2;
//                intType = NaviConstant.DATA_TYPE_STRUCT;
//                strType = NaviConstant.SET_AUDIO_SOURCE;
//                break;
//            case 31://音频源切换2:USB(MusicPlay)
//                intContent = 1;
//                intContent1 = 2;
//                len = 2;
//                intType = NaviConstant.DATA_TYPE_STRUCT;
//                strType = NaviConstant.SET_AUDIO_SOURCE;
//                break;
//            case 32://音频源切换4：BTCallPhone
//                intContent = 1;
//                intContent1 = 4;
//                len = 2;
//                intType = NaviConstant.DATA_TYPE_STRUCT;
//                strType = NaviConstant.SET_AUDIO_SOURCE;
//                break;
//            case 33://音频源切换5:BTMUSIC
//                intContent = 1;
//                intContent1 = 5;
//                len = 2;
//                intType = NaviConstant.DATA_TYPE_STRUCT;
//                strType = NaviConstant.SET_AUDIO_SOURCE;
//                break;
//            case 34://音频源切换7:HDMI
//                intContent = 1;
//                intContent1 = 7;
//                len = 2;
//                intType = NaviConstant.DATA_TYPE_STRUCT;
//                strType = NaviConstant.SET_AUDIO_SOURCE;
//                break;
//            case 35://音频源切换8:AUX
//                intContent = 1;
//                intContent1 = 8;
//                len = 2;
//                intType = NaviConstant.DATA_TYPE_STRUCT;
//                strType = NaviConstant.SET_AUDIO_SOURCE;
//                break;
//            case 36://启动导航
//                if (true) {
//                    Intent m_intent = new Intent();
//                    m_intent.setComponent(new ComponentName("com.fundrive.andrive", "com.fundrive.andrive.FullscreenActivity"));
//                    m_intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    context.startActivity(m_intent);
//                    return;
//                }
//                break;
//        }
//        intent.putExtra("CODE", strType);
//        intent.putExtra("TYPE", intType);
//        if (intType == NaviConstant.DATA_TYPE_INT) {
//            bundle.putInt("DATA",intContent);
//        } else if  (intType == NaviConstant.DATA_TYPE_STRING){
//            bundle.putString("DATA",strContent);
//        } else  if  (intType == NaviConstant.DATA_TYPE_STRUCT){//语音命令
//            if (len >= 2) {
//
//                bundle.putInt("DATA",intContent);
//                bundle.putInt("DATA1",intContent1);
//            }
//            if(len >= 3) {
//                bundle.putInt("DATA2",intContent2);
//            }
//        }
        if(bind) {
            try {
                NavService.request(intType,strJson);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    private static void initWlan() {
        device_model = mShareConfiguration.getDeviceModel();
        switch (device_model) {
            case ShareConfiguration.MODEL_NONE_KOWN:
                ModelChooseDialog();
                break;
            case ShareConfiguration.MODEL_CLIENT:
                getIpaddress();
                break;
            case ShareConfiguration.MODEL_SERVER:
                StartServer = true;
                startUdpServer();
                break;
            default: break;
        }
    }

    private static void ModelChooseDialog(){
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final AlertDialog.Builder ModelChooseDialog =
                new AlertDialog.Builder(ctx);
        ModelChooseDialog.setTitle("软件模式选择");
        ModelChooseDialog.setMessage("选择客户端还是服务端模式?");
        ModelChooseDialog.setPositiveButton("服务端",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        mShareConfiguration.setDeviceModel(device_model = ShareConfiguration.MODEL_SERVER);
                        StartServer = true;
                        startUdpServer();
                    }
                });
        ModelChooseDialog.setNegativeButton("客户端",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mShareConfiguration.setDeviceModel(device_model = ShareConfiguration.MODEL_CLIENT);
                        getIpaddress();
                    }
                });
        // 显示
        ModelChooseDialog.show();
    }
    private static void startUdpServer() {
        new Thread(){
            @Override
            public void run() {
                DatagramSocket ds = null;
                byte[] buf = new byte[1024];
                try {
                    ds = new DatagramSocket(PORT);
                }catch (SocketException e) {
                    e.printStackTrace();
                }
                while(StartServer) {
                    DatagramPacket dp = new DatagramPacket(buf,1024);
                    try {
                        ds.receive(dp);
                        String data = new String(dp.getData(), 0, dp.getLength());
                        if (data != null || data != "") {
                            Message msg = new Message();
                            msg.what = RECIVEDATA_STATE;
                            msg.obj = data;
                            myHandler.sendMessage(msg);
                        }
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    private static void getIpaddress()
    {
        meditText = new EditText(ctx);
        SharedPreferences sp = ctx.getSharedPreferences("data",Activity.MODE_PRIVATE);
        String ip =sp.getString("ip","");
        meditText.setText(ip);
        meditText.setSelection(ip.length());
        final AlertDialog dialog = new AlertDialog.Builder(ctx).setTitle("输入IP地址")
                .setIcon(android.R.drawable.ic_dialog_info)
                .setView(meditText)
                .setCancelable(false)
                .setPositiveButton("确定", null)
                .show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String input = meditText.getText().toString();
                if (input.equals("")) {
                    Toast.makeText(ctx, "IP地址不能为空！", Toast.LENGTH_LONG).show();
                    return;
                }
                else if(!isIP(input)){
                    Toast.makeText(ctx, "IP地址不正确！" , Toast.LENGTH_LONG).show();
                    return;
                }
                else{
                    serverIpaddress = input;
                    SharedPreferences sp = ctx.getSharedPreferences("data",Activity.MODE_PRIVATE);
                    sp.edit().putString("ip",input).commit();
                }
                dialog.dismiss();
            }
        });
    }

    private static boolean isIP(String addr)
    {
        if(addr.length() < 7 || addr.length() > 15 || "".equals(addr))
        {
            return false;
        }
        /**
         * 判断IP格式和范围
         */
        String rexp = "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}";

        Pattern pat = Pattern.compile(rexp);

        Matcher mat = pat.matcher(addr);

        boolean ipAddress = mat.find();

        return ipAddress;
    }

}
