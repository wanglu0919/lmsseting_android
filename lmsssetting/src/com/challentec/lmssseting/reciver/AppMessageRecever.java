package com.challentec.lmssseting.reciver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.challentec.lmssseting.bean.ResponseData;
import com.challentec.lmssseting.listener.AppMessageLinstener;

/**
 * socket返回消息广播接收者
 * 
 * @author 泰得利通 wanglu
 * 
 */
public class AppMessageRecever extends BroadcastReceiver {
	public static final String DATA_KEY = "dataKey";
	private AppMessageLinstener appMessageLinstener;//消息处理监听对象
	public static final String ACTION_STRING = "LMSS_SETTING_MESSAGE";

	public void setAppMessageLinstener(AppMessageLinstener appMessageLinstener) {
		this.appMessageLinstener = appMessageLinstener;
	}

	@Override
	public void onReceive(Context context, Intent intent) {

		ResponseData responseData = (ResponseData) intent.getExtras().get(
				DATA_KEY);//返回消息封装

		if (appMessageLinstener != null) {
			appMessageLinstener.onRespnseDataReceve(responseData);
		}

	}

}
