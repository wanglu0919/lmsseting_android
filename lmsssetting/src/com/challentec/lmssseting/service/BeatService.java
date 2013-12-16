package com.challentec.lmssseting.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.challentec.lmssseting.api.ClinetApi;
import com.challentec.lmssseting.app.AppContext;
import com.challentec.lmssseting.net.SynTask;
import com.challentec.lmssseting.util.LogUtil;

/**
 * 心跳服务
 * 
 * @author 泰得利通 wanglu
 * 
 */
public class BeatService extends Service {

	public static final String ACTION = "lmss.service.BeatService";

	private AppContext appContext;
	private SynTask synTask;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {

		appContext = (AppContext) getApplication();

		synTask = new SynTask(appContext);

	}

	@Override
	public void onStart(Intent intent, int startId) {
		beat();

	}

	/**
	 * 发送心跳数据
	 * 
	 * @author 泰得利通 wanglu
	 */
	private void beat() {
		String beatData = ClinetApi.getBeat();
		LogUtil.i(LogUtil.LOG_TAG_BEAT, "发送了心跳数据" + beatData);
		synTask.writeData(beatData);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		LogUtil.i(LogUtil.LOG_TAG_BEAT, "Beat destroy");

	}

}