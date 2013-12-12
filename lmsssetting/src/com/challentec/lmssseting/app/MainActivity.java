package com.challentec.lmssseting.app;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.challentec.lmssseting.api.Protocol;
import com.challentec.lmssseting.net.SocketClient;
import com.challentec.lmssseting.net.SynHandler;
import com.challentec.lmssseting.net.SynTask;
import com.challentec.lmssseting.ui.SettingActivity;
import com.challentec.lmssseting.util.LogUtil;
import com.challentec.lmssseting.util.UIHelper;
import com.challentec.lmssseting.view.LoadProgressView;

public class MainActivity extends Activity {
	private Button main_btn_connect;
	private long lastExitTime = 0;
	private AppManager appManager;
	private EditText main_login_ip;
	private EditText main_login_port;
	private LoadProgressView load_view;
	private SynTask synTask;
	private AppContext appContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_layout);
		appManager = AppManager.getManager(this);
		appManager.addActivity(this);
		appContext = (AppContext) getApplication();
		findViews();
		addListeners();
		synTask = new SynTask(new ConnectSynHandler(), appContext);

	}

	/**
	 * 连接服务器
	 * 
	 * @author wanglu 泰得利通
	 * 
	 */
	@SuppressLint("HandlerLeak")
	private class ConnectSynHandler extends SynHandler {

		@Override
		public void onConnectSuccess(String msg) {
/*
			Intent intent = new Intent(MainActivity.this, SettingActivity.class);
			startActivity(intent);
			*/
			regist();
		}

		@Override
		public void onFianly() {

			super.onFianly();
			load_view.setVisibility(View.GONE);
		}

	}

	private void addListeners() {
		main_btn_connect.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				if (checkInput()) {

					connectServer();

				}

			}
		});

	}
	
	/**
	 * 注册
	 *wanglu 泰得利通
	 */
	private void regist(){
		String apiData=Protocol.HEAD+"0002"+"00"+appManager.getMac()+"0000";
		LogUtil.i(LogUtil.LOG_TAG_WRITE_DATA, apiData);
		synTask.writeData(apiData);
		
		
	}

	/**
	 * 连接服务器 wanglu 泰得利通
	 */
	private void connectServer() {
		load_view.setVisibility(View.VISIBLE);
		load_view.setProgressText("正在连接服务器...");
		synTask.connectServer(SocketClient.getSocketClient(), main_login_ip
				.getText().toString(), Integer.parseInt(main_login_port
				.getText().toString()));// ;连接服务器
	}

	/**
	 * 查找组件 wanglu 泰得利通
	 */

	private void findViews() {
		main_btn_connect = (Button) findViewById(R.id.main_btn_connect);
		main_login_ip = (EditText) findViewById(R.id.main_login_ip);
		main_login_port = (EditText) findViewById(R.id.main_login_port);
		load_view = (LoadProgressView) findViewById(R.id.main_pb_load);
		main_login_ip.setText("61.160.96.205");
		main_login_port.setText("9704");

	}

	/**
	 * 检查输入 wanglu 泰得利通
	 * 
	 * @return
	 */
	private boolean checkInput() {

		if (main_login_ip.getText().toString().equals("")) {
			UIHelper.showToask(this, "请输入IP地址");
			main_login_ip.requestFocus();
			return false;
		} else if (main_login_port.getText().toString().equals("")) {
			UIHelper.showToask(this, "请输入端口");
			main_login_ip.requestFocus();
			return false;
		}
		return true;

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			exit();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * 退出app
	 * 
	 * @author 泰得利通 wanglu
	 */

	public void exit() {

		if ((System.currentTimeMillis() - lastExitTime) > 2000) {
			Toast.makeText(getApplicationContext(), "再按一次退出创联物联网测试程序",
					Toast.LENGTH_SHORT).show();
			lastExitTime = System.currentTimeMillis();
		} else {

			AppManager.getManager(this).appExit(this);
		}
	}
}
