package com.fundrive.navaidlclient.bean;

/**
 * Created by fduser on 2019/5/20.
 * 客户端需要接收服务端返回数据的地方都需要实现此接口(并注册)
 */

public interface Observer {
    void update(int cmd,String message);
}
