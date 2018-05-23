package com.navi.fduser.broadcast_json;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import org.json.JSONException;
import org.json.JSONObject;

public class MapReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            String action = intent.getAction();
            int cmd = 0;
            JSONObject jsonObject = new JSONObject();
            //来自云雀的广播
            if (Constants.BROADCAST_ACTION.equals(action)) {
                switch (intent.getIntExtra(Constants.KEY_TYPE, 0)) {
                    case Constants.TYPE_SET_DIS_REGION://设置显示区域
                        break;
                    case Constants.TYPE_ADD_FAVOR://收藏当前点
                        break;
                    case Constants.TYPE_MAP_OPERA://地图操作:
                        int type = intent.getIntExtra(Constants.EXTRA_TYPE, 0);
                        int opType = intent.getIntExtra(Constants.EXTRA_OPERA, 0);
                        if (0 == type) {//实时路况 opType 0 开启,1关闭
                            cmd = Constants.IA_CMD_ENABLE_TMC;
                            jsonObject.put("enable", opType == 0);
                        } else if (1 == type) {//缩放地图 opType 0放大地图,1缩小地图
                            cmd = Constants.IA_CMD_ZOOM_MAP;
                            jsonObject.put("screenId", 1);
                            jsonObject.put("operationType", opType + 1);
                        } else if (2 == type) {//切换视图模式 0 车头上 1北上 2 3D模式
                            cmd = Constants.IA_CMD_SET_MAP_VIEW_MODE;
                            jsonObject.put("screenId", 1);
                            if (opType == 0) {
                                jsonObject.put("viewMode", opType + 1);
                            } else if (opType == 1) {
                                jsonObject.put("viewMode", opType - 1);
                            } else {
                                jsonObject.put("viewMode", opType);
                            }
                        }
                        break;
                    case Constants.TYPE_EXIT_APP://退出app

                        break;
                    case Constants.TYPE_SHOW_MAP://进入主图
                        cmd = Constants.IA_CMD_SHOW_OR_HIDE;
                        jsonObject.put("operationType", 1);
                        break;
                    case Constants.TYPE_OPEN_FAVOR://打开收藏
                        break;
                    case Constants.TYPE_HIDE://最小化(后台)
                        cmd = Constants.IA_CMD_SHOW_OR_HIDE;
                        jsonObject.put("operationType", 2);
                        break;
                    case Constants.TYPE_SET_FAVOR://进入设置收藏点
                        break;
                    case Constants.TYPE_POI_INFO_BY_POINT://根据经纬度显示POI信息
                        cmd = Constants.IA_CMD_GET_SPECIFIC_POINT_INFO;
                        double longitude = intent.getDoubleExtra(Constants.EXTRA_LON, 0);
                        double latitude = intent.getDoubleExtra(Constants.EXTRA_LAT, 0);
                        intent.getIntExtra(Constants.EXTRA_DEV, 0);
                        //todo 根据情况转换坐标

                        jsonObject.put("longitude", 0);
                        jsonObject.put("latitude", 0);
                        break;
                }
                if (null != messageListener) {
                    messageListener.onGetMessage(cmd, jsonObject);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private MessageListener messageListener;

    public void setMessageListener(MessageListener messageListener) {
        this.messageListener = messageListener;
    }
}
