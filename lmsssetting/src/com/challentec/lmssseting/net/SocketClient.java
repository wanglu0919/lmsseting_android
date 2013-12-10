package com.challentec.lmssseting.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import com.challentec.lmssseting.exception.ReadDataException;
import com.challentec.lmssseting.util.DataPaseUtil;
import com.challentec.lmssseting.util.LogUtil;

/**
 * socket通信
 * 
 * @author 泰得利通 wanglu
 * 
 */
public class SocketClient {

	private Socket socket;
	private InputStream is;
	private OutputStream os;
	private static SocketClient instance;
	private static final int CONNECT_SERVER_TIME_OUT = 10000;// 连接服务器超时时间

	private SocketClient() {
	}

	/**
	 * 单利
	 * 
	 * @author 泰得利通 wanglu
	 * @return
	 */
	public static synchronized SocketClient getSocketClient() {

		if (instance == null) {
			instance = new SocketClient();
		}

		return instance;
	}

	/**
	 * 连接服务器
	 * 
	 * @param ip
	 *            ip地址
	 * @param port
	 *            端口
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public void connect(String ip, int port) throws UnknownHostException,
			IOException {

		boolean flag = true;

		while (flag) {// 连接服务器
			try {
				LogUtil.i(LogUtil.LOG_TAG_CONNECT, "连接服务器一次");
				Thread.sleep(500);
				socket = null;
				socket = new Socket();

				socket.connect(new InetSocketAddress(ip, port),
						CONNECT_SERVER_TIME_OUT);

				is = socket.getInputStream();
				os = socket.getOutputStream();
				flag = false;
			} catch (ConnectException e) {
				e.printStackTrace();
				LogUtil.i(LogUtil.LOG_TAG_CONNECT, "连接服务器异常");

			} catch (java.net.SocketException e) {
				e.printStackTrace();
				LogUtil.i(LogUtil.LOG_TAG_CONNECT, "连接服务器异常");
			} catch (InterruptedException e) {
				e.printStackTrace();

				LogUtil.i(LogUtil.LOG_TAG_CONNECT, "连接服务器异常");

			} catch (SocketTimeoutException e) {

				LogUtil.i(LogUtil.LOG_TAG_CONNECT, "连接服务器超时");
			}

		}

	}

	/**
	 * 写出16进制字符串
	 * 
	 * @author 泰得利通 wanglu
	 * @param hexStr
	 *            16进制字符串数据
	 * @throws IOException
	 */
	public void writeHexStr(String hexStr) throws IOException {
		if (os != null) {
			LogUtil.i(LogUtil.LOG_TAG_SEND, hexStr);// 打印日志
			os.write(DataPaseUtil.hexString2byte(hexStr));// 将16进值字符串数据转化为对应自己数组些出去
			os.flush();
		}
	}

	/**
	 * 读取数据
	 * 
	 * @author 泰得利通 wanglu
	 * @return 返回16进制字符串
	 * @throws IOException
	 * @throws ReadDataException
	 */
	public String readData() throws IOException, ReadDataException {
		byte buffer[] = new byte[1024];
		int len = is.read(buffer);// 输入流读取数据
		String responseData = null;
		if (len > 0) {
			responseData = DataPaseUtil.bytes2hexStr(buffer).substring(0,
					len * 2);
			LogUtil.i(LogUtil.LOG_TAG_READ, "读取的数据：" + responseData);// 打印日志
		} else {
			throw new ReadDataException();// 抛出读取数据异常，服务这时一般标识socket连接异常，soket断掉了.
		}
		return responseData;

	}

	/**
	 * 判断socket是否连接
	 * 
	 * @author 泰得利通 wanglu
	 * @return
	 */
	public boolean isConnected() {
		if (socket != null) {
			return socket.isConnected();
		}

		return false;
	}

	public Socket getSocket() {

		return socket;
	}

	/**
	 * 关闭socket 释放资源
	 * 
	 * @author 泰得利通 wanglu
	 * @throws IOException
	 */
	public void dispose() {
		try {
			if (socket != null && socket.isConnected()) {

				is.close();
				os.close();
				socket.close();
				socket = null;
				is = null;
				os = null;
			}
		} catch (IOException e) {

		}

	}

}
