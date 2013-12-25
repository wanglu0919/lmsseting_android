package com.challentec.lmssseting.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.challentec.lmssseting.api.ClinetApi;
import com.challentec.lmssseting.api.Protocol;
import com.challentec.lmssseting.app.AppContext;
import com.challentec.lmssseting.app.AppManager;
import com.challentec.lmssseting.app.R;
import com.challentec.lmssseting.bean.ResponseData;
import com.challentec.lmssseting.listener.AppConectStateListener;
import com.challentec.lmssseting.listener.AppMessageLinstener;
import com.challentec.lmssseting.net.SocketClient;
import com.challentec.lmssseting.net.SynHandler;
import com.challentec.lmssseting.net.SynTask;
import com.challentec.lmssseting.reciver.AppConnectStateRecever;
import com.challentec.lmssseting.reciver.AppMessageRecever;
import com.challentec.lmssseting.util.UIHelper;

/**
 * activity基类
 * 
 * @author 泰得利通 wanglu
 * 
 */
public abstract class BaseActivity extends Activity {

	private Button head_btn_back;// 回退按钮
	private TextView head_tv_title;// 标题
	protected ProgressBar pb_head;// pb_head 头部进度条
	protected TextView pb_text;// 加载进度条数字
	private FrameLayout base_main_llview;// 中间视图parentView
	protected View mainView;// 中间视图view
	protected AppManager appManager;
	private AppConnectStateRecever appConnectStateRecever;
	private Button head_btn_save;//保存
	protected AppMessageRecever appMessageRecever;
	protected SynTask SynTask;
	protected AppContext appContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_base_layout);
		appContext = (AppContext) getApplication();
		appManager = AppManager.getManager(this);
		appManager.addActivity(this);
		findViews();
		initUI();
		addListeners();

		SynTask = new SynTask(new ConnectSynHandler(), appContext);

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
			super.onConnectSuccess(msg);
			regist();
		}

		@Override
		public void onFianly() {

			super.onFianly();

		}

	}

	/**
	 * 注册 wanglu 泰得利通
	 */
	private void regist() {
		pb_text.setVisibility(View.VISIBLE);
		pb_text.setText("注册中");
		String apiData = ClinetApi.getRegistData(AppManager.getIMEI(this));

		SynTask.writeData(apiData);

	}

	/**
	 * 网络状态注册监听 wanglu 泰得利通
	 */
	private void registAppContectStateReceiver() {

		appConnectStateRecever = appManager
				.registerAppConnectStateRecever(this);

		appConnectStateRecever
				.setAppConectStateListener(new AppContectedStateListener());

	}

	/**
	 * 注册消息 wanglu 泰得利通
	 */
	private void registAppMessageReceiver() {

		appMessageRecever = appManager.registerAppMessageRecever(this);
		appMessageRecever.setAppMessageLinstener(new MainAppMessageListener());

	}

	/**
	 * 连接发生改变
	 * 
	 * @author wanglu 泰得利通
	 * 
	 */
	private class AppContectedStateListener implements AppConectStateListener {

		@Override
		public void connectStateChanged() {

			connectServer();// 连接服务器

		}

	}

	/**
	 * 消息监听
	 * 
	 * @author wanglu 泰得利通
	 * 
	 */
	private class MainAppMessageListener implements AppMessageLinstener {

		@Override
		public void onRespnseDataReceve(ResponseData responseData) {
			if (responseData.getFunctionCode().equals(Protocol.S_C_REGIST)) {// 注册结果
				String reslutData = responseData.getHexdata();
				if (reslutData.equals("01")) {
					UIHelper.showToask(appContext, "注册成功");
					Intent intent = new Intent(BaseActivity.this,
							SettingActivity.class);
					startActivity(intent);
					appManager.startBeat();// 开始心跳

					pb_text.setVisibility(View.GONE);

				} else if (reslutData.equals("02")) {
					UIHelper.showToask(appContext, "注册失败");

					pb_text.setVisibility(View.VISIBLE);
					pb_text.setText("注册失败");

				}

			}

			onReceveData(responseData);// 传递子activity实现

		}

	}

	protected abstract void onReceveData(ResponseData responseData);

	/**
	 * 连接服务器 wanglu 泰得利通
	 */
	private void connectServer() {

		pb_text.setVisibility(View.VISIBLE);
		pb_text.setText("未连接");
		SynTask.connectServer(SocketClient.getSocketClient());// ;连接服务器
	}

	private void initUI() {

		head_tv_title.setText(getTitleText());
		if (mainView != null) {
			base_main_llview.addView(mainView);
			initMainView(mainView);
		}

	}

	/**
	 * 初始化内容视图
	 * 
	 * @author 泰得利通 wanglu
	 * @param mainView
	 */
	protected abstract void initMainView(View mainView);

	/**
	 * 标题名称
	 * 
	 * @author 泰得利通 wanglu
	 * @return
	 */
	protected abstract CharSequence getTitleText();

	/**
	 * 
	 * @author 泰得利通 wanglu
	 * @return 中间视图id
	 */
	protected abstract int getMainViewLayoutId();

	protected void addListeners() {
		head_btn_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				onBack();
			}
		});
		head_btn_save.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				onSave();
				
			}
		});
	}
	

	/**
	 * 保存 
	 *wanglu 泰得利通
	 */
	protected void onSave() {
	
		
	}

	/**
	 * 查找初始化控件
	 * 
	 * @author 泰得利通 wanglu
	 */
	private void findViews() {
		head_btn_back = (Button) findViewById(R.id.head_btn_back);// 返回
		head_btn_save=(Button) findViewById(R.id.head_btn_save);//保存
		head_tv_title = (TextView) findViewById(R.id.head_tv_title);
		base_main_llview = (FrameLayout) findViewById(R.id.base_main_llview);
		pb_head = (ProgressBar) findViewById(R.id.pb_head);
		pb_text = (TextView) findViewById(R.id.pb_text);
		
		mainView = View.inflate(this, getMainViewLayoutId(), null);

	}
	
	protected void showSaveButton(boolean isShow){
		if(isShow){
			head_btn_save.setVisibility(View.VISIBLE);
		}else{
			head_btn_save.setVisibility(View.GONE);
		}
		
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			onBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * 
	 * @author 泰得利通 wanglu 返回
	 */
	protected void onBack() {
		appManager.finishActivity(this);

	}

	@Override
	protected void onStart() {

		super.onStart();
		registAppContectStateReceiver();
		registAppMessageReceiver();
	}

	@Override
	protected void onStop() {
		super.onStop();
		unregisterReceiver(appConnectStateRecever);
		unregisterReceiver(appMessageRecever);
	}

}
