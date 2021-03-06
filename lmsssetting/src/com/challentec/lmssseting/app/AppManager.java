package com.challentec.lmssseting.app;

import java.util.Stack;
import java.util.UUID;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.IntentFilter;
import android.telephony.TelephonyManager;

import com.challentec.lmssseting.net.SocketClient;
import com.challentec.lmssseting.reciver.AppConnectStateRecever;
import com.challentec.lmssseting.reciver.AppMessageRecever;
import com.challentec.lmssseting.service.BeatService;
import com.challentec.lmssseting.util.LogUtil;
import com.challentec.lmssseting.util.PollingUtils;

/**
 * 
 * @author 泰得利通 wanglu app管理类 对activity进行管理，并且开始心跳，停止心跳，广播接收着管理
 */
public class AppManager {

	public static AppManager instance;
	private Context context;
	public static Stack<Activity> activityStack;// activity栈

	private AppManager() {
	};

	public synchronized static AppManager getManager(Context context) {

		if (instance == null) {
			instance = new AppManager();
			instance.context = context;
		}
		return instance;
	}

	/**
	 * 
	 * @author 泰得利通 wanglu
	 * @param activity
	 */
	public void addActivity(Activity activity) {
		if (activityStack == null) {
			activityStack = new Stack<Activity>();
		}
		activityStack.add(activity);
	}

	/**
	 * 获得当前activity
	 * 
	 * @author 泰得利通 wanglu
	 * @return
	 */
	public Activity getCurrentActivity() {
		return activityStack.lastElement();
	}

	/**
	 * finish堆栈最上面的activty
	 * 
	 * @author 泰得利通 wanglu
	 */
	public void finishActivity() {
		Activity activity = activityStack.lastElement();
		finishActivity(activity);
	}

	/**
	 * 
	 * @author 泰得利通 wanglu
	 * @param cls
	 */
	public void finishActivity(Class<?> cls) {
		for (Activity activity : activityStack) {
			if (activity.getClass().equals(cls)) {
				finishActivity(activity);
			}
		}
	}

	/**
	 * 结束所有activity
	 * 
	 * @author 泰得利通 wanglu
	 */
	public void finishAllActivity() {
		for (int i = 0, size = activityStack.size(); i < size; i++) {
			if (null != activityStack.get(i)) {
				activityStack.get(i).finish();
			}
		}
		activityStack.clear();
	}

	/**
	 * 跳转到登陆
	 * 
	 * @author 泰得利通 wanglu
	 */
	public void redirtToLogin() {
		for (int i = 1, size = activityStack.size(); i < size; i++) {
			if (null != activityStack.get(i)) {
				activityStack.get(i).finish();
			}
		}

	}

	/**
	 * 结束activity
	 * 
	 * @author 泰得利通 wanglu
	 * @param activity
	 */
	public void finishActivity(Activity activity) {
		if (activity != null) {
			activityStack.remove(activity);
			activity.finish();
			activity = null;
		}
	}

	/**
	 * 退出APP
	 * 
	 * @author 泰得利通 wanglu
	 * @param context
	 */
	public void appExit(Context context) {
		try {
			stopBeat();
			// PollingUtils.stopPollingService(context,
			// LoginPollingService.class, LoginPollingService.ACTION);
			// PollingUtils.stopPollingService(context,
			// AutoConnectPollingService.class,
			// AutoConnectPollingService.ACTION);
			SocketClient.getSocketClient().dispose();// 释放连接资源

			finishAllActivity();
			ActivityManager activityMgr = (ActivityManager) context
					.getSystemService(Context.ACTIVITY_SERVICE);
			activityMgr.restartPackage(context.getPackageName());
			System.exit(0);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取手机IMEI
	 * 
	 * @author 泰得利通 wanglu
	 * @return
	 */
	public static String getIMEI(Context context) {

		TelephonyManager telephonyManager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		return telephonyManager.getDeviceId() == null ? UUID.randomUUID() + ""
				: telephonyManager.getDeviceId();

	}

	/**
	 * 注册消息接收类
	 * 
	 * @author 泰得利通 wanglu
	 * @param functionCode
	 *            功能代码
	 * @return
	 */

	public AppMessageRecever registerAppMessageRecever(Context context) {

		AppMessageRecever appMessageRecever = new AppMessageRecever();
		IntentFilter filter = new IntentFilter();
		filter.addAction(AppMessageRecever.ACTION_STRING);
		context.registerReceiver(appMessageRecever, filter);
		return appMessageRecever;

	}

	/**
	 * 注册APP连接状态监听
	 * 
	 * @author 泰得利通 wanglu
	 * @param context
	 * @return
	 */

	public AppConnectStateRecever registerAppConnectStateRecever(Context context) {

		AppConnectStateRecever appConnectStateRecever = new AppConnectStateRecever();
		IntentFilter filter = new IntentFilter();
		filter.addAction(AppConnectStateRecever.ACTION_STRING);
		context.registerReceiver(appConnectStateRecever, filter);
		return appConnectStateRecever;

	}

	/**
	 * 启动心跳
	 * 
	 * @author 泰得利通 wanglu
	 */

	public void startBeat() {
		LogUtil.i(LogUtil.LOG_TAG_BEAT, "开始心跳");
		PollingUtils.startPollingService(context, AppConfig.POLL_INTERVAL_TIME,
				BeatService.class, BeatService.ACTION);
	}

	/**
	 * 停止心跳
	 * 
	 * @author 泰得利通 wanglu
	 */

	public void stopBeat() {
		LogUtil.i(LogUtil.LOG_TAG_BEAT, "停止心跳");
		PollingUtils.stopPollingService(context, BeatService.class,
				BeatService.ACTION);

	}

	/**
	 * 获取mac地址 wanglu 泰得利通
	 * 
	 * @return
	 */
	public static String getMac() {
		return "12345678901234";
	}
}
