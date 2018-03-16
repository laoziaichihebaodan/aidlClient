package com.fundrive.navaidlclient;

/**
 * Created by wangjianghua on 2017/12/1.
 */

public class Constant {
    /**
     * 1.1设置NavApp授权序列号
     */
    public static int  IA_CMD_SET_AUTHORIZE_SERIAL_NUMBER = 0x1000;
    public static String  IA_CMD_SET_AUTHORIZE_SERIAL_NUMBER_CONTENT = "{\"authorNumber\":\"CG0052162470468\"}";

    /**
     * 1.2设置NavApp地图显示模式：白天/黑夜
     */
    public static int  IA_CMD_SET_MAP_DISPLAY_MODE = 0x1001;
    public static String  IA_CMD_SET_MAP_DISPLAY_MODE_DAY = "{\"mode\":1}";
    public static String  IA_CMD_SET_MAP_DISPLAY_MODE_NIGHT = "{\"mode\":2}";

    /**
     *1.3设置NavApp音量
     */
    public static int  IA_CMD_SET_SOUND_VOLUME = 0x1002;
    public static String IA_CMD_SET_SOUND_VOLUME_SIZE = "{\"volume\":";

    /**
     *1.4设置NavApp静音开/关
     */
    public static int  IA_CMD_SET_MUTE_SWITCH = 0x1003;
    public static String IA_CMD_SET_MUTE_SWITCH_ON = "{\"muteState\":true}";
    public static String IA_CMD_SET_MUTE_SWITCH_OFF = "{\"muteState\":false}";

    /**
     *1.5设置NavApp算路避让规则
     */
    public static int  IA_CMD_SET_ROUTE_CONDITION = 0x1004;
    public static String  IA_CMD_SET_ROUTE_CONDITION_CONTENT= "{\n" +
            "    \"avoidType\":2,\n" +
            "    \"routeRule\":3,\n" +
            "    \"operation\":true\n" +
            "}";

    /**
     *1.6设置NavApp的时间信息
     */
    public static int IA_CMD_SET_TIME_INFOMATION = 0x1005 ;
    public static String IA_CMD_SET_TIME_INFOMATION_12 = "{\n" +
            "    \"timeType\":1,\n" +
            "    \"timeMode\":1,\n" +
            "    \"timeValue\":{\n" +
            "        \"hour\":null,\n" +
            "        \"minute\":null,\n" +
            "        \"second\":null,\n" +
            "        \"year\":null,\n" +
            "        \"month\":null,\n" +
            "        \"date\":null\n" +
            "    }\n" +
            "}";
    public static String IA_CMD_SET_TIME_INFOMATION_24 = "{\n" +
            "    \"timeType\":2,\n" +
            "    \"timeMode\":null,\n" +
            "    \"timeValue\":{\n" +
            "        \"hour\":14,\n" +
            "        \"minute\":24,\n" +
            "        \"second\":57,\n" +
            "        \"year\":2017,\n" +
            "        \"month\":10,\n" +
            "        \"date\":57\n" +
            "    }\n" +
            "}";

    /**
     *1.7设置NavApp的输入法
     */
    public static int IA_CMD_SET_TYPE_WRITING = 0x1006 ;
    public static String IA_CMD_SET_TYPE_WRITING_CONTENT = "";
    /**
     *1.8设置NavApp的语言
     */
    public static int IA_CMD_SET_LANGUAGE = 0x1007 ;
    public static String IA_CMD_SET_LANGUAGE_CHINASE = "{\"languageType\":0}";
    public static String IA_CMD_SET_LANGUAGE_ENGLISH = "{\"languageType\":1}";

    /**
     *1.9设置NavApp的多媒体信息
     */
    public static int IA_CMD_SET_MULTIMEDIA_INFOMATION= 0x1008 ;
    public static String IA_CMD_SET_MULTIMEDIA_INFOMATION_BLUETOOTH = "{\n" +
            "    \"iaAudio\":{\n" +
            "        \"sourceType\":4,\n" +
            "        \"trafficRadio\":{\n" +
            "            \"rand\":null,\n" +
            "            \"frequency\":{\n" +
            "                \"integerBit\":null\n" +
            "                \"decimalBit\":null\n" +
            "             }  \n" +
            "          },\n" +
            "         \"musicName\":\"天空之城\"\n" +
            "    },\n" +
            "    \"iaVideo\":null\n" +
            "}";
    public static String IA_CMD_SET_MULTIMEDIA_INFOMATION_FM = "{\n" +
            "    \"iaAudio\":{\n" +
            "        \"sourceType\":1,\n" +
            "        \"trafficRadio\":{\n" +
            "            \"rand\":3,\n" +
            "            \"frequency\":{\n" +
            "                \"integerBit\":98\n" +
            "                \"decimalBit\":5\n" +
            "                }  \n" +
            "            },\n" +
            "            \"musicName\":null\n" +
            "    },\n" +
            "    \"iaVideo\":null\n" +
            "}";

    /**
     *1.10设置NavApp导航信息发送开关状态
     */
    public static int IA_CMD_SET_TBT_SWITCH_STATUS = 0x1009 ;
    public static String IA_CMD_SET_TBT_SWITCH_STATUS_ON= "{\"state\":ture}";
    public static String IA_CMD_SET_TBT_SWITCH_STATUS_OFF= "{\"state\":false}";

    /**
     *1.11设置NavApp导航播报语音类型
     */
    public static int IA_CMD_SET_GUIDENCE_SOUND_TYPE = 0x100A ;
    public static String IA_CMD_SET_GUIDENCE_SOUND_TYPE_ = "{\"voiceType\":";//普通话女
    public static String IA_CMD_SET_GUIDENCE_SOUND_TYPE_GIRL = "{\"voiceType\":1}";//普通话女
    public static String IA_CMD_SET_GUIDENCE_SOUND_TYPE_MAN = "{\"voiceType\":2}";//普通话男
    public static String IA_CMD_SET_GUIDENCE_SOUND_TYPE_EN_GIRL = "{\"voiceType\":3}";//英文女
    public static String IA_CMD_SET_GUIDENCE_SOUND_TYPE_EN_MAN = "{\"voiceType\":4}";//英文男
    public static String IA_CMD_SET_GUIDENCE_SOUND_TYPE_GD_GIRL = "{\"voiceType\":4}";//广东话女
    public static String IA_CMD_SET_GUIDENCE_SOUND_TYPE_GD_MAN = "{\"voiceType\":4}";//广东话男

    /**
     *1.12更新交互目标的写状态
     */
    public static int IA_CMD_UPDATE_IATARGET_WRITING_STATUS = 0x100B ;
    public static String IA_CMD_UPDATE_IATARGET_WRITING_STATUS_= "{\"writed\":";
    public static String IA_CMD_UPDATE_IATARGET_WRITING_STATUS_YES= "{\"writed\":1}";
    public static String IA_CMD_UPDATE_IATARGET_WRITING_STATUS_NO= "{\"writed\":2}";

    /**
     *1.13更新交互目标的路径状态
     */
    public static int IA_CMD_UPDATE_IATARGET_ROUTE_STATUS = 0x100C ;
    public static String IA_CMD_UPDATE_IATARGET_ROUTE_STATUS_= "{\"route\":";
    public static String IA_CMD_UPDATE_IATARGET_ROUTE_STATUS_YES= "{\"route\":1}";
    public static String IA_CMD_UPDATE_IATARGET_ROUTE_STATUS_NO= "{\"route\":2}";

    /**
     *2.1启动/退出NavApp
     */
    public static int IA_CMD_STARTUP_OR_EXIT = 0x2000 ;
    public static String IA_CMD_STARTUP = "{\"operationType\":1}";
    public static String IA_CMD_EXIT = "{\"operationType\":2}";

    /**
     *2.2保存NavApp数据
     */
    public static int IA_CMD_SAVE_DATA = 0x2001 ;
    public static String IA_CMD_SAVE_DATA_CONTENT = "";

    /**
     *2.3显示/隐藏NavApp
     */
    public static int IA_CMD_SHOW_OR_HIDE = 0x2002 ;
    public static String IA_CMD_SHOW = "{\"operationType\":1}" ;
    public static String IA_CMD_HIDE = "{\"operationType\":2}" ;

    /**
     *2.4缩小/放大地图
     */
    public static int IA_CMD_ZOOM_MAP = 0x2003 ;
    public static String IA_CMD_ZOOM_MAP_IN_1= "{\"operationType\":1,\"screenId\":1}";
    public static String IA_CMD_ZOOM_MAP_IN_2= "{\"operationType\":1,\"screenId\":2}";
    public static String IA_CMD_ZOOM_MAP_OUT_1= "{\"operationType\":2,\"screenId\":1}";
    public static String IA_CMD_ZOOM_MAP_OUT_2= "{\"operationType\":2,\"screenId\":2}";

    /**
     *2.5获取NavApp的导航引导信息
     */
    public static int IA_CMD_GET_NAVI_INFO = 0x2004 ;
    public static String IA_CMD_GET_NAVI_INFO_CONTENT= "";

    /**
     *2.6重播当前导航语音提示语句
     */
    public static int IA_CMD_REPEAT_NAVI_SOUND = 0x2005 ;
    public static String IA_CMD_REPEAT_NAVI_SOUND_CONTENT = "";

    /**
     *2.7画面迁移
     */
    public static int IA_CMD_MOVE_UI = 0x2006 ;
    public static String IA_CMD_MOVE_UI_CONTENT= "";

    /**
     *2.8改变NavApp的UI视觉属性
     */
    public static int IA_CMD_CHANGE_NAVI_UI_VISUAL_ATTRIBUTES = 0x2007;
    public static String IA_CMD_CHANGE_NAVI_UI_VISUAL_ATTRIBUTES_CONTENT = "";

    /**
     *2.9NavApp显示动态信息
     */
    public static int IA_CMD_SHOW_DANAMIC_INFOMATION = 0x2008;
    public static String IA_CMD_SHOW_DANAMIC_INFOMATION_CONTENT = "";

    /**
     *2.10NavApp启动/停止导航
     */
    public static int IA_CMD_SART_OR_STOP_NAVI_GUIDE = 0x2009;
    public static String IA_CMD_SART_OR_STOP_NAVI_GUIDE_START = "{\"operationType\":1,\"guideType\":2}";//启动真实导航
    public static String IA_CMD_SART_OR_STOP_NAVI_GUIDE_END = "{\"operationType\":2,\"guideType\":2}";//关闭真实导航
    public static String IA_CMD_SART_OR_STOP_NAVI_GUIDE_SIMULATION_START = "{\"operationType\":1,\"guideType\":1}";//启动模拟导航
    public static String IA_CMD_SART_OR_STOP_NAVI_GUIDE_SIMULATION_END = "{\"operationType\":2,\"guideType\":1}";//关闭模拟导航

    /**
     *2.11获取NavApp的当前状态
     */
    public static int IA_CMD_GET_NAVI_STATUAS = 0x200A;
    public static String IA_CMD_GET_NAVI_STATUAS_CONTENT = "";

    /**
     *2.12NavApp显示交互目标的音量
     */
    public static int IA_CMD_SHOW_TARGET_SOUND_VOLUME = 0x200B;
    public static String IA_CMD_SHOW_TARGET_SOUND_VOLUME_CONTENT= "{\"targetMaxVolume\":100,\"targetCurrentVolume\":";

    /**
     *2.13删除NavApp的当前路线
     */
    public static int IA_CMD_DELET_NAVI_ROUTE = 0x200C;
    public static String IA_CMD_DELET_NAVI_ROUTE_CONTENT = "";

    /**
     *2.14获取GPS信息
     */
    public static int IA_CMD_GET_GPS_INFO = 0x200D;
    public static String IA_CMD_GET_GPS_INFO_CONTENT = "";

    /**
     *2.15切换地图视图模式
     */
    public static int IA_CMD_SET_MAP_VIEW_MODE = 0x200E;
    public static String IA_CMD_SET_MAP_VIEW_MODE_1SCREEN_3D = "{\"viewMode\":2,\"screenId\":1}";//将第一个屏幕中的地图显示模式切换到3D模式
    public static String IA_CMD_SET_MAP_VIEW_MODE_1SCREEN_2D = "{\"viewMode\":1,\"screenId\":1}";//将第一个屏幕中的地图显示模式切换到2D模式
    public static String IA_CMD_SET_MAP_VIEW_MODE_2SCREEN_3D = "{\"viewMode\":2,\"screenId\":2}";//将第二个屏幕中的地图显示模式切换到3D模式
    public static String IA_CMD_SET_MAP_VIEW_MODE_2SCREEN_2D = "{\"viewMode\":1,\"screenId\":2}";//将第二个屏幕中的地图显示模式切换到2D模式

    /**
     *2.16条件算路
     */
    public static int IA_CMD_ROUTE_BY_CONDITION = 0x200F;
    public static String IA_CMD_ROUTE_BY_CONDITION_1 =  "{\"endPoint\":{\n" +
            "\"iaPoiType\":244,\n" +
            "\"iaPoiPos\" : {\n" +
            "\"longitude\":12160606,\n" +
            "\"latitude\" : 3132359\n" +
            "},\n" +
            "\"iaPoiDisPos\" : {\n" +
            "\"longitude\":12160605,\n" +
            "\"latitude\" : 3132374\n" +
            "},\n" +
            "\"iaPoiId\" : 4294967295,\n" +
            "\"iaChildPoiNum\" : 0,\n" +
            "\"iaCompoundId\" : 80262454763061964,\n" +
            "\"iaPoiName\" : \"德尔福科技研发中心Test\",\n" +
            "\"iaPoiAdress\" : \"上海市浦东新区德林路118号\",\n" +
            "\"iaPoiPhone\" : \"021-28968866\",\n" +
            "\"iaRegionName\" : \"上海市浦东新区\",\n" +
            "\"iaPoiTypeName\" : \"科研及技术服务\"\n" +
            "}}";
    public static String IA_CMD_ROUTE_BY_CONDITION_2 = "";
    public static String IA_CMD_ROUTE_BY_CONDITION_3 = "";
    public static String IA_CMD_ROUTE_BY_CONDITION_4 = "";
    public static String IA_CMD_ROUTE_BY_CONDITION_5 = "";
    public static String IA_CMD_ROUTE_BY_CONDITION_6 = "";
    public static String IA_CMD_ROUTE_BY_CONDITION_7 = "";


    /**
     *2.17设置NavApp画面所的在显示屏幕
     */
    public static int IA_CMD_SET_DISPLAY_SCREEN_FOR_NAVAPP = 0x2010;
    public static String IA_CMD_SET_DISPLAY_SCREEN_FOR_NAVAPP_SHOW1= "{\"iaDisPlayScreenId\":1}";
    public static String IA_CMD_SET_DISPLAY_SCREEN_FOR_NAVAPP_SHOW2= "{\"iaDisPlayScreenId\":2}";

    /**
     *2.18 List动画操作
     */
    public static int IA_CMD_CURRENT_UI_LIST_ANIMATION = 0x2011;
    public static String IA_CMD_CURRENT_UI_LIST_ANIMATION_ONE= "{\"iaListAnimaType\": [4]}";//当前画面中的第1个List垂直向下翻页
    public static String IA_CMD_CURRENT_UI_LIST_ANIMATION_TWO= "{\"iaListAnimaType\": [0,4,8]}";//当前画面中的第2个List垂直向下翻页，第3个List垂直向下滚动

    /**
     *2.19 收藏点导航
     */
    public static int IA_CMD_FAVORITE_GUIDANCE = 0x2012;
    public static String IA_CMD_FAVORITE_GUIDANCE_HOME = "{" +//导航到家
            " \"iaFavoriteGuide\": {" +
            "    \"iaFavTye\": 1," +
            "    \"iaFavIndex\": -1" +
            " }" +
            "}";

    //导航到收藏列表中的第10个收藏点
    public static String IA_CMD_FAVORITE_GUIDANCE_TEN = "{" +
            " \"iaFavoriteGuide\": {" +
            "    \"iaFavTye\": 0," +
            "    \"iaFavIndex\": 9" +
            " }" +
            "}";

    /**
     *2.20 定位自车
     */
    public static int IA_CMD_LOCATE_THE_CAR = 0x2013;

    /**
     *2.21 NavApp执行多媒体控制
     */
    public static int IA_CMD_NAVAPP_CONTROL_MULTIMEDIA = 0x2014;
    public static String IA_CMD_NAVAPP_CONTROL_MULTIMEDIA_OPEN_BLUETOOTH = "{" +//打开蓝牙音乐
            "  \"iaMultimediaOpType\":4," +
            "  \"iaMultiMediaApp\":4" +
            "}";
    public static String IA_CMD_NAVAPP_CONTROL_MULTIMEDIA_CLOSE_VODEO = "{" + //关闭视频
            "  \"iaMultimediaOpType\":5," +
            "  \"iaMultiMediaApp\":10" +
            "}";

    /**
     *2.22 查看帮助信息
     */
    public static int IA_CMD_BROWSE_HELP_INFORMATION = 0x2015;
    public static String IA_CMD_BROWSE_HELP_INFORMATION_USE_NAV = "{\"iaHelpType\":1}";//查看如何使用NavApp
    public static String IA_CMD_BROWSE_HELP_INFORMATION_PLAY_MUSIC = "{\"iaHelpType\":6}";//查看如何播放音乐

    /**
     *3.0条件搜索POI
     */
    public static int IA_CMD_SEARCH_POI_BY_CONDITION = 0x3000;
    public static String IA_CMD_SEARCH_POI_BY_CONDITION_CONTETN;


}
