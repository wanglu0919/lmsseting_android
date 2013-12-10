package com.challentec.lmsseting.net;

import android.os.Handler;
import android.os.Message;

import com.challentec.lmsseting.util.UIHelper;
import com.challentec.lmssseting.app.AppContext;

/**
 * 异步handler处理类
 * 
 * @author 泰得利通 wanglu
 * 
 */
public class SynHandler extends Handler {

	private AppContext context;

	public void setContext(AppContext context) {
		this.context = context;
	}

	public static final int IOEXCEPTION = 0x01;// ioExctption
	public static final int SEND_SUCCESS = 0x02;// 发送数据成功
	public static final int UN_CONNECT = 0x03;// 未连接，连接断开
	public static final int CONNECTION_SUCCESS = 0x04;// 连接服务器成功
	public static final int UNKNOWNHOST = 0x05;// 服务器不存在
	public static final int CONET_SEVER_TIME_OUT = 0x06;// 连接服务器超时
	public static final int READ_DATA_TIME_OUT = 0x07;// 读取数据超时
	public static final int NO_NET = 0x08;// 没有网络连接

	public SynHandler() {

	}

	@Override
	public void handleMessage(Message msg) {
		switch (msg.what) {

		case IOEXCEPTION:// io 异常
			onIOException("网络异常");

			break;
		case SEND_SUCCESS:// 写出数据成功

			onSendSuccess();
			break;
		case UN_CONNECT:// 未连接
			onUnConnect("未连接");

			break;
		case CONNECTION_SUCCESS:// 连接服务器成功
			onConnectSuccess("连接服务器成功！");

			break;

		case UNKNOWNHOST:// 服务器不存在
			onUNKnowHost("服务器不存在");

			break;

		case CONET_SEVER_TIME_OUT:// 连接服务器操时
			onConnectServerTimeOUt("连接服务器超市");

			break;

		case READ_DATA_TIME_OUT:// 读取数据超时
			onReadDataTimeOut("读取数据超时");

			break;
		case NO_NET:// 没有网络连接
			onNoNet("没有网路连接");

			break;

		}

		onFianly();

	}

	/**
	 * 写数据成功
	 * 
	 * @author 泰得利通 wanglu
	 */
	public void onSendSuccess() {

	}

	/**
	 * 没有网络连接处理
	 * 
	 * @author 泰得利通 wanglu
	 * @param code
	 */
	private void onNoNet(String msg) {
		onToast(msg);

	}

	/**
	 * 读取数据超时处理
	 * 
	 * @author 泰得利通 wanglu
	 * @param code
	 */
	public void onReadDataTimeOut(String msg) {
		onToast(msg);

	}

	/**
	 * 最终执行方法
	 * 
	 * @author 泰得利通 wanglu
	 */

	public void onFianly() {

	}

	/**
	 * 连接服务器超时处理
	 * 
	 * @author 泰得利通 wanglu
	 * @param connectServerTimeOut
	 */
	private void onConnectServerTimeOUt(String msg) {
		onToast(msg);

	}

	/**
	 * 主机不存在处理
	 * 
	 * @author 泰得利通 wanglu
	 */
	public void onUNKnowHost(String msg) {
		onToast(msg);

	}

	/**
	 * 异常处理
	 * 
	 * @author 泰得利通 wanglu
	 * @param code
	 */
	protected void onToast(String msg) {
		UIHelper.showToask(context, msg);

	}

	/**
	 * 连接服务器成功
	 * 
	 * @author 泰得利通 wanglu
	 */
	public void onConnectSuccess(String msg) {

		onToast(msg);
	}

	/**
	 * 处理没有返回数据
	 * 
	 * @author 泰得利通 wanglu
	 */
	public void onSuccess() {
	}

	/**
	 * IO异常处理
	 * 
	 * @author 泰得利通 wanglu
	 * @param code
	 */
	public void onIOException(String msg) {

		onToast(msg);

	}

	/**
	 * 未连接处理
	 * 
	 * @author 泰得利通 wanglu
	 * @param code
	 */
	public void onUnConnect(String msg) {
		onToast(msg);
	}
}
