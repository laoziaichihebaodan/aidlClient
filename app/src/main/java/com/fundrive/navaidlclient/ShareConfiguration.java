package com.fundrive.navaidlclient;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class ShareConfiguration {

	private SharedPreferences sp;
	private Editor editor;
	public static final int MODEL_NONE_KOWN = 0;
	public static final int MODEL_SERVER = 1;
	public static final int MODEL_CLIENT = 2;
	public static final String SETTING_INFOS = "SETTING_Infos";


	@SuppressLint("CommitPrefEdits")
	public ShareConfiguration(Context context, String file) {
		sp = context.getSharedPreferences(file, 0);
		editor = sp.edit();
	}

	//设备模式
	public int getDeviceModel() {
		return sp.getInt("DeviceModel", MODEL_NONE_KOWN);
	}

	public void setDeviceModel(int device_model) {
		editor.putInt("DeviceModel", device_model);
		editor.commit();
	}
}
