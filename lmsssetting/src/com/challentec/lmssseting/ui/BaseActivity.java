package com.challentec.lmssseting.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.challentec.lmssseting.app.AppManager;
import com.challentec.lmssseting.app.R;

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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_base_layout);
		appManager = AppManager.getManager(this);
		appManager.addActivity(this);
		findViews();
		initUI();
		addListeners();

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

	}

	/**
	 * 查找初始化控件
	 * 
	 * @author 泰得利通 wanglu
	 */
	private void findViews() {
		head_btn_back = (Button) findViewById(R.id.head_btn_back);// 返回
		head_tv_title = (TextView) findViewById(R.id.head_tv_title);
		base_main_llview = (FrameLayout) findViewById(R.id.base_main_llview);
		pb_head = (ProgressBar) findViewById(R.id.pb_head);
		pb_text = (TextView) findViewById(R.id.pb_text);
		mainView = View.inflate(this, getMainViewLayoutId(), null);

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

}
