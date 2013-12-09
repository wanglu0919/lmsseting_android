package com.challentec.lmssseting.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.challentec.lmssseting.ui.SettingActivity;

public class MainActivity extends Activity {
	private Button main_btn_connect;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_layout);
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

}
