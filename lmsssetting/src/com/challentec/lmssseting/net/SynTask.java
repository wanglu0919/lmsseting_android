package com.challentec.lmssseting.net;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.List;

import android.os.Message;

import com.challentec.lmssseting.api.Protocol;
import com.challentec.lmssseting.app.AppContext;
import com.challentec.lmssseting.bean.ResponseData;
import com.challentec.lmssseting.exception.ConnectServerTimeOutException;
import com.challentec.lmssseting.exception.ReadDataException;
import com.challentec.lmssseting.util.HandlerMessage;
import com.challentec.lmssseting.util.LogUtil;

/**
 * app异步任务
 * 
 * @author 泰得利通 wanglu
 * 
 */
public class SynTask {

	private SynHandler handler;//
	private SocketClient socketClient;
	private AppContext context;

	public SynTask(SynHandler handler, AppContext context) {
		this.handler = handler;
		this.handler.setContext(context);
		this.context = context;

		socketClient = SocketClient.getSocketClient();
	}

	public SynTask(AppContext context) {

		this.context = context;

		socketClient = SocketClient.getSocketClient();
	}

	/**
	 * 设置handler
	 * 
	 * @author 泰得利通 wanglu
	 * @param handler
	 */
	public void setHandler(SynHandler handler) {
		this.handler = handler;
		this.handler.setContext(this.context);
	}

	/**
	 * 写出数据
	 * 
	 * @author 泰得利通 wanglu
	 * @param hexStr
	 *            16进制数据字符串
	 */
	public void writeData(final String hexStr) {

		if (!context.isNetworkConnected()) {// 检查网络连接
			if (handler != null) {
				Message msg = handler.obtainMessage();
				msg.what = SynHandler.NO_NET;
				handler.sendMessage(msg);

			}

			return;

		}

		if (!socketClient.isConnected()) {// 检查连接受否断开
			if (handler != null) {
				Message msg = handler.obtainMessage();
				msg.what = SynHandler.UN_CONNECT;
				handler.sendMessage(msg);
			}

			return;
		}
		new Thread(new Runnable() {

			@Override
			public void run() {

				try {
					socketClient.writeHexStr(hexStr.toLowerCase());
					if (handler != null) {
						Message msg = handler.obtainMessage();
						msg.what = SynHandler.SEND_SUCCESS;
						handler.sendMessage(msg);
					}

				} catch (SocketTimeoutException e) {
					e.printStackTrace();
					if (handler != null) {
						Message msg = handler.obtainMessage();
						msg.what = SynHandler.READ_DATA_TIME_OUT;// 读取数据超时处理
						handler.sendMessage(msg);
					}

				} catch (IOException e) {

					e.printStackTrace();
					if (handler != null) {
						Message msg = handler.obtainMessage();
						msg.what = SynHandler.IOEXCEPTION;
						handler.sendMessage(msg);
					}

				}

			}
		}).start();

	}

	/**
	 * 启动读取数据线程
	 * 
	 * @author 泰得利通 wanglu
	 */
	private void startRead() {

		if (!context.isNetworkConnected()) {// 检查网络连接

			return;
		}

		if (!socketClient.isConnected()) {// 检查连接受否断开

			return;
		}

		/**
		 * 该线程死循环，阻塞监听服务器返回数据，当读取捕获ReadDataException
		 * 表示连接断开，发送服务器挂了消息，通知app网络监听广播接收者处理
		 */
		new Thread(new Runnable() {

			@Override
			public void run() {

				LogUtil.i(LogUtil.LOG_TAG_READ, "读取线程启动");

				while (true) {// 循环从缓冲区读取消息
					try {
						if (!context.isNetworkConnected()
								|| !socketClient.isConnected()) {// 检查网络连接和socket状态
							LogUtil.i(LogUtil.LOG_TAG_READ, "读取连接断开或未连接");
							continue;
						}
						LogUtil.i(LogUtil.LOG_TAG_READ, "读取数据等待中....");
						String responseData = socketClient.readData();// 该方法为阻塞方法，阻塞读取服务器返回数据
						LogUtil.i(LogUtil.LOG_TAG_READ, "读到了数据:" + responseData);

						if (responseData != null) {
							List<ResponseData> rds = Protocol
									.pase(responseData);

							if (rds.size() > 0) {

								for (ResponseData rd : rds) {
									HandlerMessage.handlerMessage(context, rd);
								}

							}

						}
					} catch (IOException e) {

						LogUtil.i(LogUtil.LOG_TAG_READ, "读取数据异常");
					} catch (ReadDataException e) {
						e.printStackTrace();
						LogUtil.i(LogUtil.LOG_TAG_READ, "服务端挂了");
						HandlerMessage.handlerUNConnectMessage(context);// 发送服务挂了消息
						break;
					}
				}
			}
		}).start();

	}

	/**
	 * 连接服务
	 * 
	 * @author 泰得利通 wanglu
	 */
	public void connectServer(final SocketClient socketClient) {

		if (!context.isNetworkConnected()) {// 检查网络连接

			handler.sendEmptyMessage(SynHandler.NO_NET);
			return;
		}

		socketClient.dispose();// 将原有soket连接 资源释放

		new Thread(new Runnable() {

			@Override
			public void run() {
				try {

					socketClient.connect();
					if (handler != null) {
						handler.sendEmptyMessage(SynHandler.CONNECTION_SUCCESS);
					}

					startRead();// 启动读取数据线程
				} catch (UnknownHostException e) {

					e.printStackTrace();
					if (handler != null) {
						handler.sendEmptyMessage(SynHandler.UNKNOWNHOST);
					}

				} catch (IOException e) {
					e.printStackTrace();
					if (handler != null) {
						handler.sendEmptyMessage(SynHandler.IOEXCEPTION);
					}

				} catch (ConnectServerTimeOutException e) {// 连接服务器超时

					e.printStackTrace();

					if (handler != null) {
						handler.sendEmptyMessage(SynHandler.CONET_SEVER_TIME_OUT);
					}
				}
			}
		}).start();

	}

}
