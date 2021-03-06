package com.challentec.lmssseting.util;

import android.content.Context;
import android.content.Intent;

import com.challentec.lmssseting.bean.ResponseData;
import com.challentec.lmssseting.reciver.AppMessageRecever;

/**
 * 分发消息数据类
 * 
 * @author 泰得利通 wanglu
 * 
 */
public class HandlerMessage {

	/**
	 * 分发数据,将消息发送给注册AppMessageRecever 的广播接收者处理
	 * 
	 * @author 泰得利通 wanglu
	 * @param context
	 * @param responseData
	 */
	public static void handlerMessage(Context context, ResponseData responseData) {
		
		
		Intent intent = new Intent();
		intent.setAction(AppMessageRecever.ACTION_STRING);
		intent.putExtra(AppMessageRecever.DATA_KEY, responseData);
		context.sendBroadcast(intent);// 通知广播
		
	}

	/**
	 * 分发网络连接断掉时的通信 将消息发给注册，AppConnectStateRecever 广播接收者处理网路断开消息
	 * 
	 * @author 泰得利通 wanglu
	 * @param context
	 */
	public static void handlerUNConnectMessage(Context context) {
		/*
		Intent intent = new Intent();
		intent.setAction(AppConnectStateRecever.ACTION_STRING);
		context.sendBroadcast(intent);// 网络连接端口通知广播
		LogUtil.i(LogUtil.LOG_TAG_CONNECT, "发送了服务端挂了消息");
		*/
	}

}
