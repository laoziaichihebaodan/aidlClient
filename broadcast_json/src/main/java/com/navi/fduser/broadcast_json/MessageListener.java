package com.navi.fduser.broadcast_json;

import org.json.JSONObject;

public interface MessageListener {
    void onGetMessage(int cmd ,JSONObject messageJson);
}
