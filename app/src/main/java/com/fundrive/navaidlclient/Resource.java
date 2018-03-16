package com.fundrive.navaidlclient;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
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
import java.util.Random;
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
            "设置NavApp的时间信息12",
            "设置NavApp的时间信息24",
            "设置NavApp的输入法",
            "设置NavApp的语言中文",
            "设置NavApp的语言英文",
            "设置NavApp的多媒体信息蓝牙",
            "设置NavApp的多媒体信息FM",
            "设置NavApp导航信息发送/开启",
            "设置NavApp导航信息发送/关闭",
            "设置NavApp导航播报语音类型(随机)",
            "更新交互目标的写状态(随机)",
            "更新交互目标的路径状态(随机)",
            "启动导航程序",
            "退出导航程序",
            "保存NavApp数据",
            "显示NavApp",
            "隐藏NavApp",
            "缩小第二个屏幕地图",
            "放大第一个屏幕地图",
            "获取NavApp的导航引导信息",
            "重播当前导航语音提示语句",
            "画面迁移",
            "改变NavApp的UI视觉属性",
            "NavApp显示动态信息",
            "开始真实导航",
            "停止真实导航",
            "开始模拟导航",
            "停止模拟导航",
            "获取NavApp的当前状态",
            "NavApp显示交互目标的音量(随机)",
            "删除NavApp的当前路线",
            "获取GPS信息",
            "切换地图视图模式(第一个屏幕3D)",
            "切换地图视图模式(第一个屏幕2D)",
            "切换地图视图模式(第二个屏幕3D)",
            "切换地图视图模式(第二个屏幕2D)",
            "条件算路",
            "设置NavApp画面所的在显示屏幕(默认仪表盘)",
            "List动画操作",
            "导航到家",
            "导航到收藏列表中的第10个收藏点",
            "定位自车",
            "打开蓝牙音乐",
            "关闭视频",
            "查看如何使用NavApp",
            "查看如何播放音乐",
            "条件搜索POI"
    };

    public static void showInfo(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
    public static Context ctx;
    static INavRemoteRequest navService;
    static boolean bind;
    public static void init(Context context) {
        ctx = context;
        mShareConfiguration = new ShareConfiguration(ctx, ShareConfiguration.SETTING_INFOS);
        initWlan();
    }
    public static void initRequest(INavRemoteRequest mNavService,boolean mBind) {
        navService = mNavService;
        bind = mBind;
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
                    Resource.callAidlFun(Integer.parseInt(data));
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

    public static void callAidlFun(int position) {
        if (navService == null) {
            Log.e("wjh","navService == null");
            return;
        }
        Random random = new Random();
        int intType = -1;
        String strJson = null;
        int randomInt ;
        String randomStr ;
        switch (position) {
            case 0://设置NavApp授权序列号
                intType = Constant.IA_CMD_SET_AUTHORIZE_SERIAL_NUMBER;
                strJson = Constant.IA_CMD_SET_AUTHORIZE_SERIAL_NUMBER_CONTENT;
                break;
            case 1://白天
                intType = Constant.IA_CMD_SET_MAP_DISPLAY_MODE;
                strJson = Constant.IA_CMD_SET_MAP_DISPLAY_MODE_DAY;
                break;
            case 2://黑夜
                intType = Constant.IA_CMD_SET_MAP_DISPLAY_MODE;
                strJson = Constant.IA_CMD_SET_MAP_DISPLAY_MODE_NIGHT;
                break;
            case 3://设置NavApp音量
                intType = Constant.IA_CMD_SET_SOUND_VOLUME;
                randomInt = random.nextInt(101);
                randomStr = Integer.toString(randomInt);
                strJson = Constant.IA_CMD_SET_SOUND_VOLUME_SIZE + randomStr+"}";
                break;
            case 4://设置NavApp静音开
                intType = Constant.IA_CMD_SET_MUTE_SWITCH;
                strJson = Constant.IA_CMD_SET_MUTE_SWITCH_ON;
                break;
            case 5://设置NavApp静音关
                intType = Constant.IA_CMD_SET_MUTE_SWITCH;
                strJson = Constant.IA_CMD_SET_MUTE_SWITCH_OFF;
                break;
            case 6://设置NavApp算路避让规则
                intType = Constant.IA_CMD_SET_ROUTE_CONDITION;
                strJson = Constant.IA_CMD_SET_ROUTE_CONDITION_CONTENT;
                break;
            case 7://设置NavApp的时间信息12
                intType = Constant.IA_CMD_SET_TIME_INFOMATION;
                strJson = Constant.IA_CMD_SET_TIME_INFOMATION_12;
                break;
            case 8://设置NavApp的时间信息24
                intType = Constant.IA_CMD_SET_TIME_INFOMATION;
                strJson = Constant.IA_CMD_SET_TIME_INFOMATION_24;
                break;
            case 9://设置NavApp的输入法
                intType = Constant.IA_CMD_SET_TYPE_WRITING;
                strJson = Constant.IA_CMD_SET_TYPE_WRITING_CONTENT;
                break;
            case 10://设置NavApp的语言中文
                intType = Constant.IA_CMD_SET_LANGUAGE;
                strJson = Constant.IA_CMD_SET_LANGUAGE_CHINASE;
                break;
            case 11://设置NavApp的语言英文
                intType = Constant.IA_CMD_SET_LANGUAGE;
                strJson = Constant.IA_CMD_SET_LANGUAGE_ENGLISH;
                break;
            case 12://设置NavApp的多媒体信息蓝牙
                intType = Constant.IA_CMD_SET_MULTIMEDIA_INFOMATION;
                strJson = Constant.IA_CMD_SET_MULTIMEDIA_INFOMATION_BLUETOOTH;
                break;
            case 13://设置NavApp的多媒体信息FM
                intType = Constant.IA_CMD_SET_MULTIMEDIA_INFOMATION;
                strJson = Constant.IA_CMD_SET_MULTIMEDIA_INFOMATION_FM;
                break;
            case 14://设置NavApp导航信息发送开启
                intType = Constant.IA_CMD_SET_TBT_SWITCH_STATUS;
                strJson = Constant.IA_CMD_SET_TBT_SWITCH_STATUS_ON;
                break;
            case 15://设置NavApp导航信息发送关闭
                intType = Constant.IA_CMD_SET_TBT_SWITCH_STATUS;
                strJson = Constant.IA_CMD_SET_TBT_SWITCH_STATUS_OFF;
                break;
            case 16://设置NavApp导航播报语音类型
                intType = Constant.IA_CMD_SET_GUIDENCE_SOUND_TYPE;
                randomInt = random.nextInt(6) + 1;
                randomStr = Integer.toString(randomInt);
                strJson = Constant.IA_CMD_SET_GUIDENCE_SOUND_TYPE_ + randomStr +"}";
                break;
            case 17://更新交互目标的写状态
                randomInt = random.nextInt(2) + 1;
                randomStr = Integer.toString(randomInt);
                intType = Constant.IA_CMD_UPDATE_IATARGET_WRITING_STATUS;
                strJson = Constant.IA_CMD_UPDATE_IATARGET_WRITING_STATUS_ + randomStr + "}";
                break;
            case 18://更新交互目标的路径状态
                randomInt = random.nextInt(2) + 1;
                randomStr = Integer.toString(randomInt);
                intType = Constant.IA_CMD_UPDATE_IATARGET_ROUTE_STATUS;
                strJson = Constant.IA_CMD_UPDATE_IATARGET_ROUTE_STATUS_ + randomStr + "}";
                break;
            case 19://启动导航程序
                intType = Constant.IA_CMD_STARTUP_OR_EXIT;
                strJson = Constant.IA_CMD_STARTUP;
                break;
            case 20://退出导航程序
                intType = Constant.IA_CMD_STARTUP_OR_EXIT;
                strJson = Constant.IA_CMD_EXIT;
                break;
            case 21://保存NavApp数据
                intType = Constant.IA_CMD_SAVE_DATA;
                strJson = Constant.IA_CMD_SAVE_DATA_CONTENT;
                break;
            case 22://显示NavApp
                intType = Constant.IA_CMD_SHOW_OR_HIDE;
                strJson = Constant.IA_CMD_SHOW;
                break;
            case 23://隐藏NavApp
                intType = Constant.IA_CMD_SHOW_OR_HIDE;
                strJson = Constant.IA_CMD_HIDE;
                break;
            case 24://缩小地图
                intType = Constant.IA_CMD_ZOOM_MAP;
                strJson = Constant.IA_CMD_ZOOM_MAP_OUT_2;
                break;
            case 25://放大地图
                intType = Constant.IA_CMD_ZOOM_MAP;
                strJson = Constant.IA_CMD_ZOOM_MAP_IN_1;
                break;
            case 26://获取NavApp的导航引导信息
                intType = Constant.IA_CMD_GET_NAVI_INFO;
                strJson = Constant.IA_CMD_GET_NAVI_INFO_CONTENT;
                break;
            case 27://重播当前导航语音提示语句
                intType = Constant.IA_CMD_REPEAT_NAVI_SOUND;
                strJson = Constant.IA_CMD_REPEAT_NAVI_SOUND_CONTENT;
                break;
            case 28://画面迁移
                intType = Constant.IA_CMD_MOVE_UI;
                strJson = Constant.IA_CMD_MOVE_UI_CONTENT;
                break;
            case 29://改变NavApp的UI视觉属性
                intType = Constant.IA_CMD_CHANGE_NAVI_UI_VISUAL_ATTRIBUTES;
                strJson = Constant.IA_CMD_CHANGE_NAVI_UI_VISUAL_ATTRIBUTES_CONTENT;
                break;
            case 30://9NavApp显示动态信息
                intType = Constant.IA_CMD_SHOW_DANAMIC_INFOMATION;
                strJson = Constant.IA_CMD_SHOW_DANAMIC_INFOMATION_CONTENT;
                break;
            case 31://开始真实导航
                intType = Constant.IA_CMD_SART_OR_STOP_NAVI_GUIDE;
                strJson = Constant.IA_CMD_SART_OR_STOP_NAVI_GUIDE_START;
                break;
            case 32://停止真实导航
                intType = Constant.IA_CMD_SART_OR_STOP_NAVI_GUIDE;
                strJson = Constant.IA_CMD_SART_OR_STOP_NAVI_GUIDE_END;
                break;
            case 33://开始模拟导航
                intType = Constant.IA_CMD_SART_OR_STOP_NAVI_GUIDE;
                strJson = Constant.IA_CMD_SART_OR_STOP_NAVI_GUIDE_SIMULATION_START;
                break;
            case 34://停止模拟导航
                intType = Constant.IA_CMD_SART_OR_STOP_NAVI_GUIDE;
                strJson = Constant.IA_CMD_SART_OR_STOP_NAVI_GUIDE_SIMULATION_END;
                break;
            case 35://获取NavApp的当前状态
                intType = Constant.IA_CMD_GET_NAVI_STATUAS;
                strJson = Constant.IA_CMD_GET_NAVI_STATUAS_CONTENT;
                break;
            case 36://NavApp显示交互目标的音量(默认30)
                randomInt = random.nextInt(101);
                randomStr = Integer.toString(randomInt);
                intType = Constant.IA_CMD_SHOW_TARGET_SOUND_VOLUME;
                strJson = Constant.IA_CMD_SHOW_TARGET_SOUND_VOLUME_CONTENT + randomStr + "}";
                break;
            case 37://删除NavApp的当前路线
                intType = Constant.IA_CMD_DELET_NAVI_ROUTE;
                strJson = Constant.IA_CMD_DELET_NAVI_ROUTE_CONTENT;
                break;
            case 38://获取GPS信息
                intType = Constant.IA_CMD_GET_GPS_INFO;
                strJson = Constant.IA_CMD_GET_GPS_INFO_CONTENT;
                break;
            case 39://切换地图视图模式(第一个屏幕3D)
                intType = Constant.IA_CMD_SET_MAP_VIEW_MODE;
                strJson = Constant.IA_CMD_SET_MAP_VIEW_MODE_1SCREEN_3D;
                break;
            case 40://切换地图视图模式(第一个屏幕2D)

                intType = Constant.IA_CMD_SET_MAP_VIEW_MODE;
                strJson = Constant.IA_CMD_SET_MAP_VIEW_MODE_1SCREEN_2D;
                break;
            case 41://切换地图视图模式(第二个屏幕3D)
                intType = Constant.IA_CMD_SET_MAP_VIEW_MODE;
                strJson = Constant.IA_CMD_SET_MAP_VIEW_MODE_2SCREEN_3D;
                break;
            case 42://切换地图视图模式(第二个屏幕2D)
                intType = Constant.IA_CMD_SET_MAP_VIEW_MODE;
                strJson = Constant.IA_CMD_SET_MAP_VIEW_MODE_2SCREEN_2D;
                break;
            case 43://条件算路
                intType = Constant.IA_CMD_ROUTE_BY_CONDITION;
                strJson = Constant.IA_CMD_ROUTE_BY_CONDITION_1;
                break;
            case 44://设置NavApp画面所的在显示屏幕
                intType = Constant.IA_CMD_SET_DISPLAY_SCREEN_FOR_NAVAPP;
                strJson = Constant.IA_CMD_SET_DISPLAY_SCREEN_FOR_NAVAPP_SHOW2;
                break;
            case 45://条件搜索POI
                intType = Constant.IA_CMD_SEARCH_POI_BY_CONDITION;
                strJson = Constant.IA_CMD_SEARCH_POI_BY_CONDITION_CONTETN;
                break;
            case 46://第1个List垂直向下翻页
                intType = Constant.IA_CMD_CURRENT_UI_LIST_ANIMATION;
                strJson = Constant.IA_CMD_CURRENT_UI_LIST_ANIMATION_ONE;
                break;
            case 47://导航到家
                intType = Constant.IA_CMD_FAVORITE_GUIDANCE;
                strJson = Constant.IA_CMD_FAVORITE_GUIDANCE_HOME;
                break;
            case 48://导航到收藏列表中的第10个收藏点
                intType = Constant.IA_CMD_FAVORITE_GUIDANCE;
                strJson = Constant.IA_CMD_FAVORITE_GUIDANCE_TEN;
                break;
            case 49://定位自车
                intType = Constant.IA_CMD_LOCATE_THE_CAR;
                strJson = "";
                break;
            case 50://打开蓝牙音乐
                intType = Constant.IA_CMD_NAVAPP_CONTROL_MULTIMEDIA;
                strJson = Constant.IA_CMD_NAVAPP_CONTROL_MULTIMEDIA_OPEN_BLUETOOTH;
                break;
            case 51://关闭视频
                intType = Constant.IA_CMD_NAVAPP_CONTROL_MULTIMEDIA;
                strJson = Constant.IA_CMD_NAVAPP_CONTROL_MULTIMEDIA_CLOSE_VODEO;
                break;
            case 52://查看如何使用NavApp
                intType = Constant.IA_CMD_BROWSE_HELP_INFORMATION;
                strJson = Constant.IA_CMD_BROWSE_HELP_INFORMATION_USE_NAV;
                break;
            case 53://查看如何播放音乐
                intType = Constant.IA_CMD_BROWSE_HELP_INFORMATION;
                strJson = Constant.IA_CMD_BROWSE_HELP_INFORMATION_PLAY_MUSIC;
                break;
            case 54://条件搜索POI
                intType = Constant.IA_CMD_SEARCH_POI_BY_CONDITION;
                strJson = Constant.IA_CMD_SEARCH_POI_BY_CONDITION_CONTETN;
                break;
        }
        if(bind) {
            try {
                navService.request(intType,strJson);
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
                        if (ds == null ) {
                            return;
                        }
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
