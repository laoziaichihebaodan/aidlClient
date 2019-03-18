package com.fundrive.navaidlclient.modules;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.fundrive.navaidlclient.Constant;
import com.fundrive.navaidlclient.R;
import com.fundrive.navaidlclient.adapter.AnimationListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ListAnimationActivity extends BaseActivity {

    @BindView(R.id.lv_item)
    ListView lvItem;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    private ArrayList<Integer> arrayList = new ArrayList<>();
    private AnimationListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_animation);
        ButterKnife.bind(this);
        tvTitle.setText("List动画");
        arrayList.add(1);
        adapter = new AnimationListAdapter(arrayList);
        lvItem.setAdapter(adapter);
    }

    @OnClick({R.id.btn_add, R.id.btn_sub, R.id.btn_commit, R.id.btn_return})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_add:
                arrayList.add(1);
                adapter.notifyDataSetChanged();
                break;
            case R.id.btn_sub:
                if (arrayList.size() > 0) {
                    arrayList.remove(arrayList.size() - 1);
                }
                adapter.notifyDataSetChanged();
                break;
            case R.id.btn_commit:
                makeJson();
                break;
            case R.id.btn_return:
                finish();
                break;
        }
    }

    //组装json
    private void makeJson() {
        JSONObject cmdJson = new JSONObject();
        JSONObject jsonObject = new JSONObject();
        JSONArray array = new JSONArray();
        for (int i = 0; i < arrayList.size(); i++) {
            array.put(arrayList.get(i));
        }
        try {
            jsonObject.put("iaListAnimaType", array);
            cmdJson.put(Constant.CMD_KEY, Constant.IA_CMD_CURRENT_UI_LIST_ANIMATION);
            cmdJson.put(Constant.JSON_KEY, jsonObject);

            String message = cmdJson.toString();
            sendMessage(message);
            Log.d(TAG, "makeJson: " + message);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
