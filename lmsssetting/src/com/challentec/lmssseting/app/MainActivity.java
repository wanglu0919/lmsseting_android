package com.challentec.lmssseting.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.challentec.lmssseting.ui.SettingActivity;

public class MainActivity extends Activity {
	private Button main_btn_connect;
	private long lastExitTime = 0;
	private AppManager appManager;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_layout);
		appManager=AppManager.getManager(this);
		appManager.addActivity(this);
		findViews();
		addListeners();
	}

	private void addListeners() {
		main_btn_connect.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Intent intent = new Intent(MainActivity.this,
						SettingActivity.class);
				startActivity(intent);
			}
		});

	}

	private void findViews() {
		main_btn_connect = (Button) findViewById(R.id.main_btn_connect);

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
			Toast.makeText(getApplicationContext(),
					"再按一次退出创联物联网测试程序",
					Toast.LENGTH_SHORT).show();
			lastExitTime = System.currentTimeMillis();
		} else {

			AppManager.getManager(this).appExit(this);
		}
	}
}
