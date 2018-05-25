package com.navi.fduser.broadcast_json;

public interface Constants {
    //云雀广播action
    String BROADCAST_ACTION = "AUTONAVI_STANDARD_BROADCAST_RECV";

    //广播携带数据的Key
    //===============================================================
    String KEY_TYPE = "KEY_TYPE";
    //1.1.1动态设置地图显示区域。
    int TYPE_SET_DIS_REGION = 10075;
    String EXTRA_CHANGE_APP_RECT_LEFT = "EXTRA_CHANGE_APP_RECT_LEFT";//矩阵left值
    String EXTRA_CHANGE_APP_RECT_TOP = "EXTRA_CHANGE_APP_RECT_TOP";//矩阵top值
    String EXTRA_CHANGE_APP_RECT_RIGHT = "EXTRA_CHANGE_APP_RECT_RIGHT";//矩阵right值
    String EXTRA_CHANGE_APP_RECT_BOTTOM = "EXTRA_CHANGE_APP_RECT_BOTTOM";//矩阵bottom值

    //1.1.2 地图标注  第三方发送名称或经纬度信息给auto,auto展示一个标注点或者启动auto后展现一个标注点
    int TYPE_MARK_POINT = 10039;
    String SOURCE_APP = "SOURCE_APP";//第三方应用名称(String)
    String POINAME = "POINAME";//POI 名称(String)（必填）
    String LAT = "LAT";//（必填）纬度
    String LON = "LON";//（必填）经度
    String DEV = "DEV";//（必填）是否偏移(0:lat 和 lon 是已经加密后的,不需要国测加密; 1:需要国测加密)

    //1.2.1 指定偏移量移图 以某个位置为中心，设置制动的上下左右偏移的位置。
    int SET_OFFSET = 10004;
    String EXTRA_OFFSET_X = "EXTRA_OFFSET_X";//x轴偏移量(int)
    String EXTRA_OFFSET_Y = "EXTRA_OFFSET_Y";//y轴偏移量(int)

    //1.2.2.1 收藏当前点  车机系统发送收藏当前点信息给auto【任何场景下都支持（包含导航，巡航，以及非地图界面）】
    int TYPE_ADD_FAVOR = 11003;
    //1.2.2.2收藏当前点的结果透出
    String EXTRA_FAVORITE_MY_LOCATION = "EXTRA_FAVORITE_MY_LOCATION";// (String) 当前车位数据；

    //1.2.3 地图操作 第三方发送相关消息可对auto图面进行操作，auto响应，其中包含实时路况的开启与关闭、缩放地图、视图模式的转换，可同时
    //组合使用。
    /*\\地图操作
    @param type 0 实时路况; 1 缩放地图; 2 视图模式（必填）
    type为0：opera 0 实时路况开；1实时路况关
    type为1：opera 0 放大地图； 1缩小地图
    type为2：opera 0 切换2d车上； 1切换2d北上；2切换3d车上支持*/
    int TYPE_MAP_OPERA = 10027;
    String EXTRA_TYPE = "EXTRA_TYPE";//类型（int）type
    String EXTRA_OPERA = "EXTRA_OPERA";//操作（int）opera
    //1.3.1 退出软件
    int TYPE_EXIT_APP = 10021;
    //1.3.2 进入主图
    int TYPE_SHOW_MAP = 10034;
    //1.3.3 打开收藏夹
    int TYPE_OPEN_FAVOR = 10028;
    //1.3.4 最小化
    int TYPE_HIDE = 10031;
    //1.3.5 进入家/公司设置
    int TYPE_SET_FAVOR = 10070;
    //1.4.1 根据经纬度查看POI  传入经纬度信息进行poi查看，在地图左侧显示poi的相关信息，其名称是通过逆地理获取的
    int TYPE_POI_INFO_BY_POINT = 10013;
    String EXTRA_LON = "EXTRA_LON";//经度
    String EXTRA_LAT = "EXTRA_LAT";//维度
    String EXTRA_DEV = "EXTRA_DEV";//是否偏移(0:lat 和 lon 是已经加密后的,不需要国测加密; 1:需要国测加密)(必填)(int
    //1.4.2 根据地址查看POI 并展现在地图左侧。
    int TYPE_POI_INFO_BY_ADDRESS = 10011;
    String EXTRA_ADDRESS = "EXTRA_ADDRESS";
    //1.4.3 POI名称显示 例如：一键导航给出的名称是“麦当劳”，但实际上此处的逆地理为”XX大楼”，在这种情况下，信息卡片显示“麦当劳”。即第三方同时传入经纬度和
    //poi名称时，信息卡片优先显示第三方传入poi名称。
    int TYPE_POI_INFO_BY_NAME = 10012;
    //1.4.4 查看我的位置
    int TYPE_POI_INFO_SELF = 10008;
    //1.5.1 恢复默认设置 在任意界面响应导航恢复出厂设置的动作。
    int TYPE_RESET = 11001;
    //1.5.2 查询后台巡航播报的开关状态
    int TYPE_BOARD_STATE = 12007;
    String EXTRA_SETTING_TYPE = "EXTRA_SETTING_TYPE";//1（int类型）（1：后台巡航播报）
    //1.5.3 设置后台巡航播报开关
    int TYPE_BOARD_SWITCH = 12006;
    String EXTRA_SETTING_RESULT = "EXTRA_SETTING_RESULT";//true（boolean类型）（true设置为开，false设置为关）
    //1.5.5 巡航播报设置
    // @param type 0 : 所有 ；1: 路况播报；2: 电子眼播报； 3: 警示播报；
    //@param opera 0 : 打开 ；1: 关闭 ；
    int TYPE_SET_BROADCAST_FUN = 10064;
    //1.5.6 设置昼夜模式
    int TYPE_DAY_NIGHT = 10048;
    String EXTRA_DAY_NIGHT_MODE = "EXTRA_DAY_NIGHT_MODE";//昼夜模式类型（int）
    //1.6.1 获取静音状态 第三方请求获取当前静音状态（包括临时静音和永久静音）。
    int TYPE_GET_MUTE_STATE = 10071;
    //1.6.2 设置地图是否播报
    int TYPE_SET_MUTE = 10047;
    String EXTRA_MUTE = "EXTRA_MUTE";//是否永久静音
    String EXTRA_CASUAL_MUTE = "EXTRA_CASUAL_MUTE";//是否临时静音
    //1.6.3 设置语音播报角色 role 0:国语女声; 1：国语男声; 2：周星星；3：广东话；4：林志玲 ； 5：郭德纲；
    //1.4.2 以上版本支持
    //role 6:东北话; 7：河南话;8：湖南话；9：四川话；10：台湾话 ；
    int TYPE_SET_ROLE = 10044;
    String VOICE_ROLE = "VOICE_ROLE";//语音角色（int）role
    //2.1.1 设置拓展屏的地图状态 在支持多屏显示的项目上，且开启双屏绘制功能，系统方可通过协议设置指定拓展屏的地图状态（比例尺范围设置、视图切换、车标位置设
    //置）。如传入视角：3D，在拓展屏设置3D视角。仅支持在双屏功能接口开启的版本上响应协议指令，其他场景下均不响应。
    //版本支持：官网2.6及以上版本
    int TYPE_EXTERNAL_SCREEN = 10104;
    String EXTRA_EXTERNAL_ENGINE_ID = "EXTRA_EXTERNAL_ENGINE_ID"; //仪表对应的engine id
    String EXTRA_EXTERNAL_MAP_MODE = "EXTRA_EXTERNAL_MAP_MODE";/*仪表模式
            0 : 2D车首上
            1 : 2D北首上
            2 : 3D车首上
            其它值表示取消固定*/
    String EXTRA_EXTERNAL_MAP_POSITION = "EXTRA_EXTERNAL_MAP_POSITION"; /*仪表车标位置
            1 : 左侧
            2 : 居中
            3 : 右侧
            其它值表示取消固定*/
    String EXTRA_EXTERNAL_MAP_LEVEL = "EXTRA_EXTERNAL_MAP_LEVEL"; //仪表比例尺级别（0~17）
    //2.1.2 设置拓展屏的路口大图开启/关闭
    int TYPE_EXTERNAL_SCREEN_SWITCH = 10105;
    String EXTRA_EXTERNAL_CROSS_CONTROL = "EXTRA_EXTERNAL_CROSS_CONTROL"; /*: 仪表路口大图开关
                                                        true: 显示
                                                        false : 不显示*/
    //2.1.3 设置拓展屏的路口大图类型
    int TYPE_EXTERNAL_SCREEN_TYPE = 10106;
    String EXTRA_EXTERNAL_CROSS_TYPE = "EXTRA_EXTERNAL_CROSS_TYPE"; /*: 路口大图类型
                                                        0 : 栅格图
                                                        1 : 矢量图
                                                        2 : 3D实景*/
    //2.3.3 当前行政区域信息请求
    int TYPE_GET_REGION_INFO = 10029;
    //2.3.5 传入经纬度查询当前行政区域
    int TYPE_GET_REGION_INFO_BY_POINT = 10077;
    //2.3.6  发起路况查询请求
    int TYPE_GET_TRAFFIC_CONDITION = 12401;
    String EXTRA_TRAFFIC_CONDITION = "EXTRA_TRAFFIC_CONDITION";//火车站堵不堵（String类型）
    //2.3.7 前方路况查询
    int TYPE_GET_TRAFFIC_CONDITION_AHEAD = 10109;
    //2.4.1  回家回公司的弹条消息的控制，如出发、取消
    int TYPE_CONTROL_MESSAGE = 12004;
    String EXTRA_HOME_OR_COMPANY_WHAT = "EXTRA_HOME_OR_COMPANY_WHAT";//true：出发；false：取消
    //2.4.2 油量预警弹条控制消息
    // 加油无忧字段说明
    int TYPE_WARING_OIL_CONTROL = 13005;
    String CARD_TYPES = "CARD_TYPES"; // 加油无忧卡片类型
    String EXTRA_KEYWORD = "EXTRA_KEYWORD"; // 搜索关键字 加油站
    String EXTRA_MYLOCLAT = "EXTRA_MYLOCLAT";//纬度
    String EXTRA_MYLOCLON = "EXTRA_MYLOCLON"; // 经度
    String EXTRA_SEARCHTYPE = "EXTRA_SEARCHTYPE";// 搜索类型
    String EXTRA_MAXCOUNT = "EXTRA_MAXCOUNT"; // 搜索最大个数
    String IS_CANCEL = "IS_CANCEL";//   1/0 1：取消、0：规划 // 是否取消

    //2.4.3.2  接收第三方对更优路线（躲避拥堵）弹窗的控制，如选择避开或者忽略
    int TYPE_AVOID_CONTROL = 12106;
    String EXTRA_AVOID_TRAFFIC_JAM_CONTROL = "EXTRA_AVOID_TRAFFIC_JAM_CONTROL";//true：避开；false：忽略

    //2.4.4.2 目的地附近停车场弹窗选择  第三方通过接口可传入停车场选择的结果，auto执行对应操作
    int Type_SELCET_PARK = 10052;
    String EXTRA_PARK_DATA = "EXTRA_PARK_DATA";//(-1：忽略;0：第一个停车场；1：第二个停车场；2：第三停车场)

    //2.4.5.2 接收到第三方应用关于续航消息的选择，以此判断是否续航
    int TYPE_GUIDE = 10049;
    String EXTRA_ENDURANCE_DATA = "EXTRA_ENDURANCE_DATA";//true(继续),false(取消)
    //2.4.6.2 第三方可通过接口传入选择结果，调用auto执行对应操作。
    int TYPE_SELECT_ROUTE= 10050;
    String EXTRA_SEND2CAR_DATA = "EXTRA_SEND2CAR_DATA";//true(规划路线);false(取消)
    //2.5.1 支持第三方通过接口主动获取auto是否处于前台可见状态/是否处在导航中。
    int TYPE_GET_APP_STATE = 12404;
   String  EXTRA_REQUEST_AUTO_STATE="EXTRA_REQUEST_AUTO_STATE";//int
                                /**
                                 * EXTRA_REQUEST_AUTO_STATE第三方查询地图的状态
                                 * 0：前后台状态查询
                                 * 1：导航状态查询
                                 * 2：导航中路线信息查询
                                 */

                                
    //=========================================================
    /**
     * 1.1设置NavApp授权序列号
     */
    int IA_CMD_SET_AUTHORIZE_SERIAL_NUMBER = 0x1000;

    /**
     * 1.2设置NavApp地图显示模式：白天/黑夜
     */
    int IA_CMD_SET_MAP_DISPLAY_MODE = 0x1001;

    /**
     * 1.3设置NavApp音量
     */
    int IA_CMD_SET_SOUND_VOLUME = 0x1002;

    /**
     * 1.4设置NavApp静音开/关
     */
    int IA_CMD_SET_MUTE_SWITCH = 0x1003;

    /**
     * 1.5设置NavApp算路避让规则
     */
    int IA_CMD_SET_ROUTE_CONDITION = 0x1004;

    /**
     * 1.6设置NavApp的时间信息
     */
    int IA_CMD_SET_TIME_INFOMATION = 0x1005;

    /**
     * 1.7设置NavApp的输入法
     */
    int IA_CMD_SET_TYPE_WRITING = 0x1006;

    /**
     * 1.8设置NavApp的语言
     */
    int IA_CMD_SET_LANGUAGE = 0x1007;
    /**
     * 1.9设置NavApp的多媒体信息
     */
    int IA_CMD_SET_MULTIMEDIA_INFOMATION = 0x1008;

    /**
     * 1.10设置NavApp导航信息发送开关状态
     */
    int IA_CMD_SET_TBT_SWITCH_STATUS = 0x1009;

    /**
     * 1.11设置NavApp导航播报语音类型
     */
    int IA_CMD_SET_GUIDENCE_SOUND_TYPE = 0x100A;

    /**
     * 1.12更新交互目标的写状态
     */
    public static final int IA_CMD_UPDATE_IATARGET_WRITING_STATUS = 0x100B;
    public static String IA_CMD_UPDATE_IATARGET_WRITING_STATUS_ = "{\"writed\":";
    public static String IA_CMD_UPDATE_IATARGET_WRITING_STATUS_YES = "{\"writed\":1}";
    public static String IA_CMD_UPDATE_IATARGET_WRITING_STATUS_NO = "{\"writed\":2}";

    /**
     * 1.13更新交互目标的路径状态
     */
    public static final int IA_CMD_UPDATE_IATARGET_ROUTE_STATUS = 0x100C;
    public static String IA_CMD_UPDATE_IATARGET_ROUTE_STATUS_ = "{\"route\":";
    public static String IA_CMD_UPDATE_IATARGET_ROUTE_STATUS_YES = "{\"route\":1}";
    public static String IA_CMD_UPDATE_IATARGET_ROUTE_STATUS_NO = "{\"route\":2}";

    /**
     * 2.1启动/退出NavApp
     */
    public static final int IA_CMD_STARTUP_OR_EXIT = 0x2000;
    public static String IA_CMD_STARTUP = "{\"operationType\":1}";
    public static String IA_CMD_EXIT = "{\"operationType\":2}";

    /**
     * 2.2保存NavApp数据
     */
    public static final int IA_CMD_SAVE_DATA = 0x2001;
    public static String IA_CMD_SAVE_DATA_CONTENT = "";

    /**
     * 2.3显示/隐藏NavApp
     */
    public static final int IA_CMD_SHOW_OR_HIDE = 0x2002;
    public static String IA_CMD_SHOW = "{\"operationType\":1}";
    public static String IA_CMD_HIDE = "{\"operationType\":2}";

    /**
     * 2.4缩小/放大地图
     */
    int IA_CMD_ZOOM_MAP = 0x2003;
    /**
     * 2.5获取NavApp的导航引导信息
     */
    public static final int IA_CMD_GET_NAVI_INFO = 0x2004;
    public static String IA_CMD_GET_NAVI_INFO_CONTENT = "";

    /**
     * 2.6重播当前导航语音提示语句
     */
    public static final int IA_CMD_REPEAT_NAVI_SOUND = 0x2005;
    public static String IA_CMD_REPEAT_NAVI_SOUND_CONTENT = "";

    /**
     * 2.7画面迁移
     */
    public static final int IA_CMD_MOVE_UI = 0x2006;
    public static String IA_CMD_MOVE_UI_CONTENT = "";

    /**
     * 2.8改变NavApp的UI视觉属性
     */
    public static final int IA_CMD_CHANGE_NAVI_UI_VISUAL_ATTRIBUTES = 0x2007;
    public static String IA_CMD_CHANGE_NAVI_UI_VISUAL_ATTRIBUTES_CONTENT = "";

    /**
     * 2.9NavApp显示动态信息
     */
    public static final int IA_CMD_SHOW_DANAMIC_INFOMATION = 0x2008;
    public static String IA_CMD_SHOW_DANAMIC_INFOMATION_CONTENT = "";

    /**
     * 2.10NavApp启动/停止导航
     */
    public static final int IA_CMD_SART_OR_STOP_NAVI_GUIDE = 0x2009;
    public static String IA_CMD_SART_OR_STOP_NAVI_GUIDE_START = "{\"operationType\":1,\"guideType\":2}";//启动真实导航
    public static String IA_CMD_SART_OR_STOP_NAVI_GUIDE_END = "{\"operationType\":2,\"guideType\":2}";//关闭真实导航
    public static String IA_CMD_SART_OR_STOP_NAVI_GUIDE_SIMULATION_START = "{\"operationType\":1,\"guideType\":1}";//启动模拟导航
    public static String IA_CMD_SART_OR_STOP_NAVI_GUIDE_SIMULATION_END = "{\"operationType\":2,\"guideType\":1}";//关闭模拟导航

    /**
     * 2.11获取NavApp的当前状态
     */
    public static final int IA_CMD_GET_NAVI_STATUAS = 0x200A;
    public static String IA_CMD_GET_NAVI_STATUAS_CONTENT = "";

    /**
     * 2.12NavApp显示交互目标的音量
     */
    public static final int IA_CMD_SHOW_TARGET_SOUND_VOLUME = 0x200B;
    public static String IA_CMD_SHOW_TARGET_SOUND_VOLUME_CONTENT = "{\"targetMaxVolume\":100,\"targetCurrentVolume\":";

    /**
     * 2.13删除NavApp的当前路线
     */
    public static final int IA_CMD_DELET_NAVI_ROUTE = 0x200C;
    public static String IA_CMD_DELET_NAVI_ROUTE_CONTENT = "";

    /**
     * 2.14获取GPS信息
     */
    public static final int IA_CMD_GET_GPS_INFO = 0x200D;
    public static String IA_CMD_GET_GPS_INFO_CONTENT = "";

    /**
     * 2.15切换地图视图模式
     */
    int IA_CMD_SET_MAP_VIEW_MODE = 0x200E;

    /**
     * 2.16条件算路
     */
    public static final int IA_CMD_ROUTE_BY_CONDITION = 0x200F;
    public static String IA_CMD_ROUTE_BY_CONDITION_1 = "{\"endPoint\":{\n" +
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
     * 2.17设置NavApp画面所的在显示屏幕
     */
    public static final int IA_CMD_SET_DISPLAY_SCREEN_FOR_NAVAPP = 0x2010;
    public static String IA_CMD_SET_DISPLAY_SCREEN_FOR_NAVAPP_SHOW1 = "{\"iaDisPlayScreenId\":1}";
    public static String IA_CMD_SET_DISPLAY_SCREEN_FOR_NAVAPP_SHOW2 = "{\"iaDisPlayScreenId\":2}";

    /**
     * 2.18 List动画操作
     */
    public static final int IA_CMD_CURRENT_UI_LIST_ANIMATION = 0x2011;
    public static String IA_CMD_CURRENT_UI_LIST_ANIMATION_ONE = "{\"iaListAnimaType\": [4]}";//当前画面中的第1个List垂直向下翻页
    public static String IA_CMD_CURRENT_UI_LIST_ANIMATION_TWO = "{\"iaListAnimaType\": [0,4,8]}";//当前画面中的第2个List垂直向下翻页，第3个List垂直向下滚动

    /**
     * 2.19 收藏点导航
     */
    public static final int IA_CMD_FAVORITE_GUIDANCE = 0x2012;
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
     * 2.20 定位自车
     */
    public static final int IA_CMD_LOCATE_THE_CAR = 0x2013;

    /**
     * 2.21 NavApp执行多媒体控制
     */
    public static final int IA_CMD_NAVAPP_CONTROL_MULTIMEDIA = 0x2014;
    public static String IA_CMD_NAVAPP_CONTROL_MULTIMEDIA_OPEN_BLUETOOTH = "{" +//打开蓝牙音乐
            "  \"iaMultimediaOpType\":4," +
            "  \"iaMultiMediaApp\":4" +
            "}";
    public static String IA_CMD_NAVAPP_CONTROL_MULTIMEDIA_CLOSE_VODEO = "{" + //关闭视频
            "  \"iaMultimediaOpType\":5," +
            "  \"iaMultiMediaApp\":10" +
            "}";

    /**
     * 2.22 查看帮助信息
     */
    public static final int IA_CMD_BROWSE_HELP_INFORMATION = 0x2015;
    public static String IA_CMD_BROWSE_HELP_INFORMATION_USE_NAV = "{\"iaHelpType\":1}";//查看如何使用NavApp
    public static String IA_CMD_BROWSE_HELP_INFORMATION_PLAY_MUSIC = "{\"iaHelpType\":6}";//查看如何播放音乐
    /**
     * 2.23 指定线路导航
     */
    public static final int IA_CMD_START_GUIDING_WITH_ROUTE = 0x2016;
    public static String IA_CMD_START_GUIDING_WITH_ROUTE_CONTENT = "{\n" +
            "  \"routeNumber\": 2,\n" +
            "  \"guideType\": 1\n" +
            "}";
    /**
     * 2.24 切换NavApp窗口模式
     */
    int IA_CMD_CHANGE_NAVAPP_WINDOW_MODE = 0x2017;

    /**
     * 2.25 获取NavApp窗口模式
     */
    int IA_CMD_GET_NAVAPP_WINDOW_MODE = 0x2018;

    /**
     * 2.26 获取收藏点
     */
    public static final int IA_CMD_GET_FAVORITE_POINT = 0x2019;
    public static String IA_CMD_GET_FAVORITE_POINT_CONTENT = "{\"iaFavType\": 1}";
    /**
     * 2.27 更新收藏点
     */
    public static final int IA_CMD_UPDATE_FAVORITE_POINT = 0x201A;
    public static String IA_CMD_UPDATE_FAVORITE_POINT_CONTENT = "{\n" +
            "  \"iaFavType\": 1,\n" +
            "  \"favContent\":{\n" +
            "      \"iaPoiType\": 78,\n" +
            "      \"iaPoiPos\": {\n" +
            "        \"longitude\": 12141938,\n" +
            "        \"latitude\": 3116152\n" +
            "      },\n" +
            "      \"iaPoiDisPos\": {\n" +
            "        \"longitude\": 12141708,\n" +
            "        \"latitude\": 3116179\n" +
            "      },\n" +
            "      \"iaPoiId\": 4294967295,\n" +
            "      \"iaChildPoiNum\": 0,\n" +
            "      \"iaCompoundId\": 0,\n" +
            "      \"iaPoiName\": \"上海师范大学徐汇校区\",\n" +
            "      \"iaPoiAdress\": \"上海市徐汇区桂林路100号\",\n" +
            "      \"iaPoiPhone\": \"\",\n" +
            "      \"iaRegionName\": \"上海市徐汇区\",\n" +
            "      \"iaPoiTypeName\": \"大专院校\"\n" +
            "   }\n" +
            "}";
    /**
     * 2.28 键盘输入
     */
    public static final int IA_CMD_UPDATE_KEYBOARD_INPUT = 0x202A;
    public static String IA_CMD_UPDATE_KEYBOARD_INPUT_CONTENT = "{\n\"iaKeyboardInput\":\"人民广场\"\n}";
    /**
     * 3.0条件搜索POI
     */
    public static final int IA_CMD_SEARCH_POI_BY_CONDITION = 0x3000;
    public static String IA_CMD_SEARCH_POI_BY_CONDITION_CONTETN = "{\n" +
            "  \"poiSearchType\": 2,\n" +
            "  \"poiSearchCenter\": {\n" +
            "    \"centerType\": 1,\n" +
            "    \"centerExpression\": 1,\n" +
            "    \"centerPoint\": \"五角场\"\n" +
            "  },\n" +
            "  \"poiSearchScope\": {\n" +
            "    \"scopeType\": 2,\n" +
            "    \"scopeExpression\": 1,\n" +
            "    \"serchScope\": \"上海\"\n" +
            "  },\n" +
            "  \"poiSearchContents\": \"麦当劳\",\n" +
            "  \"searchRadius\": 10000,\n" +
            "  \"poiDataPageSize\": 10\n" +
            "}";

    /**
     * 3.2 选择POI条件搜索时的搜索中心点
     */
    public static final int IA_CMD_SELECT_POI_SEARCH_CENTER = 0x3001;
    public static String IA_CMD_SELECT_POI_SEARCH_CENTER_CONTENT = "{\"poiSearchCenterIndex\":2}";
    /**
     * 3.3 获取给定页面的POI页数据
     */
    public static final int IA_CMD_GET_POI_PAGE_DATA = 0x3002;
    public static String IA_CMD_GET_POI_PAGE_DATA_CONTENT = "{\n" +
            " \"poiDataType\": 2,\n" +
            " \"poiDataPageNum\": 2\n" +
            "}\n";
    /**
     * 3.4 获取指定经纬度位置详细信息
     */
    public static final int IA_CMD_GET_SPECIFIC_POINT_INFO = 0x3003;
    public static String IA_CMD_GET_SPECIFIC_POINT_INFO_CONTENT = "{\n" +
            "    \"longitude\": 0,\n" +
            "    \"latitude\" : 0\n" +
            "}";


    /**
     * 4.1 开启/关闭超速提醒
     */
    public static final int IA_CMD_ENABLE_SPEEDLIMIT_WARNING = 0x4000;
    public static String IA_CMD_ENABLE_SPEEDLIMIT_WARNING_CONTENT = "{\"enable\":true}";
    /**
     * 4.2 开启/关闭电子警察
     */
    public static final int IA_CMD_ENABLE_CAMERA_WARNING = 0x4001;
    public static String IA_CMD_ENABLE_CAMERA_WARNING_CONTENT = "{\"enable\":true}";
    /**
     * 4.3 开启/关闭实时路况
     */
    int IA_CMD_ENABLE_TMC = 0x4002;
    /**
     * 4.4 设置路径浏览模式
     */
    public static final int IA_CMD_SET_ROUTE_VIEW_MODE = 0x4003;
    public static String IA_CMD_SET_ROUTE_VIEW_MODE_CONTENT = "{\"routeviewMode\":2}";
    /**
     * 4.5 设置NavApp导航播报频率
     */
    public static final int IA_CMD_SET_BOARDCAST_MODE = 0x4004;
    public static String IA_CMD_SET_BOARDCAST_MODE_CONSTANT = "{\"boardcastMode\":1}";
    /**
     * 4.6 获取剩余路线信息
     */
    public static final int IA_CMD_GET_REMAINING_ROUTEINFO = 0x4005;
    public static String IA_CMD_GET_REMAINING_ROUTEINFO_CONTANT = "";
    /**
     * 4.7 开启/关闭避开限行道路功能
     */
    public static final int IA_CMD_ENABLE_AVOID_RESTRICTION_ROADS = 0x4006;
    public static String IA_CMD_ENABLE_AVOID_RESTRICTION_ROADS_CONTENT = "{\"enable\":true}";


}
