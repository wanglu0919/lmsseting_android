package com.challentec.lmssseting.app;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * app配置文件 处理类
 * 
 * @author 泰得利通 wanglu
 * 
 */
public class AppConfig {

	private static AppConfig appConfig;
	private static final String SP_CONFIG = "sp_config";// SharePrefence配置文件名称
	
	public static final int POLL_INTERVAL_TIME = 20;// 心跳时间间隔
	public static final int AUTO_CONNECT_TIME = 5;// 自动重练时间
	public static final String SERVER__HANG_TIME_KEY = "server_hang_time_key";// 服务端挂了时间
	private static SharedPreferences sp;
	private Context mContext;

	/**
	 * 单列
	 * 
	 * @author 泰得利通 wanglu
	 * @param context
	 * @return
	 */
	public synchronized static AppConfig getAppConfig(Context context) {
		if (appConfig == null) {
			appConfig = new AppConfig();
			appConfig.mContext = context;
		}
		return appConfig;
	}

	/**
	 * 获取SharedPreferences
	 * 
	 * @author 泰得利通 wanglu
	 * @return
	 */
	public SharedPreferences getSharedPreferences() {

		sp = mContext.getSharedPreferences(SP_CONFIG, Context.MODE_PRIVATE);
		return sp;

	}

	public static int getSharePreferenceInt(String key) {
		return sp.getInt(key, 0);
	}

}
